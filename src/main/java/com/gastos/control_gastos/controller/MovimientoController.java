package com.gastos.control_gastos.controller;


import com.gastos.control_gastos.model.Movimiento;
import com.gastos.control_gastos.repository.MovimientoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class MovimientoController {

    @Autowired
    private MovimientoRepository movimientoRepository;

    @GetMapping("/")
    public String index(@RequestParam(required = false) String mes,
                        @RequestParam(required = false) String anio, Model model) {

        model.addAttribute("movimiento", new Movimiento());

        // Obtener fecha actual
        LocalDate hoy = LocalDate.now();
        int anioActual = (anio != null && !anio.isEmpty()) ? Integer.parseInt(anio) : hoy.getYear();
        int mesActual = (mes != null && !mes.isEmpty()) ? Integer.parseInt(mes) : hoy.getMonthValue();

        // Calcular fechas de inicio y fin del mes
        YearMonth yearMonth = YearMonth.of(anioActual, mesActual);
        LocalDate fechaInicio = yearMonth.atDay(1);
        LocalDate fechaFin = yearMonth.atEndOfMonth();

        // Obtener movimientos filtrados
        List<Movimiento> movimientos;
        BigDecimal totalIngresos;
        BigDecimal totalEgresos;

        if (mes != null || anio != null) {
            movimientos = movimientoRepository.findByFechaBetweenOrderByFechaDescIdDesc(fechaInicio, fechaFin);
            totalIngresos = movimientoRepository.getTotalIngresosPorPeriodo(fechaInicio, fechaFin);
            totalEgresos = movimientoRepository.getTotalEgresosPorPeriodo(fechaInicio, fechaFin);
        } else {
            movimientos = movimientoRepository.findAllByOrderByFechaDescIdDesc();
            totalIngresos = movimientoRepository.getTotalIngresos();
            totalEgresos = movimientoRepository.getTotalEgresos();
        }

        BigDecimal balance = totalIngresos.subtract(totalEgresos);

        model.addAttribute("movimientos", movimientos);
        model.addAttribute("totalIngresos", totalIngresos);
        model.addAttribute("totalEgresos", totalEgresos);
        model.addAttribute("balance", balance);
        model.addAttribute("categorias", getCategorias());
        model.addAttribute("mesSeleccionado", mesActual);
        model.addAttribute("anioSeleccionado", anioActual);
        model.addAttribute("esFiltrado", mes != null || anio != null);

        return "index";
    }

    @PostMapping("/guardar")
    public String guardarMovimiento(@ModelAttribute Movimiento movimiento) {
        movimientoRepository.save(movimiento);
        return "redirect:/";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminarMovimiento(@PathVariable Long id) {
        movimientoRepository.deleteById(id);
        return "redirect:/";
    }

    @ResponseBody
    @GetMapping("/categorias/{tipo}")
    public List<String> getCategoriasPorTipo(@PathVariable String tipo) {
        Map<String, List<String>> categorias = getCategorias();
        return categorias.getOrDefault(tipo, List.of());
    }

    private Map<String, List<String>> getCategorias() {
        Map<String, List<String>> categorias = new HashMap<>();
        categorias.put("INGRESO", List.of("Sueldo", "Negocio", "Otro"));
        categorias.put("EGRESO", List.of("Servicios", "Gasto", "Deuda"));
        return categorias;
    }
}