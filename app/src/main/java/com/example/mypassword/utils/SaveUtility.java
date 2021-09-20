package com.example.mypassword.utils;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mypassword.model.Account;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SaveUtility {

    public static void salvaPin(AppCompatActivity context, String pin){
        try{
            FileOutputStream fOut = context.openFileOutput(Costanti.Save.NOME_FILE_PIN_SAVES, context.MODE_PRIVATE);
            OutputStreamWriter osw = new OutputStreamWriter(fOut);

            String recordString = pin;

            osw.write(""+recordString);
            osw.flush();
            osw.close();
        }
        catch (IOException ioe){
            ioe.printStackTrace();
        }
    }

    public static String findPin(AppCompatActivity context){
        try {
            FileInputStream fIn = context.openFileInput(Costanti.Save.NOME_FILE_PIN_SAVES);

            InputStreamReader isr = new InputStreamReader(fIn);

            char[] inputBuffer = new char[Costanti.Save.READ_BLOCK_SIZE];
            String s = "";

            int charRead;

            while ((charRead = isr.read(inputBuffer))>0){
                String readString = String.copyValueOf(inputBuffer, 0,charRead);
                s += readString;
                inputBuffer = new char[Costanti.Save.READ_BLOCK_SIZE];
            }

            if(s==null || s.isEmpty())
                return null;

            return s;
        }
        catch (IOException ioe) {
            return null;
        }
    }

    public static void salvaListaAccount(AppCompatActivity context, List<Account> listaAccount){
        try{
            FileOutputStream fOut = context.openFileOutput(Costanti.Save.NOME_FILE_SAVES, context.MODE_PRIVATE);
            OutputStreamWriter osw = new OutputStreamWriter(fOut);

            String recordString = ConvertiUtility.accountListToString(listaAccount);

            osw.write(""+recordString);
            osw.flush();
            osw.close();
        }
        catch (IOException ioe){
            ioe.printStackTrace();
        }
    }

    public static List<Account> findAllAccount(AppCompatActivity context){
        try {
            FileInputStream fIn = context.openFileInput(Costanti.Save.NOME_FILE_SAVES);

            InputStreamReader isr = new InputStreamReader(fIn);

            char[] inputBuffer = new char[Costanti.Save.READ_BLOCK_SIZE];
            String s = "";

            int charRead;

            while ((charRead = isr.read(inputBuffer))>0){
                String readString = String.copyValueOf(inputBuffer, 0,charRead);
                s += readString;
                inputBuffer = new char[Costanti.Save.READ_BLOCK_SIZE];
            }

            if(s==null || s.isEmpty())
                return null;

            return ConvertiUtility.stringToAccountList(s);
        }
        catch (IOException ioe) {
            return null;
        }
    }

    public static Account findAccountById(AppCompatActivity context, String idAccount){
        if(idAccount==null || idAccount.isEmpty())
            return null;
        List<Account> listaAccount = findAllAccount(context);
        for(Account account : listaAccount){
            if(idAccount.equalsIgnoreCase(account.getId()))
                return account;
        }
        return null;
    }

    public static String getNewId(AppCompatActivity context){
        List<Account> listaAccount = findAllAccount(context);
        if(listaAccount==null || listaAccount.isEmpty())
            return "1";
        int massimo = 0;
        for (Account a : listaAccount){
            int id = 0;
            try{
                id = Integer.parseInt(a.getId());
            }catch (NumberFormatException e){
                id = 0;
            }
            if(id > massimo)
                massimo = id;
        }
        return String.valueOf(massimo+1);
    }

    public static List<String> getTagValues(final String str, final String tagName) {

        final Pattern TAG_REGEX = Pattern.compile("<"+tagName+">(.+?)</"+tagName+">", Pattern.DOTALL);
        final List<String> tagValues = new ArrayList<String>();
        final Matcher matcher = TAG_REGEX.matcher(str);
        while (matcher.find()) {
            tagValues.add(matcher.group(1));
        }
        return tagValues;
    }

    public static String getTagString(final String value, final String tagName){
        String ret = "";
        ret += "<" + tagName + ">\n";
        ret += "    " + value + "\n";
        ret += "</" + tagName + ">\n";
        return ret;
    }
}
