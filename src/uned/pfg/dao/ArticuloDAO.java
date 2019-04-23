package uned.pfg.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javax.sql.DataSource;


import uned.pfg.bean.Articulo;

public class ArticuloDAO {

	
	private DataSource origendatos;
	
	public ArticuloDAO(DataSource origendatos) {
		
		this.origendatos = origendatos;
		
	}
	
	
	public List<Articulo> getArticulos(){
		
		
		List<Articulo> articulos = new ArrayList<Articulo>();
		Connection conexion = null;
		Statement state=null;
		ResultSet rs =null;
		
		try {
			
		
			
			conexion = origendatos.getConnection();
			
			
			
			String consulta = "SELECT * FROM ARTICULO";
			
			state = conexion.createStatement();
			
			rs=state.executeQuery(consulta);
			
			while(rs.next()) {
				
				int id_articulo = rs.getInt(1);
				String nombre = rs.getString(2);
				double precio = rs.getDouble(4);

				articulos.add(new Articulo(id_articulo, nombre, null, precio));
			}
			
			state.close();
			conexion.close();
			
		}catch (Exception e) {

			e.printStackTrace();
		}
		
		
		return articulos;
		
		
	}
}
