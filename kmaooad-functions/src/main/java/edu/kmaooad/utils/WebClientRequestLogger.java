package edu.kmaooad.utils;

import org.springframework.stereotype.Component;

@Component
public class WebClientRequestLogger {

    public void logRequest(String req) {
        //should use real logger
        System.out.println("[Sending request: " + req + " ]");
    }
}
