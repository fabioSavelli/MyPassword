package com.example.mypassword.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.biometric.BiometricPrompt;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.mypassword.R;
import com.example.mypassword.model.Account;
import com.example.mypassword.utils.Costanti;
import com.example.mypassword.utils.SaveUtility;
import com.example.mypassword.utils.Validator;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Executor;

public class ModificaPasswordActivity extends AppCompatActivity {

    private EditText sitoWeb, username, password, appunti;
    private Account account;

    private Executor executor;
    private BiometricPrompt biometricPrompt;
    private androidx.biometric.BiometricPrompt.PromptInfo promptInfo;

    private String idAccount, pin = "";
    private int position = 0;
    private BottomSheetDialog bottomSheetDialog;
    private ImageView pinImage1, pinImage2, pinImage3, pinImage4;
    private ImageButton bottoneCancellaInserisciPin, bottoneConfermaInserisciPin;
    private Button bottoneInserisciPin1, bottoneInserisciPin2, bottoneInserisciPin3, bottoneInserisciPin4, bottoneInserisciPin5,
            bottoneInserisciPin6, bottoneInserisciPin7, bottoneInserisciPin8, bottoneInserisciPin9, bottoneInserisciPin0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modifica_password);

        Bundle b = getIntent().getExtras();
        if (b != null){
            idAccount = b.getString(Costanti.Messaggi.MODIFICA_PASSWORD_ID_ACCOUNT);
        }

        sitoWeb     = findViewById(R.id.sitoModificaPassword);
        username    = findViewById(R.id.usernameModificaPassword);
        password    = findViewById(R.id.passwordModificaPassword);
        appunti     = findViewById(R.id.appuntiModificaPassword);

        if(idAccount!=null){
            Account account = SaveUtility.findAccountById(this, idAccount);
            sitoWeb.setText(account.getServizio());
            username.setText(account.getUsername());
            password.setText(account.getPassword());
            appunti.setText(account.getAppunti());
        }

        // init biometric
        executor = ContextCompat.getMainExecutor(this);
        biometricPrompt = new BiometricPrompt(ModificaPasswordActivity.this,
                executor, new BiometricPrompt.AuthenticationCallback() {
            @Override
            public void onAuthenticationError(int errorCode, @NonNull CharSequence errString) {
                super.onAuthenticationError(errorCode, errString);

                if(11 == errorCode)
                    lanciaModalPin();
                else if(13 == errorCode)
                    lanciaModalPin();
                else
                    Toast.makeText(getApplicationContext(), "Errore autenticazione: " +errorCode + " - " + errString, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAuthenticationSucceeded(@NonNull BiometricPrompt.AuthenticationResult result) {
                super.onAuthenticationSucceeded(result);
                salvataggio(account);
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
    }

    public void modificaPassword(View view) {
        String messaggioErrore = Validator.validaNuovaPassword(sitoWeb, username, password);
        if(messaggioErrore != null){
            Toast.makeText(getApplicationContext(), messaggioErrore, Toast.LENGTH_SHORT).show();
            return;
        }
        account = new Account();
        account.setId(idAccount);
        account.setServizio(sitoWeb.getText().toString());
        account.setUsername(username.getText().toString());
        account.setPassword(password.getText().toString());
        account.setAppunti(appunti.getText().toString());

        biometricPrompt.authenticate(promptInfo);
    }

    public void lanciaModalPin(){
        // apro modal inserisci pin
        bottomSheetDialog = new BottomSheetDialog(ModificaPasswordActivity.this, R.style.BottomSheetDialogTheme);
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
                if(SaveUtility.findPin(ModificaPasswordActivity.this).equals(pin)){
                    salvataggio(account);
                    bottomSheetDialog.dismiss();
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

    private void salvataggio(Account account){
        List<Account> listaAccount = SaveUtility.findAllAccount(this);
        List<Account> listaDaSalvare = new LinkedList<>();

        if (listaAccount == null)
            listaAccount = new LinkedList<>();

        for(Account a : listaAccount){
            if(!a.getId().equals(account.getId())){
                listaDaSalvare.add(a);
            }
        }
        listaDaSalvare.add(account);
        SaveUtility.salvaListaAccount(this, listaDaSalvare);
        Toast.makeText(getApplicationContext(), "Password modificata correttamente", Toast.LENGTH_SHORT).show();

        Intent intent = new Intent(ModificaPasswordActivity.this, ElencoPasswordActivity.class);
        startActivity(intent);
        finish();
    }
}