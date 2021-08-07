package me.d3249.demo.krugervacunas.negocio;

import me.d3249.demo.krugervacunas.excepcion.MarcaDuplicadaException;
import me.d3249.demo.krugervacunas.excepcion.ValorInvalidoException;
import me.d3249.demo.krugervacunas.modelo.InventarioVacuna;
import me.d3249.demo.krugervacunas.persistencia.InventarioVacunaRepository;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class AdministradorInventario {

    private final InventarioVacunaRepository repository;

    public AdministradorInventario(InventarioVacunaRepository repository) {
        this.repository = repository;
    }


    public InventarioVacuna registrarMarca(String marca, int existenciaInicial) {

        if (repository.existsByMarca(marca))
            throw new MarcaDuplicadaException("Marca duplicada");

        return repository.save(new InventarioVacuna(marca, existenciaInicial));
    }

    public List<InventarioVacuna> inventarioCompleto() {
        return StreamSupport.stream(repository.findAll().spliterator(), false)
                .sorted(Comparator.comparing(InventarioVacuna::marca))
                .collect(Collectors.toList());
    }

    public InventarioVacuna actualizar(String marca, int existencia) {
        var registroInventario = repository.findByMarca(marca)
                .orElseThrow(() -> new ValorInvalidoException("No se encuentra la marca solicitada"));

        registroInventario.actualizarExistencias(existencia);

        return repository.save(registroInventario);
    }
}
