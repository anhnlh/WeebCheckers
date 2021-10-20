package com.webcheckers.ui;
import com.webcheckers.app.PlayerLobby;
import com.webcheckers.model.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import spark.*;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * @Authot Phil Ganem
 */
@Tag("UI-tier")
public class PostSignInRouteTest{

    private PostSignInRoute CuT;
    private PlayerLobby lobby;
    private Request request;
    private Session session;
    private Player asker;
    private TemplateEngine engine;
    private Response response;

    @BeforeEach
    public void setup()
    {
        request = mock(Request.class);
        session = mock(Session.class);
        when(request.session()).thenReturn(session);
        when(session.id()).thenReturn("session1");
        response = mock(Response.class);
        engine = mock(TemplateEngine.class);

        lobby = new PlayerLobby();

        asker = new Player("player1");
        lobby.addPlayer(asker.getName());
        CuT = new PostSignInRoute(lobby, engine);
    }


    @Test
    /**
     * Test the constructor for PostSignInRoute with null values
     */
    public void testConstructor_Null() {
        boolean isThrown = false;

        try {
            new PostSignInRoute(null, null);
        }
        catch (NullPointerException e) {
            isThrown = true;
        }

        assertTrue(isThrown);
    }

    
    @Test
    /**
     * Test the constructor for PostSignInRoute
     */
    public void testConstructor() {

        boolean isThrown = false;

        try {
            CuT = new PostSignInRoute(lobby, engine);
        }
        catch (Error e) {
            isThrown = true;
        }

        assertFalse(isThrown);
    }
}

