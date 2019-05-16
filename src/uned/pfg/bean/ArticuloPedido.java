package uned.pfg.bean;


/**
 * Clase que representa a un bean de cada articulo que tenga un pedido en concreto, y cuyos campos de clase
 * son los siguiente;
 * Articulo cuyo tipo de dato es el bean Articulo, cantidad de ese articulo, pedido al que representa ese
 * articulo, realizado que representa un boolean para conocer si esta fabricado ese articulo o no, y embalado,
 * que tambien representa un boolean para conocer si el articulo ha sido embalado o no.
 * 
 * @author JORGE VILLALBA RUIZ 47536486V
 * @version 1.0
 */
public class ArticuloPedido {
	
	private Articulo articulo;
	private int cant;
	private Pedido pedido;
	boolean realizado, embalado;
	
	
	/**
	 * Constructor que inicializa todos los campos de clase con los parametros que tiene esta funcion.
	 * @param articulo Objeto de tipo Articulo en representacion al articulo que tiene el pedido
	 * @param cant Valor numerico que representa la cantidad del articulo que se pide
	 * @param pedido Objeto de tipo Pedido que representa al pedido en sí
	 * @param realizado Valor boolean que representa el estado realizado o no del articulo en el pedido
	 * @param embalado Valor boolean que representa el estado embalado o no del articulo en el pedido. 
	 */
	public ArticuloPedido(Articulo articulo, int cant, Pedido pedido, boolean realizado, boolean embalado) {
		this.articulo = articulo;
		this.cant = cant;
		this.pedido = pedido;
		this.realizado = realizado;
		this.embalado = embalado;
	}


	
	/**
	 * Constructor que iniciliza 4 de los 5 campos de clase, a excepcion del pedido.
	 * @param articulo Objeto de tipo Articulo en representacion al articulo que tiene el pedido
	 * @param cant Valor numerico que representa la cantidad del articulo que se pide
	 * @param realizado Valor boolean que representa el estado realizado o no del articulo en el pedido
	 * @param embalado Valor boolean que representa el estado embalado o no del articulo en el pedido. 
	 */
	public ArticuloPedido(Articulo articulo, int cant, boolean realizado, boolean embalado) {
		this.articulo = articulo;
		this.cant = cant;
		this.realizado = realizado;
		this.embalado = embalado;
	}

	
	/**
	 * Conctrucor que inicializa el valor de Articulo y de cantidad
	 * @param articulo Objeto de tipo articulo que representa un articulo que hay en el sistema
	 * @param cant Valor numerico que representa la cantidad de ese articulo que se desea incluir en el pedido
	 */
	public ArticuloPedido(Articulo articulo, int cant) {
		this.articulo = articulo;
		this.cant = cant;
	}


	
	/**
	 * Devuelve un objeto de tipo Pedido
	 * @return objeto de tipo Pedido que representa a un pedido concreto
	 */
	public Pedido getPedido() {
		return pedido;
	}



	/**
	 * Establece el valor del campo pedido con el valor pasado por parametro
	 * @param pedido Objeto de tipo Pedido que representa a un pedido concreto
	 */
	public void setPedido(Pedido pedido) {
		this.pedido = pedido;
	}



	/**
	 * Devuelve el valor del estado de realizado del articulo en el pedido
	 * @return True, si el articulo ya esta realizado, o false, si aun no esta realizado
	 */
	public boolean isRealizado() {
		return realizado;
	}



	/**
	 * Establece el valor de realizado al valor que se le pasa por parametro
	 * @param realizado Valor booleano que establece el estado de realizado.
	 */
	public void setRealizado(boolean realizado) {
		this.realizado = realizado;
	}


	/**
	 * Devuelve el valor del estado de embalado de un articulo en concreto dentro del pedido
	 * @return True si el articulo del pedido esta embalado, false, si aun no esta embalado
	 */
	public boolean isEmbalado() {
		return embalado;
	}



	/**
	 * Establece el estado de embalado mediante el parametro que se le pasa a la funcion
	 * @param embalado Valor booleano que representa el estado embalado del objeto
	 */
	public void setEmbalado(boolean embalado) {
		this.embalado = embalado;
	}

	
	/**
	 * Devuelve un objeto de tipo Articulo que representa al articulo que hay en el pedido
	 * @return Objeto de tipo Articulo
	 * @see Articulo
	 */
	public Articulo getArticulo() {
		return articulo;
	}



	/**
	 * Establece el valor del campo articulo mediante el parametro de la funcion
	 * @param articulo Objeto de tipo Articulo que representa un articulo que hay en el pedido
	 */
	public void setArticulo(Articulo articulo) {
		this.articulo = articulo;
	}



	/**
	 * Devuelve la cantidad del articulo que hay en el pedido
	 * @return Valor numerico que representa la cantidad que hay pedida del articulo
	 */
	public int getCant() {
		return cant;
	}



	/**
	 * Establece la cantidad que se pide de un articulo
	 * @param cant Valor entero en representacion de la cantidad
	 */
	public void setCant(int cant) {
		this.cant = cant;
	}



	public String toString() {
		return "ArticuloPedido [articulo=" + articulo + ", cant=" + cant + "]";
	}

	
}
