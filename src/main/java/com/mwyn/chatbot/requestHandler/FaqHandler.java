package com.mwyn.chatbot.requestHandler;

import com.fasterxml.jackson.databind.util.JSONPObject;
import com.mwyn.chatbot.requestHandler.helpers.Constants;
import com.mwyn.chatbot.requestHandler.sessionManager.SessionServices;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileReader;
import java.util.Iterator;
import java.util.List;

import static com.mwyn.chatbot.requestHandler.helpers.Constants.MESSAGE.UNRECOGNISED;

@Service
public class FaqHandler {

     private static JSONObject jo;

     @Autowired
     private SessionServices sessionServices;

     @Autowired
     private CallBackHandler callBackHandler;


     public void parse() {

     try {
          ClassLoader classLoader = new FaqHandler().getClass().getClassLoader();
//
          File file = new File(classLoader.getResource("faq/faq.json").getFile());
//          FileReader file = new FileReader("/faq/faq.json");
          Object obj = new JSONParser().parse( new FileReader(file));

          jo = (JSONObject) obj;
          }catch (Exception e){
          System.out.println("Execption");
          System.out.println(e);
          }
     }

     public String getResponse(List<Integer> path, String user){

          Iterator<Integer> pathIterator = path.iterator();


          JSONObject nextVal=jo;
          while(pathIterator.hasNext()){
               try {
                    nextVal = ((JSONObject) nextVal.get(pathIterator.next().toString()));
                    if(nextVal==null){
                         this.handleInputExp(user);
                    }
               }catch (Exception e){
                    this.handleInputExp(user);
               }
          }
          JSONArray response = (JSONArray) nextVal.get("value");
          Iterator itr2 = response.iterator();
          String responseString="";
          while (itr2.hasNext()){
               String next=  itr2.next() + "";
               if(next.startsWith("%")) {
                    String s = callBackHandler.Callback(user,next.substring(1));
                    if (s == null || s.length()<=0){
                         return null;
                    }
                    responseString = responseString +" "+ s;
               }
               else {
                    responseString = responseString + "\n" + next;
               }
          }
          return responseString;
     }

     public String handleInputExp(String user){
          sessionServices.popFaqState(user);
          return UNRECOGNISED;
     }

     public String getResponse(String user){

               String response = this.getResponse(sessionServices.getSession(user).getStates(),user);

               return response;

     }
     
}
