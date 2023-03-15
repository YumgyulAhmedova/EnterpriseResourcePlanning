package com.example.EnterpriseResourcePlanningTESTS.error;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

@Controller
public class CustomErrorController implements ErrorController {


//    @RequestMapping("/error")
//    public String handleError(HttpServletRequest request) {
//        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
//
//        if (status == null && Integer.valueOf(status.toString()) == HttpStatus.NOT_FOUND.value()) {
//            return "Errors/404";
//        }
//        return "Errors/error";
//    }


    private static final Logger LOGGER = LoggerFactory.getLogger(CustomErrorController.class);

//    @RequestMapping("/error")
//    public String handleError(HttpServletRequest request, Model model) {
//        String errorPage = "error";
//        String pageTitle = "Error";
//
//        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
//
//        if (status != null) {
//            Integer statusCode = Integer.valueOf(status.toString());
//            if (statusCode == HttpStatus.NOT_FOUND.value()) {
//                pageTitle = "PAGE NOT FOUND";
//                errorPage = "Errors/404";
//                LOGGER.error("Error 404");
//            } else if (statusCode == HttpStatus.INTERNAL_SERVER_ERROR.value()) {
//                pageTitle = "INTERNAL SERVER ERROR";
//                errorPage = "Errors/500";
//                LOGGER.error("Error 500");
//            } else if (statusCode == HttpStatus.BAD_REQUEST.value()) {
//                pageTitle = "BAD REQUEST ERROR";
//                errorPage = "Errors/400";
//                LOGGER.error("Error 400");
//            } else if (statusCode == HttpStatus.FORBIDDEN.value()) {
//                pageTitle = "FORBIDDEN ERROR";
//                errorPage = "Errors/403";
//                LOGGER.error("Error 403");
////                model.addAttribute("message", "You are not authorized to view this page.");
//                model.addAttribute("accessDeniedMessage", "Access to localhost is denied. You are not authorized to view this page.");
//            }
//        }
//        model.addAttribute("pageTitle", pageTitle);
//
//        return errorPage;
//
//
//    }
//
//
////    @Override
//    public String getErrorPath() {
//        return "/error";
//    }


//    private static final Logger LOGGER = LoggerFactory.getLogger(CustomErrorController.class);

    @RequestMapping("/error")
    public String handleError(HttpServletRequest request, Model model) {
        String errorPage = "error";
        String pageTitle = "Error";

        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);

        if (status != null) {
            Integer statusCode = Integer.valueOf(status.toString());
            if (statusCode == HttpStatus.NOT_FOUND.value()) {
                pageTitle = "PAGE NOT FOUND";
                errorPage = "Errors/404";
                LOGGER.error("Error 404");
            } else if (statusCode == HttpStatus.INTERNAL_SERVER_ERROR.value()) {
                pageTitle = "INTERNAL SERVER ERROR";
                errorPage = "Errors/500";
                LOGGER.error("Error 500");
            } else if (statusCode == HttpStatus.BAD_REQUEST.value()) {
                pageTitle = "BAD REQUEST ERROR";
                errorPage = "Errors/400";
                LOGGER.error("Error 400");
            } else if (statusCode == HttpStatus.FORBIDDEN.value()) {
                pageTitle = "FORBIDDEN ERROR";
                errorPage = "Errors/403";
                LOGGER.error("Error 403");
                model.addAttribute("pageTitle", pageTitle);
                model.addAttribute("accessDeniedMessage", "Access to localhost is denied. You are not authorized to view this page.");
                return errorPage;
            }
        }
        model.addAttribute("pageTitle", pageTitle);
        return errorPage;
    }

//    @Override
    public String getErrorPath() {
        return "/error";
    }






}