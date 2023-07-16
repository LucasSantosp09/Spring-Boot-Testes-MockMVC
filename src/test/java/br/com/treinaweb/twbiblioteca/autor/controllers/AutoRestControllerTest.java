package br.com.treinaweb.twbiblioteca.autor.controllers;


import br.com.treinaweb.twbiblioteca.autor.builders.AutorResponseBuilder;
import br.com.treinaweb.twbiblioteca.autor.services.AutorService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;



@WebMvcTest(AutorRestController.class)
public class AutoRestControllerTest {

    @MockBean
    private AutorService service;
    @Autowired
    private MockMvc mockMvc;

    private static final String AUTOR_API_URL_PATH = "/api/v1/autores";

    @Test
    void quandoGETBuscarTodosDeveRetornarListaDeAutoresComStatusCode200 () throws Exception {
        var responseBody = AutorResponseBuilder.builder().build();

        Mockito.when(service.buscarTodos()).thenReturn(List.of(responseBody));

        mockMvc.perform(MockMvcRequestBuilders.get(AUTOR_API_URL_PATH).contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
    }

