package uned.pfg.bean;

import java.util.Date;

public class Articulo {

	int id_articulo;
	String nombre;
	Date fecha_entrada;
	double precio;
	
	
	
	public Articulo(int id_articulo, String nombre, Date fecha_entrada, double precio) {
		this.id_articulo = id_articulo;
		this.nombre = nombre;
		this.fecha_entrada = fecha_entrada;
		this.precio = precio;
	}
	
	public Articulo() {
		// TODO Auto-generated constructor stub
	}



	public int getId_articulo() {
		return id_articulo;
	}



	public void setId_articulo(int id_articulo) {
		this.id_articulo = id_articulo;
	}



	public String getNombre() {
		return nombre;
	}



	public void setNombre(String nombre) {
		this.nombre = nombre;
	}



	public Date getFecha_entrada() {
		return fecha_entrada;
	}



	public void setFecha_entrada(Date fecha_entrada) {
		this.fecha_entrada = fecha_entrada;
	}



	public double getPrecio() {
		return precio;
	}



	public void setPrecio(double precio) {
		this.precio = precio;
	}
	
	
	
	
}
