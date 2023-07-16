package br.com.treinaweb.twbiblioteca.autor.controllers;


import br.com.treinaweb.twbiblioteca.autor.builders.AutorResponseBuilder;

import br.com.treinaweb.twbiblioteca.autor.exceptions.AutorNaoEncontradoException;
import br.com.treinaweb.twbiblioteca.autor.services.AutorService;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


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

        when(service.buscarTodos()).thenReturn(List.of(responseBody));

        mockMvc.perform(MockMvcRequestBuilders.get(AUTOR_API_URL_PATH).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void quandoGETBuscarPorIdComIdValidoDeveRetornarAutoComStatusCode200() throws Exception {
        var responseBody = AutorResponseBuilder.builder().build();

        when(service.buscarPorId(1L)).thenReturn(responseBody);

        mockMvc.perform(MockMvcRequestBuilders.get(AUTOR_API_URL_PATH + "/1").contentType(MediaType.APPLICATION_JSON))
               .andExpect(status().isOk());
    }


    @Test
    void quandoGETBuscarPorIdComIdInvalidoDeveRetornarStatusCode404() throws Exception {
       var id = 1L;

       when(service.buscarPorId(id)).thenThrow(AutorNaoEncontradoException.class);

      mockMvc.perform(MockMvcRequestBuilders.get(AUTOR_API_URL_PATH + "/1").contentType(MediaType.APPLICATION_JSON))
              .andExpect(status().isNotFound());

    }

    @Test
    void quandoDELETEExcluirPorIdComIdValidoDeveRetornarStatusCode204() throws Exception {
        var id = 1L;

        doNothing().when(service).excluirPorId(id);

        mockMvc.perform(delete(AUTOR_API_URL_PATH + "/" + id).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @Test
    void quandoDELETEExcluirPorIdComIdInvalidoDeveRetornarStatusCode404() throws Exception {
        var id = 1L;

        doThrow(AutorNaoEncontradoException.class).when(service).excluirPorId(id);

        mockMvc.perform(delete(AUTOR_API_URL_PATH + "/" + id).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

    }

    }

