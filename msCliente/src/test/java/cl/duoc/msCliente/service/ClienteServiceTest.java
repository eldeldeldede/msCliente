package cl.duoc.msCliente.service;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import cl.duoc.msCliente.client.UsuarioClient;
import cl.duoc.msCliente.dto.ClienteDTO;
import cl.duoc.msCliente.dto.UsuarioDTO;
import cl.duoc.msCliente.model.Cliente;
import cl.duoc.msCliente.model.Licencia;
import cl.duoc.msCliente.model.TipoLicencia;
import cl.duoc.msCliente.repository.ClienteRepository;

@ExtendWith(MockitoExtension.class)
public class ClienteServiceTest {

    @Mock
    private ClienteRepository repo; // ⚠️ debe llamarse igual que el campo en ClienteService

    @Mock
    private UsuarioClient clientUsuario; // ⚠️ necesario porque ClienteService lo tiene inyectado

    @InjectMocks
    private ClienteService clienteService;

    private Cliente clienteEjemplo;

    @BeforeEach
    void setUp() {
        clienteEjemplo = new Cliente();
        clienteEjemplo.setId(1);
        clienteEjemplo.setNombre("Gonzalo Bahamondes");
        clienteEjemplo.setRut("19111111");
        clienteEjemplo.setCorreo("gon.bah@gmil.com");
        clienteEjemplo.setLicencia(new Licencia(1, "12321k", null, new TipoLicencia(1, "C")));
        clienteEjemplo.setTelefono("9999999");
        clienteEjemplo.setUsuarioId(12);
    }

    // ─────────────────────────────────────────────
    // buscarCliente
    // ─────────────────────────────────────────────

    @Test
    void buscarPorId_encontrado() {
        // ARRANGE
        when(repo.findById(1)).thenReturn(Optional.of(clienteEjemplo));

        // ACT
        Cliente resultado = clienteService.buscarCliente(1);

        // ASSERT
        assertEquals(1, resultado.getId());
        assertEquals("Gonzalo Bahamondes", resultado.getNombre());
        assertEquals("19111111", resultado.getRut());
    }

    @Test
    void buscarCliente_noEncontrado() {
        // ARRANGE
        when(repo.findById(777)).thenReturn(Optional.empty());

        // ACT + ASSERT
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            clienteService.buscarCliente(777);
        });

        assertEquals("cliente no encontrado", exception.getMessage());
    }

    // ─────────────────────────────────────────────
    // guardarCliente
    // ─────────────────────────────────────────────

    @Test
    void guardar() {
        // ARRANGE
        when(repo.save(clienteEjemplo)).thenReturn(clienteEjemplo);

        // ACT
        Cliente resultado = clienteService.guardarCliente(clienteEjemplo);

        // ASSERT
        assertEquals("Gonzalo Bahamondes", resultado.getNombre());
    }

    // ─────────────────────────────────────────────
    // eliminar
    // ─────────────────────────────────────────────

    @Test
    void eliminarExitoso() {
        // ARRANGE
        when(repo.existsById(1)).thenReturn(true);

        // ACT + ASSERT: no debe lanzar excepción
        assertDoesNotThrow(() -> clienteService.eliminar(1));

        // VERIFY: deleteById se llamó exactamente una vez
        verify(repo, times(1)).deleteById(1);
    }

    @Test
    void eliminar_noExiste() {
        // ARRANGE
        when(repo.existsById(999)).thenReturn(false);

        // ACT + ASSERT
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            clienteService.eliminar(999);
        });

        assertEquals("cliente no encontrado", exception.getMessage());

        // VERIFY: deleteById nunca debe llamarse
        verify(repo, times(0)).deleteById(999);
    }

    // ─────────────────────────────────────────────
    // actualizarCliente
    // ─────────────────────────────────────────────

    @Test
    void actualizarCliente_exitoso() {
        // ARRANGE
        Cliente clienteActualizado = new Cliente();
        clienteActualizado.setNombre("Gonzalo Actualizado");
        clienteActualizado.setRut("19222222");
        clienteActualizado.setCorreo("nuevo@correo.com");
        clienteActualizado.setTelefono("8888888");
        clienteActualizado.setLicencia(new Licencia(2, "99999z", null, new TipoLicencia(2, "B")));

        when(repo.findById(1)).thenReturn(Optional.of(clienteEjemplo));
        when(repo.save(clienteEjemplo)).thenReturn(clienteEjemplo);

        // ACT
        Cliente resultado = clienteService.actualizarCliente(1, clienteActualizado);

        // ASSERT
        assertEquals("Gonzalo Actualizado", resultado.getNombre());
        assertEquals("19222222", resultado.getRut());
        assertEquals("nuevo@correo.com", resultado.getCorreo());
        verify(repo, times(1)).save(clienteEjemplo);
    }

    @Test
    void actualizarCliente_noEncontrado() {
        // ARRANGE
        when(repo.findById(999)).thenReturn(Optional.empty());

        // ACT + ASSERT
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            clienteService.actualizarCliente(999, new Cliente());
        });

        assertEquals("cliente no encontrado", exception.getMessage());
    }

    // ─────────────────────────────────────────────
    // buscarClienteDTO
    // ─────────────────────────────────────────────

    @Test
    void buscarClienteDTO_exitoso() {
        // ARRANGE
        UsuarioDTO usuarioDTO = new UsuarioDTO();
        usuarioDTO.setId(12);

        when(repo.findById(1)).thenReturn(Optional.of(clienteEjemplo));
        when(clientUsuario.obtenerUsuarioDTO("gon.bah@gmil.com")).thenReturn(usuarioDTO);

        // ACT
        ClienteDTO resultado = clienteService.buscarClienteDTO(1);

        // ASSERT
        assertEquals(1, resultado.getId());
        assertEquals("Gonzalo Bahamondes", resultado.getNombre());
        assertEquals("19111111", resultado.getRut());
        assertEquals("gon.bah@gmil.com", resultado.getEmail());
        assertEquals(12, resultado.getIdUsuario());
    }

    @Test
    void buscarClienteDTO_clienteNoEncontrado() {
        // ARRANGE
        when(repo.findById(555)).thenReturn(Optional.empty());

        // ACT + ASSERT
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            clienteService.buscarClienteDTO(555);
        });

        assertEquals("cliente no encontrado", exception.getMessage());
    }

    // ─────────────────────────────────────────────
    // listarClientes
    // ─────────────────────────────────────────────

    @Test
    void listarClientes_retornaLista() {
        // ARRANGE
        when(repo.findAll()).thenReturn(List.of(clienteEjemplo));

        // ACT
        List<Cliente> resultado = clienteService.listarClientes();

        // ASSERT
        assertEquals(1, resultado.size());
        assertEquals("Gonzalo Bahamondes", resultado.get(0).getNombre());
    }

    // ─────────────────────────────────────────────
    // buscarPorRut
    // ─────────────────────────────────────────────

    @Test
    void buscarPorRut_encontrado() {
        // ARRANGE
        when(repo.findByRut("19111111")).thenReturn(Optional.of(clienteEjemplo));

        // ACT
        Cliente resultado = clienteService.buscarPorRut("19111111");

        // ASSERT
        assertEquals("19111111", resultado.getRut());
        assertEquals("Gonzalo Bahamondes", resultado.getNombre());
    }

    @Test
    void buscarPorRut_noEncontrado() {
        // ARRANGE
        when(repo.findByRut("00000000")).thenReturn(Optional.empty());

        // ACT + ASSERT
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            clienteService.buscarPorRut("00000000");
        });

        assertEquals("cliente no encontrado", exception.getMessage());
    }
}