package com.webcheckers.ui;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.*;

import java.util.HashMap;

import com.webcheckers.app.Game;
import com.webcheckers.app.PlayerLobby;
import com.webcheckers.model.Player;

import org.junit.jupiter.api.*;
import org.junit.platform.commons.annotation.Testable;

import spark.*;

@Tag("UI-tier")
@Testable
public class TestGetHomeRoute {
    
    private GetHomeRoute CuT;

    private PlayerLobby playerLobby;
    private HashMap<String, Game> gameMap;
    private Player player;    

    private Request request;
    private Response response;
    private Session session;
    private TemplateEngine templateEngine;

    private String playerName;

    @BeforeEach
    public void setup() {
        request = mock(Request.class);
        session = mock(Session.class);
        when(request.session()).thenReturn(session);
        response = mock(Response.class);
        templateEngine = mock(TemplateEngine.class);
        
        playerName = "player1";
        player = new Player(playerName);
        playerLobby = new PlayerLobby();

        gameMap = new HashMap<>();

        CuT = new GetHomeRoute(gameMap, playerLobby, templateEngine);
    }

    @Test
    public void constructorTest() {
        new GetHomeRoute(gameMap, playerLobby, templateEngine);
        assertNotNull(gameMap);
        assertNotNull(playerLobby);
        assertNotNull(templateEngine);
    }

    @Test
    public void handleTest() {
        // When the player isn't signed in
        final TemplateEngineTester testHelper = new TemplateEngineTester();
        when(templateEngine.render(any(ModelAndView.class))).thenAnswer(testHelper.makeAnswer());

        CuT.handle(request, response);

        testHelper.assertViewModelExists();
        testHelper.assertViewModelIsaMap();

        testHelper.assertViewModelAttribute(GetHomeRoute.TITLE, "Welcome!");
        testHelper.assertViewModelAttribute(GetHomeRoute.ACTIVE_PLAYER_COUNT, playerLobby.activePlayersMessage());

        // When the player is signed in
        when(session.attribute(GetHomeRoute.CURRENT_USER)).thenReturn(player);

        CuT.handle(request, response);

        testHelper.assertViewModelExists();
        testHelper.assertViewModelIsaMap();

        testHelper.assertViewModelAttribute(GetHomeRoute.TITLE, "Welcome!");
        testHelper.assertViewModelAttribute(GetHomeRoute.ACTIVE_PLAYERS, playerLobby.getOtherActivePlayers(player));

    }
}
