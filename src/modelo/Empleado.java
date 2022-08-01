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
public class Empleado {
    private int codigoEmpleado;
    private String nombre;
    private String apellidoP;
    private String apellidoM;
    private String pass;
    private String puesto;
    private String nombreCompleto;
    

    public Empleado(int codigoEmpleado, String nombre, String apellidoP, String apellidoM, String pass, String puesto) {
        this.codigoEmpleado = codigoEmpleado;
        this.nombre = nombre;
        this.apellidoP = apellidoP;
        this.apellidoM = apellidoM;
        this.pass = pass;
        this.puesto = puesto;
    }
    
     public Empleado(int codigoEmpleado, String nombre, String apellidoP, String apellidoM,  String puesto) {
        this.codigoEmpleado = codigoEmpleado;
        this.nombre = nombre;
        this.apellidoP = apellidoP;
        this.apellidoM = apellidoM;
        this.puesto = puesto;
    }

    public Empleado(int codigoEmpleado, String nombreCompleto) {
        this.codigoEmpleado = codigoEmpleado;
        this.nombreCompleto = nombreCompleto;
    }
    
    
     
     
    
   
   

    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }
    
    public void setCodigoEmpleado(int codigoEmpleado) {
        this.codigoEmpleado = codigoEmpleado;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setApellidoP(String apellidoP) {
        this.apellidoP = apellidoP;
    }

    public void setApellidoM(String apellidoM) {
        this.apellidoM = apellidoM;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }
    
    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public void setPuesto(String puesto) {
        this.puesto = puesto;
    }

    public int getCodigoEmpleado() {
        return codigoEmpleado;
    }

    public String getNombre() {
        return nombre;
    }

    public String getApellidoP() {
        return apellidoP;
    }

    public String getApellidoM() {
        return apellidoM;
    }

    public String getPass() {
        return pass;
    }

    public String getPuesto() {
        return puesto;
    }
    
    public String toString() {
        return nombreCompleto;
    }

    
     
    
    
}


