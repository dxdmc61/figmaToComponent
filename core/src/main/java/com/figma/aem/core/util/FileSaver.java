package com.figma.aem.core.util;

import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jcr.RepositoryException;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
import java.util.Arrays; // Added for string manipulation
import java.util.Locale;  // Added for string manipulation

@Component(service = FileSaver.class)
public class FileSaver {
    private static final Logger LOG = LoggerFactory.getLogger(FileSaver.class);
    // Constants for Maven structure prefixes
    private static final String UI_APPS_ROOT = "/ui.apps/src/main/content/jcr_root";
    private static final String CORE_JAVA_ROOT = "/core/src/main/java/com/figma/core/models/";
    private static final String JCR_APPS_PREFIX = "/apps/figma/";

    @Reference
    private ResourceResolverFactory resourceResolverFactory;

    // NOTE: The unused static arrays have been removed for code cleanliness.

    /**
     * Saves generated AEM files by mapping their JCR paths (from the AI response keys)
     * to the corresponding Maven filesystem paths.
     *
     * @param projectRoot The root directory of the AEM Maven project.
     * @param componentName The human-readable component name (e.g., "Apple Hero").
     * @param generatedFiles A map where keys are JCR paths or Java filenames, and values are file contents.
     * @throws IOException If file saving fails.
     */
    public void saveFiles(String projectRoot, String componentName, Map<String, String> generatedFiles)
            throws IOException {
        // Validate inputs
        if (projectRoot == null || projectRoot.isEmpty()) {
            throw new IllegalArgumentException("Project directory cannot be null or empty");
        }
        if (componentName == null || componentName.isEmpty()) {
            throw new IllegalArgumentException("Component name cannot be null or empty");
        }

        // Standardize component name to match the folder name used in JCR paths (e.g., "Apple Hero" -> "applehero")
        String slingFolderName = getSlingFolderName(componentName);

        // 1. Create component directory structure
        createDirectoryStructure(projectRoot, slingFolderName);

        // 2. Save all generated files
        String modelFileName = getModelName(componentName) + ".java"; // e.g., "AppleHeroModel.java"

        for (Map.Entry<String, String> entry : generatedFiles.entrySet()) {
            String inputKey = entry.getKey().trim();
            String content = entry.getValue();
            String targetPath;

            try {
                if (inputKey.endsWith(modelFileName)) {
                    // Case 1: Java Sling Model (uses simple filename as key)
                    // Maps "AppleHeroModel.java" to core/src/.../AppleHeroModel.java
                    targetPath = CORE_JAVA_ROOT + modelFileName;
                } else if (inputKey.startsWith(JCR_APPS_PREFIX)) {
                    // Case 2: JCR-based files (HTL, XML, ClientLibs)
                    // Maps /apps/figma/... to ui.apps/src/main/content/jcr_root/apps/figma/...
                    targetPath = UI_APPS_ROOT + inputKey;
                } else {
                    LOG.warn("Unknown file path format in AI response: {}", inputKey);
                    continue;
                }

                saveFile(projectRoot, targetPath, content);
            } catch (IOException e) {
                LOG.error("Failed to save {} for component {}", inputKey, componentName, e);
                throw e;
            }
        }

        LOG.info("Successfully saved all files for component: {}", componentName);
    }

    /**
     * Helper to derive the component folder name used in JCR paths (e.g., "Apple Hero" -> "applehero").
     */
    private String getSlingFolderName(String componentName) {
        return componentName.toLowerCase(Locale.ROOT).replaceAll("[\\s-]", "");
    }

    /**
     * Helper to derive the Java Model name (e.g., "Apple Hero" -> "AppleHeroModel").
     */
    private String getModelName(String componentName) {
        String[] parts = componentName.split("[\\s-]");
        StringBuilder sb = new StringBuilder();
        for (String part : parts) {
            if (!part.isEmpty()) {
                sb.append(part.substring(0, 1).toUpperCase(Locale.ROOT)).append(part.substring(1));
            }
        }
        return sb.toString() + "Model";
    }

    private void createDirectoryStructure(String projectRoot, String slingFolderName) throws IOException {
        // 1. UI.APPS Component directories
        String componentBasePath = UI_APPS_ROOT + JCR_APPS_PREFIX + "components/content/" + slingFolderName;
        createDirectory(projectRoot + componentBasePath);
        createDirectory(projectRoot + componentBasePath + "/_cq_dialog");

        // 2. UI.APPS ClientLib directories
        String clientlibBasePath = UI_APPS_ROOT + JCR_APPS_PREFIX + "clientlibs/" + slingFolderName;
        createDirectory(projectRoot + clientlibBasePath);
        createDirectory(projectRoot + clientlibBasePath + "/css");
        createDirectory(projectRoot + clientlibBasePath + "/js"); // Create js just in case

        // 3. CORE Model package
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

    private void saveFile(String projectRoot, String relativePath, String content) throws IOException {
        Path fullPath = Paths.get(projectRoot, relativePath);
        Files.createDirectories(fullPath.getParent());
        Files.write(fullPath, content.getBytes(StandardCharsets.UTF_8));
        LOG.debug("Saved file: {}", fullPath);
    }

    // Unused method kept for original context
    public void saveToRepository(ResourceResolver resolver, String componentPath, Map<String, String> generatedFiles)
            throws RepositoryException {
        // Implementation for saving directly to AEM repository would go here
    }
}