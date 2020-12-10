package com.example.logreg;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    Button btnBejelentkezes, btnRegisztracio;
    EditText etFelhn, etJelszo;
    DbHelper adatbazis;
    SharedPreferences sharedPref;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();

        onClickListeners();
    }

    private void onClickListeners() {
        btnBejelentkezes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bejelentkezes();
            }
        });
        btnRegisztracio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent reg = new Intent(MainActivity.this, RegisterActivity.class);
                startActivity(reg);
                finish();
            }
        });
    }

    private void bejelentkezes() {
        String felh = etFelhn.getText().toString().trim();
        String jelszo = etJelszo.getText().toString().trim();
        if (felh.isEmpty()) {
            Toast.makeText(this, "A felhasználó megadása kötelező", Toast.LENGTH_SHORT).show();
            return;
        }
        if (jelszo.isEmpty()) {
            Toast.makeText(this, "A jelszo megadása kötelező", Toast.LENGTH_SHORT).show();
            return;
        }
        Cursor adatok = adatbazis.bejelentkezes(felh, jelszo);
        StringBuilder sb = new StringBuilder();
        while (adatok.moveToNext()) {
            sb.append(adatok.getString(2)).append(" ");
        }
        String nev = sb.toString();
        editor.putString("neved", nev);
        editor.commit();

        if (adatok == null) {
            Toast.makeText(this, "Adatbázis hiba", Toast.LENGTH_SHORT).show();
            return;
        }
        if (adatok.getCount() == 0) {
            Toast.makeText(this, "Sikertelen bejelentkezes", Toast.LENGTH_SHORT).show();
        }
        else if (adatok.getCount() == 1) {
            Toast.makeText(this, "Sikeres bejelentkezes", Toast.LENGTH_SHORT).show();
            Intent masikActivityre = new Intent(MainActivity.this, LoggedInActivity.class);
            startActivity(masikActivityre);
            finish();
        }
        else {
            Toast.makeText(this, "Sikertelen bejelentkezes", Toast.LENGTH_SHORT).show();
        }
    }

    private void init() {
        btnBejelentkezes = findViewById(R.id.btn_bejelentkezesMain);
        btnRegisztracio = findViewById(R.id.btn_regisztracioMain);
        etFelhn = findViewById(R.id.et_felhMain);
        etJelszo = findViewById(R.id.et_jelszoMain);
        adatbazis = new DbHelper(MainActivity.this);
        sharedPref = getSharedPreferences("adatok", Context.MODE_PRIVATE);
        editor = sharedPref.edit();
    }
}