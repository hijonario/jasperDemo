package org.dam39.jasperdemo.controllers;

import net.sf.jasperreports.engine.JRException;
import org.dam39.jasperdemo.services.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Files;
import java.nio.file.Path;

@RestController
// Indica que esta clase es un controlador REST, lo que significa que maneja solicitudes HTTP y devuelve respuestas.
@RequestMapping("/report") // Define la ruta base para todas las solicitudes manejadas por este controlador.

public class ReportsController {

    @Autowired // Inyecta automáticamente la dependencia del servicio de reportes (ReportService).
    private ReportService reportService;

    // Define un endpoint GET para obtener un informe.
    @GetMapping("/getReport") // La URL completa será "/report/getReport".
    public ResponseEntity<byte[]> getEvents() {
        System.out.println("Obteniendo informe"); // Mensaje en consola para indicar que se está procesando la solicitud.

        try {
            // Llama al servicio para generar el informe y lo almacena como un array de bytes.
            byte[] report = reportService.generarReport("VapersReport");

            // Crea encabezados HTTP para especificar que la respuesta será un archivo PDF.
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_PDF); // Indica que el contenido es un PDF.
            headers.add("Content-Disposition", "inline; filename=report.pdf"); // Especifica cómo se debe mostrar el archivo (en línea).

            // Retorna el informe con los encabezados y un código de estado HTTP 200 (OK).
            return new ResponseEntity<>(report, headers, HttpStatus.OK);

        } catch (JRException e) {
            // Maneja excepciones relacionadas con JasperReports.
            System.out.println(e.getMessage()); // Muestra el mensaje de error en consola.

        } catch (FileNotFoundException e) {
            // Maneja excepciones cuando no se encuentra el archivo del informe.
            System.out.println(e.getMessage()); // Muestra el mensaje de error en consola.

        } catch (Exception e) {
            // Maneja cualquier otra excepción no anticipada.
            throw new RuntimeException(e); // Lanza una excepción para que sea manejada por el framework.
        }

        // En caso de error, retorna una respuesta con estado 500 (Error interno del servidor) y un cuerpo nulo.
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
    }

    @GetMapping("/getReport2/{filter}")
    public ResponseEntity<byte[]> getReports2(@PathVariable String filter) {
        System.out.println("Obteniendo informe"); // Mensaje en consola para indicar que se está procesando la solicitud.

        try {
            // Llama al servicio para generar el informe y lo almacena como un array de bytes.
            byte[] report = reportService.generarReport2("VapersReport", filter);

            // Crea encabezados HTTP para especificar que la respuesta será un archivo PDF.
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_PDF); // Indica que el contenido es un PDF.
            headers.add("Content-Disposition", "inline; filename=report.pdf"); // Especifica cómo se debe mostrar el archivo (en línea).

            // Retorna el informe con los encabezados y un código de estado HTTP 200 (OK).
            return new ResponseEntity<>(report, headers, HttpStatus.OK);

        } catch (JRException e) {
            // Maneja excepciones relacionadas con JasperReports.
            System.out.println(e.getMessage()); // Muestra el mensaje de error en consola.

        } catch (FileNotFoundException e) {
            // Maneja excepciones cuando no se encuentra el archivo del informe.
            System.out.println(e.getMessage()); // Muestra el mensaje de error en consola.

        } catch (Exception e) {
            // Maneja cualquier otra excepción no anticipada.
            throw new RuntimeException(e); // Lanza una excepción para que sea manejada por el framework.
        }

        // En caso de error, retorna una respuesta con estado 500 (Error interno del servidor) y un cuerpo nulo.
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
    }
}
