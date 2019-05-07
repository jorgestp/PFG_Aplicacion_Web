package uned.pfg.dao;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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

import uned.pfg.bean.Articulo;
import uned.pfg.bean.ArticuloPedido;

public class ArticuloDAO {

	
	private DataSource origendatos;
	private final String FILENAME_ARTICULOS = "XML_articulos.xml";
	private final String FILENAME_ARTICULO_selec = "XML_articulo_selec";
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
				Date fecha = rs.getDate(3);
				double precio = rs.getDouble(4);

				articulos.add(new Articulo(id_articulo, nombre, fecha, precio));
			}
			
			state.close();
			conexion.close();
			
		}catch (Exception e) {

			e.printStackTrace();
		}
		
		
		return articulos;
		
		
	}
	
	public boolean introducirArticulo(Articulo a) {
		
		
		Connection conexion = null;
		PreparedStatement state = null;

		try {

			conexion = origendatos.getConnection();

			String sql = "INSERT INTO ARTICULO (nombre, "
					+ "fecha_entrada, precio) "
					+ "VALUES ( ?,?,?)";

			state = conexion.prepareStatement(sql);

			state.setString(1, a.getNombre());
	
			java.util.Date f = a.getFecha_entrada();

			java.sql.Date fecha = new java.sql.Date(f.getTime());

			state.setDate(2, fecha);
			state.setDouble(3, a.getPrecio());


			state.execute();

			state.close();
			conexion.close();
			return true;

		} catch (Exception e) {

			e.printStackTrace();
			return false;
		}
	}
	
	
	
	public Articulo SeleccionarArticulo(int id) {
		
		Connection conexion = null;
		PreparedStatement state = null;
		ResultSet rs =null;
		Articulo a = null;

		try {

			conexion = origendatos.getConnection();

			String sql = "SELECT * FROM ARTICULO WHERE ID_ARTICULO=?";

			state = conexion.prepareStatement(sql);

			state.setInt(1, id);
	
			rs = state.executeQuery();
			
			while(rs.next()) {
				
				a = new Articulo(id, rs.getString(2), rs.getDate(3), rs.getDouble(4));
			}

			state.close();
			conexion.close();
			
			return a;

		} catch (Exception e) {

			e.printStackTrace();
			
		}
		
		return a;
		
	}
	
	public String crearXML_Articulos(List<Articulo> listaArticulos) {
		
		
		 String s = "";
	        String line;
	          
	      try{
	           
	              DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
	              DocumentBuilder builder = factory.newDocumentBuilder();
	              Document document = builder.newDocument();
	              
	              Element root = document.createElement("articulos");
	              document.appendChild(root);
	              
	              Iterator<Articulo> it = listaArticulos.iterator();
	              
	              while(it.hasNext()){
	                  
	            	  Articulo p = it.next();
	                  
	                  Element art = document.createElement("articulo");
	                  root.appendChild(art);
	                 
	                  Element id_articulo = document. createElement("id_articulo");
	                  art.appendChild(id_articulo);
	                  id_articulo.appendChild(document.createTextNode(String.valueOf(p.getId_articulo())));
	                  
	                  
	                  Element nombre = document.createElement("nombre");
	                  art.appendChild(nombre);
	                  nombre.appendChild(document.createTextNode(String.valueOf(p.getNombre())));
	   
	                  Element fecha_entrada = document.createElement("fecha_entrada");
	                  art.appendChild(fecha_entrada);
	                  fecha_entrada.appendChild(document.createTextNode(String.valueOf(p.getFecha_entrada())));
	                  
	                  Element precio = document.createElement("precio");
	                  art.appendChild(precio);
	                  precio.appendChild(document.createTextNode(String.valueOf(p.getPrecio())));
	                  
            
	              }
	              
	              
	              TransformerFactory tFactory = TransformerFactory.newInstance();
	              Transformer transformer = tFactory.newTransformer();
	              DOMSource source = new DOMSource(document);
	              StreamResult result = new StreamResult(new File(FILENAME_ARTICULOS));

	              transformer.transform(source, result);
	              
	              File ar = new File(FILENAME_ARTICULOS);
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
	
	public String crearXML_artSelec(Articulo a) {
		
		
		 String s = "";
	        String line;
	          
	      try{
	           
	              DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
	              DocumentBuilder builder = factory.newDocumentBuilder();
	              Document document = builder.newDocument();
	              
	              Element root = document.createElement("articulo_selec");
	              document.appendChild(root);

	                  
	                  Element art = document.createElement("articulo");
	                  root.appendChild(art);
	                 
	                  Element id_articulo = document. createElement("id_articulo");
	                  art.appendChild(id_articulo);
	                  id_articulo.appendChild(document.createTextNode(String.valueOf(a.getId_articulo())));
	                  
	                  
	                  Element nombre = document.createElement("nombre");
	                  art.appendChild(nombre);
	                  nombre.appendChild(document.createTextNode(String.valueOf(a.getNombre())));
	   
	                  Element fecha_entrada = document.createElement("fecha_entrada");
	                  art.appendChild(fecha_entrada);
	                  fecha_entrada.appendChild(document.createTextNode(String.valueOf(a.getFecha_entrada())));
	                  
	                  Element precio = document.createElement("precio");
	                  art.appendChild(precio);
	                  precio.appendChild(document.createTextNode(String.valueOf(a.getPrecio())));
	                  
           
	              
	              
	              
	              TransformerFactory tFactory = TransformerFactory.newInstance();
	              Transformer transformer = tFactory.newTransformer();
	              DOMSource source = new DOMSource(document);
	              StreamResult result = new StreamResult(new File(FILENAME_ARTICULO_selec));

	              transformer.transform(source, result);
	              
	              File ar = new File(FILENAME_ARTICULO_selec);
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
