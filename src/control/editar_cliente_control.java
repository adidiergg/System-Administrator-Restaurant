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
import modelo.Cliente;

/**
 * FXML Controller class
 *
 * @author Alanprogrammer
 */
public class editar_cliente_control implements Initializable {

    @FXML
    private JFXTextField tbox_nombre;
    @FXML
    private JFXTextField tbox_apellidoP;
    @FXML
    private JFXTextField tbox_apellidoM;
    @FXML
    private JFXTextField tbox_direccion;
    @FXML
    private JFXTextField tbox_cp;
    @FXML
    private JFXTextField tbox_rfc;
    @FXML
    private JFXTextField tbox_celular;
    @FXML
    private JFXTextField tbox_correo;
    @FXML
    private JFXButton bt_cancelar;
    @FXML
    private JFXButton bt_aceptar;
    private int idCliente;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        RegexValidator validar_telefono = new RegexValidator();
        validar_telefono.setRegexPattern("^[0-9]{1,10}$");
        RegexValidator validar_cp = new RegexValidator();
        validar_cp.setRegexPattern("^[0-9]{1,5}$");

        RegexValidator validar_letras = new RegexValidator();
        validar_letras.setRegexPattern("^[a-zA-Z]+$");
        
        tbox_nombre.getValidators().add(validar_letras);
        tbox_apellidoP.getValidators().add(validar_letras);
        tbox_apellidoM.getValidators().add(validar_letras);
        tbox_celular.getValidators().add(validar_telefono);
        tbox_cp.getValidators().add(validar_cp);
        tbox_cp.textProperty().addListener((o, anterior, nuevo) -> {
            if(tbox_cp.getText().isEmpty()){
               tbox_cp.setText("");
            }
            else if(!tbox_cp.validate()){
                tbox_cp.setText(anterior);
            }
            
        });
        
          tbox_celular.textProperty().addListener((o, anterior, nuevo) -> {
            if( tbox_celular.getText().isEmpty()){
               tbox_celular.setText("");
            }
            else if(!tbox_celular.validate()){
                 tbox_celular.setText(anterior);
            }
            
        }); 
         
        tbox_nombre.textProperty().addListener((o, anterior, nuevo) -> {
            if(tbox_nombre.getText().isEmpty()){
              tbox_nombre.setText("");
            }
            else if(!tbox_nombre.validate()){
                tbox_nombre.setText(anterior);
            }
            
        });
        
         tbox_apellidoP.textProperty().addListener((o, anterior, nuevo) -> {
            if(tbox_apellidoP.getText().isEmpty()){
              tbox_apellidoP.setText("");
            }
            else if(!tbox_apellidoP.validate()){
                tbox_apellidoP.setText(anterior);
            }
            
        });
         
         tbox_apellidoM.textProperty().addListener((o, anterior, nuevo) -> {
            if( tbox_apellidoM.getText().isEmpty()){
               tbox_apellidoM.setText("");
            }
            else if(! tbox_apellidoM.validate()){
                 tbox_apellidoM.setText(anterior);
            }
            
        });
    }    

    @FXML
    private void cancelar(MouseEvent event) {
      cerrar(event);
    }

    @FXML
    private void editarCliente(MouseEvent event) {
        Connection conn=null;
        PreparedStatement sentencia=null;
        Alert alerta;
        Node nodo = (Node) event.getSource();
        Stage escenario = (Stage) nodo.getScene().getWindow() ;
        
        if(!tbox_nombre.getText().isEmpty() && !tbox_apellidoP.getText().isEmpty() 
                && !tbox_apellidoM.getText().isEmpty()   && 
                !tbox_direccion.getText().isEmpty()   && 
                  !tbox_rfc.getText().isEmpty()  && 
                  !tbox_cp.getText().isEmpty()  && 
                  !tbox_correo.getText().isEmpty()  && 
                  !tbox_celular.getText().isEmpty()  
                ){ 

        try{
         conn= Main.conn.getConnection();
         sentencia = conn.prepareStatement("UPDATE clientes SET rfc_cl=?,nombres_cl=?,apellidop_cl=?,apellidom_cl=?,direccion_cl=?,cp_cl=?,correo_cl=?,celular_cl=? WHERE id_cliente=?");
         sentencia.setString(1, tbox_rfc.getText());
         sentencia.setString(2, tbox_nombre.getText());
         sentencia.setString(3, tbox_apellidoP.getText());
         sentencia.setString(4, tbox_apellidoM.getText());
         sentencia.setString(5, tbox_direccion.getText());
         sentencia.setString(6, tbox_cp.getText());
         sentencia.setString(7, tbox_correo.getText());
         sentencia.setString(8, tbox_celular.getText());
         sentencia.setInt(9, idCliente);
         sentencia.executeUpdate();
         cerrar(event);
         alerta = new Alert(Alert.AlertType.INFORMATION);
         alerta.setHeaderText("Exito");
         alerta.setContentText("Se actualizo la información correctamente");
         alerta.initOwner(Main.stage);
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
    
     private void cerrar(MouseEvent event){
          Node nodo = (Node) event.getSource();
          Stage escenario = (Stage) nodo.getScene().getWindow();
          escenario.close();
      
     } 
    
     public void setClienteInformacion(Cliente clienteInfomarcion) {
        this.idCliente = clienteInfomarcion.getIdCliente();
        this.tbox_nombre.setText(clienteInfomarcion.getNombre());
        this.tbox_apellidoP.setText(clienteInfomarcion.getApellidoP());
        this.tbox_apellidoM.setText(clienteInfomarcion.getApellidoM());
        this.tbox_rfc.setText(clienteInfomarcion.getRfc());
        this.tbox_direccion.setText(clienteInfomarcion.getDireccion() );
        this.tbox_cp.setText(clienteInfomarcion.getCp());
        this.tbox_correo.setText(clienteInfomarcion.getCorreo());
        this.tbox_celular.setText(clienteInfomarcion.getCelular());

     }
    
}
