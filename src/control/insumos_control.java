/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

/**
 * FXML Controller class
 *
 * @author Alanprogrammer
 */
public class insumos_control implements Initializable {

    @FXML
    private JFXTextField tbox_caja;
    @FXML
    private JFXTextField tbox_nombre;
    @FXML
    private JFXTextField tbox_rfc;
    @FXML
    private JFXTextField tbox_correo;
    @FXML
    private JFXTextField tbox_telefono;
    @FXML
    private JFXTextField tbox_direccion;
    @FXML
    private JFXTextField tbox_host;
    @FXML
    private JFXTextField tbox_port;
    @FXML
    private JFXTextField tbox_user;
    @FXML
    private JFXPasswordField tbox_pass;
    @FXML
    private JFXButton bt_guardar;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void guardarConfiguracion(MouseEvent event) {
    }

    
}
