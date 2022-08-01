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
import static control.Main.conn;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URISyntaxException;
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
import recursos.ruta;

/**
 * FXML Controller class
 *
 * @author Alanprogrammer
 */
public class configuracion_servidor_control implements Initializable {

    @FXML
    private JFXTextField tbox_host;
    @FXML
    private JFXTextField tbox_port;
    @FXML
    private JFXTextField tbox_user;
    @FXML
    private JFXPasswordField tbox_password;
    @FXML
    private JFXButton bt_cancelar;
    @FXML
    private JFXButton bt_guardar;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
         try (InputStream configuracion = new FileInputStream("configuracion.properties") ) {
            Properties datosConexion = new Properties();
            datosConexion.load(configuracion);
            System.out.println();
            
            tbox_host.setText(datosConexion.getProperty("host")); 
            tbox_port.setText(datosConexion.getProperty("port"));
            tbox_user.setText(datosConexion.getProperty("user"));
            tbox_password.setText(datosConexion.getProperty("password"));
           } catch (IOException ex) {
            Logger.getLogger(configuracion_servidor_control.class.getName()).log(Level.SEVERE, null, ex);
        }
         
        //`Puerto
        RegexValidator validar_port = new RegexValidator();
        validar_port.setRegexPattern("^[0-9]+$");
        
        tbox_port.getValidators().add(validar_port);
        
        tbox_port.textProperty().addListener((o, anterior, nuevo) -> {
            if(tbox_port.getText().isEmpty()){
              tbox_port.setText("");
            }
            else if(!tbox_port.validate()){
                tbox_port.setText(anterior);
            }
            
        });
    
    
    }    

    @FXML
    private void guardar(MouseEvent event) {
        Alert alerta;
        
        if(!tbox_host.getText().isEmpty() && !tbox_port.getText().isEmpty() && !tbox_user.getText().isEmpty() && !tbox_password.getText().isEmpty()){
        
        try(OutputStream configuracion = new FileOutputStream("configuracion.properties")){
            Properties datosConexion = new Properties();
            datosConexion.setProperty("host",tbox_host.getText());
            datosConexion.setProperty("port", tbox_port.getText());
            datosConexion.setProperty("user",  tbox_user.getText());
            datosConexion.setProperty("password",  tbox_password.getText());
            datosConexion.store(configuracion, null);
            Main.conn = new  MariaDbPoolDataSource();
            Main.conn.setLoginTimeout(3);
                //conn.setMinPoolSize(3);
            Main.conn.setMaxPoolSize(5);
            Main.conn.setMaxIdleTime(60);
            
            Main.conn.setServerName(tbox_host.getText());
            Main.conn.setPort(Integer.parseInt(tbox_port.getText()));
            Main.conn.setUser(tbox_user.getText());
            Main.conn.setPassword(tbox_password.getText());
            Main.conn.setDatabaseName("plataforma_restaurante");
            Main.conn.initialize();
            alerta = new Alert(Alert.AlertType.INFORMATION);
            alerta.setHeaderText("Exito");
            alerta.initOwner(Main.stage);
            alerta.setContentText("Se actualizo la información correctamente");
            alerta.show();
            cerrar(event);
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

    @FXML
    private void cancelar(MouseEvent event) {
        cerrar(event);
    }
    
    

  
    
}
