package com.figma.aem.core.services.imp;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.servlet.http.Part;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ChatGPTGeneratorTest {
    private ChatGPTGenerator generator;
    private final String apiKey = "test-api-key";

    @BeforeEach
    void setUp() {
        generator = new ChatGPTGenerator(apiKey);
    }

    @Test
    void testExtractFigmaJson() throws Exception {
        Part part = Mockito.mock(Part.class);
        String json = "{\"key\":\"value\"}";
        InputStream is = new ByteArrayInputStream(json.getBytes());
        Mockito.when(part.getInputStream()).thenReturn(is);
        String result = generator.extractFigmaJson(part);
        assertEquals(json, result);
    }

    @Test
    void testBuildUserPrompt() {
        String componentName = "TestComponent";
        String prompt = "Add a button.";
        String figmaJson = "{\"type\":\"button\"}";
        String result = generator.buildUserPrompt(componentName, prompt, figmaJson);
        assertTrue(result.contains(componentName));
        assertTrue(result.contains(prompt));
        assertTrue(result.contains(figmaJson));
    }

    @Test
    void testParseGeneratedCode_validJson() {
        String response = "{\n  \"component.html\": \"<div>Test</div>\",\n  \"dialog.xml\": \"<dialog/>\"\n}";
        Map<String, String> files = generator.parseGeneratedCode(response);
        assertEquals("<div>Test</div>", files.get("component.html"));
        assertEquals("<dialog/>", files.get("dialog.xml"));
    }

    @Test
    void testParseGeneratedCode_withCodeBlockFormatting() {
        String response = "```json\n{\n  \"component.html\": \"<div>Test</div>\"\n}\n```";
        Map<String, String> files = generator.parseGeneratedCode(response);
        assertEquals("<div>Test</div>", files.get("component.html"));
    }

    @Test
    void testParseGeneratedCode_invalidJson() {
        String response = "not a json";
        Exception exception = assertThrows(RuntimeException.class, () -> generator.parseGeneratedCode(response));
        assertTrue(exception.getMessage().contains("Failed to parse AI JSON output"));
    }

    @Test
    void testGenerateComponent_success() throws Exception {
        Part part = Mockito.mock(Part.class);
        String figmaJson = "{\"type\":\"button\"}";
        InputStream is = new ByteArrayInputStream(figmaJson.getBytes());
        Mockito.when(part.getInputStream()).thenReturn(is);

        ChatGPTGenerator spyGenerator = Mockito.spy(new ChatGPTGenerator(apiKey));
        String userPrompt = "user prompt";
        String responseContent = "{\"component.html\":\"<div>Test</div>\"}";

        Mockito.doReturn(userPrompt).when(spyGenerator).buildUserPrompt(Mockito.anyString(), Mockito.anyString(), Mockito.anyString());
        Mockito.doReturn(responseContent).when(spyGenerator).callOpenAI(Mockito.anyString());

        Map<String, String> result = spyGenerator.generateComponent(part, "TestComponent", "Add a button.");
        assertEquals("<div>Test</div>", result.get("component.html"));
    }

}