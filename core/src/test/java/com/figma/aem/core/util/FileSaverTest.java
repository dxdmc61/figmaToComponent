package com.figma.aem.core.util;

import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;

import javax.jcr.RepositoryException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class FileSaverTest {
    @Mock
    private ResourceResolverFactory resourceResolverFactory;

    @Mock
    private Logger logger;

    @InjectMocks
    private FileSaver fileSaver;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        fileSaver = new FileSaver();
    }

    @Test
    void testSaveFiles_createsFilesAndDirectories(@TempDir Path tempDir) throws IOException {
        String projectRoot = tempDir.toString();
        String componentName = "testComponent";
        Map<String, String> generatedFiles = new HashMap<>();
        generatedFiles.put("testComponent.html", "<div>Test HTML</div>");
        generatedFiles.put("_cq_dialog/.content.xml", "<jcr:root/>");
        generatedFiles.put("_cq_editConfig.xml", "<editConfig/>");
        generatedFiles.put(".content.xml", "<component/>");
        generatedFiles.put("testComponent.css", ".test {}\n");
        generatedFiles.put("testComponent.js", "console.log('test');");
        generatedFiles.put("testComponent.java", "public class TestComponent {}");
        generatedFiles.put("test.txt", "test");

        fileSaver.saveFiles(projectRoot, componentName, generatedFiles);

        // Check that files exist
        assertTrue(Files.exists(tempDir.resolve("ui.apps/src/main/content/jcr_root/apps/figma/components/testComponent/testComponent.html")));
        assertTrue(Files.exists(tempDir.resolve("ui.apps/src/main/content/jcr_root/apps/figma/components/testComponent/_cq_dialog/.content.xml")));
        assertTrue(Files.exists(tempDir.resolve("ui.apps/src/main/content/jcr_root/apps/figma/components/testComponent/_cq_editConfig.xml")));
        assertTrue(Files.exists(tempDir.resolve("ui.apps/src/main/content/jcr_root/apps/figma/components/testComponent/.content.xml")));
        assertTrue(Files.exists(tempDir.resolve("ui.apps/src/main/content/jcr_root/apps/figma/clientlibs/testComponent/testComponent.css")));
        assertTrue(Files.exists(tempDir.resolve("ui.apps/src/main/content/jcr_root/apps/figma/clientlibs/testComponent/testComponent.js")));
        assertTrue(Files.exists(tempDir.resolve("core/src/main/java/com/figma/aem/core/models/testComponent.java")));
        assertTrue(Files.exists(tempDir.resolve("ui.apps/src/main/content/jcr_root/apps/figma/clientlibs/testComponent/test.txt")));
    }

    @Test
    void testSaveFiles_invalidProjectRoot() {
        Map<String, String> generatedFiles = new HashMap<>();
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                fileSaver.saveFiles("", "comp", generatedFiles));
        assertEquals("Project directory cannot be null or empty", exception.getMessage());
    }

    @Test
    void testSaveFiles_invalidComponentName() {
        Map<String, String> generatedFiles = new HashMap<>();
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                fileSaver.saveFiles("/tmp", "", generatedFiles));
        assertEquals("Component name cannot be null or empty", exception.getMessage());
    }

    @Test
    void testCapitalizeFirstLetter() {
        assertEquals("Test", invokeCapitalizeFirstLetter("test"));
        assertEquals("A", invokeCapitalizeFirstLetter("a"));
        assertEquals("", invokeCapitalizeFirstLetter(""));
        assertNull(invokeCapitalizeFirstLetter(null));
    }

    private String invokeCapitalizeFirstLetter(String str) {
        try {
            java.lang.reflect.Method method = FileSaver.class.getDeclaredMethod("capitalizeFirstLetter", String.class);
            method.setAccessible(true);
            return (String) method.invoke(fileSaver, str);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    @Test
    void testSaveToRepository_unimplemented() {
        ResourceResolver resolver = mock(ResourceResolver.class);
        Map<String, String> files = new HashMap<>();
        assertDoesNotThrow(() -> fileSaver.saveToRepository(resolver, "/some/path", files));
    }
}
