package com.example.mypassword.utils;

public class Costanti {

    public class Messaggi {
        public static final String MESSAGGIO_MENU_OK = "MESSAGGIO_MENU_OK";
        public static final String MESSAGGIO_MENU_KO = "MESSAGGIO_MENU_KO";
    }

    public class Save{
        public final static int READ_BLOCK_SIZE = 100;
        public final static String NOME_FILE_SAVES = "account.xml";

        public class TagNameAccount{
            public static final String TAG_ACCOUNT = "account";
            public final static String ID = "id";
            public final static String SERVIZIO = "servizio";
            public final static String USERNAME = "username";
            public final static String PASSWORD = "password";
        }
    }
}
