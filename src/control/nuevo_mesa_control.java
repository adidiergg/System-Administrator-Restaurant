/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.validation.RegexValidator;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import modelo.Empleado;
import modelo.Puesto;

/**
 * FXML Controller class
 *
 * @author Alanprogrammer
 */
public class nuevo_mesa_control implements Initializable {

    @FXML
    private JFXTextField tbox_mesa;
    @FXML
    private JFXTextField tbox_persona;
    @FXML
    private JFXTextField tbox_ubicacion;
    @FXML
    private JFXButton bt_cancelar;
    @FXML
    private JFXButton bt_agregar;
    
    

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        RegexValidator validar_numero = new RegexValidator();
        validar_numero.setRegexPattern("^[0-9]+$");
        tbox_persona.getValidators().add(validar_numero);
        tbox_mesa.getValidators().add(validar_numero);
        
        tbox_persona.textProperty().addListener((o, anterior, nuevo) -> {
            if(tbox_persona.getText().isEmpty()){
              tbox_persona.setText("");
            }
            else if(!tbox_persona.validate()){
                tbox_persona.setText(anterior);
            }
            
        });
        
        tbox_mesa.textProperty().addListener((o, anterior, nuevo) -> {
            if(tbox_mesa.getText().isEmpty()){
              tbox_mesa.setText("");
            }
            else if(!tbox_mesa.validate()){
                tbox_mesa.setText(anterior);
            }
            
        });
        
    }    

    @FXML
    private void cancelar(MouseEvent event) {
          cerrar(event);
    }

    
    
     private void cerrar(MouseEvent event){
         Node nodo = (Node) event.getSource();
         Stage escenario = (Stage) nodo.getScene().getWindow() ;
         escenario.close();
    }
     
     
  

    @FXML
    private void agregarMesa(MouseEvent event) {
        
        
        Alert alerta;
        Node nodo = (Node) event.getSource();
        Stage escenario = (Stage) nodo.getScene().getWindow() ;
        Connection conn=null;
        PreparedStatement sentencia=null;
        
        if(!tbox_mesa.getText().isEmpty() 
             && !tbox_persona.getText().isEmpty() && !tbox_ubicacion.getText().isEmpty() 
                ){
        

        try{
         conn= Main.conn.getConnection();
         sentencia = conn.prepareStatement("INSERT INTO mesas(num_mesa, ubicacion, personas_m, estado_m) VALUES (?,?,?,?)");
         sentencia.setInt(1, Integer.parseInt(tbox_mesa.getText()));
         sentencia.setString(2, tbox_ubicacion.getText());
         sentencia.setInt(3, Integer.parseInt(tbox_persona.getText()));
         sentencia.setInt(4, 1);
         sentencia.executeUpdate();
         cerrar(event);
         alerta = new Alert(Alert.AlertType.INFORMATION);
         alerta.initOwner(escenario);
         alerta.setHeaderText("Exito");
         alerta.setContentText("Usuario creado correctamente");
         alerta.show();
        } catch (SQLException ex) {
            System.out.println(ex);
            
        }finally{
          try{ if(conn!=null) sentencia.close();} catch(Exception e){};
          try{ if(conn!=null) conn.close();} catch(Exception e){};
        }
        
        }else{
           alerta = new Alert(Alert.AlertType.ERROR, "Hay campos vacio");
           alerta.setTitle("ERROR");
           alerta.setHeaderText("Hay campos vacios");
           alerta.setContentText("Por favor llene todos los campos requeridos");
           alerta.initOwner(escenario);
           alerta.showAndWait();
        }
    }
    
}
