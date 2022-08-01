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
import java.util.Calendar;
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
import modelo.Categoria;
import modelo.Cliente;
import modelo.Empleado;
import modelo.Mesa;

/**
 * FXML Controller class
 *
 */
public class nuevo_comanda_control implements Initializable {

    @FXML
    private JFXComboBox<Mesa> cbox_mesa;
    @FXML
    private JFXComboBox<Empleado> cbox_camarero;
    @FXML
    private JFXComboBox<Cliente> cbox_cliente;
    
   
    
    @FXML
    private JFXTextField tbox_personas;
    @FXML
    private JFXButton bt_cancelar;
    @FXML
    private JFXButton bt_crear;
    
    private ObservableList<Mesa> mesaLista = FXCollections.observableArrayList(); 
    private ObservableList<Empleado> camareroLista = FXCollections.observableArrayList();
    private ObservableList<Cliente> clienteLista= FXCollections.observableArrayList();
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        
        RegexValidator validar_numero = new RegexValidator();
        validar_numero.setRegexPattern("^[0-9]+$");
        tbox_personas.getValidators().add(validar_numero);
       
        
        tbox_personas.textProperty().addListener((o, anterior, nuevo) -> {
            if(tbox_personas.getText().isEmpty()){
              tbox_personas.setText("");
            }
            else if(!tbox_personas.validate()){
                tbox_personas.setText(anterior);
            }
            
        }); 
        
        
        actualizarMesas();
        cbox_mesa.setItems(mesaLista);
        
        actualizarCamareros();
        cbox_camarero.setItems(camareroLista);  
        
        actualizarClientes();
        cbox_cliente.setItems(clienteLista);  
        // TODO
        
       
    }    


    
    private void actualizarMesas(){
       mesaLista.clear();
       Connection conn=null;
       PreparedStatement sentencia=null;
       ResultSet resultado = null;
       try {
        conn = Main.conn.getConnection();
        sentencia = conn.prepareStatement("SELECT num_mesa from mesas WHERE estado_m=1");
        resultado = sentencia.executeQuery();
        while(resultado.next()){
           mesaLista.add( new Mesa(resultado.getInt("num_mesa")));
        }
    }   catch (SQLException ex) {
            Logger.getLogger(nuevo_empleado_control.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
          try{ if(conn!=null) sentencia.close();} catch(Exception e){};
          try{ if(conn!=null) conn.close();} catch(Exception e){};
        }
          
    }
    
    private void actualizarCamareros(){
       camareroLista.clear();
       Connection conn=null;
       PreparedStatement sentencia=null;
       ResultSet resultado = null;
       try {
         conn = Main.conn.getConnection();
        sentencia = conn.prepareStatement("SELECT codigo_empleado,CONCAT(nombres_e,' ',apellido_pe,' ',apellido_me) AS nombrecompleto FROM empleados WHERE puesto_e=3");
        resultado = sentencia.executeQuery();
        while(resultado.next()){
            camareroLista.add(new Empleado(resultado.getInt("codigo_empleado"),resultado.getString("nombrecompleto")));
        }
    }   catch (SQLException ex) {
            Logger.getLogger(nuevo_empleado_control.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
          try{ if(conn!=null) sentencia.close();} catch(Exception e){};
          try{ if(conn!=null) conn.close();} catch(Exception e){};
        }
          
    }
    
    private void actualizarClientes(){
       clienteLista.clear();
       Connection conn=null;
       PreparedStatement sentencia=null;
       ResultSet resultado = null;
       try {
        conn = Main.conn.getConnection();
        sentencia = conn.prepareStatement("SELECT id_cliente,CONCAT(nombres_cl,' ',apellidop_cl,' ',apellidom_cl) AS nombrecompleto FROM clientes");
        resultado = sentencia.executeQuery();
        while(resultado.next()){
            System.out.println("dfff");
            clienteLista.add(new Cliente(resultado.getInt("id_cliente"),resultado.getString("nombrecompleto")));
        }
    }   catch (SQLException ex) {
            Logger.getLogger(nuevo_empleado_control.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
          try{ if(conn!=null) sentencia.close();} catch(Exception e){};
          try{ if(conn!=null) conn.close();} catch(Exception e){};
        }
          
    }

    @FXML
    private void crearCMD(MouseEvent event) {
        Alert alerta;
        Node nodo = (Node) event.getSource();
        Stage escenario = (Stage) nodo.getScene().getWindow() ;
        Connection conn=null;
        PreparedStatement sentencia=null;
         if(
            !tbox_personas.getText().isEmpty() && !cbox_camarero.getSelectionModel().isEmpty() &&
                !cbox_mesa.getSelectionModel().isEmpty() && !cbox_cliente.getSelectionModel().isEmpty()
                ){

        try{
         
         conn= Main.conn.getConnection();
         sentencia = conn.prepareStatement("INSERT INTO comandas(fecha_c,hora_c, estado_c, camarero_c,cliente,num_comensales,mesa_c) VALUES(CURRENT_DATE(),CURRENT_TIME(),1,?,?,?,?)");
         sentencia.setInt(1, cbox_camarero.getSelectionModel().getSelectedItem().getCodigoEmpleado());
         sentencia.setInt(2, cbox_cliente.getSelectionModel().getSelectedItem().getIdCliente());
         sentencia.setInt(3, Integer.parseInt(tbox_personas.getText()));
         sentencia.setInt(4, cbox_mesa.getSelectionModel().getSelectedItem().getNumMesa());
         sentencia.executeUpdate();
         sentencia.close();
         
         sentencia = conn.prepareStatement("UPDATE mesas SET  estado_m=2 WHERE num_mesa=?");
         sentencia.setInt(1,  cbox_mesa.getSelectionModel().getSelectedItem().getNumMesa());
         sentencia.executeQuery();

         /*
         
         sentencia.setInt(1, Integer.parseInt(tbox_mesa.getText()));
        
         sentencia.setInt(1, Integer.parseInt(tbox_mesa.getText()));
         sentencia.setString(2, tbox_ubicacion.getText());
         sentencia.setInt(3, Integer.parseInt(tbox_persona.getText()));
         sentencia.setInt(4, 1);
         sentencia.setInt(5, cbox_encargado.getSelectionModel().getSelectedItem().getCodigoEmpleado());
         */        
         
         cerrar(event);
         alerta = new Alert(Alert.AlertType.INFORMATION);
         alerta.setHeaderText("Exito");
         alerta.setContentText("Usuario creado correctamente");
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
         Stage escenario = (Stage) nodo.getScene().getWindow() ;
         escenario.close();
    }

    @FXML
    private void cancelar(MouseEvent event) {
        cerrar(event);
    }
}
