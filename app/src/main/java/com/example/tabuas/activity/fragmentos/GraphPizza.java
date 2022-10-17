package com.example.tabuas.activity.fragmentos;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.tabuas.R;
import com.example.tabuas.helper.RegistroDAO;
import com.example.tabuas.helper.TiposCategorias;
import com.example.tabuas.model.Registro;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link GraphPizza#newInstance} factory method to
 * create an instance of this fragment.
 */
public class GraphPizza extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String dataSelecionada = "";
    private double totalMetroCubico;
    private double totalTabuas;
    private double totalToras;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public GraphPizza() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment GraphPizza.
     */
    // TODO: Rename and change types and number of parameters
    public static GraphPizza newInstance(String param1, String param2) {
        GraphPizza fragment = new GraphPizza();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public void onStart() {
        super.onStart();

        agregaVals();
        atribui();

        data();

        listenerData();

        showVals();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_graph_pizza, container, false);
    }

    private void agregaVals () {
        RegistroDAO registroDAO = new RegistroDAO(getContext());

        ArrayList<Registro> registros = (ArrayList<Registro>) registroDAO.listar();

        for (Registro reg : registros) {
            if (reg.getDateTime().trim().equals(dataSelecionada)) {

                if (reg.getCategoria().equals(TiposCategorias.METRO_CUBICO.getValor())) {
                    totalMetroCubico += reg.getValor();
                } else if (reg.getCategoria().equals(TiposCategorias.TABUA.getValor())) {
                    totalTabuas += reg.getValor();
                } else if (reg.getCategoria().equals(TiposCategorias.TORA.getValor())) {
                    totalToras += reg.getValor();
                }
            } else {
                totalMetroCubico = 0;
                totalTabuas = 0;
                totalToras = 0;
            }
        }
    }

    private void showVals () {
        if (totalToras != 0 || totalTabuas != 0 || totalMetroCubico != 0) {
            mostra();
        } else {
            oculta();
        }
    }

    private void mostra () {

        LinearLayout ll = getView().findViewById(R.id.tabelaValores);
        TextView txtDia = (TextView) getView().findViewById(R.id.txtDia);

        ll.setVisibility(View.VISIBLE);
        txtDia.setVisibility(View.VISIBLE);
    }

    private void oculta () {

        LinearLayout ll = getView().findViewById(R.id.tabelaValores);
        TextView txtDia = (TextView) getView().findViewById(R.id.txtDia);

        ll.setVisibility(View.GONE);
        txtDia.setVisibility(View.GONE);

        //todo tambem mostrar "nenhum valor encontrado"
    }

    private void atribui () {

        TextView txtDia = (TextView) getView().findViewById(R.id.txtDia);
        txtDia.setText("Total produzido no dia "+ dataSelecionada);


        TextView txtMCubico = (TextView) getView().findViewById(R.id.txtCubico);
        TextView txtTabuas = (TextView) getView().findViewById(R.id.txtTabua);
        TextView txtTora = (TextView) getView().findViewById(R.id.txtTora);

        txtMCubico.setText("R$ "+String.format("%.2f", totalMetroCubico));
        txtTabuas.setText("R$ "+String.format("%.2f", totalTabuas));
        txtTora.setText("R$ "+String.format("%.2f", totalToras));
    }

    private void data () {
        EditText inputDate = getView().findViewById(R.id.inputDataBusca);
        DatePickerDialog.OnDateSetListener setListener;

        Calendar calendar = Calendar.getInstance();
        final int ano = calendar.get(Calendar.YEAR);
        final int mes = calendar.get(Calendar.MONTH);
        final int dia = calendar.get(Calendar.DAY_OF_MONTH);

        inputDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        getContext(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                        month = month + 1;

                        String date = day + "/" + month + "/" + year;

                        String dataFormatada;
                        if(day < 10) {
                            if (month < 10) {
                                dataFormatada = year + "-0" + month + "-0" + day;
                            } else {
                                dataFormatada = year + "-" + month + "-0" + day;
                            }
                        } else {
                            if (month < 10) {
                                dataFormatada = year + "-0" + month + "-" + day;
                            } else {
                                dataFormatada = year + "-" + month + "-" + day;
                            }
                        }
                        inputDate.setText(dataFormatada);
                        dataSelecionada = dataFormatada;
                    }
                }, ano, mes, dia);
                datePickerDialog.show();
            }
        });
    }

    private void listenerData () {
        EditText inputDate = getView().findViewById(R.id.inputDataBusca);

        inputDate.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int keyCode, KeyEvent keyEvent) {
                if(keyCode == KeyEvent.KEYCODE_ENTER) {
                    if(!inputDate.getText().toString().equals("")) {

                        agregaVals();
                        atribui();
                        showVals();

                        InputMethodManager inputManager = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                        inputManager.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(),
                                InputMethodManager.HIDE_NOT_ALWAYS);
                    }
                    return true;
                }
                return false;
            }
        });


    }
}