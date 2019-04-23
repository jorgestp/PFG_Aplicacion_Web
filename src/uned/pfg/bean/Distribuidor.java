package uned.pfg.bean;

import java.util.Date;

public class Distribuidor {
	
	int id, tfno, cp;
	String nombre, domicilio, email, pais, user, password;
	Date fecha;
	
	
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

	

	public Distribuidor(int id, String nombre) {
		this.id = id;
		this.nombre = nombre;
	}


	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public int getTfno() {
		return tfno;
	}


	public void setTfno(int tfno) {
		this.tfno = tfno;
	}


	public int getCp() {
		return cp;
	}


	public void setCp(int cp) {
		this.cp = cp;
	}


	public String getNombre() {
		return nombre;
	}


	public void setNombre(String nombre) {
		this.nombre = nombre;
	}


	public String getDomicilio() {
		return domicilio;
	}


	public void setDomicilio(String domicilio) {
		this.domicilio = domicilio;
	}


	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}


	public String getPais() {
		return pais;
	}


	public void setPais(String pais) {
		this.pais = pais;
	}


	public String getUser() {
		return user;
	}


	public void setUser(String user) {
		this.user = user;
	}


	public String getPassword() {
		return password;
	}


	public void setPassword(String password) {
		this.password = password;
	}


	public Date getFecha() {
		return fecha;
	}


	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}
	
	
	

}
