package com.example.logreg;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class LoggedInActivity extends AppCompatActivity {

    Button btnKijelentkezes;
    TextView tvNev;
    DbHelper adatbazis;
    SharedPreferences sharedPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logged_in);

        init();

        btnKijelentkezes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ki = new Intent(LoggedInActivity.this, MainActivity.class);
                startActivity(ki);
                finish();
            }
        });
        String nev = sharedPref.getString("neved","");
        tvNev.setText(nev);
    }

    private void init() {
        btnKijelentkezes = findViewById(R.id.btn_kijelentkezesLog);
        tvNev = findViewById(R.id.tv_nevLog);
        sharedPref = getSharedPreferences("adatok", Context.MODE_PRIVATE);
        adatbazis = new DbHelper(LoggedInActivity.this);
    }
}