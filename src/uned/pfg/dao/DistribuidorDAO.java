package uned.pfg.dao;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.sql.DataSource;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.mysql.fabric.xmlrpc.base.Array;

import uned.pfg.bean.ArticuloPedido;
import uned.pfg.bean.Distribuidor;
import uned.pfg.bean.Pedido;
import uned.pfg.ws.PoolConexiones;

public class DistribuidorDAO {

	private DataSource origendatos;
	private final String FILENAME_DIST = "XML_list_distribuidor.xml";
	private final String FILENAME_un_dist = "XML_distr.xml";

	public DistribuidorDAO(DataSource origendatos) {

		origendatos = PoolConexiones.getInstance().getConnection();
		
		this.origendatos = origendatos;
		
	}

	public void insert(Distribuidor dist) {

		Connection conexion = null;
		PreparedStatement state = null;

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
			

		} catch (Exception e) {

			e.printStackTrace();
		}
	}

	public Distribuidor buscarUsuario(String usuario, String contra) {

		Connection conexion = null;
		PreparedStatement state = null;
		ResultSet rs = null;
		Distribuidor distribuidor = null;

		try {

			conexion = origendatos.getConnection();

			String sql = "SELECT * FROM DISTRIBUIDOR WHERE USUARIO=? AND PASSWORD=?";

			state = conexion.prepareStatement(sql);

			state.setString(1, usuario);
			state.setString(2, contra);

			rs = state.executeQuery();

			if (rs.next()) {

				int id = rs.getInt(1);
				String nombre = rs.getString(2);
				String domicilio = rs.getString(3);
				String email = rs.getString(4);
				int tfno = rs.getInt(5);
				int cp = rs.getInt(6);
				String pais = rs.getString(7);
				Date fecha = rs.getDate(8);

				distribuidor = new Distribuidor(id, tfno, cp, nombre, domicilio, email, pais, usuario, null, fecha);

				return distribuidor;
			}

			state.close();
			conexion.close();

		} catch (Exception e) {

			e.printStackTrace();
		}

		return distribuidor;
	}

	public int actualizar(Distribuidor d) {

		Connection conexion = null;
		PreparedStatement state = null;
		int flag = 0;

		try {

			conexion = origendatos.getConnection();
			String sql = "UPDATE `distribuidor` SET `nombre`=?,`domicilio`=?,`email`=?,`telefono`=?,"
					+ "`CP`=?,`pais`=?," + "`password`=? WHERE id_distribuidor = ?";

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

		} catch (Exception e) {

			e.printStackTrace();

		}

		return flag;

	}
	
	public List<Distribuidor> obtenerDistribuidores() {
		
		
		List<Distribuidor> distribuidores = new ArrayList<Distribuidor>();
		Connection conexion = null;
		Statement state = null;
		ResultSet rs = null;
		int i = 0;

		try {

			conexion = origendatos.getConnection();

			String sql = "select * from distribuidor";

			state = conexion.createStatement();

			rs = state.executeQuery(sql);

			while (rs.next()) {
				
				int id = rs.getInt(1);
				String nombre = rs.getString(2);
				
				Distribuidor d = new Distribuidor(id, nombre);
				
				distribuidores.add(d);
				
			}

			state.close();
			conexion.close();

		} catch (Exception e) {

			e.printStackTrace();
		}

		return distribuidores;
		

	
	}
	
	public Distribuidor obtenDistri(String nombre) {
		
		Connection conexion = null;
		PreparedStatement state = null;
		Distribuidor d = null;
		ResultSet rs =null;

		try {

			conexion = origendatos.getConnection();
			String sql = "SELECT * FROM DISTRIBUIDOR WHERE NOMBRE = ?";

			state = conexion.prepareStatement(sql);

			state.setString(1, nombre);

			rs = state.executeQuery();

			while(rs.next()) {
				
				d = new Distribuidor(rs.getInt(1), rs.getString(2));
			}
			

			state.close();
			conexion.close();

		} catch (Exception e) {

			e.printStackTrace();

		}

		return d;
		
	}
	
	public Distribuidor obtenDistribuidor_porID(int id_dist) {
		
		Connection conexion = null;
		PreparedStatement state = null;
		Distribuidor d = null;
		ResultSet rs =null;

		try {

			conexion = origendatos.getConnection();
			String sql = "SELECT * FROM DISTRIBUIDOR WHERE ID_DISTRIBUIDOR = ?";

			state = conexion.prepareStatement(sql);

			state.setInt(1, id_dist);

			rs = state.executeQuery();

			while(rs.next()) {
				
				d = new Distribuidor(rs.getInt(1), rs.getString(2));
			}
			

			state.close();
			conexion.close();

		} catch (Exception e) {

			e.printStackTrace();

		}

		return d;
	}
	


	
	public String crearXML_Distribuidores(List<Distribuidor> listaArticulos) {
		
		
		 String s = "";
	        String line;
	          
	      try{
	           
	              DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
	              DocumentBuilder builder = factory.newDocumentBuilder();
	              Document document = builder.newDocument();
	              
	              Element root = document.createElement("distribuidores");
	              document.appendChild(root);
	              
	              Iterator<Distribuidor> it = listaArticulos.iterator();
	              
	              while(it.hasNext()){
	                  
	            	  Distribuidor p = it.next();
	                  
	                  Element art = document.createElement("distribuidor");
	                  root.appendChild(art);
	                 
	                  Element id_dist = document. createElement("id");
	                  art.appendChild(id_dist);
	                  id_dist.appendChild(document.createTextNode(String.valueOf(p.getId())));
	                  
	                  
	                  Element nombre = document. createElement("nombre");
	                  art.appendChild(nombre);
	                  nombre.appendChild(document.createTextNode(p.getNombre()));
         
	              }
	              
	              
	              TransformerFactory tFactory = TransformerFactory.newInstance();
	              Transformer transformer = tFactory.newTransformer();
	              DOMSource source = new DOMSource(document);
	              StreamResult result = new StreamResult(new File(FILENAME_DIST));

	              transformer.transform(source, result);
	              
	              File ar = new File(FILENAME_DIST);
	              FileReader f = new FileReader(ar);
	              BufferedReader b = new BufferedReader(f); 
	              while((line = b.readLine())!=null) {
	                  s= s + line +"\n";
	                  
	              } 
	              
	              System.out.println(ar.getAbsolutePath());
	              
	          }catch( IOException | ParserConfigurationException | TransformerException | DOMException e){
	              
	              e.printStackTrace();
	          }
	     
	          
	          return s;
	}

	public void eliminaDistribuidor(int parseInt) throws SQLException {

			Connection conexion = null;
			PreparedStatement state = null;

			try {

				conexion = origendatos.getConnection();
				String sql = "DELETE FROM DISTRIBUIDOR WHERE ID_DISTRIBUIDOR=?";

				state = conexion.prepareStatement(sql);

				state.setInt(1, parseInt);


				state.execute();



			} catch (Exception e) {

				e.printStackTrace();

			}finally {
				
				state.close();
				conexion.close();
			}
		
	}

	
	

	public String crearXML_un_Distribuidor(Distribuidor d) {
		
		 String s = "";
	        String line;
	          
	      try{
	           
	              DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
	              DocumentBuilder builder = factory.newDocumentBuilder();
	              Document document = builder.newDocument();
	              
	              Element root = document.createElement("distribuidor_pedido");
	              document.appendChild(root);

	                  
	                  Element dis = document.createElement("distribuidor");
	                  root.appendChild(dis);
	                 
	                  Element id_distribuidor = document. createElement("id_distribuidor");
	                  dis.appendChild(id_distribuidor);
	                  id_distribuidor.appendChild(document.createTextNode(String.valueOf(d.getId())));
	                  
	                  
	                  Element nombre = document.createElement("nombre");
	                  dis.appendChild(nombre);
	                  nombre.appendChild(document.createTextNode(String.valueOf(d.getNombre())));
	   

	                  
        
	              
	              
	              
	              TransformerFactory tFactory = TransformerFactory.newInstance();
	              Transformer transformer = tFactory.newTransformer();
	              DOMSource source = new DOMSource(document);
	              StreamResult result = new StreamResult(new File(FILENAME_un_dist));

	              transformer.transform(source, result);
	              
	              File ar = new File(FILENAME_un_dist);
	              FileReader f = new FileReader(ar);
	              BufferedReader b = new BufferedReader(f); 
	              while((line = b.readLine())!=null) {
	                  s= s + line +"\n";
	                  
	              } 
	              
	              System.out.println(ar.getAbsolutePath());
	              
	          }catch( IOException | ParserConfigurationException | TransformerException | DOMException e){
	              
	              e.printStackTrace();
	          }
	     
	          
	          return s;
	}



}
