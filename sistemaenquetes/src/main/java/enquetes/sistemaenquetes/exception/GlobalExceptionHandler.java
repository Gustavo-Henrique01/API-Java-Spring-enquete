package enquetes.sistemaenquetes.exception;


import java.util.HashMap;
import java.util.Map;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.http.HttpStatus;

@RestControllerAdvice
public class GlobalExceptionHandler {
	
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public Map<String, String> tratamentoExcecaoValidacao(MethodArgumentNotValidException ex) {
		Map<String, String> errors = new HashMap<>();
		ex.getBindingResult().getAllErrors().forEach((error) -> {
			String atributo = ((FieldError) error).getField();
			String mensagem = error.getDefaultMessage();
			errors.put(atributo, mensagem);
		});
		return errors;
	}
	
	@ResponseStatus(HttpStatus.NOT_FOUND)
	@ExceptionHandler(ResourceNotFoundException.class)
	public Map<String, String> ResourceNotFoundException (ResourceNotFoundException ex){
		Map<String, String> errosMap = new HashMap<String, String>();
		errosMap.put("mensagem", ex.getMessage());
		return errosMap;
	}
	@ResponseStatus(HttpStatus.NOT_FOUND)
	@ExceptionHandler(UserAlreadyVotedException.class)
	public Map<String, String> UserAlreadyVotedException (UserAlreadyVotedException  ex){
		Map<String, String> errosMap = new HashMap<String, String>();
		errosMap.put("mensagem", ex.getMessage());
		return errosMap;
	}
	@ResponseStatus(HttpStatus.NOT_FOUND)
	@ExceptionHandler(InvalidPollDateException.class)
	public Map<String, String> InvalidPollDateException (InvalidPollDateException  ex){
		Map<String, String> errosMap = new HashMap<String, String>();
		errosMap.put("mensagem", ex.getMessage());
		return errosMap;
	}
	@ResponseStatus(HttpStatus.NOT_FOUND)
	@ExceptionHandler(PollNotActiveException.class)
	public Map<String, String> PollNotActiveException (PollNotActiveException  ex){
		Map<String, String> errosMap = new HashMap<String, String>();
		errosMap.put("mensagem", ex.getMessage());
		return errosMap;
	}
	
	@ResponseStatus(HttpStatus.NOT_FOUND)
	@ExceptionHandler(UsernameAlreadyExistsException.class)
	public Map<String, String> UsernameAlreadyExistsException (UsernameAlreadyExistsException  ex){
		Map<String, String> errosMap = new HashMap<String, String>();
		errosMap.put("mensagem", ex.getMessage());
		return errosMap;
	}

}
