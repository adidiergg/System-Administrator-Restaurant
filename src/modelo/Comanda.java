/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.sql.Date;
import java.sql.Time;

/**
 *
 * @author Alanprogrammer
 */
public class Comanda {
    private  int num;
    private Date fecha;
    private Time hora;
    private String estado;
    private int codEstado;
    private int codEmpleado;
    private String camarero;
    private int codCliente;
    private String cliente;
    private int comensales;
    private int codMesa;
    

    public Comanda(int num, Date fecha, Time hora, String estado, int codEstado, int codEmpleado, String camarero, int codCliente, String cliente, int comensales, int codMesa) {
        this.num = num;
        this.fecha = fecha;
        this.hora = hora;
        this.estado = estado;
        this.codEstado = codEstado;
        this.codEmpleado = codEmpleado;
        this.camarero = camarero;
        this.codCliente = codCliente;
        this.cliente = cliente;
        this.comensales = comensales;
        this.codMesa = codMesa;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
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

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public int getCodEstado() {
        return codEstado;
    }

    public void setCodEstado(int codEstado) {
        this.codEstado = codEstado;
    }

    public int getCodEmpleado() {
        return codEmpleado;
    }

    public void setCodEmpleado(int codEmpleado) {
        this.codEmpleado = codEmpleado;
    }

    public String getCamarero() {
        return camarero;
    }

    public void setCamarero(String camarero) {
        this.camarero = camarero;
    }

    public int getCodCliente() {
        return codCliente;
    }

    public void setCodCliente(int codCliente) {
        this.codCliente = codCliente;
    }

    public String getCliente() {
        return cliente;
    }

    public void setCliente(String cliente) {
        this.cliente = cliente;
    }

    public int getComensales() {
        return comensales;
    }

    public void setComensales(int comensales) {
        this.comensales = comensales;
    }

    public int getCodMesa() {
        return codMesa;
    }

    public void setCodMesa(int codMesa) {
        this.codMesa = codMesa;
    }

    

   
    
    
}
