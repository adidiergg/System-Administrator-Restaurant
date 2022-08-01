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
public class PlatillosBebidas {
    private int clave ;
    private String nombre ;
    private String descripcion;
    private float precioUnitario;
    private float precio;
    private String estado;
    private String categoria;

    public PlatillosBebidas(int clave, String nombre, String descripcion, float precioUnitario, float precio, String estado, String categoria) {
        this.clave = clave;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precioUnitario = precioUnitario;
        this.precio = precio;
        this.estado = estado;
        this.categoria = categoria;
    }

    public PlatillosBebidas(int clave, String nombre, float precio, String estado, String categoria) {
        this.clave = clave;
        this.nombre = nombre;
        this.precio = precio;
        this.estado = estado;
        this.categoria = categoria;
    }
    
    
    
    
    
    public int getClave() {
        return clave;
    }

    public void setClave(int clave) {
        this.clave = clave;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public float getPrecioUnitario() {
        return precioUnitario;
    }

    public void setPrecioUnitario(float precioUnitario) {
        this.precioUnitario = precioUnitario;
    }

    public float getPrecio() {
        return precio;
    }

    public void setPrecio(float precio) {
        this.precio = precio;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    
   
    
}
