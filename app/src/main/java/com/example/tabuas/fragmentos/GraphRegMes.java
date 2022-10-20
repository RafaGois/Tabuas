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
import android.widget.Toast;

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
import java.util.Date;
import java.util.List;

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

    private String mesSelecionado = "08";
    private String anoSelecionado = "2022";
    private double totalMetroCubico;
    private double totalTabuas;
    private double totalToras;
    private String dataBusca;

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

        getAnoAtual();
        agregaSpinner();

        agregaSpinnerAno();

        listenerSpinner();
        listenerSpinnerAno();

        vals();
        agregaGraph();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_graph_reg_mes, container, false);
    }

    private void getAnoAtual (){
        Date hoje = new Date();
        SimpleDateFormat df;
        df = new SimpleDateFormat("yyyy-MM");
        dataBusca = df.format(hoje);
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

        pieChart.animateY(3600);

        pieChart.setDrawCenterText(true);
        pieChart.setCenterText("TOTAL PRODUZIDO POR MÊS");
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
            if (cortaData(reg.getDateTime()).equals(dataBusca)) {

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

        spinner.setSelection(Integer.parseInt(mesSelecionado) + 1);
    }

    private String cortaData (String data) {
        if (data != null && data.length() >= 7) {
            return data.substring(0,7);
        } else {
            return "";
        }
    }

    private void agregaSpinnerAno () {
        Spinner spinner = getView().findViewById(R.id.spinnerAnoMes);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, retornaAnos());
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        //todo futuramente fazer ano maleavel que nem data
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

    private void listenerSpinner () {
        Spinner spinner = getView().findViewById(R.id.spinnerMeses);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                totalMetroCubico = 0;
                totalTabuas = 0;
                totalToras = 0;

                mesSelecionado = Integer.toString(i + 1);

                if ((i + 1) <= 9) {
                    dataBusca = anoSelecionado + "-0" + mesSelecionado;
                } else {
                    dataBusca = anoSelecionado + "-"+ mesSelecionado;
                }

                vals();
                agregaGraph();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void listenerSpinnerAno () {
        Spinner spinner = getView().findViewById(R.id.spinnerAnoMes);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                totalMetroCubico = 0;
                totalTabuas = 0;
                totalToras = 0;

                anoSelecionado = spinner.getSelectedItem().toString();

                //todo remover a soma do i cpara mes
                if (Integer.parseInt(mesSelecionado) <= 9) {
                    dataBusca = anoSelecionado + "-0" + mesSelecionado;
                } else {
                    dataBusca = anoSelecionado + "-"+ mesSelecionado;
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