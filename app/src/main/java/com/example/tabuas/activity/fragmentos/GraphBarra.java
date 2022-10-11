package com.example.tabuas.activity.fragmentos;

import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.tabuas.R;
import com.example.tabuas.helper.RegistroDAO;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

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
        agregaGrafico();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_graph_barra, container, false);
    }

    private void agregaGrafico () {
        RegistroDAO registroDAO = new RegistroDAO(getContext());

        ArrayList<BarEntry> barEntries = new ArrayList<>();

        for (int i = 0; i < registroDAO.listar().size(); i ++) {
            BarEntry barEntry = new BarEntry( i, (float) registroDAO.listar().get(i).getValor());

            barEntries.add(barEntry);
        }

        BarDataSet barDataSet = new BarDataSet(barEntries,"Valores");
        barDataSet.setColors(ColorTemplate.COLORFUL_COLORS);

        barDataSet.setValueTextSize(14);

        BarChart barChart = (BarChart) getView().findViewById(R.id.graficoBarras);

        barChart.setData(new BarData(barDataSet));

        barChart.animateY(6000);

        barChart.getDescription().setText("Descricao");
        barChart.getDescription().setTextColor(Color.BLUE);
    }
}