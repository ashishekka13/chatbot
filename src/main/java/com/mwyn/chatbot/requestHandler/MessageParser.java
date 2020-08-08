package com.mwyn.chatbot.requestHandler;


import com.mwyn.chatbot.requestHandler.sessionManager.SessionServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MessageParser {

    @Autowired
    FaqHandler faqHandler;

    @Autowired
    SessionServices sessionServices;

    public String parse(String user, String message){
        if(sessionServices.isNew(user)){
            return "Welcome to MoneyTap. How Can I help you today \n"+faqHandler.getResponse(user);
        }

        if(message.length()<=1){
            int i = Integer.parseInt(message.trim());
            sessionServices.addFaqState(user,i);
            return faqHandler.getResponse(user);
        }
        return "Sorry, I couldn't understand that.";
    }
}
