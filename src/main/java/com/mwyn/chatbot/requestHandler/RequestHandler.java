package com.mwyn.chatbot.requestHandler;


import com.twilio.twiml.MessagingResponse;
import com.twilio.twiml.messaging.Body;
import com.twilio.twiml.messaging.Message;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.sql.SQLOutput;
import java.util.Enumeration;


@RestController
public class RequestHandler {

    @RequestMapping(value = "/home")
    public String Home(){
        return  "Hello. You are home. Please stay here";
    }

    @PostMapping(path = "/messageRequest", consumes = "application/x-www-form-urlencoded")
//    @RequestMapping(method = RequestMethod.POST, value = "/messageRequest")
    public String receiveMessage(HttpServletRequest req){
        Enumeration<String> params = req.getParameterNames();
//        System.out.println(req.getBody());
        System.out.println("API hit");
        System.out.println(req.getParameter("Body"));
//        System.out.println(req.getBody().toString());
        String from = req.getParameter("From");
        System.out.println(from                                     );


//        Body body = new Body.Builder("Hey there").build();
//        Message reply = new Message.Builder().body(body).build();
//        MessagingResponse response = new MessagingResponse.Builder().message(reply).build();

        return "hey you";
    }

}
