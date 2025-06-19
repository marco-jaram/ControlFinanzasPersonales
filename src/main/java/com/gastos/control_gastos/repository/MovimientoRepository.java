package com.gastos.control_gastos.repository;



import com.gastos.control_gastos.model.Movimiento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface MovimientoRepository extends JpaRepository<Movimiento, Long> {

    List<Movimiento> findAllByOrderByFechaDescIdDesc();

    List<Movimiento> findByFechaBetweenOrderByFechaDescIdDesc(LocalDate fechaInicio, LocalDate fechaFin);

    @Query("SELECT COALESCE(SUM(m.monto), 0) FROM Movimiento m WHERE m.tipo = 'INGRESO'")
    BigDecimal getTotalIngresos();

    @Query("SELECT COALESCE(SUM(m.monto), 0) FROM Movimiento m WHERE m.tipo = 'EGRESO'")
    BigDecimal getTotalEgresos();

    @Query("SELECT COALESCE(SUM(m.monto), 0) FROM Movimiento m WHERE m.tipo = 'INGRESO' AND m.fecha BETWEEN :fechaInicio AND :fechaFin")
    BigDecimal getTotalIngresosPorPeriodo(@Param("fechaInicio") LocalDate fechaInicio, @Param("fechaFin") LocalDate fechaFin);

    @Query("SELECT COALESCE(SUM(m.monto), 0) FROM Movimiento m WHERE m.tipo = 'EGRESO' AND m.fecha BETWEEN :fechaInicio AND :fechaFin")
    BigDecimal getTotalEgresosPorPeriodo(@Param("fechaInicio") LocalDate fechaInicio, @Param("fechaFin") LocalDate fechaFin);
}
