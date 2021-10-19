package com.webcheckers.ui;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

import com.webcheckers.app.PlayerLobby;

import org.junit.jupiter.api.*;
import org.junit.platform.commons.annotation.Testable;

import spark.*;

@Tag("UI-tier")
@Testable
public class TestPostSignOutRoute {
    
    private PostSignOutRoute CuT;

    private PlayerLobby playerLobby;
    private TemplateEngine templateEngine;

    private Request request;
    private Response response;

    private Session session;
    private String name;

    @BeforeEach
    public void setup() {
        request = mock(Request.class);
        session = mock(Session.class);
        name = "Player";
        when(request.session()).thenReturn(session);
        response = mock(Response.class);
        templateEngine = mock(TemplateEngine.class);

        playerLobby = new PlayerLobby();
        playerLobby.addPlayer(name);

        CuT = new PostSignOutRoute(playerLobby, templateEngine);
    }

    @Test
    public void constructorTest() {
        new PostSignOutRoute(playerLobby, templateEngine);
        assertNotNull(playerLobby);
        assertNotNull(templateEngine);
    }

    @Test
    public void handleTest() {
        when(session.attribute(PostSignInRoute.SESSION_ATTR)).thenReturn(name);

        final TemplateEngineTester testHelper = new TemplateEngineTester();
        when(templateEngine.render(any(ModelAndView.class))).thenAnswer(testHelper.makeAnswer());

        CuT.handle(request, response);

        testHelper.assertViewModelExists();
        testHelper.assertViewModelIsaMap();

        testHelper.assertViewModelAttribute(GetHomeRoute.TITLE, "Welcome!");
        testHelper.assertViewModelAttribute(GetHomeRoute.ACTIVE_PLAYER_COUNT, playerLobby.activePlayersMessage());
    }
}
