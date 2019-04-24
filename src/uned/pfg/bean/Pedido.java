package uned.pfg.bean;

import java.util.Date;
import java.util.List;

public class Pedido {

	int id_pedido, id_distribuidor;
	Date fecha_entrada, fecha_envio;
	String estado;
	List<ArticuloPedido> articulos;
	
	public Pedido(int id_pedido, int id_distribuidor, Date fecha_entrada, Date fecha_envio, String estado,
			List<ArticuloPedido> articulos) {
		
		this.id_pedido = id_pedido;
		this.id_distribuidor = id_distribuidor;
		this.fecha_entrada = fecha_entrada;
		this.fecha_envio = fecha_envio;
		this.estado = estado;
		this.articulos = articulos;
	}
	
	

	public Pedido(int id_distribuidor, Date fecha_entrada, Date fecha_envio, String estado,
			List<ArticuloPedido> articulos) {
		this.id_distribuidor = id_distribuidor;
		this.fecha_entrada = fecha_entrada;
		this.fecha_envio = fecha_envio;
		this.estado = estado;
		this.articulos = articulos;
	}
	
	



	public Pedido(int id_pedido, Date fecha_entrada, Date fecha_envio, String estado) {
		this.id_pedido = id_pedido;
		this.fecha_entrada = fecha_entrada;
		this.fecha_envio = fecha_envio;
		this.estado = estado;
	}







	public Pedido(int id_pedido, int id_distribuidor, Date fecha_entrada, Date fecha_envio, String estado) {
		this.id_pedido = id_pedido;
		this.id_distribuidor = id_distribuidor;
		this.fecha_entrada = fecha_entrada;
		this.fecha_envio = fecha_envio;
		this.estado = estado;
	}



	public int getId_pedido() {
		return id_pedido;
	}

	public void setId_pedido(int id_pedido) {
		this.id_pedido = id_pedido;
	}

	public int getId_distribuidor() {
		return id_distribuidor;
	}

	public void setId_distribuidor(int id_distribuidor) {
		this.id_distribuidor = id_distribuidor;
	}

	public Date getFecha_entrada() {
		return fecha_entrada;
	}

	public void setFecha_entrada(Date fecha_entrada) {
		this.fecha_entrada = fecha_entrada;
	}

	public Date getFecha_envio() {
		return fecha_envio;
	}

	public void setFecha_envio(Date fecha_envio) {
		this.fecha_envio = fecha_envio;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public List<ArticuloPedido> getArticulos() {
		return articulos;
	}

	public void setArticulos(List<ArticuloPedido> articulos) {
		this.articulos = articulos;
	}
	
	
	
}
