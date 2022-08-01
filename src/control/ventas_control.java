/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
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
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import modelo.Exportar;
import modelo.Venta;

/**
 * FXML Controller class
 *
 * @author Alanprogrammer
 */
public class ventas_control implements Initializable {

    @FXML
    private JFXButton bt_editar_venta;
    @FXML
    private JFXButton bt_imprimir_venta;
    @FXML
    private TableView<Venta> tabla_venta;
    @FXML
    private JFXTextField tbox_buscar;
    @FXML
    private TableColumn<?, ?> col_folio;
    @FXML
    private TableColumn<?, ?> col_caja;
    @FXML
    private TableColumn<?, ?> col_fecha;
    @FXML
    private TableColumn<?, ?> col_hora;
    @FXML
    private TableColumn<?, ?> col_cajero;
    @FXML
    private TableColumn<?, ?> col_cambio;
    @FXML
    private TableColumn<?, ?> col_efectivo;
    @FXML
    private TableColumn<?, ?> col_importe;
    @FXML
    private TableColumn<?, ?> col_propina;
    @FXML
    private TableColumn<?, ?> col_descuento;
    @FXML
    private JFXButton bt_refrescar;
    @FXML
    private JFXButton bt_exportar;
    
    private ObservableList<Venta> ventaLista = FXCollections.observableArrayList();
    @FXML
   

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        col_folio.setCellValueFactory(new PropertyValueFactory<>("folio"));
        col_caja.setCellValueFactory(new PropertyValueFactory<>("caja"));
        col_hora.setCellValueFactory(new PropertyValueFactory<>("hora"));
        col_fecha.setCellValueFactory(new PropertyValueFactory<>("fecha"));
        col_cajero.setCellValueFactory(new PropertyValueFactory<>("cajero"));
        col_cambio.setCellValueFactory(new PropertyValueFactory<>("cambio"));
        col_efectivo.setCellValueFactory(new PropertyValueFactory<>("efectivo"));
        col_importe.setCellValueFactory(new PropertyValueFactory<>("importe"));
        col_propina.setCellValueFactory(new PropertyValueFactory<>("propina"));
        col_descuento.setCellValueFactory(new PropertyValueFactory<>("descuento"));
        // TODO
        
        FilteredList<Venta> filtroVenta = new FilteredList<>(ventaLista, p -> true);
        tabla_venta.setItems(filtroVenta);
        tbox_buscar.textProperty().addListener((o, anterior, nuevo) -> {
            filtroVenta.setPredicate(Venta -> {
                if (nuevo == null || nuevo.isEmpty()) {
                    return true;
                }
                
                
                
                if(
                    Integer.toString(Venta.getFolio()).contains(nuevo)    ||
                    Integer.toString(Venta.getCaja()).contains(nuevo)    || 
                    Venta.getCajero().toLowerCase().contains(nuevo.toLowerCase())  ||
                    fechaTexto(Venta.getFecha()).contains(nuevo) ||
                    horaTexto(Venta.getHora()).contains(nuevo) ||
                    Float.toString(Venta.getCambio()).contains(nuevo)  ||
                    Float.toString(Venta.getEfectivo()).contains(nuevo)  ||
                    Float.toString(Venta.getImporte()).contains(nuevo)  ||
                    Float.toString(Venta.getPropina()).contains(nuevo)  ||
                    Float.toString(Venta.getDescuento()).contains(nuevo)  
                    
                            )
                {
                  return true;
                }
                return false;
            });
            
        });
        
        actualizarTabla();
    }    

    @FXML
    private void editarCliente(MouseEvent event) {
    }

    @FXML
    private void refrescar(MouseEvent event) {
        actualizarTabla();
    }

    @FXML
    private void exportar(MouseEvent event) {
        Exportar e =  new Exportar();
        e.export("ventas");
    }
    
    
    private void actualizarTabla() {
        ventaLista.clear();
        Connection conn = null;
        PreparedStatement sentencia = null;
        ResultSet resultado = null;
        try {
            conn = Main.conn.getConnection();
            sentencia = conn.prepareStatement("SELECT folio,comanda_v,fecha_v,hora_v,num_caja_v,cambio,efectivo,importe,propina,descuento,CONCAT(e.nombres_e,' ',e.apellido_pe,' ',e.apellido_me) AS cajero FROM ventas v LEFT JOIN empleados e ON v.cajero_v=e.codigo_empleado");
            resultado = sentencia.executeQuery();
            
            while (resultado.next()) {
                
                ventaLista.add( new Venta(
                      resultado.getInt("folio"),
                      resultado.getInt("num_caja_v"),
                      resultado.getDate("fecha_v"),
                      resultado.getTime("hora_v"),
                      resultado.getString("cajero"),
                      resultado.getFloat("cambio"),
                      resultado.getFloat("efectivo"),
                      resultado.getFloat("importe"),
                      resultado.getFloat("propina"),
                      resultado.getFloat("descuento")
                
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
    
    private String fechaTexto(Date fecha){
       return new SimpleDateFormat("yyyy-MM-dd").format(fecha);
    }
    
    private String horaTexto(Time hora){
       return new SimpleDateFormat("HH:mm:ss").format(hora);
    }
    
}
