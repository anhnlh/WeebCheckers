package com.webcheckers.ui;

import java.util.*;
import java.util.logging.*;

import spark.*;

public class GetHelpRoute implements Route {
    private static final Logger LOG = Logger.getLogger(GetHelpRoute.class.getName());

    // freemarker file
    public static final String VIEW_NAME = "help.ftl";

    // freemarker variables
    public static final String TITLE_ATTR = "title";

    // parameter initializations
    private final TemplateEngine templateEngine;


    public GetHelpRoute(TemplateEngine templateEngine) {
        Objects.requireNonNull(templateEngine, "templateEngine is required");

        this.templateEngine = templateEngine;

        LOG.config("GetHelpRoute is initialized.");
    }

    @Override
    public Object handle(Request request, Response response) {
        
        LOG.finer("GetHelpRoute is invoked.");

        // start building the View-Model
        final Map<String, Object> vm = new HashMap<>();

        // display help title
        vm.put(TITLE_ATTR, "Help");
        
        return templateEngine.render(new ModelAndView(vm, VIEW_NAME));
    }
}
