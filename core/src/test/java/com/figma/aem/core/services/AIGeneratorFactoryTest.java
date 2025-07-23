package com.figma.aem.core.services;

import com.figma.aem.core.services.imp.ChatGPTGenerator;
import com.figma.aem.core.services.imp.DeepSeekGenerator;
import com.figma.aem.core.services.imp.GeminiGenerator;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AIGeneratorFactoryTest {
    @Test
    void testGetGenerator_DeepSeek() {
        AIGenerator generator = AIGeneratorFactory.getGenerator("deepseek", "dummyKey");
        assertTrue(generator instanceof DeepSeekGenerator);
    }

    @Test
    void testGetGenerator_ChatGPT() {
        AIGenerator generator = AIGeneratorFactory.getGenerator("chatgpt", "dummyKey");
        assertTrue(generator instanceof ChatGPTGenerator);
    }

    @Test
    void testGetGenerator_Gemini() {
        AIGenerator generator = AIGeneratorFactory.getGenerator("gemini", "dummyKey");
        assertTrue(generator instanceof GeminiGenerator);
    }

    @Test
    void testGetGenerator_InvalidProvider() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            AIGeneratorFactory.getGenerator("invalid", "dummyKey");
        });
        assertTrue(exception.getMessage().contains("Unsupported AI provider"));
    }
}