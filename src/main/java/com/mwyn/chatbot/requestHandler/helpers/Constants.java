package com.mwyn.chatbot.requestHandler.helpers;

public class Constants {
    private Constants(){
    }

    public static class TWILIO {
        private TWILIO() {}

        public static final String ACCOUNT_SID= "ACedf51626a9b0d19601150591a98b5f4d";
        public static final String AUTH_TOKEN= "cb5366b38885a0b8a3a34016a956d048";
        public static final String SENDER_WHATSAPP="whatsapp:+14155238886";
    }

    public static class MESSAGE {
        private MESSAGE(){
        }

        public static final String UNRECOGNISED = "Sorry, I am unable to understand what you just said.";
        public static final String MENU_FOOTER="Any any stage Enter 0 to go back to the previous Menu.";
    }

    public static class CALLBACKS {
        private CALLBACKS(){
        }

        public static final String CHECK_APPLICATION = "CHECK_APPLICATION";
    }
}
