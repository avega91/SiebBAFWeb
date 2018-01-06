package com.baf.bean;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import org.apache.log4j.Logger;

@ManagedBean
@SessionScoped
public class ClienteBean implements Serializable {
	static final long serialVersionUID = 1L;
	final static Logger logger = Logger.getLogger(LoginBean.class);
	
	private String[] motivo;
//	Direccion
	private String calle;
	private String numExterior;
	private String numInterior;
	private String piso;
	private String entreCalles;
	private int cp;
	private String estado;
	private String municipio;
	private String colonia;
	private int sector;
	private String tipoCasa;
	private int antiguedad;
//	Economicos
	private String dedicacion;
	private float ingresoMensual;
	private int antiguedadEmpleo;
	private long capacidadPago;
	private boolean ingresoExtra;
	private float percepcionMensual;
	
	public String[] getMotivo() {
		return motivo;
	}
	public void setMotivo(String[] motivo) {
		this.motivo = motivo;
	}
	public String getCalle() {
		return calle;
	}
	public void setCalle(String calle) {
		this.calle = calle;
	}
	public String getNumExterior() {
		return numExterior;
	}
	public void setNumExterior(String numExterior) {
		this.numExterior = numExterior;
	}
	public String getNumInterior() {
		return numInterior;
	}
	public void setNumInterior(String numInterior) {
		this.numInterior = numInterior;
	}
	public String getPiso() {
		return piso;
	}
	public void setPiso(String piso) {
		this.piso = piso;
	}
	public String getEntreCalles() {
		return entreCalles;
	}
	public void setEntreCalles(String entreCalles) {
		this.entreCalles = entreCalles;
	}
	public int getCp() {
		return cp;
	}
	public void setCp(int cp) {
		this.cp = cp;
	}
	public String getEstado() {
		return estado;
	}
	public void setEstado(String estado) {
		this.estado = estado;
	}
	public String getMunicipio() {
		return municipio;
	}
	public void setMunicipio(String municipio) {
		this.municipio = municipio;
	}
	public String getColonia() {
		return colonia;
	}
	public void setColonia(String colonia) {
		this.colonia = colonia;
	}
	public int getSector() {
		return sector;
	}
	public void setSector(int sector) {
		this.sector = sector;
	}
	public String getTipoCasa() {
		return tipoCasa;
	}
	public void setTipoCasa(String tipoCasa) {
		this.tipoCasa = tipoCasa;
	}
	public int getAntiguedad() {
		return antiguedad;
	}
	public void setAntiguedad(int antiguedad) {
		this.antiguedad = antiguedad;
	}
	public String getDedicacion() {
		return dedicacion;
	}
	public void setDedicacion(String dedicacion) {
		this.dedicacion = dedicacion;
	}
	public float getIngresoMensual() {
		return ingresoMensual;
	}
	public void setIngresoMensual(float ingresoMensual) {
		this.ingresoMensual = ingresoMensual;
	}
	public int getAntiguedadEmpleo() {
		return antiguedadEmpleo;
	}
	public void setAntiguedadEmpleo(int antiguedadEmpleo) {
		this.antiguedadEmpleo = antiguedadEmpleo;
	}
	public long getCapacidadPago() {
		return capacidadPago;
	}
	public void setCapacidadPago(long capacidadPago) {
		this.capacidadPago = capacidadPago;
	}
	public boolean isIngresoExtra() {
		return ingresoExtra;
	}
	public void setIngresoExtra(boolean ingresoExtra) {
		this.ingresoExtra = ingresoExtra;
	}
	public float getPercepcionMensual() {
		return percepcionMensual;
	}
	public void setPercepcionMensual(float percepcionMensual) {
		this.percepcionMensual = percepcionMensual;
	}
	
	public void enviarDatos() {
		logger.debug("Enviando los datos");
	}
}
