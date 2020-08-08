package com.mwyn.chatbot.requestHandler;


import com.mwyn.chatbot.requestHandler.helpers.Constants;
import com.mwyn.chatbot.requestHandler.sessionManager.SessionServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


import static com.mwyn.chatbot.requestHandler.helpers.Constants.*;
import static com.mwyn.chatbot.requestHandler.helpers.Constants.MESSAGE.*;

@Service
public class MessageParser {

    @Autowired
    private FaqHandler faqHandler;

    private static Pattern pattern = Pattern.compile("\\d+");

    @Autowired
    private SessionServices sessionServices;


    public String parse(String user, String message) {
        if(sessionServices.isNew(user)){
            return faqHandler.getResponse(user) + "\n"+ MENU_FOOTER;
        }
        Matcher matcher = pattern.matcher(message);
        if(message.length()<=4){
            if(matcher.find()) {
                int i = Integer.parseInt(message.substring(matcher.start(),matcher.end()));
                if(i==0) {
                    sessionServices.resetFaqState(user);
                }
                else sessionServices.addFaqState(user, i);
                String response = faqHandler.getResponse(user);
                return response;
            }
        }

        if(matcher.find()) {
            int i = Integer.parseInt(message.substring(matcher.start(),matcher.end()));
            String key=sessionServices.getSession(user).getRequestedAttribute();
            if(key!=null){
                switch (key) {
                    case "otp": if(sessionServices.getField(user,"otp").equals(Integer.toString(i)) ) {
                                    sessionServices.authenticate(user,Integer.toString(i));
                                    return faqHandler.getResponse(user);
                                }
                                else{
                                    return OTP_ERROR;
                                }
                    default: sessionServices.setSession(user, key, Integer.toString(i));
                    sessionServices.setSession(user, "requestedAttribute", null);
                    return VALUE_UPDATED + "\n" + faqHandler.getResponse(user);
                }
            }
            else return UNRECOGNISED;
        }

        List<String> ls=new ArrayList<>();
        ls.add(message);
        try {
            String st= Dialogflow.detectIntentTexts("twilio-chatbot-gpgm", ls,UUID.randomUUID().toString(),"en-US");
            if(st.substring(0,3).equals("otp"))
                st=st.substring(4);
            else if(st.substring(0,2).equals("ph"))
                st=st.substring(3);
            else {
                List<Integer> fetch = new ArrayList<>();
                for (int i = 0; i < st.length(); i++) {
                    if(st.charAt(i)!=',')
                        fetch.add(Integer.parseInt(String.valueOf(st.charAt(i))));
                }
                System.out.println(fetch);
                sessionServices.setFaqState(user,fetch);
                String response=faqHandler.getResponse(fetch,user);
                if (fetch.size()>2)
                    sessionServices.resetFaqState(user);
                return response;

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return UNRECOGNISED;
    }
}
