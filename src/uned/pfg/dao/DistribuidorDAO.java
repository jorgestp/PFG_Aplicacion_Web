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

import uned.pfg.bean.Distribuidor;

import uned.pfg.ws.PoolConexiones;

/**
 * Clase que representa el Objeto de Acceso a Datos almacenados de los
 * Distribuidores del sistema.
 * 
 * Se hacen las pertinentes consultas de actualizacion, inserccion o borrado de
 * todo lo referente a los distribuidores que tiene el sistema y que se
 * detallarán en las funciones correspondientes.
 * 
 * 
 * @author JORGE VILLALBA RUIZ 47536486V
 * @version 1.0
 */
public class DistribuidorDAO {

	private DataSource origendatos;
	private final String FILENAME_DIST = "XML_list_distribuidor.xml";
	private final String FILENAME_un_dist = "XML_distr.xml";

	/**
	 * Constructor que inicializa la variable pasada por parametros con el pool de
	 * conexiones establecido para la base de datos, y ademas asigna esta variable
	 * al campo de clase correspondiente al DataSource
	 * 
	 * @param origendatos Objeto que representa el pool de conexiones y el que es
	 *                    inicializado y luego asignado al campo de clase
	 */
	public DistribuidorDAO(DataSource origendatos) {

		origendatos = PoolConexiones.getInstance().getConnection();

		this.origendatos = origendatos;

	}

	/**
	 * Funcion que se encarga de insertar en la BBDD un distribuidor pasado por
	 * parametro
	 * 
	 * @param dist Objeto Bean que representa al distribuidor que queremos insertar.
	 */
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

	/**
	 * Funcion que busca un usuario dado su nick y su contraseña, devolviendo todos
	 * los valores almacenados de dicho distribuidor
	 * 
	 * @param usuario String que representa al nick con el que se accede al sistema
	 * @param contra  String que representa la contraseña con la que se accede al
	 *                sistema.
	 * @return Bean del distribuidor con todos sus valores recogidos de la BBDD. En
	 *         el caso de no encontrar a ninguno en el sistema, devuelve null.
	 */
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

	/**
	 * Funcion que se encarga de actualizar los datos de un distribuidor en la BBDD
	 * 
	 * @param d Bean del Distribuidor al que se quiere actualizar sus valores en la
	 *          BBDD
	 * @return Valor entero que representa la actualizacion correcta en el sistema
	 *         del objeto pasado por parametro. Si es mayor a 0, la actualizacion se
	 *         ha realizado correctamente.
	 */
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

	/**
	 * Funcion que consulta a la base de datos los distribuidores que hay en ese
	 * instante en en el sistema.
	 * 
	 * @return Lista de distribuidores encontrados en la BBDD.
	 */
	public List<Distribuidor> obtenerDistribuidores() {

		List<Distribuidor> distribuidores = new ArrayList<Distribuidor>();
		Connection conexion = null;
		Statement state = null;
		ResultSet rs = null;
		// int i = 0;

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

	/**
	 * Funcion que, dado un nombre pasado por parametro, consulta a la base de datos
	 * la existenacia de ese distribuidor.
	 * 
	 * @param nombre String que representa al nombre del distribuidor.
	 * @return Distribuidor encontrado en caso de que la busqueda tenga exito, o
	 *         null en caso de que la consulta no devuelva ningun valor
	 */
	public Distribuidor obtenDistri(String nombre) {

		Connection conexion = null;
		PreparedStatement state = null;
		Distribuidor d = null;
		ResultSet rs = null;

		try {

			conexion = origendatos.getConnection();
			String sql = "SELECT * FROM DISTRIBUIDOR WHERE NOMBRE = ?";

			state = conexion.prepareStatement(sql);

			state.setString(1, nombre);

			rs = state.executeQuery();

			while (rs.next()) {

				d = new Distribuidor(rs.getInt(1), rs.getString(2));
			}

			state.close();
			conexion.close();

		} catch (Exception e) {

			e.printStackTrace();

		}

		return d;

	}

	/**
	 * Funcion que, dado un id de distribuidor, se consulta a la base de datos la
	 * existendia de algun registro en el que coincida el id con el valor pasado por
	 * parametro
	 * 
	 * @param id_dist Valor entero que representa al id de distribuidor
	 * @return Distribuidor encontrado, o null en caso de que la consulta no
	 *         devuelva ningun registro.
	 */
	public Distribuidor obtenDistribuidor_porID(int id_dist) {

		Connection conexion = null;
		PreparedStatement state = null;
		Distribuidor d = null;
		ResultSet rs = null;

		try {

			conexion = origendatos.getConnection();
			String sql = "SELECT * FROM DISTRIBUIDOR WHERE ID_DISTRIBUIDOR = ?";

			state = conexion.prepareStatement(sql);

			state.setInt(1, id_dist);

			rs = state.executeQuery();

			while (rs.next()) {

				d = new Distribuidor(rs.getInt(1), rs.getString(2));
			}

			state.close();
			conexion.close();

		} catch (Exception e) {

			e.printStackTrace();

		}

		return d;
	}

	/**
	 * Funcion que crea un XML usando el parser DOM de Java. Se crea un xml de todos
	 * los distribuidores que hay en el sistema, y que estan recogidos en la lista
	 * que se le pasa por parametro.
	 * 
	 * @param listaDist Objeto lista que representa a la coleccion de objetos de
	 *                  tipo Distribuidor
	 * @return String que representa al contenido del xml creado
	 */
	@SuppressWarnings("resource")
	public String crearXML_Distribuidores(List<Distribuidor> listaDist) {

		String s = "";
		String line;

		try {

			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document document = builder.newDocument();

			Element root = document.createElement("distribuidores");
			document.appendChild(root);

			Iterator<Distribuidor> it = listaDist.iterator();

			while (it.hasNext()) {

				Distribuidor p = it.next();

				Element art = document.createElement("distribuidor");
				root.appendChild(art);

				Element id_dist = document.createElement("id");
				art.appendChild(id_dist);
				id_dist.appendChild(document.createTextNode(String.valueOf(p.getId())));

				Element nombre = document.createElement("nombre");
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
			while ((line = b.readLine()) != null) {
				s = s + line + "\n";

			}

			// System.out.println(ar.getAbsolutePath());
			ar.delete();

		} catch (IOException | ParserConfigurationException | TransformerException | DOMException e) {

			e.printStackTrace();
		}

		return s;
	}

	/**
	 * Elimina un distribudor de la base de datos cuyo id es pasado por parametro.
	 * 
	 * @param parseInt Valor que representa al id de distribuidor que estamos
	 *                 buscando para eliminar.
	 * @throws SQLException Excepcion SQL producida por problemas en la conexion con
	 *                      la BBDD
	 */
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

		} finally {

			state.close();
			conexion.close();
		}

	}

	/**
	 * Funcion que crea un XML usando el parser DOM de Java. Se crea un xml con el
	 * distribuidor pasado por parametro.
	 * 
	 * @param d Bean del distribuidor que se quiere pasar a xml
	 * @return String que representa al contenido del xml creado.
	 */
	@SuppressWarnings("resource")
	public String crearXML_un_Distribuidor(Distribuidor d) {

		String s = "";
		String line;

		try {

			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document document = builder.newDocument();

			Element root = document.createElement("distribuidor_pedido");
			document.appendChild(root);

			Element dis = document.createElement("distribuidor");
			root.appendChild(dis);

			Element id_distribuidor = document.createElement("id_distribuidor");
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
			while ((line = b.readLine()) != null) {
				s = s + line + "\n";

			}

			// System.out.println(ar.getAbsolutePath());
			ar.delete();

		} catch (IOException | ParserConfigurationException | TransformerException | DOMException e) {

			e.printStackTrace();
		}

		return s;
	}

}
