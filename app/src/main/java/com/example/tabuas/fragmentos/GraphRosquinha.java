package com.example.tabuas.fragmentos;

import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.tabuas.R;
import com.example.tabuas.helper.Colors;
import com.example.tabuas.helper.RegistroDAO;
import com.example.tabuas.helper.TiposCategorias;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link GraphRosquinha#newInstance} factory method to
 * create an instance of this fragment.
 */
public class GraphRosquinha extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private double somaTora = 0;
    private double somaM3 = 0;
    private double somaTabua = 0;

    public GraphRosquinha() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment GraphGenerico.
     */
    // TODO: Rename and change types and number of parameters
    public static GraphRosquinha newInstance(String param1, String param2) {
        GraphRosquinha fragment = new GraphRosquinha();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onStart() {
        super.onStart();
        agregaGrafico();
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_graph_rosquinha, container, false);
    }

    private void agregaGrafico () {
        RegistroDAO registroDAO = new RegistroDAO(getContext());

        ArrayList<PieEntry> pieEntries = new ArrayList<>();

        //todo futuramente colocar esse valor o numero de categorias diferentes
        for (int i = 1; i <= 3; i ++) {

            PieEntry pieEntry = new PieEntry( (float) retornaSomaCategoria(i),retornaCategoria(i));
            pieEntries.add(pieEntry);
        }

        PieDataSet pieDataSet = new PieDataSet(pieEntries,"");
        pieDataSet.setColors(Colors.VERDE_PADRAO_COLORS);
        pieDataSet.setValueTextSize(16);

        PieChart pieChart = (PieChart) getView().findViewById(R.id.graficoPie);

        pieChart.getDescription().setText("Total: "+ String.format("%.2f", ( somaM3 + somaTora + somaTabua )) );
        pieChart.getDescription().setTextSize(16);
        pieChart.getDescription().setTextColor(Color.GRAY);
        //pieChart.setUsePercentValues(true);

        pieChart.setData(new PieData(pieDataSet));

        pieChart.animateY(6000);

        pieChart.setDrawCenterText(true);
        pieChart.setCenterText("TOTAL PRODUZIDO POR CATEGORIA");
        pieChart.setCenterTextSize(20);
        pieChart.setCenterTextColor(Color.GRAY);

        pieChart.setEntryLabelColor(Color.BLACK);
    }

    private double retornaSomaCategoria (int opcao) {
        RegistroDAO registroDAO = new RegistroDAO(getContext());

        somaTabua = 0;
        somaTora = 0;
        somaM3 = 0;

        for (int i = 0; i < registroDAO.listar().size(); i++) {
            String op = registroDAO.listar().get(i).getCategoria();
            if (op.equals(TiposCategorias.TORA.getValor())) {
                somaTora += registroDAO.listar().get(i).getValor();
            } else if (op.equals(TiposCategorias.METRO_CUBICO.getValor())){
                somaM3 += registroDAO.listar().get(i).getValor();
            } else if (op.equals(TiposCategorias.TABUA.getValor())) {
                somaTabua += registroDAO.listar().get(i).getValor();
            }
        }

        if (opcao == 1) {
            return somaTora;
        } else if (opcao == 2) {
            return somaM3;
        } else {
            return somaTabua;
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
}