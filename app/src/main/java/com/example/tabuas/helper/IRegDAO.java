package com.example.tabuas.helper;

import com.example.tabuas.model.Registro;

import java.util.List;

public interface IRegDAO {

    public boolean salvar(Registro registro);
    public boolean atualizar (Registro registro);
    public boolean deletar (Registro registro);

    public List<Registro> listar ();

}
