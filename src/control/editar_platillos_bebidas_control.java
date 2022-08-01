/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import java.io.IOException;
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
import modelo.Categoria;
import modelo.Estado;
import modelo.PlatillosBebidas;

/**
 * FXML Controller class
 *
 * @author Alanprogrammer
 */
public class editar_platillos_bebidas_control implements Initializable {

    private JFXTextField tbox_pb;
    @FXML
    private JFXTextField tbox_nombre;
    @FXML
    private JFXTextArea tbox_descripcion;
    @FXML
    private JFXTextField tbox_precioU;
    @FXML
    private JFXTextField tbox_precio;
    @FXML
    private JFXComboBox<Categoria> cbox_categoria;
    @FXML
    private JFXComboBox<Estado> cbox_estado;
    @FXML
    private JFXButton bt_cancelar;
    @FXML
    private JFXButton bt_guardar;
    private ObservableList<Categoria> categoriaLista = FXCollections.observableArrayList();
    private ObservableList<Estado> estadoLista = FXCollections.observableArrayList();
    private int pb;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
         // TODO
         actualizarCategorias();
         cbox_categoria.setItems(categoriaLista);
         actualizarEstados();
         cbox_estado.setItems(estadoLista);
    }    

    @FXML
    private void cancelar(MouseEvent event) {
        cerrar(event);
    }

    @FXML
    private void editarPlatilloBebida(MouseEvent event) {
        Connection conn=null;
        PreparedStatement sentencia=null;
        Alert alerta;
        Node nodo = (Node) event.getSource();
        Stage escenario = (Stage) nodo.getScene().getWindow() ;
        
        if(!tbox_nombre.getText().isEmpty() 
             && !tbox_descripcion.getText().isEmpty() && !tbox_precioU.getText().isEmpty() 
                && !tbox_precio.getText().isEmpty()   && 
                !tbox_precioU.getText().isEmpty()   && 
                !cbox_categoria.getSelectionModel().isEmpty() && 
                !cbox_estado.getSelectionModel().isEmpty()
                ){ 

        try{
         conn= Main.conn.getConnection();
         sentencia = conn.prepareStatement("UPDATE platillosbebidas SET nombre_pb=?, descripcion_pb=?, precio_unitario_pb=?, precio_pb=?,categoria_pb=?,estado_pb=? WHERE id_pb=?");
         sentencia.setString(1, tbox_nombre.getText());
         sentencia.setString(2, tbox_descripcion.getText());
         sentencia.setFloat(3, Float.parseFloat(tbox_precioU.getText()));
         sentencia.setFloat(4, Float.parseFloat(tbox_precio.getText()));
         sentencia.setInt(5, cbox_categoria.getSelectionModel().getSelectedItem().getId());
         sentencia.setInt(6, cbox_estado.getSelectionModel().getSelectedItem().getId());
         sentencia.setInt(7, pb);
         sentencia.executeUpdate();
         cerrar(event);
         alerta = new Alert(Alert.AlertType.INFORMATION);
         alerta.setHeaderText("Exito");
         alerta.setContentText("Se actualizo la información correctamente");
         alerta.initOwner(Main.stage);
         alerta.showAndWait();
        } catch (SQLException  ex) {
            System.out.println(ex);
            
        }
        
        finally{
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
    
    
    
     public void setPBInformacion(PlatillosBebidas pbInfomarcion) {
        this.pb = pbInfomarcion.getClave();
        this.tbox_nombre.setText(pbInfomarcion.getNombre());
        this.tbox_descripcion.setText( pbInfomarcion.getDescripcion());
        this.tbox_precioU.setText(Float.toString( pbInfomarcion.getPrecioUnitario() ));
        this.tbox_precio.setText(Float.toString( pbInfomarcion.getPrecio() ));
        
        
        ListIterator i =  cbox_estado.getItems().listIterator() ;
        while(i.hasNext()){
           if(i.next().toString().equals( pbInfomarcion.getEstado())){
              System.out.println("cacth the moment");
              this.cbox_estado.getSelectionModel().select(i.nextIndex()-1);
              
           }
        }
        
        i =  cbox_categoria.getItems().listIterator() ;
        while(i.hasNext()){
           if(i.next().toString().equals(pbInfomarcion.getCategoria())){
              System.out.println("cacth the moment");
              this.cbox_categoria.getSelectionModel().select(i.nextIndex()-1);
              
           }
        }
        
        
        
        
    }
    
    
    
    private void actualizarCategorias(){
       categoriaLista.clear();
       Connection conn=null;
       PreparedStatement sentencia=null;
       ResultSet resultado = null;
       try {
        conn = Main.conn.getConnection();
        sentencia = conn.prepareStatement("SELECT * FROM categoriapb");
        resultado = sentencia.executeQuery();
        while(resultado.next()){
            categoriaLista.add(new Categoria(resultado.getInt("id_categoriapb"),resultado.getString("nombre_cpb")));
        }
    }   catch (SQLException ex) {
            Logger.getLogger(nuevo_empleado_control.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
          try{ if(conn!=null) sentencia.close();} catch(Exception e){};
          try{ if(conn!=null) conn.close();} catch(Exception e){};
        }
          
    }
    
    private void actualizarEstados(){
       estadoLista.clear();
       Connection conn=null;
       PreparedStatement sentencia=null;
       ResultSet resultado = null;
       try {
        conn = Main.conn.getConnection();
        sentencia = conn.prepareStatement("SELECT * from estados_platillobebida");
        resultado = sentencia.executeQuery();
        while(resultado.next()){
            estadoLista.add(new Estado(resultado.getInt("id_estado_epb"),resultado.getString("estado_epb")));
        }
    }   catch (SQLException ex) {
            Logger.getLogger(nuevo_empleado_control.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
          try{ if(conn!=null) sentencia.close();} catch(Exception e){};
          try{ if(conn!=null) conn.close();} catch(Exception e){};
        }
          
    }
    
    private void cerrar(MouseEvent event){
         Node nodo = (Node) event.getSource();
         Stage escenario = (Stage) nodo.getScene().getWindow() ;
         escenario.close();
    }
    
}
