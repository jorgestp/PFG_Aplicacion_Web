package uned.pfg.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.sql.DataSource;

import uned.pfg.bean.ArticuloPedido;
import uned.pfg.bean.Pedido;

public class PedidoDAO {

	private DataSource origendatos;
	
	public PedidoDAO(DataSource origendatos) {
		
		this.origendatos = origendatos;
		
	}
	
	
	public int insertaPedido(Pedido p) {

		Connection conexion = null;
		PreparedStatement state =null;
		
		try {
			
			conexion = origendatos.getConnection();
			
			String sql = "INSERT INTO PEDIDO (id_distribuidor, fecha_pedido, fecha_envio, estado) "
					+ "VALUES (?,?,?,?)";
			
			state = conexion.prepareStatement(sql);
			
			
			state.setInt(1, p.getId_distribuidor());
			
			java.util.Date entrada = p.getFecha_entrada();
			
			java.sql.Date fecha_entrada = new java.sql.Date(entrada.getTime());
			
			state.setDate(2, fecha_entrada);
			
			
			java.util.Date envio = p.getFecha_entrada();
			
			java.sql.Date fecha_envio  = new java.sql.Date(envio.getTime());
			
			state.setDate(3, fecha_envio);
			
			state.setString(4, p.getEstado());
					
			state.execute();
			
			state.close();
			conexion.close();
			
			int id_pedido =  obteneridPedido();
			
			
			
			return id_pedido;
			
			
			
		}catch (Exception e) {

				e.printStackTrace();
		}
		
		return 0;
	}


	private int obteneridPedido() {
		Connection conexion = null;
		Statement state =null;
		ResultSet rs = null;
		
		int id =0;
		
		try {
			
			conexion = origendatos.getConnection();
			
			String sql = "SELECT * FROM PEDIDO ORDER BY id_pedido DESC";
			

			state = conexion.createStatement();
			rs=state.executeQuery(sql);
			
			boolean flag = true;
			while(rs.next() && flag) {
			 id = rs.getInt(1);
			 flag = false;
			}
			
			state.close();
			conexion.close();
			return id;
			
			
		}catch (Exception e) {

				e.printStackTrace();
		}
		
		
		
		return id;
	}


	public boolean insertarArticulos(Pedido p) {
		
		//Obtener la conexion
		
		Connection conexion = null;
		PreparedStatement state =null;
		List<ArticuloPedido> listaArticulos = p.getArticulos();
		

			
		Iterator<ArticuloPedido> it = listaArticulos.iterator();
		
		while(it.hasNext()) {
			
			ArticuloPedido artPed = it.next();
			
			try {
				
				conexion = origendatos.getConnection();
			String sql ="INSERT INTO ARTICULO_PEDIDO (id_pedido, id_articulo, cantidad, realizado, embalado)"
					+ "VALUES (?,?,?,?,?) ";
			state = conexion.prepareStatement(sql);
			
			state.setInt(1, p.getId_pedido());
			state.setInt(2, artPed.getArticulo().getId_articulo());
			state.setInt(3, artPed.getCant());
			state.setBoolean(4, artPed.isRealizado());
			state.setBoolean(5, artPed.isEmbalado());
			
			state.execute();
			
			state.close();
			conexion.close();
			
			}catch (Exception e) {
			e.printStackTrace();
			return false;
			}
			
		}
		
		return true;

		
	}


	public List<Pedido> obtenerPedidos(int id) {
		
		List<Pedido> lista = new ArrayList<Pedido>();
		Pedido p = null;
		Connection conexion = null;
		PreparedStatement state =null;
		ResultSet rs =null;
		
		try {
			
			conexion = origendatos.getConnection();
			
			String sql = "SELECT * FROM PEDIDO WHERE ID_DISTRIBUIDOR = ?";
			
			state = conexion.prepareStatement(sql);
			state.setInt(1, id);
			
			rs = state.executeQuery();
			
			while(rs.next()) {
			
			int idpedido = rs.getInt(1);
			Date realizado  =rs.getDate(3);
			Date envio  =rs.getDate(4);
			String estado = rs.getString(5);
			
			p = new Pedido(idpedido, realizado, envio, estado);
			
			lista.add(p);
			
			}
			
			state.close();
			conexion.close();
			
		}catch (Exception e) {
			
			e.printStackTrace();
		}
		
		return lista;
	}


	public static String diHola() {
		// TODO Auto-generated method stub
		return "HOLA JORGE";
	}
	
	public List<Pedido> obtenPedidos(){
		
		List<Pedido> lista = new ArrayList<Pedido>();
		
		Connection conexion = null;
		Statement state = null;
		ResultSet rs = null;
		Pedido p = null;
		
		try {
			
			conexion = origendatos.getConnection();
			
			String sql = "select * from pedido";
			
			state = conexion.createStatement();
			
			rs = state.executeQuery(sql);
			
			while(rs.next()) {
				
				p = new Pedido(rs.getInt(1), rs.getDate(3), rs.getDate(4), rs.getString(5));
				lista.add(p);
			}
			
			
		}catch (Exception e) {
			
			e.printStackTrace();
		}
		
		return lista;
		
	}
	
}
