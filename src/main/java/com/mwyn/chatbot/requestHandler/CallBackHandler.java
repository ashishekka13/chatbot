package com.mwyn.chatbot.requestHandler;

import com.mwyn.chatbot.requestHandler.helpers.Constants;
import com.mwyn.chatbot.requestHandler.messageService.MessageService;
import com.mwyn.chatbot.requestHandler.sessionManager.SessionServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

import java.text.DecimalFormat;
import java.util.Random;

import static com.mwyn.chatbot.requestHandler.helpers.Constants.CALLBACKS.*;
import static com.mwyn.chatbot.requestHandler.helpers.Constants.MESSAGE.*;

@Service
public class CallBackHandler {

    @Autowired
    private SessionServices sessionServices;

    @Autowired
    private MessageService messageService;


    private String triggerOtp(String mobile){
        System.out.println("sending OTP");

        String otp= new DecimalFormat("000000").format(new Random().nextInt(999999));
        messageService.sendSMSMessage(mobile.substring(9),OTP_HEAD+otp+OTP_FOOTER);
        messageService.sendWhatsAppMessage(mobile,OTP_SENT);
        System.out.println("Your OTP is: "+otp);
        return otp;
    }

    private boolean authenticateUser(String user){
        if(sessionServices.isAuthenticated(user))
            return true;
        String otp = triggerOtp(user);
        sessionServices.setSession(user,"otp",otp);
        sessionServices.setSession(user,"requestedAttribute","otp");
        return false;
    }

    private String callApplicationStatusAPI(String user){
        int key = Integer.parseInt(user.substring(user.length() - 1))%4;
        switch (key){
            case 0: return "Pending";
            case 1: return "Soft Approval Done";
            case 2: return "Final Approval Pending";
            default: return "Approved";
        }
    }


    private String checkApplicationStatus(String user){
        if(this.authenticateUser(user)){
            return callApplicationStatusAPI(user);
        }
        else return null;
    }

    private String getVerificationDetails(String user){
        if(this.authenticateUser(user)){
            return getVerificationHelpAPI(user);
        }
        else return null;
    }

    private String getExecutiveDetails(String user){
        if(this.authenticateUser(user)){
            return getExecutiveUpdatesAPI(user);
        }
        else return null;
    }


    private String getVerificationHelpAPI(String user) {
        int key = Integer.parseInt(user.substring(user.length() - 1))%4;
        switch (key){
            case 0: return "5-6 working days";
            case 1: return "a day or two. Thank you for our Patience.";
            case 2: return "the next 24 hours.";
            default: return "the next 2 hours";
        }
    }

    private String getExecutiveUpdatesAPI(String user) {
        int key = Integer.parseInt(user.substring(user.length() - 1))%4;
        switch (key){
            case 0: return "the next 10 days";
            case 1: return "5-6 working days";
            case 2: return "the next 2 days";
            default: return "the next 12 hours.";
        }
    }

    public String Callback(String user,String function){
        System.out.printf("Entered callback");
        System.out.println(function);
        switch (function) {
            case CHECK_APPLICATION:
                return checkApplicationStatus(user);
            case VERIFICATION_CALL:
                return getVerificationDetails(user);
            case COLLECTION_TIME:
                return getExecutiveDetails(user);
            default:
                return function;
        }
    }

}
