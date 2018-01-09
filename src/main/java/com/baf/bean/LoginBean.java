package com.baf.bean;

import java.io.Serializable;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.xml.ws.http.HTTPException;

import org.apache.log4j.Logger;

import com.baf.util.ValidacionUsuarios;

@ManagedBean
@SessionScoped
public class LoginBean implements Serializable {
	static final long serialVersionUID = 1L;
	final static Logger logger = Logger.getLogger(LoginBean.class);
	
	final String INICIO_PAGE_REDIRECT = "content/inicio.xhtml?faces-redirect=true";
	final String LOGIN_PAGE_REDIRECT = "index?faces-redirect=true";

	String username;
	String password;
	
//	banderas
	boolean passwordIncorrecto = false;
	

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}

	public String getPassword() {
		return password;
	}
	
	public boolean isPasswordIncorrecto() {
		return passwordIncorrecto;
	}

	public void setPasswordIncorrecto(boolean passwordIncorrecto) {
		this.passwordIncorrecto = passwordIncorrecto;
	}

	// Validar en LDAP las credenciales del usuario
	public String validarUsuario() {
//		Validar que la sesion este activa
		if(checkSession()) {
			logger.debug("Validando usuario: " + this.username);
			try {
				ValidacionUsuarios objValidacion = new ValidacionUsuarios();
				if (objValidacion.isUsuarioAutentico(this.username, this.password)) {
					logger.debug("Ingreso exitoso");
					crearMensaje("info", "Bienvenido");
					return INICIO_PAGE_REDIRECT;
				}
			} catch (Exception ex) {
				logger.error(ex.getMessage());
				ex.printStackTrace();
			}
			logger.debug("El usuario " + this.username + " no esta registrado");
			passwordIncorrecto = true;
			crearMensaje("error", "Usuario o password incorrectos");
			return null;
		}else {
			return "error";
		}
	}

	// Generar un mensaje de error o exito segun el parametro severity (error, info)
	private void crearMensaje(String severity, String message) {
		FacesContext context = FacesContext.getCurrentInstance();
		switch (severity) {
		case "error":
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, null, message));
			break;
		case "info":
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, null, message));
			break;
		default:
			break;
		}
	}

	public String cerrarSesion() {
		FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
		return LOGIN_PAGE_REDIRECT;
	}
	
	private boolean checkSession() {
		try {
			HttpServletRequest request = (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
			if(request.getRequestedSessionId() != null && !request.isRequestedSessionIdValid()) {
//				Sesion no valida
				return false;
			}
//			sesion valida
			return true;
		}catch(HTTPException e) {
			logger.error("Error al obtener la sesion actual: ", e);
		}
		return false;
	}
	
	private void limpiarFormulario() {
		this.passwordIncorrecto = false;
		this.password = "";
		this.username = "";
	}
}
