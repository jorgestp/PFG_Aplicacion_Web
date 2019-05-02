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

import uned.pfg.bean.Almacen;
import uned.pfg.bean.Articulo;
import uned.pfg.bean.ArticuloPedido;


public class AlmacenDAO {
	
	private DataSource origendatos;
	Almacen almacen;
	private final String ALMACEN = "XML_env_almacen.xml";
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
	
	public List<Almacen> obtenAlmacen(){
		
		List<Almacen> lista = new ArrayList<Almacen>();
		Connection conexion = null;
		Statement state = null;
		ResultSet rs = null;
		Almacen a=null;
		
		try {
			
			conexion = origendatos.getConnection();
			
			String sql = "select * from almacen";
			
			state = conexion.createStatement();
			
			rs = state.executeQuery(sql);
			
			while(rs.next()) {
				
				int id_articulo = rs.getInt(1);
				int cant_alm = rs.getInt(2);
				int cant_libre = rs.getInt(3);
	
				//List<ArticuloPedido> articulosPedido = obtenArticulosPedido(id_pedido);
				
				a =new Almacen(id_articulo, cant_alm, cant_libre);
				lista.add(a);
			}
			
			state.close();
			conexion.close();
			
		}catch (Exception e) {
			
			e.printStackTrace();
		}
		
		return lista;
	}
	
	
	
    public String crearXML (List<Almacen> lista){
        
        String s = "";
        String line;
          
      try{
           
              DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
              DocumentBuilder builder = factory.newDocumentBuilder();
              Document document = builder.newDocument();
              
              Element root = document.createElement("Almacen_empresa");
              document.appendChild(root);
              
              Iterator<Almacen> it = lista.iterator();
              
              while(it.hasNext()){
                  
            	  Almacen a = it.next();
                  
                  Element pedido = document.createElement("almacen");
                  root.appendChild(pedido);
                 
                  Element id_articulo = document. createElement("id_articulo");
                  pedido.appendChild(id_articulo);
                  id_articulo.appendChild(document.createTextNode(String.valueOf(a.getId_articulo())));
                  
                  
                  Element cantidad_almacenada = document.createElement("cantidad_almacenada");
                  pedido.appendChild(cantidad_almacenada);
                  cantidad_almacenada.appendChild(document.createTextNode(String.valueOf(a.getCantidad_almacenada())));
   
                  Element cantidad_libre = document.createElement("cantidad_libre");
                  pedido.appendChild(cantidad_libre);
                  cantidad_libre.appendChild(document.createTextNode(String.valueOf(a.getCantidad_libre())));

                
              }
              
              
              TransformerFactory tFactory = TransformerFactory.newInstance();
              Transformer transformer = tFactory.newTransformer();
              DOMSource source = new DOMSource(document);
              StreamResult result = new StreamResult(new File(ALMACEN));

              transformer.transform(source, result);
              
              File ar = new File(ALMACEN);
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
