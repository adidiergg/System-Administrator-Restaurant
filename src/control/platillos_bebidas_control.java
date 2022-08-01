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
import javafx.scene.Node;
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
import modelo.Exportar;
import modelo.PlatillosBebidas;

/**
 * FXML Controller class
 *
 * @author Alanprogrammer
 */
public class platillos_bebidas_control implements Initializable {

    @FXML
    private JFXButton bt_nuevo_pb;
    @FXML
    private JFXButton bt_editar_pb;
    @FXML
    private JFXButton bt_eliminar_pb;
    @FXML
    private JFXTextField tbox_buscar;
    @FXML
    private TableView<PlatillosBebidas> tabla_pb;
    @FXML
    private TableColumn<?, ?> col_pb;
    @FXML
    private TableColumn<?, ?> col_nombre;
    @FXML
    private TableColumn<?, ?> col_descripcion;
    @FXML
    private TableColumn<?, ?> col_precioU;
    @FXML
    private TableColumn<?, ?> col_precio;
    @FXML
    private TableColumn<?, ?> col_estado;
    @FXML
    private JFXButton bt_refrescar;
    @FXML
    private JFXButton bt_exportar;
    
    private ObservableList<PlatillosBebidas> pbLista = FXCollections.observableArrayList();
    @FXML
    private TableColumn<?, ?> col_categoria;
        

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        bt_editar_pb.setDisable(true);
        bt_eliminar_pb.setDisable(true);
        
        col_pb.setCellValueFactory(new PropertyValueFactory<>("clave"));
        col_nombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        col_descripcion.setCellValueFactory(new PropertyValueFactory<>("descripcion"));
        col_precioU.setCellValueFactory(new PropertyValueFactory<>("precioUnitario"));
        col_precio.setCellValueFactory(new PropertyValueFactory<>("precio"));
        //col_categoria.setCellValueFactory(new PropertyValueFactory<>("categoria"));
        col_estado.setCellValueFactory(new PropertyValueFactory<>("estado"));
        
        FilteredList<PlatillosBebidas> filtropb = new FilteredList<>(pbLista, p -> true);
        tabla_pb.setItems(filtropb);
        tbox_buscar.textProperty().addListener((o, anterior, nuevo) -> {
            filtropb.setPredicate(PlatillosBebidas -> {
                if (nuevo == null || nuevo.isEmpty()) {
                    return true;
                }
                
                if(
                    Integer.toString(PlatillosBebidas.getClave()).contains(nuevo)    ||
                    PlatillosBebidas.getNombre().toLowerCase().contains(nuevo.toLowerCase())  ||
                    PlatillosBebidas.getDescripcion().toLowerCase().contains(nuevo.toLowerCase())  ||
                    PlatillosBebidas.getEstado().toLowerCase().contains(nuevo.toLowerCase())  ||
                    PlatillosBebidas.getCategoria().toLowerCase().contains(nuevo.toLowerCase())  ||
                    Float.toString(PlatillosBebidas.getPrecioUnitario()).contains(nuevo)  ||
                    Float.toString(PlatillosBebidas.getPrecio()).contains(nuevo) 
                            )
                {
                  return true;
                }
                
                
                
                return false;
            });
            
        });
        
        actualizarTabla();
        
        tabla_pb.getSelectionModel().selectedItemProperty().addListener((o, anterior, nuevo) -> {
           bt_editar_pb.setDisable(false);
           bt_eliminar_pb.setDisable(false);
        });
    
    
    }    

    @FXML
    private void nuevoPlatilloBebida(MouseEvent event) {
         try {
            Stage escenario = new Stage();

            Parent dialogo = FXMLLoader.load(getClass().getResource("/vista/nuevo_platillos_bebidas_vista.fxml"));
            //JFXDecorator decorador = new JFXDecorator(escenario,dialogo);

            Scene escena = new Scene(dialogo);
            escenario.setScene(escena);
            escenario.setTitle("Nuevo Platillo o Bebida");
            escenario.initModality(Modality.APPLICATION_MODAL);
            escenario.initStyle(StageStyle.UTILITY);

            escenario.initOwner(Main.stage);
            escenario.setResizable(false);

            escenario.showAndWait();

        } catch (IOException ex) {
            Logger.getLogger(empleados_control.class.getName()).log(Level.SEVERE, null, ex);
        }
         
        actualizarTabla();
        bt_editar_pb.setDisable(false);
        bt_eliminar_pb.setDisable(false);
    
    }

    @FXML
    private void editarPlatilloBebida(MouseEvent event) {
          try {
            Stage escenario = new Stage();
            FXMLLoader fxml = new  FXMLLoader(getClass().getResource("/vista/editar_platillos_bebidas_control.fxml"));
            Parent dialogo = fxml.load();
            
            editar_platillos_bebidas_control controlador = fxml.<editar_platillos_bebidas_control>getController();
            
            PlatillosBebidas pb = tabla_pb.getSelectionModel().getSelectedItem();
            
            controlador.setPBInformacion(pb);
            
            
            
            Scene escena = new Scene(dialogo);
            escenario.setScene(escena);
            escenario.setTitle("Nuevo Platillo o Bebida");
            escenario.initModality(Modality.APPLICATION_MODAL);
            escenario.initStyle(StageStyle.UTILITY);
            escenario.initOwner(Main.stage);
            escenario.setResizable(false);
            escenario.showAndWait();
        
        } catch (IOException ex) {
            Logger.getLogger(empleados_control.class.getName()).log(Level.SEVERE, null, ex);
        }
        actualizarTabla();
        bt_editar_pb.setDisable(true);
        bt_eliminar_pb.setDisable(true);
    
    }

    @FXML
    private void eliminarPlatilloBebida(MouseEvent event) {
      Connection conn = null;
        PreparedStatement sentencia=null;
        ResultSet resultado = null;
        try {
            conn = Main.conn.getConnection();
            sentencia = conn.prepareStatement("DELETE FROM platillosbebidas WHERE id_pb=?");
            sentencia.setInt(1,tabla_pb.getSelectionModel().getSelectedItem().getClave());
            sentencia.executeUpdate();
          
        } catch (SQLException ex) {
            Logger.getLogger(empleados_control.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            try{ if(conn!=null) conn.close();} catch(Exception e){};     
            try{ if(resultado!=null) resultado.close();} catch(Exception e){};
            try{ if(sentencia!=null) sentencia.close();} catch(Exception e){};
        }
        
        actualizarTabla();
        
        bt_editar_pb.setDisable(true);
        bt_eliminar_pb.setDisable(true);
    }

    @FXML
    private void refrescar(MouseEvent event) {
        actualizarTabla();
    }

    @FXML
    private void exportar(MouseEvent event) {
        Exportar e =  new Exportar();
        e.export("platillosbebidas");
    }
    
    private void actualizarTabla() {
        pbLista.clear();
        Connection conn = null;
        PreparedStatement sentencia = null;
        ResultSet resultado = null;
        try {
            conn = Main.conn.getConnection();
            sentencia = conn.prepareStatement("SELECT id_pb,nombre_pb,descripcion_pb,precio_unitario_pb,precio_pb,nombre_cpb,estado_epb FROM platillosbebidas pb LEFT JOIN categoriapb c ON pb.categoria_pb=c.id_categoriapb LEFT JOIN estados_platillobebida epb ON pb.estado_pb = epb.id_estado_epb");
            resultado = sentencia.executeQuery();
            
            while (resultado.next()) {
                pbLista.add(new PlatillosBebidas(
                  resultado.getInt("id_pb"),
                  resultado.getString("nombre_pb"),
                  resultado.getString("descripcion_pb"),
                  resultado.getFloat("precio_unitario_pb"),
                  resultado.getFloat("precio_pb"),
                  resultado.getString("estado_epb"),
                  resultado.getString("nombre_cpb")
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
