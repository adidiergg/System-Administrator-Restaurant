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
import java.util.ListIterator;
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
import modelo.Estado;
import modelo.Mesa;

/**
 * FXML Controller class
 *
 * @author Alanprogrammer
 */
public class editar_mesa_control implements Initializable {

    private JFXTextField tbox_mesa;
    @FXML
    private JFXTextField tbox_persona;
    @FXML
    private JFXTextField tbox_ubicacion;
    @FXML
    private JFXComboBox<Estado> cbox_estado;
    private JFXComboBox<Empleado> cbox_encargado;
    @FXML
    private JFXButton bt_cancelar;
    @FXML
    private JFXButton bt_guardar;

    
    private ObservableList<Empleado> encargadoLista = FXCollections.observableArrayList();
    private ObservableList<Estado> estadoLista = FXCollections.observableArrayList();
    private int mesa;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        RegexValidator validar_numero = new RegexValidator();
        validar_numero.setRegexPattern("^[0-9]+$");
        tbox_persona.getValidators().add(validar_numero);
       
        
        tbox_persona.textProperty().addListener((o, anterior, nuevo) -> {
            if(tbox_persona.getText().isEmpty()){
              tbox_persona.setText("");
            }
            else if(!tbox_persona.validate()){
                tbox_persona.setText(anterior);
            }
            
        });  
     
        cbox_estado.setItems(estadoLista);  
        actualizarEstados();
    }    

    @FXML
    private void cancelar(MouseEvent event) {
         cerrar(event);
    }

    @FXML
    private void editarMesa(MouseEvent event) {
        
        Alert alerta;
        Node nodo = (Node) event.getSource();
        Stage escenario = (Stage) nodo.getScene().getWindow() ;
        Connection conn=null;
        PreparedStatement sentencia=null;
        
        if(
            !tbox_persona.getText().isEmpty() && !tbox_ubicacion.getText().isEmpty() &&
                !cbox_estado.getSelectionModel().isEmpty()
                ){
        
        try{
         conn= Main.conn.getConnection();
         sentencia = conn.prepareStatement("UPDATE mesas SET personas_m=?, ubicacion=?, estado_m=? WHERE num_mesa=?");
         sentencia.setInt(1, Integer.parseInt(tbox_persona.getText()));
         sentencia.setString(2, tbox_ubicacion.getText());
         sentencia.setInt(3, cbox_estado.getSelectionModel().getSelectedItem().getId());
         sentencia.setInt(4, mesa);
         sentencia.executeUpdate();
         cerrar(event);
         alerta = new Alert(Alert.AlertType.INFORMATION);
         alerta.setHeaderText("Exito");
         alerta.initOwner(escenario);
         alerta.setContentText("Se actualizo la información correctamente");
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
    
    

     
     private void actualizarEstados(){
       estadoLista.clear();
       try {
        Connection conn = Main.conn.getConnection();
        PreparedStatement sentencia = conn.prepareStatement("SELECT * FROM estados_mesa");
        ResultSet resultado = sentencia.executeQuery();
        while(resultado.next()){
            estadoLista.add(new Estado(resultado.getInt("id_estado_em"),resultado.getString("estado_em")));
        }
    }   catch (SQLException ex) {
            Logger.getLogger(nuevo_empleado_control.class.getName()).log(Level.SEVERE, null, ex);
        }
          
    }
     public void setMesaInformacion(Mesa mesaInfomarcion) {
        this.mesa =mesaInfomarcion.getNumMesa();
        this.tbox_persona.setText(Integer.toString( mesaInfomarcion.getPersonas() ));
        this.tbox_ubicacion.setText( mesaInfomarcion.getUbicacion());
       
        
        ListIterator i =  cbox_estado.getItems().listIterator() ;
        while(i.hasNext()){
           if(i.next().toString().equals(mesaInfomarcion.getEstado())){
              System.out.println("cacth the moment");
              this.cbox_estado.getSelectionModel().select(i.nextIndex()-1);
              
           }
        }
        
        
        
        
        
    }
     
     private void cerrar(MouseEvent event){
          Node nodo = (Node) event.getSource();
          Stage escenario = (Stage) nodo.getScene().getWindow();
          escenario.close();
      
      } 
    
}
