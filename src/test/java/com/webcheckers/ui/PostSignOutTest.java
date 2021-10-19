package com.webcheckers.ui;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Map;

import com.webcheckers.app.PlayerLobby;

import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import spark.HaltException;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.Session;
import spark.TemplateEngine;

/**
 * Helper class to extract data from Spark's {@link ModelAndView} objects
 * that are passed to the {@code TemplateEngine.render} method.
 *
 * @author <a href='mailto:bdbvse@rit.edu'>Bryan Basham</a>
 */
@Tag("UI-tier")
public class PostSignOutTest {

  private PostSignOutRoute CuT;

  private PlayerLobby playerLobby;
  private TemplateEngine engine;

  private Response response;
  private Request request;
  private Session session;

  @BeforeEach
  public void setup() {
    request = mock(Request.class);
    session = mock(Session.class);
    when(request.session()).thenReturn(session);
    engine = mock(TemplateEngine.class);
    response = mock(Response.class);

    playerLobby = new PlayerLobby();

    CuT = new PostSignOutRoute(playerLobby, engine);
  }

  @Test
  public void successful_log_out() throws Exception {
    final TemplateEngineTester testHelper = new TemplateEngineTester();
    when(engine.render(any(ModelAndView.class))).thenAnswer(testHelper.makeAnswer());

    CuT.handle(request, response);

    testHelper.assertViewModelExists();
    testHelper.assertViewModelIsaMap();
    testHelper.assertViewName(GetHomeRoute.VIEW_NAME);
  }
}