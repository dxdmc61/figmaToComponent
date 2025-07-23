package com.figma.aem.core.services.imp;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.servlet.http.Part;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class GeminiGeneratorTest {
    @Mock
    private Part figmaFile;

    private GeminiGenerator geminiGenerator;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        geminiGenerator = new GeminiGenerator("dummy-key");
    }

    @Test
    void testExtractFigmaJson() throws Exception {
        String json = "{\"test\":123}";
        InputStream is = new ByteArrayInputStream(json.getBytes(StandardCharsets.UTF_8));
        when(figmaFile.getInputStream()).thenReturn(is);
        java.lang.reflect.Method method = geminiGenerator.getClass().getDeclaredMethod("extractFigmaJson", Part.class);
        method.setAccessible(true);
        String result = method.invoke(geminiGenerator, figmaFile).toString();
        assertEquals(json, result);
    }

    @Test
    void testBuildUserPrompt() throws Exception {
        String componentName = "TestComponent";
        String prompt = "Generate a button.";
        String figmaJson = "{\"type\":\"button\"}";
        java.lang.reflect.Method method = geminiGenerator.getClass().getDeclaredMethod("buildUserPrompt", String.class, String.class, String.class);
        method.setAccessible(true);
        String result = method.invoke(geminiGenerator, componentName, prompt, figmaJson).toString();
        assertTrue(result.contains(componentName));
        assertTrue(result.contains(prompt));
        assertTrue(result.contains(figmaJson));
    }

    @Test
    void testParseGeneratedCode() throws Exception {
        String json = "{\"file1.html\":\"<div>Test</div>\",\"file2.js\":\"console.log('hi');\"}";
        java.lang.reflect.Method method = geminiGenerator.getClass().getDeclaredMethod("parseGeneratedCode", String.class);
        method.setAccessible(true);
        @SuppressWarnings("unchecked")
        Map<String, String> files = (Map<String, String>) method.invoke(geminiGenerator, json);
        assertEquals(2, files.size());
        assertEquals("<div>Test</div>", files.get("file1.html"));
        assertEquals("console.log('hi');", files.get("file2.js"));
    }

    @Test
    void testParseGeneratedCodeWithCodeBlock() throws Exception {
        String json = "```json\n{\"file.html\":\"<div>Test</div>\"}```";
        java.lang.reflect.Method method = geminiGenerator.getClass().getDeclaredMethod("parseGeneratedCode", String.class);
        method.setAccessible(true);
        @SuppressWarnings("unchecked")
        Map<String, String> files = (Map<String, String>) method.invoke(geminiGenerator, json);
        assertEquals(1, files.size());
        assertEquals("<div>Test</div>", files.get("file.html"));
    }

    @Test
    void testGenerateComponentReturnsFiles() throws Exception {
        String json = "{\"file.html\":\"<div>Test</div>\"}";
        InputStream is = new ByteArrayInputStream(json.getBytes(StandardCharsets.UTF_8));
        when(figmaFile.getInputStream()).thenReturn(is);
        Map<String, String> files = geminiGenerator.generateComponent(figmaFile, "TestComponent", "Prompt");
        assertNotNull(files);
        assertTrue(files.containsKey("productCarousel.html"));
    }
}