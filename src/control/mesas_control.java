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
import modelo.Empleado;
import modelo.Estado;
import modelo.Exportar;
import modelo.Mesa;

/**
 * FXML Controller class
 *
 * @author Alanprogrammer
 */
public class mesas_control implements Initializable {
    
    @FXML
    private JFXButton bt_nuevo_mesa;
    @FXML
    private JFXButton bt_editar_mesa;
    @FXML
    private JFXButton bt_eliminar_mesa;
    @FXML
    private JFXTextField tbox_buscar;
    @FXML
    private TableView<Mesa> tabla_mesa;
    @FXML
    private TableColumn<?, ?> col_mesa;
    @FXML
    private TableColumn<?, ?> col_estado;
    @FXML
    private TableColumn<?, ?> col_ubicacion;
    @FXML
    private TableColumn<?, ?> col_personas;
    @FXML
    private JFXButton bt_refrescar;
    @FXML
    private JFXButton bt_exportar;
    
    private ObservableList<Mesa> mesaLista = FXCollections.observableArrayList();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        bt_editar_mesa.setDisable(true);
        bt_eliminar_mesa.setDisable(true);
        
        col_mesa.setCellValueFactory(new PropertyValueFactory<>("numMesa"));
        col_personas.setCellValueFactory(new PropertyValueFactory<>("personas"));
        col_ubicacion.setCellValueFactory(new PropertyValueFactory<>("ubicacion"));
        col_estado.setCellValueFactory(new PropertyValueFactory<>("estado"));
        FilteredList<Mesa> filtroMesa = new FilteredList<>(mesaLista, p -> true);
        tabla_mesa.setItems(filtroMesa);
        tbox_buscar.textProperty().addListener((o, anterior, nuevo) -> {
            filtroMesa.setPredicate(Mesa -> {
                if (nuevo == null || nuevo.isEmpty()) {
                    return true;
                }
                
                if(
                        
                    Integer.toString(Mesa.getPersonas()).contains(nuevo)  ||
                    Mesa.getUbicacion().toLowerCase().contains(nuevo.toLowerCase())  ||
                    Mesa.getEstado().toLowerCase().contains(nuevo.toLowerCase())  ||
                    Integer.toString(Mesa.getNumMesa()).contains(nuevo)
                        )
                {
                  return true;
                }
                
                return false;
            });
            
        });
        
        actualizarTabla();
        
        tabla_mesa.getSelectionModel().selectedItemProperty().addListener((o, anterior, nuevo) -> {
           bt_editar_mesa.setDisable(false);
           bt_eliminar_mesa.setDisable(false);
        });
        
    }    
    
    @FXML
    private void nuevoMesa(MouseEvent event) {
         try {
            Stage escenario = new Stage();

            Parent dialogo = FXMLLoader.load(getClass().getResource("/vista/nuevo_mesa_vista.fxml"));
            //JFXDecorator decorador = new JFXDecorator(escenario,dialogo);

            Scene escena = new Scene(dialogo);
            escenario.initOwner(Main.stage);
            escenario.setScene(escena);
            escenario.setTitle("Nuevo Mesa");
            escenario.initModality(Modality.APPLICATION_MODAL);
            escenario.initStyle(StageStyle.UTILITY);

            escenario.setResizable(false);

            escenario.showAndWait();

        } catch (IOException ex) {
            Logger.getLogger(empleados_control.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        actualizarTabla();
        bt_editar_mesa.setDisable(true);
        bt_eliminar_mesa.setDisable(true);
    
    }
    
    @FXML
    private void editarMesa(MouseEvent event) {
        try {
            Stage escenario = new Stage();
            FXMLLoader fxml = new  FXMLLoader(getClass().getResource("/vista/editar_mesa_vista.fxml"));
            Parent dialogo = fxml.load();
            
            editar_mesa_control controlador = fxml.<editar_mesa_control>getController();
            
            Mesa mesa = tabla_mesa.getSelectionModel().getSelectedItem();
            
            controlador.setMesaInformacion(mesa);
            
            
            
            Scene escena = new Scene(dialogo);
            escenario.initOwner(Main.stage);
            escenario.setScene(escena);
            escenario.setTitle("Editar Mesa");
            escenario.initModality(Modality.APPLICATION_MODAL);
            escenario.initStyle(StageStyle.UTILITY);
            escenario.setResizable(true);
            escenario.showAndWait();
        
        } catch (IOException ex) {
            Logger.getLogger(empleados_control.class.getName()).log(Level.SEVERE, null, ex);
        }
        actualizarTabla();
        bt_editar_mesa.setDisable(true);
        bt_eliminar_mesa.setDisable(true);
    }
    
    @FXML
    private void eliminarMesa(MouseEvent event) {
        Connection conn = null;
        PreparedStatement sentencia=null;
        ResultSet resultado = null;
        try {
            conn = Main.conn.getConnection();
            sentencia = conn.prepareStatement("DELETE FROM mesas WHERE num_mesa=?");
            sentencia.setInt(1,tabla_mesa.getSelectionModel().getSelectedItem().getNumMesa());
            sentencia.executeUpdate();
          
        } catch (SQLException ex) {
            Logger.getLogger(empleados_control.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            try{ if(conn!=null) conn.close();} catch(Exception e){};     
            try{ if(resultado!=null) resultado.close();} catch(Exception e){};
            try{ if(sentencia!=null) sentencia.close();} catch(Exception e){};
        }
        
        actualizarTabla();
        bt_editar_mesa.setDisable(true);
        bt_eliminar_mesa.setDisable(true);
    }
    
    @FXML
    private void refrescar(MouseEvent event) {
        actualizarTabla();
    }

    @FXML
    private void exportar(MouseEvent event) {
        Exportar e =  new Exportar();
        e.export("mesas");
    }
    
    private void actualizarTabla() {
        mesaLista.clear();
        Connection conn = null;
        PreparedStatement sentencia = null;
        ResultSet resultado = null;
        try {
            conn = Main.conn.getConnection();
            sentencia = conn.prepareStatement("SELECT num_mesa,ubicacion,personas_m,estado_m,em.estado_em FROM mesas m  LEFT JOIN estados_mesa em ON m.estado_m=em.id_estado_em;");
            resultado = sentencia.executeQuery();
            
            while (resultado.next()) {
                mesaLista.add(new Mesa(
                   resultado.getInt("num_mesa"),
                   resultado.getString("ubicacion"),
                   resultado.getInt("personas_m"),
                   resultado.getInt("estado_m"), 
                   resultado.getString("em.estado_em")
                ));
            }
        } catch (SQLException ex) {
            Logger.getLogger(empleados_control.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (Exception e) {
            };            
            try {
                if (resultado != null) {
                    resultado.close();
                }
            } catch (Exception e) {
            };
            try {
                if (sentencia != null) {
                    sentencia.close();
                }
            } catch (Exception e) {
            };
            
        }
        
    }
    
}
