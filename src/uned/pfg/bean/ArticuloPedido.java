package uned.pfg.bean;

public class ArticuloPedido {
	
	private Articulo articulo;
	private int cant;
	private Pedido pedido;
	boolean realizado, embalado;
	
	
	
	public ArticuloPedido(Articulo articulo, int cant, Pedido pedido, boolean realizado, boolean embalado) {
		this.articulo = articulo;
		this.cant = cant;
		this.pedido = pedido;
		this.realizado = realizado;
		this.embalado = embalado;
	}


	

	public ArticuloPedido(Articulo articulo, int cant, boolean realizado, boolean embalado) {
		this.articulo = articulo;
		this.cant = cant;
		this.realizado = realizado;
		this.embalado = embalado;
	}




	public Pedido getPedido() {
		return pedido;
	}



	public void setPedido(Pedido pedido) {
		this.pedido = pedido;
	}



	public boolean isRealizado() {
		return realizado;
	}



	public void setRealizado(boolean realizado) {
		this.realizado = realizado;
	}



	public boolean isEmbalado() {
		return embalado;
	}



	public void setEmbalado(boolean embalado) {
		this.embalado = embalado;
	}



	public ArticuloPedido(Articulo articulo, int cant) {
		this.articulo = articulo;
		this.cant = cant;
	}



	public Articulo getArticulo() {
		return articulo;
	}



	public void setArticulo(Articulo articulo) {
		this.articulo = articulo;
	}



	public int getCant() {
		return cant;
	}



	public void setCant(int cant) {
		this.cant = cant;
	}



	@Override
	public String toString() {
		return "ArticuloPedido [articulo=" + articulo + ", cant=" + cant + "]";
	}

	
}
