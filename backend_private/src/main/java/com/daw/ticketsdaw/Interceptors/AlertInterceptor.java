package com.daw.ticketsdaw.Interceptors;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AlertInterceptor implements HandlerInterceptor {

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        String errorParam = request.getParameter("error");
        if(errorParam == null)
            errorParam = "";

        switch (errorParam){
            case "validation":
                modelAndView.addObject("error", "Validation error");
                break;
            case "unauthorized":
                modelAndView.addObject("error", "You are not authorized to perform this action");
                break;
            case "password":
                modelAndView.addObject("error", "The password entered isn't valid");
                break;
            case "edit_visible":
                modelAndView.addObject("error", "You can't update data that is flagged as 'visible'");
                break;
        }

        String warningParam = request.getParameter("warning");
        if(warningParam == null)
            warningParam = "";

        switch (warningParam){
            case "overlap":
                modelAndView.addObject("warning", "Your room was saved correctly, but an overlap has been detected");
                break;
        }
        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
    }
}
