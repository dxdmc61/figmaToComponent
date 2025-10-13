package com.figma.aem.core.services;

import org.osgi.service.metatype.annotations.AttributeDefinition;
import org.osgi.service.metatype.annotations.ObjectClassDefinition;

@ObjectClassDefinition(name = "MyProject IMS Configuration", description = "Credentials and endpoints for Adobe IMS / User Management APIs")
public @interface ImsConfig {

    @AttributeDefinition(name = "IMS Endpoint", description = "Base endpoint for Adobe User Management API (SCIM/UserSync)")
    String ims_endpoint() default "https://usermanagement.adobe.io/v2/usermanagement";

    @AttributeDefinition(name = "Organization ID", description = "Adobe Org ID (e.g., 12345@AdobeOrg)")
    String ims_org_id();

    @AttributeDefinition(name = "API Key (Client ID)")
    String ims_api_key();

    @AttributeDefinition(name = "Client Secret")
    String ims_client_secret();

    @AttributeDefinition(name = "Technical Account ID")
    String ims_technical_account_id();

    @AttributeDefinition(name = "Metascopes (comma-separated)", description = "e.g., ent_user_sdk")
    String ims_metascopes() default "ent_user_sdk";

    @AttributeDefinition(name = "Private Key (PEM)", description = "PEM contents or a secret reference")
    String ims_private_key_pem();

    @AttributeDefinition(name = "Use OAuth Server-to-Server (preferred in new integrations)")
    boolean ims_use_oauth_s2s() default true;

    @AttributeDefinition(name = "Token Endpoint", description = "IMS token endpoint, varies by auth flow")
    String ims_token_endpoint() default "https://ims-na1.adobelogin.com/ims/token/v3";
}
