/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import java.io.IOException;
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
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import modelo.Empleado;
import modelo.Exportar;
import modelo.Proveedor;

/**
 * FXML Controller class
 *
 * @author Alanprogrammer
 */
public class proveedores_control implements Initializable {

    @FXML
    private JFXButton bt_nuevo_proveedor;
    @FXML
    private JFXButton bt_editar_proveedor;
    @FXML
    private JFXButton bt_eliminar_proveedor;
    @FXML
    private JFXTextField tbox_buscar;
    @FXML
    private TableView<Proveedor> tabla_proveedor;
    @FXML
    private TableColumn<?, ?> col_proveedor;
    @FXML
    private TableColumn<?, ?> col_nombre;
    @FXML
    private TableColumn<?, ?> col_telefono;
    @FXML
    private TableColumn<?, ?> col_correo;
    @FXML
    private JFXButton bt_refrescar;
    @FXML
    private JFXButton bt_exportar;
    private ObservableList<Proveedor> proveedorLista = FXCollections.observableArrayList();
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        bt_editar_proveedor.setDisable(true);
        bt_eliminar_proveedor.setDisable(true);
        col_proveedor.setCellValueFactory(new PropertyValueFactory<>("clave"));
        col_nombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        col_telefono.setCellValueFactory(new PropertyValueFactory<>("telefono"));
        col_correo.setCellValueFactory(new PropertyValueFactory<>("correo"));
        
        FilteredList<Proveedor> filtroProveedor = new FilteredList<>(proveedorLista, p -> true);

        tabla_proveedor.setItems(filtroProveedor);
        tbox_buscar.textProperty().addListener((o, anterior, nuevo) -> {
            filtroProveedor.setPredicate(Proveedor -> {
                if (nuevo == null || nuevo.isEmpty()) {
                    return true;
                }
                if(
                        
                    Proveedor.getNombre().toLowerCase().contains(nuevo.toLowerCase())  ||
                    Proveedor.getTelefono().toLowerCase().contains(nuevo.toLowerCase())  ||
                    Proveedor.getCorreo().toLowerCase().contains(nuevo.toLowerCase())  ||
                    Integer.toString(Proveedor.getClave()).contains(nuevo)
                        )
                {
                  return true;
                }
                

                return false;
            });

        });
        actualizarTabla();
        
        tabla_proveedor.getSelectionModel().selectedItemProperty().addListener((o, anterior, nuevo) -> {
              bt_editar_proveedor.setDisable(false);
              bt_eliminar_proveedor.setDisable(false);
        });
    }    

    @FXML
    private void nuevoProveedor(MouseEvent event) {
        try {
            Stage escenario = new Stage();

            Parent dialogo = FXMLLoader.load(getClass().getResource("/vista/nuevo_proveedor_vista.fxml"));
            //JFXDecorator decorador = new JFXDecorator(escenario,dialogo);

            Scene escena = new Scene(dialogo);
            escenario.setScene(escena);
            escenario.setTitle("Nuevo Proveedor");
            escenario.initModality(Modality.APPLICATION_MODAL);
            escenario.initStyle(StageStyle.UTILITY);
      
            escenario.initOwner(Main.stage);
            escenario.setResizable(false);

            escenario.showAndWait();

        } catch (IOException ex) {
            Logger.getLogger(empleados_control.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        actualizarTabla();
        bt_editar_proveedor.setDisable(true);
        bt_eliminar_proveedor.setDisable(true);
    
    }

    @FXML
    private void editarProveedor(MouseEvent event) {
          try {
            Stage escenario = new Stage();
            FXMLLoader fxml = new  FXMLLoader(getClass().getResource("/vista/editar_proveedor_vista.fxml"));
            Parent dialogo = fxml.load();
            editar_proveedor_control controlador = fxml.<editar_proveedor_control>getController();
            
            Proveedor proveedor = tabla_proveedor.getSelectionModel().getSelectedItem();
            
            controlador.setProveedorInformacion(proveedor);
            
            
            Scene escena = new Scene(dialogo);
            escenario.setScene(escena);
            escenario.setTitle("Editar Proveedor");
            escenario.initModality(Modality.APPLICATION_MODAL);
            escenario.initStyle(StageStyle.UTILITY);
            escenario.initOwner(Main.stage);
            escenario.setResizable(false);
            escenario.showAndWait();
        
        } catch (IOException ex) {
            Logger.getLogger(empleados_control.class.getName()).log(Level.SEVERE, null, ex);
        }
        actualizarTabla();
        bt_editar_proveedor.setDisable(true);
        bt_eliminar_proveedor.setDisable(true);
    
    
    }

    @FXML
    private void eliminarProveedor(MouseEvent event) {
       Connection conn = null;
        PreparedStatement sentencia=null;
        ResultSet resultado = null;
        try {
            conn = Main.conn.getConnection();
            sentencia = conn.prepareStatement("DELETE FROM proveedores WHERE id_proveedor=?");
            sentencia.setInt(1,tabla_proveedor.getSelectionModel().getSelectedItem().getClave());
            sentencia.executeUpdate();
          
        } catch (SQLException ex) {
            Logger.getLogger(empleados_control.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            try{ if(conn!=null) conn.close();} catch(Exception e){};     
            try{ if(resultado!=null) resultado.close();} catch(Exception e){};
            try{ if(sentencia!=null) sentencia.close();} catch(Exception e){};
        }
        
        actualizarTabla();
        bt_editar_proveedor.setDisable(true);
        bt_eliminar_proveedor.setDisable(true);
    
    
    }

    @FXML
    private void refrescar(MouseEvent event) {
        actualizarTabla();
    }

    @FXML
    private void exportar(MouseEvent event) {
        Exportar e =  new Exportar();
        e.export("proveedores");
    }
    
      private void actualizarTabla() {
        proveedorLista.clear();
        Connection conn =null;
        PreparedStatement sentencia=null;
        ResultSet resultado= null;
        try {
            conn = Main.conn.getConnection();
            sentencia = conn.prepareStatement("SELECT * FROM proveedores");
            resultado = sentencia.executeQuery();
           
            while(resultado.next()){
            proveedorLista.add(new Proveedor(resultado.getInt("id_proveedor"),resultado.getString("nombre_pr"),resultado.getString("telefono_pr"),resultado.getString("correo_pr")));
            
            }
        } catch (SQLException ex) {
            Logger.getLogger(empleados_control.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
             try{ if(conn!=null) conn.close();} catch(Exception e){};     
             try{ if(resultado!=null) resultado.close();} catch(Exception e){};
             try{ if(sentencia!=null) sentencia.close();} catch(Exception e){};
        
        }

        
    }
    
}
