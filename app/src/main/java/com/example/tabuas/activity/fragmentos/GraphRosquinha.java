package com.example.tabuas.activity.fragmentos;

import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.tabuas.R;
import com.example.tabuas.helper.RegistroDAO;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

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
        return inflater.inflate(R.layout.fragment_graph_generico, container, false);
    }

    private void agregaGrafico () {
        RegistroDAO registroDAO = new RegistroDAO(getContext());

        ArrayList<PieEntry> pieEntries = new ArrayList<>();

        for (int i = 0; i < registroDAO.listar().size(); i ++) {
            PieEntry pieEntry = new PieEntry( i, (float) registroDAO.listar().get(i).getValor());

            pieEntries.add(pieEntry);
        }

        PieDataSet pieDataSet = new PieDataSet(pieEntries,"slaaaa");
        pieDataSet.setColors(ColorTemplate.COLORFUL_COLORS);

        PieChart pieChart = (PieChart) getView().findViewById(R.id.graficoPie);

        pieChart.setData(new PieData(pieDataSet));

        pieChart.animateY(6000);

        pieChart.getDescription().setText("Descricao");
        pieChart.getDescription().setTextColor(Color.BLUE);
    }


}