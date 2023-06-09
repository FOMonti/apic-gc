package com.gestion.comercial;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.gestion.comercial.controller.ClienteController;
import com.gestion.comercial.entity.Cliente;
import com.gestion.comercial.mapper.ClienteMapper;
import com.gestion.comercial.repository.ClienteRepository;
import com.gestion.comercial.service.ClienteService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;


import static net.bytebuddy.matcher.ElementMatchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

//@RunWith(MockitoJUnitRunner.class)
//@SpringBootTest
@WebMvcTest(controllers = ClienteController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
public class ClienteControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    ObjectMapper objectMapper = new ObjectMapper();
    ObjectWriter objectWriter = objectMapper.writer();

    ClienteMapper clienteMapper = new ClienteMapper();
    @Mock
    private ClienteRepository clienteController;

    @MockBean
    private ClienteService clienteService;

//    @BeforeEach
//    public void setUp(){
//        MockitoAnnotations.initMocks(this);
//        this.mockMvc = MockMvcBuilders.standaloneSetup(clienteController).build();
//    }
    @Test
    public void save_success() throws Exception {
        Cliente esperado = new Cliente();
        esperado.setDni("12345678");
        esperado.setNombre("Juan");
        esperado.setApellido("Perez");
        esperado.setNumTelefono("1122334455");
        esperado.setEmail("juan.perez@gmail.com");
        esperado.setDireccion("Perez 1234");

        Mockito.when(clienteService.save(clienteMapper.clienteEntityARequest(esperado)))
                .thenReturn(clienteMapper.clienteEntityAResponse(esperado));

        String content = objectWriter.writeValueAsString(esperado);

        MockHttpServletRequestBuilder mockRequest = post("/clientes/save")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(content);

        ResultActions resultActions = mockMvc.perform(mockRequest);

        resultActions.andExpect(status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$").value(notNullValue()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.dni").value(esperado.getDni()));
    }
}
