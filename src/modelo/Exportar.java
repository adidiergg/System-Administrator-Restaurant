/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

/**
 *
 * @author Alanprogrammer
 */
import control.Main;
import java.io.*;
import java.sql.*;
import java.text.*;
import java.util.Date;
import javafx.scene.control.Alert;
 
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.*;
 
/**
 * An advanced Java program that exports data from any table to Excel file.
 * @author Nam Ha Minh
 * (C) Copyright codejava.net
 */
public class Exportar {
 
  
 
    private String getFileName(String baseName) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
        String dateTimeInfo = dateFormat.format(new Date());
        return baseName.concat(String.format("_%s.xlsx", dateTimeInfo));
    }
 
    public void export(String table) {
        Connection conn=null;
        PreparedStatement sentencia=null;
        ResultSet resultado=null;
        Alert alerta;
        String ruta = getFileName(table.concat("_exportacion"));
    
        try {
            conn= Main.conn.getConnection();
            //String sql = "SELECT codigo_empleado AS Codigo,nombres_e AS nombres,apellido_pe AS 'Apellido Paterno',apellido_me AS 'Apellido Materno' FROM ".concat(table);
            String sql = "SELECT * FROM ".concat(table);
            Statement statement = conn.createStatement();
 
            resultado = statement.executeQuery(sql);
            System.out.println("ddddbbbbbbbbbbb");
            
            XSSFWorkbook workbook = new XSSFWorkbook();
            System.out.println("ddddbbbbbbbbbbb");
            XSSFSheet sheet = workbook.createSheet(table);
 
            writeHeaderLine(resultado, sheet);
 
            writeDataLines(resultado, workbook, sheet);
 
            FileOutputStream outputStream = new FileOutputStream(ruta);
            workbook.write(outputStream);
            workbook.close();
 
            statement.close();
            
            alerta = new Alert(Alert.AlertType.INFORMATION);
            alerta.initOwner(Main.stage);
            alerta.setTitle("Exportacion existosa");
            alerta.setHeaderText("Se exporto los datos correctamente");
            alerta.setContentText(ruta);
            alerta.show();
 
        } catch (SQLException e) {
            System.out.println("Datababse error:");
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("File IO error:");
            e.printStackTrace();
        }finally{
             try{ if(conn!=null) conn.close();} catch(Exception e){};     
             try{ if(resultado!=null) resultado.close();} catch(Exception e){};
             try{ if(sentencia!=null) sentencia.close();} catch(Exception e){};
        
        }
    }
 
    private void writeHeaderLine(ResultSet result, XSSFSheet sheet) throws SQLException {
        // write header line containing column names
        ResultSetMetaData metaData = result.getMetaData();
        int numberOfColumns = metaData.getColumnCount();
 
        Row headerRow = sheet.createRow(0);
 
        // exclude the first column which is the ID field
        for (int i = 1; i <= numberOfColumns; i++) {
           
            String columnName = metaData.getColumnLabel(i);
            System.out.println(columnName);
            Cell headerCell = headerRow.createCell(i - 1);
            headerCell.setCellValue(columnName);
            /*
            Cell headerCell = headerRow.createCell(i - 2);
            
            */
        }
    }
 
    private void writeDataLines(ResultSet result, XSSFWorkbook workbook, XSSFSheet sheet)
            throws SQLException {
        ResultSetMetaData metaData = result.getMetaData();
        int numberOfColumns = metaData.getColumnCount();
 
        int rowCount = 1;
 
        while (result.next()) {
            Row row = sheet.createRow(rowCount++);
 
            for (int i = 1; i <= numberOfColumns; i++) {
                Object valueObject = result.getObject(i);
 
                Cell cell = row.createCell(i - 1);
 
                if (valueObject instanceof Boolean)
                    cell.setCellValue((Boolean) valueObject);
                else if (valueObject instanceof Double)
                    cell.setCellValue((double) valueObject);
                else if (valueObject instanceof Float)
                    cell.setCellValue((float) valueObject);
                else if (valueObject instanceof Date) {
                    cell.setCellValue((Date) valueObject);
                    formatDateCell(workbook, cell);
                } else cell.setCellValue(valueObject.toString());
 
            }
 
        }
    }
 
    private void formatDateCell(XSSFWorkbook workbook, Cell cell) {
        CellStyle cellStyle = workbook.createCellStyle();
        CreationHelper creationHelper = workbook.getCreationHelper();
        cellStyle.setDataFormat(creationHelper.createDataFormat().getFormat("yyyy-MM-dd HH:mm:ss"));
        cell.setCellStyle(cellStyle);
    }
    
    
}
