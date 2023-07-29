package com.simplesystem.todo.exception;

import com.simplesystem.todo.TodoController;
import com.simplesystem.todo.exception.model.ErrorResponse;
import com.simplesystem.todo.exception.model.ValidationErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@Slf4j
@ControllerAdvice(assignableTypes = {TodoController.class})
public class TodoControllerAdvice {
    @ExceptionHandler({RuntimeException.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public ErrorResponse onRunTimeException(final RuntimeException exception) {
        log.error(exception.getMessage());

        return new ErrorResponse(exception.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ValidationErrorResponse onMethodArgumentNotValidException(final MethodArgumentNotValidException exception) {
        final var validationErrorResponse = new ValidationErrorResponse();
        exception.getBindingResult()
                .getFieldErrors()
                .forEach(fieldError -> validationErrorResponse.getViolations()
                        .add(new ValidationErrorResponse.Violation(fieldError.getField(), fieldError.getDefaultMessage())));
        return validationErrorResponse;
    }
}

