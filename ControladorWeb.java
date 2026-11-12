package com.transporte.flores;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

@Controller
public class ControladorWeb {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    // 1. Página de Inicio (Muestra los clientes)
    @GetMapping("/")
    public String inicio(Model model) {
        String sql = "SELECT NombreEmpresa, NombreContacto, Telefono FROM Cliente";
        List<Map<String, Object>> clientes = jdbcTemplate.queryForList(sql);
        model.addAttribute("clientes", clientes);
        return "index";
    }

    // 2. Mostrar la página de Contacto
    @GetMapping("/contacto")
    public String mostrarContacto() {
        return "contacto";
    }

    // 3. Recibir los datos del formulario y guardarlos en SQL Server
    @PostMapping("/guardarContacto")
    public String guardarContacto(
            @RequestParam("nombre") String nombre,
            @RequestParam("correo") String correo,
            @RequestParam(value = "empresa", required = false) String empresa,
            @RequestParam("telefono") String telefono,
            @RequestParam("servicio") String servicio,
            @RequestParam(value = "mensaje", required = false) String mensaje,
            Model model) {

        // Consulta SQL para insertar en la tabla MensajeContacto
        String sql = "INSERT INTO MensajeContacto (NombreCompleto, Correo, Empresa, Telefono, ServicioInteres, Mensaje) VALUES (?, ?, ?, ?, ?, ?)";

        // Ejecutamos el insert en la base de datos
        jdbcTemplate.update(sql, nombre, correo, empresa, telefono, servicio, mensaje);

        // Enviamos un mensaje de éxito a la vista
        model.addAttribute("mensajeExito", true);

        return "contacto";
    }
}