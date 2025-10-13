package com.figma.aem.core.servlets;

import com.google.auth.oauth2.GoogleCredentials;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;
import org.osgi.service.component.annotations.Component;
import javax.servlet.Servlet;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Collections;

@Component(
    service = {Servlet.class},
    property = {
        "sling.servlet.methods=GET",
        "sling.servlet.paths=/bin/gemini/demo"
    })
public class GeminiApiServlet extends SlingAllMethodsServlet {

    private static final String LOCAL_JSON_FILE = "googleFile.json"; 
    // 👆 Place this file in the same directory as this servlet class file

    @Override
    protected void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response)
            throws IOException {

        response.setContentType("application/json;charset=UTF-8");
        PrintWriter out = response.getWriter();
        out.println("{");
        out.println("\"status\":\"Authenticating with Google Gemini...\",");
        out.flush();

        try {
            // 🟢 Locate the googleFile.json in the same directory as this class
            File currentDir = new File(GeminiApiServlet.class.getProtectionDomain().getCodeSource().getLocation().getPath());
            File jsonFile = new File(currentDir.getParentFile(), LOCAL_JSON_FILE);

            // Fallback (if the file is not found next to class)
            if (!jsonFile.exists()) {
                // Optional: absolute path fallback (for AEM environments)
                jsonFile = new File("/opt/aem/" + LOCAL_JSON_FILE);
            }

            if (!jsonFile.exists()) {
                response.setStatus(500);
                out.println("\"error\":\"googleFile.json not found at: " + jsonFile.getAbsolutePath() + "\"");
                out.println("}");
                return;
            }

            // 🧩 Load credentials
            try (InputStream credentialsStream = new FileInputStream(jsonFile)) {
                GoogleCredentials credentials =
                        GoogleCredentials.fromStream(credentialsStream)
                                .createScoped(Collections.singletonList("https://www.googleapis.com/auth/cloud-platform"));
                credentials.refreshIfExpired();

                String accessToken = credentials.getAccessToken().getTokenValue();
                out.println("\"accessToken\":\"" + accessToken.substring(0, 25) + "...\",");
                out.flush();

                // 🪄 Gemini API call
                String project = "wipro-dmc-aem-dev";
                String model = "gemini-2.5-flash";
                String endpoint =
                        "https://us-central1-aiplatform.googleapis.com/v1/projects/"
                                + project
                                + "/locations/us-central1/publishers/google/models/"
                                + model
                                + ":generateContent";

                String requestJson =
                        "{\"contents\": [{\"role\": \"user\", \"parts\": [{\"text\": \"Write a short description of AEM component generation\"}]}]}";

                URL url = new URL(endpoint);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("POST");
                conn.setDoOutput(true);
                conn.setRequestProperty("Authorization", "Bearer " + accessToken);
                conn.setRequestProperty("Content-Type", "application/json; charset=UTF-8");

                try (OutputStream os = conn.getOutputStream()) {
                    os.write(requestJson.getBytes(StandardCharsets.UTF_8));
                }

                int status = conn.getResponseCode();
                InputStream responseStream =
                        (status >= 200 && status < 300) ? conn.getInputStream() : conn.getErrorStream();

                BufferedReader br = new BufferedReader(new InputStreamReader(responseStream, StandardCharsets.UTF_8));
                StringBuilder sb = new StringBuilder();
                String line;
                while ((line = br.readLine()) != null) {
                    sb.append(line);
                }

                response.setStatus(status);
                out.println("\"geminiResponse\":" + sb.toString());
                out.println("}");
            }

        } catch (Exception e) {
            response.setStatus(500);
            out.println("\"error\":\"" + e.getMessage().replace("\"", "'") + "\"");
            out.println("}");
        }
    }
}