package com.example.tabuas.activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.tabuas.R;
import com.example.tabuas.adapter.RegistroAdapter;
import com.example.tabuas.helper.RecyclerItemClickListener;
import com.example.tabuas.helper.RegistroDAO;
import com.example.tabuas.model.Registro;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ListagemRegsTabuas extends AppCompatActivity {

    RecyclerView recyclerView;
    private RegistroAdapter adapter;

    private List<Registro> registros = new ArrayList<>();

    private Registro registroSelecionado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listagem_regs_tabuas);

        Objects.requireNonNull(getSupportActionBar()).hide();

        recyclerView = findViewById(R.id.recycler);

        recyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(getApplicationContext(), recyclerView, new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        Registro regSelecionado = registros.get(position);

                        Intent intent = new Intent(ListagemRegsTabuas.this,AddRegTabuas.class);
                        intent.putExtra("registro",regSelecionado);
                        startActivity(intent);
                    }

                    @Override
                    public void onLongItemClick(View view, int position) {
                        registroSelecionado = registros.get(position);

                        AlertDialog.Builder dialog = new AlertDialog.Builder(ListagemRegsTabuas.this);

                        dialog.setTitle("Confirmar exclusão");
                        dialog.setMessage("Deseja excluir a tarefa :"+ registroSelecionado.getId()+" ?");
                        dialog.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                RegistroDAO tarefaDAO = new RegistroDAO(getApplicationContext());

                                if (tarefaDAO.deletar(registroSelecionado)) {
                                    Toast.makeText(ListagemRegsTabuas.this, "TAREFA DELETADA", Toast.LENGTH_SHORT).show();
                                    carregaListaRecycler();
                                } else {
                                    Toast.makeText(ListagemRegsTabuas.this, "ERRO AO EXCLUIR TAREFA", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });

                        dialog.setNegativeButton("Não", null);

                        dialog.create();
                        dialog.show();

                    }

                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                    }
                }));

    }

    @Override
    protected void onStart() {
        super.onStart();

        carregaListaRecycler();
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