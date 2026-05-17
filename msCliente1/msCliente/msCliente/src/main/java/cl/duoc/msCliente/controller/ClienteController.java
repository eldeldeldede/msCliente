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

@RestController
@RequestMapping("api/v1/clientes")
public class ClienteController {

    @Autowired
    private ClienteService service;

    @GetMapping
    public ResponseEntity<List<Cliente>> listar(){
        try {
            List<Cliente> cliente = service.listarClientes();
            return ResponseEntity.ok(cliente);
        } catch (Exception e) {
            return ResponseEntity.noContent().build();
        }
    }

    @PostMapping
    public ResponseEntity<Cliente> guardarCliente(@RequestBody Cliente cliente){
        try {
            Cliente clienteNuevo = service.guardarCliente(cliente);
            return ResponseEntity.ok(clienteNuevo);
        } catch (Exception e) {
            return ResponseEntity.noContent().build();
        }
    }

    @GetMapping("/id/{id}") 
    public ResponseEntity<Cliente> buscarPorId(@PathVariable Integer id){
        try {
            Cliente cliente = service.buscarCliente(id);
            return ResponseEntity.ok(cliente);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/rut/{rut}")
    public ResponseEntity<Cliente> buscarPorRut(@PathVariable String rut){
        try {
            Cliente cliente = service.buscarPorRut(rut);
            return ResponseEntity.ok(cliente);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/dto/{id}") 
    public ResponseEntity<ClienteDTO> buscarDTO(@PathVariable Integer id){
        try {
            Cliente cliente = service.buscarCliente(id);
            ClienteDTO dto = new ClienteDTO(cliente.getId(), cliente.getNombre(), cliente.getRut());

            return ResponseEntity.ok(dto);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Cliente> actualizar(@PathVariable Integer id, @RequestBody Cliente clienteActualizado){
        try {
            Cliente cliente = service.actualizarCliente(id, clienteActualizado);
            return ResponseEntity.ok(cliente);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id){
        try{
            service.eliminar(id);
            return ResponseEntity.noContent().build();
        }catch(Exception e){
            return ResponseEntity.notFound().build();
        }
    }
}
