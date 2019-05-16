package uned.pfg.bean;

/**
 * Clase que representa el Bean del Almacen, que dispone como campos de clase
 * los siguientes;
 * id del articulo, cantidad almacenada en el almacen, y cantidad que hay libre en 
 * el almacen.
 * 
 * 
 * @author JORGE VILLALBA RUIZ 47536486V
 * @version 1.0
 */
public class Almacen {

	private int id_articulo, cantidad_almacenada, cantidad_libre;

	
	
	/**
	 * Metodo para incializar los campos de la clase con los atributos pasados por parametro.
	 * @param id_articulo. Identificador del articulo que hay en el sistema.
	 * @param cantidad_almacenada. Cantidad numerica que hay almacenada de ese articulo en el almacen.
	 * @param cantidad_libre. Cantidad numerica que hay libre de ese articulo en el almacen.
	 */
	public Almacen(int id_articulo, int cantidad_almacenada, int cantidad_libre) {
		this.id_articulo = id_articulo;
		this.cantidad_almacenada = cantidad_almacenada;
		this.cantidad_libre = cantidad_libre;
	}
	
	/**
	 * Constructor por defecto del bean
	 */
	public Almacen() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * Devuelve el id del articulo
	 * @return Identificador numerico del articulo
	 */
	public int getId_articulo() {
		return id_articulo;
	}
	

	/**
	 * Establece un identificador de articulo
	 * @param id_articulo. Parametro que representa el identificador del articulo
	 */
	public void setId_articulo(int id_articulo) {
		this.id_articulo = id_articulo;
	}
	
	
	/**
	 * Devuelve la cantidad almacenada que hay en el almacen
	 * @return Valor numerico representativo a la cantidad almacenada que hay de un articulo
	 */
	
	public int getCantidad_almacenada() {
		return cantidad_almacenada;
	}
	
	
	/**
	 * Establece la cantidad almacenada de un articulo
	 * @param cantidad_almacenada. Valor numerico representativo a la cantidad almacenada.
	 */

	public void setCantidad_almacenada(int cantidad_almacenada) {
		this.cantidad_almacenada = cantidad_almacenada;
	}
	
	/**
	 * Devuelve la cantidad libre que hay en el almacen de un articulo
	 * @return  Valor numerico representativo a la cantidad libre que hay en el almacen.
	 */

	public int getCantidad_libre() {
		return cantidad_libre;
	}
	
	/**
	 * Establece la cantidad libre que hay en el almacen de un articulo en concreto
	 * 
	 * @param cantidad_libre. Valor numerico que representa a la cantidad libre de un articulo que hay en
	 * el almacen
	 */

	public void setCantidad_libre(int cantidad_libre) {
		this.cantidad_libre = cantidad_libre;
	}
	
	
}
