package com.example.tabuas.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.tabuas.R;

public class ListagemRegsTabuas extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listagem_regs_tabuas);
    }

    public void navegaAdicao (View view) {
        Intent intent =  new Intent(this,ListagemRegsTabuas.class);
        startActivity(intent);
    }

    public void volta (View view) {
        finish();
    }
}