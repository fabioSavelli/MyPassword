package com.example.mypassword.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mypassword.R;
import com.example.mypassword.utils.SaveUtility;

public class NuovoPinActivity extends AppCompatActivity {

    private boolean primaVolta = true;
    private String pin;
    private String confermaPin;
    private int position;

    private TextView istruzioniNuovoPin;
    private ImageView pinImage1, pinImage2, pinImage3, pinImage4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nuovo_pin);

        String pin = SaveUtility.findPin(this);
        if(pin!=null && !pin.trim().isEmpty()) {
            openMenu();
        }

        istruzioniNuovoPin = findViewById(R.id.istruzioniNuovoPin);
        pinImage1 = findViewById(R.id.pinImage1);
        pinImage2 = findViewById(R.id.pinImage2);
        pinImage3 = findViewById(R.id.pinImage3);
        pinImage4 = findViewById(R.id.pinImage4);

        azzeraVariabili();
    }

    private void azzeraVariabili(){
        if(primaVolta)
            istruzioniNuovoPin.setText("Crea il codice pin");
        else
            istruzioniNuovoPin.setText("Conferma il codice pin");
        pin = "";
        position = 0;

        pinImage1.setImageResource(R.drawable.pin_empty);
        pinImage2.setImageResource(R.drawable.pin_empty);
        pinImage3.setImageResource(R.drawable.pin_empty);
        pinImage4.setImageResource(R.drawable.pin_empty);

        abilitaDisabilitaConferma();
    }

    private void openMenu(){
        Intent intent = new Intent(this, MenuActivity.class);
        startActivity(intent);
        finish();
    }

    public void add1(View view) {
        if(position>=4)
            return;
        position++;
        pin += "1";
        inserisciColorePin();
        abilitaDisabilitaConferma();
    }

    public void add2(View view) {
        if(position>=4)
            return;
        position++;
        pin += "2";
        inserisciColorePin();
        abilitaDisabilitaConferma();
    }

    public void add3(View view) {
        if(position>=4)
            return;
        position++;
        pin += "3";
        inserisciColorePin();
        abilitaDisabilitaConferma();
    }

    public void add4(View view) {
        if(position>=4)
            return;
        position++;
        pin += "4";
        inserisciColorePin();
        abilitaDisabilitaConferma();
    }

    public void add5(View view) {
        if(position>=4)
            return;
        position++;
        pin += "5";
        inserisciColorePin();
        abilitaDisabilitaConferma();
    }

    public void add6(View view) {
        if(position>=4)
            return;
        position++;
        pin += "6";
        inserisciColorePin();
        abilitaDisabilitaConferma();
    }

    public void add7(View view) {
        if(position>=4)
            return;
        position++;
        pin += "7";
        inserisciColorePin();
        abilitaDisabilitaConferma();
    }

    public void add8(View view) {
        if(position>=4)
            return;
        position++;
        pin += "8";
        inserisciColorePin();
        abilitaDisabilitaConferma();
    }

    public void add9(View view) {
        if(position>=4)
            return;
        position++;
        pin += "9";
        inserisciColorePin();
        abilitaDisabilitaConferma();
    }

    public void add0(View view) {
        if(position>=4)
            return;
        position++;
        pin += "0";
        inserisciColorePin();
        abilitaDisabilitaConferma();
    }

    public void conferma(View view) {
        if(primaVolta){
            primaVolta = false;
            confermaPin = pin;
            azzeraVariabili();
        }
        else if(confermaPin.equals(pin)){
            Toast.makeText(getApplicationContext(), "PIN salvato correttamente", Toast.LENGTH_SHORT).show();
            SaveUtility.salvaPin(this, pin);

            Intent intent = new Intent(this, MenuActivity.class);
            startActivity(intent);
            finish();
        }
        else{
            Toast.makeText(getApplicationContext(), "I PIN inseriti non coincidono", Toast.LENGTH_SHORT).show();
        }
    }

    public void cancella(View view) {
        if(position<=0)
            return;
        position--;
        pin = pin.substring(0, pin.length() - 1);
        rimuoviColorePin();
        abilitaDisabilitaConferma();
    }

    private void abilitaDisabilitaConferma(){
        ImageButton bottoneConferma = findViewById(R.id.bottoneConfermaPin);
        Button bottone1 = (Button) findViewById(R.id.bottonePin1);
        Button bottone2 = (Button) findViewById(R.id.bottonePin2);
        Button bottone3 = (Button) findViewById(R.id.bottonePin3);
        Button bottone4 = (Button) findViewById(R.id.bottonePin4);
        Button bottone5 = (Button) findViewById(R.id.bottonePin5);
        Button bottone6 = (Button) findViewById(R.id.bottonePin6);
        Button bottone7 = (Button) findViewById(R.id.bottonePin7);
        Button bottone8 = (Button) findViewById(R.id.bottonePin8);
        Button bottone9 = (Button) findViewById(R.id.bottonePin9);
        Button bottone0 = (Button) findViewById(R.id.bottonePin0);

        if(position!=4){
            findViewById(R.id.bottoneConfermaPin).setEnabled(false);
            bottoneConferma.setImageResource(R.drawable.ic_baseline_check_grey_24);

            bottone1.setTextColor(ContextCompat.getColor(this, R.color.my_light_blue));
            bottone2.setTextColor(ContextCompat.getColor(this, R.color.my_light_blue));
            bottone3.setTextColor(ContextCompat.getColor(this, R.color.my_light_blue));
            bottone4.setTextColor(ContextCompat.getColor(this, R.color.my_light_blue));
            bottone5.setTextColor(ContextCompat.getColor(this, R.color.my_light_blue));
            bottone6.setTextColor(ContextCompat.getColor(this, R.color.my_light_blue));
            bottone7.setTextColor(ContextCompat.getColor(this, R.color.my_light_blue));
            bottone8.setTextColor(ContextCompat.getColor(this, R.color.my_light_blue));
            bottone9.setTextColor(ContextCompat.getColor(this, R.color.my_light_blue));
            bottone0.setTextColor(ContextCompat.getColor(this, R.color.my_light_blue));
        }
        else{
            bottoneConferma.setEnabled(true);
            bottoneConferma.setImageResource(R.drawable.ic_baseline_check_green_24);

            bottone1.setTextColor(ContextCompat.getColor(this, R.color.edit_text_hint_grey));
            bottone2.setTextColor(ContextCompat.getColor(this, R.color.edit_text_hint_grey));
            bottone3.setTextColor(ContextCompat.getColor(this, R.color.edit_text_hint_grey));
            bottone4.setTextColor(ContextCompat.getColor(this, R.color.edit_text_hint_grey));
            bottone5.setTextColor(ContextCompat.getColor(this, R.color.edit_text_hint_grey));
            bottone6.setTextColor(ContextCompat.getColor(this, R.color.edit_text_hint_grey));
            bottone7.setTextColor(ContextCompat.getColor(this, R.color.edit_text_hint_grey));
            bottone8.setTextColor(ContextCompat.getColor(this, R.color.edit_text_hint_grey));
            bottone9.setTextColor(ContextCompat.getColor(this, R.color.edit_text_hint_grey));
            bottone0.setTextColor(ContextCompat.getColor(this, R.color.edit_text_hint_grey));
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