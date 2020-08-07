package com.mwyn.chatbot.requestHandler;


import com.twilio.twiml.MessagingResponse;
import com.twilio.twiml.messaging.Body;
import com.twilio.twiml.messaging.Message;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class RequestHandler {

    @RequestMapping(value = "/home")
    public String Home(){
        return  "Hello. You are home. Please stay here";
    }


    @RequestMapping(method = RequestMethod.POST, value = "/messageRequest")
    public String receiveMessage(){
        System.out.println("APIhit");
        Body body = new Body.Builder("Hey there").build();

//        Message reply = new Message.Builder().body(body).build();
//        MessagingResponse response = new MessagingResponse.Builder().message(reply).build();

        return body.toString();
    }

}
