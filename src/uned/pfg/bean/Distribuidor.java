package uned.pfg.bean;

import java.util.Date;

/**
 * Clase que representa el bean de Distribuidor, que dispone de los siguientes campos de clase;
 * 
 * identificador de distribuidor, telefono, codigo postal, nombre, domicilio, email, pais, usuario, 
 * contraseña y fecha de alta.
 * 
 * 
 * @author JORGE VILLALBA RUIZ 47536486V
 * @version 1.0
 */
public class Distribuidor {
	
	int id, tfno, cp;
	String nombre, domicilio, email, pais, user, password;
	Date fecha;
	
	
	/**
	 * Contructor que inicializa los campos de clase mediantes los parametros
	 * @param id Representa el identificador del distribuidor.
	 * @param tfno Representa el numero de telefono del distribuidor.
	 * @param cp Representa el codigo postal del distribuidor.
	 * @param nombre Representa el nombre de un distribuidor.
	 * @param domicilio Representa el domicilio que el distribuidor tiene como envio
	 * @param email Representa el correo electronico del distribuidor
	 * @param pais Representa el pais de origen del distribuidor
	 * @param user Representa el nick del distribuidor para darse de alta en el sistema.
	 * @param password Representa la contraseña del distribuidor para entrar en el sistema
	 * @param fecha Representa la fecha de alta del distribuidor en el sistema.
	 */
	public Distribuidor(int id, int tfno, int cp, String nombre, String domicilio, String email, String pais,
			String user, String password, Date fecha) {
		this.id = id;
		this.tfno = tfno;
		this.cp = cp;
		this.nombre = nombre;
		this.domicilio = domicilio;
		this.email = email;
		this.pais = pais;
		this.user = user;
		this.password = password;
		this.fecha = fecha;
	}


	/**
	 * Contructor que inicializa los campos de clase mediantes los parametros
	 * @param tfno Representa el numero de telefono del distribuidor.
	 * @param cp Representa el codigo postal del distribuidor.
	 * @param nombre Representa el nombre de un distribuidor.
	 * @param domicilio Representa el domicilio que el distribuidor tiene como envio
	 * @param email Representa el correo electronico del distribuidor
	 * @param pais Representa el pais de origen del distribuidor
	 * @param user Representa el nick del distribuidor para darse de alta en el sistema.
	 * @param password Representa la contraseña del distribuidor para entrar en el sistema
	 * @param fecha Representa la fecha de alta del distribuidor en el sistema.
	 */
	public Distribuidor(int tfno, int cp, String nombre, String domicilio, String email, String pais, String user,
			String password, Date fecha) {
		this.tfno = tfno;
		this.cp = cp;
		this.nombre = nombre;
		this.domicilio = domicilio;
		this.email = email;
		this.pais = pais;
		this.user = user;
		this.password = password;
		this.fecha = fecha;
	}

	
	/**
	 * Contructor que inicializa los campos de clase mediantes los parametros
	 * @param id Representa el identificador del distribuidor.
	 * @param nombre Representa el nombre de un distribuidor.
	 */
	public Distribuidor(int id, String nombre) {
		this.id = id;
		this.nombre = nombre;
	}


	/**
	 *  Devuelve el id del distribuidor.
	 * @return entero representativo al id de distribuidor
	 */
	public int getId() {
		return id;
	}


	/**
	 * Establece el id del distribuidor al valor pasado por parametro.
	 * @param id Entero para inicializar el id del distribuidor
	 */
	public void setId(int id) {
		this.id = id;
	}


	/**
	 * Devuelve el valor entero del telefono del distribuidor.
	 * @return Entero representativo al numero de telefono del distribuidor.
	 */
	public int getTfno() {
		return tfno;
	}

	/**
	 * Establece el numero de telefono del distribuidor al valor entero pasado por parametro
	 * @param tfno Valor representativo al numero de telefono del distribuidor
	 */
	public void setTfno(int tfno) {
		this.tfno = tfno;
	}

	/**
	 * Devuelve el valor representativo al codido postal del distribuidor.
	 * @return Valor entero que representa el codido postal.
	 */
	public int getCp() {
		return cp;
	}

	/**
	 * Estable el codigo postal del distribuidor al valor entero pasado por parametro.
	 * @param cp Valor entero representativo al codigo postal del distribuidor.
	 */
	public void setCp(int cp) {
		this.cp = cp;
	}

	/**
	 * Devuelve el nombre del distribuidor.
	 * @return Cadena de caracteres representativo al nombre del distribuidor.
	 */
	public String getNombre() {
		return nombre;
	}

	/**
	 * Establece el nombre del distribuidor al valor pasado por parametro
	 * @param nombre String representativo al nombre del distribuidor.
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	/**
	 * Devuelve el valor de tipo String que representa el domicilio de envio del distribuidor.
	 * @return Valor de tipo String que representa al domicilio de envio.
	 */
	public String getDomicilio() {
		return domicilio;
	}

	/**
	 * Establece el domicilio del distribuidor al valor pasado por parametro
	 * @param domicilio Valor de tipo String que representa al domicilio.
	 */
	public void setDomicilio(String domicilio) {
		this.domicilio = domicilio;
	}

	/**
	 * Devuelve el correo electronico del distribuidor.
	 * @return Valor de tipo String representativo al correo electronico.
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * Establece el email de distribuidor al pasado por parametro
	 * @param email Valor de tipo String que representa al correo.
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * Devuelve el valor del pais del distribuidor
	 * @return String que representa al pais
	 */
	public String getPais() {
		return pais;
	}

	/**
	 * Establace el pais del distribuidor al valor pasado por parametro
	 * @param pais Valor de tipo String que representa el pais. 
	 */
	public void setPais(String pais) {
		this.pais = pais;
	}

	/**
	 * Devuelve el valor del nick del distribuidor
	 * @return String que representa al nick
	 */
	public String getUser() {
		return user;
	}

	/**
	 * Establace el valor del nick del distribuidor al pasado por parametro.
	 * @param user Valor de tipo String que representa al nick del distribuidor.
	 */
	public void setUser(String user) {
		this.user = user;
	}

	/**
	 * Devuelve el valor de la contraseña del distribudidor.
	 * @return Valor de tipo String que representa la constraseña del distribuidor.
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * Establece el valor del password al valor pasado por parametro.
	 * @param password Valor representativo al password del distribuidor
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * Devuelve la fecha de alta del distribuidor
	 * @return Valor de tipo Date que representa a la fecha de alta
	 */
	public Date getFecha() {
		return fecha;
	}

	/**
	 * Establece la fecha de alta del sitema del distribuidor al valor pasado por parametro
	 * @param fecha Valor de tipo Date que representa la fecha de alta.
	 */
	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}
	
	
	

}
