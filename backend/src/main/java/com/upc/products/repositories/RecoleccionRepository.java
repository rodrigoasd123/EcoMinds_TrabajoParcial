package com.upc.products.repositories;

import com.upc.products.entities.Recoleccion;
import com.upc.products.security.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface RecoleccionRepository extends JpaRepository<Recoleccion, Long> {
    List<Recoleccion> findByUsuario(User usuario);
    List<Recoleccion> findByUsuarioOrderByFechaDescHoraDesc(User usuario);
}