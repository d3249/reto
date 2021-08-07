package me.d3249.demo.krugervacunas.excepcion;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class MarcaDuplicadaException extends ResponseStatusException {

    public MarcaDuplicadaException(String reason) {
        super(HttpStatus.BAD_REQUEST, reason);
    }
}
