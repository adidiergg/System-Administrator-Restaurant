/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.mariadb.jdbc.MariaDbPoolDataSource;

/**
 *
 * @author Alanprogrammer
 */
public class Conexion {
    private String user;
    private String password;
    private String host;
    private int port;
    private MariaDbPoolDataSource conn;
   
    
    public MariaDbPoolDataSource connectar(String user,String password,String host,int port){
        try { 
            
            conn.setUser(user);
            conn.setPassword(password);
         
        } catch (SQLException ex) {
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
        }
       
        return conn;   
    }
    
    public static void main(String[] args){
        System.out.println("bb");
        Conexion conn = new Conexion();
        conn.connectar("root", "root","pp" , 0);
              
    }
    
    
}
