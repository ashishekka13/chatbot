package com.mwyn.chatbot.requestHandler.sessionManager;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter @Setter @AllArgsConstructor
public class UserSession {
    private String mobile;
    private String applicationId;
    private int group;
    private int stage;
    private List<String> states;


    UserSession(){
        stage=-1;
    }

}
