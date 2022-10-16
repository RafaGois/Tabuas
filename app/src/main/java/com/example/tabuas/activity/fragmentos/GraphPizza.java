package com.example.tabuas.activity.fragmentos;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.tabuas.R;
import com.example.tabuas.helper.RegistroDAO;
import com.example.tabuas.helper.TiposCategorias;
import com.example.tabuas.model.Registro;
import com.github.mikephil.charting.data.PieEntry;

import java.util.ArrayList;

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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_graph_pizza, container, false);
    }

    private void agregaVals () {
        RegistroDAO registroDAO = new RegistroDAO(getContext());

        ArrayList<Registro> registros = new ArrayList<>();

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
}