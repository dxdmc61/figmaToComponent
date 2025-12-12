package com.figma.aem.core.util;

import org.apache.sling.api.resource.ResourceResolverFactory;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Locale;
import java.util.Map;

@Component(service = FileSaver.class)
public class FileSaver {

    private static final Logger LOG = LoggerFactory.getLogger(FileSaver.class);

    // Maven project folder roots
    private static final String UI_APPS_ROOT = "/ui.apps/src/main/content/jcr_root";
    private static final String CORE_JAVA_ROOT = "/core/src/main/java/com/figma/aem/core/models/";
    private static final String JCR_APPS_PREFIX = "/apps/figma/";

    @Reference
    private ResourceResolverFactory resourceResolverFactory;

    // -----------------------------------------------------
    // MAIN ENTRY
    // -----------------------------------------------------
    public void saveFiles(String projectRoot, String componentName, Map<String, String> generatedFiles)
            throws IOException {

        if (projectRoot == null || projectRoot.isEmpty()) {
            throw new IllegalArgumentException("Project directory cannot be null or empty");
        }
        if (componentName == null || componentName.isEmpty()) {
            throw new IllegalArgumentException("Component name cannot be null or empty");
        }

        String slingFolderName = getSlingFolderName(componentName);
        createDirectoryStructure(projectRoot, slingFolderName);

        String modelFileName = getModelName(componentName) + ".java";

        for (Map.Entry<String, String> entry : generatedFiles.entrySet()) {
            String key = normalizeKey(entry.getKey());
            String content = entry.getValue();

            String resolvedPath = resolveOutputPath(key, slingFolderName, modelFileName);

            if (resolvedPath == null) {
                LOG.warn("Skipping unknown file format: {}", key);
                continue;
            }

            saveFile(projectRoot, resolvedPath, content);
        }

        LOG.info("Successfully saved component: {}", componentName);
    }

    // -----------------------------------------------------
    // PATH RESOLUTION
    // -----------------------------------------------------
    private String resolveOutputPath(String key, String folder, String modelFileName) {

        // Java Sling Model
        if (key.endsWith(modelFileName)) {
            return CORE_JAVA_ROOT + modelFileName;
        }

        // HTL (.html)
        if (key.endsWith(".html")) {
            return UI_APPS_ROOT + JCR_APPS_PREFIX +
                    "components/content/" + folder + "/" + folder + ".html";
        }

        // Dialogs (content.xml or .xml inside _cq_dialog)
        if (key.contains("_cq_dialog")) {
            return UI_APPS_ROOT + normalizeDialogPath(key, folder);
        }

        // Clientlibs (css, js, .content.xml)
        if (key.contains("clientlibs")) {
            return UI_APPS_ROOT + normalizeClientlibPath(key, folder);
        }

        // Generic AEM JCR paths (/apps/figma/...)
        if (key.startsWith(JCR_APPS_PREFIX)) {
            return UI_APPS_ROOT + key;
        }

        return null;
    }

    // -----------------------------------------------------
    // NORMALIZERS
    // -----------------------------------------------------
    private String normalizeKey(String key) {
        if (key == null)
            return "";
        return key.trim().replace("\\", "/");
    }

    private String normalizeDialogPath(String key, String folder) {
        if (!key.startsWith("/apps/figma/")) {
            return "/apps/figma/components/content/" + folder + "/_cq_dialog/.content.xml";
        }
        return key;
    }

    private String normalizeClientlibPath(String key, String folder) {
        if (!key.contains(folder)) {
            // Rewrite incorrect paths to proper structure
            if (key.endsWith(".css")) {
                return "/apps/figma/clientlibs/" + folder + "/css/styles.css";
            }
            if (key.endsWith(".js")) {
                return "/apps/figma/clientlibs/" + folder + "/js/scripts.js";
            }
            return "/apps/figma/clientlibs/" + folder + "/.content.xml";
        }
        return key;
    }

    // -----------------------------------------------------
    // HELPERS
    // -----------------------------------------------------
    private String getSlingFolderName(String componentName) {
        return componentName.toLowerCase(Locale.ROOT)
                .replaceAll("[\\s-]", "");
    }

    private String getModelName(String componentName) {
        String[] parts = componentName.split("[\\s-]");
        StringBuilder sb = new StringBuilder();
        for (String p : parts) {
            sb.append(p.substring(0, 1).toUpperCase(Locale.ROOT))
                    .append(p.substring(1));
        }
        return sb.toString() + "Model";
    }

    private void createDirectoryStructure(String projectRoot, String folder) throws IOException {

        // Component folder
        createDirectory(projectRoot + UI_APPS_ROOT +
                JCR_APPS_PREFIX + "components/content/" + folder);

        // Dialog
        createDirectory(projectRoot + UI_APPS_ROOT +
                JCR_APPS_PREFIX + "components/content/" + folder + "/_cq_dialog");

        // Clientlibs
        createDirectory(projectRoot + UI_APPS_ROOT +
                JCR_APPS_PREFIX + "clientlibs/" + folder + "/css");

        createDirectory(projectRoot + UI_APPS_ROOT +
                JCR_APPS_PREFIX + "clientlibs/" + folder + "/js");

        // Sling Model package
        createDirectory(projectRoot + CORE_JAVA_ROOT);
    }

    private void createDirectory(String path) throws IOException {
        File dir = new File(path);
        if (!dir.exists()) {
            if (!dir.mkdirs()) {
                throw new IOException("Failed to create directory: " + path);
            }
            LOG.debug("Created directory: {}", path);
        }
    }

    private void saveFile(String projectRoot, String relativePath, String content)
            throws IOException {

        Path fullPath = Paths.get(projectRoot, relativePath);

        Files.createDirectories(fullPath.getParent());
        Files.write(fullPath, content.getBytes(StandardCharsets.UTF_8));

        LOG.info("Saved: {}", fullPath);
    }
}
