package com.webcheckers.ui;

import com.webcheckers.app.Game;
import com.webcheckers.app.PlayerLobby;
import com.webcheckers.model.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.platform.commons.annotation.Testable;
import static org.junit.jupiter.api.Assertions.*;
import spark.*;

import java.util.HashMap;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@Tag("UI-tier")
@Testable
public class TestGetGameRoute {
    /**
     * The component-under-test (CuT).
     */
    private GetGameRoute CuT;

    private Request request;
    private Session session;
    private Response response;
    private TemplateEngine engine;
    private HashMap<String, Game> gameMap;
    private PlayerLobby playerLobby;

    /**
     * Setup new mock objects for each test.
     */
    @BeforeEach
    public void setup() {
        request = mock(Request.class);
        session = mock(Session.class);
        when(request.session()).thenReturn(session);
        response = mock(Response.class);
        engine = mock(TemplateEngine.class);

        // simulate the player lobby
        playerLobby = new PlayerLobby();
        playerLobby.addPlayer("Player_1");
        playerLobby.addPlayer("Player_2");

        // simulate a game created
        Player p1 = playerLobby.getPlayer("Player_1");
        Player p2 = playerLobby.getPlayer("Player_2");
        Game game = new Game(p1, p2);
        gameMap = new HashMap<>();
        gameMap.put(String.valueOf(game.getID()), game);


        // create a unique CuT for each test
        CuT = new GetGameRoute(gameMap, playerLobby, engine);
    }

    @Test
    public void newGame() {
        final TemplateEngineTester testHelper = new TemplateEngineTester();
        when(engine.render(any(ModelAndView.class))).thenAnswer(testHelper.makeAnswer());

        Player player = session.attribute(GetHomeRoute.CURRENT_USER);
        assertNotNull(player);

        CuT.handle(request, response);

        testHelper.assertViewModelExists();
        testHelper.assertViewModelIsaMap();


        testHelper.assertViewModelAttribute(GetHomeRoute.CURRENT_USER, player);
        testHelper.assertViewModelAttribute(GetGameRoute.TITLE_ATTR, "Welcome!");

        Game game = ((List<Game>) gameMap.values()).get(0);
        assertNull(request.queryParams(GetGameRoute.GAME_ID_PARAM));
    }
}
