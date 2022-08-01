/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXPasswordField;
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
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import modelo.Empleado;
import modelo.Puesto;

/**
 * FXML Controller class
 *
 * @author Alanprogrammer
 */
public class nuevo_empleado_control implements Initializable {

    @FXML
    private JFXTextField tbox_codEmpleado;
    @FXML
    private JFXTextField tbox_nombre;
    @FXML
    private JFXTextField tbox_apellidoP;
    @FXML
    private JFXTextField tbox_apellidoM;
    @FXML
    private JFXComboBox<Puesto> cbox_puesto;
    private ObservableList<Puesto> puestoLista = FXCollections.observableArrayList();
    @FXML
    private JFXButton bt_cancelar;
    @FXML
    private JFXButton bt_agregar;
    @FXML
    private JFXPasswordField tbox_password;

    

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        actualizarPuestos();
        cbox_puesto.setItems(puestoLista);
        
        //Validacion
       
        RegexValidator validar_codigo = new RegexValidator();
        validar_codigo.setRegexPattern("^[0-9]+$");
        RegexValidator validar_letras = new RegexValidator();
        validar_letras.setRegexPattern("^[a-zA-Z]+$");
        
        tbox_codEmpleado.getValidators().add(validar_codigo);
        tbox_nombre.getValidators().add(validar_letras);
        tbox_apellidoP.getValidators().add(validar_letras);
        tbox_apellidoM.getValidators().add(validar_letras);
        
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
         
        tbox_codEmpleado.textProperty().addListener((o, anterior, nuevo) -> {
            if(tbox_codEmpleado.getText().isEmpty()){
              tbox_codEmpleado.setText("");
            }
            else if(!tbox_codEmpleado.validate()){
                tbox_codEmpleado.setText(anterior);
            }
            
        });
        
    }    

    
    
    private void actualizarPuestos(){
       puestoLista.clear();
       Connection conn=null;
       PreparedStatement sentencia=null;
        ResultSet resultado = null;
       try {
        conn = Main.conn.getConnection();
        sentencia = conn.prepareStatement("SELECT * FROM puestos");
        resultado = sentencia.executeQuery();
        while(resultado.next()){
           puestoLista.add(new Puesto(resultado.getInt("id_puesto"),resultado.getString("nombre_pu")));
        }
    }   catch (SQLException ex) {
            Logger.getLogger(nuevo_empleado_control.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
          try{ if(conn!=null) sentencia.close();} catch(Exception e){};
          try{ if(conn!=null) conn.close();} catch(Exception e){};
        }
          
    }

    @FXML
    private void agregarEmpleado(MouseEvent event){
        Alert alerta;
        Node nodo = (Node) event.getSource();
        Stage escenario = (Stage) nodo.getScene().getWindow() ;
        if(!tbox_codEmpleado.getText().isEmpty() && !tbox_nombre.getText().isEmpty() 
             && !tbox_apellidoP.getText().isEmpty() && !tbox_apellidoM.getText().isEmpty() 
                && !tbox_password.getText().isEmpty()   && 
                !cbox_puesto.getSelectionModel().isEmpty()
                ){
        
        
        
        
        Connection conn=null;
        PreparedStatement sentencia=null;

        try{
         conn= Main.conn.getConnection();
         sentencia = conn.prepareStatement("INSERT INTO empleados(codigo_empleado, nombres_e, apellido_me, apellido_pe, password, puesto_e) VALUES (?,?,?,?,?,?)");
         sentencia.setString(1, tbox_codEmpleado.getText() );
         sentencia.setString(2, tbox_nombre.getText());
         sentencia.setString(3, tbox_apellidoP.getText());
         sentencia.setString(4, tbox_apellidoM.getText());
         sentencia.setString(5, tbox_password.getText());
         sentencia.setInt(6, cbox_puesto.getSelectionModel().getSelectedItem().getId_puesto());
         sentencia.executeUpdate();
         cerrar(event);
         alerta = new Alert(Alert.AlertType.INFORMATION);
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
            System.out.println("yo estoy fuera");
            alerta = new Alert(Alert.AlertType.ERROR, "Hay campos vacio");
            alerta.setTitle("ERROR");
            alerta.setHeaderText("Hay campos vacios");
            alerta.setContentText("Por favor llene todos los campos requeridos");
            alerta.initOwner(escenario);
            alerta.showAndWait();
            
            
        }
     
        
        
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
    
    
    
}
