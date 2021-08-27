package com.example.mypassword.utils;

import com.example.mypassword.model.Account;

import java.util.ArrayList;
import java.util.List;

public class ConvertiUtility {

    public static Account stringToAccount(String stringaAccount){
        String id = SaveUtility.getTagValues(stringaAccount, Costanti.Save.TagNameAccount.ID).get(0).trim();
        String servizio = SaveUtility.getTagValues(stringaAccount, Costanti.Save.TagNameAccount.SERVIZIO).get(0).trim();
        String username = SaveUtility.getTagValues(stringaAccount, Costanti.Save.TagNameAccount.USERNAME).get(0).trim();
        String password = SaveUtility.getTagValues(stringaAccount, Costanti.Save.TagNameAccount.PASSWORD).get(0).trim();

        Account account = new Account();
        account.setId(id);
        account.setServizio(servizio);
        account.setUsername(username);
        account.setPassword(password);

        return account;
    }

    public static String accountToString(Account account){
        String accountString = "";

        if(account!=null){
            accountString =  "<" + Costanti.Save.TagNameAccount.TAG_ACCOUNT + ">\n";
            accountString += SaveUtility.getTagString(account.getId(), Costanti.Save.TagNameAccount.ID);
            accountString += SaveUtility.getTagString(account.getServizio(), Costanti.Save.TagNameAccount.SERVIZIO);
            accountString += SaveUtility.getTagString(account.getUsername(), Costanti.Save.TagNameAccount.USERNAME);
            accountString += SaveUtility.getTagString(account.getPassword(), Costanti.Save.TagNameAccount.PASSWORD);
            accountString +=  "</" + Costanti.Save.TagNameAccount.TAG_ACCOUNT + ">\n";
        }
        return accountString;
    }

    public static List<Account> stringToAccountList(String stringaAccount){
        List<String> listaAccountString = SaveUtility.getTagValues(stringaAccount, Costanti.Save.TagNameAccount.TAG_ACCOUNT);
        if(listaAccountString==null || listaAccountString.isEmpty())
            return null;

        List<Account> listaAccount = new ArrayList<>();
        for(String accountString : listaAccountString){
            Account account = ConvertiUtility.stringToAccount(accountString);
            listaAccount.add(account);
        }

        return listaAccount;
    }

    public static String accountListToString(List<Account> listaAccount){
        String accountString = "";

        if(listaAccount!=null){
            for(Account a : listaAccount){
                accountString += accountToString(a);
            }
        }
        return accountString;
    }
}
