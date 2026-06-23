package cl.duoc.msCliente.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import cl.duoc.msCliente.dto.ClienteDTO;
import cl.duoc.msCliente.model.Cliente;
import cl.duoc.msCliente.model.Licencia;
import cl.duoc.msCliente.model.TipoLicencia;
import cl.duoc.msCliente.service.ClienteService;

@WebMvcTest(ClienteController.class)
public class ClienteControllerTest {

    @Autowired
    private MockMvc mock;

    @Autowired
    private ObjectMapper objectMapper; // serializa objetos Java a JSON

    @MockitoBean
    private ClienteService service;

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
    // GET /api/v1/clientes  →  listar()
    // ─────────────────────────────────────────────

    @Test
    void listar_retorna200ConLista() throws Exception {
        // ARRANGE
        when(service.listarClientes()).thenReturn(List.of(clienteEjemplo));

        // ACT + ASSERT
        mock.perform(get("/api/v1/clientes"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].nombre").value("Gonzalo Bahamondes"));
    }

    @Test
    void listar_retorna204CuandoFalla() throws Exception {
        // ARRANGE: el service lanza excepción → controller devuelve 204 noContent
        when(service.listarClientes()).thenThrow(new RuntimeException("error inesperado"));

        // ACT + ASSERT
        mock.perform(get("/api/v1/clientes"))
                .andExpect(status().isNoContent());
    }

    // ─────────────────────────────────────────────
    // POST /api/v1/clientes  →  guardarCliente()
    // ─────────────────────────────────────────────

    @Test
    void guardarCliente_retorna200() throws Exception {
        // ARRANGE
        when(service.guardarCliente(any(Cliente.class))).thenReturn(clienteEjemplo);

        // ACT + ASSERT
        mock.perform(post("/api/v1/clientes")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(clienteEjemplo)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre").value("Gonzalo Bahamondes"))
                .andExpect(jsonPath("$.rut").value("19111111"));
    }

    @Test
    void guardarCliente_retorna204CuandoFalla() throws Exception {
        // ARRANGE: service lanza excepción → controller devuelve 204 noContent
        when(service.guardarCliente(any(Cliente.class))).thenThrow(new RuntimeException("error al guardar"));

        // ACT + ASSERT
        mock.perform(post("/api/v1/clientes")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(clienteEjemplo)))
                .andExpect(status().isNoContent());
    }

    // ─────────────────────────────────────────────
    // GET /api/v1/clientes/id/{id}  →  buscarPorId()
    // ─────────────────────────────────────────────

    @Test
    void buscarCliente_retorno200() throws Exception {
        // ARRANGE
        when(service.buscarCliente(1)).thenReturn(clienteEjemplo);

        // ACT + ASSERT
        mock.perform(get("/api/v1/clientes/id/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.nombre").value("Gonzalo Bahamondes"));
    }

    @Test
    void buscarPorId_retorna404() throws Exception {
        // ARRANGE
        when(service.buscarCliente(99)).thenThrow(new RuntimeException("cliente no encontrado"));

        // ACT + ASSERT  ⚠️ corregida la URL: era /usuarios, debe ser /clientes
        mock.perform(get("/api/v1/clientes/id/99"))
                .andExpect(status().isNotFound());
    }

    // ─────────────────────────────────────────────
    // GET /api/v1/clientes/rut/{rut}  →  buscarPorRut()
    // ─────────────────────────────────────────────

    @Test
    void buscarPorRut_retorna200() throws Exception {
        // ARRANGE
        when(service.buscarPorRut("19111111")).thenReturn(clienteEjemplo);

        // ACT + ASSERT
        mock.perform(get("/api/v1/clientes/rut/19111111"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.rut").value("19111111"));
    }

    @Test
    void buscarPorRut_retorna404() throws Exception {
        // ARRANGE
        when(service.buscarPorRut("00000000")).thenThrow(new RuntimeException("cliente no encontrado"));

        // ACT + ASSERT
        mock.perform(get("/api/v1/clientes/rut/00000000"))
                .andExpect(status().isNotFound());
    }

    // ─────────────────────────────────────────────
    // GET /api/v1/clientes/dto/{id}  →  buscarDTO()
    // ─────────────────────────────────────────────

    @Test
    void buscarDTO_retorna200() throws Exception {
        // ARRANGE
        ClienteDTO dto = new ClienteDTO(1, "Gonzalo Bahamondes", "19111111", "gon.bah@gmil.com", 12);
        when(service.buscarClienteDTO(1)).thenReturn(dto);

        // ACT + ASSERT
        mock.perform(get("/api/v1/clientes/dto/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.nombre").value("Gonzalo Bahamondes"))
                .andExpect(jsonPath("$.idUsuario").value(12));
    }

    @Test
    void buscarDTO_retorna404() throws Exception {
        // ARRANGE
        when(service.buscarClienteDTO(99)).thenThrow(new RuntimeException("cliente no encontrado"));

        // ACT + ASSERT
        mock.perform(get("/api/v1/clientes/dto/99"))
                .andExpect(status().isNotFound());
    }

    // ─────────────────────────────────────────────
    // PUT /api/v1/clientes/{id}  →  actualizar()
    // ─────────────────────────────────────────────

    @Test
    void actualizar_retorna200() throws Exception {
        // ARRANGE
        when(service.actualizarCliente(eq(1), any(Cliente.class))).thenReturn(clienteEjemplo);

        // ACT + ASSERT
        mock.perform(put("/api/v1/clientes/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(clienteEjemplo)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre").value("Gonzalo Bahamondes"));
    }

    @Test
    void actualizar_retorna404() throws Exception {
        // ARRANGE
        when(service.actualizarCliente(eq(99), any(Cliente.class)))
                .thenThrow(new RuntimeException("cliente no encontrado"));

        // ACT + ASSERT
        mock.perform(put("/api/v1/clientes/99")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(clienteEjemplo)))
                .andExpect(status().isNotFound());
    }

    // ─────────────────────────────────────────────
    // DELETE /api/v1/clientes/{id}  →  eliminar()
    // ─────────────────────────────────────────────

    @Test
    void eliminar_retorna204() throws Exception {
        // ARRANGE: eliminar() es void, con doNothing simulamos que no hace nada
        doNothing().when(service).eliminar(1);

        // ACT + ASSERT: 204 No Content es el retorno exitoso del controller
        mock.perform(delete("/api/v1/clientes/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    void eliminar_retorna404() throws Exception {
        // ARRANGE
        doThrow(new RuntimeException("cliente no encontrado")).when(service).eliminar(99);

        // ACT + ASSERT
        mock.perform(delete("/api/v1/clientes/99"))
                .andExpect(status().isNotFound());
    }
}