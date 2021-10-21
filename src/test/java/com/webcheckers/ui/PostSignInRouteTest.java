package com.webcheckers.ui;
import com.webcheckers.app.PlayerLobby;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import spark.*;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.junit.Assert.assertEquals;

/**
 * @Authot Phil Ganem
 */
@Tag("UI-tier")
public class PostSignInRouteTest{

    private PostSignInRoute CuT;
    private PlayerLobby playerLobby;
    private TemplateEngine templateEngine;
    private Request request;
    private Response response;
    private Session session;
    private String name;

    @BeforeEach
    public void setup()
    {
        request = mock(Request.class);
        session = mock(Session.class);
        name = "Player";
        when(request.session()).thenReturn(session);
        response = mock(Response.class);
        templateEngine = mock(TemplateEngine.class);

        // simulate the player lobby
        playerLobby = new PlayerLobby();
        playerLobby.addPlayer(name);

        CuT = new PostSignInRoute(playerLobby, templateEngine);
    }


    @Test
    /**
     * Test the constructor for PostSignInRoute with null values
     */
    public void testConstructor_Null() {
        boolean isThrown = false;

        try {CuT = new PostSignInRoute(null, null);}
        catch (NullPointerException e) {isThrown = true;}
        
        assertTrue(isThrown);
    }

    
    @Test
    /**
     * Test the constructor for PostSignInRoute
     */
    public void testConstructor() {

        boolean isThrown = false;
        
        try {CuT = new PostSignInRoute(playerLobby, templateEngine);}
        catch (Error e) {isThrown = true;}

        assertFalse(isThrown);
    }

    @Test 
    /**
     * Tests handle to see if it takes player signin
     */
    public void testHandle(){
        when(session.attribute(PostSignInRoute.SESSION_ATTR)).thenReturn(name);

        final TemplateEngineTester testHelper = new TemplateEngineTester();
        when(templateEngine.render(any(ModelAndView.class))).thenAnswer(testHelper.makeAnswer());

        int expected = 1;
        int actual = playerLobby.size();

        assertEquals(expected, actual);
    }
}

