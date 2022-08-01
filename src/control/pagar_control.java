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
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Alanprogrammer
 */
public class pagar_control implements Initializable {

    @FXML
    private JFXTextField tbox_efectivo;
    @FXML
    private JFXTextField tbox_propina;
    @FXML
    private JFXTextField tbox_descuento;
    @FXML
    private JFXButton bt_cancelar;
    @FXML
    private JFXButton bt_agregar;
    
    
    private int comanda;
    private int cajero;
    private int total;
    private int mesa;
    @FXML
    private Label lb_total;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void cancelar(MouseEvent event) {
        cerrar(event);
    }
    
    
     private void cerrar(MouseEvent event){
          Node nodo = (Node) event.getSource();
          Stage escenario = (Stage) nodo.getScene().getWindow();
          escenario.close();
      
     } 

    @FXML
    private void pagarComanda(MouseEvent event) {
        //INSERT INTO ventas(comanda_v,fecha_v,hora_v,num_caja_v,cajero_v,efectivo,importe,propina,descuento) VALUES(?,?,?,?,?,?,?,?,?)
        Node nodo = (Node) event.getSource();
        Stage escenario = (Stage) nodo.getScene().getWindow() ;
        
        Alert alerta; 
        Connection conn=null;
        PreparedStatement sentencia=null;

        try{
         conn= Main.conn.getConnection();
         sentencia = conn.prepareStatement("INSERT INTO ventas(comanda_v,fecha_v,hora_v,num_caja_v,cajero_v,efectivo,importe,propina,descuento,cambio) VALUES(?,CURRENT_DATE(),CURRENT_TIME(),?,?,?,?,?,?,?)");
         sentencia.setInt(1,  comanda );
         System.out.println(comanda);
         sentencia.setInt(2, Main.caja);
         sentencia.setInt(3, Main.codigoEmpleado);
         sentencia.setFloat(4, Float.parseFloat(tbox_efectivo.getText()));
         sentencia.setFloat(5, total);
         sentencia.setFloat(6, Float.parseFloat(tbox_propina.getText()));
         sentencia.setFloat(7, Float.parseFloat(tbox_descuento.getText()));
         sentencia.setFloat(8, Float.parseFloat(tbox_efectivo.getText())-((Float.parseFloat(tbox_propina.getText())+total)- Float.parseFloat(tbox_descuento.getText())) );
         sentencia.executeUpdate();
         sentencia.close();
         sentencia = conn.prepareStatement("UPDATE comandas SET estado_c=2 WHERE num_comanda=?");
         sentencia.setInt(1, comanda);
         sentencia.executeUpdate();
         sentencia = conn.prepareStatement("UPDATE mesas SET  estado_m=2 WHERE num_mesa=?");
         sentencia.setInt(1,  mesa);
         sentencia.executeQuery();
         
         alerta = new Alert(Alert.AlertType.INFORMATION);
         alerta.initOwner(escenario);
         alerta.setHeaderText("Exito");
         alerta.setContentText("Venta existosa");
         alerta.showAndWait();
         cerrar(event);
         
        } catch (SQLException ex) {
            System.out.println(ex);
            
        }finally{
          try{ if(conn!=null) sentencia.close();} catch(Exception e){};
          try{ if(conn!=null) conn.close();} catch(Exception e){};
        }
    
    
    }
    
    public void setCMDInformacion(int comanda,int total,int mesa){
         this.comanda=comanda;
         this.total=total;
         this.mesa=mesa;
         this.lb_total.setText("Total:"+Integer.toString(total));
    }
}
