package uned.pfg.controllers;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;


import uned.pfg.bean.Articulo;
import uned.pfg.bean.ArticuloPedido;
import uned.pfg.bean.Distribuidor;
import uned.pfg.bean.Pedido;
import uned.pfg.dao.AlmacenDAO;
import uned.pfg.dao.ArticuloDAO;

import uned.pfg.dao.PedidoDAO;

/**
 * Servlet implementation class ControllerNuevoPedido
 */
@WebServlet("/ControllerNuevoPedido")
public class ControllerNuevoPedido extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private ArticuloDAO articuloDAO;
	private PedidoDAO pedidoDAO;
	private AlmacenDAO almacenDAO;
	private static List<ArticuloPedido> listaSeleccionados = new ArrayList<ArticuloPedido>();
	
	
	@Resource(name="jdbc/prueba")
	private DataSource pool;
	
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		
		try {
			articuloDAO = new ArticuloDAO(pool);
			pedidoDAO = new PedidoDAO(pool);
			almacenDAO = new AlmacenDAO(pool);
		
		}catch (Exception e) {
			
			throw new ServletException();
		}
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String comando = request.getParameter("instruccion");
		
		if(comando == null) {
			
			HttpSession session = request.getSession();

			List<Articulo> listaArticulos= articuloDAO.getArticulos();
			session.setAttribute("listaArt", listaArticulos);
			Distribuidor dist = (Distribuidor) session.getAttribute("usuario");
			request.setAttribute("dist", dist);
			request.setAttribute("seleccionados", listaSeleccionados);
			request.setAttribute("numero", listaSeleccionados.size());
			RequestDispatcher dispatcher =request.getRequestDispatcher("/nuevoPedido.jsp");
			
			dispatcher.forward(request, response);
		}else if(comando.equals("eliminar")) {
			
			int codigo = Integer.parseInt(request.getParameter("codigo"));
			
			eliminarArticulo(codigo);
			HttpSession session = request.getSession();

			List<Articulo> listaArticulos= articuloDAO.getArticulos();
			session.setAttribute("listaArt", listaArticulos);
			Distribuidor dist = (Distribuidor) session.getAttribute("usuario");
			request.setAttribute("dist", dist);
			request.setAttribute("seleccionados", listaSeleccionados);
			request.setAttribute("numero", listaSeleccionados.size());
			RequestDispatcher dispatcher =request.getRequestDispatcher("/nuevoPedido.jsp");
			
			dispatcher.forward(request, response);
			
		
		}else if( comando.equals("formaliza")) {
			
			
			HttpSession session = request.getSession();
			Distribuidor dist = (Distribuidor) session.getAttribute("usuario");
			String lista = request.getParameter("lista");
			System.out.println(lista);
			
			Date act = new Date();
			
			
			
			String fecha = "2022-01-01";
			SimpleDateFormat formatofecha = new SimpleDateFormat("yyyy-MM-dd");
			String f = formatofecha.format(act);
			Date actual = null;
			Date d = null;
			try {
				d = formatofecha.parse(fecha);
				actual = formatofecha.parse(f);
			} catch (ParseException e) {
				
				e.printStackTrace();
			}
			
			
			Pedido p = new Pedido(dist.getId(), d, actual, "En Tramite", listaSeleccionados);
			
			
			p.setId_pedido(pedidoDAO.insertaPedido(p));
			
			
			
			if(pedidoDAO.insertarArticulos(p)) {
				
				request.setAttribute("dist", dist);
				
				listaSeleccionados.clear();
				
				RequestDispatcher dispatcher =request.getRequestDispatcher("/pedidoExito.jsp");
				
				dispatcher.forward(request, response);
				
			}else {
				
				
			}
			
		}
	}



	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		
		int id = Integer.parseInt(request.getParameter("nom"));
		int cantidad = Integer.parseInt(request.getParameter("cant"));
		
		Articulo a = buscaArticulo(id, request);
		
		ArticuloPedido artPedido = new ArticuloPedido(a, cantidad);
		if(!BuscaRepeticion(a, cantidad)){
			
			listaSeleccionados.add(artPedido);
		}
		
		
		HttpSession session = request.getSession();
		List<Articulo> listaArticulos= articuloDAO.getArticulos();
		session.setAttribute("listaArt", listaArticulos);
		Distribuidor dist = (Distribuidor) session.getAttribute("usuario");
		request.setAttribute("dist", dist);
		request.setAttribute("listaArt", listaArticulos);
		request.setAttribute("seleccionados", listaSeleccionados);
		request.setAttribute("numero", listaSeleccionados.size());
		RequestDispatcher dispatcher =request.getRequestDispatcher("/nuevoPedido.jsp");
		
		dispatcher.forward(request, response);
		
	}

	private boolean BuscaRepeticion(Articulo a, int cantidad) {
		
		Iterator<ArticuloPedido> it = listaSeleccionados.iterator();
		while(it.hasNext()) {
			
			ArticuloPedido artPed = it.next();
			if(artPed.getArticulo().getId_articulo() == a.getId_articulo()) {
				
				artPed.setCant(artPed.getCant()+cantidad);
				return true;
			}
		}
		
		return false;
		
	}

	private Articulo buscaArticulo(int id, HttpServletRequest request) {
		
		HttpSession session = request.getSession();
		@SuppressWarnings("unchecked")
		List<Articulo> list = (List<Articulo>) session.getAttribute("listaArt");
		
		Iterator<Articulo> it = list.iterator();
		
		while(it.hasNext()) {
			Articulo a = it.next();
			int id_a = a.getId_articulo();
			if(id_a == id) {
				
				return a;
			}
		}
		return new Articulo();
	}

	

	
	private void eliminarArticulo(int id) {
		
		Iterator<ArticuloPedido> it = listaSeleccionados.iterator();
		
		while(it.hasNext()) {
			
			ArticuloPedido a = it.next();
			int id_articulo = a.getArticulo().getId_articulo();
			
			if(id_articulo == id) {
				
				it.remove();
			}
		}
		
	}
}
