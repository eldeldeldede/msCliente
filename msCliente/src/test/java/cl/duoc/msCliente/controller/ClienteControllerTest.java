package cl.duoc.msCliente.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import cl.duoc.msCliente.model.Cliente;
import cl.duoc.msCliente.model.Licencia;
import cl.duoc.msCliente.model.TipoLicencia;
import cl.duoc.msCliente.service.ClienteService;

@WebMvcTest(ClienteController.class)//levanta solo la capa web
public class ClienteControllerTest {

    @Autowired
    private MockMvc mock; //Mock que elimina las peticiones http

    @MockitoBean
    private ClienteService service;
    
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
    void buscarCliente_retorno200() throws Exception{
        //ARRANGE: el service debe retornar el cliente
        when(service.buscarCliente(1)).thenReturn(clienteEjemplo);

        //ACT + ASSERT
        mock.perform(get("/api/v1/clientes/id/1")).andExpect(status().isOk());
    }

    @Test
    void buscarPorId_retorna404() throws Exception{
        //ARRANGE
        when(service.buscarCliente(99)).thenThrow(new RuntimeException("cliente no encontrado"));

        //ACT +ASSERT
        mock.perform(get("/api/v1/usuarios/id/99")).andExpect(status().isNotFound());
    }
}
