/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;

/**
 * FXML Controller class
 *
 * @author Alanprogrammer
 */
public class panel_cajero_control implements Initializable {

    @FXML
    private HBox bt_inicio;
    @FXML
    private HBox bt_comandas;
    @FXML
    private Pane panel;
    @FXML
    private HBox bt_caja;
    @FXML
    private HBox bt_clientes;
    @FXML
    private HBox bt_ventas;
    @FXML
    private HBox bt_configuracion;
    @FXML
    private HBox bt_salir;
    @FXML
    private Label titulo;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        
    }    

    @FXML
    private void Comandas(MouseEvent event) throws IOException {
        Pane comanda = FXMLLoader.load(getClass().getResource("/vista/comandas_vista.fxml"));
        panel.getChildren().clear();
        panel.getChildren().add(comanda);
        titulo.setText("Comandas");
    }

    @FXML
    private void Caja(MouseEvent event) throws IOException {
        Pane comanda = FXMLLoader.load(getClass().getResource("/vista/caja_vista.fxml"));
        panel.getChildren().clear();
        panel.getChildren().add(comanda);
        titulo.setText("Caja");
    }

    @FXML
    private void Clientes(MouseEvent event) throws IOException {
        Pane comanda = FXMLLoader.load(getClass().getResource("/vista/clientes_vista.fxml"));
        panel.getChildren().clear();
        panel.getChildren().add(comanda);
        titulo.setText("Clientes");
    }

    @FXML
    private void Ventas(MouseEvent event) throws IOException {
        Pane comanda = FXMLLoader.load(getClass().getResource("/vista/ventas_vista.fxml"));
        panel.getChildren().clear();
        panel.getChildren().add(comanda);
        titulo.setText("Ventas");
    }

    @FXML
    private void Configuracion(MouseEvent event) throws IOException {
        Pane comanda = FXMLLoader.load(getClass().getResource("/vista/configuraciones_vista.fxml"));
        panel.getChildren().clear();
        panel.getChildren().add(comanda);
        titulo.setText("Configuración");
    }

    @FXML
    private void Salir(MouseEvent event) {
       Alert alerta =  new Alert(Alert.AlertType.CONFIRMATION,"¿Estás seguro de que quieres salir?",ButtonType.YES,ButtonType.CANCEL);
       ButtonType resultado = alerta.showAndWait().orElse(ButtonType.YES);
       if(ButtonType.YES.equals(resultado)){
           Platform.exit();
       }
       
        //
    }

    @FXML
    private void Inicio(MouseEvent event) throws IOException {
        Pane inicio = FXMLLoader.load(getClass().getResource("/vista/inicio_vista.fxml"));
        panel.getChildren().clear();
        panel.getChildren().add(inicio);
        titulo.setText("INICIO");
    }
    
}
