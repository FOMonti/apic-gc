package com.gestion.comercial.service;

import com.gestion.comercial.dto.ClienteRequest;
import com.gestion.comercial.dto.ReservaResponse;
import com.gestion.comercial.dto.Vehicle;
import com.gestion.comercial.entity.Cliente;
import com.gestion.comercial.entity.Reserva;
import com.gestion.comercial.exception.ValidationException;
import com.gestion.comercial.mapper.ClienteMapper;
import com.gestion.comercial.mapper.ReservaMapper;
import com.gestion.comercial.repository.ClienteRepository;
import com.gestion.comercial.repository.ReservaRepository;
import com.gestion.comercial.types.EstadoReserva;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class ReservaService {

    private final ClienteService clienteService;
    private final ClienteMapper clienteMapper;
    private final VehiculoService vehiculoService;
    private final ReservaRepository reservaRepository;
    private final ReservaMapper reservaMapper;
    private final ClienteRepository clienteRepository;
    private final UtilService utilService;

    @Autowired
    public ReservaService(ClienteService clienteService, ClienteMapper clienteMapper,
                          VehiculoService vehiculoService, ReservaRepository reservaRepository,
                          ReservaMapper reservaMapper, ClienteRepository clienteRepository,
                          UtilService utilService){
        this.clienteService = clienteService;
        this.clienteMapper = clienteMapper;
        this.vehiculoService = vehiculoService;
        this.reservaRepository = reservaRepository;
        this.reservaMapper = reservaMapper;
        this.clienteRepository = clienteRepository;
        this.utilService = utilService;
    }

    public ReservaResponse save(ClienteRequest clienteRequest, String patente) {
        Optional<Cliente> clienteOp = clienteRepository.getClienteByDni(clienteRequest.getDni());
        Timestamp fechaAux = null;
        if(clienteOp.isPresent()){
            fechaAux = clienteOp.get().getFecha();
        }
        LocalDate fechaActual = LocalDate.now();
        Cliente cliente = clienteMapper.clienteRequestAEntity(clienteRequest);
        Vehicle vehicle = vehiculoService.getVehicleByPlate(patente);
        if(!vehicle.getStatus().equals("DISPONIBLE")){
            throw new ValidationException("Para reservar un auto este tiene que estar DISPONIBLE", "/reservas/save");
        }
        cliente = clienteRepository.save(cliente);
        if(fechaAux != null){
            cliente.setFecha(fechaAux);
        }
        Reserva reserva = new Reserva();
        reserva.setCliente(cliente);
        reserva.setEstadoReserva(EstadoReserva.PENDIENTE);
        reserva.setFechaVencimiento(fechaActual.plusDays(15));
        reserva.setFechaVencimientoPago(fechaActual.plusDays(2));
        reserva.setPatente(patente);
        reserva.setImporte(vehicle.getBasePrice() * 0.02);
        reserva = reservaRepository.save(reserva);
        ReservaResponse reservaResponse = reservaMapper.entityAResponse(reserva);
        reservaResponse.setClienteResponse(clienteMapper.clienteEntityAResponse(cliente));
        vehiculoService.actualizarEstado(patente, "RESERVADO");
        return reservaResponse;
    }


    public List<ReservaResponse> getAll() {
        return reservaMapper.entityAResponseList(reservaRepository.findAll());
    }

    public List<Reserva> getAllByClienteAndPagada(String dni) {
        return reservaRepository.findReservaByClienteDniAndEstadoReservaOrderByImporteDesc(dni, EstadoReserva.PAGADA);
    }

    public Optional<ReservaResponse> getReservaById(Long id) {
        Optional<Reserva> reservaOptional = reservaRepository.findById(id);
        ReservaResponse reservaResponse = null;
        if(reservaOptional.isPresent()){
            reservaResponse = reservaMapper.entityAResponse(reservaOptional.get());
        }
        return Optional.ofNullable(reservaResponse);
    }

    public Optional<ReservaResponse> anularCotizacion(Long id) {
        Optional<Reserva> reservaOptional =reservaRepository.findById(id);
        if(reservaOptional.isPresent()){
            Reserva reserva = reservaOptional.get();
            if(reserva.getEstadoReserva().equals(EstadoReserva.PAGADA)){
                throw new ValidationException("No se puede anular la reserva ya que la misma esta en estado: PAGADA",
                        "/reservas/anular/{id}");
            }else{
                reserva.setEstadoReserva(EstadoReserva.ANULADA);
                vehiculoService.actualizarEstado(reserva.getPatente(),"DISPONIBLE");
                reservaRepository.save(reserva);
            }
        }
        return getReservaById(id);
    }
}
