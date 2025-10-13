package com.figma.aem.core.servlets;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;
import org.json.JSONObject;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import javax.jcr.Value;
import javax.jcr.ValueFactory;
import javax.servlet.Servlet;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.jackrabbit.api.security.user.UserManager;
import org.apache.jackrabbit.api.security.user.User;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.jackrabbit.api.security.user.Authorizable;

@Component(service = { Servlet.class }, property = {
        "sling.servlet.methods=POST",
        "sling.servlet.paths=/bin/myproject/registration"
})
public class RegistrationServlet extends SlingAllMethodsServlet {

    @Reference
    private ResourceResolverFactory resolverFactory;

    @Override
    protected void doPost(SlingHttpServletRequest request, SlingHttpServletResponse response) throws IOException {
        String companyName = request.getParameter("companyName");
        String username = request.getParameter("username");
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        Map<String, Object> params = new HashMap<>();
        params.put(ResourceResolverFactory.SUBSERVICE, "userWriteService");

        try (ResourceResolver resolver = resolverFactory.getServiceResourceResolver(params)) {
            UserManager userManager = resolver.adaptTo(UserManager.class);

            if (userManager == null) {
                response.setContentType("application/json");
                response.getWriter().write("{\"message\": \"Could not adapt to UserManager\"}");
                return;
            }

            Authorizable existingUser = userManager.getAuthorizable(username);
            if (existingUser != null) {
                response.setContentType("application/json");
                response.getWriter().write("{\"message\": \"User already exists\"}");
                return;
            }

            ValueFactory valueFactory = resolver.adaptTo(javax.jcr.Session.class).getValueFactory();

            Value emailValue = valueFactory.createValue(email);
            Value companyValue = valueFactory.createValue(companyName);
            // ✅ Only two parameters: userID and password
            User newUser = userManager.createUser(username, password);
            newUser.setProperty("profile/email", emailValue);
            newUser.setProperty("profile/company", companyValue);

            resolver.commit(); // persist changes
            response.setContentType("application/json");
            response.getWriter().write("{\"message\": \"User successfully registered in AEM & IMS\"}");

        } catch (Exception e) {
            response.setContentType("application/json");
            response.getWriter().write("{\"message\": \"Error: " + e.getMessage() + "\"}");
        }
    }

    private boolean createUserInIMS(String username, String email, String company) {
        String imsEndpoint = "https://usermanagement.adobe.io/v2/usermanagement/users";

        try (CloseableHttpClient client = HttpClients.createDefault()) {

            // 1. Get access token (implement with IMS JWT or OAuth2)
            String accessToken = getIMSAccessToken();

            // 2. Build payload — IMS requires organization and user details
            String payload = "{\n" +
                    "  \"user\": \"" + email + "\",\n" +
                    "  \"email\": \"" + email + "\",\n" +
                    "  \"firstname\": \"" + username + "\",\n" +
                    "  \"lastname\": \"" + company + "\"\n" +
                    "}";

            HttpPost post = new HttpPost(imsEndpoint);
            post.setHeader("Authorization", "Bearer " + accessToken);
            post.setHeader("x-api-key", "<your-api-key>");
            post.setHeader("x-gw-ims-org-id", "<your-org-id>");
            post.setHeader("Content-Type", "application/json");

            post.setEntity(new StringEntity(payload, "UTF-8"));

            // 3. Execute
            CloseableHttpResponse response = client.execute(post);
            int statusCode = response.getStatusLine().getStatusCode();
            String responseBody = EntityUtils.toString(response.getEntity());

            if (statusCode >= 200 && statusCode < 300) {
                // success
                return true;
            } else {
                // log failure details
                System.err.println("IMS User creation failed. Code=" + statusCode + " Response=" + responseBody);
                return false;
            }

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private String getIMSAccessToken() throws Exception {
        String tokenEndpoint = "https://ims-na1.adobelogin.com/ims/token";

        String clientId = "c7255c321071463f8a963cd8cb6e1081"; // API Key from Adobe Developer Console
        String clientSecret = "p8e-8nbJQEV22z4K-Lf_0aeyrZZUldNnTIDd"; // Client Secret from Adobe Developer Console
        String scopes = "openid,AdobeID,user_management_sdk"; // scope(s) required for your API

        try (CloseableHttpClient client = HttpClients.createDefault()) {

            HttpPost post = new HttpPost(tokenEndpoint);
            post.setHeader("Content-Type", "application/x-www-form-urlencoded");

            String body = "grant_type=client_credentials" +
                    "&client_id=" + clientId +
                    "&client_secret=" + clientSecret +
                    "&scope=" + scopes;

            post.setEntity(new StringEntity(body));

            CloseableHttpResponse response = client.execute(post);
            int statusCode = response.getStatusLine().getStatusCode();
            String responseBody = EntityUtils.toString(response.getEntity());

            if (statusCode >= 200 && statusCode < 300) {
                JSONObject json = new JSONObject(responseBody);
                return json.getString("access_token");
            } else {
                throw new RuntimeException("Failed to get IMS token. Code=" + statusCode + " Response=" + responseBody);
            }
        }
    }
}