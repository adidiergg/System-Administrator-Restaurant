/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

/**
 * FXML Controller class
 *
 * @author Alanprogrammer
 */
public class panel_gerente_control implements Initializable {

    @FXML
    private HBox bt_empleados;
    @FXML
    private HBox bt_proveedores;
    @FXML
    private HBox bt_clientes;
    @FXML
    private HBox bt_platillos_bebidas;
    @FXML
    private HBox bt_insumos;
    @FXML
    private HBox bt_mesas;
    @FXML
    private HBox bt_ventas;
    @FXML
    private HBox bt_configuracion;
    @FXML
    private HBox bt_salir;
    @FXML
    private Label titulo;
    @FXML
    private StackPane panel;
    @FXML
    private HBox bt_inicio1;
    @FXML
    private HBox bt_comanda;
    @FXML
    private VBox panel_menu;
    @FXML
    private VBox contenido;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        
        if(Main.usuario==2){
             panel_menu.getChildren().remove(bt_proveedores);
             panel_menu.getChildren().remove(bt_empleados);
        }
    }    

    @FXML
    private void Inicio(MouseEvent event) {
        try {
            Pane tab = FXMLLoader.load(getClass().getResource("/vista/inicio_vista.fxml"));
            contenido.getStyleClass().add("fondo_inicio");
            panel.getChildren().clear();
            panel.getChildren().add(tab);
            titulo.setText("");
        } catch (IOException ex) {
            Logger.getLogger(panel_gerente_control.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void Empleados(MouseEvent event) {
     try {  
            
            Pane tab = FXMLLoader.load(getClass().getResource("/vista/empleados_vista.fxml"));
            contenido.getStyleClass().remove("fondo_inicio");
            panel.getChildren().clear();
            panel.getChildren().add(tab);
            titulo.setText("Empleados");
            
        } catch (IOException ex) {
            Logger.getLogger(panel_gerente_control.class.getName()).log(Level.SEVERE, null, ex);
        }
    
    }

    @FXML
    private void Proveedores(MouseEvent event) {
        try {
            Pane tab = FXMLLoader.load(getClass().getResource("/vista/proveedores_vista.fxml"));
            contenido.getStyleClass().remove("fondo_inicio");
            panel.getChildren().clear();
            panel.getChildren().add(tab);
            titulo.setText("Proveedores");
        } catch (IOException ex) {
            Logger.getLogger(panel_gerente_control.class.getName()).log(Level.SEVERE, null, ex);
        }
    
    }

    @FXML
    private void Clientes(MouseEvent event) {
   
         try {
            Pane tab = FXMLLoader.load(getClass().getResource("/vista/clientes_vista.fxml"));
            contenido.getStyleClass().remove("fondo_inicio");
            panel.getChildren().clear();
            panel.getChildren().add(tab);
            titulo.setText("Clientes");
        } catch (IOException ex) {
            Logger.getLogger(panel_gerente_control.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void bt_caja(MouseEvent event) {
        try {
            Pane tab = FXMLLoader.load(getClass().getResource("/vista/caja_vista.fxml"));
            panel.getChildren().clear();
            panel.getChildren().add(tab);
            titulo.setText("Caja");
        } catch (IOException ex) {
            Logger.getLogger(panel_gerente_control.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void PlatillosBebidas(MouseEvent event) {
      try {
            Pane tab = FXMLLoader.load(getClass().getResource("/vista/platillos_bebidas_vista.fxml"));
            contenido.getStyleClass().remove("fondo_inicio");
            panel.getChildren().clear();
            panel.getChildren().add(tab);
            titulo.setText("Platillos y Bebidas");
        } catch (IOException ex) {
            Logger.getLogger(panel_gerente_control.class.getName()).log(Level.SEVERE, null, ex);
        }
    
    
    }

    @FXML
    private void Insumos(MouseEvent event) {
     try {
            Pane tab = FXMLLoader.load(getClass().getResource("/vista/insumos_vista.fxml"));
            panel.getChildren().clear();
            panel.getChildren().add(tab);
            titulo.setText("Insumos");
        } catch (IOException ex) {
            Logger.getLogger(panel_gerente_control.class.getName()).log(Level.SEVERE, null, ex);
        }
    
    
    }

    @FXML
    private void Mesas(MouseEvent event) {
        
        try {
            Pane tab = FXMLLoader.load(getClass().getResource("/vista/mesas_vista.fxml"));
            contenido.getStyleClass().remove("fondo_inicio");
            panel.getChildren().clear();
            panel.getChildren().add(tab);
            titulo.setText("Mesas");
        } catch (IOException ex) {
            Logger.getLogger(panel_gerente_control.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void Ventas(MouseEvent event) {
        try {
            Pane tab = FXMLLoader.load(getClass().getResource("/vista/ventas_vista.fxml"));
            contenido.getStyleClass().remove("fondo_inicio");
            panel.getChildren().clear();
            panel.getChildren().add(tab);
            titulo.setText("Ventas");
        } catch (IOException ex) {
            Logger.getLogger(panel_gerente_control.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void Configuracion(MouseEvent event) {
       try {
            Pane tab = FXMLLoader.load(getClass().getResource("/vista/configuraciones_vista.fxml"));
            contenido.getStyleClass().remove("fondo_inicio");
            panel.getChildren().clear();
            panel.getChildren().add(tab);
            titulo.setText("Configuración");
        } catch (IOException ex) {
            Logger.getLogger(panel_gerente_control.class.getName()).log(Level.SEVERE, null, ex);
        }
    
    }

    @FXML
    private void Salir(MouseEvent event) {
        Alert alerta = new Alert(Alert.AlertType.CONFIRMATION,"¿Estás seguro de que quieres salir?",ButtonType.YES,ButtonType.CANCEL);
        alerta.initOwner(Main.stage);
        ButtonType resultado = alerta.showAndWait().orElse(ButtonType.YES);
        if(ButtonType.YES.equals(resultado)){
          Platform.exit();
        }
    }

    @FXML
    private void comanda(MouseEvent event) {
        Pane comanda;
        try {
            comanda = FXMLLoader.load(getClass().getResource("/vista/comandas_vista.fxml"));
            contenido.getStyleClass().remove("fondo_inicio");
            panel.getChildren().clear();
            panel.getChildren().add(comanda);
            titulo.setText("Comandas");
        } catch (IOException ex) {
            Logger.getLogger(panel_gerente_control.class.getName()).log(Level.SEVERE, null, ex);
        }
       
    }
    
}
