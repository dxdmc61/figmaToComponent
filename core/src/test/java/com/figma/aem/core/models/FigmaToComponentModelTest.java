package com.figma.aem.core.models;

import org.apache.sling.api.SlingHttpServletRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;

class FigmaToComponentModelTest {
    private FigmaToComponentModel model;

    @BeforeEach
    void setUp() {
        model = new FigmaToComponentModel();
        // Use reflection to set private fields for testing
        setField("aiProvider", "chatgpt");
        setField("apiKey", "test-key");
        setField("prompt", "test-prompt");
        setField("projectDirectory", "/test/dir");
        setField("jenkinsUrl", "http://jenkins.local");
    }

    private void setField(String fieldName, Object value) {
        try {
            Class<?> clazz = model.getClass();
            java.lang.reflect.Field field = null;
            while (clazz != null) {
                try {
                    field = clazz.getDeclaredField(fieldName);
                    break;
                } catch (NoSuchFieldException e) {
                    clazz = clazz.getSuperclass();
                }
            }
            if (field == null) {
                throw new RuntimeException("Field not found: " + fieldName);
            }
            field.setAccessible(true);
            field.set(model, value);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void testGetServletPath() {
        assertEquals("/bin/figma/generate-component/v1", model.getServletPath());
    }

    @Test
    void testGetAiProvider() {
        assertEquals("chatgpt", model.getAiProvider());
    }

    @Test
    void testGetApiKey() {
        assertEquals("test-key", model.getApiKey());
    }

    @Test
    void testGetPrompt() {
        assertEquals("test-prompt", model.getPrompt());
    }

    @Test
    void testGetProjectDirectory() {
        assertEquals("/test/dir", model.getProjectDirectory());
    }

    @Test
    void testGetJenkinsUrl() {
        assertEquals("http://jenkins.local", model.getJenkinsUrl());
    }
}