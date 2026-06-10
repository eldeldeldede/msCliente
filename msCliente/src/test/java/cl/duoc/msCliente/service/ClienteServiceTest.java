package cl.duoc.msCliente.service;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import cl.duoc.msCliente.model.Cliente;
import cl.duoc.msCliente.model.Licencia;
import cl.duoc.msCliente.model.TipoLicencia;
import cl.duoc.msCliente.repository.ClienteRepository;

@ExtendWith(MockitoExtension.class)
public class ClienteServiceTest {

    @Mock// no es e repositorY real, solo va a ser una simulacion
    private ClienteRepository clienteRepository;

    @InjectMocks//el servicio real con el repo simulado inyectado
    private ClienteService clienteService;

    private Cliente clienteEjemplo;

    @BeforeEach
    void setUp(){

        clienteEjemplo = new Cliente();
        clienteEjemplo.setId(1);
        clienteEjemplo.setNombre("Gonzalo Bahamondes");
        clienteEjemplo.setRut("19111111");
        clienteEjemplo.setCorreo("gon.bah@gmil.com");
        clienteEjemplo.setLicencia(new Licencia(1, "12321k", null, new TipoLicencia(1, "C")));
        clienteEjemplo.setTelefono("9999999");
        clienteEjemplo.setUsuarioId(12);
    }
    @Test
    public void buscarPorId_encontrado(){
        
        //ARRANGE
        Cliente cliente = clienteEjemplo;
        Optional<Cliente> optionalCliente = Optional.of(clienteEjemplo);
        when(clienteRepository.findById(1)).thenReturn(optionalCliente);
        //ACT
        Cliente resultado = clienteService.buscarCliente(1);
        //ASSERT
        assertEquals(1, resultado.getId());
        assertEquals("Gonzalo Bahamondes", resultado.getNombre());
        assertEquals("19111111", resultado.getRut());
    }

    @Test 
    void buscarCliente_noEncontrado(){
        //ARRANGE
        Optional<Cliente> clienteVacio =  Optional.empty();
        when(clienteRepository.findById(777)).thenReturn(clienteVacio);
        //ACT + ASSERT: verificamos si lanza la exception correcta
        RuntimeException exception = assertThrows(RuntimeException.class,() ->{
        clienteService.buscarCliente(777);
        });
        
        assertEquals("cliente no encontrado", exception.getMessage());
    }

    @Test
    void guardar(){
        //ARANGE: configuramos que el repository retorne el cliente guardado
        when(clienteRepository.save(clienteEjemplo)).thenReturn(clienteEjemplo);

        //ACT
        Cliente resultado= clienteService.guardarCliente(clienteEjemplo);

        //ASSERT
        assertEquals("Gonzalo Bahamondes", resultado.getNombre());
    }

    @Test
    void eliminarExitoso(){
        //ARRANGE
        when(clienteRepository.existsById(1)).thenReturn(true);
        //ASSERT + ASSERT: NO DEBE LANZAR ERROR/EXCEPTION
        assertDoesNotThrow(() -> clienteService.eliminar(1));
        //VERIFFICAMOS QUE EL DELETEBYID FUE EXITOSO SOLO UNA VEZ
        verify(clienteRepository, times(1)).deleteById(1);
        
    }
    @Test
    void eliminar_noExiste(){
        //ARRANGE
    when(clienteRepository.existsById(999)).thenReturn(false);
    
    //ACT + ASSERT: verificar que lanza la excepción
    RuntimeException exception = assertThrows(RuntimeException.class, () -> {
        clienteService.eliminar(999);
    });
    
    //ASSERT: verificar el mensaje de la excepción
    assertEquals("cliente no encontrado", exception.getMessage());
    
    //VERIFY: asegurar que nunca se llama a deleteById
    verify(clienteRepository, times(0)).deleteById(999);
    }

}
