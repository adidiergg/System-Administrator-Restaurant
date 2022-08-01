/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

/**
 *
 * @author Alanprogrammer
 */
public class Mesa {
    private int numMesa;
    private String ubicacion ;
    private int personas ;
    private int idEstado;
    private String estado;

    public Mesa(int numMesa, String ubicacion, int personas,int idEstado, String estado) {
        this.numMesa = numMesa;
        this.ubicacion = ubicacion;
        this.personas = personas;
        this.idEstado = idEstado;
        this.estado = estado;
    }

    public Mesa(int numMesa) {
        this.numMesa = numMesa;
    }
    
    

    public int getNumMesa() {
        return numMesa;
    }

    public void setNumMesa(int numMesa) {
        this.numMesa = numMesa;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    public int getPersonas() {
        return personas;
    }

    public void setPersonas(int personas) {
        this.personas = personas;
    }

   

    

    public int getIdEstado() {
        return idEstado;
    }

    public void setIdEstado(int idEstado) {
        this.idEstado = idEstado;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    @Override
    public String toString() {
        return Integer.toString(numMesa);
    }
    
    
    
}