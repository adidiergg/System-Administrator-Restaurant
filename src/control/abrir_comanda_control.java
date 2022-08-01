/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTimePicker;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ListIterator;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.print.PrinterJob;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import modelo.Cliente;
import modelo.Comanda;
import modelo.Empleado;
import modelo.Mesa;
import modelo.Pedido;
import modelo.PlatillosBebidas;

/**
 * FXML Controller class
 *
 * @author Alanprogrammer
 */
public class abrir_comanda_control implements Initializable {


    @FXML
    private JFXTextField tbox_fecha;
    @FXML
    private JFXTextField tbox_hora;
    @FXML
    private JFXTextField tbox_comanda;
    @FXML
    private JFXTextField tbox_caja;
    private JFXTextField tbox_cliente;
    @FXML
    private JFXTextField tbox_comensal;
    @FXML
    private TableView<Pedido> tabla_pedido;
    @FXML
    private TableView<PlatillosBebidas> tabla_pb;
    @FXML
    private TableColumn<?, ?> col_producto;
    @FXML
    private TableColumn<?, ?> col_codigo;
    @FXML
    private TableColumn<?, ?> col_precio;
    
    
    @FXML
    private JFXComboBox<Mesa> cbox_mesa;
    @FXML
    private JFXComboBox<Empleado> cbox_camarero;
    
   
    @FXML
    private JFXTextField tbox_buscar;
    @FXML
    private TableColumn<?, ?> col_cantidad;
    @FXML
    private TableColumn<?, ?> col_nombre;
    @FXML
    private JFXComboBox<Cliente> cbox_cliente;
    
    
     private ObservableList<Mesa> mesaLista = FXCollections.observableArrayList(); 
    private ObservableList<Empleado> camareroLista = FXCollections.observableArrayList();
    private ObservableList<PlatillosBebidas> pbLista = FXCollections.observableArrayList();
    private ObservableList<Cliente> clienteLista= FXCollections.observableArrayList();
    private ObservableList<Pedido> pedidoLista= FXCollections.observableArrayList();
    @FXML
    private JFXTextField tbox_cantidad;
    @FXML
    private JFXButton bt_pedido;
    @FXML
    private Label lb_total;
    @FXML
    private JFXButton bt_pagar1;
    
    private int total;
    @FXML
    private TableColumn<?, ?> col_precio1;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        bt_pedido.setDisable(true);
        
        
        col_cantidad.setCellValueFactory(new PropertyValueFactory<>("cantidad"));
      
        col_nombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        
        col_precio1.setCellValueFactory(new PropertyValueFactory<>("precio"));
        
        
        
        tabla_pedido.setItems(pedidoLista);
        
        
        
        col_codigo.setCellValueFactory(new PropertyValueFactory<>("clave"));
        col_producto.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        col_precio.setCellValueFactory(new PropertyValueFactory<>("precio"));
        
        FilteredList<PlatillosBebidas> filtropb = new FilteredList<>(pbLista, p -> true);
        tabla_pb.setItems(filtropb);
        tbox_buscar.textProperty().addListener((o, anterior, nuevo) -> {
            filtropb.setPredicate(PlatillosBebidas -> {
                if (nuevo == null || nuevo.isEmpty()) {
                    return true;
                }
                if(PlatillosBebidas.getNombre() .toLowerCase().contains(nuevo.toLowerCase()) 
                   || Float.toString(PlatillosBebidas.getPrecio()).contains(nuevo)
                   || Integer.toString(PlatillosBebidas.getClave()).contains(nuevo)
                        )
                {
                  return true;
                }
               
                
                
                return false;
            });
            
        });
        
        actualizarTablaProductos();
        
        
        actualizarMesas();
        cbox_mesa.setItems(mesaLista);
        actualizarCamareros();
        cbox_camarero.setItems(camareroLista);
         actualizarClientes();
        cbox_cliente.setItems(clienteLista);  
        
        tabla_pb.getSelectionModel().selectedItemProperty().addListener((o, anterior, nuevo) -> {
        bt_pedido.setDisable(false);
        });
        
        tbox_caja.setText(Integer.toString(Main.caja));
        // TODO
    }    
    
    
    
    private void actualizarMesas(){
       mesaLista.clear();
       Connection conn=null;
       PreparedStatement sentencia=null;
       ResultSet resultado = null;
       try {
        conn = Main.conn.getConnection();
        sentencia = conn.prepareStatement("SELECT num_mesa from mesas");
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
       try {
        Connection conn = Main.conn.getConnection();
        PreparedStatement sentencia = conn.prepareStatement("SELECT codigo_empleado,CONCAT(nombres_e,' ',apellido_pe,' ',apellido_me) AS nombrecompleto FROM empleados WHERE puesto_e=3");
        ResultSet resultado = sentencia.executeQuery();
        while(resultado.next()){
            camareroLista.add(new Empleado(resultado.getInt("codigo_empleado"),resultado.getString("nombrecompleto")));
        }
    }   catch (SQLException ex) {
            Logger.getLogger(nuevo_empleado_control.class.getName()).log(Level.SEVERE, null, ex);
        }
          
    }

    @FXML
    private void pagarComanda(MouseEvent event) {
        Node nodo = (Node) event.getSource();
        Stage principal = (Stage) nodo.getScene().getWindow() ;
        try {
            Stage escenario = new Stage();
            FXMLLoader fxml = new  FXMLLoader(getClass().getResource("/vista/pagar_vista.fxml"));
            Parent dialogo = fxml.load();
            
            pagar_control controlador = fxml.<pagar_control>getController();
            
           
            controlador.setCMDInformacion(  Integer.parseInt(tbox_comanda.getText()),total,cbox_mesa.getSelectionModel().getSelectedItem().getNumMesa() );
            
            Scene escena = new Scene(dialogo);
            escenario.setScene(escena);
            escenario.setTitle("Pagar");
            escenario.initOwner(principal);
            escenario.initModality(Modality.APPLICATION_MODAL);
            escenario.initStyle(StageStyle.UTILITY);
            
            escenario.showAndWait();
            cerrar(event);
        
        } catch (IOException ex) {
            Logger.getLogger(empleados_control.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    
    
    private void actualizarTablaProductos() {
        pbLista.clear();
        Connection conn = null;
        PreparedStatement sentencia = null;
        ResultSet resultado = null;
        try {
            conn = Main.conn.getConnection();
            sentencia = conn.prepareStatement("SELECT id_pb,nombre_pb,precio_pb,nombre_cpb,estado_epb FROM platillosbebidas pb LEFT JOIN categoriapb c ON pb.categoria_pb=c.id_categoriapb LEFT JOIN estados_platillobebida epb ON pb.estado_pb = epb.id_estado_epb");
            resultado = sentencia.executeQuery();
            
            while (resultado.next()) {
                pbLista.add(new PlatillosBebidas(
                  resultado.getInt("id_pb"),
                  resultado.getString("nombre_pb"),
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
    
    private void actualizarTablaPedidos() {
        pedidoLista.clear();
        Connection conn = null;
        PreparedStatement sentencia = null;
        ResultSet resultado = null;
        try {
            conn = Main.conn.getConnection();
            sentencia = conn.prepareStatement("SELECT cantidad_ppb,producto_ppb,nombre_pb,precio_pb FROM pedido_pb_detalles p LEFT JOIN platillosbebidas pb ON p.producto_ppb=pb.id_pb WHERE num_orden_ppb=?");
            System.out.println(tbox_comanda.getText());
            sentencia.setInt(1, Integer.parseInt(tbox_comanda.getText()) );
            
            resultado = sentencia.executeQuery();
            
            while (resultado.next()) {
                pedidoLista.add(new Pedido(
                  resultado.getInt("producto_ppb"),
                  resultado.getString("nombre_pb"),
                  resultado.getInt("cantidad_ppb"),
                  resultado.getFloat("precio_pb"),
                 (resultado.getFloat("precio_pb")*resultado.getInt("cantidad_ppb"))
                ));
                
            }
            resultado.close();
            sentencia.close();
            
            sentencia = conn.prepareStatement("SELECT SUM(precio_pb*cantidad_ppb) AS total FROM pedido_pb_detalles p LEFT JOIN platillosbebidas pb ON p.producto_ppb=pb.id_pb WHERE num_orden_ppb=?");
            sentencia.setInt(1, Integer.parseInt(tbox_comanda.getText()) );
            resultado = sentencia.executeQuery();
             while (resultado.next()) {
                lb_total.setText("Total: "+resultado.getInt("total"));
                total = resultado.getInt("total");
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
    
    
    
    
    public void setCMDInformacion(Comanda cmdInfomarcion) {
        this.tbox_comanda.setText(Integer.toString( cmdInfomarcion.getNum()));
        this.tbox_fecha.setText(fechaTexto(cmdInfomarcion.getFecha()));
        this.tbox_hora.setText(horaTexto(cmdInfomarcion.getHora()));
        this.tbox_comensal.setText(Integer.toString( cmdInfomarcion.getComensales() ));
        
        ListIterator i =  cbox_camarero.getItems().listIterator() ;
        while(i.hasNext()){
           if(i.next().toString().equals( cmdInfomarcion.getCamarero())){
              this.cbox_camarero.getSelectionModel().select(i.nextIndex()-1);
           }
        }
        
        
        i =  cbox_mesa.getItems().listIterator() ;
        while(i.hasNext()){
           if(i.next().toString().equals( Integer.toString(cmdInfomarcion.getCodMesa()))){
               System.out.println("muerto");
              this.cbox_mesa.getSelectionModel().select(i.nextIndex()-1);
           }
        }
        
        i =  cbox_cliente.getItems().listIterator() ;
        while(i.hasNext()){
           if(i.next().toString().equals( cmdInfomarcion.getCliente())){
              this.cbox_cliente.getSelectionModel().select(i.nextIndex()-1);
           }
        }
        
        
        actualizarTablaPedidos();
         /*
        
        this.tbox_comensal.setText(Integer.toString( cmdInfomarcion.getNum()));
        
        
       
        this.tbox_pb.setText(Integer.toString( pbInfomarcion.getClave() ));
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
        */
        
        
        
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
    
    private void cerrar(MouseEvent event){
         Node nodo = (Node) event.getSource();
         Stage escenario = (Stage) nodo.getScene().getWindow() ;
         escenario.close();
    }
    
    private String fechaTexto(Date fecha){
       return new SimpleDateFormat("yyyy-MM-dd").format(fecha);
    }
    
    private String horaTexto(Time hora){
       return new SimpleDateFormat("HH:mm:ss").format(hora);
    }

    private void imprimirComanda(MouseEvent event) {
        Node node = (Node) event.getSource();
        PrinterJob job = PrinterJob.createPrinterJob();
if (job != null && job.showPrintDialog(node.getScene().getWindow())){
    boolean success = job.printPage(node);
    if (success) {
        job.endJob();
    }
}
        /*
        Node nodo = (Node) event.getSource();
         Stage escenario = (Stage) nodo.getScene().getWindow() ;
        PrinterJob job = PrinterJob.createPrinterJob();
 if(job != null){
   job.showPrintDialog(Main.stage); // Window must be your main Stage
   job.printPage(nodo);
   job.endJob();
 }
 */
 
 
    }

    @FXML
    private void pedidoComanda(MouseEvent event) {
         Connection conn=null;
        PreparedStatement sentencia=null;
        

        try{
         
         conn= Main.conn.getConnection();
         sentencia = conn.prepareStatement("INSERT INTO pedido_pb_detalles(num_orden_ppb,cantidad_ppb, producto_ppb) VALUES(?,?,?)");
         sentencia.setInt(1, Integer.parseInt(tbox_comanda.getText()));
         sentencia.setInt(2,  Integer.parseInt(tbox_cantidad.getText())  );
         sentencia.setInt(3, tabla_pb.getSelectionModel().getSelectedItem().getClave() );
       
               
         sentencia.executeUpdate();
         
        } catch (SQLException ex) {
            System.out.println(ex);
            
        }finally{
          try{ if(conn!=null) sentencia.close();} catch(Exception e){};
          try{ if(conn!=null) conn.close();} catch(Exception e){};
        }
        
        actualizarTablaPedidos();
        bt_pedido.setDisable(true);
        
        
    }
    
}
