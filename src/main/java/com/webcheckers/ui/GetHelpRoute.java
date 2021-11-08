package com.webcheckers.ui;

import java.util.logging.*;
import spark.*;

public class GetHelpRoute implements Route {
    private static final Logger LOG = Logger.getLogger(GetHelpRoute.class.getName());

    // freemarker file
    public static final String VIEW_NAME = "help.ftl";

    // freemarker variables

    // parameter initializations
    private final TemplateEngine templateEngine;


    public GetHelpRoute(TemplateEngine templateEngine) {
        this.templateEngine = templateEngine;

        LOG.config("GetHelpRoute is initialized.");
    }

    @Override
    public Object handle(Request request, Response response) {
        
        LOG.finer("GetHelpRoute is invoked.");
        
        return templateEngine.render(new ModelAndView(vm, VIEW_NAME));
    }
}
