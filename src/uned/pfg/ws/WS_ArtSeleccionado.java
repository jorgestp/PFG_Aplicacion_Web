package uned.pfg.ws;

import org.apache.commons.dbcp.BasicDataSource;

import uned.pfg.dao.ArticuloDAO;

public class WS_ArtSeleccionado {

	private BasicDataSource basicDataSource;
	private ArticuloDAO articulodao;
	
	public WS_ArtSeleccionado() {

		basicDataSource = PoolConexiones.getInstance().getConnection();
		articulodao = new ArticuloDAO(basicDataSource);
	}
	
	public String enviarArticuloSeleccionado(String id) {
		
		
		return articulodao.crearXML_artSelec(articulodao.SeleccionarArticulo(Integer.parseInt(id)));
	}
}
