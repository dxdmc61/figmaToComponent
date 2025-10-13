package com.figma.aem.core.servlets;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

import javax.servlet.Servlet;
import javax.servlet.ServletException;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;
import org.osgi.service.component.annotations.Component;

@Component(service = Servlet.class, property = {
        "sling.servlet.paths=/bin/ims/login/test", // servlet path
        "sling.servlet.methods=GET" // supported method
})
public class ImsLoginRedirectServlet extends SlingAllMethodsServlet {

    private static final long serialVersionUID = 1L;

    // Replace with your Adobe IMS client details
    private static final String IMS_AUTH_URL = "https://ims-na1.adobelogin.com/ims/authorize";
    private static final String CLIENT_ID = "c7255c321071463f8a963cd8cb6e1081";
    private static final String REDIRECT_URI = "http://localhost:4502/content/redirect.html";
    private static final String RESPONSE_TYPE = "code"; // or token if implicit flow
    private static final String SCOPE = "openid,AdobeID,user_management_sdk"; // adjust scopes as needed

    @Override
    protected void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response)
            throws ServletException, IOException {

   // Build IMS login URL
        String loginUrl = IMS_AUTH_URL
                + "?client_id=" + CLIENT_ID
                + "&redirect_uri=" + URLEncoder.encode(REDIRECT_URI, StandardCharsets.UTF_8.toString())
                + "&response_type=" + RESPONSE_TYPE
                + "&scope=" + URLEncoder.encode(SCOPE, StandardCharsets.UTF_8.toString());


        // Redirect user to Adobe IMS
        response.sendRedirect(loginUrl);
    }
}
