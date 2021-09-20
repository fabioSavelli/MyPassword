package com.example.mypassword.utils;

public class Costanti {

    public class Messaggi {
        public static final String ELENCO_PASSWORD_RICERCA = "ELENCO_PASSWORD_RICERCA";
        public static final String MODIFICA_PASSWORD_ID_ACCOUNT = "MODIFICA_PASSWORD_ID_ACCOUNT";
    }

    public class Save{
        public final static int READ_BLOCK_SIZE = 100;
        public final static String NOME_FILE_PIN_SAVES = "pin.xml";
        public final static String NOME_FILE_SAVES = "account.xml";

        public class TagNameAccount{
            public static final String TAG_ACCOUNT = "account";
            public final static String ID = "id";
            public final static String SERVIZIO = "servizio";
            public final static String USERNAME = "username";
            public final static String PASSWORD = "password";
            public final static String APPUNTI  = "appunti";
        }
    }
}
