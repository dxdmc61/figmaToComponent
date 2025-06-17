package com.figma.aem.core.services;

import com.figma.aem.core.services.imp.ChatGPTGenerator;
import com.figma.aem.core.services.imp.DeepSeekGenerator;
import com.figma.aem.core.services.imp.GeminiGenerator;

public class AIGeneratorFactory {
    public static AIGenerator getGenerator(String provider, String apiKey) {
        switch (provider.toLowerCase()) {
            case "deepseek":
                return new DeepSeekGenerator(apiKey);
            case "chatgpt":
                return new ChatGPTGenerator(apiKey);
            case "gemini":
                return new GeminiGenerator(apiKey);
            default:
                throw new IllegalArgumentException("Unsupported AI provider: " + provider);
        }
    }
}