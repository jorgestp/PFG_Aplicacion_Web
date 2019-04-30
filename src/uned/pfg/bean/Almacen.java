package uned.pfg.bean;

public class Almacen {

	int id_articulo, cantidad_almacenada, cantidad_libre;

	public Almacen(int id_articulo, int cantidad_almacenada, int cantidad_libre) {
		this.id_articulo = id_articulo;
		this.cantidad_almacenada = cantidad_almacenada;
		this.cantidad_libre = cantidad_libre;
	}
	
	public Almacen() {
		// TODO Auto-generated constructor stub
	}

	public int getId_articulo() {
		return id_articulo;
	}

	public void setId_articulo(int id_articulo) {
		this.id_articulo = id_articulo;
	}

	public int getCantidad_almacenada() {
		return cantidad_almacenada;
	}

	public void setCantidad_almacenada(int cantidad_almacenada) {
		this.cantidad_almacenada = cantidad_almacenada;
	}

	public int getCantidad_libre() {
		return cantidad_libre;
	}

	public void setCantidad_libre(int cantidad_libre) {
		this.cantidad_libre = cantidad_libre;
	}
	
	
}
