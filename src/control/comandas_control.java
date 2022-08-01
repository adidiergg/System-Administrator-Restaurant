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
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.Date;
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
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import modelo.Comanda;
import modelo.Exportar;

/**
 * FXML Controller class
 *
 * @author Alanprogrammer
 */
public class comandas_control implements Initializable {

    @FXML
    private JFXButton bt_nuevo_cmd;
    @FXML
    private JFXButton bt_abrir_cmd;
    @FXML
    private JFXButton bt_cancelar_cmd;
    @FXML
    private JFXTextField tbox_buscar;
    @FXML
    private TableView<Comanda> tabla_cmd;
    @FXML
    private TableColumn<?, ?> col_cmd;
    @FXML
    private JFXButton bt_refrescar;
    @FXML
    private JFXButton bt_exportar;
    @FXML
    private TableColumn<?, ?> col_fecha;
    @FXML
    private TableColumn<?, ?> col_hora;
    @FXML
    private TableColumn<?, ?> col_mesa;
    @FXML
    private TableColumn<?, ?> col_camarero;
    @FXML
    private TableColumn<?, ?> col_personas;
    @FXML
    private TableColumn<?, ?> col_cliente;
    private TableColumn<?, ?> col_estado;
    
    private ObservableList<Comanda> cmdLista = FXCollections.observableArrayList();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
         // TODO
        bt_abrir_cmd.setDisable(true);
        bt_cancelar_cmd.setDisable(true);
        
        col_cmd.setCellValueFactory(new PropertyValueFactory<>("num"));
        col_fecha.setCellValueFactory(new PropertyValueFactory<>("fecha"));
        col_hora.setCellValueFactory(new PropertyValueFactory<>("hora"));
        col_mesa.setCellValueFactory(new PropertyValueFactory<>("codMesa"));
        col_camarero.setCellValueFactory(new PropertyValueFactory<>("camarero"));
        col_personas.setCellValueFactory(new PropertyValueFactory<>("comensales"));
        col_cliente.setCellValueFactory(new PropertyValueFactory<>("cliente"));
    
        FilteredList<Comanda> filtroCmd = new FilteredList<>(cmdLista, p -> true);
        tabla_cmd.setItems(filtroCmd);
        tbox_buscar.textProperty().addListener((o, anterior, nuevo) -> {
            filtroCmd.setPredicate(Comanda -> {
                if (nuevo == null || nuevo.isEmpty()) {
                    return true;
                }
                
                if(
                        
                    
                    Comanda.getCliente().toLowerCase().contains(nuevo.toLowerCase())  ||
                    Comanda.getCamarero().toLowerCase().contains(nuevo.toLowerCase())  ||
                    fechaTexto(Comanda.getFecha()).contains(nuevo) ||
                    horaTexto(Comanda.getHora()).contains(nuevo) ||
                    Integer.toString(Comanda.getComensales()).contains(nuevo) || 
                    Integer.toString(Comanda.getCodMesa()).contains(nuevo) ||
                    Integer.toString(Comanda.getCodCliente()).contains(nuevo)
                        )
                {
                  return true;
                }
                
                return false;
            });
            
        });
        
        tabla_cmd.getSelectionModel().selectedItemProperty().addListener((o, anterior, nuevo) -> {
        bt_abrir_cmd.setDisable(false);
        bt_cancelar_cmd.setDisable(false);
        });
        
        actualizarTabla();
        
    }    

    @FXML
    private void nuevoCMD(MouseEvent event) {
        try {
            Stage escenario = new Stage();

            Parent dialogo = FXMLLoader.load(getClass().getResource("/vista/nuevo_comanda_vista.fxml"));
            //JFXDecorator decorador = new JFXDecorator(escenario,dialogo);

            Scene escena = new Scene(dialogo);
            escenario.setScene(escena);
            escenario.setTitle("Nueva comanda");
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
    private void abrirCMD(MouseEvent event) {
        try {
            Stage escenario = new Stage();
            FXMLLoader fxml = new  FXMLLoader(getClass().getResource("/vista/abrir_comanda_vista.fxml"));
            Parent dialogo = fxml.load();
            
            abrir_comanda_control controlador = fxml.<abrir_comanda_control>getController();
            
            Comanda cmd = tabla_cmd.getSelectionModel().getSelectedItem();
            
            controlador.setCMDInformacion(cmd);
            
            
            
            Scene escena = new Scene(dialogo);
            escenario.setScene(escena);
            escenario.setTitle("Abrir comanda");
            escenario.initOwner(Main.stage);
            escenario.initModality(Modality.APPLICATION_MODAL);
            escenario.initStyle(StageStyle.UTILITY);
            escenario.setResizable(false);
            escenario.setMaximized(true);
            escenario.showAndWait();
        
        } catch (IOException ex) {
            Logger.getLogger(empleados_control.class.getName()).log(Level.SEVERE, null, ex);
        }
        actualizarTabla();
        bt_abrir_cmd.setDisable(true);
        bt_cancelar_cmd.setDisable(true);
    }


    @FXML
    private void refrescar(MouseEvent event) {
        actualizarTabla();
    }

    @FXML
    private void exportar(MouseEvent event) {
        Exportar e =  new Exportar();
        e.export("comandas");
    }
    
    private void actualizarTabla() {
        cmdLista.clear();
        Connection conn = null;
        PreparedStatement sentencia = null;
        ResultSet resultado = null;
        try {
            conn = Main.conn.getConnection();
            sentencia = conn.prepareStatement("SELECT num_comanda,fecha_c,hora_c,estado_c,camarero_c,cliente,num_comensales,mesa_c,CONCAT(e.nombres_e,' ',e.apellido_pe,' ',e.apellido_me) AS nombrecamarero,ec.estado_ec,CONCAT(nombres_cl,' ',apellidop_cl,' ',apellidom_cl) AS nombrecliente  FROM comandas c LEFT JOIN empleados e ON c.camarero_c=e.codigo_empleado LEFT JOIN estados_comanda ec ON c.estado_c=ec.id_estado_ec LEFT JOIN clientes cl ON c.cliente=cl.id_cliente WHERE estado_c=1");
            resultado = sentencia.executeQuery();
            
            while (resultado.next()) {
                
                cmdLista.add( new Comanda(
                   resultado.getInt("num_comanda"),
                   resultado.getDate("fecha_c"),
                   resultado.getTime("hora_c"),
                        
                   resultado.getString("estado_ec"),
                   resultado.getInt("estado_c"),
                        
                   resultado.getInt("camarero_c"),
                   resultado.getString("nombrecamarero"),
                    
                   resultado.getInt("cliente"),
                   resultado.getString("nombrecliente"),
                   resultado.getInt("num_comensales"),
                   resultado.getInt("mesa_c")
                   
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

    @FXML
    private void cancelarCMD(MouseEvent event) {
        Alert alerta; 
        Connection conn=null;
        PreparedStatement sentencia=null;

        try{
         conn= Main.conn.getConnection();
         
         sentencia = conn.prepareStatement("UPDATE comandas SET estado_c=2 WHERE num_comanda=?");
         sentencia.setInt(1, tabla_cmd.getSelectionModel().getSelectedItem().getNum());
         sentencia.executeUpdate();
         sentencia = conn.prepareStatement("UPDATE mesas SET  estado_m=2 WHERE num_mesa=?");
         sentencia.setInt(1,  tabla_cmd.getSelectionModel().getSelectedItem().getCodMesa());
         sentencia.executeQuery();
         
         alerta = new Alert(Alert.AlertType.INFORMATION);
         alerta.initOwner(Main.stage);
         alerta.setHeaderText("Exito");
         alerta.setContentText("Cancelación existosa");
         alerta.showAndWait();
         
         
        } catch (SQLException ex) {
            System.out.println(ex);
            
        }finally{
          try{ if(conn!=null) sentencia.close();} catch(Exception e){};
          try{ if(conn!=null) conn.close();} catch(Exception e){};
        }
        
        actualizarTabla();
        bt_abrir_cmd.setDisable(true);
        bt_cancelar_cmd.setDisable(true);
    }
    
    
     private String fechaTexto(Date fecha){
       return new SimpleDateFormat("yyyy-MM-dd").format(fecha);
    }
    
    private String horaTexto(Time hora){
       return new SimpleDateFormat("HH:mm:ss").format(hora);
    }
    
}
