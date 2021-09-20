package com.example.mypassword.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.biometric.BiometricPrompt;
import androidx.core.content.ContextCompat;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.text.method.TransformationMethod;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mypassword.R;
import com.example.mypassword.model.Account;
import com.example.mypassword.utils.ConvertiUtility;
import com.example.mypassword.utils.Costanti;
import com.example.mypassword.utils.SaveUtility;
import com.example.mypassword.utils.Validator;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Executor;

public class ElencoPasswordActivity extends AppCompatActivity {

    private ListView listaAccountView;
    private TextView servizioAccountModal, usernameAccountModal, passwordAccountModal, appuntiAccountModal;
    private EditText campoRicercaElencoPassword;
    private Button mostraPasswordModificaAccountModal, buttonModificaAccountModal, buttonCancellaAccountModal;

    private Executor executor;
    private BiometricPrompt biometricPrompt;
    private androidx.biometric.BiometricPrompt.PromptInfo promptInfo;

    private String pin = "";
    private int position = 0;

    private BottomSheetDialog bottomSheetDialog;
    private ImageView pinImage1, pinImage2, pinImage3, pinImage4;
    private ImageButton bottoneCancellaInserisciPin, bottoneConfermaInserisciPin, bottoneRicercaElenco, bottoneCancellaRicercaElenco;
    private Button  bottoneInserisciPin1, bottoneInserisciPin2, bottoneInserisciPin3, bottoneInserisciPin4, bottoneInserisciPin5,
            bottoneInserisciPin6, bottoneInserisciPin7, bottoneInserisciPin8, bottoneInserisciPin9, bottoneInserisciPin0;

    private boolean mostroPassword;
    private int funzioneConferma; // 1 mostro password, 2 cancello account
    private Account accountCanc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_elenco_password);

        listaAccountView = findViewById(R.id.lista_account);

        campoRicercaElencoPassword   = findViewById(R.id.campoRicercaElencoPassword);
        bottoneRicercaElenco         = findViewById(R.id.bottoneRicercaElenco);
        bottoneCancellaRicercaElenco = findViewById(R.id.bottoneCancellaRicercaElenco);

        Bundle b = getIntent().getExtras();
        String campoRicercaString = null;
        if (b != null){
            campoRicercaString = b.getString(Costanti.Messaggi.ELENCO_PASSWORD_RICERCA);
            campoRicercaElencoPassword.setText(campoRicercaString);
        }

        campoRicercaElencoPassword.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    ricerca(campoRicercaElencoPassword.getText().toString());
                    return true;
                }
                return false;
            }
        });
        bottoneRicercaElenco.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ricerca(campoRicercaElencoPassword.getText().toString());
            }
        });
        bottoneCancellaRicercaElenco.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ElencoPasswordActivity.this, ElencoPasswordActivity.class);
                startActivity(intent);
                finish();
            }
        });

        // init biometric
        executor = ContextCompat.getMainExecutor(this);
        biometricPrompt = new BiometricPrompt(ElencoPasswordActivity.this,
                executor, new BiometricPrompt.AuthenticationCallback() {
            @Override
            public void onAuthenticationError(int errorCode, @NonNull CharSequence errString) {
                super.onAuthenticationError(errorCode, errString);

                if(11 == errorCode || 13 == errorCode)
                    lanciaModalPin();
                else
                    Toast.makeText(getApplicationContext(), "Errore durante l'autenticazione:\n" +errorCode + " - " + errString, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAuthenticationSucceeded(@NonNull BiometricPrompt.AuthenticationResult result) {
                super.onAuthenticationSucceeded(result);
                autorizzato();
            }

            @Override
            public void onAuthenticationFailed() {
                super.onAuthenticationFailed();
                Toast.makeText(getApplicationContext(), "Autenticazione fallita", Toast.LENGTH_SHORT).show();
            }
        });

        promptInfo = new BiometricPrompt.PromptInfo.Builder()
                .setTitle("Verifica la tua identità")
                .setSubtitle("Desideri utilizzare il riconoscimento biometrico?")
                .setNegativeButtonText("USA IL PIN")
                .build();

        List<Account> listaAccountDb = SaveUtility.findAllAccount(this);
        if(listaAccountDb==null)
            listaAccountDb = new LinkedList<>();

        List<Account> listaAccountDbFiltrato = new LinkedList<>();
        for(Account account : listaAccountDb){
            if(campoRicercaString==null || campoRicercaString.isEmpty())
                listaAccountDbFiltrato.add(account);
            else if(campoRicercaString!=null && !campoRicercaString.isEmpty() && account.getServizio()!=null &&
                    account.getServizio().toUpperCase().contains(campoRicercaString.toUpperCase()))
                listaAccountDbFiltrato.add(account);
        }
        final List<Account> listaAccount = listaAccountDbFiltrato;

        List<HashMap<String, String>> listaAccountMappa = new ArrayList<>();

        if(listaAccount != null && !listaAccount.isEmpty()){
            for(Account account : listaAccount){
                HashMap<String, String> hm = new HashMap<>();
                hm.put("Titolo", ConvertiUtility.primaLetteraMaiuscola(account.getServizio()));
                hm.put("Username", account.getUsername());
                listaAccountMappa.add(hm);
            }
        }
        else if(listaAccount == null || listaAccount.isEmpty()){
            HashMap<String, String> hm = new HashMap<>();
            hm.put("Titolo", "Nessuna password trovata");
            hm.put("Username", "Salva un nuovo account cliccando qui");
            listaAccountMappa.add(hm);
        }
        String [] from = {"Titolo", "Username"};
        int [] to = {R.id.title, R.id.description};

        SimpleAdapter simpleAdapter = new SimpleAdapter(this, listaAccountMappa, R.layout.item_list_account, from , to);

        listaAccountView.setAdapter(simpleAdapter);


        listaAccountView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(listaAccount == null || listaAccount.isEmpty()){
                    Intent intent = new Intent(ElencoPasswordActivity.this, NuovaPasswordActivity.class);
                    startActivity(intent);
                    finish();
                }
                else{
                    mostraAccount(listaAccount.get(position));
                }
            }
        });

        if(campoRicercaString==null || campoRicercaString.isEmpty()){
            bottoneCancellaRicercaElenco.setVisibility(View.GONE);
        }

        // per rimuovere il focus sul campo di ricerca
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }

    private void mostraAccount(Account account){
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(ElencoPasswordActivity.this, R.style.BottomSheetDialogTheme);
        View bottomSheetView = LayoutInflater.from(getApplicationContext())
                .inflate(R.layout.account_bottom_modal,
                        (LinearLayout)findViewById(R.id.accountBottomContainer));

        mostroPassword = true;
        servizioAccountModal = bottomSheetView.findViewById(R.id.servizioAccountModal);
        usernameAccountModal = bottomSheetView.findViewById(R.id.usernameAccountModal);
        passwordAccountModal = bottomSheetView.findViewById(R.id.passwordAccountModal);
        appuntiAccountModal  = bottomSheetView.findViewById(R.id.appuntiAccountModal);

        mostraPasswordModificaAccountModal = bottomSheetView.findViewById(R.id.mostraPasswordModificaAccountModal);
        buttonModificaAccountModal = bottomSheetView.findViewById(R.id.buttonModificaAccountModal);
        buttonCancellaAccountModal = bottomSheetView.findViewById(R.id.buttonCancellaAccountModal);

        servizioAccountModal.setText(ConvertiUtility.primaLetteraMaiuscola(account.getServizio()));
        usernameAccountModal.setText(account.getUsername());
        passwordAccountModal.setText(account.getPassword());
        appuntiAccountModal.setText(account.getAppunti());

        mostraPasswordModificaAccountModal.setText("Mostra Password e Appunti");

        mostraPasswordModificaAccountModal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mostroPassword){
                    funzioneConferma = 1;
                    biometricPrompt.authenticate(promptInfo);
                }
                else{
                    ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                    ClipData clip = ClipData.newPlainText("Password", account.getPassword());
                    clipboard.setPrimaryClip(clip);

                    Toast.makeText(getApplicationContext(), "Password Copiata...", Toast.LENGTH_SHORT).show();
                    mostraPasswordModificaAccountModal.setVisibility(View.GONE);
                }
            }
        });

        buttonModificaAccountModal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                funzioneConferma = 3;
                accountCanc = account;
                biometricPrompt.authenticate(promptInfo);
            }
        });

        buttonCancellaAccountModal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                funzioneConferma = 2;
                accountCanc = account;
                biometricPrompt.authenticate(promptInfo);
            }
        });

        bottomSheetDialog.setContentView(bottomSheetView);
        bottomSheetDialog.show();
    }

    public void lanciaModalPin(){
        // apro modal inserisci pin
        bottomSheetDialog = new BottomSheetDialog(ElencoPasswordActivity.this, R.style.BottomSheetDialogTheme);
        View bottomSheetView = LayoutInflater.from(getApplicationContext())
                .inflate(R.layout.layout_inserisci_pin_bottom_modal, (LinearLayout)findViewById(R.id.inserisciPinBottomContainer));

        pinImage1 = bottomSheetView.findViewById(R.id.pinImageInserisci1);
        pinImage2 = bottomSheetView.findViewById(R.id.pinImageInserisci2);
        pinImage3 = bottomSheetView.findViewById(R.id.pinImageInserisci3);
        pinImage4 = bottomSheetView.findViewById(R.id.pinImageInserisci4);

        bottoneInserisciPin1 = bottomSheetView.findViewById(R.id.bottoneInserisciPin1);
        bottoneInserisciPin2 = bottomSheetView.findViewById(R.id.bottoneInserisciPin2);
        bottoneInserisciPin3 = bottomSheetView.findViewById(R.id.bottoneInserisciPin3);
        bottoneInserisciPin4 = bottomSheetView.findViewById(R.id.bottoneInserisciPin4);
        bottoneInserisciPin5 = bottomSheetView.findViewById(R.id.bottoneInserisciPin5);
        bottoneInserisciPin6 = bottomSheetView.findViewById(R.id.bottoneInserisciPin6);
        bottoneInserisciPin7 = bottomSheetView.findViewById(R.id.bottoneInserisciPin7);
        bottoneInserisciPin8 = bottomSheetView.findViewById(R.id.bottoneInserisciPin8);
        bottoneInserisciPin9 = bottomSheetView.findViewById(R.id.bottoneInserisciPin9);
        bottoneInserisciPin0 = bottomSheetView.findViewById(R.id.bottoneInserisciPin0);
        bottoneCancellaInserisciPin = bottomSheetView.findViewById(R.id.bottoneCancellaInserisciPin);
        bottoneConfermaInserisciPin = bottomSheetView.findViewById(R.id.bottoneConfermaInserisciPin);

        pin = "";
        position = 0;

        pinImage1.setImageResource(R.drawable.pin_empty);
        pinImage2.setImageResource(R.drawable.pin_empty);
        pinImage3.setImageResource(R.drawable.pin_empty);
        pinImage4.setImageResource(R.drawable.pin_empty);

        abilitaDisabilitaConferma();

        bottoneInserisciPin1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(position>=4)
                    return;
                position++;
                pin += "1";
                inserisciColorePin();
                abilitaDisabilitaConferma();
            }
        });

        bottoneInserisciPin2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(position>=4)
                    return;
                position++;
                pin += "2";
                inserisciColorePin();
                abilitaDisabilitaConferma();
            }
        });

        bottoneInserisciPin3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(position>=4)
                    return;
                position++;
                pin += "3";
                inserisciColorePin();
                abilitaDisabilitaConferma();
            }
        });

        bottoneInserisciPin4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(position>=4)
                    return;
                position++;
                pin += "4";
                inserisciColorePin();
                abilitaDisabilitaConferma();
            }
        });

        bottoneInserisciPin5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(position>=4)
                    return;
                position++;
                pin += "5";
                inserisciColorePin();
                abilitaDisabilitaConferma();
            }
        });

        bottoneInserisciPin6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(position>=4)
                    return;
                position++;
                pin += "6";
                inserisciColorePin();
                abilitaDisabilitaConferma();
            }
        });

        bottoneInserisciPin7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(position>=4)
                    return;
                position++;
                pin += "7";
                inserisciColorePin();
                abilitaDisabilitaConferma();
            }
        });

        bottoneInserisciPin8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(position>=4)
                    return;
                position++;
                pin += "8";
                inserisciColorePin();
                abilitaDisabilitaConferma();
            }
        });

        bottoneInserisciPin9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(position>=4)
                    return;
                position++;
                pin += "9";
                inserisciColorePin();
                abilitaDisabilitaConferma();
            }
        });

        bottoneInserisciPin0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(position>=4)
                    return;
                position++;
                pin += "0";
                inserisciColorePin();
                abilitaDisabilitaConferma();
            }
        });

        bottoneConfermaInserisciPin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(SaveUtility.findPin(ElencoPasswordActivity.this).equals(pin)){
                    bottomSheetDialog.dismiss();
                    autorizzato();
                }
                else{
                    Toast.makeText(getApplicationContext(), "Il PIN inserito non è corretto", Toast.LENGTH_SHORT).show();
                }
            }
        });

        bottoneCancellaInserisciPin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(position<=0)
                    return;
                position--;
                pin = pin.substring(0, pin.length() - 1);
                rimuoviColorePin();
                abilitaDisabilitaConferma();
            }
        });

        bottomSheetDialog.setContentView(bottomSheetView);
        bottomSheetDialog.show();
    }

    private void abilitaDisabilitaConferma(){
        if(position!=4){
            bottoneConfermaInserisciPin.setEnabled(false);
            bottoneConfermaInserisciPin.setImageResource(R.drawable.ic_baseline_check_grey_24);

            bottoneInserisciPin1.setTextColor(ContextCompat.getColor(this, R.color.my_light_blue));
            bottoneInserisciPin2.setTextColor(ContextCompat.getColor(this, R.color.my_light_blue));
            bottoneInserisciPin3.setTextColor(ContextCompat.getColor(this, R.color.my_light_blue));
            bottoneInserisciPin4.setTextColor(ContextCompat.getColor(this, R.color.my_light_blue));
            bottoneInserisciPin5.setTextColor(ContextCompat.getColor(this, R.color.my_light_blue));
            bottoneInserisciPin6.setTextColor(ContextCompat.getColor(this, R.color.my_light_blue));
            bottoneInserisciPin7.setTextColor(ContextCompat.getColor(this, R.color.my_light_blue));
            bottoneInserisciPin8.setTextColor(ContextCompat.getColor(this, R.color.my_light_blue));
            bottoneInserisciPin9.setTextColor(ContextCompat.getColor(this, R.color.my_light_blue));
            bottoneInserisciPin0.setTextColor(ContextCompat.getColor(this, R.color.my_light_blue));
        }
        else{
            bottoneConfermaInserisciPin.setEnabled(true);
            bottoneConfermaInserisciPin.setImageResource(R.drawable.ic_baseline_check_green_24);

            bottoneInserisciPin1.setTextColor(ContextCompat.getColor(this, R.color.edit_text_hint_grey));
            bottoneInserisciPin2.setTextColor(ContextCompat.getColor(this, R.color.edit_text_hint_grey));
            bottoneInserisciPin3.setTextColor(ContextCompat.getColor(this, R.color.edit_text_hint_grey));
            bottoneInserisciPin4.setTextColor(ContextCompat.getColor(this, R.color.edit_text_hint_grey));
            bottoneInserisciPin5.setTextColor(ContextCompat.getColor(this, R.color.edit_text_hint_grey));
            bottoneInserisciPin6.setTextColor(ContextCompat.getColor(this, R.color.edit_text_hint_grey));
            bottoneInserisciPin7.setTextColor(ContextCompat.getColor(this, R.color.edit_text_hint_grey));
            bottoneInserisciPin8.setTextColor(ContextCompat.getColor(this, R.color.edit_text_hint_grey));
            bottoneInserisciPin9.setTextColor(ContextCompat.getColor(this, R.color.edit_text_hint_grey));
            bottoneInserisciPin0.setTextColor(ContextCompat.getColor(this, R.color.edit_text_hint_grey));
        }
    }

    private void inserisciColorePin(){
        switch (position){
            case 1:
                pinImage1.setImageResource(R.drawable.pin_full);
                break;
            case 2:
                pinImage2.setImageResource(R.drawable.pin_full);
                break;
            case 3:
                pinImage3.setImageResource(R.drawable.pin_full);
                break;
            case 4:
                pinImage4.setImageResource(R.drawable.pin_full);
                break;
            default:
                break;
        }
    }

    private void rimuoviColorePin(){
        switch (position){
            case 0:
                pinImage1.setImageResource(R.drawable.pin_empty);
                break;
            case 1:
                pinImage2.setImageResource(R.drawable.pin_empty);
                break;
            case 2:
                pinImage3.setImageResource(R.drawable.pin_empty);
                break;
            case 3:
                pinImage4.setImageResource(R.drawable.pin_empty);
                break;
            default:
                break;
        }
    }

    private void autorizzato(){
        if(funzioneConferma==1){ // mostro password
            mostraPassword();
        }
        else if(funzioneConferma==2){ // elimino account
            if(bottomSheetDialog!=null)
                bottomSheetDialog.dismiss();
            eliminaAccount(accountCanc);
        }
        else if(funzioneConferma==3){ // modifica account
            if(bottomSheetDialog!=null)
                bottomSheetDialog.dismiss();
            modificaAccount(accountCanc);
        }
    }

    private void mostraPassword(){
        mostroPassword = false;
        mostraPasswordModificaAccountModal.setText("Copia Password");
        passwordAccountModal.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
        appuntiAccountModal.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
    }

    private void modificaAccount(Account account){
        Intent intent = new Intent(ElencoPasswordActivity.this, ModificaPasswordActivity.class);
        intent.putExtra(Costanti.Messaggi.MODIFICA_PASSWORD_ID_ACCOUNT, account.getId());
        startActivity(intent);
        finish();
    }

    private void eliminaAccount(Account account){
        List<Account> listaAccount = SaveUtility.findAllAccount(this);
        List<Account> listaDaSalvare = new LinkedList<>();

        if (listaAccount == null)
            listaAccount = new LinkedList<>();

        for(Account a : listaAccount){
            if(!a.getId().equals(account.getId())){
                listaDaSalvare.add(a);
            }
        }
        SaveUtility.salvaListaAccount(this, listaDaSalvare);
        Toast.makeText(getApplicationContext(), "Password cancellata correttamente", Toast.LENGTH_SHORT).show();

        Intent intent = new Intent(ElencoPasswordActivity.this, ElencoPasswordActivity.class);
        startActivity(intent);
        finish();
    }

    private void ricerca(String ricerca){
        Intent intent = new Intent(this, ElencoPasswordActivity.class);
        intent.putExtra(Costanti.Messaggi.ELENCO_PASSWORD_RICERCA, ricerca);
        startActivity(intent);
        finish();
    }
}