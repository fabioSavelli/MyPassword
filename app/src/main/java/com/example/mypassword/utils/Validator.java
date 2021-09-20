package com.example.mypassword.utils;

import android.widget.EditText;

public class Validator {
    public static boolean validaCampoRicerca(EditText campoRicerca){
        return campoRicerca==null || campoRicerca.getText().toString()==null || campoRicerca.getText().toString().isEmpty();
    }

    public static String validaNuovaPassword(EditText sito, EditText username, EditText password){
        if(sito.getText().toString()==null || sito.getText().toString().isEmpty())
            return "Inserisci un Sito Web o il Servizio";
        if(username.getText().toString()==null || username.getText().toString().isEmpty())
            return "Inserisci Username o l'Email";
        if(password.getText().toString()==null || password.getText().toString().isEmpty())
            return "Inserisci la password";
        return null;
    }
}
