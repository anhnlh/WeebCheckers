package com.webcheckers.ui;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Logger;

import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.Route;
import spark.TemplateEngine;

import com.webcheckers.app.PlayerLobby;
import com.webcheckers.util.Message;

/**
 * The UI Controller to GET the Home page.
 *
 * @author <a href='mailto:bdbvse@rit.edu'>Bryan Basham</a>
 */
public class GetHomeRoute implements Route {
  private static final Logger LOG = Logger.getLogger(GetHomeRoute.class.getName());
  private static final Message WELCOME_MSG = Message.info("Welcome to the world of online Checkers.");
  private TemplateEngine templateEngine;
  private PlayerLobby playerLobby;

  public static final String SIGNED_IN = "signedIn";
  public static final String PLAYER_NAME = "playerName";
  public static final String ACTIVE_PLAYERS = "activePlayers";
  public static final String ACTIVE_PLAYER_COUNT = "activePlayerCount";
  public static final String MESSAGE = "message";
  public static final String REPLAY_GAME_LIST = "replayGameList";
  /**
   * Create the Spark Route (UI controller) to handle all {@code GET /} HTTP requests.
   *
   * @param templateEngine
   *   the HTML template rendering engine
   */
  public GetHomeRoute(PlayerLobby playerLobby, final TemplateEngine templateEngine) {
    Objects.requireNonNull(playerLobby, "playerLobby must not be null");
    this.templateEngine = Objects.requireNonNull(templateEngine, "templateEngine is required");
    //
    LOG.config("GetHomeRoute is initialized.");
    this.playerLobby = playerLobby;
    this.templateEngine = templateEngine;
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
    //
    Map<String, Object> vm = new HashMap<>();
    vm.put("title", "Welcome!");

    // display a user message in the Home page
    vm.put("message", WELCOME_MSG);

    // render the View
    return templateEngine.render(new ModelAndView(vm , "home.ftl"));
  }
}
