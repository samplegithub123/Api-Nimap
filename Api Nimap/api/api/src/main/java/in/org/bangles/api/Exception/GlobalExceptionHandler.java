package in.org.bangles.api.Exception;

import java.util.HashMap;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class GlobalExceptionHandler {

	// handling specific exception
	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<?> resourceNotFoundHandling(ResourceNotFoundException exception, WebRequest request){
		ErrorDetails errorDetails = 
				new ErrorDetails(exception.getMessage(), false);
		return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
	}

	// handling global exception	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<?> globalExceptionHandling(Exception exception, WebRequest request){
		ErrorDetails errorDetails = 
				new ErrorDetails(exception.getMessage(), true);
		return new ResponseEntity<>(errorDetails, HttpStatus.OK);
	}
	
	//handling custom validation error	
//	@ExceptionHandler(MethodArgumentNotValidException.class)
//	public ResponseEntity<?> custumValidationErrorHanding(MethodArgumentNotValidException exception){
//		ErrorDetails errorDetails = 
//				new ErrorDetails(new Date(), "Validation Error", exception.getBindingResult().getFieldError().getDefaultMessage());
//		return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
//	}
	
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<Map<String, String>> handleMethodArgsNotValidException(MethodArgumentNotValidException ex){
		Map<String, String> resp = new HashMap<>();
		ex.getBindingResult().getAllErrors().forEach((error) -> {
			String fieldName = ((FieldError) error).getField();
			String message = error.getDefaultMessage();
			resp.put(fieldName, message);
		});
		
		return new ResponseEntity<Map<String, String>>(resp, HttpStatus.BAD_REQUEST);
	}
}
