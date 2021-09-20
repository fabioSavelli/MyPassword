package com.example.mypassword.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mypassword.R;
import com.example.mypassword.utils.Costanti;
import com.example.mypassword.utils.SaveUtility;
import com.example.mypassword.utils.Validator;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.textfield.TextInputLayout;

public class MenuActivity extends AppCompatActivity {

    private EditText campoRicerca;

    private int turno = 1;
    private String pin = "";
    private String confermaPin = "";
    private int position = 0;

    private TextView istruzioniModificaPin;
    private ImageView pinImage1, pinImage2, pinImage3, pinImage4;

    private Button bottoneModificaPin1, bottoneModificaPin2, bottoneModificaPin3, bottoneModificaPin4, bottoneModificaPin5, bottoneModificaPin6,
            bottoneModificaPin7, bottoneModificaPin8, bottoneModificaPin9, bottoneModificaPin0;
    private ImageButton bottoneCancellaModificaPin, bottoneConfermaModificaPin;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
    }

    public void passwordSalvate(View view) {
        Intent intent = new Intent(this, ElencoPasswordActivity.class);
        startActivity(intent);
    }

    public void cercaPassword(View view) {
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(MenuActivity.this, R.style.BottomSheetDialogTheme);
        View bottomSheetView = LayoutInflater.from(getApplicationContext())
                .inflate(R.layout.layout_cerca_bottom_modal, (LinearLayout)findViewById(R.id.cercaBottomContainer));

        campoRicerca = bottomSheetView.findViewById(R.id.campoRicerca);
        campoRicerca.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    if(Validator.validaCampoRicerca(campoRicerca))
                        Toast.makeText(getApplicationContext(), "Inserire un testo valido nel campo di ricerca", Toast.LENGTH_SHORT).show();
                    else{
                        bottomSheetDialog.dismiss();
                        startRicerca(campoRicerca.getText().toString());
                    }
                    return true;
                }
                return false;
            }
        });

        bottomSheetView.findViewById(R.id.buttonCercaModal).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Validator.validaCampoRicerca(campoRicerca))
                    Toast.makeText(getApplicationContext(), "Inserire un testo valido nel campo di ricerca", Toast.LENGTH_SHORT).show();
                else{
                    bottomSheetDialog.dismiss();
                    startRicerca(campoRicerca.getText().toString());
                }
            }
        });

        bottomSheetDialog.setContentView(bottomSheetView);
        bottomSheetDialog.show();
    }

    public void nuovaPassword(View view) {
        Intent intent = new Intent(this, NuovaPasswordActivity.class);
        startActivity(intent);
    }

    public void info(View view) {
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(MenuActivity.this, R.style.BottomSheetDialogTheme);
        View bottomSheetView = LayoutInflater.from(getApplicationContext())
                .inflate(R.layout.layout_info_bottom_modal, (LinearLayout)findViewById(R.id.infoBottomContainer));

        bottomSheetView.findViewById(R.id.buttonCloseInfo).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomSheetDialog.dismiss();
            }
        });

        bottomSheetView.findViewById(R.id.buttonEditPinInfo).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomSheetDialog.dismiss();

                // apro modal modifica pin
                BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(MenuActivity.this, R.style.BottomSheetDialogTheme);
                View bottomSheetView = LayoutInflater.from(getApplicationContext())
                        .inflate(R.layout.layout_modifica_pin_bottom_modal, (LinearLayout)findViewById(R.id.modificaPinBottomContainer));

                istruzioniModificaPin = bottomSheetView.findViewById(R.id.istruzioniModificaPin);;
                pinImage1 = bottomSheetView.findViewById(R.id.pinImageModifica1);
                pinImage2 = bottomSheetView.findViewById(R.id.pinImageModifica2);
                pinImage3 = bottomSheetView.findViewById(R.id.pinImageModifica3);
                pinImage4 = bottomSheetView.findViewById(R.id.pinImageModifica4);

                bottoneModificaPin1 = bottomSheetView.findViewById(R.id.bottoneModificaPin1);
                bottoneModificaPin2 = bottomSheetView.findViewById(R.id.bottoneModificaPin2);
                bottoneModificaPin3 = bottomSheetView.findViewById(R.id.bottoneModificaPin3);
                bottoneModificaPin4 = bottomSheetView.findViewById(R.id.bottoneModificaPin4);
                bottoneModificaPin5 = bottomSheetView.findViewById(R.id.bottoneModificaPin5);
                bottoneModificaPin6 = bottomSheetView.findViewById(R.id.bottoneModificaPin6);
                bottoneModificaPin7 = bottomSheetView.findViewById(R.id.bottoneModificaPin7);
                bottoneModificaPin8 = bottomSheetView.findViewById(R.id.bottoneModificaPin8);
                bottoneModificaPin9 = bottomSheetView.findViewById(R.id.bottoneModificaPin9);
                bottoneModificaPin0 = bottomSheetView.findViewById(R.id.bottoneModificaPin0);
                bottoneCancellaModificaPin = bottomSheetView.findViewById(R.id.bottoneCancellaModificaPin);
                bottoneConfermaModificaPin = bottomSheetView.findViewById(R.id.bottoneConfermaModificaPin);

                azzeraVariabili();

                bottoneModificaPin1.setOnClickListener(new View.OnClickListener() {
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

                bottoneModificaPin2.setOnClickListener(new View.OnClickListener() {
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

                bottoneModificaPin3.setOnClickListener(new View.OnClickListener() {
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

                bottoneModificaPin4.setOnClickListener(new View.OnClickListener() {
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

                bottoneModificaPin5.setOnClickListener(new View.OnClickListener() {
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

                bottoneModificaPin6.setOnClickListener(new View.OnClickListener() {
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

                bottoneModificaPin7.setOnClickListener(new View.OnClickListener() {
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

                bottoneModificaPin8.setOnClickListener(new View.OnClickListener() {
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

                bottoneModificaPin9.setOnClickListener(new View.OnClickListener() {
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

                bottoneModificaPin0.setOnClickListener(new View.OnClickListener() {
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

                bottoneConfermaModificaPin.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(turno == 1){
                            if(SaveUtility.findPin(MenuActivity.this).equals(pin)){
                                turno = 2;
                                azzeraVariabili();
                            }
                            else{
                                Toast.makeText(getApplicationContext(), "Il PIN inserito non è corretto", Toast.LENGTH_SHORT).show();
                            }
                        }
                        else if(turno == 2){
                            turno = 3;
                            confermaPin = pin;
                            azzeraVariabili();
                        }
                        else if(confermaPin.equals(pin)){
                            Toast.makeText(getApplicationContext(), "PIN modificato correttamente", Toast.LENGTH_SHORT).show();
                            SaveUtility.salvaPin(MenuActivity.this, pin);
                            bottomSheetDialog.dismiss();

                            Intent intent = new Intent(MenuActivity.this, MenuActivity.class);
                            startActivity(intent);
                            finish();
                        }
                        else{
                            Toast.makeText(getApplicationContext(), "I PIN inseriti non coincidono", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

                bottoneCancellaModificaPin.setOnClickListener(new View.OnClickListener() {
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
        });

        bottomSheetDialog.setContentView(bottomSheetView);
        bottomSheetDialog.show();
    }

    private void startRicerca(String campoRicerca){
        Intent intent = new Intent(this, ElencoPasswordActivity.class);
        intent.putExtra(Costanti.Messaggi.ELENCO_PASSWORD_RICERCA, campoRicerca);
        startActivity(intent);
    }

    private void azzeraVariabili(){
        if(turno == 1)
            istruzioniModificaPin.setText("Inserisci il codice PIN");
        else if(turno == 2)
            istruzioniModificaPin.setText("Inserisci il nuovo codice pin");
        else
            istruzioniModificaPin.setText("Conferma il nuovo codice pin");

        pin = "";
        position = 0;

        pinImage1.setImageResource(R.drawable.pin_empty);
        pinImage2.setImageResource(R.drawable.pin_empty);
        pinImage3.setImageResource(R.drawable.pin_empty);
        pinImage4.setImageResource(R.drawable.pin_empty);

        abilitaDisabilitaConferma();
    }

    private void abilitaDisabilitaConferma(){
        if(position!=4){
            bottoneConfermaModificaPin.setEnabled(false);
            bottoneConfermaModificaPin.setImageResource(R.drawable.ic_baseline_check_grey_24);

            bottoneModificaPin1.setTextColor(ContextCompat.getColor(this, R.color.my_light_blue));
            bottoneModificaPin2.setTextColor(ContextCompat.getColor(this, R.color.my_light_blue));
            bottoneModificaPin3.setTextColor(ContextCompat.getColor(this, R.color.my_light_blue));
            bottoneModificaPin4.setTextColor(ContextCompat.getColor(this, R.color.my_light_blue));
            bottoneModificaPin5.setTextColor(ContextCompat.getColor(this, R.color.my_light_blue));
            bottoneModificaPin6.setTextColor(ContextCompat.getColor(this, R.color.my_light_blue));
            bottoneModificaPin7.setTextColor(ContextCompat.getColor(this, R.color.my_light_blue));
            bottoneModificaPin8.setTextColor(ContextCompat.getColor(this, R.color.my_light_blue));
            bottoneModificaPin9.setTextColor(ContextCompat.getColor(this, R.color.my_light_blue));
            bottoneModificaPin0.setTextColor(ContextCompat.getColor(this, R.color.my_light_blue));
        }
        else{
            bottoneConfermaModificaPin.setEnabled(true);
            bottoneConfermaModificaPin.setImageResource(R.drawable.ic_baseline_check_green_24);

            bottoneModificaPin1.setTextColor(ContextCompat.getColor(this, R.color.edit_text_hint_grey));
            bottoneModificaPin2.setTextColor(ContextCompat.getColor(this, R.color.edit_text_hint_grey));
            bottoneModificaPin3.setTextColor(ContextCompat.getColor(this, R.color.edit_text_hint_grey));
            bottoneModificaPin4.setTextColor(ContextCompat.getColor(this, R.color.edit_text_hint_grey));
            bottoneModificaPin5.setTextColor(ContextCompat.getColor(this, R.color.edit_text_hint_grey));
            bottoneModificaPin6.setTextColor(ContextCompat.getColor(this, R.color.edit_text_hint_grey));
            bottoneModificaPin7.setTextColor(ContextCompat.getColor(this, R.color.edit_text_hint_grey));
            bottoneModificaPin8.setTextColor(ContextCompat.getColor(this, R.color.edit_text_hint_grey));
            bottoneModificaPin9.setTextColor(ContextCompat.getColor(this, R.color.edit_text_hint_grey));
            bottoneModificaPin0.setTextColor(ContextCompat.getColor(this, R.color.edit_text_hint_grey));
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
}