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

/**
 * Clase que representa el Objeto de Acceso a Datos almacenados del Almacen del sistema.
 * 
 * Se hacen las pertinentes consultas de actualizacion, inserccion o borrado
 * de todo lo referente al almacen  que tiene el sistema y que se detallarán
 * en las funciones correspondientes.
 * 
 * 
 * @author JORGE VILLALBA RUIZ 47536486V
 * @version 1.0
 */
public class AlmacenDAO {
	
	private BasicDataSource origendatos;
	private Almacen almacen;
	private final String ALMACEN = "XML_env_almacen.xml";
	private PedidoDAO pedidoDAO;
	
	/**
	 * Constructor que inicializa la variable pasada por parametros con el pool de conexiones
	 * establecido para la base de datos, y ademas asigna esta variable al campo de clase
	 * correspondiente al DataSource
	 * @param origendatos Objeto que representa el pool de conexiones y el que es inicializado
	 * y luego asignado al campo de clase
	 */
	public AlmacenDAO(BasicDataSource origendatos) {
		
		origendatos = PoolConexiones.getInstance().getConnection();
		
		this.origendatos = origendatos;
		
	}

	/**
	 * Comprueba si el objeto que se le pasa por parametro está en el almacén.
	 * @param artPed Objeto que representa a un bean de un articulo de un pedido
	 * @return Null en el caso de que el objeto pasado por parametro no este en el objeto,
	 * o un Objeto de tipo Almacen con toda la informacion del articulo encontrado
	 */
	public Almacen comprobarArticuloEnAlmacen(ArticuloPedido artPed) {
		
		
		if(estaEnAlmacen(artPed.getArticulo())) {
			
			return almacen;
			
		}else {
			
			return null;
		}

	}
	
	/**
	 * Hace una busqueda en el almacen del articulo que se le pasa por parametro, en 
	 * busca de algun registro cuyo id de articulo coincida con el que id del articulo
	 * del objeto que se le pasa a la funcion.
	 * @param art Objeto que representa a un articulo que buscamos en el almacen
	 * @return True si el articulo es encontrado en el almacen, False si no 
	 * esta en el almacen
	 */
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

	/**
	 * Funcion que actualiza la cantidad libre que hay de un articulo en el almacen.
	 * Se busca el articulo mediante el objeto de tipo ArticuloPedido pasado por 
	 * parametro, y a ese objeto encontrado, se le resta la cantidad libre que tenia
	 * con el valor entero pasado tambien por parametro y dando consigo a la nueva cantidad
	 * libre que queda actualizada
	 * @param artPed Bean que representa al Articulo del Pedido que queremos actualizar en el 
	 * sistema
	 * @param cantidadLibre Valor entero que representa a la cantidad libre que tiene en el almacen
	 * el articulo
	 */
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
	
	
	/**
	 * Funcion que tiene por objetivo dar entrada a una nueva produccion que se ha 
	 * hecho de un articulo.
	 * 
	 * En primer lugar se comprueba si ese articulo pasado por parametro está ya en el 
	 * almacen. Si se encuentra, se suma la cantidad del articulo que se quiere dar entrada
	 * junto a la cantidad libre que tiene ese mismo articulo en el almacen.
	 * Luego se obtiene los articulos que estan sin realizar y que tienen el mismo id de articulo
	 * que el pasado por parametro. Si esa lista no esta vacia, se van actualizando a realizado
	 * aquellos articulos de Pedido cuya cantidad sea menor que la dada de alta. Por ultimo se 
	 * actualiza el articulo en almacen con la nueva cantidad que quede libre y la almacenada.
	 * En el caso de que no encuentre ningun articulo en el almacen, lo introduce y de nuevo
	 * pasa a actualizar los valores de los articulos de pedido que estan aun sin realizar.
	 * @param articuloPedido Bean de Articulo de Pedido que se quiere dar de alta en el sistema
	 */
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
	/**
	 *Funcion que inserta en el almacen un Articulo de Pedido pasado por parametro. 
	 * @param articuloPedido Bean que se quiere insertar en la BBDD
	 */
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

	/*
	 * Funcion que actualiza los valores de un articulo en el almacen
	 * 
	 */
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

	/*
	 *Funcion que actualiza el valor de realizado de los articulos que han sido 
	 *pasados por parametro en la lista.
	 *
	 *Esa lista contiene todos el mismo articulo pero con valores de cantidad diferentes, resultante
	 *de seleccionar todos los articulos con un mismo id de articulo y el valor de realizado en false.
	 *El otro parametro es la cantidad que hay libre total de ese articulo en cuestion.
	 *
	 *Se va recorriendo la lista y se va comprobando que la cantidad libre total sea mayor
	 *que la cantidad pedida del articulo que esta seleccionado en el recorrido.
	 *Si es asi, el parametro con tipo entero se va actualizando, de modo que se le resta a esa
	 *cantidad Libre el valor de la cantidad del articulo en cuestion. Por ulitmo, habia que actualizar
	 *ese articulo seleccionado de la lista, para pasar el valor de Realizado a true.
	 *
	 *El valor devuelto es un entero y corresponde a la cantidad libre que quedaria 
	 *una vez se haya recorrido toda la lista.
	 * 
	 */
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

	/**
	 * Funcion que obtiene todos los bean del almacen con el fin de poder ver el stock
	 * que hay disponieble.
	 * 
	 * @return Lista de bean almacen que representa a los articulos que hay en el Almacen
	 * del sistema.
	 */
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
	
	
	/**
	 * Crea un xml y lo transforma en formato cadena de caracteres que representa todos los
	 * valores de los articulos que hay en el almacen
	 * @param lista Objeto que contiene todos los articulos del almacen
	 * @return String que representa el xml creado
	 */
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
              
              //System.out.println(ar.getAbsolutePath());
              ar.delete();
              
          }catch( IOException | ParserConfigurationException | TransformerException | DOMException e){
              
              e.printStackTrace();
          }
     
          
          return s;
      }
	

}
