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

    private String datas [] = {
            "2022-01-05","2022-01-05","2022-01-05",
            "2022-01-05","2022-01-05","2022-01-05",
            "2022-01-06","2022-01-06","2022-01-06",
            "2022-01-10","2022-01-10","2022-01-10",
            "2022-01-11","2022-01-11","2022-01-11",
            "2021-06-11","2021-06-11","2021-08-11",
            "2021-08-11","2021-08-11","2021-08-11",
            "2021-07-19","2021-02-01","2021-03-01",
            "2022-02-19","2022-02-03","2022-09-08",
            "2021-05-07","2022-07-05","2021-08-11",
            "2021-04-21","2022-02-05","2021-08-11",
            "2021-03-11","2022-03-09","2022-10-10",
            "2021-02-07","2022-05-05","2022-01-10",
            "2021-02-03","2022-06-04","2021-09-09",
            "2021-10-10","2022-11-11","2021-08-11",
            "2022-10-20","2022-10-20","2022-10-20",
            "2022-10-21","2022-10-20","2022-10-20",
            "2022-11-21","2022-11-20","2022-11-20",
            "2022-11-20","2022-10-20","2022-10-20",

    };
    private String categorias [] = {
            "TORA","METRO CÚBICO","TÁBUA",
            "TORA","METRO CÚBICO","TÁBUA",
            "TORA","METRO CÚBICO","TÁBUA",
            "TORA","METRO CÚBICO","TÁBUA",
            "TORA","METRO CÚBICO","TÁBUA",
            "TORA","METRO CÚBICO","TÁBUA",
            "TORA","METRO CÚBICO","TÁBUA",
            "TORA","METRO CÚBICO","TÁBUA",
            "TORA","METRO CÚBICO","TÁBUA",
            "TORA","METRO CÚBICO","TÁBUA",
            "TORA","METRO CÚBICO","TÁBUA",
            "TORA","METRO CÚBICO","TÁBUA",
            "TORA","METRO CÚBICO","TÁBUA",
            "TORA","METRO CÚBICO","TÁBUA",
            "TORA","METRO CÚBICO","TÁBUA",
            "TORA","METRO CÚBICO","TÁBUA",
            "TORA","METRO CÚBICO","TÁBUA",
            "TORA","METRO CÚBICO","TÁBUA",
            "TORA","METRO CÚBICO","TÁBUA",

    };
    private double valores [] = {
            1529,147.62,6152,
            1477,137.0602,5885,
            1624,150.06,7449,
            1024,106.358,5420,
            1775,164.906,8069,
            155.612,7381,1999,
            145.644,132,700,
            34.9,77,120,
            134.9,234,1600,
            1000,877,1200,
            7701,8707,1200,
            900.9,8506,1200,
            777.9,822,1200,
            1000.9,875,900,
            897.9,877.6,300,
            100,200.6,100,
            3333.9,1141.6,900,
            3343.9,1151.6,9050,
            3330.9,1151.6,9400,

    };
    private String turnos [] = {
            "1","1","1",
            "2","2","2",
            "2","2","1",
            "1","1","1",
            "1","1","2",
            "2","2","1",
            "1","1","1",
            "2","1","2",
            "1","1","1",
            "2","1","1",
            "2","1","1",
            "2","2","1",
            "2","2","2",
            "1","1","1",
            "1","2","1",
            "1","1","1",
            "1","1","1",
            "1","1","1",
            "1","1","1",
    };


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
