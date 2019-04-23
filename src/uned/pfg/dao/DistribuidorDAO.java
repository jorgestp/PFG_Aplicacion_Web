package uned.pfg.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Date;

import javax.sql.DataSource;

import uned.pfg.bean.Distribuidor;

public class DistribuidorDAO {
	
	private DataSource origendatos;
	
	public DistribuidorDAO(DataSource origendatos) {
		
		this.origendatos = origendatos;
	}

	public void insert(Distribuidor dist) {

		Connection conexion = null;
		PreparedStatement state =null;
		
		try {
			
			conexion = origendatos.getConnection();
			
			String sql = "INSERT INTO DISTRIBUIDOR (id_distribuidor, nombre, "
					+ "domicilio, email, telefono, CP, pais, fecha_alta, usuario, password) "
					+ "VALUES (NULL, ?,?,?,?,?,?,?,?,?)";
			
			state = conexion.prepareStatement(sql);
			
			
			state.setString(1, dist.getNombre());
			state.setString(2, dist.getDomicilio());
			state.setString(3, dist.getEmail());
			state.setInt(4, dist.getTfno());
			state.setInt(5, dist.getCp());
			state.setString(6, dist.getPais());
			java.util.Date f = dist.getFecha();
			
			java.sql.Date fecha = new java.sql.Date(f.getTime());
			
			state.setDate(7, fecha);
			state.setString(8, dist.getUser());
			state.setString(9, dist.getPassword());
			
			
			
			state.execute();
			
			state.close();
			conexion.close();
			
		}catch (Exception e) {

				e.printStackTrace();
		}
	}

	public Distribuidor buscarUsuario(String usuario, String contra) {

		Connection conexion = null;
		PreparedStatement state =null;
		ResultSet rs = null;
		Distribuidor distribuidor = null;
		
		try {
			
			conexion = origendatos.getConnection();
			

			String sql = "SELECT * FROM DISTRIBUIDOR WHERE USUARIO=? AND PASSWORD=?";
			
			state = conexion.prepareStatement(sql);
			
			
			state.setString(1, usuario);
			state.setString(2, contra);
			
			rs = state.executeQuery();
			
			if(rs.next()) {
				
				int id =rs.getInt(1);
				String nombre = rs.getString(2);
				String domicilio = rs.getString(3);
				String email = rs.getString(4);
				int tfno =rs.getInt(5);
				int cp =rs.getInt(6);
				String pais = rs.getString(7);
				Date fecha = rs.getDate(8);
				
				
				
				
				distribuidor = new Distribuidor(id, tfno, cp, nombre, domicilio, email, pais, usuario, null, fecha);
				
				
				return distribuidor;
			}
			
			state.close();
			conexion.close();
			
			
		}catch (Exception e) {

				e.printStackTrace();
		}
		
		return distribuidor;
	}

	
	
	
	public int actualizar(Distribuidor d)   {

		Connection conexion = null;
		PreparedStatement state = null;
		int flag = 0;
		
		try {
			
			conexion = origendatos.getConnection();
			String sql = "UPDATE `distribuidor` SET `nombre`=?,`domicilio`=?,`email`=?,`telefono`=?,"
					+ "`CP`=?,`pais`=?,"
					+ "`password`=? WHERE id_distribuidor = ?";
			
			state = conexion.prepareStatement(sql);
			
			state.setString(1, d.getNombre());
			state.setString(2, d.getDomicilio());
			state.setString(3, d.getEmail());
			state.setInt(4, d.getTfno());
			
			state.setInt(5, d.getCp());
			state.setString(6, d.getPais());
			state.setString(7, d.getPassword());
			state.setInt(8, d.getId());
			
			

			 flag = state.executeUpdate();
			 
				state.close();
				conexion.close();
			
		}catch (Exception e) {
			
			e.printStackTrace();
		
		}
		
		return flag;
		
	}
}
	
	
	

