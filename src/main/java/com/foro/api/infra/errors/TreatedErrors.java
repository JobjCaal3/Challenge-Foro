package com.foro.api.infra.errors;

import jakarta.persistence.EntityNotFoundException;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.NoSuchElementException;

@RestControllerAdvice
public class TreatedErrors {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity tratarError400(MethodArgumentNotValidException e){
        var errores = e.getFieldErrors().stream().map(DatosErrorValidacion::new).toList();
        return ResponseEntity.badRequest().body(errores);
    }

    @ExceptionHandler(ValidationIntegration.class)
    public ResponseEntity tratarDeserializable400(Exception e){
        return ResponseEntity.badRequest().body(e.getMessage());
    }
    //TODO hacer una captura de excepcion de JWTVerificationException para ver si el token expiro y uno para JWTVerificationException
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<String> handleConstraintViolationException(ConstraintViolationException e) {
        String errorMessage = "El correo ya existe, intente con otro.";
        return ResponseEntity.badRequest().body(errorMessage);
    }

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity errorSearch(){
        String errorMessage = "no se encontro el elemento";
        return ResponseEntity.badRequest().body(errorMessage);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity tratarError404(){
        return ResponseEntity.notFound().build();
    }

    private record DatosErrorValidacion(String campo, String error){
        public DatosErrorValidacion(FieldError error) {
            this(error.getField(), error.getDefaultMessage());
        }
    }
}
