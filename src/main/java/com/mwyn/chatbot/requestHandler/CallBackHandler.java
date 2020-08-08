package com.mwyn.chatbot.requestHandler;

import com.mwyn.chatbot.requestHandler.helpers.Constants;
import com.mwyn.chatbot.requestHandler.sessionManager.SessionServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

@Service
public class CallBackHandler {

    @Autowired
    private SessionServices sessionServices;

    public static final String ACCOUNT_SID = Constants.TWILIO.ACCOUNT_SID;
    public static final String AUTH_TOKEN = Constants.TWILIO.AUTH_TOKEN;
    public static final String SENDER = Constants.TWILIO.SENDER_WHATSAPP;

    public static void sendTo(String user,String body) {
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
        Message message = Message.creator(
                new com.twilio.type.PhoneNumber(user),
                new com.twilio.type.PhoneNumber(SENDER),
                body)
                .create();

        System.out.println(message.getSid());
    }

    public void Callback(String user,String Function){

    }

}
