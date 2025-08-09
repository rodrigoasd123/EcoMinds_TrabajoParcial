package com.upc.products.serviceimpl;

import com.upc.products.dtos.CrearOrdenRequestDTO;
import com.upc.products.dtos.DetalleOrdenDTO;
import com.upc.products.dtos.OrdenDTO;
import com.upc.products.entities.Cliente;
import com.upc.products.entities.DetalleOrden;
import com.upc.products.entities.Orden;
import com.upc.products.entities.Producto;
import com.upc.products.repositories.ClienteRepositorio;
import com.upc.products.repositories.OrdenRepositorio;
import com.upc.products.repositories.ProductoRespositorio;
import com.upc.products.security.entities.User;
import com.upc.products.security.repositories.UserRepository;
import com.upc.products.services.OrderService;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.time.LocalDate;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrdenRepositorio ordenRepositorio;

    @Autowired
    private ClienteRepositorio clienteRepositorio;

    @Autowired
    private ProductoRespositorio productoRespositorio;

    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private UserRepository userRepository;

    @Transactional(rollbackOn = Exception.class)
    public boolean crearOrden(CrearOrdenRequestDTO requestDTO) {
        Long userId = requestDTO.getUserID();
        List<DetalleOrdenDTO> detallesDTO = requestDTO.getDetalles();

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User no encontrado con ID: " + userId));

        Orden orden = new Orden();
        orden.setUser(user);
        orden.setFecha(LocalDate.now());

        for (DetalleOrdenDTO detalleDTO : detallesDTO) {
            Producto producto = productoRespositorio.findById(detalleDTO.getProducto().getId())
                    .orElseThrow(() -> new IllegalArgumentException("Producto no encontrado con ID: " + detalleDTO.getProducto().getId()));

            DetalleOrden detalleOrden = modelMapper.map(detalleDTO, DetalleOrden.class);
            detalleOrden.setDetalleId(null);
            detalleOrden.setOrden(orden);
            detalleOrden.setProducto(producto);
            orden.getDetalles().add(detalleOrden);

            // Actualizar el stock del producto si es necesario
            producto.setStock(producto.getStock() - detalleDTO.getCantidad());
            productoRespositorio.save(producto);
        }

        orden = ordenRepositorio.save(orden);
        return true;
    }

    public List<Orden> obtenerTodasLasOrdenes() {
        List<Orden> ordenes = ordenRepositorio.findAll();
        return ordenes;
    }

    public Orden obtenerOrdenPorId(Long ordenId) {
        Orden orden = ordenRepositorio.findById(ordenId)
                .orElseThrow(() -> new IllegalArgumentException("Orden no encontrada con ID: " + ordenId));
        return orden;
    }
}
