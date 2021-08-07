package me.d3249.demo.krugervacunas.excepcion;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class ValorInvalidoException extends ResponseStatusException {

    public ValorInvalidoException(String message) {
        super(HttpStatus.BAD_REQUEST, message);
    }
}
