package com.webcheckers.ui;

import java.util.*;
import java.util.logging.*;

import spark.*;

import com.webcheckers.app.PlayerLobby;
import com.webcheckers.model.Player;
import com.webcheckers.util.Message;

/**
 * The UI Controller to GET the Home page.
 *
 * @author <a href='mailto:bdbvse@rit.edu'>Bryan Basham</a>
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

  // message
  private static final Message WELCOME_MSG = Message.info("Welcome to the world of online Checkers.");
  
  // parameter initalizations
  private TemplateEngine templateEngine;
  private PlayerLobby playerLobby;

  /**
   * Create the Spark Route (UI controller) to handle all {@code GET /} HTTP requests.
   *
   * @param templateEngine
   *   the HTML template rendering engine
   */
  public GetHomeRoute(PlayerLobby playerLobby, final TemplateEngine templateEngine) {
    Objects.requireNonNull(playerLobby, "playerLobby must not be null");
    Objects.requireNonNull(templateEngine, "templateEngine is required");

    this.playerLobby = playerLobby;
    this.templateEngine = templateEngine;

    LOG.config("GetHomeRoute is initialized.");
  }

  /**
   * Render the WebCheckers Home page.
   *
   * @param request
   *   the HTTP request
   * @param response
   *   the HTTP response
   *
   * @return
   *   the rendered HTML for the Home page
   */
  @Override
  public Object handle(Request request, Response response) {

    LOG.finer("GetHomeRoute is invoked.");

    // retrieve session
    final Session httpSession = request.session();
    Player currentUser = httpSession.attribute("currentUser");

    
    // start building the View-Model
    final Map<String, Object> vm = new HashMap<>();

    Collection<Player> activePlayers = playerLobby.getOtherActivePlayers(currentUser);
    int activePlayersCount = playerLobby.size();

    // display welcome title
    vm.put(TITLE, "Welcome!");

    // store current user
    vm.put(CURRENT_USER, currentUser);

    // display a user message in the Home page
    vm.put(MESSAGE, WELCOME_MSG);

    // displays other active players
    vm.put(ACTIVE_PLAYERS, activePlayers);

    // displays nunmber of players
    vm.put(ACTIVE_PLAYER_COUNT, activePlayersCount);

    // render the View
    return templateEngine.render(new ModelAndView(vm , "home.ftl"));
  }
}
