package com.mwyn.chatbot.requestHandler.messageService;

import com.mwyn.chatbot.requestHandler.helpers.Constants;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import org.springframework.stereotype.Service;

import static com.mwyn.chatbot.requestHandler.helpers.Constants.TWILIO.*;

@Service
public class MessageService {



    public void sendWhatsAppMessage(String user,String body) {
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
        Message message = Message.creator(
                new com.twilio.type.PhoneNumber(user),
                new com.twilio.type.PhoneNumber(SENDER_WHATSAPP),
                body)
                .create();
        System.out.println(message.getSid());
    }

    public  void sendSMSMessage(String user,String body) {
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
        Message message = Message.creator(
                new com.twilio.type.PhoneNumber(user),
                new com.twilio.type.PhoneNumber(SENDER_MOBILE),
                body)
                .create();
        System.out.println(message.getSid());
    }




}
