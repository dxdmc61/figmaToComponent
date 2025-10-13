package com.figma.aem.core.servlets;

import com.google.auth.oauth2.GoogleCredentials;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Collections;

public class GeminiAccessTokenDemo {

  // 🛑 REMOVED: private static final String SERVICE_ACCOUNT_JSON = ...

  public static void main(String[] args) throws Exception {
    System.out.println("🔑 Authenticating with Google Gemini...");

    // 1️⃣ Load credentials from a file in the resources folder
    InputStream credentialsStream =
    GeminiAccessTokenDemo.class.getClassLoader().getResourceAsStream("googleFile.json");

    if (credentialsStream == null) {
      System.err.println(
          "FATAL ERROR: Could not find 'service-account-key.json' in the classpath (e.g., in the 'resources' folder).");
      return; // Exit if file not found
    }

    GoogleCredentials credentials =
        GoogleCredentials.fromStream(credentialsStream)
            .createScoped(
                Collections.singletonList("https://www.googleapis.com/auth/cloud-platform"));

    // Close the stream once credentials are loaded
    credentialsStream.close();

    // Refresh to get access token
    credentials.refreshIfExpired();
    String accessToken = credentials.getAccessToken().getTokenValue();
    System.out.println("✅ Access Token: " + accessToken);

    // 2️⃣ Make API request to Google Gemini (using the correct Vertex AI/Gemini endpoint)
    String project = "wipro-dmc-aem-dev";
    String model = "gemini-2.5-flash"; // Use a Gemini model
    String endpoint =
        "https://us-central1-aiplatform.googleapis.com/v1/projects/"
            + project
            + "/locations/us-central1/publishers/google/models/"
            + model
            + ":generateContent"; // Correct method for Gemini

    // Prepare JSON payload for the model - Correct format for Gemini
    String requestJson =
        "{\"contents\": [{\"role\": \"user\", \"parts\": [{\"text\": \"Write a short description of AEM component generation\"}]}]}";

    // 3️⃣ Open connection
    URL url = new URL(endpoint);
    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
    conn.setRequestMethod("POST");
    conn.setDoOutput(true);
    conn.setRequestProperty("Authorization", "Bearer " + accessToken);
    conn.setRequestProperty("Content-Type", "application/json; charset=UTF-8");

    // 4️⃣ Send request
    try (OutputStream os = conn.getOutputStream()) {
      byte[] input = requestJson.getBytes(StandardCharsets.UTF_8);
      os.write(input);
    }

    // 5️⃣ Read response
    int status = conn.getResponseCode();
    InputStream responseStream =
        (status >= 200 && status < 300) ? conn.getInputStream() : conn.getErrorStream();

    System.out.println("📝 Gemini Response (Status: " + status + "):");
    try (BufferedReader br =
        new BufferedReader(new InputStreamReader(responseStream, StandardCharsets.UTF_8))) {
      String line;
      while ((line = br.readLine()) != null) {
        System.out.println(line); // Print the response line by line
      }
    }
  }
}