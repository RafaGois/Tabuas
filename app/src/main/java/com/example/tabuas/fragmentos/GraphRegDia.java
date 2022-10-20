package com.example.tabuas.fragmentos;

import android.app.DatePickerDialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.DatePicker;
import android.widget.EditText;

import com.example.tabuas.R;
import com.example.tabuas.helper.Colors;
import com.example.tabuas.helper.RegistroDAO;
import com.example.tabuas.helper.TiposCategorias;
import com.example.tabuas.model.Registro;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link GraphRegDia#newInstance} factory method to
 * create an instance of this fragment.
 */
public class GraphRegDia extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String dataSelecionada = "2022-01-05";
    private double totalMetroCubico;
    private double totalTabuas;
    private double totalToras;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public GraphRegDia() {
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
    public static GraphRegDia newInstance(String param1, String param2) {
        GraphRegDia fragment = new GraphRegDia();
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

        getDataAtual();

        agregaVals();

        data();

        listenerData();

        vals();
        agregaGrafico();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_graph_reg_dia, container, false);
    }

    private void agregaGrafico () {


        ArrayList<PieEntry> pieEntries = new ArrayList<>();

        for (int i = 1; i <= 3; i++) {
            PieEntry pieEntry = new PieEntry( (float) retornaSomaCategoria(i),retornaCategoria(i));
            pieEntries.add(pieEntry);
        }

        PieDataSet pieDataSet = new PieDataSet(pieEntries,"");
        pieDataSet.setColors(Colors.VERDE_PADRAO_COLORS);
        pieDataSet.setValueTextSize(16);

        PieChart pieChart = (PieChart) getView().findViewById(R.id.graficoRegDiario);

        pieChart.getDescription().setText("Total: "+ String.format("%.2f", ( totalTabuas + totalToras + totalMetroCubico )) );
        pieChart.getDescription().setTextSize(16);
        pieChart.getDescription().setTextColor(Color.GRAY);
        //pieChart.setUsePercentValues(true);

        pieChart.setData(new PieData(pieDataSet));

        pieChart.animateY(6000);

        pieChart.setDrawCenterText(true);
        pieChart.setCenterText("TOTAL PRODUZIDO POR MÊS");
        pieChart.setCenterTextSize(20);
        pieChart.setCenterTextColor(Color.GRAY);

        pieChart.setEntryLabelColor(Color.BLACK);
    }

    private void vals () {
        RegistroDAO registroDAO = new RegistroDAO(getContext());

        ArrayList<Registro> registros = (ArrayList<Registro>) registroDAO.listar();

        totalMetroCubico = 0;
        totalToras = 0;
        totalTabuas = 0;

        for (Registro reg : registros) {
            if (reg.getDateTime().equals(dataSelecionada)) {

                if (reg.getCategoria().equals(TiposCategorias.METRO_CUBICO.getValor())) {
                    totalMetroCubico += reg.getValor();
                } else if (reg.getCategoria().equals(TiposCategorias.TABUA.getValor())) {
                    totalTabuas += reg.getValor();
                } else if (reg.getCategoria().equals(TiposCategorias.TORA.getValor())) {
                    totalToras += reg.getValor();
                }

            }
        }
    }

    private double retornaSomaCategoria (int categ) {
            if (categ == 1) {
                return totalToras;
            } else if (categ == 2) {
                return totalMetroCubico;
            } else {
                return totalTabuas;
            }
    }

    private String retornaCategoria (int opcao) {

        if (opcao == 1) {
            return TiposCategorias.TORA.getValor();
        } else if (opcao == 2) {
            return TiposCategorias.METRO_CUBICO.getValor();
        } else {
            return TiposCategorias.TABUA.getValor();
        }
    }

    private void agregaVals () {
        RegistroDAO registroDAO = new RegistroDAO(getContext());

        ArrayList<Registro> registros = (ArrayList<Registro>) registroDAO.listar();

        totalMetroCubico = 0;
        totalTabuas = 0;
        totalToras = 0;

        for (Registro reg : registros) {
            if (reg.getDateTime().trim().equals(dataSelecionada)) {

                if (reg.getCategoria().equals(TiposCategorias.METRO_CUBICO.getValor())) {
                    totalMetroCubico += reg.getValor();
                } else if (reg.getCategoria().equals(TiposCategorias.TABUA.getValor())) {
                    totalTabuas += reg.getValor();
                } else if (reg.getCategoria().equals(TiposCategorias.TORA.getValor())) {
                    totalToras += reg.getValor();
                }
            }
        }
    }

    private void data () {
        EditText inputDate = getView().findViewById(R.id.inputDataBusca);

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
                        vals();
                        agregaGrafico();

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

    private void getDataAtual (){
        Date hoje = new Date();
        SimpleDateFormat df;
        df = new SimpleDateFormat("yyyy-MM-dd");
        dataSelecionada = df.format(hoje);
    }
}