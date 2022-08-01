/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.validation.RegexValidator;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import modelo.Empleado;
import modelo.Proveedor;

/**
 * FXML Controller class
 *
 * @author Alanprogrammer
 */
public class editar_proveedor_control implements Initializable {

    @FXML
    private JFXTextField tbox_nombre;
    @FXML
    private JFXTextField tbox_telefono;
    @FXML
    private JFXTextField tbox_correo;
    @FXML
    private JFXButton bt_cancelar;
    @FXML
    private JFXButton bt_aceptar;
    
    private int clave;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        RegexValidator validar_telefono = new RegexValidator();
        validar_telefono.setRegexPattern("^[0-9]{1,10}$");
        
        tbox_telefono.getValidators().add(validar_telefono);
        
         tbox_telefono.textProperty().addListener((o, anterior, nuevo) -> {
            if(tbox_telefono.getText().isEmpty()){
              tbox_telefono.setText("");
            }
            else if(!tbox_telefono.validate()){
                tbox_telefono.setText(anterior);
            }
            
        });  
    }    

    @FXML
    private void cancelar(MouseEvent event) {
      cerrar(event);
    }

    @FXML
    private void editarProveedor(MouseEvent event) {
        Alert alerta;
        Node nodo = (Node) event.getSource();
        Stage escenario = (Stage) nodo.getScene().getWindow() ;
        if(!tbox_nombre.getText().isEmpty() 
             && !tbox_correo.getText().isEmpty() && !tbox_telefono.getText().isEmpty() 
                ){
        Connection conn=null;
        PreparedStatement sentencia=null;

        try{
         conn= Main.conn.getConnection();
         sentencia = conn.prepareStatement("UPDATE proveedores SET nombre_pr=?, telefono_pr=?, correo_pr=? WHERE id_proveedor=?");
         sentencia.setString(1, tbox_nombre.getText());
         sentencia.setString(2, tbox_telefono.getText());
         sentencia.setString(3, tbox_correo.getText());
         sentencia.setInt(4, clave);
         sentencia.executeUpdate();
         cerrar(event);
         alerta = new Alert(Alert.AlertType.INFORMATION);
         alerta.setHeaderText("Exito");
         alerta.setContentText("Se actualizo la información correctamente");
         alerta.initOwner(Main.stage);
         alerta.showAndWait();
         
         
         
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
    
     public void setProveedorInformacion(Proveedor proveedorInfomarcion) {
         this.clave = proveedorInfomarcion.getClave();
        this.tbox_nombre.setText(proveedorInfomarcion.getNombre());
        this.tbox_telefono.setText(proveedorInfomarcion.getTelefono());
        this.tbox_correo.setText(proveedorInfomarcion.getCorreo());
     }
     
     
     private void cerrar(MouseEvent event){
          Node nodo = (Node) event.getSource();
          Stage escenario = (Stage) nodo.getScene().getWindow();
          escenario.close();
      
      } 
    
    
    
}
