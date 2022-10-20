package com.example.tabuas.fragmentos;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.tabuas.R;
import com.example.tabuas.helper.RegistroDAO;
import com.example.tabuas.helper.TiposCategorias;
import com.example.tabuas.model.Registro;

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
        agregaVals();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_graph_reg_mes, container, false);
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

    private void agregaVals () {

        TextView tMc = getView().findViewById(R.id.textView12);
        TextView txtabuas = getView().findViewById(R.id.textView13);
        TextView txtoras = getView().findViewById(R.id.textView14);

        tMc.setText("R$ "+String.format("%.2f", totalMetroCubico));
        txtabuas.setText("R$ "+String.format("%.2f", totalTabuas));
        txtoras.setText("R$ "+String.format("%.2f", totalToras));

        TextView txtTotal = getView().findViewById(R.id.txtRegMesTotal);
        txtTotal.setText("Total: R$ "+ String.format("%.2f", (totalTabuas + totalToras + totalMetroCubico)));

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
                agregaVals();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }
}