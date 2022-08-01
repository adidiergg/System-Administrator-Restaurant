/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDecorator;
import com.jfoenix.controls.JFXTextField;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLNonTransientConnectionException;
import java.sql.Statement;
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
import javafx.scene.input.KeyCombination;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import modelo.Empleado;
import modelo.Exportar;

/**
 * FXML Controller class
 *
 * @author Alanprogrammer
 */
public class empleados_control implements Initializable {

    @FXML
    private JFXButton bt_nuevo_empleado;
    @FXML
    private TableView<Empleado> tabla_empleado;
    @FXML
    private TableColumn<?, ?> col_nombre;
    @FXML
    private TableColumn<?, ?> col_apellidoP;
    @FXML
    private TableColumn<?, ?> col_apellidoM;
    @FXML
    private TableColumn<?, ?> col_puesto;
    @FXML
    private TableColumn<?, ?> col_empleado;

    private ObservableList<Empleado> empleadoLista = FXCollections.observableArrayList();
    @FXML
    private JFXTextField tbox_buscar;
    @FXML
    private JFXButton bt_editar_empleado;
    @FXML
    private JFXButton bt_eliminar_empleado;
    @FXML
    private JFXButton bt_refrescar;
    @FXML
    private JFXButton bt_exportar;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        bt_editar_empleado.setDisable(true);
        bt_eliminar_empleado.setDisable(true);
        col_empleado.setCellValueFactory(new PropertyValueFactory<>("codigoEmpleado"));
        col_nombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        col_apellidoP.setCellValueFactory(new PropertyValueFactory<>("apellidoP"));
        col_apellidoM.setCellValueFactory(new PropertyValueFactory<>("apellidoM"));
        col_puesto.setCellValueFactory(new PropertyValueFactory<>("puesto"));

        FilteredList<Empleado> filtroEmpleado = new FilteredList<>(empleadoLista, p -> true);

        tabla_empleado.setItems(filtroEmpleado);
        
        tbox_buscar.textProperty().addListener((o, anterior, nuevo) -> {
            filtroEmpleado.setPredicate(Empleado -> {
                if (nuevo == null || nuevo.isEmpty()) {
                    return true;
                }
                if(
                        
                    Empleado.getNombre().toLowerCase().contains(nuevo.toLowerCase())  ||
                    Empleado.getPuesto().toLowerCase().contains(nuevo.toLowerCase())  ||
                    Empleado.getApellidoP().toLowerCase().contains(nuevo.toLowerCase())  ||
                    Empleado.getApellidoM().toLowerCase().contains(nuevo.toLowerCase())  ||
                    Integer.toString(Empleado.getCodigoEmpleado()).contains(nuevo)
                        )
                {
                  return true;
                }
               
                
                
                return false;
            });
            
        });
        actualizarTabla();
       
                
        tabla_empleado.getSelectionModel().selectedItemProperty().addListener((o, anterior, nuevo) -> {
           bt_editar_empleado.setDisable(false);
           bt_eliminar_empleado.setDisable(false);
        });

    }

    @FXML
    private void nuevoEmpleado(MouseEvent event) {
        try {
            Stage escenario = new Stage();

            Parent dialogo = FXMLLoader.load(getClass().getResource("/vista/nuevo_empleado_vista.fxml"));
            //JFXDecorator decorador = new JFXDecorator(escenario,dialogo);

            Scene escena = new Scene(dialogo);
            escenario.setScene(escena);
            escenario.setTitle("Nuevo Empleado");
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
    private void editarEmpleado(MouseEvent event) {
        
        
        try {
            Stage escenario = new Stage();
            FXMLLoader fxml = new  FXMLLoader(getClass().getResource("/vista/editar_empleado_vista.fxml"));
            Parent dialogo = fxml.load();
            editar_empleado_control controlador = fxml.<editar_empleado_control>getController();
            
            Empleado empleado = tabla_empleado.getSelectionModel().getSelectedItem();
            
            controlador.setEmpleadoInformacion(empleado);
            
            
            Scene escena = new Scene(dialogo);
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
        bt_editar_empleado.setDisable(true);
        bt_eliminar_empleado.setDisable(true);
           
           
        
       
    }

    @FXML
    private void eliminarEmpleado(MouseEvent event) {
        Connection conn = null;
        PreparedStatement sentencia=null;
        ResultSet resultado = null;
        try {
            conn = Main.conn.getConnection();
            sentencia = conn.prepareStatement("DELETE FROM empleados WHERE codigo_empleado=?");
            sentencia.setInt(1,tabla_empleado.getSelectionModel().getSelectedItem().getCodigoEmpleado());
            sentencia.executeUpdate();
          
        } catch (SQLException ex) {
            Logger.getLogger(empleados_control.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            try{ if(conn!=null) conn.close();} catch(Exception e){};     
            try{ if(resultado!=null) resultado.close();} catch(Exception e){};
            try{ if(sentencia!=null) sentencia.close();} catch(Exception e){};
        }
        
        actualizarTabla();
         bt_editar_empleado.setDisable(false);
           bt_eliminar_empleado.setDisable(false);
    }

    private void actualizarTabla() {
        empleadoLista.clear();
        Connection conn =null;
        PreparedStatement sentencia=null;
        ResultSet resultado= null;
        try {
            conn = Main.conn.getConnection();
            sentencia = conn.prepareStatement("SELECT e.codigo_empleado,e.nombres_e,e.apellido_pe,e.apellido_me,p.nombre_pu,e.password FROM empleados e INNER JOIN puestos p ON e.puesto_e=p.id_puesto");
            resultado = sentencia.executeQuery();
           
            while(resultado.next()){
            empleadoLista.add(new Empleado(resultado.getInt("codigo_empleado"),resultado.getString("nombres_e"),resultado.getString("apellido_pe"),resultado.getString("apellido_me"),resultado.getString("password"),resultado.getString("nombre_pu")));
            
            }
        } catch (SQLException ex) {
            Logger.getLogger(empleados_control.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
             try{ if(conn!=null) conn.close();} catch(Exception e){};     
             try{ if(resultado!=null) resultado.close();} catch(Exception e){};
             try{ if(sentencia!=null) sentencia.close();} catch(Exception e){};
        
        }

        
    }

    @FXML
    private void refrescar(MouseEvent event) {
        actualizarTabla();
    }

    @FXML
    private void exportar(MouseEvent event) {
        Exportar e =  new Exportar();
        e.export("empleados");
    }

}
