package com.mwyn.chatbot.requestHandler.sessionManager;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Getter @Setter @AllArgsConstructor
public class UserSession {
    private String mobile;
    private String applicationId;
    private String token;
    private String otp;
    private boolean valid;
    private int group;
    private int stage;
    private String requestedAttribute;
    private List<Integer> states;
    private List<List<Integer>> callbackQueue;

    UserSession(){
        stage=-1;
        states=new ArrayList<Integer>();
        callbackQueue= new ArrayList<List<Integer>>();
        valid=false;
    }

}
