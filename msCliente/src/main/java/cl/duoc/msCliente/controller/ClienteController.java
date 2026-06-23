package cl.duoc.msCliente.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cl.duoc.msCliente.dto.ClienteDTO;
import cl.duoc.msCliente.model.Cliente;
import cl.duoc.msCliente.service.ClienteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("api/v1/clientes")
@Tag(name = "Cliente", description = "Operaciones relacionadas con clientes")
public class ClienteController {

    @Autowired
    private ClienteService service;

    @GetMapping
    @Operation(
        summary = "Obtener la lista de clientes registrados",
        description = "Retorna la lista de clientes registrados en el sistema del Rent a Car."
    )
    public ResponseEntity<List<Cliente>> listar(){
        try {
            List<Cliente> cliente = service.listarClientes();
            return ResponseEntity.ok(cliente);
        } catch (Exception e) {
            return ResponseEntity.noContent().build();
        }
    }

    @PostMapping
    @Operation(
        summary = "Registrar un nuevo Cliente",
        description = "Permite registrar un nuevo cliente en el sistema del Rent a Car."
    )
    public ResponseEntity<Cliente> guardarCliente(@RequestBody Cliente cliente){
        try {
            Cliente clienteNuevo = service.guardarCliente(cliente);
            return ResponseEntity.ok(clienteNuevo);
        } catch (Exception e) {
            return ResponseEntity.noContent().build();
        }
    }

    @GetMapping("/id/{id}") 
    @Operation(
        summary = "Buscar cliente por ID",
        description = "Retorna los detalles de un cliente específico por su ID."
    )
    public ResponseEntity<Cliente> buscarPorId(@PathVariable Integer id){
        try {
            Cliente cliente = service.buscarCliente(id);
            return ResponseEntity.ok(cliente);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/rut/{rut}")
    @Operation(
        summary = "Buscar cliente por RUT",
        description = "Retorna los detalles de un cliente específico por su RUT."
    )
    public ResponseEntity<Cliente> buscarPorRut(@PathVariable String rut){
        try {
            Cliente cliente = service.buscarPorRut(rut);
            return ResponseEntity.ok(cliente);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/dto/{id}") 
    @Operation(
        summary = "Buscar cliente por ID (DTO)",
        description = "Retorna los detalles de un cliente específico por su ID en formato DTO."
    )
    public ResponseEntity<ClienteDTO> buscarDTO(@PathVariable Integer id){
        try {
            ClienteDTO clienteDTO = service.buscarClienteDTO(id);
            return ResponseEntity.ok(clienteDTO);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}")
    @Operation(
        summary = "Actualizar cliente",
        description = "Permite actualizar los detalles de un cliente específico por su ID."
    )
    public ResponseEntity<Cliente> actualizar(@PathVariable Integer id, @RequestBody Cliente clienteActualizado){
        try {
            Cliente cliente = service.actualizarCliente(id, clienteActualizado);
            return ResponseEntity.ok(cliente);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    @Operation(
        summary = "Eliminar cliente",
        description = "Permite eliminar un cliente específico por su ID."
    )
    public ResponseEntity<Void> eliminar(@PathVariable Integer id){
        try{
            service.eliminar(id);
            return ResponseEntity.noContent().build();
        }catch(Exception e){
            return ResponseEntity.notFound().build();
        }
    }
}
