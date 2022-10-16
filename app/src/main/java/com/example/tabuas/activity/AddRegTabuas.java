package com.example.tabuas.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.tabuas.R;
import com.example.tabuas.helper.RegistroDAO;
import com.example.tabuas.helper.TiposCategorias;
import com.example.tabuas.model.Registro;

import java.util.Calendar;

public class AddRegTabuas extends AppCompatActivity {

    private String [] arr1 = {TiposCategorias.TORA.getValor(),TiposCategorias.METRO_CUBICO.getValor(),TiposCategorias.TABUA.getValor()};
    private String [] arr2 = {"1","2"};

    private Registro registroAtual;

    Spinner spinnerCategoria,spinnerTurno;
    EditText inputValor;
    EditText inputData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_reg_tabuas);

        inputValor = findViewById(R.id.inputValor);
        inputData = findViewById(R.id.inputData);
        spinnerCategoria = findViewById(R.id.spinnerCategoria);
        spinnerTurno = findViewById(R.id.spinnerTurno);

        registroAtual = (Registro) getIntent().getSerializableExtra("registro");

        adicionaSpinners();

        if (registroAtual != null) {
            spinnerCategoria.setSelection(retornaIndexBusca(arr1,registroAtual.getCategoria()));
            inputValor.setText(String.valueOf(registroAtual.getValor()));
            spinnerTurno.setSelection(retornaIndexBusca(arr2,registroAtual.getTurno()));
        }

        data();
    }

    private int retornaIndexBusca (String [] arr, String busca) {

        int contador = 0;

        for (String s : arr) {
            if (s.equals(busca)) {
                return contador;
            }
            contador++;
        }
        return 0;
    }

    public void volta (View view) {
        finish();
    }

    public void adiciona (View view) {



        if (!inputValor.getText().toString().isEmpty() && !inputData.getText().toString().isEmpty()) {

            RegistroDAO dao = new RegistroDAO(getApplicationContext());

            String data =  inputData.getText().toString();
            String categoria = spinnerCategoria.getSelectedItem().toString();
            double valor = Double.parseDouble(inputValor.getText().toString());
            String turno = spinnerTurno.getSelectedItem().toString();

            if (registroAtual != null) {
                if (!categoria.isEmpty()) {

                    Registro registro = new Registro();
                    registro.setId(registroAtual.getId());
                    registro.setDateTime(data);
                    registro.setCategoria(categoria);
                    registro.setValor(valor);
                    registro.setTurno(turno);

                    if (dao.atualizar(registro)) {
                        Toast.makeText(this, "Valor atualizado com sucesso", Toast.LENGTH_SHORT).show();
                        finish();
                    } else {
                        Toast.makeText(this, "Erro", Toast.LENGTH_SHORT).show();
                    }
                }

            } else {

                if (!data.isEmpty()) {
                    Registro registro = new Registro();

                    registro.setDateTime(data);
                    registro.setCategoria(categoria);
                    registro.setValor(valor);
                    registro.setTurno(turno);

                    if (dao.salvar(registro)) {
                        Toast.makeText(this, "Registro salvo", Toast.LENGTH_SHORT).show();
                        finish();
                    } else {
                        Toast.makeText(this, "Erro", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        } else {
            Toast.makeText(this, "Informe todos os campos", Toast.LENGTH_SHORT).show();
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

    private void data () {
        EditText inputData = findViewById(R.id.inputData);
        DatePickerDialog.OnDateSetListener setListener;

        Calendar calendar = Calendar.getInstance();
        final int ano = calendar.get(Calendar.YEAR);
        final int mes = calendar.get(Calendar.MONTH);
        final int dia = calendar.get(Calendar.DAY_OF_MONTH);

        inputData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(AddRegTabuas.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                        month = month + 1;

                        String dataFormatada;
                        if(day < 10) {
                            if (month <= 9) {
                                dataFormatada = day + "-0" + month + "-0" + year;
                            } else {
                                dataFormatada = day + "-" + month + "-0" + year;
                            }
                        } else {
                            if (month <= 9) {
                                dataFormatada = day + "-0" + month + "-" + year;
                            } else {
                                dataFormatada = day + "-" + month + "-" + year;
                            }
                        }
                        inputData.setText(dataFormatada);
                    }
                },ano,mes,dia);
                datePickerDialog.show();
            }
        });
    }
}