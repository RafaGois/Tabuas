package com.example.tabuas.fragmentos;

import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.tabuas.R;
import com.example.tabuas.helper.Colors;
import com.example.tabuas.helper.RegistroDAO;
import com.example.tabuas.helper.TiposCategorias;
import com.example.tabuas.model.Registro;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link GraphRegMes#newInstance} factory method to
 * create an instance of this fragment.
 */
public class GraphRegMes extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private String [] meses = {"Janeiro","Fevereiro","Março","Abril","Maio","Junho","Julho","Agosto","Setembro","Outubro","Novembro","Dezembro"};

    private String mesSelecionado = "01";
    private double totalMetroCubico;
    private double totalTabuas;
    private double totalToras;

    public GraphRegMes() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment GraphRegMes.
     */
    // TODO: Rename and change types and number of parameters
    public static GraphRegMes newInstance(String param1, String param2) {
        GraphRegMes fragment = new GraphRegMes();
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

        agregaSpinner();
        listenerSpinner();

        vals();
        agregaGraph();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_graph_reg_mes, container, false);
    }

    private void agregaGraph () {


        ArrayList<PieEntry> pieEntries = new ArrayList<>();

        for (int i = 1; i <= 3; i++) {
            PieEntry pieEntry = new PieEntry( (float) retornaSomaCategoria(i),retornaCategoria(i));
            pieEntries.add(pieEntry);
        }

        PieDataSet pieDataSet = new PieDataSet(pieEntries,"");
        pieDataSet.setColors(Colors.VERDE_VIBRANTE_COLORS);
        pieDataSet.setValueTextSize(16);

        PieChart pieChart = (PieChart) getView().findViewById(R.id.graficoRegMensal);

        pieChart.getDescription().setText("Total: "+ String.format("%.2f", ( totalTabuas + totalToras + totalMetroCubico )) );
        pieChart.getDescription().setTextSize(16);
        pieChart.getDescription().setTextColor(Color.GRAY);
        //pieChart.setUsePercentValues(true);

        pieChart.setData(new PieData(pieDataSet));

        pieChart.animateY(6000);

        pieChart.setDrawCenterText(true);
        pieChart.setCenterText("TOTAL PRODUZIDO POR DIA");
        pieChart.setCenterTextSize(20);
        pieChart.setCenterTextColor(Color.GRAY);

        pieChart.setEntryLabelColor(Color.BLACK);
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


    private void vals () {
        RegistroDAO registroDAO = new RegistroDAO(getContext());

        ArrayList<Registro> registros = (ArrayList<Registro>) registroDAO.listar();

        for (Registro reg : registros) {
            if (cortaData(reg.getDateTime()).equals(mesSelecionado)) {

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

    private void agregaSpinner () {
        Spinner spinner = getView().findViewById(R.id.spinnerMeses);
        ArrayAdapter<String> adapterSpinner = new ArrayAdapter<>(getContext(),android.R.layout.simple_list_item_1,meses);
        spinner.setAdapter(adapterSpinner);
    }

    private String cortaData (String data) {
        if (data != null) {
            return data.substring(5,7);
        } else {
            return "";
        }
    }

    private void listenerSpinner () {
        Spinner spinner = getView().findViewById(R.id.spinnerMeses);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                totalMetroCubico = 0;
                totalTabuas = 0;
                totalToras = 0;

                if ((i + 1) <= 9) {
                    mesSelecionado = "0" + (i + 1);
                } else {
                    mesSelecionado = Integer.toString(i + 1);
                }
                vals();
                agregaGraph();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }
}