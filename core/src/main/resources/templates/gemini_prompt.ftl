You are generating an Adobe Experience Manager (AEM) component.

COMPONENT TITLE: ${componentTitle}
DESCRIPTION: ${description}

The component contains the following fields:
${fields!""}

=====================================================================
⚠️ STRICT AEM DIALOG RULES — YOU MUST FOLLOW THESE EXACTLY
=====================================================================

### GENERAL RULES
- Use ONLY Granite UI Coral 3 components.
- NEVER use Coral 2.
- NEVER generate: <columns>, <fixedcolumns>, <column>, <container>,
  <responsivegrid>, <layout>, <itemsWrapper>, or any decorative wrapper.
- NEVER add margin="true", style attributes, or extra wrappers.
- ALL dialog fields MUST be placed under:
  /content/items/tabs/items/properties/items
- No nested wrappers beyond what is explicitly allowed.
- Output must be valid XML.

=====================================================================
### ⚠️ CRITICAL: COMPOSITE MULTIFIELD RULES
=====================================================================

When generating a **composite multifield**, you MUST follow this exact structure:

❌ The <multifield> MUST *NOT* have a "name" property.

✔ The <field> node INSIDE the multifield MUST contain:
      name="./yourFieldName"

✔ Each internal field MUST use relative names:
      name="./childFieldName"

✔ Example (this is the ONLY valid structure):

<myMultifield
    sling:resourceType="granite/ui/components/coral/foundation/form/multifield"
    composite="true"
    fieldLabel="Items">

    <field
        sling:resourceType="granite/ui/components/coral/foundation/container"
        name="./items">

        <items>
            <title
                sling:resourceType="granite/ui/components/coral/foundation/form/textfield"
                name="./title" />
            <description
                sling:resourceType="granite/ui/components/coral/foundation/form/textarea"
                name="./description" />
        </items>
    </field>
</myMultifield>

✔ This MUST produce JCR output:
items
  ├── item0
  │     ├── title
  │     └── description
  ├── item1
        ├── title
        └── description

If the user requests ANY field that should be a multifield (array, list, repeatable items),
you MUST generate the dialog following this pattern exactly.

=====================================================================
### REQUIRED DIALOG STRUCTURE (DO NOT MODIFY)

jcr:root
  → content (granite/ui/components/coral/foundation/form)
      → items
          → tabs (granite/ui/components/coral/foundation/tabs)
              → items
                  → properties (granite/ui/components/coral/foundation/form)
                      → items
                          → FIELDS GO HERE ONLY

=====================================================================

⚠️ JUNIT TEST GENERATION RULES (MANDATORY)

=====================================================================

You MUST generate a JUnit 5 test class for the Sling Model.

Rules:

Use JUnit 5

Use io.wcm.testing.mock.aem.junit5

Package:
com.figma.aem.core.models

Class name:
<SlingModelName>Test

Test MUST validate:

Model adapts correctly from Resource

All dialog properties map correctly

Composite multifield items are read as a list of objects

Optional fields do not cause NullPointerException

Use AEM Mocks only — DO NOT mock Sling or JCR manually

=====================================================================

Generate these AEM files (ALL returned in a JSON object):

1. Dialog XML  
2. HTL  
3. Component .content.xml  
4. README.md  
5. Sling Model (OSGi R7)  
6. Clientlibs (xml, txt, css, js)
7. JUnit 5 Test (AEM Mocks)

=====================================================================
### OUTPUT FORMAT (STRICT)
Return ONLY:

{
  "${componentPath}/_cq_dialog/.content.xml": "<dialog xml>",
  "${componentPath}.html": "<htl>",
  "${componentPath}/.content.xml": "<component xml>",
  "${componentPath}/README.md": "<readme>",
  "${javaModelPath}": "<java>",
  "${junitTestPath}": "<junit>",
  "${clientLibPath}/.content.xml": "<clientlib xml>",
  "${clientLibPath}/css.txt": "css/style.css",
  "${clientLibPath}/js.txt": "js/script.js",
  "${clientLibPath}/css/style.css": "<css>",
  "${clientLibPath}/js/script.js": "<js>"
}

DO NOT add markdown.
DO NOT add explanations.
DO NOT wrap in code fences.
Return ONLY the JSON object.
