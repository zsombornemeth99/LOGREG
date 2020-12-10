package com.example.logreg;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class RegisterActivity extends AppCompatActivity {

    Button btnReg, btnVissza;
    EditText etEmail, etFelhn, etJelszo, etTeljn;
    DbHelper adatbazis;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        init();

        onClickListeners();
    }

    private void onClickListeners() {
        btnReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                regisztral();
            }
        });
        btnVissza.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent vissza = new Intent(RegisterActivity.this, MainActivity.class);
                startActivity(vissza);
                finish();
            }
        });
    }

    private void regisztral() {
        String emial = etEmail.getText().toString().trim();
        String felh = etFelhn.getText().toString().trim();
        String jelszo = etJelszo.getText().toString().trim();
        String teljn = etTeljn.getText().toString().trim();
        if (emial.isEmpty()) {
            Toast.makeText(this, "Email megadása kötelező", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!emial.contains("@") || !emial.contains(".")) {
            Toast.makeText(this, "Hibás email formátum", Toast.LENGTH_SHORT).show();
            return;
        }
        if (felh.isEmpty()) {
            Toast.makeText(this, "Felhasználónév megadása kötelező", Toast.LENGTH_SHORT).show();
            return;
        }
        if (jelszo.isEmpty()) {
            Toast.makeText(this, "Jelszo megadása kötelező", Toast.LENGTH_SHORT).show();
            return;
        }
        if (teljn.isEmpty()) {
            Toast.makeText(this, "Teljes név megadása kötelező", Toast.LENGTH_SHORT).show();
            return;
        }
        String[] st = teljn.split(" ");
        if (st.length < 2) {
            Toast.makeText(this, "Teljes név minimum 2 szóból állhat", Toast.LENGTH_SHORT).show();
            return;
        }
        boolean helyes = false;
        for (String item : st) {
            if(Character.isUpperCase(item.charAt(0))){
                helyes = true;
            }
            else {
                helyes =false;
            }
        }
        if (!helyes) {
            Toast.makeText(this, "A névnek nagybetűvel kell kezdődnie", Toast.LENGTH_SHORT).show();
            return;
        }

        boolean sikeres = adatbazis.adatRogzites(emial, felh, jelszo, teljn);
        if (sikeres) {
            Toast.makeText(this, "Sikeres regisztráció", Toast.LENGTH_SHORT).show();
            Intent vissza = new Intent(RegisterActivity.this, MainActivity.class);
            startActivity(vissza);
            finish();
        } else {
            Toast.makeText(this, "Sikertelen regisztráció", Toast.LENGTH_SHORT).show();
        }
    }

    private void init() {
        btnReg = findViewById(R.id.btn_regisztracioReg);
        btnVissza = findViewById(R.id.btn_visszaReg);
        etFelhn = findViewById(R.id.et_felhReg);
        etJelszo = findViewById(R.id.et_jelszoReg);
        etEmail = findViewById(R.id.et_emialReg);
        etTeljn = findViewById(R.id.et_teljNevReg);
        adatbazis = new DbHelper(RegisterActivity.this);
    }
}