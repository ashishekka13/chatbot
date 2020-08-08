package com.mwyn.chatbot.requestHandler;


import com.twilio.twiml.MessagingResponse;
import com.twilio.twiml.messaging.Body;
import com.twilio.twiml.messaging.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.sql.SQLOutput;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.List;


@RestController
public class RequestHandler {



    @Autowired
    private FaqHandler faqs;

    @Autowired
    private MessageParser messageParser;


    @RequestMapping(value = "/home")
    public String Home(){
        return  "Hello. You are home. Please stay here";
    }

    @PostMapping(path = "/messageRequest", consumes = "application/x-www-form-urlencoded")
//    @RequestMapping(method = RequestMethod.POST, value = "/messageRequest")
    public String receiveMessage(HttpServletRequest req){
//        System.out.println(req.getBody());
        System.out.println("API hit");
        System.out.println(req.getParameter("Body"));
//        System.out.println(req.getBody().toString());
        String from = req.getParameter("From");
        String message=req.getParameter("Body");
        System.out.println(from);

        return messageParser.parse(from,message);
//        Body body = new Body.Builder("Hey there").build();
//        Message reply = new Message.Builder().body(body).build();
//        MessagingResponse response = new MessagingResponse.Builder().message(reply).build();

//        return "hey you";
    }

    @RequestMapping(method=RequestMethod.POST , value="/try")
    public String test(){
        Dialogflow dialogflow = new Dialogflow();
        try {
            dialogflow.callback();
            return "done";
        }catch (Exception e){
            System.out.println(e);
            return "fail";
        }
    }

}
