package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import db.DB;
import db.DbException;
import model.dao.VendedorDao;
import model.entities.Departamento;
import model.entities.Vendedor;

public class VendedorDaoJDBC implements VendedorDao {

	private Connection conn;

	public VendedorDaoJDBC(Connection conn) {
		this.conn = conn;
	}

	@Override
	public void insert(Vendedor vend) {
		PreparedStatement st = null;
		ResultSet rs = null;
		 
		try {
			st = conn.prepareStatement(
					"INSERT INTO seller "
					+"(Name, Email, BirthDate, BaseSalary, DepartmentId) "  
					+"VALUES (?, ?, ?, ?, ?)",
					Statement.RETURN_GENERATED_KEYS);
			
			st.setString(1, vend.getNome());
			st.setString(2, vend.getEmail());
			st.setDate(3, new java.sql.Date(vend.getDataNascimento().getTime()));
			st.setDouble(4, vend.getSalarioBase());
			st.setInt(5, vend.getDepartamento().getId());
			
			int linhasAfetadas = st.executeUpdate();
			
			if (linhasAfetadas > 0) {
				rs = st.getGeneratedKeys();
				if (rs.next()) {
					int id = rs.getInt(1);
					vend.setId(id);
				}
				DB.closeResultSet(rs);;
			} 
			else {
				throw new DbException("Erro inesperado! Nenhuma linha foi afetada");
			}
			
		}catch(SQLException e) {
			System.out.println("Error: " + e.getErrorCode());
		
		}finally { 
			DB.closeStatement(st);
		}
		
	}

	@Override
	public void update(Vendedor vend) {
		PreparedStatement st = null;
		ResultSet rs = null;

		try {
			st = conn.prepareStatement("UPDATE seller " + "SET Name = ?, Email = ?, BirthDate = ?, "
					+ "BaseSalary = ?, DepartmentId = ? " + "WHERE Id = ?");

			st.setString(1, vend.getNome());
			st.setString(2, vend.getEmail());
			st.setDate(3, new java.sql.Date(vend.getDataNascimento().getTime()));
			st.setDouble(4, vend.getSalarioBase());
			st.setInt(5, vend.getDepartamento().getId());
			st.setInt(6, vend.getId());

			st.executeUpdate();

		} catch (SQLException e) {
			System.out.println("Error: " + e.getErrorCode());

		} finally {
			DB.closeResultSet(rs);
			DB.closeStatement(st);
		}

	}

	@Override
	public void deleteId(Integer id) {
		PreparedStatement st = null;

		try {
			st = conn.prepareStatement("DELETE FROM seller " + "WHERE Id = ?");
			st.setInt(1, id);
			st.executeUpdate();
		} catch (SQLException e) {
			throw new DbException("Erro:" + e.getMessage());
		} finally {
			DB.closeStatement(st);
		}

	}

	@Override
	public Vendedor buscaId(Integer id) {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement(
					"SELECT seller.*,department.Name as DepName " + "FROM seller INNER JOIN department "
							+ "ON seller.DepartmentId = department.Id " + "WHERE seller.Id = ?");
			st.setInt(1, id);
			rs = st.executeQuery();
			if (rs.next()) {
				Departamento dep = instanciarDepartamento(rs);
				Vendedor vend = instanciarVendedor(rs, dep);
				return vend;
			}
			return null;

		} catch (SQLException e) {
			throw new DbException("Erro: " + e.getMessage());

		} finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}
	}

	private Vendedor instanciarVendedor(ResultSet rs, Departamento dep) throws SQLException {
		Vendedor vend = new Vendedor();
		vend.setId(rs.getInt("Id"));
		vend.setNome(rs.getString("Name"));
		vend.setEmail(rs.getString("Email"));
		vend.setSalarioBase(rs.getDouble("BaseSalary"));
		vend.setDataNascimento(rs.getDate("BirthDate"));
		vend.setDepartamento(dep);
		return vend;
	}

	private Departamento instanciarDepartamento(ResultSet rs) throws SQLException {
		Departamento dep = new Departamento();
		dep.setId(rs.getInt("DepartmentId"));
		dep.setNome(rs.getString("DepName"));
		return dep;
	}

	@Override
	public List<Vendedor> encontreTodos() {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement("SELECT seller.*,department.Name as DepName "
					+ "FROM seller INNER JOIN department " + "ON seller.DepartmentId = department.Id "
					+ "WHERE DepartmentId = departmentId " + "ORDER BY Name");

			rs = st.executeQuery();

			List<Vendedor> listaVendedor = new ArrayList<>();
			Map<Integer, Departamento> map = new HashMap<>();

			while (rs.next()) {

				Departamento dep = map.get(rs.getInt("DepartmentId"));

				if (dep == null) {
					dep = instanciarDepartamento(rs);
					map.put(rs.getInt("DepartmentId"), dep);
				}

				Vendedor vend = instanciarVendedor(rs, dep);
				listaVendedor.add(vend);
			}
			return listaVendedor;

		} catch (SQLException e) {
			throw new DbException("Erro: " + e.getMessage());

		} finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}
	}

	@Override
	public List<Vendedor> encontrarDepartamento(Departamento departamento) {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement(
					"SELECT seller.*,department.Name as DepName " + "FROM seller INNER JOIN department "
							+ "ON seller.DepartmentId = department.Id " + "WHERE DepartmentId = ? " + "ORDER BY Name");
			st.setInt(1, departamento.getId());

			rs = st.executeQuery();

			List<Vendedor> listaVendedor = new ArrayList<>();
			Map<Integer, Departamento> map = new HashMap<>();

			while (rs.next()) {

				Departamento dep = map.get(rs.getInt("DepartmentId"));

				if (dep == null) {
					dep = instanciarDepartamento(rs);
					map.put(rs.getInt("DepartmentId"), dep);
				}

				Vendedor vend = instanciarVendedor(rs, dep);
				listaVendedor.add(vend);
			}
			return listaVendedor;

		} catch (SQLException e) {
			throw new DbException("Erro: " + e.getMessage());

		} finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}
	}

}
