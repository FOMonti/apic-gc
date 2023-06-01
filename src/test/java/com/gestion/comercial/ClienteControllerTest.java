package com.gestion.comercial;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.gestion.comercial.controller.ClienteController;
import com.gestion.comercial.entity.Cliente;
import com.gestion.comercial.repository.ClienteRepository;
import com.gestion.comercial.service.ClienteService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;


import static net.bytebuddy.matcher.ElementMatchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

//@RunWith(MockitoJUnitRunner.class)
public class ClienteControllerTest {

    private MockMvc mockMvc;

    ObjectMapper objectMapper = new ObjectMapper();
    ObjectWriter objectWriter = objectMapper.writer();
    @Mock
    private ClienteRepository clienteRepository;

    @Mock
    private ClienteService clienteService;
    @InjectMocks
    private ClienteController clienteController;

    @BeforeEach
    public void setUp(){
        MockitoAnnotations.initMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(clienteController).build();
    }
    @Test
    public void save_success() throws Exception {
        Cliente esperado = new Cliente();
        esperado.setDni("12345678");
        esperado.setNombre("Juan");
        esperado.setApellido("Perez");
        esperado.setNumTelefono("1122334455");
        esperado.setEmail("juan.perez@gmail.com");
        esperado.setDireccion("Perez 1234");

        //Mockito.when(clienteService.save(esperado)).thenReturn(esperado);

        String content = objectWriter.writeValueAsString(esperado);

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.post("/clientes/save")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(content);

        mockMvc.perform(mockRequest)
                .andExpect(status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$", notNullValue()))
                .andExpect((ResultMatcher) MockMvcResultMatchers.jsonPath("$.dni", is(esperado.getDni())));
    }
}
