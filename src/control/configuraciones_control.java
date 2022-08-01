/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.validation.RegexValidator;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.sql.SQLException;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import org.mariadb.jdbc.MariaDbPoolDataSource;

/**
 * FXML Controller class
 *
 * @author Alanprogrammer
 */
public class configuraciones_control implements Initializable {

    @FXML
    private JFXTextField tbox_caja;
    @FXML
    private JFXTextField tbox_nombre;
    @FXML
    private JFXTextField tbox_rfc;
    @FXML
    private JFXTextField tbox_correo;
    @FXML
    private JFXTextField tbox_telefono;
    @FXML
    private JFXTextField tbox_direccion;
    @FXML
    private JFXTextField tbox_host;
    @FXML
    private JFXTextField tbox_port;
    @FXML
    private JFXTextField tbox_user;
    @FXML
    private JFXPasswordField tbox_pass;
    @FXML
    private JFXButton bt_guardar;
    @FXML
    private JFXTextField tbox_web;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        try (InputStream configuracion = new FileInputStream("configuracion.properties") ) {
            Properties datosConexion = new Properties();
            datosConexion.load(configuracion);
            //Configuracion informacion de la empresa
            tbox_caja.setText(datosConexion.getProperty("caja"));
            tbox_nombre.setText(datosConexion.getProperty("empresa"));
            tbox_rfc.setText(datosConexion.getProperty("rfc"));
            tbox_correo.setText(datosConexion.getProperty("correo"));
            tbox_telefono.setText(datosConexion.getProperty("telefono"));
            tbox_web.setText(datosConexion.getProperty("web"));
            tbox_direccion.setText(datosConexion.getProperty("direccion"));
            
            
            //Configuracion servidor
            tbox_host.setText(datosConexion.getProperty("host")); 
            tbox_port.setText(datosConexion.getProperty("port"));
            tbox_user.setText(datosConexion.getProperty("user"));
            tbox_pass.setText(datosConexion.getProperty("password"));
           } catch (IOException ex) {
            Logger.getLogger(configuracion_servidor_control.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        RegexValidator validar_numero = new RegexValidator();
        validar_numero.setRegexPattern("^[0-9]+$");
        tbox_caja.getValidators().add(validar_numero);
        
        tbox_caja.textProperty().addListener((o, anterior, nuevo) -> {
            if(tbox_caja.getText().isEmpty()){
              tbox_caja.setText("");
            }
            else if(!tbox_caja.validate()){
                tbox_caja.setText(anterior);
            }
            
        });
    }    

    @FXML
    private void guardarConfiguracion(MouseEvent event) {
        Alert alerta;
        
        if(!tbox_host.getText().isEmpty() && !tbox_port.getText().isEmpty() && !tbox_user.getText().isEmpty() && 
                !tbox_pass.getText().isEmpty() &&
                !tbox_caja.getText().isEmpty() &&
                !tbox_nombre.getText().isEmpty() &&
                !tbox_rfc.getText().isEmpty() &&
                !tbox_correo.getText().isEmpty() && 
                !tbox_telefono.getText().isEmpty() && 
                !tbox_web.getText().isEmpty() &&
                !tbox_direccion.getText().isEmpty()
                ){
        
        try(OutputStream configuracion = new FileOutputStream("configuracion.properties")){
            Properties datosConexion = new Properties();
            datosConexion.setProperty("caja", tbox_caja.getText());
            datosConexion.setProperty("empresa",tbox_nombre.getText() );
            datosConexion.setProperty("rfc", tbox_rfc.getText());
            datosConexion.setProperty("correo", tbox_correo.getText());
            datosConexion.setProperty("telefono", tbox_telefono.getText());
            datosConexion.setProperty("web", tbox_web.getText());
            datosConexion.setProperty("direccion", tbox_direccion.getText());
            datosConexion.setProperty("host",tbox_host.getText());
            datosConexion.setProperty("port", tbox_port.getText());
            datosConexion.setProperty("user",  tbox_user.getText());
            datosConexion.setProperty("password",  tbox_pass.getText());
            datosConexion.store(configuracion, null);
            Main.caja= Integer.parseInt(tbox_caja.getText());
            Main.conn = new  MariaDbPoolDataSource();
            Main.conn.setLoginTimeout(3);
                //conn.setMinPoolSize(3);
            Main.conn.setMaxPoolSize(5);
            Main.conn.setMaxIdleTime(60);
            
            Main.conn.setServerName(tbox_host.getText());
            Main.conn.setPort(Integer.parseInt(tbox_port.getText()));
            Main.conn.setUser(tbox_user.getText());
            Main.conn.setPassword(tbox_pass.getText());
            Main.conn.setDatabaseName("plataforma_restaurante");
            Main.conn.initialize();
            alerta = new Alert(Alert.AlertType.INFORMATION);
            alerta.setHeaderText("Exito");
            alerta.initOwner(Main.stage);
            alerta.setContentText("Se actualizo la información correctamente");
            alerta.show();
            
        }catch (IOException ex) {
            Logger.getLogger(configuracion_servidor_control.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(configuracion_servidor_control.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        }else{
            
            alerta = new Alert(Alert.AlertType.ERROR, "Hay campos vacio");
            alerta.setTitle("ERROR");
            alerta.setHeaderText("Hay campos vacios");
            alerta.setContentText("Por favor llene todos los campos requeridos");
            alerta.initOwner(Main.stage);
            alerta.show();
        }
        
        
    }
    
     private void cerrar(MouseEvent event){
         Node nodo = (Node) event.getSource();
         Stage escenario = (Stage) nodo.getScene().getWindow() ;
         escenario.close();
    }
}
