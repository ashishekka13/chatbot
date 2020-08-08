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
    private int group;
    private int stage;
    private List<Integer> states;

    UserSession(){
        stage=-1;
        states=new ArrayList<Integer>();
    }

}
