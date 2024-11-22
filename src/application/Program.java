package application;

import java.util.List;

import db.DB;
import model.dao.Daofactory;
import model.dao.DepartamentoDao;
import model.entities.Departamento;

public class Program {

	public static void main(String[] args) {

//		Departamento dep1 = new Departamento(1, "RH");
//		System.out.println(dep1);
//
//		VendedorDao vendedorDao = Daofactory.criarVendedorDao();
//		Vendedor vend = vendedorDao.buscaId(7);
//		System.out.println(vend);
//		
//
//		System.out.println("\n========= TESTE 2 encontrarDepartamento =======");
//		Departamento dep = new Departamento(2, null);
//		List<Vendedor> ListaVend = vendedorDao.encontrarDepartamento(dep);
//
//		for (Vendedor v : ListaVend) {
//			System.out.println(v);
//		}
//
//		System.out.println("\n========= TESTE 3 encontreTodos =======");
//		List<Vendedor> ListaVendedor = vendedorDao.encontreTodos();
//
//		for (Vendedor v : ListaVendedor) {
//			System.out.println(v);
//		}
//		
//		System.out.println("\n========= TESTE 4 Insert =======");
//		Vendedor novoVendedor = new Vendedor(12, "Lucas", "lucas@gmail.com", new Date(), 12000.0, dep);
//		vendedorDao.insert(novoVendedor);
//		System.out.println("Insert novo Id:" + novoVendedor.getId());
//		
//		System.out.println("\n========= TESTE 5 Update =======");
//		vend =  vendedorDao.buscaId(1);
//		vend.setNome("Beatriz Alves da Costa");
//		vendedorDao.update(vend);
//		System.out.println("Update completado");
//		
//		System.out.println("\n========= TESTE 6 Delete =======");
//		vendedorDao.deleteId(11);
//		System.out.println("Delete completado");
		
		DepartamentoDao departamentoDao = Daofactory.criarDepartamentoDao();
		System.out.println("\n========= TESTE 1 buscaId Department  =======");

		Departamento dep = departamentoDao.buscaId(1);
		System.out.println(dep);

		System.out.println("\n========= TESTE 2 insert Department  =======");
//		
//		Departamento newDepartment = new Departamento(null, "Cloud Enginner");
//		System.out.println(newDepartment);
		
		System.out.println("\n========= TESTE 3 update Department  =======");
		
		Departamento updateDep = departamentoDao.buscaId(2);
		updateDep.setNome("Software Enginner");
		System.out.println("Update completado");
		
		System.out.println("\n========= TESTE 4 delete Department  =======");
		
//		departamentoDao.deleteId(3);
//		System.out.println("Delete completado");

		System.out.println("\n========= TESTE 5 EncontreTodos Department  =======");
		List<Departamento> ListaDepartamento = departamentoDao.encontreTodos();

		for(Departamento d: ListaDepartamento) {
			System.out.println(d);
		}

		DB.closeConnection();
	}
}
