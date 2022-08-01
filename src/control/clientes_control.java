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
import modelo.Cliente;
import modelo.Exportar;
import modelo.Proveedor;

/**
 * FXML Controller class
 *
 * @author Alanprogrammer
 */
public class clientes_control implements Initializable {

    @FXML
    private TableView<Cliente> tabla_cliente;
    @FXML
    private JFXButton bt_nuevo_cliente;
    @FXML
    private JFXButton bt_editar_cliente;
    @FXML
    private JFXButton bt_eliminar_cliente;
    @FXML
    private JFXTextField tbox_buscar;
    @FXML
    private TableColumn<?, ?> col_nombre;
    @FXML
    private TableColumn<?, ?> col_correo;
    @FXML
    private JFXButton bt_refrescar;
    @FXML
    private JFXButton bt_exportar;
    @FXML
    private TableColumn<?, ?> col_cliente;
    @FXML
    private TableColumn<?, ?> col_rfc;
    @FXML
    private TableColumn<?, ?> col_apellidoP;
    @FXML
    private TableColumn<?, ?> col_apellidoM;
    @FXML
    private TableColumn<?, ?> col_direccion;
    @FXML
    private TableColumn<?, ?> col_cp;
    @FXML
    private TableColumn<?, ?> col_celular;
    
    private ObservableList<Cliente> clienteLista = FXCollections.observableArrayList();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        bt_editar_cliente.setDisable(true);
        bt_eliminar_cliente.setDisable(true);
        col_cliente.setCellValueFactory(new PropertyValueFactory<>("idCliente"));
        col_rfc.setCellValueFactory(new PropertyValueFactory<>("rfc"));
        col_nombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        col_apellidoP.setCellValueFactory(new PropertyValueFactory<>("apellidoP"));
        col_apellidoM.setCellValueFactory(new PropertyValueFactory<>("apellidoM"));
        col_direccion.setCellValueFactory(new PropertyValueFactory<>("direccion"));
        col_cp.setCellValueFactory(new PropertyValueFactory<>("cp"));
        col_correo.setCellValueFactory(new PropertyValueFactory<>("correo"));
        col_celular.setCellValueFactory(new PropertyValueFactory<>("celular"));

        FilteredList<Cliente> filtroCliente = new FilteredList<>(clienteLista, p -> true);

        tabla_cliente.setItems(filtroCliente);
        tbox_buscar.textProperty().addListener((o, anterior, nuevo) -> {
            filtroCliente.setPredicate(Cliente -> {
                if (nuevo == null || nuevo.isEmpty()) {
                    return true;
                }
                  if(
                    Integer.toString(Cliente.getIdCliente()).contains(nuevo)    ||
                    Cliente.getRfc().toLowerCase().contains(nuevo.toLowerCase()) ||
                    Cliente.getNombre().toLowerCase().contains(nuevo.toLowerCase()) ||
                    Cliente.getApellidoP().toLowerCase().contains(nuevo.toLowerCase()) ||
                    Cliente.getApellidoM().toLowerCase().contains(nuevo.toLowerCase()) ||
                    Cliente.getDireccion().toLowerCase().contains(nuevo.toLowerCase()) ||
                    Cliente.getCp().toLowerCase().contains(nuevo.toLowerCase()) ||
                    Cliente.getCorreo().toLowerCase().contains(nuevo.toLowerCase()) ||
                    Cliente.getCelular().toLowerCase().contains(nuevo.toLowerCase()) 
                      
                            )
                {
                  return true;
                }

                return false;
            });

        });
        
        actualizarTabla();
        
        tabla_cliente.getSelectionModel().selectedItemProperty().addListener((o, anterior, nuevo) -> {
        bt_editar_cliente.setDisable(false);
        bt_eliminar_cliente.setDisable(false);
        });
    }    

    @FXML
    private void nuevoCliente(MouseEvent event) {
           try {
            Stage escenario = new Stage();

            Parent dialogo = FXMLLoader.load(getClass().getResource("/vista/nuevo_cliente_vista.fxml"));
            //JFXDecorator decorador = new JFXDecorator(escenario,dialogo);

            Scene escena = new Scene(dialogo);
            
            escenario.setScene(escena);
            escenario.setTitle("Nuevo Cliente");
            escenario.initModality(Modality.APPLICATION_MODAL);
            escenario.initStyle(StageStyle.UTILITY);
            escenario.initOwner(Main.stage);
            escenario.setResizable(false);

            escenario.showAndWait();

        } catch (IOException ex) {
            Logger.getLogger(empleados_control.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        actualizarTabla();
    
    }

    @FXML
    private void editarCliente(MouseEvent event) {
      try {
            Stage escenario = new Stage();
            FXMLLoader fxml = new  FXMLLoader(getClass().getResource("/vista/editar_cliente_vista.fxml"));
            Parent dialogo = fxml.load();
            editar_cliente_control controlador = fxml.<editar_cliente_control>getController();
            
            Cliente cliente = tabla_cliente.getSelectionModel().getSelectedItem();
            
            controlador.setClienteInformacion(cliente);
            
            
            Scene escena = new Scene(dialogo);
            escenario.initOwner(Main.stage);
            escenario.setScene(escena);
            escenario.setTitle("Editar empleado");
            escenario.initModality(Modality.APPLICATION_MODAL);
            escenario.initStyle(StageStyle.UTILITY);
            escenario.initOwner(Main.stage);
            escenario.setResizable(false);
            escenario.showAndWait();
        
        } catch (IOException ex) {
            Logger.getLogger(empleados_control.class.getName()).log(Level.SEVERE, null, ex);
        }
        actualizarTabla();
        bt_editar_cliente.setDisable(true);
        bt_eliminar_cliente.setDisable(true);
    
    
    }

    @FXML
    private void eliminarCliente(MouseEvent event) {
      Connection conn = null;
        PreparedStatement sentencia=null;
        ResultSet resultado = null;
        try {
            conn = Main.conn.getConnection();
            sentencia = conn.prepareStatement("DELETE FROM clientes WHERE id_cliente=?");
            sentencia.setInt(1,tabla_cliente.getSelectionModel().getSelectedItem().getIdCliente());
            sentencia.executeUpdate();
          
        } catch (SQLException ex) {
            Logger.getLogger(empleados_control.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            try{ if(conn!=null) conn.close();} catch(Exception e){};     
            try{ if(resultado!=null) resultado.close();} catch(Exception e){};
            try{ if(sentencia!=null) sentencia.close();} catch(Exception e){};
        }
        
        
        actualizarTabla();
        bt_editar_cliente.setDisable(true);
        bt_eliminar_cliente.setDisable(true);
    
    
    }

    @FXML
    private void refrescar(MouseEvent event) {
        actualizarTabla();
    }

    @FXML
    private void exportar(MouseEvent event) {
        Exportar e =  new Exportar();
        e.export("clientes");
    }
    
    private void actualizarTabla() {
        clienteLista.clear();
        Connection conn =null;
        PreparedStatement sentencia=null;
        ResultSet resultado= null;
        try {
            conn = Main.conn.getConnection();
            sentencia = conn.prepareStatement("SELECT * FROM clientes");
            resultado = sentencia.executeQuery();
           
            while(resultado.next()){
            clienteLista.add(new Cliente(resultado.getInt("id_cliente"),resultado.getString("rfc_cl"),resultado.getString("nombres_cl"),resultado.getString("apellidop_cl"),resultado.getString("apellidom_cl"),resultado.getString("direccion_cl"),resultado.getString("cp_cl"),resultado.getString("correo_cl"),resultado.getString("celular_cl")));
            
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
