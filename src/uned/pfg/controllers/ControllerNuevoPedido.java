package uned.pfg.controllers;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
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

import org.apache.commons.dbcp.BasicDataSource;

import uned.pfg.bean.Articulo;
import uned.pfg.bean.ArticuloPedido;
import uned.pfg.bean.Distribuidor;
import uned.pfg.bean.Pedido;
import uned.pfg.dao.AlmacenDAO;
import uned.pfg.dao.ArticuloDAO;

import uned.pfg.dao.PedidoDAO;
import uned.pfg.ws.PoolConexiones;

/**
 * Servlet para la introducir nuevos pedidos en el sistema 
 */
@WebServlet("/ControllerNuevoPedido")
public class ControllerNuevoPedido extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private ArticuloDAO articuloDAO;
	private PedidoDAO pedidoDAO;
	private AlmacenDAO almacenDAO;
	private static List<ArticuloPedido> listaSeleccionados = new ArrayList<ArticuloPedido>();
	private BasicDataSource basicDataSource;
	
	//@Resource(name="jdbc/prueba")
	//private DataSource pool;
	
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		
		try {
			//basicDataSource = PoolConexiones.getInstance().getConnection();
			articuloDAO = new ArticuloDAO(basicDataSource);
			pedidoDAO = new PedidoDAO(basicDataSource);
			almacenDAO = new AlmacenDAO(basicDataSource);
		
		}catch (Exception e) {
			
			throw new ServletException();
		}
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String comando = request.getParameter("instruccion");
		Date act = new Date();
		Calendar fech = Calendar.getInstance();
		if(comando == null) {
			
			HttpSession session = request.getSession();

			List<Articulo> listaArticulos= articuloDAO.getArticulos();
			session.setAttribute("listaArt", listaArticulos);
			Distribuidor dist = (Distribuidor) session.getAttribute("usuario");
			request.setAttribute("dist", dist);
			request.setAttribute("seleccionados", listaSeleccionados);
			request.setAttribute("numero", listaSeleccionados.size());
			request.setAttribute("inicio", fech.get(Calendar.YEAR));
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
			request.setAttribute("inicio", fech.get(Calendar.YEAR));
			RequestDispatcher dispatcher =request.getRequestDispatcher("/nuevoPedido.jsp");
			
			dispatcher.forward(request, response);
			
		
		}else if( comando.equals("formaliza")) {

			HttpSession session = request.getSession();
			Distribuidor dist = (Distribuidor) session.getAttribute("usuario");
			
			if(listaSeleccionados.isEmpty()) {
				

				List<Articulo> listaArticulos= articuloDAO.getArticulos();
				session.setAttribute("listaArt", listaArticulos);
				request.setAttribute("dist", dist);
				request.setAttribute("seleccionados", listaSeleccionados);
				request.setAttribute("numero", listaSeleccionados.size());
				request.setAttribute("inicio", fech.get(Calendar.YEAR));
				request.setAttribute("dist", dist);
				RequestDispatcher dispatcher =request.getRequestDispatcher("/pedidoVacio.jsp");
				
				dispatcher.forward(request, response);
				
			}else {
			
			String dia = request.getParameter("dia");
			String mes = request.getParameter("mes");
			String any = request.getParameter("any");
			
			if(dia ==null || mes == null || any ==null) {
				
				List<Articulo> listaArticulos= articuloDAO.getArticulos();
				session.setAttribute("listaArt", listaArticulos);
				request.setAttribute("dist", dist);
				request.setAttribute("seleccionados", listaSeleccionados);
				request.setAttribute("numero", listaSeleccionados.size());
				request.setAttribute("inicio", fech.get(Calendar.YEAR));
				request.setAttribute("dist", dist);
				RequestDispatcher dispatcher =request.getRequestDispatcher("/errorPedidoFecha.jsp");
				
				dispatcher.forward(request, response);
				
			}else {
				
				
				
				String fecha = any + "-" + mes + "-" + dia;
				SimpleDateFormat formatofecha = new SimpleDateFormat("yyyy-MM-dd");
				String f = formatofecha.format(act);
				Date actual = null;
				Date envio = null;
				try {
					envio = formatofecha.parse(fecha);
					actual = formatofecha.parse(f);
				} catch (ParseException e) {
					
					e.printStackTrace();
				}
				
				if(actual.after(envio)) {
					
					List<Articulo> listaArticulos= articuloDAO.getArticulos();
					session.setAttribute("listaArt", listaArticulos);
					request.setAttribute("dist", dist);
					request.setAttribute("seleccionados", listaSeleccionados);
					request.setAttribute("numero", listaSeleccionados.size());
					request.setAttribute("inicio", fech.get(Calendar.YEAR));
					request.setAttribute("dist", dist);
					RequestDispatcher dispatcher =request.getRequestDispatcher("/errorPedidoFecha.jsp");
					
					dispatcher.forward(request, response);
					
				}else {
					
					Pedido p = new Pedido(dist.getId(), actual, envio, "En Tramite", listaSeleccionados);
					
					
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
			
			
		}
			
		}
	}



	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		
		int id = Integer.parseInt(request.getParameter("nom"));
		int cantidad = Integer.parseInt(request.getParameter("cant"));
		Calendar fech = Calendar.getInstance();
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
		request.setAttribute("inicio", fech.get(Calendar.YEAR));
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
