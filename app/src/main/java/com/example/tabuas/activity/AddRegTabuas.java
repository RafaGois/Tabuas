package com.example.tabuas.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.tabuas.R;
import com.example.tabuas.helper.RegistroDAO;
import com.example.tabuas.model.Registro;

public class AddRegTabuas extends AppCompatActivity {

    private String [] arr1 = {"Tábua","Ripa"};
    private String [] arr2 = {"Diurno","Noturno"};

    private Registro registroAtual;

    Spinner spinnerCategoria,spinnerTurno;
    EditText inputValor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_reg_tabuas);

        inputValor = findViewById(R.id.inputValor);
        spinnerCategoria = findViewById(R.id.spinnerCategoria);
        spinnerTurno = findViewById(R.id.spinnerTurno);

        registroAtual = (Registro) getIntent().getSerializableExtra("registro");

        adicionaSpinners();

    }

    public void volta (View view) {
        finish();
    }

    public void adiciona (View view) {

        RegistroDAO dao = new RegistroDAO(getApplicationContext());

        String data = "08/10/2022";
        String categoria = spinnerCategoria.getSelectedItem().toString();
        double valor = Double.parseDouble(inputValor.getText().toString());
        String turno = spinnerTurno.getSelectedItem().toString();

        Registro registro = new Registro();
        registro.setDateTime(data);
        registro.setCategoria(categoria);
        registro.setValor(valor);
        registro.setTurno(turno);

        if (registroAtual != null) {

            if (dao.atualizar(registro)) {
                Toast.makeText(this, "Valor atualizado com sucesso", Toast.LENGTH_SHORT).show();
                finish();
            } else {
                Toast.makeText(this, "Erro", Toast.LENGTH_SHORT).show();
            }
        } else {

            if (data.isEmpty()) {
                Registro registro1 = new Registro();


            }
        }

    }

    public void adicionaSpinners () {
        ArrayAdapter<String> adapterSpinner = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, arr1);
        spinnerCategoria.setAdapter(adapterSpinner);

        ArrayAdapter<String> adapterSpinner2 = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, arr2);
        spinnerTurno.setAdapter(adapterSpinner2);
    }

    public void salva (View view) {
        if (inputValor.getText().toString().isEmpty()) {
            Toast.makeText(this, "Informe todos os valores", Toast.LENGTH_SHORT).show();
        }
    }
}