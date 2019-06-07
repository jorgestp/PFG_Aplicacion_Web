package uned.pfg.bean;

import java.util.Date;
import java.util.List;
/**
 * Clase que representa el bean de Pedido, que dispone de los siguientes campos de clase;
 * 
 * Identificador de pedido, identificador del distribuidor, fecha de entrada y de envio, estado
 * del pedido y lista con los articulos que dispone el pedido.
 * 
 * 
 * @author JORGE VILLALBA RUIZ 47536486V
 * @version 1.0
 */
public class Pedido {

	private int id_pedido, id_distribuidor;
	private Date fecha_entrada, fecha_envio;
	private String estado;
	private List<ArticuloPedido> articulos;
	
	
	/**
	 * Constructor que inicializa todas las variables de la clase con valores de los parametros.
	 * @param id_pedido Entero que representa el identificador de un pedidos
	 * @param id_distribuidor Entero que representa el identificador de un distribuidor.
	 * @param fecha_entrada Representa la fecha de entrada del pedido en el sistema.
	 * @param fecha_envio Representa la fecha de envio del pedido hacia el distribuidor.
	 * @param estado Representa el estado en el que se encuentra el pedido
	 * @param articulos Representa la lista de articulos que tiene el pedido
	 */
	public Pedido(int id_pedido, int id_distribuidor, Date fecha_entrada, Date fecha_envio, String estado,
			List<ArticuloPedido> articulos) {
		
		this.id_pedido = id_pedido;
		this.id_distribuidor = id_distribuidor;
		this.fecha_entrada = fecha_entrada;
		this.fecha_envio = fecha_envio;
		this.estado = estado;
		this.articulos = articulos;
	}
	
	

	/**
	 * Constructor que inicializa los 4 campos de clase con los valores de los parametros
	 * @param id_distribuidor Entero que representa el identificador de un distribuidor.
	 * @param fecha_entrada Representa la fecha de entrada del pedido en el sistema.
	 * @param fecha_envio Representa la fecha de envio del pedido hacia el distribuidor.
	 * @param estado Representa el estado en el que se encuentra el pedido
	 * @param articulos Representa la lista de articulos que tiene el pedido
	 */
	 
	public Pedido(int id_distribuidor, Date fecha_entrada, Date fecha_envio, String estado,
			List<ArticuloPedido> articulos) {
		this.id_distribuidor = id_distribuidor;
		this.fecha_entrada = fecha_entrada;
		this.fecha_envio = fecha_envio;
		this.estado = estado;
		this.articulos = articulos;
	}
	
	


	/**
	 * 
	 * @param id_pedido Entero que representa el identificador de un pedidos
	 * @param fecha_entrada Representa la fecha de entrada del pedido en el sistema.
	 * @param fecha_envio Representa la fecha de envio del pedido hacia el distribuidor.
	 * @param estado Representa el estado en el que se encuentra el pedido
	 */
	public Pedido(int id_pedido, Date fecha_entrada, Date fecha_envio, String estado) {
		this.id_pedido = id_pedido;
		this.fecha_entrada = fecha_entrada;
		this.fecha_envio = fecha_envio;
		this.estado = estado;
	}






	/**
	 * 
	 * @param id_pedido Entero que representa el identificador de un pedidos
	 * @param id_distribuidor Entero que representa el identificador de un distribuidor.
	 * @param fecha_entrada Representa la fecha de entrada del pedido en el sistema.
	 * @param fecha_envio Representa la fecha de envio del pedido hacia el distribuidor.
	 * @param estado Representa el estado en el que se encuentra el pedido
	 */
	public Pedido(int id_pedido, int id_distribuidor, Date fecha_entrada, Date fecha_envio, String estado) {
		this.id_pedido = id_pedido;
		this.id_distribuidor = id_distribuidor;
		this.fecha_entrada = fecha_entrada;
		this.fecha_envio = fecha_envio;
		this.estado = estado;
	}



	/**
	 * Devuelve el id del pedido
	 * @return Entero que representa el identificador del pedido
	 */
	public int getId_pedido() {
		return id_pedido;
	}

	/**
	 * Establece el id del pedido al valor pasado por parametroS
	 * @param id_pedido Identificador del pedido
	 */
	public void setId_pedido(int id_pedido) {
		this.id_pedido = id_pedido;
	}

	/**
	 * Devuelve el identificador del distribuidor.
	 * @return Entero que representa el identificador del distribuidor.
	 */
	public int getId_distribuidor() {
		return id_distribuidor;
	}

	/**
	 * Establece el id del distribuidor al valor pasado por parametroS
	 * @param id_distribuidor Identificador del distribuidor
	 */
	public void setId_distribuidor(int id_distribuidor) {
		this.id_distribuidor = id_distribuidor;
	}

	/**
	 * Devuelve la fecha de entrada del pedido en el sistema
	 * @return Objeto de tipo Date que representa la fecha de entrada del pedido.
	 */
	public Date getFecha_entrada() {
		return fecha_entrada;
	}

	/**
	 * Establece la fecha de entrada al valor pasado por parametro
	 * @param fecha_entrada Valor de tipo Date que representa la fecha de entrada
	 */
	public void setFecha_entrada(Date fecha_entrada) {
		this.fecha_entrada = fecha_entrada;
	}

	/**
	 * Devuelve la fecha de envio del pedido 
	 * @return Objeto de tipo Date que representa la fecha de envio del pedido
	 */
	public Date getFecha_envio() {
		return fecha_envio;
	}

	/**
	 * Establece la fecha de envio del pedido al valor pasado por parametro
	 * @param fecha_envio Valor de tipo Date que representa la fecha de envio del pedido
	 */
	public void setFecha_envio(Date fecha_envio) {
		this.fecha_envio = fecha_envio;
	}

	/**
	 * Devuelve el estado del pedido
	 * @return String que representa el estado en el que se encuentra el pedido
	 */
	public String getEstado() {
		return estado;
	}

	/**
	 * Establece el estado del pedido al valor pasado por parametro
	 * @param estado String que representa el estado del pedido
	 */
	public void setEstado(String estado) {
		this.estado = estado;
	}

	/**
	 * Devuelve la lista de los articulos que tiene un pedido.
	 * @return Coleccion de Articulos que tiene el pedido
	 */
	public List<ArticuloPedido> getArticulos() {
		return articulos;
	}

	/**
	 * Establece la lista de articulos al valor pasado por parametro
	 * @param articulos Lista de articulos del pedido.
	 */
	public void setArticulos(List<ArticuloPedido> articulos) {
		this.articulos = articulos;
	}
	
	
	
}
