package model.dao;

import java.util.List;

import model.entities.Departamento;

public interface DepartamentoDao {
	
	void insert(Departamento dep);
	void update(Departamento dep);
	void deleteId(Integer id);
	Departamento buscaId(Integer id);
	List<Departamento> encontreTodos();
}
