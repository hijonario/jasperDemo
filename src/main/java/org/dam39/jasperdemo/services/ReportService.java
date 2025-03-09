package org.dam39.jasperdemo.services;


import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import org.dam39.jasperdemo.database.SQLDatabaseManager;
import org.springframework.stereotype.Service;

import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;


@Service
public class ReportService {

    private Connection connection;

    private boolean initDBConnection(){
        try {
            connection = SQLDatabaseManager.connect();
            return true;
        } catch (SQLException e) {
            System.err.println("Error al conectar con la base de datos");
        }
        return false;
    }

    private boolean closeDBConnection(){
        try {
            SQLDatabaseManager.disconnect(connection);
            return true;
        } catch (SQLException e) {
            System.err.println("Error al desconectar con la base de datos");
        }
        return false;
    }


    public byte[] generarReport(String reportName) throws Exception {
        try {
            // Verifica si la conexión con la base de datos se pudo establecer
            if (!initDBConnection()) {
                throw new SQLException("Error al conectar con la base de datos");
            }

            // Carga el archivo del informe Jasper (.jasper) desde el sistema de archivos
            InputStream reportStream = new FileInputStream("src/main/resources/reports/" + reportName + ".jasper");

            // Verifica si el archivo del informe existe y se cargó correctamente
            if (reportStream == null) {
                throw new JRException("El informe no se encontró: " + reportName);
            }

            // Mapa para almacenar parámetros que se pasarán al informe
            Map<String, Object> parms = new HashMap<>();
            // Ejemplo de parámetro que se puede personalizar según la necesidad
            // parms.put("clave", "valor");

            // Rellena el informe con los datos obtenidos de la conexión a la base de datos
            JasperPrint jasperPrint = JasperFillManager.fillReport(reportStream, parms, connection);

            // Exporta el informe rellenado a formato PDF y lo devuelve como un array de bytes
            return JasperExportManager.exportReportToPdf(jasperPrint);

        } finally {
            // Asegura que la conexión a la base de datos se cierre en cualquier caso
            closeDBConnection();
        }
    }

    public byte[] generarReport2(String reportName, String filter) throws Exception {
        try {
            // Verifica si la conexión con la base de datos se pudo establecer
            if (!initDBConnection()) {
                throw new SQLException("Error al conectar con la base de datos");
            }

            System.out.println("Filtro recibido en JasperReports: " + filter);


            // Carga el archivo del informe Jasper (.jasper) desde el sistema de archivos
            InputStream reportStream = new FileInputStream("src/main/resources/reports/" + reportName + ".jasper");

            // Verifica si el archivo del informe existe y se cargó correctamente
            if (reportStream == null) {
                throw new JRException("El informe no se encontró: " + reportName);
            }

            // Mapa para almacenar parámetros que se pasarán al informe
            Map<String, Object> parms = new HashMap<>();
            // Ejemplo de parámetro que se puede personalizar según la necesidad
            parms.put("filtro", filter);

            // Rellena el informe con los datos obtenidos de la conexión a la base de datos
            JasperPrint jasperPrint = JasperFillManager.fillReport(reportStream, parms, connection);

            // Exporta el informe rellenado a formato PDF y lo devuelve como un array de bytes
            return JasperExportManager.exportReportToPdf(jasperPrint);

        } finally {
            // Asegura que la conexión a la base de datos se cierre en cualquier caso
            closeDBConnection();
        }
    }




}

