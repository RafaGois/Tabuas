package com.example.tabuas.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.example.tabuas.R;
import com.example.tabuas.activity.fragmentos.GraphBarra;
import com.example.tabuas.activity.fragmentos.GraphRegMes;
import com.example.tabuas.activity.fragmentos.GraphRosquinha;
import com.example.tabuas.activity.fragmentos.GraphRegDia;
import com.ogaclejapan.smarttablayout.SmartTabLayout;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItemAdapter;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItems;

import java.util.Objects;

public class Graph extends AppCompatActivity {

    private SmartTabLayout smartTabLayout;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph);

        Objects.requireNonNull(getSupportActionBar()).setElevation(0);

        FragmentPagerItemAdapter adapter = new FragmentPagerItemAdapter(
                getSupportFragmentManager(), FragmentPagerItems.with(this)
                .add("Registro por Dia", GraphRegDia.class)
                .add("Registro por Mês", GraphRegMes.class)
                .add("Relatório por Registro", GraphBarra.class)
                .add("Total por Categoria", GraphRosquinha.class)
                .create());

        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        viewPager.setAdapter(adapter);

        SmartTabLayout viewPagerTab = (SmartTabLayout) findViewById(R.id.viewPagerTab);
        viewPagerTab.setViewPager(viewPager);
    }
}