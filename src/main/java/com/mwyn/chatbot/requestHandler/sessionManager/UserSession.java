package com.mwyn.chatbot.requestHandler.sessionManager;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter @AllArgsConstructor
public class UserSession {
    private String mobile;
    private String applicationId;
    private int group;
    private int stage;

    UserSession(){
        stage=-1;
    }

}
