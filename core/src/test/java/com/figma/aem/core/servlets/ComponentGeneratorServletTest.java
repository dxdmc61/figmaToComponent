package com.figma.aem.core.servlets;

import com.figma.aem.core.services.AIGenerator;
import com.figma.aem.core.services.AIGeneratorFactory;
import com.figma.aem.core.util.FileSaver;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import javax.servlet.http.Part;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import static org.mockito.Mockito.*;

public class ComponentGeneratorServletTest {
    private ComponentGeneratorServlet servlet;
    private FileSaver fileSaver;
    private SlingHttpServletRequest request;
    private SlingHttpServletResponse response;
    private AIGenerator generator;
    private Part figmaFile;
    private PrintWriter writer;

    @BeforeEach
    void setUp() throws Exception {
        fileSaver = mock(FileSaver.class);
        request = mock(SlingHttpServletRequest.class);
        response = mock(SlingHttpServletResponse.class);
        generator = mock(AIGenerator.class);
        figmaFile = mock(Part.class);
        writer = mock(PrintWriter.class);

        servlet = new ComponentGeneratorServlet();
        // Inject the mock FileSaver
        java.lang.reflect.Field field = ComponentGeneratorServlet.class.getDeclaredField("fileSaver");
        field.setAccessible(true);
        field.set(servlet, fileSaver);
    }


    @Test
    void testDoPost_Exception() throws Exception {
        when(request.getParameter(anyString())).thenThrow(new RuntimeException("Test error"));
        when(response.getWriter()).thenReturn(writer);

        servlet.doPost(request, response);

        verify(response).setStatus(eq(500));
        verify(writer).write(contains("Error: Test error"));
    }
}