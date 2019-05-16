package uned.pfg.bean;

import java.util.Date;


/**
 * Clase que representa a un bean de Articulo, que dispone de los siguientes campos de clase;
 * identificador de articulo, nombre del articulo, fecha de entrada en el sistema y precio 
 * 
 * @author JORGE VILLALBA RUIZ 47536486V
 * @version 1.0
 */
public class Articulo {

	int id_articulo;
	String nombre;
	Date fecha_entrada;
	double precio;
	
	
	
	/**
	 * Contructor que inicializa el id del articulo mediante el parametro del contructor.
	 * @param id_articulo Valor numerico representativo a un identificador de articulo
	 */
	public Articulo(int id_articulo) {
		this.id_articulo = id_articulo;
	}

	
	/**
	 * Contructor que inicializa todos los campos de clase con los parametros que se le pasa a este constructor
	 * @param id_articulo Valor numerico representativo del identificador del articulo.
	 * @param nombre String que representa al nombre que se le da al articulo
	 * @param fecha_entrada valor de tipo Date que representa la fecha
	 * @param precio Valor de tipo double que representa el precio que tiene el articulo
	 */
	public Articulo(int id_articulo, String nombre, Date fecha_entrada, double precio) {
		this.id_articulo = id_articulo;
		this.nombre = nombre;
		this.fecha_entrada = fecha_entrada;
		this.precio = precio;
	}
	
	/**
	 * Constructor por defecto de la clase
	 */
	public Articulo() {
	}

 
	/**
	 * Constructor que inicializa los campos de clase nombre, fecha de entrada y precio.
	 * 
	 * @param nombre String que representa al nombre que se le da al articulo
	 * @param fecha_entrada valor de tipo Date que representa la fecha
	 * @param precio Valor de tipo double que representa el precio que tiene el articulo
	 */

	public Articulo(String nombre, Date fecha_entrada, double precio) {
		this.nombre = nombre;
		this.fecha_entrada = fecha_entrada;
		this.precio = precio;
	}
	
	
	
	/**
	 * Devuelve el valor del identificador del articulo
	 * @return valor numerico que representa el id del articulo
	 */
	public int getId_articulo() {
		return id_articulo;
	}



	/**
	 * Establece el id del articulo al valor que se le pase por parametro.
	 * @param id_articulo Valor numerico que representa un id de articulo.
	 */
	public void setId_articulo(int id_articulo) {
		this.id_articulo = id_articulo;
	}



	/**
	 * Devuele el nombre del articulo
	 * @return String con el valor del nombre del articulo
	 */
	public String getNombre() {
		return nombre;
	}



	/**
	 * Establece el nombre del articulo al valor que se le pasa por parametro
	 * @param nombre String cuyo valor representa el nombre del articulo
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}



	/**
	 * Devuelve el valor de la fecha de entrada del articulo en el sistema
	 * @return Date que representa a la fecha de alta en el sistema.
	 */
	public Date getFecha_entrada() {
		return fecha_entrada;
	}



	/**
	 * Establece la fecha del sistema al valor que se le pasa por parametro
	 * @param fecha_entrada Valor de tipo Date que representa la fecha que se le da de alta en el sistema
	 */
	public void setFecha_entrada(Date fecha_entrada) {
		this.fecha_entrada = fecha_entrada;
	}



	/**
	 * Devuelve el valor del precio del articulo
	 * @return Valor de tipo double que representa el precio del articulo
	 */
	public double getPrecio() {
		return precio;
	}


	/**
	 * Establece el valor del precio del articulo al valor que se le pasa por parametro
	 * 
	 * @param precio valor de tipo double que representra el precio que tiene el articulo.
	 */
	public void setPrecio(double precio) {
		this.precio = precio;
	}
	
	
	
	
}
