package com.example.tabuas.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.tabuas.R;

public class AddRegTabuas extends AppCompatActivity {

    private String [] arr1 = {"opcao1","opcao3","opcao4","opcao5","opcao6"};

    Spinner spinner1,spinner2;
    EditText input;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_reg_tabuas);
    }

    public void volta (View view) {
        finish();
    }
}