package com.example.tabuas.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.example.tabuas.R;
import com.example.tabuas.adapter.RegistroAdapter;
import com.example.tabuas.helper.RegistroDAO;
import com.example.tabuas.model.Registro;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ListagemRegsTabuas extends AppCompatActivity {

    RecyclerView recyclerView;
    private RegistroAdapter adapter;

    private List<Registro> registros = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listagem_regs_tabuas);

        Objects.requireNonNull(getSupportActionBar()).hide();

        recyclerView = findViewById(R.id.recycler);

        carregaListaRecycler();
    }

    @Override
    protected void onStart() {
        super.onStart();


    }

    public void navegaAdicao (View view) {
        Intent intent =  new Intent(this,AddRegTabuas.class);

        startActivity(intent);
    }

    public void volta (View view) {
        finish();
    }

    private void carregaListaRecycler () {
        RegistroDAO dao = new RegistroDAO(getApplicationContext());

        registros = dao.listar();

        adapter =  new RegistroAdapter(registros);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.addItemDecoration(new DividerItemDecoration(getApplicationContext(), LinearLayout.VERTICAL));
        recyclerView.setAdapter(adapter);
    }
}