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

import org.apache.commons.dbcp.BasicDataSource;
import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import uned.pfg.bean.Almacen;
import uned.pfg.bean.Articulo;
import uned.pfg.bean.ArticuloPedido;
import uned.pfg.ws.PoolConexiones;


public class AlmacenDAO {
	
	private BasicDataSource origendatos;
	private Almacen almacen;
	private final String ALMACEN = "XML_env_almacen.xml";
	private PedidoDAO pedidoDAO;
	
	public AlmacenDAO(BasicDataSource origendatos) {
		
		origendatos = PoolConexiones.getInstance().getConnection();
		
		this.origendatos = origendatos;
		
	}

	public Almacen comprobarArticuloEnAlmacen(ArticuloPedido artPed) {
		
		
		if(estaEnAlmacen(artPed.getArticulo())) {
			
			return almacen;
			
		}else {
			
			return null;
		}

	}
	
	public boolean estaEnAlmacen(Articulo art) {
		
		Connection conexion =null;
		PreparedStatement state = null;
		ResultSet rs;
		boolean flag = false;
		try {
			
			conexion = origendatos.getConnection();
			
			String sql = "SELECT * FROM ALMACEN WHERE ID_ARTICULO=?";
			
			state = conexion.prepareStatement(sql);
			
			state.setInt(1, art.getId_articulo());
			rs = state.executeQuery();
			
			while(rs.next()) {
				
				 almacen = new Almacen(rs.getInt(1), rs.getInt(2), rs.getInt(3));
				flag = true;
				
				
			}
			state.close();
			conexion.close();
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		return flag;
		
		
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
	
	public void altaProduccionArticulo(ArticuloPedido articuloPedido) {
		
		
		//Buscar en el almacen el articulo
		Almacen almacen = comprobarArticuloEnAlmacen(articuloPedido);
		pedidoDAO = new PedidoDAO(origendatos);
		List<ArticuloPedido> list = pedidoDAO.obtenArticulosPedidoSinRealizar(articuloPedido.getArticulo().getId_articulo());
		
		
		if(almacen != null) {//quiere decir que ha encontrado el articulo en el almacen
			
			int sumalibreTotal = almacen.getCantidad_libre() + articuloPedido.getCant();
			if(list.isEmpty()) {
				
				almacen.setCantidad_almacenada(almacen.getCantidad_almacenada()+articuloPedido.getCant());
				almacen.setCantidad_libre(sumalibreTotal);
				
				
			}else {
			
			
				int sobrante = actualizarArticulos(list, sumalibreTotal);
				
				almacen.setCantidad_almacenada(almacen.getCantidad_almacenada()+articuloPedido.getCant());
				almacen.setCantidad_libre(sobrante);			
			}
	
			actualizaArticuloAlmacen(almacen, articuloPedido.getArticulo().getId_articulo());
			
			
		}else { //almacen = null y por tanto no hay ninguno en el almacen
			

			introducirArticuloEnAlmacen(articuloPedido);
			
			//si la lista de articulosPedidoSinRealizar es diferente de vacia
			if(!list.isEmpty()) {
				
				Almacen alm = comprobarArticuloEnAlmacen(articuloPedido);
				int suma = alm.getCantidad_libre();
				int sob = actualizarArticulos(list, suma);
				if(sob != suma) {
				alm.setCantidad_libre(sob);
				actualizaArticuloAlmacen(alm, articuloPedido.getArticulo().getId_articulo());
				}
			}
			
		}
		
		
	}
	
	private void introducirArticuloEnAlmacen(ArticuloPedido articuloPedido) {
		
		Connection conexion =null;
		PreparedStatement state = null;
		
		
		try {
			
			conexion = origendatos.getConnection();
			
			String sql = "INSERT INTO ALMACEN "
					+ "(ID_ARTICULO, CANTIDAD_ALMACENADA, CANTIDAD_LIBRE) "
					+ "VALUES (?,?,?)";
			
			state = conexion.prepareStatement(sql);
			
			state.setInt(1, articuloPedido.getArticulo().getId_articulo());
			state.setInt(2, articuloPedido.getCant());
			state.setInt(3, articuloPedido.getCant());
			
			
			state.execute();
			

			state.close();
			conexion.close();
			
		}catch (Exception e) {
			
			e.printStackTrace();
		}
		
	}

	private void actualizaArticuloAlmacen(Almacen alm, int id_articulo) {


		Connection conexion =null;
		PreparedStatement state = null;
		
		
		try {
			
			conexion = origendatos.getConnection();
			
			String sql = "UPDATE ALMACEN SET CANTIDAD_ALMACENADA =?, "
					+ "CANTIDAD_LIBRE = ? WHERE  ID_ARTICULO = ?";
			
			state = conexion.prepareStatement(sql);
			
			
			
			state.setInt(1, alm.getCantidad_almacenada());
			state.setInt(2, alm.getCantidad_libre());
			state.setInt(3, id_articulo);
			
			state.execute();
			

			state.close();
			conexion.close();
			
		}catch (Exception e) {
			
			e.printStackTrace();
		}
		
		
	}

	private int actualizarArticulos(List<ArticuloPedido> list, int sumalibreTotal) {
		
		Iterator<ArticuloPedido> it = list.iterator();
		
		
		while(it.hasNext()) {
			
			ArticuloPedido artPed = it.next();
			
			if(sumalibreTotal >= artPed.getCant()) {
				
				sumalibreTotal = sumalibreTotal - artPed.getCant();				
				pedidoDAO.actualizarRealizado(artPed);
				
			}	
			
		}
		
		return sumalibreTotal;
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
