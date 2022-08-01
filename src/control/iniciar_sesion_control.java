package control;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.validation.NumberValidator;
import com.jfoenix.validation.RegexValidator;
import com.jfoenix.validation.RequiredFieldValidator;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.DragEvent;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import java.sql.*;
import javafx.scene.control.Alert;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCombination;
import javafx.stage.Modality;
import org.mariadb.jdbc.MariaDbPoolDataSource;

/**
 * FXML Controller class
 *
 * @author Alanprogrammer
 */
public class iniciar_sesion_control implements Initializable {

    private double xposicion = 0;
    private double yposicion = 0;
    @FXML
    private AnchorPane inicio;
    @FXML
    private JFXButton bt_login;
    @FXML
    private JFXTextField tbox_empleado;
    @FXML
    private JFXPasswordField tbox_pass;
    @FXML
    private ImageView bt_salir;
    @FXML
    private ImageView bt_minimizar;
    @FXML
    private ImageView bt_configuracion;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        tbox_empleado.setText("1");
    
        tbox_pass.setText("1234");
        // Validaciones para login
        //Empleado
        RegexValidator validar_empleado = new RegexValidator();
        validar_empleado.setRegexPattern("^[0-9]+$");
        tbox_empleado.getValidators().add(validar_empleado);
        
        tbox_empleado.textProperty().addListener((o, anterior, nuevo) -> {
            if(tbox_empleado.getText().isEmpty()){
              tbox_empleado.setText("");
            }
            else if(!tbox_empleado.validate()){
                tbox_empleado.setText(anterior);
            }
            
        });
        //

       
    }

    @FXML
    private void posicion(MouseEvent event) {
        xposicion = event.getSceneX();
        yposicion = event.getSceneY();

    }

    @FXML
    private void mover_ventana(MouseEvent event) {
        Main.stage.setX(event.getScreenX() - xposicion);
        Main.stage.setY(event.getScreenY() - yposicion);
        Main.stage.setOpacity(0.8f);
    }

    @FXML
    private void recuperar_opacidad(DragEvent event) {
        Main.stage.setOpacity(1.0f);
    }

    @FXML
    private void recuperar_opacidad1(MouseEvent event) {
        Main.stage.setOpacity(1.0f);
    }

   

    private void abrir_configuracion_servidor(MouseEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/vista/configuracion_servidor_vista.fxml"));
        Main.stage.setScene(new Scene(root));

        /*
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initStyle(StageStyle.UNDECORATED);
        Main.stage.setOpacity(0.8);
        stage.show();

         */
    }

    @FXML
    private void iniciar_sesion(ActionEvent event) {

        Alert alerta;
        

        if (!tbox_empleado.getText().isEmpty() && !tbox_pass.getText().isEmpty()) {
            Parent root;
            Connection conn = null;
            PreparedStatement sentencia=null;
            ResultSet resultado = null;
            try {
                
                sentencia = Main.conn.getConnection().prepareStatement("SELECT * FROM empleados WHERE codigo_empleado=? AND password=?");
                sentencia.setString(1, tbox_empleado.getText());
                sentencia.setString(2, tbox_pass.getText());
                resultado = sentencia.executeQuery();
                resultado.next();
               
                if (resultado.getInt("puesto_e") == 2) {
                   Main.usuario = 2;
                   
                } else if (resultado.getInt("puesto_e") == 1) {
                   Main.usuario = 1;
                }
                Main.codigoEmpleado=resultado.getInt("codigo_empleado");
                root = FXMLLoader.load(getClass().getResource("/vista/panel_gerente_vista.fxml"));
                    Main.stage.setScene(new Scene(root));
                    Main.stage.setFullScreenExitKeyCombination(KeyCombination.NO_MATCH);
                    Main.stage.setFullScreen(true);
            } catch (SQLException | IOException ex) {
                System.out.println(ex);
                alerta = new Alert(Alert.AlertType.ERROR);
                alerta.setTitle("ERROR BASE DE DATOS");
                alerta.setHeaderText("");
                alerta.setContentText("Error de conexion a la base de datos");
                alerta.initOwner(Main.stage);
                alerta.show();
            }finally{
                try{ if(conn!=null) conn.close();} catch(Exception e){};     
                try{ if(resultado!=null) resultado.close();} catch(Exception e){};
                try{ if(sentencia!=null) sentencia.close();} catch(Exception e){};
            } 

        } else {
            alerta = new Alert(Alert.AlertType.ERROR, "Hay campos vacio");
            alerta.setTitle("ERROR");
            alerta.setHeaderText("Hay campos vacios");
            alerta.setContentText("Por favor llene todos los campos requeridos");
            alerta.initOwner(Main.stage);
            alerta.show();
        }

    }

    @FXML
    private void Salir(MouseEvent event) {
         Platform.exit();
    }

    @FXML
    private void Minimizar(MouseEvent event) {
        Main.stage.setIconified(true);
    }

    @FXML
    private void configuracion(MouseEvent event) {
        try{
         Stage escenario = new Stage();
         Parent dialogo = FXMLLoader.load(getClass().getResource("/vista/configuracion_servidor_vista.fxml"));
         Scene escena = new Scene(dialogo);
         escenario.setScene(escena);
         escenario.setTitle("Configurar conexion a base de datos");
         escenario.initModality(Modality.APPLICATION_MODAL);
         escenario.initStyle(StageStyle.UTILITY);
         escenario.initOwner(Main.stage);
         escenario.setResizable(false);
         escenario.showAndWait();
         
        } catch (IOException ex) {
            Logger.getLogger(iniciar_sesion_control.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

}
