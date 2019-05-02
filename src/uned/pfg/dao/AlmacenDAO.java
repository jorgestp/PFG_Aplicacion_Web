package uned.pfg.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.sql.DataSource;

import uned.pfg.bean.Almacen;
import uned.pfg.bean.Articulo;
import uned.pfg.bean.ArticuloPedido;

public class AlmacenDAO {
	
	private DataSource origendatos;
	Almacen almacen;
	public AlmacenDAO(DataSource origendatos) {
		
		this.origendatos = origendatos;
		
	}

	public Almacen comprobarArticuloEnAlmacen(ArticuloPedido artPed) {
		
		
		if(estaEnAlmacen(artPed.getArticulo())) {
			
			return almacen;
			
		}else {
			
			return null;
		}
		
		
		
		
	}
	
	private boolean estaEnAlmacen(Articulo art) {
		
		Connection conexion =null;
		PreparedStatement state = null;
		ResultSet rs;
		
		try {
			
			conexion = origendatos.getConnection();
			
			String sql = "SELECT * FROM ALMACEN WHERE ID_ARTICULO=?";
			
			state = conexion.prepareStatement(sql);
			
			state.setInt(1, art.getId_articulo());
			rs = state.executeQuery();
			
			while(rs.next()) {
				
				 almacen = new Almacen(rs.getInt(1), rs.getInt(2), rs.getInt(3));
				
				
				return true;
			}
			state.close();
			conexion.close();
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		return false;
		
		
	}

	public void actualizarCantidadLibre(ArticuloPedido artPed, int cantidadLibre) {
		Connection conexion =null;
		PreparedStatement state = null;
		
		
		try {
			
			conexion = origendatos.getConnection();
			
			String sql = "UPDATE ALMACEN SET CANTIDAD_LIBRE=? WHERE ID_ARTICULO=?";
			
			state = conexion.prepareStatement(sql);
			
			int cant_actualizada = cantidadLibre - artPed.getCant();
			
			state.setInt(1, cant_actualizada);
			state.setInt(2, artPed.getArticulo().getId_articulo());
			
			state.execute();
			

			state.close();
			conexion.close();
			
		}catch (Exception e) {
			
			e.printStackTrace();
		}
	
		
	}
	

}
