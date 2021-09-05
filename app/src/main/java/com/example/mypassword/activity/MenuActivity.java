package com.example.mypassword.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.mypassword.R;

public class MenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
    }

    public void passwordSalvate(View view) {
        Toast.makeText(getApplicationContext(), "password salvate", Toast.LENGTH_SHORT).show();
    }

    public void cercaPassword(View view) {
        Toast.makeText(getApplicationContext(), "cerca password", Toast.LENGTH_SHORT).show();
    }

    public void nuovaPassword(View view) {
        Toast.makeText(getApplicationContext(), "nuova password", Toast.LENGTH_SHORT).show();
    }

    public void info(View view) {
        Toast.makeText(getApplicationContext(), "info", Toast.LENGTH_SHORT).show();
    }

}