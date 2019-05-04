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

import uned.pfg.bean.Almacen;
import uned.pfg.bean.Articulo;
import uned.pfg.bean.ArticuloPedido;
import uned.pfg.bean.Pedido;


public class PedidoDAO {

		private DataSource origendatos;
		private final String FILENAME = "XML.xml";
		private final String FILENAME_ARTICULO = "XML_ART.xml";
		private final String FILENAME_artSinRealizar = "XML_ARTsinRealizar.xml";
		private AlmacenDAO almacenDAO;
		private ArticuloDAO articuloDAO;
	
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
			
			
			java.util.Date envio = p.getFecha_envio();
			
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

	
	public int tienePedidoDistribuidor(int parseInt) {
		Connection conexion = null;
		PreparedStatement state =null;
		ResultSet rs = null;
		int result = 0;
		try {
			
			conexion = origendatos.getConnection();
			
			String sql = "SELECT ID_PEDIDO FROM PEDIDO WHERE ID_DISTRIBUIDOR = ? AND ESTADO =?";
			
			state = conexion.prepareStatement(sql);
			
			
			state.setInt(1, parseInt);
			state.setString(2, "En Tramite");
					
			rs = state.executeQuery();
			
			while(rs.next()) {
				
				result++;
			}
			
			state.close();
			conexion.close();
		
		}catch (Exception e) {

				e.printStackTrace();
		}
		
		return result;
	
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
		almacenDAO = new AlmacenDAO(origendatos);
		boolean realizado = false;
			
		Iterator<ArticuloPedido> it = listaArticulos.iterator();
		
		while(it.hasNext()) {
			
			ArticuloPedido artPed = it.next();
			/////////////////////////////////////////////////////////////////////////////////////////////////
			/////////////////////////////////////////////////////////////////////////////////////////////////
			/////////////////////////////////////////////////////////////////////////////////////////////////
			/////////////////////////////////////////////////////////////////////////////////////////////////
			Almacen alm = almacenDAO.comprobarArticuloEnAlmacen(artPed);
						
			if(alm.getCantidad_libre()>=artPed.getCant() && alm != null) {
				
				realizado = true;
				
				
				almacenDAO.actualizarCantidadLibre(artPed, alm.getCantidad_libre());
				
			}
			/////////////////////////////////////////////////////////////////////////////////////////////////
			/////////////////////////////////////////////////////////////////////////////////////////////////
			/////////////////////////////////////////////////////////////////////////////////////////////////
			
			try {
				
				conexion = origendatos.getConnection();
			String sql ="INSERT INTO ARTICULO_PEDIDO (id_pedido, id_articulo, cantidad, realizado, embalado)"
					+ "VALUES (?,?,?,?,?) ";
			state = conexion.prepareStatement(sql);
			
			state.setInt(1, p.getId_pedido());
			state.setInt(2, artPed.getArticulo().getId_articulo());
			state.setInt(3, artPed.getCant());
			state.setBoolean(4, realizado);
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
				
				int id_pedido = rs.getInt(1);
				int id_dist = rs.getInt(2);
				Date f_realizado = rs.getDate(3);
				Date f_envio = rs.getDate(4);
				String estado = rs.getString(5);
				//List<ArticuloPedido> articulosPedido = obtenArticulosPedido(id_pedido);
				
				p = new Pedido(id_pedido, id_dist, f_realizado, f_envio, estado);
				lista.add(p);
			}
			
			state.close();
			conexion.close();
			
		}catch (Exception e) {
			
			e.printStackTrace();
		}
		
		return lista;
		
	}


	public List<ArticuloPedido> obtenArticulosPedido(int id_pedido) {
		
		List<ArticuloPedido> listaArt = new ArrayList<ArticuloPedido>();
		
		Connection conexion = null;
		PreparedStatement state = null;
		ResultSet rs = null;
		ArticuloPedido a_p = null;
		
		
		try {
			
			conexion = origendatos.getConnection();
			
			String sql = "SELECT * FROM ARTICULO_PEDIDO WHERE ID_PEDIDO=?";
			
			state = conexion.prepareStatement(sql);
			
			state.setInt(1, id_pedido);
			
			rs = state.executeQuery();
			
			while(rs.next()) {
				
				int id_articulo = rs.getInt(3);
				int cantidad = rs.getInt(4);
				boolean realizado = rs.getBoolean(5);
				boolean embalado = rs.getBoolean(6);
				
				a_p = new ArticuloPedido(obtenArticulo(id_articulo), cantidad, realizado, embalado);
				
				listaArt.add(a_p);
			}
			
			state.close();
			conexion.close();
			
		}catch (Exception e) {
			
			e.printStackTrace();
		}
		
		return listaArt;
	}
	
	private Articulo obtenArticulo (int id_articulo) {
		
		
		Articulo art = null;
		Connection conexion = null;
		PreparedStatement state =null;
		ResultSet rs =null;
		
		
		try {
			
			conexion = origendatos.getConnection();
			
			String sql = "SELECT * FROM ARTICULO WHERE ID_ARTICULO = ?";
			
			state = conexion.prepareStatement(sql);
			state.setInt(1, id_articulo);
			
			rs = state.executeQuery();
			
			while(rs.next()) {
			String nombre = rs.getString(2);
			Date entrada  =rs.getDate(3);
			Double precio  = rs.getDouble(4);

				art = new Articulo(id_articulo, nombre, entrada, precio);
			}
			
			state.close();
			conexion.close();
			
		}catch (Exception e) {
			
			e.printStackTrace();
		}
		
		return art;
		
	}
	
	
	public List<ArticuloPedido> articulosSinRealizar(){
		
		List<ArticuloPedido> lista = new ArrayList<ArticuloPedido>();
		
		Connection conexion = null;
		
		
		articuloDAO = new ArticuloDAO(origendatos);
		almacenDAO = new AlmacenDAO(origendatos);
		try {
			
			conexion = origendatos.getConnection();
			
		       String sql = "SELECT ID_ARTICULO, SUM(CANTIDAD) FROM ARTICULO_PEDIDO WHERE REALIZADO = 0 "
		               + "GROUP BY ID_ARTICULO";
		        Statement  state = conexion.createStatement();
		             
		        ResultSet rs = state.executeQuery(sql);
		             

		        while(rs.next()){

					Articulo articulo = articuloDAO.SeleccionarArticulo(rs.getInt(1));
					int cantidad = rs.getInt(2);
					System.out.println(articulo.toString() + "  " + cantidad);
					ArticuloPedido artPed = new ArticuloPedido(articulo, cantidad);					
					if(almacenDAO.estaEnAlmacen(articulo)) {
						
						Almacen almacen = almacenDAO.comprobarArticuloEnAlmacen(artPed);
						artPed.setCant(artPed.getCant()-almacen.getCantidad_libre());
		
					}
					
					lista.add(artPed);

		        }
			
			
			state.close();
			conexion.close();
			
		}catch (Exception e) {
			
			e.printStackTrace();
		}
		
		return lista;
		
		
	}
	
	
	public List<Pedido> Pedidos_que_tiene_el_articulo(int id_articulo) {
		List<Pedido> pedidos = new ArrayList<Pedido>();
		Connection conexion = null;
		PreparedStatement state =null;
		ResultSet rs =null;
		
		
		try {
			
			conexion = origendatos.getConnection();
			
			String sql = "SELECT * FROM ARTICULO_PEDIDO WHERE ID_ARTICULO = ?";
			
			state = conexion.prepareStatement(sql);
			state.setInt(1, id_articulo);
			
			rs = state.executeQuery();
			
			while(rs.next()) {

				int id_pedido = rs.getInt(2);
				
				Pedido pedido = devolverPedidoPorID(id_pedido);
				pedidos.add(pedido);
				
			}
			
			state.close();
			conexion.close();
			
		}catch (Exception e) {
			
			e.printStackTrace();
		}
		
		return pedidos;
		
	}
	
    private Pedido devolverPedidoPorID(int id_pedido) {
    	Pedido p =null;
		Connection conexion = null;
		PreparedStatement state =null;
		ResultSet rs =null;
		
		
		try {
			
			conexion = origendatos.getConnection();
			
			String sql = "SELECT * FROM PEDIDO WHERE ID_PEDIDO = ?";
			
			state = conexion.prepareStatement(sql);
			state.setInt(1, id_pedido);
			
			rs = state.executeQuery();
			
			while(rs.next()) {

				
				p = new Pedido(rs.getInt(1), 
						rs.getInt(2), 
						rs.getDate(3), 
						rs.getDate(4), 
						rs.getString(5));
				
			}
			
			state.close();
			conexion.close();
			
		}catch (Exception e) {
			
			e.printStackTrace();
		}
		
		return p;
	}
    
    
    public int pedidosActivos(int id_distribuidor) {
    	
    	Connection conexion = null;
		PreparedStatement state =null;
		ResultSet rs =null;
		
		
		try {
			
			conexion = origendatos.getConnection();
			
			String sql = "SELECT COUNT( ID_DISTRIBUIDOR ) AS SUMA " + 
					"FROM PEDIDO " + 
					"WHERE ID_DISTRIBUIDOR = ? " + 
					"AND ESTADO = \"En Tramite\"";
			
			state = conexion.prepareStatement(sql);
			state.setInt(1, id_distribuidor);
			
			rs = state.executeQuery();
			
			while(rs.next()) {

				
				return rs.getInt(1);
				
			}
			
			state.close();
			conexion.close();
			
		}catch (Exception e) {
			
			e.printStackTrace();
		}
    	
    	
		return 0;
    	
    	
    	 
    }


	public String crearXML_ArticulosSinRealizar (List<ArticuloPedido> lista){
        
        String s = "";
        String line;
          
      try{
           
              DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
              DocumentBuilder builder = factory.newDocumentBuilder();
              Document document = builder.newDocument();
              
              Element root = document.createElement("articulosSinRealizar");
              document.appendChild(root);
              
              Iterator<ArticuloPedido> it = lista.iterator();
              
              while(it.hasNext()){
                  
            	  ArticuloPedido p = it.next();
                  
                  Element pedido = document.createElement("articulo");
                  root.appendChild(pedido);
                 
                  Element id_articulo = document. createElement("id_articulo");
                  pedido.appendChild(id_articulo);
                  id_articulo.appendChild(document.createTextNode(String.valueOf(p.getArticulo().getId_articulo())));
                  
                  
                  Element cantidad = document.createElement("cantidad");
                  pedido.appendChild(cantidad);
                  cantidad.appendChild(document.createTextNode(String.valueOf(p.getCant())));

              }
              
              
              TransformerFactory tFactory = TransformerFactory.newInstance();
              Transformer transformer = tFactory.newTransformer();
              DOMSource source = new DOMSource(document);
              StreamResult result = new StreamResult(new File(FILENAME_artSinRealizar));

              transformer.transform(source, result);
              
              File ar = new File(FILENAME_artSinRealizar);
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
	
	
    public String crearXML (List<Pedido> lista){
        
        String s = "";
        String line;
          
      try{
           
              DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
              DocumentBuilder builder = factory.newDocumentBuilder();
              Document document = builder.newDocument();
              
              Element root = document.createElement("pedidos");
              document.appendChild(root);
              
              Iterator<Pedido> it = lista.iterator();
              
              while(it.hasNext()){
                  
            	  Pedido p = it.next();
                  
                  Element pedido = document.createElement("pedido");
                  root.appendChild(pedido);
                 
                  Element idpedido = document. createElement("id_pedido");
                  pedido.appendChild(idpedido);
                  idpedido.appendChild(document.createTextNode(String.valueOf(p.getId_pedido())));
                  
                  
                  Element id_distribuidor = document.createElement("id_distribuidor");
                  pedido.appendChild(id_distribuidor);
                  id_distribuidor.appendChild(document.createTextNode(String.valueOf(p.getId_distribuidor())));
   
                  Element fecha_pedido = document.createElement("fecha_pedido");
                  pedido.appendChild(fecha_pedido);
                  fecha_pedido.appendChild(document.createTextNode(String.valueOf(p.getFecha_entrada())));
                  
                  Element fecha_envio = document.createElement("fecha_envio");
                  pedido.appendChild(fecha_envio);
                  fecha_envio.appendChild(document.createTextNode(String.valueOf(p.getFecha_envio())));
                  
                  Element estado = document.createElement("estado");
                  pedido.appendChild(estado);
                  estado.appendChild(document.createTextNode(p.getEstado()));
                                 
              }
              
              
              TransformerFactory tFactory = TransformerFactory.newInstance();
              Transformer transformer = tFactory.newTransformer();
              DOMSource source = new DOMSource(document);
              StreamResult result = new StreamResult(new File(FILENAME));

              transformer.transform(source, result);
              
              File ar = new File(FILENAME);
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


	public String crearXML_Articulos(List<ArticuloPedido> listaArticulos) {
		
		
		 String s = "";
	        String line;
	          
	      try{
	           
	              DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
	              DocumentBuilder builder = factory.newDocumentBuilder();
	              Document document = builder.newDocument();
	              
	              Element root = document.createElement("articulos");
	              document.appendChild(root);
	              
	              Iterator<ArticuloPedido> it = listaArticulos.iterator();
	              
	              while(it.hasNext()){
	                  
	            	  ArticuloPedido p = it.next();
	                  
	                  Element art = document.createElement("articulo");
	                  root.appendChild(art);
	                 
	                  Element id_articulo = document. createElement("id_articulo");
	                  art.appendChild(id_articulo);
	                  id_articulo.appendChild(document.createTextNode(String.valueOf(p.getArticulo().getId_articulo())));
	                  
	                  
	                  Element nombre = document.createElement("nombre");
	                  art.appendChild(nombre);
	                  nombre.appendChild(document.createTextNode(String.valueOf(p.getArticulo().getNombre())));
	   
	                  Element fecha_entrada = document.createElement("fecha_entrada");
	                  art.appendChild(fecha_entrada);
	                  fecha_entrada.appendChild(document.createTextNode(String.valueOf(p.getArticulo().getFecha_entrada())));
	                  
	                  Element precio = document.createElement("precio");
	                  art.appendChild(precio);
	                  precio.appendChild(document.createTextNode(String.valueOf(p.getArticulo().getPrecio())));
	                  
	                  Element cantidad = document.createElement("cantidad");
	                  art.appendChild(cantidad);
	                  cantidad.appendChild(document.createTextNode(String.valueOf(p.getCant())));
	                  
	                  Element realizado = document.createElement("realizado");
	                  art.appendChild(realizado);
	                  realizado.appendChild(document.createTextNode(String.valueOf(p.isRealizado())));
	                  
	                  Element embalado = document.createElement("embalado");
	                  art.appendChild(embalado);
	                  embalado.appendChild(document.createTextNode(String.valueOf(p.isEmbalado())));
             
	              }
	              
	              
	              TransformerFactory tFactory = TransformerFactory.newInstance();
	              Transformer transformer = tFactory.newTransformer();
	              DOMSource source = new DOMSource(document);
	              StreamResult result = new StreamResult(new File(FILENAME_ARTICULO));

	              transformer.transform(source, result);
	              
	              File ar = new File(FILENAME_ARTICULO);
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
