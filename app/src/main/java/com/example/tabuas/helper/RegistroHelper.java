package com.example.tabuas.helper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.core.app.NavUtils;

public class RegistroHelper extends SQLiteOpenHelper {

    public static int VERSION = 1;
    public static String NOME_DB = "DB_REG_TABUAS";
    public static String TABELA_TABUAS = "TBL_REG_TABUAS";

    private String datas [] = {"29/07/2022","17/09/2022","23/06/2002","09/02/2027","25/01/2022","20/11/2022","22/11/2022","11/09/2022","01/08/2022","12/04/2028"};
    private String categorias [] = {"1","2","2","2","2","1","1","2","1","1"};
    private double valores [] = {11.1,10.4,20.5,30.5,11.4,56.8,22.4,10,23,21};
    private String turnos [] = {"1","1","1","1","2","1","1","2","2","2"};


    public RegistroHelper(Context context) {
        super(context,NOME_DB, null,VERSION);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE IF NOT EXISTS "+ TABELA_TABUAS +"(id INTEGER PRIMARY KEY AUTOINCREMENT, data TEXT NOT NULL, categoria TEXT NOT NULL, valor FLOAT NOT NULL, turno TEXT NOT NULL);";


        try {
            db.execSQL(query);

            for (int i = 0; i < valores.length; i++) {
                String queryAdicao = "INSERT INTO "+ TABELA_TABUAS +"(data,categoria,valor,turno) VALUES ('"+datas[i]+"','"+categorias[i]+"',"+valores[i]+",'"+turnos[i]+"')";
                db.execSQL(queryAdicao);
            }

            Log.i("Sucesso ao criar tabela","");
        } catch (Exception e) {
            Log.e("Erro ao criar tabela", e.getMessage());
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        String query = "DROP TABLE IF EXISTS "+ TABELA_TABUAS +";";

        try {

            db.execSQL(query);
            //cria a tabela
            onCreate(db);
            Log.i("INFO DB","Sucesso ao atualizar tabela");

        }catch (Exception e) {
            Log.i("INFO DB","Erro ao atualizar tabela"+ e.getMessage());
        }
    }
}
