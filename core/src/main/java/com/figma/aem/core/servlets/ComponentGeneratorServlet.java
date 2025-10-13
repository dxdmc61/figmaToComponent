package com.figma.aem.core.servlets;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import com.figma.aem.core.services.AIGenerator;
import com.figma.aem.core.services.AIGeneratorFactory;
import com.figma.aem.core.util.FileSaver;
import com.google.auth.oauth2.GoogleCredentials;

import javax.servlet.Servlet;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import org.apache.sling.servlets.annotations.SlingServletPaths;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.Map;

@Component(service = Servlet.class)
@SlingServletPaths("/bin/figma/generate-component")
public class ComponentGeneratorServlet extends SlingAllMethodsServlet {

    @Reference
    private FileSaver fileSaver;

    @Override
    protected void doPost(SlingHttpServletRequest request, SlingHttpServletResponse response) throws IOException {
        try {
            String aiProvider = request.getParameter("aiProvider");
            String apiKey = request.getParameter("apiKey");
            String prompt = request.getParameter("prompt");
            String componentName = request.getParameter("componentName");
            String projectDirectory = request.getParameter("projectDirectory");
            String jenkinsUrl = request.getParameter("jenkinsUrl");

            // ⚠️ Hardcoded Google service account JSON for testing (never commit this to
            // Git!)
            String serviceAccountJson = "{\n" +
                    "  \"type\": \"service_account\",\n" +
                    "  \"project_id\": \"erp-dmc-eds-dev\",\n" +
                    "  \"private_key_id\": \"4b5b2070514c42ec91706e42c1a0ea6f62f22b63\",\n" +
                    "  \"private_key\": \"-----BEGIN PRIVATE KEY-----\\nMIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQDZZo+jA7OQdJX2\\n8y8Ym5EnHWZpOfjY7F32KNwhJ3fFcRww2d8HEm/Po4W7f7UjHqHc88r8F+mioc5f\\nx+G8fSgg77KhCTfyeAg9Mw+3ITnUAZe2RVtHxDTI3ytur5RknjU9gUeDkUO9ZpPc\\n...snipped...\\n-----END PRIVATE KEY-----\\n\",\n"
                    +
                    "  \"client_email\": \"eds-dev@erp-dmc-eds-dev.iam.gserviceaccount.com\",\n" +
                    "  \"client_id\": \"113013541530942054214\",\n" +
                    "  \"auth_uri\": \"https://accounts.google.com/o/oauth2/auth\",\n" +
                    "  \"token_uri\": \"https://oauth2.googleapis.com/token\",\n" +
                    "  \"auth_provider_x509_cert_url\": \"https://www.googleapis.com/oauth2/v1/certs\",\n" +
                    "  \"client_x509_cert_url\": \"https://www.googleapis.com/robot/v1/metadata/x509/eds-dev%40erp-dmc-eds-dev.iam.gserviceaccount.com\",\n"
                    +
                    "  \"universe_domain\": \"googleapis.com\"\n" +
                    "}";

            // Load credentials from hardcoded JSON
            GoogleCredentials credentials = GoogleCredentials
                    .fromStream(new ByteArrayInputStream(serviceAccountJson.getBytes(StandardCharsets.UTF_8)))
                    .createScoped(Collections.singletonList("https://www.googleapis.com/auth/cloud-platform"));

            // Process Figma file upload
            Part figmaFile = request.getPart("figmaFile");

            // Use the selected AI provider (ChatGPT, Gemini, DeepSeek, etc.)
            AIGenerator generator = AIGeneratorFactory.getGenerator(aiProvider, apiKey);
            Map<String, String> generatedFiles = generator.generateComponent(figmaFile, componentName, prompt);

            // Save generated component files to the AEM project structure
            //fileSaver.saveFiles(projectDirectory, componentName, generatedFiles);

            // Optional: Trigger Jenkins pipeline
            if (jenkinsUrl != null && !jenkinsUrl.isEmpty()) {
                // JenkinsTrigger.triggerBuild(jenkinsUrl);
            }

            response.getWriter().write("✅ Component generated successfully!");
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("❌ Error: " + e.getMessage());
        }
    }
}
