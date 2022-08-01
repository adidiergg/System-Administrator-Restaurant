/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.sql.Time;
import java.util.Date;

/**
 *
 * @author Alanprogrammer
 */
public class Venta {
    private int folio;
    private int caja;
    private Date fecha;
    private Time hora;
    private String cajero;
    private float cambio;
    private float efectivo;
    private float importe;
    private float propina;
    private float descuento;

    public Venta(int folio, int caja, Date fecha, Time hora, String cajero, float cambio, float efectivo, float importe, float propina, float descuento) {
        this.folio = folio;
        this.caja = caja;
        this.fecha = fecha;
        this.hora = hora;
        this.cajero = cajero;
        this.cambio = cambio;
        this.efectivo = efectivo;
        this.importe = importe;
        this.propina = propina;
        this.descuento = descuento;
    }

    public int getFolio() {
        return folio;
    }

    public void setFolio(int folio) {
        this.folio = folio;
    }

    public int getCaja() {
        return caja;
    }

    public void setCaja(int caja) {
        this.caja = caja;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Time getHora() {
        return hora;
    }

    public void setHora(Time hora) {
        this.hora = hora;
    }

    public String getCajero() {
        return cajero;
    }

    public void setCajero(String cajero) {
        this.cajero = cajero;
    }

    public float getCambio() {
        return cambio;
    }

    public void setCambio(float cambio) {
        this.cambio = cambio;
    }

    public float getEfectivo() {
        return efectivo;
    }

    public void setEfectivo(float efectivo) {
        this.efectivo = efectivo;
    }

    public float getImporte() {
        return importe;
    }

    public void setImporte(float importe) {
        this.importe = importe;
    }

    public float getPropina() {
        return propina;
    }

    public void setPropina(float propina) {
        this.propina = propina;
    }

    public float getDescuento() {
        return descuento;
    }

    public void setDescuento(float descuento) {
        this.descuento = descuento;
    }
    
    
    
    
}
