package model.dao;

import java.util.List;

import model.entities.Departamento;
import model.entities.Vendedor;

public interface VendedorDao {
	
	void insert(Vendedor vend);
	void update(Vendedor vend);
	void deleteId(Integer id);
	Vendedor buscaId(Integer id);
	List<Vendedor> encontreTodos();
	List<Vendedor> encontrarDepartamento(Departamento departamento);
}
