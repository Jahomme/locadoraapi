package io.github.jahomme.locadora.controller;

import io.github.jahomme.locadora.entity.CarroEntity;
import io.github.jahomme.locadora.exceptions.EntityNotFoundException;
import io.github.jahomme.locadora.service.CarroService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@WebMvcTest(CarroController.class)
public class CarroControllerTest {

    @Autowired
    MockMvc mvc;

    @MockitoBean
    CarroService carroService;

    @Test
    void deveSalvarUmCarro() throws Exception {
        CarroEntity carro = new CarroEntity(1L, "Corolla", 150, 2026);

        when(carroService.salvar(any())).thenReturn(carro);

        String json = """
                {
                    "modelo": "Corolla",
                    "valorDiaria": 150,
                    "ano": 2026
                }
                """;

        ResultActions result = mvc.perform(
                post("/carros")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
        );

        result
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1));
    }

    @Test
    void deveRetornarIllegalArgumentAoTentarSalvarComDiariaNegativa() throws Exception {
        when(carroService.salvar(any())).thenThrow(IllegalArgumentException.class);

        String json = """
                {
                    "modelo": "Corolla",
                    "valorDiaria": 150,
                    "ano": 2026
                }
                """;

        ResultActions result = mvc.perform(
                post("/carros")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
        );

        result
                .andExpect(MockMvcResultMatchers.status().isUnprocessableEntity());
    }

    @Test
    void deveBuscarUmCarroPorId() throws Exception {
        CarroEntity carro = new CarroEntity(1L, "Corolla", 150, 2026);

        when(carroService.buscarPorId(any())).thenReturn(carro);

        ResultActions result = mvc.perform(
                get("/carros/1")
        );

        result
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.modelo").value("Corolla"));
    }

    @Test
    void deveRetornarNotFoundAoTentarBuscarUmCarroPorIdInexistente() throws Exception {
        when(carroService.buscarPorId(any())).thenThrow(EntityNotFoundException.class);

        ResultActions result = mvc.perform(
                get("/carros/1")
        );

        result.andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    void deveRetornarUmaListaDeCarros() throws Exception {
        CarroEntity carro = new CarroEntity(1L, "Corolla", 150, 2026);
        CarroEntity carro2 = new CarroEntity(1L, "Civic", 140, 2025);

        when(carroService.listarTodos()).thenReturn(List.of(carro, carro2));

        ResultActions result = mvc.perform(
                get("/carros")
        );

        result
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].modelo").value("Corolla"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].modelo").value("Civic"));

    }

    @Test
    void deveAtualizarUmCarro() throws Exception{
        CarroEntity carro = new CarroEntity(1L, "Corolla", 150, 2026);

        when(carroService.atualizar(any(), any())).thenReturn(carro);

        String json = """
                {
                    "modelo": "Corolla",
                    "valorDiaria": 150,
                    "ano": 2026
                }
                """;

        mvc.perform(
                put("/carros/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json)
        )
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    @Test
    void deveRetornarNotFoundAoTentarAtualizarCarroInexistente() throws Exception{
        when(carroService.atualizar(any(), any())).thenThrow(EntityNotFoundException.class);

        String json = """
                {
                    "modelo": "Corolla",
                    "valorDiaria": 150,
                    "ano": 2026
                }
                """;

        mvc.perform(
                put("/carros/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
        ).andExpect(MockMvcResultMatchers.status().isNotFound());

    }

    @Test
    void deveDeletarUmCarro() throws Exception{
        doNothing().when(carroService).deletar(any());

        mvc.perform(
                delete("/carros/1")
        ).andExpect(MockMvcResultMatchers.status().isNoContent());
    }


    @Test
    void deveRetornarNotFoundAoTentarDeletarCarroInexistente() throws Exception{
        doThrow(EntityNotFoundException.class).when(carroService).deletar(any());

        mvc.perform(
                delete("/carros/1")
        ).andExpect(MockMvcResultMatchers.status().isNotFound());
    }
}
