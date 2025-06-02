package com.picpaysimplificado.exception;

import com.picpaysimplificado.dtos.ExceptionsDTO;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ModifiedExceptions {

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ExceptionsDTO> threatDuplicateEntry(DataIntegrityViolationException exception) {
        ExceptionsDTO exceptionsDTO = new ExceptionsDTO("Usuario já cadastrado", "400");
        return ResponseEntity.badRequest().body(exceptionsDTO);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<Void> threatNotFoundException(EntityNotFoundException exception) {
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ExceptionsDTO> threatException(Exception exception) {
        ExceptionsDTO exceptionsDTO = new ExceptionsDTO(exception.getMessage(), "500");
        return ResponseEntity.internalServerError().body(exceptionsDTO);
    }
}
