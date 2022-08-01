package control;


import com.jfoenix.controls.JFXDecorator;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.mariadb.jdbc.MariaDbPoolDataSource;
import recursos.ruta;

/**
 *
 * @author Alanprogrammer
 */
/*INICIO DE PUNTO DE VENTA*/
public class Main extends Application {

    public static Stage stage = null;
    public static MariaDbPoolDataSource conn = null;
    public  static int usuario;
    public static int codigoEmpleado;
   public static int caja;
    @Override
    public void start(Stage stage) throws Exception {
        
        File configuraciones  = new File("configuracion.properties");
        if(configuraciones.exists()){
           System.out.println("siiiii");
        }else{
             try(OutputStream configuracion = new FileOutputStream("configuracion.properties")){
            Properties datosConexion = new Properties();
            datosConexion.setProperty("host","127.0.0.1");
            datosConexion.setProperty("port", "3306");
            datosConexion.setProperty("user",  "root");
            datosConexion.setProperty("password",  "root");
            datosConexion.setProperty("caja",  "1");
            datosConexion.setProperty("empresa",  "Master chef");
            datosConexion.setProperty("rfc",  "gg");
            datosConexion.setProperty("correo",  "gg");
            datosConexion.setProperty("web",  "gg");
            datosConexion.setProperty("telefono",  "gg");
            datosConexion.setProperty("direccion",  "gg");
            datosConexion.store(configuracion, null);
            
             }
        }
        
        
        //Iniciar base de datos
        /*Iniciar conexion base de datos*/
        ClassLoader classLoader = getClass().getClassLoader();
        try {
            //src/recursos/configuracion/conexion.properties
            try (InputStream configuracion = new FileInputStream("configuracion.properties") ) {

                Properties datosConexion = new Properties();
                datosConexion.load(configuracion);
                
                caja =  Integer.parseInt(datosConexion.getProperty("caja"));
                
                conn = new MariaDbPoolDataSource();
                //conn.setUrl("jdbc:mariadb://localhost:3306/plataforma_restaurante");
                conn.setLoginTimeout(3);
                //conn.setMinPoolSize(3);
                conn.setMaxPoolSize(5);
                conn.setMaxIdleTime(60);
                
                conn.setServerName(datosConexion.getProperty("host"));
                conn.setPort(Integer.parseInt(datosConexion.getProperty("port")));
                conn.setUser(datosConexion.getProperty("user"));
                conn.setPassword(datosConexion.getProperty("password"));
                
                conn.setDatabaseName("plataforma_restaurante");
                
                conn.initialize();
                
            }

        } catch (SQLException ex) {
            System.out.println(ex);
            new Alert(Alert.AlertType.ERROR, "Error de conexión a base de datos").showAndWait();
        }

        Parent root = FXMLLoader.load(getClass().getResource("/vista/iniciar_sesion_vista.fxml"));

        /*Decorador
         JFXDecorator decorador = new JFXDecorator(stage,root);
         decorador.setCustomMaximize(true);
         */
        Scene escena = new Scene(root);
        stage.setScene(escena);
        /*Titulo de la ventana*/
        stage.setTitle("Sistema punto de venta");
        stage.initStyle(StageStyle.UNDECORATED);
        
        stage.getIcons().add(new Image(classLoader.getResourceAsStream("recursos/imagenes/logo.png") ));
        //stage.getIcons().add(new Image(ruta.class.getResourceAsStream("iconos/borrar.png") ));
        this.stage = stage;
        stage.show();

    }

    public void stop() {
        System.out.println("dd");
    }

    public static void main(String[] args) {
        /*Iniciar apli
    cacion grafica*/
        launch(args);
    }

}
