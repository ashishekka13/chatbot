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
import java.util.Map;

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
          System.out.println(obj);
          jo = (JSONObject) obj;
          }catch (Exception e){
          System.out.println("Execption");
          System.out.println(e);
          }
     }

     public String getResponse(List<Integer> path, String user){
          this.parse();
          System.out.println("called1");

          Iterator<Integer> pathIterator = path.iterator();
          System.out.println(jo);
          System.out.println(jo.toJSONString().toString());
          JSONObject nextVal=jo;
          while(pathIterator.hasNext()){
               try {
                    nextVal = ((JSONObject) nextVal.get(pathIterator.next().toString()));
                    if(nextVal==null){
                         return null;
                    }
               }catch (Exception e){
                    return null;
               }
          }
          JSONArray response = (JSONArray) nextVal.get("value");
          Iterator itr2 = response.iterator();
          String responseString="";
          while (itr2.hasNext()){
               if(responseString.startsWith("%")) {
                    callBackHandler.Callback(user,responseString.substring(1));

               }
               responseString = responseString +"\n"+ itr2.next();
          }
          return responseString;
     }

     public String handleInputExp(String user){
          sessionServices.popFaqState(user);
          return Constants.MESSAGE.UNRECOGNISED;
     }

     public String getResponse(String user){
          try {
               String response = this.getResponse(sessionServices.getSession(user).getStates(),user);
               if(response==null) {
                    return handleInputExp(user);
               }
               return response;
          }catch (Exception e) {
               return handleInputExp(user);
          }
     }
     
}
