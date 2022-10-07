package com.example.tabuas.helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.tabuas.model.Registro;

import java.util.ArrayList;
import java.util.List;

public class RegistroDAO implements IRegDAO {

    private SQLiteDatabase escreve;
    private SQLiteDatabase le;

    public RegistroDAO(Context context) {
        RegistroHelper rh = new RegistroHelper(context);

        escreve = rh.getWritableDatabase();
        le = rh.getReadableDatabase();

    }

    @Override
    public boolean salvar(Registro registro) {

        ContentValues cv = new ContentValues();
        cv.put("data",registro.getDateTime());
        cv.put("categoria",registro.getCategoria());
        cv.put("valor",registro.getValor());
        cv.put("turno",registro.getTurno());

        try {
            escreve.insert(RegistroHelper.TABELA_TABUAS,null,cv);
            Log.i("INFO TABLE","Sucesso ao inserir valores na tabela");
        } catch (Exception e) {
            Log.e("INFO TABLE","Erro ao salvar arquivos na tabela");
            return false;
        }

        return true;
    }

    @Override
    public boolean atualizar(Registro registro) {

        ContentValues cv = new ContentValues();
        cv.put("data",registro.getDateTime());
        cv.put("categoria",registro.getCategoria());
        cv.put("valor",registro.getValor());
        cv.put("turno",registro.getTurno());

        String args [] = {String.valueOf(registro.getId())};

        try {
            escreve.update(RegistroHelper.TABELA_TABUAS,cv,"id=?",args);
            Log.i("INFO TABLE","Valores salvos");
        } catch (Exception e) {
            Log.e("INFO TABLE","Erro ao atualizar tabela");
            return false;
        }
        return true;
    }

    @Override
    public boolean deletar(Registro registro) {

        String args [] = {String.valueOf(registro.getId())};

        try {
            escreve.delete(RegistroHelper.TABELA_TABUAS,"id=?",args);
            Log.i("INFO DB","Valor salvo com sucesso");
            return true;
        } catch (Exception e) {
            Log.e("Erro",e.getMessage());
        }
        return false;
    }

    @Override
    public List<Registro> listar() {
        List<Registro> registros = new ArrayList<>();

        String sql = "SELECT * FROM"+RegistroHelper.TABELA_TABUAS+";";

        Cursor c = le.rawQuery(sql,null);

        int posicaoId = c.getColumnIndex("id");
        int posicaoData = c.getColumnIndex("data");
        int posicaoCategoria = c.getColumnIndex("categoria");
        int posicaoValor = c.getColumnIndex("valor");
        int posicaoTurno = c.getColumnIndex("turno");


        c.moveToFirst();
        while (c.moveToNext()) {
            Registro registro = new Registro();

            Long id = c.getLong(posicaoId);
            String data = c.getString(posicaoData);
            String categoria = c.getString(posicaoCategoria);
            double valor = c.getDouble(posicaoValor);
            String turno = c.getString(posicaoTurno);

            registro.setDateTime(data);
            registro.setCategoria(categoria);
            registro.setValor(valor);
            registro.setTurno(turno);

            registros.add(registro);
        }

        return registros;
    }
}
