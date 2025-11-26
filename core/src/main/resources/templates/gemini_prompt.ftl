You are generating an Adobe Experience Manager (AEM) component.

COMPONENT TITLE: ${componentTitle}
DESCRIPTION: ${description}

The component contains the following fields:
${fields!""}

Generate the following AEM files (ALL must be returned in a single JSON object):

---------------------------------------------------------------------
1. COMPONENT DIALOG (`_cq_dialog/.content.xml`)
   KEY: ${componentPath}/_cq_dialog/.content.xml

   You MUST generate a valid Granite UI Coral 3 dialog.

   STRICT RULES — DO NOT BREAK THESE:
   - Never generate: <columns>, <fixedcolumns>, <column>, <container>, 
     <responsivegrid>, <layout>, <itemsWrapper>, or any decorative wrapper node.
   - Never use Coral 2 components.
   - All fields MUST be direct children of:
       /content/items/tabs/items/properties/items
   - No nested structures.
   - No unnecessary wrapper nodes.
   - No margin="true" or UI styling attributes.

   REQUIRED STRUCTURE (DO NOT CHANGE):
   jcr:root
     → content (granite/ui/components/coral/foundation/form)
         → items
             → tabs (granite/ui/components/coral/foundation/tabs)
                 → items
                     → properties (granite/ui/components/coral/foundation/form)
                         → items
                             → FIELDS GO HERE ONLY (flat list)

---------------------------------------------------------------------

2. GLOBAL HTL (${componentPath}.html)
   KEY: ${componentPath}.html

   REQUIREMENTS:
   - Must use clean HTL only.
   - Escape variables properly.
   - Reference Sling Model getters or properties.
   - Include the component clientlib in HTL.

   Use **escaped literal HTL** for clientlibs:

   <sly data-sly-use.clientlib="granite.ui.clientlibs.ClientLibrary" />
   <sly data-sly-call="${"$"}{clientlib.css @ categories='${clientLibCategory}'}" />
   <sly data-sly-call="${"$"}{clientlib.js @ categories='${clientLibCategory}'}" />

   Then output simple semantic HTML, e.g.:

   <div class="component">
       <h2>${"$"}{properties.title}</h2>
       <p>${"$"}{properties.description}</p>
   </div>

---------------------------------------------------------------------

3. COMPONENT DEFINITION FILE (${componentPath}/.content.xml)
   KEY: ${componentPath}/.content.xml

---------------------------------------------------------------------

4. README.md
   KEY: ${componentPath}/README.md

---------------------------------------------------------------------

5. JAVA SLING MODEL
   KEY: ${javaModelPath}

   RULES:
   - Valid OSGi R7 Sling Model:
       @Model(
         adaptables = Resource.class,
         defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL
       )
   - Use @Inject for each dialog field
   - Create public getters only

---------------------------------------------------------------------

6. CLIENTLIBS
   KEY: ${componentPath}/clientlibs/${clientLibCategory}/.content.xml
   KEY: ${componentPath}/clientlibs/${clientLibCategory}/css.txt
   KEY: ${componentPath}/clientlibs/${clientLibCategory}/js.txt
   KEY: ${componentPath}/clientlibs/${clientLibCategory}/css/style.css
   KEY: ${componentPath}/clientlibs/${clientLibCategory}/js/script.js

---------------------------------------------------------------------

OUTPUT FORMAT (MUST BE FOLLOWED EXACTLY)

Return a JSON object like:

{
  "${componentPath}/_cq_dialog/.content.xml": "<dialog xml>",
  "${componentPath}.html": "<htl>",
  "${componentPath}/.content.xml": "<component xml>",
  "${componentPath}/README.md": "<readme>",
  "${javaModelPath}": "<java>",
  "${componentPath}/clientlibs/${clientLibCategory}/.content.xml": "<clientlib xml>",
  "${componentPath}/clientlibs/${clientLibCategory}/css.txt": "css/style.css",
  "${componentPath}/clientlibs/${clientLibCategory}/js.txt": "js/script.js",
  "${componentPath}/clientlibs/${clientLibCategory}/css/style.css": "<css>",
  "${componentPath}/clientlibs/${clientLibCategory}/js/script.js": "<js>"
}

DO NOT include markdown fences.
DO NOT include explanations.
DO NOT include commentary.

ONLY return the JSON object.
