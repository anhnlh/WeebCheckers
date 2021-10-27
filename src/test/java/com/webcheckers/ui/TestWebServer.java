package com.webcheckers.ui;

import com.google.gson.Gson;
import com.webcheckers.app.Game;
import com.webcheckers.app.PlayerLobby;
import com.webcheckers.model.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.platform.commons.annotation.Testable;
import spark.Request;
import spark.Response;
import spark.Session;
import spark.TemplateEngine;
import spark.template.freemarker.FreeMarkerEngine;

import java.util.HashMap;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@Tag("UI-tier")
@Testable
public class TestWebServer {
    private WebServer CuT;

    private TemplateEngine templateEngine;
    private PlayerLobby playerLobby;
    private Gson gson;

    /**
     * Setup new mock objects for each test.
     */
    @BeforeEach
    public void setup() {
        templateEngine = new FreeMarkerEngine();
        playerLobby = new PlayerLobby();
        gson = new Gson();

        // create a unique CuT for each test
        CuT = new WebServer(templateEngine, gson, playerLobby);
    }

    /**
     * Tests {@link WebServer#initialize()}
     */
    @Test
    public void testInitialize() {
        CuT.initialize();
    }
}
