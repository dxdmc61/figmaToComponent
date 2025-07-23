package com.figma.aem.core.services.imp;

import com.figma.aem.core.services.AIGenerator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import javax.servlet.http.Part;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class DeepSeekGeneratorTest {
    private DeepSeekGenerator generator;
    private final String apiKey = "test-api-key";

    @BeforeEach
    void setUp() {
        generator = Mockito.spy(new DeepSeekGenerator(apiKey));
    }

    @Test
    void testConstructorSetsApiKey() {
        assertNotNull(generator);
    }

    @Test
    void testGenerateComponentReturnsFiles() throws Exception {
        Part mockPart = mock(Part.class);
        String figmaJson = "{\"key\":\"value\"}";
        InputStream inputStream = new ByteArrayInputStream(figmaJson.getBytes());
        when(mockPart.getInputStream()).thenReturn(inputStream);

        // Use doAnswer to mock the private method via spy
        Mockito.doReturn("dummy code").when(generator).callDeepSeekAPI(Mockito.anyString());

        Map<String, String> result = generator.generateComponent(mockPart, "TestComponent", "Custom prompt");
        assertNotNull(result);
        assertEquals(6, result.size());
        assertTrue(result.values().stream().allMatch(code -> code.equals("dummy code")));
    }

    @Test
    void testParseFigmaFile() throws Exception {
        Part mockPart = mock(Part.class);
        String figmaJson = "{\"key\":\"value\"}";
        InputStream inputStream = new ByteArrayInputStream(figmaJson.getBytes());
        when(mockPart.getInputStream()).thenReturn(inputStream);
        String result = generator.parseFigmaFile(mockPart);
        assertEquals(figmaJson, result);
    }

    @Test
    void testCreatePrompts() {
        String componentName = "TestComponent";
        String figmaJson = "{\"key\":\"value\"}";
        String customPrompt = "Custom prompt";
        Map<String, String> prompts = generator.createPrompts(componentName, figmaJson, customPrompt);
        assertEquals(6, prompts.size());
        assertTrue(prompts.keySet().containsAll(
                java.util.Arrays.asList("html", "dialog", "editConfig", "css", "js", "model")));
    }

    @Test
    void testCallDeepSeekAPI_Mocked() throws Exception {
        // Arrange
        DeepSeekGenerator generator = Mockito.spy(new DeepSeekGenerator(apiKey));
        String prompt = "Test prompt";
        String expectedResponse = "{\"choices\":[{\"message\":{\"content\":\"mocked response\"}}]}";

        // Mock HttpURLConnection
        HttpURLConnection mockConnection = mock(HttpURLConnection.class);
        ByteArrayInputStream responseStream = new ByteArrayInputStream(expectedResponse.getBytes(StandardCharsets.UTF_8));
        when(mockConnection.getResponseCode()).thenReturn(200);
        when(mockConnection.getInputStream()).thenReturn(responseStream);
        when(mockConnection.getOutputStream()).thenReturn(mock(OutputStream.class));

        // Mock openConnection to return our mockConnection
        Mockito.doReturn(mockConnection).when(generator).openConnection(anyString());

        // Act
        String result = generator.callDeepSeekAPI(prompt);

        // Assert
        assertEquals("mocked response", result);
    }
}