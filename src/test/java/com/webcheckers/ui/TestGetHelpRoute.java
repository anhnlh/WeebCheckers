package com.webcheckers.ui;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import spark.*;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * Test class for {@link GetHelpRoute}
 */
public class TestGetHelpRoute {
    /**
     * The component-under-test (CuT).
     */
    private GetHelpRoute CuT;

    /**
     * Attributes holding mock objects
     */
    private Request request;
    private Response response;
    private Session session;
    private TemplateEngine templateEngine;

    /**
     * Setup mock objects before each test
     */
    @BeforeEach
    public void setup() {
        request = mock(Request.class);
        session = mock(Session.class);
        when(request.session()).thenReturn(session);
        response = mock(Response.class);
        templateEngine = mock(TemplateEngine.class);
        
        // Create a unique CuT for each test
        CuT = new GetHelpRoute(templateEngine);
    }

    /**
     * Tests {@link GetHelpRoute#GetHelpRoute(TemplateEngine)}
     */
    @Test
    public void constructorTest() {
        new GetHelpRoute(templateEngine);
        assertNotNull(templateEngine);
    }

    /**
     * Tests {@link GetHelpRoute#handle(Request, Response)}
     */
    @Test
    public void handleTest() {
        // When the player isn't signed in
        final TemplateEngineTester testHelper = new TemplateEngineTester();
        when(templateEngine.render(any(ModelAndView.class))).thenAnswer(testHelper.makeAnswer());

        CuT.handle(request, response);

        testHelper.assertViewModelExists();
        testHelper.assertViewModelIsaMap();

        testHelper.assertViewModelAttribute(GetHelpRoute.TITLE_ATTR, "Help");

        assertEquals(GetHelpRoute.VIEW_NAME, "help.ftl");
    }
}