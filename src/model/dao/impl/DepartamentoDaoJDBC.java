package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import db.DB;
import db.DbException;
import model.dao.DepartamentoDao;
import model.entities.Departamento;

public class DepartamentoDaoJDBC implements DepartamentoDao {

	private Connection conn;

	public DepartamentoDaoJDBC(Connection conn) {
		this.conn = conn;
	}

	@Override
	public void insert(Departamento dep) {
		PreparedStatement st = null;
		ResultSet rs = null;

		try {
			st = conn.prepareStatement("INSERT INTO department " + "(Name) VALUES (?)",
					Statement.RETURN_GENERATED_KEYS);

			st.setString(1, dep.getNome());

			int linhasAfetadas = st.executeUpdate();
			if (linhasAfetadas > 0) {
				rs = st.getGeneratedKeys();
				if (rs.next()) {
					int id = rs.getInt(1);
					dep.setId(id);
				}
			} else {
				throw new DbException("Erro inesperado! Zero linhas afetadas: ");
			}
			DB.closeResultSet(rs);

		} catch (SQLException e) {
			throw new DbException("Erro: " + e.getMessage());
		} finally {
			DB.closeStatement(st);
		}
	}

	@Override
	public void update(Departamento dep) {
		PreparedStatement st = null;
		ResultSet rs = null;

		try {
			st = conn.prepareStatement("UPDATE department " + " SET Name = ? " + "WHERE ID = ? ",
					Statement.RETURN_GENERATED_KEYS);

			st.setString(1, dep.getNome());
			st.setInt(2, dep.getId());

			int linhasAfetadas = st.executeUpdate();

			if (linhasAfetadas > 0) {
				rs = st.getGeneratedKeys();
				if (rs.next()) {
					int id = rs.getInt(1);
					dep.setId(id);
				}
			} else {
				throw new DbException("Erro inesperado! Zero linhas afetadas: ");
			}

			DB.closeResultSet(rs);

		} catch (SQLException e) {
			throw new DbException("Erro: " + e.getMessage());
		} finally {
			DB.closeStatement(st);
		}
	}

	@Override
	public void deleteId(Integer id) {

		PreparedStatement st = null;

		try {
			st = conn.prepareStatement("DELETE FROM department WHERE Id = ? ");
			st.setInt(1, id);

			st.executeUpdate();
			
		} catch (SQLException e) {
			throw new DbException("Erro: " + e.getMessage());
		} finally {
			DB.closeStatement(st);
		}
	}

	@Override
	public Departamento buscaId(Integer id) {
		PreparedStatement st = null;
		ResultSet rs = null;

		try {
			st = conn.prepareStatement("SELECT * FROM department WHERE Id = ?");

			st.setInt(1, id);
			rs = st.executeQuery();
			if (rs.next()) {
				Departamento dep = instanciarDepartamento(rs);
				return dep;
			}
			return null;

		} catch (SQLException e) {
			throw new DbException("Erro: " + e.getMessage());
		} finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}
	}

	@Override
	public List<Departamento> encontreTodos() {
		PreparedStatement st = null;
		ResultSet rs = null;

		try {
			st = conn.prepareStatement("SELECT * FROM department ORDER BY Name");
			rs = st.executeQuery();

			List<Departamento> lista = new ArrayList<>();

			while (rs.next()) {
				Departamento dep = new Departamento(rs.getInt("Id"), rs.getString("Name"));
				lista.add(dep);
			}
			return lista;

		} catch (SQLException e) {
			throw new DbException("Erro: " + e.getMessage());
		} finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}
	}

	private Departamento instanciarDepartamento(ResultSet rs) throws SQLException {
		Departamento dep = new Departamento();
		dep.setId(rs.getInt("Id"));
		dep.setNome(rs.getString("Name"));
		return dep;
	}
}
