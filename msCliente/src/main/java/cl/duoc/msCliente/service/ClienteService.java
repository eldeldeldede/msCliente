package cl.duoc.msCliente.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cl.duoc.msCliente.client.UsuarioClient;
import cl.duoc.msCliente.dto.ClienteDTO;
import cl.duoc.msCliente.dto.UsuarioDTO;
import cl.duoc.msCliente.model.Cliente;
import cl.duoc.msCliente.repository.ClienteRepository;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository repo;

    private UsuarioClient clientUsuario;;

    public List<Cliente> listarClientes(){
        return repo.findAll();
    }

    public Cliente buscarCliente(Integer id){
        return repo.findById(id)
        .orElseThrow(() -> new RuntimeException("cliente no encontrado"));
    }

    public Cliente buscarPorRut(String rut){
        return repo.findByRut(rut)
        .orElseThrow(() -> new RuntimeException("cliente no encontrado"));
    }

    public Cliente guardarCliente(Cliente cliente){
        if(cliente.getLicencia() != null){
            cliente.getLicencia().setCliente(cliente);
        }

        return repo.save(cliente);
    }

    public void eliminar(Integer id){
        if(repo.existsById(id)){
            repo.deleteById(id);
        }else{
            throw new RuntimeException("cliente no encontrado");
        }
    }

    public Cliente actualizarCliente(Integer id, Cliente clienteActualizado){
        Cliente cliente = repo.findById(id).orElseThrow(() -> new RuntimeException("cliente no encontrado"));
        cliente.setNombre(clienteActualizado.getNombre());
        cliente.setRut(clienteActualizado.getRut());
        cliente.setCorreo(clienteActualizado.getCorreo());
        cliente.setTelefono(clienteActualizado.getTelefono());
        cliente.setDireccion(clienteActualizado.getDireccion());

        if(clienteActualizado.getLicencia() != null){
            cliente.getLicencia().setCliente(cliente);
            cliente.setLicencia(clienteActualizado.getLicencia());
        }

        return repo.save(cliente);
    }

    public ClienteDTO buscarClienteDTO(Integer id){
        Cliente cliente = buscarCliente(id);

        UsuarioDTO usuario = clientUsuario.obtenerUsuarioDTO(cliente.getUsuarioId());
        return new ClienteDTO(cliente.getId(), cliente.getNombre(), cliente.getRut(), usuario);
        
    }
}
