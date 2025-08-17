package br.com.viniapp.vinitefapp.infraestructure.exception;


import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
public class HandlerExceptions {

    final private Logger logger = LoggerFactory.getLogger(HandlerExceptions.class);

    @ExceptionHandler(HttpException.class)
    public ResponseEntity<StandardErrorSpring> validationException(HttpException e, HttpServletRequest req) {
        logger.error("Erro da requisição", e);
        StandardErrorSpring err = new StandardErrorSpring(System.currentTimeMillis(), e.getStatus().value(),
                "Erro da requisição", List.of(), e.getMessage(), req.getRequestURI());

        return ResponseEntity.status(e.getStatus()).body(err);
    }

    @ExceptionHandler(SocketTimeoutException.class)
    public ResponseEntity<StandardErrorSpring> timeoutException(SocketTimeoutException e, HttpServletRequest req) {
        logger.error("Timeout", e);
        StandardErrorSpring err = new StandardErrorSpring(System.currentTimeMillis(), HttpStatus.REQUEST_TIMEOUT.value(),
                e.getMessage(), List.of(), "O servidor TEF VINI-TEF-SERVER demorou muito para responder a requisição", req.getRequestURI());

        return ResponseEntity.status(HttpStatus.REQUEST_TIMEOUT).body(err);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<StandardErrorSpring> objectNotFound(HttpMessageNotReadableException e,
                                                              HttpServletRequest req) {
        logger.error("Falha interna não esperada :: ", e);
        StandardErrorSpring err = new StandardErrorSpring(System.currentTimeMillis(),
                HttpStatus.UNPROCESSABLE_ENTITY.value(), "Propriedade não reconhecida", List.of(),
                getPropertyMessageError(e), req.getRequestURI());

        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(err);
    }

    private String getPropertyMessageError(HttpMessageNotReadableException e) {
        String message = e.getMostSpecificCause().getMessage();
        message = message.substring(message.indexOf(" \"") + 2);
        message = message.substring(0, message.indexOf("\" "));
        return String.format("A propriedade '%s' não é reconhecida", message);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<StandardErrorSpring> methodArgumentNotValidException(MethodArgumentNotValidException e,
                                                                               HttpServletRequest req) {
        logger.error("Erro de validação :: ", e);
        StandardErrorSpring err = new StandardErrorSpring(System.currentTimeMillis(),
                HttpStatus.UNPROCESSABLE_ENTITY.value(), "Erro de validação", getValidations(e),
                "Erro de validação", req.getRequestURI());

        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(err);
    }

    private List<ErrorItem> getValidations(MethodArgumentNotValidException e) {
        List<ErrorItem> errors = new ArrayList<>();
        for (FieldError error : e.getBindingResult().getFieldErrors()) {
            errors.add(new ErrorItem(error.getField(), error.getDefaultMessage()));
        }
        return errors;
    }

}