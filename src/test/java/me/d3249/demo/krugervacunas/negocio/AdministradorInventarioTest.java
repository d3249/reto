package me.d3249.demo.krugervacunas.negocio;

import me.d3249.demo.krugervacunas.excepcion.MarcaDuplicadaException;
import me.d3249.demo.krugervacunas.excepcion.ValorInvalidoException;
import me.d3249.demo.krugervacunas.modelo.InventarioVacuna;
import me.d3249.demo.krugervacunas.persistencia.InventarioVacunaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;

class AdministradorInventarioTest {
    private final int existenciaInicial = 0;

    private final String marca1 = "AstraZeneca";
    private final InventarioVacuna registro1 = new InventarioVacuna(marca1, existenciaInicial);

    private final String marca2 = "Sputnik";
    private final InventarioVacuna registro2 = new InventarioVacuna(marca2, existenciaInicial);

    private final String marca3 = "Sinovac";
    private final InventarioVacuna registro3 = new InventarioVacuna(marca3, existenciaInicial);

    @Mock
    InventarioVacunaRepository repository;

    @InjectMocks
    AdministradorInventario sut;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void registraUnaNuevaMarcaConInventarioInicial() {
        when(repository.save(any())).thenAnswer(context -> context.getArgument(0));

        InventarioVacuna resultado = sut.registrarMarca(marca1, existenciaInicial);

        assertThat(resultado).isEqualTo(registro1);
    }

    @Test
    void recuperaListaOrdenadaPorOrdenAlfabetico() {

        given(repository.findAll()).willReturn(List.of(registro2, registro1, registro3));

        List<InventarioVacuna> resultado = sut.inventarioCompleto();

        assertThat(resultado).containsExactly(registro1, registro3, registro2);


    }

    @Test
    void actualizaExistenciasDeUnaVacuna() {

        int nuevoValor = 50;

        when(repository.findByMarca(marca1)).thenReturn(Optional.of(registro1));
        when(repository.save(any())).thenAnswer(context -> context.getArgument(0));

        InventarioVacuna resultado = sut.actualizar(marca1, nuevoValor);

        assertThat(resultado).isEqualTo(new InventarioVacuna(marca1, nuevoValor));
    }

    @Test
    void lanzaExcepcionSiLaMarcaNoSeEncuentra(){

        when(repository.findByMarca(marca1)).thenReturn(Optional.empty());

        assertThrows(ValorInvalidoException.class, () -> sut.actualizar(marca1, 10));
    }

    @Test
    void registrarUnaMarcaDuplicadaLanzaExcepcion() {

        when(repository.existsByMarca(marca1)).thenReturn(true);

        assertThrows(MarcaDuplicadaException.class, () -> sut.registrarMarca(marca1, existenciaInicial));
    }
}