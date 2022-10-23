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

import com.example.tabuas.model.Registro;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link GraphBarra#newInstance} factory method to
 * create an instance of this fragment.
 */
public class GraphBarra extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private String anoSelecioado;

    public GraphBarra() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment GraphBarra.
     */
    // TODO: Rename and change types and number of parameters
    public static GraphBarra newInstance(String param1, String param2) {
        GraphBarra fragment = new GraphBarra();
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

        getAnoAtual();

        agregaSpinner();

        agregaGrafico();

        listenerSpinner();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_graph_barra, container, false);
    }

    private void agregaGrafico () {

        ArrayList<BarEntry> barEntries = new ArrayList<>();

        for (int i = 0; i < valoresAno().size(); i ++) {
            //todo colocar dia no lugar do i
            BarEntry barEntry = new BarEntry(i , (float) valoresAno().get(i).getValor(),valoresAno().get(i).getCategoria());
            barEntries.add(barEntry);
        }

        BarDataSet barDataSet = new BarDataSet(barEntries,"Valores");
        barDataSet.setColors(Colors.VERDE_PADRAO_COLORS);

        barDataSet.setValueTextSize(14);

        BarChart barChart = (BarChart) getView().findViewById(R.id.graficoBarras);

        barChart.setData(new BarData(barDataSet));

        barChart.animateY(6000);

        barChart.getDescription().setText("Descricao");
        barChart.getDescription().setTextColor(Color.BLUE);
    }

    private List<Registro> valoresAno () {
        RegistroDAO registroDAO = new RegistroDAO(getContext());

        List<Registro> registros = registroDAO.listar();
        List<Registro> registrosAno = new ArrayList<>();

        for (Registro reg : registros) {
            if (cortaAno(reg.getDateTime()).equals(anoSelecioado)) {
                registrosAno.add(reg);
            }
        }
        return registrosAno;
    }

    private List<String> retornaAnos () {
        RegistroDAO dao = new RegistroDAO(getContext());

        List<Registro> registros = dao.listar();
        List<String> anos = new ArrayList<>();

        for (Registro reg : registros) {
            if (!anos.contains(cortaAno(reg.getDateTime()))) {
                anos.add(cortaAno(reg.getDateTime()));
            }
        }
        return anos;
    }

    private String cortaAno (String input) {
        String ano;
        if (input.length() >= 4) {
            ano = input.substring(0,4);
            return ano;
        } else {
            return "";
        }
    }

    private void getAnoAtual (){
        Date hoje = new Date();
        SimpleDateFormat df;
        df = new SimpleDateFormat("yyyy");
        anoSelecioado = df.format(hoje);
    }

    private void agregaSpinner () {
        Spinner spinner = getView().findViewById(R.id.spinner2);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, retornaAnos());
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }

    private void listenerSpinner () {
        Spinner spinner = getView().findViewById(R.id.spinner2);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                anoSelecioado = spinner.getSelectedItem().toString();

                agregaGrafico();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }
}