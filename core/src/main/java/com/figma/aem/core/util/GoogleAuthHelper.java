package com.figma.aem.core.util;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Collections;

import com.google.auth.oauth2.GoogleCredentials;

public class GoogleAuthHelper {
    public static String getAccessToken(String serviceAccountPath) throws IOException {
        GoogleCredentials credentials = GoogleCredentials
            .fromStream(new FileInputStream(serviceAccountPath))
            .createScoped(Collections.singletonList("https://www.googleapis.com/auth/cloud-platform"));

        credentials.refreshIfExpired();
        return credentials.getAccessToken().getTokenValue();
    }
}

