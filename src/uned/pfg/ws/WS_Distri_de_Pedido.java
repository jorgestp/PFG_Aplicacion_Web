package uned.pfg.ws;

import org.apache.commons.dbcp.BasicDataSource;

import uned.pfg.dao.DistribuidorDAO;

public class WS_Distri_de_Pedido {
	
	
	/**
	 * Clase que representa a un WEB SERVICE que tiene como objetivo buscar en la base de datos
	 * mediante el campo de clase correspondiente a un Distribuidor en concreto.
	 *
	 * @author JORGE VILLALBA RUIZ 47536486V
	 * @version 1.0
	 */
	private DistribuidorDAO distribuidorDAO;
	

	BasicDataSource basicDataSource;
	
	
	
	/**
	 * Construtor por defecto, que llama al pool de conexiones ( si es que estaba ya creado, si no
	 * lo crea) y pasa este pool de conexiones al constructor del DAO de distribuidor.
	 */
	public WS_Distri_de_Pedido() {
		

		
		basicDataSource = PoolConexiones.getInstance().getConnection();
		
		
		distribuidorDAO = new DistribuidorDAO(basicDataSource);
		
	}
	
	/**
	 * Funcion que busca un distribuidor en la base de datos mediante el objeto DAO de distribuidor
	 * inicializado en el contructor de esta clase, y cuyo identificador de distribuidro es pasado
	 * por parametro.
	 * @param id String que representa al identificador del distribuidor que estamos buscando
	 * @return String que representa al xml con los datos y valores del distribuidor encontrado.
	 */
	public String envioDistribuidor(String id) {
		
		return distribuidorDAO.crearXML_un_Distribuidor(distribuidorDAO.obtenDistribuidor_porID(Integer.parseInt(id)));
		
	}

}
