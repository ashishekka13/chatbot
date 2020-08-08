package com.mwyn.chatbot.requestHandler.sessionManager;


import org.springframework.stereotype.Service;

import javax.jws.soap.SOAPBinding;
import java.util.HashMap;

@Service
public class SessionServices {

    private static HashMap<String,UserSession> sessions = new HashMap<String,UserSession>();



    public void setSession(String user, String id, String value){
        UserSession session;

        if (sessions.containsKey(user) )
            session = sessions.get(user);
        else
            session = new UserSession();
        switch (id){
            case "mobile" : session.setMobile(value); break;
            case "applicationId": session.setApplicationId(value); break;
            default:
        }
    }

    public void addFaqState(String user, int val){
        UserSession session =this.getSession(user);
        session.getStates().add(val);
        session.setStates(session.getStates());
    }

    public void setSession(String user, String id, int value){
        UserSession session;

        if (sessions.containsKey(user) )
            session = sessions.get(user);
        else
            session = new UserSession();
        switch (id){
            case "group" : session.setGroup(value); break;
            case "stage": session.setStage(value); break;
            default:
        }
    }

    public UserSession getSession(String user){
        if(user!=null) {
            if (sessions.containsKey(user))
                return sessions.get(user);
        }
        sessions.put(user,new UserSession());
        return new UserSession();
    }

    public boolean isNew(String user){
        if(user!=null){
            if (sessions.containsKey(user))
                return false;
        }
        return true;
    }

}
