package com.mwyn.chatbot.requestHandler.sessionManager;


import org.springframework.stereotype.Service;

import javax.jws.soap.SOAPBinding;
import java.util.HashMap;
import java.util.List;

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
            case "otp" : session.setOtp(value); break;
            case "requestedAttribute" : session.setRequestedAttribute(value); break;
            default:
        }
    }

    public void authenticate(String user,String otp){
        UserSession session = sessions.get(user);
        session.setValid(true);
        session.setToken("<token>");

    }

    public String getField(String user, String id){
        UserSession session;
        session = sessions.get(user);
        switch (id){
            case "mobile" : return session.getMobile();
            case "applicationId": return session.getApplicationId();
            case "otp" : return session.getOtp();
            case "requestedAttribute" : return session.getRequestedAttribute();
            default: return null;
        }
    }

    public boolean isAuthenticated(String user){
        if(this.getSession(user).isValid())
            return true;
        return false;
    }

    public void addFaqState(String user, int val){
        UserSession session =this.getSession(user);
        session.getStates().add(val);
        session.setStates(session.getStates());
    }

    public void popFaqState(String user) {
        UserSession session = this.getSession(user);
        List<Integer> states = session.getStates();
        if(states.size()>0)
            states.remove(states.size()-1);
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
