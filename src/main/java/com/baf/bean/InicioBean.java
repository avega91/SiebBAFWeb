package com.baf.bean;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import org.apache.log4j.Logger;

@ManagedBean
@SessionScoped
public class InicioBean implements Serializable {
	static final long serialVersionUID = 1L;
	static final Logger logger = Logger.getLogger(InicioBean.class);
	
	public String cerrarSesion() {
		FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
		return "/index?faces-redirect=true";
	}
}
