package com.example.tabuas.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tabuas.R;
import com.example.tabuas.model.Registro;

import java.util.ArrayList;
import java.util.List;

public class RegistroAdapter extends RecyclerView.Adapter<RegistroAdapter.MyViewHolder> {

    List<Registro> registros;

    public RegistroAdapter(List<Registro> registros) {
        this.registros = registros;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemLista = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_listagem,parent,false);
        return new MyViewHolder(itemLista);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Registro registro = registros.get(position);

        holder.txtData.setText(registro.getDateTime());
        holder.txtCategoria.setText(registro.getCategoria());
        holder.txtValor.setText(Double.toString(registro.getValor()));
        holder.txtTurno.setText(registro.getTurno());

    }

    @Override
    public int getItemCount() {
        return registros.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView txtData;
        TextView txtCategoria;
        TextView txtValor;
        TextView txtTurno;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            txtData = itemView.findViewById(R.id.txtData);
            txtCategoria = itemView.findViewById(R.id.txtCategoria);
            txtValor = itemView.findViewById(R.id.txtValor);
            txtTurno = itemView.findViewById(R.id.txtTurno);
        }
    }
}
