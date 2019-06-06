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
import uned.pfg.bean.Pedido;
import uned.pfg.ws.PoolConexiones;

/**
 * Clase que representa el Objeto de Acceso a Datos almacenados del pedido.
 * 
 * Se hacen las pertinentes consultas de actualizacion, inserccion o borrado de
 * todo lo referente a los pedidos que tiene el sistema y que se detallarán en
 * las funciones correspondientes.
 * 
 * 
 * @author JORGE VILLALBA RUIZ 47536486V
 * @version 1.0
 */
public class PedidoDAO {

	// CAMPOS DE CLASE
	private BasicDataSource origendatos;
	private final String FILENAME = "XML.xml";
	private final String FILENAME_ARTICULO = "XML_ART.xml";
	private final String FILENAME_artSinRealizar = "XML_ARTsinRealizar.xml";
	private AlmacenDAO almacenDAO;
	private ArticuloDAO articuloDAO;
	// FIN CAMPOS DE CLASE

	/**
	 * Constructor que inicializa la variable pasada por parametros con el pool de
	 * conexiones establecido para la base de datos, y ademas asigna esta variable
	 * al campo de clase correspondiente al DataSource
	 * 
	 * @param origendatos
	 */
	public PedidoDAO(BasicDataSource origendatos) {

		// Inicializa el parametro con el pool de conexiones
		origendatos = PoolConexiones.getInstance().getConnection();

		// Se asigna el campo de clase con el parametro inizializado.
		this.origendatos = origendatos;

	}

	/**
	 * Funcion que tiene como objetivo insertar en la base de datos un pedido. Se
	 * establece la pertinente conexion a la BBDD usando una conexion del pool que
	 * se ha creado. Se inserta todos los campos de clase del Pedido excepto el id
	 * del pedido, que al ser un campo de la BBDD autoincremental, no hace falta.
	 * 
	 * @param p Objeto representativo a un bean de Pedido
	 * @return Valor entero correspondiente a id de pedido que le ha sido asignado
	 *         en la BBDD
	 */
	public int insertaPedido(Pedido p) {

		Connection conexion = null;
		PreparedStatement state = null;

		try {

			// Se le asigna una conexion que este libre en el pool de conexiones.
			conexion = origendatos.getConnection();

			/*
			 * System.out.println("Nº MAX CONEXIONES ACTIVAS => " +
			 * origendatos.getMaxActive());
			 * System.out.println("Nº MAX CONEXIONES que pueden estar INACTIVAS => " +
			 * origendatos.getMaxIdle());
			 * System.out.println("Nº min CONEXIONES INACTIVAS => " +
			 * origendatos.getMinIdle());
			 * System.out.println("Nº ACTUAL CONEXIONES ACTIVAS => " +
			 * origendatos.getNumActive());
			 * System.out.println("Nº ACTUAL CONEXIONES INACTIVAS => " +
			 * origendatos.getNumIdle());
			 * 
			 * System.out.println(
			 * "###########################################################################"
			 * ); System.out.println(
			 * "###########################################################################"
			 * );
			 */

			// Sentencia SQL para la inserccion del pedido
			String sql = "INSERT INTO PEDIDO (id_distribuidor, fecha_pedido, fecha_envio, estado) "
					+ "VALUES (?,?,?,?)";

			state = conexion.prepareStatement(sql);

			// Se le asigna cada valor de la sentencia SQL con el correspondiente del objeto
			// pedido

			state.setInt(1, p.getId_distribuidor());

			java.util.Date entrada = p.getFecha_entrada();

			java.sql.Date fecha_entrada = new java.sql.Date(entrada.getTime());

			state.setDate(2, fecha_entrada);

			java.util.Date envio = p.getFecha_envio();

			java.sql.Date fecha_envio = new java.sql.Date(envio.getTime());

			state.setDate(3, fecha_envio);

			state.setString(4, p.getEstado());
			// Fin de asignacion de valores

			// Ejecucion de la sentencia SQL
			state.execute();

			// Cierre y liberacion de la conexion
			state.close();
			conexion.close();

			// Obtencion del id de este ultimo pedido introducido
			int id_pedido = obteneridPedido();

			return id_pedido;

		} catch (Exception e) {

			e.printStackTrace();
		}

		return 0;
	}

	/**
	 * Funcion que devuelve el numero de pedidos que un distribuidor tiene en el
	 * estado activos, es decir, que se encuentran en trámite en el sistema.
	 * 
	 * @param id_dist Entero que representa el id del distribuidor.
	 * @return Devuelve el valor entero que representa al numero de pedidos que
	 *         tiene activos en el sitema.
	 */
	public int tienePedidoDistribuidor(int id_dist) {
		Connection conexion = null;
		PreparedStatement state = null;
		ResultSet rs = null;
		int result = 0;
		try {

			conexion = origendatos.getConnection();

			String sql = "SELECT ID_PEDIDO FROM PEDIDO WHERE ID_DISTRIBUIDOR = ? AND ESTADO =?";

			state = conexion.prepareStatement(sql);

			state.setInt(1, id_dist);
			state.setString(2, "En Tramite");

			rs = state.executeQuery();

			while (rs.next()) {

				result++;
			}

			state.close();
			conexion.close();

		} catch (Exception e) {

			e.printStackTrace();
		}

		return result;

	}

	/**
	 * Metodo que sirve para obtener el id del ultimo pedido introducido en el
	 * sistema. Su funcionamiento es el siguiente; Debido a que el valor del id del
	 * pedido es autoincremental, lo que se hace es ordenar de forma descenciente
	 * los pedidos por el valor id_pedido. De esta forma, se recoge el primer valor
	 * de la tabla ordenada, obteniendo así el pedido cuyo id es mas elevado y por
	 * tanto, el ultimo en ser introducido
	 * 
	 * @return Devuelve el id del pedido de este ultimo pedido introducido en la
	 *         BBDD
	 */
	private int obteneridPedido() {
		Connection conexion = null;
		Statement state = null;
		ResultSet rs = null;

		int id = 0;

		try {

			conexion = origendatos.getConnection();

			String sql = "SELECT * FROM PEDIDO ORDER BY id_pedido DESC";

			state = conexion.createStatement();
			rs = state.executeQuery(sql);

			boolean flag = true;
			while (rs.next() && flag) {
				id = rs.getInt(1);
				flag = false;
			}

			state.close();
			conexion.close();
			return id;

		} catch (Exception e) {

			e.printStackTrace();
		}

		return id;
	}

	/**
	 * Funcion que sirve para introducir en la BBDD los articulos que tiene un
	 * pedido.
	 * 
	 * El funcionamiento es el siguiente; Se recoge la lista de articulos del pedido
	 * pasado por parametro y se recorre dicha lista de forma secuencial. A cada
	 * objeto de esta lista, que representa a un bean de ArticuloPedido, se
	 * comprueba si existe o no en el almacen. En caso de que exista, se comprueba
	 * que el valor de cantidad libre de este articulo en el almacen sea mayor que
	 * la cantidad que se requiere de ese articulo. Si es asi, el valor de
	 * "realizado" de ese articulo en el pedido pasa a verdadero, representando de
	 * este modo que ya se encuentra realizado el articulo.
	 * 
	 * Posteriormente se introduce el articulo en la base de datos con todos sus
	 * valores actualizados, como puede ser el caso del valor "realizado".
	 * 
	 * Por ultimo, una vez que todos los articulos han sido introducidos, se
	 * comprueba si todos los articulos de este pedido en cuestion están realizado,
	 * que siendo asi, se le cambiaría el estado de pedido de "En Tramite" a
	 * "Realizado".
	 * 
	 * @param p Objeto que representa a un bean de Pedido
	 * @return Devuelve cierto, si se ha introducido todos los articulos en la base
	 *         de datos de forma correcta, y falso, en el caso de que algun articulo
	 *         no haya sido introducido en el sistema
	 */
	public boolean insertarArticulos(Pedido p) {

		// Obtener la conexion

		Connection conexion = null;
		PreparedStatement state = null;
		List<ArticuloPedido> listaArticulos = p.getArticulos();
		almacenDAO = new AlmacenDAO(origendatos);
		boolean realizado = false;

		Iterator<ArticuloPedido> it = listaArticulos.iterator();

		while (it.hasNext()) {

			ArticuloPedido artPed = it.next();
			/////////////////////////////////////////////////////////////////////////////////////////////////
			/////////////////////////////////////////////////////////////////////////////////////////////////
			/////////////////////////////////////////////////////////////////////////////////////////////////
			/////////////////////////////////////////////////////////////////////////////////////////////////
			Almacen alm = almacenDAO.comprobarArticuloEnAlmacen(artPed);

			if (alm != null) {

				if (alm.getCantidad_libre() >= artPed.getCant() && alm != null) {

					realizado = true;

					almacenDAO.actualizarCantidadLibre(artPed, alm.getCantidad_libre());

				}
			}
			/////////////////////////////////////////////////////////////////////////////////////////////////
			/////////////////////////////////////////////////////////////////////////////////////////////////
			/////////////////////////////////////////////////////////////////////////////////////////////////

			try {

				conexion = origendatos.getConnection();

				String sql = "INSERT INTO ARTICULO_PEDIDO (id_pedido, id_articulo, cantidad, realizado, embalado)"
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

			} catch (Exception e) {
				e.printStackTrace();
				return false;
			}

		}

		estadoPedidoARealizado(p.getId_pedido());
		return true;

	}

	/**
	 * Funcion que obtiene todos los pedidos de un distribuidor en cuestion, cuyo
	 * identificador ha sido pasado por parametro
	 * 
	 * @param id Valor que representa el id del distribuidor y con el que se hará la
	 *           consulta en la BBDD.
	 * @return Lista con todos los pedidos que el distribuidor posee en el sistema
	 */
	public List<Pedido> obtenerPedidos(int id) {

		List<Pedido> lista = new ArrayList<Pedido>();
		Pedido p = null;
		Connection conexion = null;
		PreparedStatement state = null;
		ResultSet rs = null;

		try {

			conexion = origendatos.getConnection();

			String sql = "SELECT * FROM PEDIDO WHERE ID_DISTRIBUIDOR = ?";

			state = conexion.prepareStatement(sql);
			state.setInt(1, id);

			rs = state.executeQuery();

			while (rs.next()) {

				int idpedido = rs.getInt(1);
				Date realizado = rs.getDate(3);
				Date envio = rs.getDate(4);
				String estado = rs.getString(5);

				p = new Pedido(idpedido, realizado, envio, estado);

				lista.add(p);

			}

			state.close();
			conexion.close();

		} catch (Exception e) {

			e.printStackTrace();
		}

		return lista;
	}

	/*
	 * public static String diHola() { // TODO Auto-generated method stub return
	 * "HOLA JORGE"; }
	 */

	/**
	 * Funcion que devuelve todos los pedidos que hay en el sistema.
	 * 
	 * @return Lista de pedidos que hay en el sistema
	 */
	public List<Pedido> obtenPedidos() {

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

			while (rs.next()) {

				int id_pedido = rs.getInt(1);
				int id_dist = rs.getInt(2);
				Date f_realizado = rs.getDate(3);
				Date f_envio = rs.getDate(4);
				String estado = rs.getString(5);
				// List<ArticuloPedido> articulosPedido = obtenArticulosPedido(id_pedido);

				p = new Pedido(id_pedido, id_dist, f_realizado, f_envio, estado);
				lista.add(p);
			}

			state.close();
			conexion.close();

		} catch (Exception e) {

			e.printStackTrace();
		}

		return lista;

	}

	/**
	 * Funcion que devuelve todos los articulos de un pedido en cuestion
	 * 
	 * @param id_pedido Valor que representa el id del pedido y con el que se
	 *                  consultará a la BBDD para poder obtener todos los articulos
	 *                  cuyo id de pedido sea igual al pasado por parametro
	 * @return Lista de beans ArticulosPedidos que representa a los articulos que
	 *         tiene el pedido
	 */
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

			while (rs.next()) {

				int id_articulo = rs.getInt(3);
				int cantidad = rs.getInt(4);
				boolean realizado = rs.getBoolean(5);
				boolean embalado = rs.getBoolean(6);

				a_p = new ArticuloPedido(obtenArticulo(id_articulo), cantidad, realizado, embalado);

				listaArt.add(a_p);
			}

			state.close();
			conexion.close();

		} catch (Exception e) {

			e.printStackTrace();
		}

		return listaArt;
	}

	/**
	 * Devuelve la lista de Articulos cuyo estado realizado sea falso, de modo que
	 * el articulo aun no ha sido fabricado
	 * 
	 * @param id_articulo Valor que representa el id del articulo
	 * 
	 * @return Lista de articulos que estan sin realizar
	 */
	public List<ArticuloPedido> obtenArticulosPedidoSinRealizar(int id_articulo) {

		List<ArticuloPedido> listaArt = new ArrayList<ArticuloPedido>();

		Connection conexion = null;
		PreparedStatement state = null;
		ResultSet rs = null;
		ArticuloPedido a_p = null;

		try {

			conexion = origendatos.getConnection();

			String sql = "SELECT * FROM ARTICULO_PEDIDO WHERE ID_ARTICULO=? AND REALIZADO = 0";

			state = conexion.prepareStatement(sql);

			state.setInt(1, id_articulo);

			rs = state.executeQuery();

			while (rs.next()) {

				int id_pedido = rs.getInt(2);
				int cantidad = rs.getInt(4);
				boolean realizado = rs.getBoolean(5);
				boolean embalado = rs.getBoolean(6);

				a_p = new ArticuloPedido(obtenArticulo(id_articulo), cantidad, devolverPedidoPorID(id_pedido),
						realizado, embalado);

				listaArt.add(a_p);
			}

			state.close();
			conexion.close();

		} catch (Exception e) {

			e.printStackTrace();
		}

		return listaArt;
	}

	/*
	 * Metodo privado que consulta a la BBDD y obtiene los valore del articulo cuyo
	 * id del articulo es igual al pasado por parametro.
	 */
	private Articulo obtenArticulo(int id_articulo) {

		Articulo art = null;
		Connection conexion = null;
		PreparedStatement state = null;
		ResultSet rs = null;

		try {

			conexion = origendatos.getConnection();

			String sql = "SELECT * FROM ARTICULO WHERE ID_ARTICULO = ?";

			state = conexion.prepareStatement(sql);
			state.setInt(1, id_articulo);

			rs = state.executeQuery();

			while (rs.next()) {
				String nombre = rs.getString(2);
				Date entrada = rs.getDate(3);
				Double precio = rs.getDouble(4);

				art = new Articulo(id_articulo, nombre, entrada, precio);
			}

			state.close();
			conexion.close();

		} catch (Exception e) {

			e.printStackTrace();
		}

		return art;

	}

	/**
	 * Funcion que devuelve los articulos y la cantidad de estos que hay que
	 * fabricar para poder satisfacer todos los pedidos que hay en el sistema en ese
	 * momento.
	 * 
	 * Se agrupa por articulos y se suman todas las cantidades de aquellos que aun
	 * no esten fabricados.
	 * 
	 * A cada articulo de esa lista, se comprueba de que exista stock libre en el
	 * almacen, y si es asi, se le resta esa cantidad libre dando como resultado la
	 * cantidad exacta que habria que fabricar para satisfacer los pedidos y que no
	 * quedara nada libre en el almacen.
	 * 
	 * @return Lista de articulos y la cantidad de estos articulos que hay que
	 *         fabricar.
	 */
	public List<ArticuloPedido> articulosSinRealizar() {

		List<ArticuloPedido> lista = new ArrayList<ArticuloPedido>();
		Articulo articulo = null;
		Connection conexion = null;

		articuloDAO = new ArticuloDAO(origendatos);
		almacenDAO = new AlmacenDAO(origendatos);
		try {

			conexion = origendatos.getConnection();

			String sql = "SELECT ID_ARTICULO, SUM(CANTIDAD) FROM ARTICULO_PEDIDO WHERE REALIZADO = 0 "
					+ "GROUP BY ID_ARTICULO";
			Statement state = conexion.createStatement();

			ResultSet rs = state.executeQuery(sql);

			while (rs.next()) {

				int id_art = rs.getInt(1);
				try {

					String sql2 = "SELECT * FROM ARTICULO WHERE ID_ARTICULO=?";

					PreparedStatement state2 = conexion.prepareStatement(sql2);

					state2.setInt(1, id_art);

					ResultSet rs2 = state2.executeQuery();

					while (rs2.next()) {

						articulo = new Articulo(id_art, rs2.getString(2), rs2.getDate(3), rs2.getDouble(4));
					}

					state2.close();

				} catch (Exception e) {

					e.printStackTrace();

				}

				int cantidad = rs.getInt(2);
				System.out.println(articulo.getId_articulo() + " " + articulo.getNombre() + "  " + cantidad);
				ArticuloPedido artPed = new ArticuloPedido(articulo, cantidad);
				if (almacenDAO.estaEnAlmacen(articulo)) {

					Almacen almacen = almacenDAO.comprobarArticuloEnAlmacen(artPed);
					artPed.setCant(artPed.getCant() - almacen.getCantidad_libre());

				}

				lista.add(artPed);

			}

			state.close();
			conexion.close();

		} catch (Exception e) {

			e.printStackTrace();
		}

		return lista;

	}

	/**
	 * Funcion que devuelve una lista de Pedidos que posee el articulo en cuestion
	 * que se esta buscando.
	 * 
	 * @param id_articulo Valor que representa al id del articulo
	 * @return Lista de los Pedidos que tiene el articulo con id pasado por
	 *         parametro.
	 */
	public List<Pedido> Pedidos_que_tiene_el_articulo(int id_articulo) {
		List<Pedido> pedidos = new ArrayList<Pedido>();
		Connection conexion = null;
		PreparedStatement state = null;
		ResultSet rs = null;

		try {

			conexion = origendatos.getConnection();

			String sql = "SELECT * FROM ARTICULO_PEDIDO WHERE ID_ARTICULO = ?";

			state = conexion.prepareStatement(sql);
			state.setInt(1, id_articulo);

			rs = state.executeQuery();

			while (rs.next()) {

				int id_pedido = rs.getInt(2);

				Pedido pedido = devolverPedidoPorID(id_pedido);
				pedidos.add(pedido);

			}

			state.close();
			conexion.close();

		} catch (Exception e) {

			e.printStackTrace();
		}

		return pedidos;

	}

	/**
	 * Funcion que devuelve un objeto de tipo Pedido con todos los valores que han
	 * sido buscado en la BBDD mediante el id del pedido pasado por parametro
	 * 
	 * @param id_pedido Valor que representa el id del pedido que se busca
	 * @return Bean de Pedido con todos sus valores encontrados en la BBDD
	 */
	public Pedido devolverPedidoPorID(int id_pedido) {
		Pedido p = null;
		Connection conexion = null;
		PreparedStatement state = null;
		ResultSet rs = null;

		try {

			conexion = origendatos.getConnection();

			String sql = "SELECT * FROM PEDIDO WHERE ID_PEDIDO = ?";

			state = conexion.prepareStatement(sql);
			state.setInt(1, id_pedido);

			rs = state.executeQuery();

			while (rs.next()) {

				p = new Pedido(rs.getInt(1), rs.getInt(2), rs.getDate(3), rs.getDate(4), rs.getString(5));

			}

			state.close();
			conexion.close();

		} catch (Exception e) {

			e.printStackTrace();
		}

		return p;
	}

	/**
	 * Devuelve el valor entero correspondiente al numero de pedidos de un
	 * distribuidor con id pasado por parametro cuyo estado de los pedidos sea "En
	 * tramite". De este modo se obtiene los pedidos que estan activos en el sistema
	 * de un distribuidor en concreto
	 * 
	 * @param id_distribuidor Valor entero que representa el id del distribuidor y
	 *                        es usado como condicion en la busqueda en la BBDD
	 * @return Valor entero que representa a la cantidad de pedidos cuyo estado es
	 *         "En Tramite".
	 * 
	 */
	public int pedidosActivos(int id_distribuidor) {

		Connection conexion = null;
		PreparedStatement state = null;
		ResultSet rs = null;

		try {

			conexion = origendatos.getConnection();

			String sql = "SELECT COUNT( ID_DISTRIBUIDOR ) AS SUMA " + "FROM PEDIDO " + "WHERE ID_DISTRIBUIDOR = ? "
					+ "AND ESTADO = \"En Tramite\" ";

			state = conexion.prepareStatement(sql);
			state.setInt(1, id_distribuidor);

			rs = state.executeQuery();

			while (rs.next()) {

				return rs.getInt(1);

			}

			state.close();
			conexion.close();

		} catch (Exception e) {

			e.printStackTrace();
			return -1;
		}

		return 0;

	}

	/**
	 * Funcion que se encarga de usar el parseardor XML de Java para crear un
	 * fichero XML con los objetos que tiene la lista que se le pasa por parametro.
	 * Este xml representa a los articulos que estan sin realizar y que fueron
	 * recogidos desde la BBDD
	 * 
	 * @param lista Lista que representa los articulos sin realizar a los que se le
	 *              va a parsear
	 * @return String que representa el xml creado y que sirve para ser mandado por
	 *         Web Service
	 */
	public String crearXML_ArticulosSinRealizar(List<ArticuloPedido> lista) {

		String s = "";
		String line;

		try {

			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document document = builder.newDocument();

			Element root = document.createElement("articulosSinRealizar");
			document.appendChild(root);

			Iterator<ArticuloPedido> it = lista.iterator();

			while (it.hasNext()) {

				ArticuloPedido p = it.next();

				Element pedido = document.createElement("articulo");
				root.appendChild(pedido);

				Element id_articulo = document.createElement("id_articulo");
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
			while ((line = b.readLine()) != null) {
				s = s + line + "\n";

			}
			ar.delete();
			//System.out.println(ar.getAbsolutePath());

		} catch (IOException | ParserConfigurationException | TransformerException | DOMException e) {

			e.printStackTrace();
		}

		System.out.println(s);
		return s;
	}

	/**
	 * Funcion que usa el parseador DOM de XML para crear un fichero xml con los
	 * objetos de la lista que se le pasa por parametro y que representan a Pedidos.
	 * 
	 * @param lista Lista de pedidos que se quiere parsear.
	 * @return String que representa el xml creao y que sirve para ser mandado
	 *         mediante Web Service
	 */
	public String crearXML(List<Pedido> lista) {

		String s = "";
		String line;

		try {

			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document document = builder.newDocument();

			Element root = document.createElement("pedidos");
			document.appendChild(root);

			Iterator<Pedido> it = lista.iterator();

			while (it.hasNext()) {

				Pedido p = it.next();

				Element pedido = document.createElement("pedido");
				root.appendChild(pedido);

				Element idpedido = document.createElement("id_pedido");
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
			while ((line = b.readLine()) != null) {
				s = s + line + "\n";

			}
			ar.delete();
			//System.out.println(ar.getAbsolutePath());

		} catch (IOException | ParserConfigurationException | TransformerException | DOMException e) {

			e.printStackTrace();
		}

		return s;
	}

	/**
	 * Funcion que utiliza el parser DOM de xml para crear un ficher xml de la lista
	 * de Articulos de Pedido con toda la informacion relativa a esos articulos,
	 * cantidad y estado de realizacon y embalaje de ellos.
	 * 
	 * @param listaArticulos Lista de articulos de Pedido que servirá para crear el
	 *                       xml
	 * @return String representativo al xml que se ha creado.
	 */
	public String crearXML_Articulos(List<ArticuloPedido> listaArticulos) {

		String s = "";
		String line;

		try {

			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document document = builder.newDocument();

			Element root = document.createElement("articulos");
			document.appendChild(root);

			Iterator<ArticuloPedido> it = listaArticulos.iterator();

			while (it.hasNext()) {

				ArticuloPedido p = it.next();

				Element art = document.createElement("articulo");
				root.appendChild(art);

				Element id_articulo = document.createElement("id_articulo");
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
			while ((line = b.readLine()) != null) {
				s = s + line + "\n";

			}
			ar.delete();
			//System.out.println(ar.getAbsolutePath());

		} catch (IOException | ParserConfigurationException | TransformerException | DOMException e) {

			e.printStackTrace();
		}

		return s;
	}

	/**
	 * Funcion que actualiza el estado de Realizado de un articulo de un pedido en
	 * concreto.
	 * 
	 * Mediante el id del articulo y el id del pedido que posee el objeto pasado por
	 * parametro, se consulta y actualiza el estado realizado del articulo de dicho
	 * pedido a true.
	 * 
	 * @param artPed Objeto representativo de un articulo de un pedido del que se
	 *               quiere actualizar el valor de realizado
	 * 
	 */
	public void actualizarRealizado(ArticuloPedido artPed) {

		Connection conexion = null;
		PreparedStatement state = null;

		try {

			conexion = origendatos.getConnection();

			String sql = "UPDATE ARTICULO_PEDIDO SET REALIZADO=1 WHERE ID_PEDIDO = ? AND ID_ARTICULO = ?";

			state = conexion.prepareStatement(sql);

			state.setInt(1, artPed.getPedido().getId_pedido());
			state.setInt(2, artPed.getArticulo().getId_articulo());

			state.execute();

			state.close();
			conexion.close();

		} catch (Exception e) {

			e.printStackTrace();
		}

		estadoPedidoARealizado(artPed.getPedido().getId_pedido());

	}

	/**
	 * Funcion que actualiza el valor de Embalado de un articulo en un pedido en
	 * concreto. Mediante los parametros que se le pasa al metodo, se actualiza el
	 * valor Embalado de ese articulo y ese pedido
	 * 
	 * @param id_pedido   Identificador del pedido que se quiere buscar
	 * @param id_articulo Identificador del articulo que se quiere buscar
	 * @return True, si se actualiza correctamente, False, si no se puede actualizar
	 */
	public boolean actualizarEmbalado(int id_pedido, int id_articulo) {

		Connection conexion = null;
		PreparedStatement state = null;

		try {

			conexion = origendatos.getConnection();

			String sql = "UPDATE ARTICULO_PEDIDO SET EMBALADO = 1 WHERE ID_PEDIDO = ? AND ID_ARTICULO = ?";

			state = conexion.prepareStatement(sql);

			state.setInt(1, id_pedido);
			state.setInt(2, id_articulo);

			state.execute();

			state.close();
			conexion.close();
			return true;

		} catch (Exception e) {

			e.printStackTrace();

			return false;
		}
	}

	/**
	 * Funcion que cambia el estado de un pedido.
	 * 
	 * @param id_pedido Identificador del pedido al que se le quiere cambiar el
	 *                  estado
	 * @param estado    Nuevo estado que se le quiere poner al pedido
	 */
	public void cambiarEstadoPedido(int id_pedido, String estado) {

		Connection conexion = null;
		PreparedStatement state = null;

		try {

			conexion = origendatos.getConnection();

			String sql = "UPDATE PEDIDO SET ESTADO = ?  WHERE ID_PEDIDO = ? ";

			state = conexion.prepareStatement(sql);

			state.setString(1, estado);
			state.setInt(2, id_pedido);

			state.execute();

			state.close();
			conexion.close();

		} catch (Exception e) {

			e.printStackTrace();
		}

	}

	/*
	 * Funcion privada que comprueba en primer luegar que todos los articulos de un
	 * pedido, mediante su id de pedido, estén ya realizados. Si es asi, llama a
	 * otra función con el fin de cambiar el estado de ese pedido mediante su id de
	 * pedido, al estado Realizado
	 * 
	 */
	private void estadoPedidoARealizado(int id_pedido) {

		boolean flag = comprobarArticulosPedido(id_pedido);

		if (flag) {

			cambioEstadoPedido_a_Realizado(id_pedido);
		}

	}

	/*
	 * Funcion que actualiza el estado a Realizado de un registro cuyo id de pedido
	 * se le pasa por parametro.
	 * 
	 */
	private void cambioEstadoPedido_a_Realizado(int id_pedido) {

		// Pedido pedido = devolverPedidoPorID(id_pedido);

		Connection conexion = null;
		PreparedStatement state = null;

		try {

			conexion = origendatos.getConnection();

			String sql = "UPDATE PEDIDO SET ESTADO = ?  WHERE ID_PEDIDO = ? ";

			state = conexion.prepareStatement(sql);

			state.setString(1, "Realizado");
			state.setInt(2, id_pedido);

			state.execute();

			state.close();
			conexion.close();

		} catch (Exception e) {

			e.printStackTrace();
		}

	}

	/*
	 * Comprueba, mediante el id del pedido pasado por parametro, que todos los
	 * articulos de ese pedido posean el valor de Realizado en True. Si encuentra un
	 * solo articulo del pedido que no posea ese valor de Realizado en true,
	 * devolverá False. En caso contrario, es decir, en caso de que todos los
	 * articulos posean el valor de Realizado en verdadero, devolverá True.
	 * 
	 */
	private boolean comprobarArticulosPedido(int id_pedido) {

		List<ArticuloPedido> listaArticulos = obtenArticulosPedido(id_pedido);

		Iterator<ArticuloPedido> it = listaArticulos.iterator();

		while (it.hasNext()) {

			ArticuloPedido artPed = it.next();

			if (artPed.isRealizado() == false) {

				return false;
			}
		}

		return true;
	}

	/**
	 * Funcion que devuelve aquellos pedidos cuyo estado sea diferente al estado "En
	 * tramite"
	 * 
	 * @return Lista de Pedidos
	 */
	public List<Pedido> obtenPedidosConProdAcabados() {

		List<Pedido> lista = new ArrayList<Pedido>();

		Connection conexion = null;
		PreparedStatement state = null;
		ResultSet rs = null;
		Pedido p = null;

		try {

			conexion = origendatos.getConnection();

			String sql = "SELECT * FROM PEDIDO WHERE ESTADO != ? ORDER BY ESTADO";

			state = conexion.prepareStatement(sql);

			state.setString(1, "En Tramite");

			rs = state.executeQuery();

			while (rs.next()) {

				int id_pedido = rs.getInt(1);
				int id_dist = rs.getInt(2);
				Date f_realizado = rs.getDate(3);
				Date f_envio = rs.getDate(4);
				String estado = rs.getString(5);
				// List<ArticuloPedido> articulosPedido = obtenArticulosPedido(id_pedido);

				p = new Pedido(id_pedido, id_dist, f_realizado, f_envio, estado);
				lista.add(p);
			}

			state.close();
			conexion.close();

		} catch (Exception e) {

			e.printStackTrace();
		}

		return lista;
	}

}
