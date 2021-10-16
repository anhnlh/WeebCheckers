package com.webcheckers.ui;

import java.util.*;
import java.util.logging.*;

import com.webcheckers.app.Game;
import spark.*;

import com.webcheckers.app.PlayerLobby;
import com.webcheckers.model.Player;
import com.webcheckers.util.Message;

import static spark.Spark.halt;

/**
 * The UI Controller to GET the Home page.
 *
 * @author Anh Nguyen
 */
public class GetHomeRoute implements Route {
    private static final Logger LOG = Logger.getLogger(GetHomeRoute.class.getName());

    // freemarker file
    public static final String VIEW_NAME = "home.ftl";

    // freemarker variables
    public static final String TITLE = "title";
    public static final String CURRENT_USER = "currentUser";
    public static final String MESSAGE = "message";
    public static final String ACTIVE_PLAYERS = "activePlayers";
    public static final String ACTIVE_PLAYER_COUNT = "activePlayerCount";
    public static final Message OPPONENT_IN_GAME = Message.error("Opponent is in game. Try another player.");


    // message
    private static final Message WELCOME_MSG = Message.info("Welcome to the world of online Checkers.");

    // parameter initalizations
    private TemplateEngine templateEngine;
    private PlayerLobby playerLobby;
    private HashMap<String, Game> gameMap;

    /**
     * Create the Spark Route (UI controller) to handle all {@code GET /} HTTP requests.
     *
     * @param templateEngine the HTML template rendering engine
     */
    public GetHomeRoute(HashMap<String, Game> gameMap, PlayerLobby playerLobby, final TemplateEngine templateEngine) {
        Objects.requireNonNull(playerLobby, "playerLobby must not be null");
        Objects.requireNonNull(templateEngine, "templateEngine is required");

        this.gameMap = gameMap;
        this.playerLobby = playerLobby;
        this.templateEngine = templateEngine;

        LOG.config("GetHomeRoute is initialized.");
    }

    /**
     * Render the WebCheckers Home page.
     *
     * @param request  the HTTP request
     * @param response the HTTP response
     * @return the rendered HTML for the Home page
     */
    @Override
    public Object handle(Request request, Response response) {

        LOG.finer("GetHomeRoute is invoked.");

        // retrieve session
        final Session httpSession = request.session();
        Player player = httpSession.attribute(CURRENT_USER);


        // start building the View-Model
        final Map<String, Object> vm = new HashMap<>();

        Collection<Player> activePlayers = playerLobby.getOtherActivePlayers(player);
        String activePlayersCount = playerLobby.activePlayersMessage();

        // display welcome title
        vm.put(TITLE, "Welcome!");

        if (player != null) {
            if (player.isPlaying()) {
                int gameID = 0;
                for (Game game : gameMap.values()) {
                    if (player.equals(game.getWhitePlayer())) {
                        gameID = game.getID();
                        break;
                    }
                }
                response.redirect(WebServer.GAME_URL + "?gameID=" + gameID);
                halt();
                return null;
            }
        }

        // store current user
        vm.put(CURRENT_USER, player);

        // display a user message in the Home page
        vm.put(MESSAGE, WELCOME_MSG);

        if (httpSession.attribute(GetGameRoute.ERROR_ATTR) != null) {
            vm.put(GetGameRoute.ERROR_ATTR, OPPONENT_IN_GAME);
        }

        // displays other active players
        vm.put(ACTIVE_PLAYERS, activePlayers);

        // displays number of players
        vm.put(ACTIVE_PLAYER_COUNT, activePlayersCount);

        // render the View
        return templateEngine.render(new ModelAndView(vm, VIEW_NAME));
    }
}
