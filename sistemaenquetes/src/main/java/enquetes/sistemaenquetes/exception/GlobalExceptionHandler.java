package enquetes.sistemaenquetes.exception;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity; 
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus; 
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.userdetails.UsernameNotFoundException; 

@RestControllerAdvice 
public class GlobalExceptionHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST) 
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleResourceNotFoundException(ResourceNotFoundException ex) {
        Map<String, String> errorsMap = new HashMap<>();
        errorsMap.put("message", ex.getMessage());
        return new ResponseEntity<>(errorsMap, HttpStatus.NOT_FOUND); 
    }

 
    @ExceptionHandler(UserAlreadyVotedException.class)
    public ResponseEntity<Map<String, String>> handleUserAlreadyVotedException(UserAlreadyVotedException ex) {
        Map<String, String> errorsMap = new HashMap<>();
        errorsMap.put("message", ex.getMessage());
        return new ResponseEntity<>(errorsMap, HttpStatus.CONFLICT); 
    }

    @ExceptionHandler(InvalidPollDateException.class)
    public ResponseEntity<Map<String, String>> handleInvalidPollDateException(InvalidPollDateException ex) {
        Map<String, String> errorsMap = new HashMap<>();
        errorsMap.put("message", ex.getMessage());
        return new ResponseEntity<>(errorsMap, HttpStatus.BAD_REQUEST); 
    }

    @ExceptionHandler(PollNotActiveException.class)
    public ResponseEntity<Map<String, String>> handlePollNotActiveException(PollNotActiveException ex) {
        Map<String, String> errorsMap = new HashMap<>();
        errorsMap.put("message", ex.getMessage());
        return new ResponseEntity<>(errorsMap, HttpStatus.BAD_REQUEST); 
    }
    @ExceptionHandler(UnauthorizedActionException.class)
    public ResponseEntity<Map<String, String>> handlePollNotActiveException(UnauthorizedActionException ex) {
        Map<String, String> errorsMap = new HashMap<>();
        errorsMap.put("message", ex.getMessage());
        return new ResponseEntity<>(errorsMap, HttpStatus.NOT_FOUND); 
    }

    @ExceptionHandler(UsernameAlreadyExistsException.class)
    public ResponseEntity<Map<String, String>> handleUsernameAlreadyExistsException(UsernameAlreadyExistsException ex) {
        Map<String, String> errorsMap = new HashMap<>();
        errorsMap.put("message", ex.getMessage());
        return new ResponseEntity<>(errorsMap, HttpStatus.CONFLICT); 
    }
    
  
    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleUsernameNotFoundException(UsernameNotFoundException ex) {
        Map<String, String> errorsMap = new HashMap<>();
        errorsMap.put("message", ex.getMessage());
      
        return new ResponseEntity<>(errorsMap, HttpStatus.NOT_FOUND); 
    }

   
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<Map<String, String>> handleAccessDeniedException(AccessDeniedException ex) {
        Map<String, String> errorsMap = new HashMap<>();
        errorsMap.put("message", "Você não tem permissão para acessar este recurso.");
        return new ResponseEntity<>(errorsMap, HttpStatus.FORBIDDEN); 
    }

    
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, String>> handleGenericException(Exception ex) {
        Map<String, String> errorsMap = new HashMap<>();
        errorsMap.put("message", "Ocorreu um erro interno no servidor.");
       
        ex.printStackTrace(); 
        return new ResponseEntity<>(errorsMap, HttpStatus.INTERNAL_SERVER_ERROR); 
    }
}