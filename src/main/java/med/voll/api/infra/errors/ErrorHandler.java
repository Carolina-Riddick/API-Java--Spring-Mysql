package med.voll.api.infra.errors;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import jakarta.persistence.EntityNotFoundException;

@RestControllerAdvice
public class ErrorHandler {
	
	@ExceptionHandler(EntityNotFoundException.class)
	public ResponseEntity errorHandler404() {
		return ResponseEntity.notFound().build();
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity errorHandler400(MethodArgumentNotValidException ex) {
		var errors = ex.getFieldErrors().stream().map(ErrorValidation::new).toList();
		return ResponseEntity.badRequest().body(errors);
	}
	
	private record ErrorValidation(String field, String error ) {
		public ErrorValidation(FieldError error) {
			this(error.getField(), error.getDefaultMessage());
		}
	}
}