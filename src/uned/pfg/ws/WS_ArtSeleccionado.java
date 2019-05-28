package uned.pfg.ws;

import org.apache.commons.dbcp.BasicDataSource;

import uned.pfg.dao.ArticuloDAO;

/**
 * Clase que representa a un WEB SERVICE que tiene como objetivo seleccionar un 
 * articulo del sistema.
 *
 * @author JORGE VILLALBA RUIZ 47536486V
 * @version 1.0
 */
public class WS_ArtSeleccionado {

	private BasicDataSource basicDataSource;
	private ArticuloDAO articulodao;
	
	
	/**
	 * Construtor por defecto, que llama al pool de conexiones ( si es que estaba ya creado, si no
	 * lo crea) y pasa este pool de conexiones al constructor del DAO de articulo.
	 */
	public WS_ArtSeleccionado() {

		basicDataSource = PoolConexiones.getInstance().getConnection();
		articulodao = new ArticuloDAO(basicDataSource);
	}
	
	
	/**
	 * Funcion que busca en la base de datos un articulo en concreto y cuyo id es pasado
	 * en formato String por parametro a esta funcion.
	 * @param id String que representa el identificador de un articulos.
	 * @return String que representa el xml con el articulo que ha sido encontrado.
	 */
	public String enviarArticuloSeleccionado(String id) {
		
		
		return articulodao.crearXML_artSelec(articulodao.SeleccionarArticulo(Integer.parseInt(id)));
	}
}
