package com.example.tabuas.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.tabuas.R;

import java.util.Objects;

public class ListagemRegsTabuas extends AppCompatActivity {

    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listagem_regs_tabuas);

        Objects.requireNonNull(getSupportActionBar()).hide();

        recyclerView = findViewById(R.id.recycler);
    }

    @Override
    protected void onStart() {
        super.onStart();


    }

    public void navegaAdicao (View view) {
        Intent intent =  new Intent(this,ListagemRegsTabuas.class);
        startActivity(intent);
    }

    public void volta (View view) {
        finish();
    }
}