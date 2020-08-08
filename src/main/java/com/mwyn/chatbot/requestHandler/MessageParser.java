package com.mwyn.chatbot.requestHandler;


import com.mwyn.chatbot.requestHandler.helpers.Constants;
import com.mwyn.chatbot.requestHandler.sessionManager.SessionServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class MessageParser {

    @Autowired
    private FaqHandler faqHandler;

    private static Pattern pattern = Pattern.compile("\\d+");

    @Autowired
    private SessionServices sessionServices;


    public String parse(String user, String message){
        if(sessionServices.isNew(user)){
            return faqHandler.getResponse(user) + "\n"+ Constants.MESSAGE.MENU_FOOTER;
        }

        if(message.length()<=4){
            Matcher matcher = pattern.matcher(message);
            if(matcher.find()) {
                int i = Integer.parseInt(message.substring(matcher.start(),matcher.end()));
                if(i==0) {
                    sessionServices.popFaqState(user);
                }
                else sessionServices.addFaqState(user, i);
                String response = faqHandler.getResponse(user);
                return response;
            }
        }


        return "NLP processing needed";
    }
}
