package com.example.sam_tp1_seminario2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class WinActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_win);
    }

    public void NuevoJuego(View v) {
        Intent i = new Intent(WinActivity.this, NewGameActivity.class);
        startActivity(i);
        finish();
    }

    public void Salir(View v) {
        finish();
    }
}
