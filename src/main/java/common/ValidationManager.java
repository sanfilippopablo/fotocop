package common;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * Clase de ayuda para cuestiones de validación. Es un pequeño envoltorio sobre Map.
 * Provee métodos útles para llevar registros de errores en diferentes campos.
 * El caso de uso normal es durante la validación de un formulario ir registrando
 * los errores en una instancia de esta clase y luego, en el JSP, mostrar los errores
 * para cada campo.
 * 
 * ValidationManager NO soporta campos con múltiples errores. Cuando más de un error
 * se agrega a un campo, el error que se mantiene es el primero que se agregó.
 * 
 * Los errores que no pertenecen a ningún campo se guardan en el campo *misc*.
 */
public class ValidationManager {
	
	private static Map<String, String> defaultMessages = new HashMap<String, String>();
	
	static {
		defaultMessages.put("isEmpty", "Este campo no puede estar vacío.");
		defaultMessages.put("unknownError", "Error desconocido.");
		defaultMessages.put("tooShort", "El valor ingresado es demasiado corto.");
		defaultMessages.put("tooLong", "El valor ingresado es demasiado largo.");
		defaultMessages.put("invalidEmail", "El valor ingresado no es una dirección de e-mail válida.");
	}
	
	Map<String, String> errors = new HashMap<String, String>();
	
	/**
	 * Agrega un nuevo error al registro de errores.
	 * El mensaje de error es determinado por el parámetro
	 * errorType, buscando en una tabla de mensajes
	 * de error.
	 * 
	 * @param field Campo que contiene el error.
	 * @param errorType Tipo de error.
	 */
	public void addError(String field, String errorType) {
		if (!defaultMessages.containsKey(errorType)) {
			errorType = "unknownError";
		}
		if (!errors.containsKey(field)) {
			errors.put(field, defaultMessages.get(errorType));
		}
	}
	
	/**
	 * Agrega un nuevo error al registro de errores.
	 * La diferencia con addError es que el mensaje de
	 * error es determinado directamente por el parámetro
	 * errorMessage. Usar este para mensajes de error muy
	 * raros o únicos.
	 * 
	 * @param field
	 * @param errorMessage
	 */
	public void addCustomError(String field, String errorMessage) {
		if (!errors.containsKey(field)) {
			errors.put(field, errorMessage);
		}
	}
	
	/**
	 * Checkea si no hay errores en el ValidationManager.
	 * 
	 * @return booleano que indica si es válido.
	 */
	public boolean isValid() {
		return errors.isEmpty();
	}
	
	/**
	 *
	 * @return Map<String,String> que contiene
	 * los campos con error como key y el mensaje
	 * de error como value.
	 */
	public Map<String,String> getErrors() {
		return this.errors;
	}
	
	
	/**
	 * Checkea si el campo tiene errores o no.
	 * Usar este método al renderizar cada campo
	 * en un formulario para saber si agregar o no
	 * error al campo.
	 * @param field Nombre del campo
	 * @return boolean que indica si tiene error
	 */
	public boolean fieldHasError(String field) {
		return this.errors.containsKey(field);
	}
	
	/**
	 * Devuelve el mensaje de error para el campo.
	 * Si el campo no tiene errores, devuelve una cadena
	 * vacía. Usar este método al renderizar cada campo
	 * para mostrar el mensaje de error del campo.
	 * 
	 * @param field Nombre del campo
	 * @return El mensaje de error
	 */
	public String errorForField(String field) {
		if (fieldHasError(field)) {
			return this.errors.get(field);
		}
		else {
			return "";
		}
	}
	
	/**
	 * Valida que el valor pasado en value no es vacío.
	 * Si resulta que lo es, Se agrega al validationManager
	 * un error del tipo isEmpty con el fieldname recibido.
	 * 
	 * @param field Nombre del campo
	 * @param value Valor a probar
	 */
	public boolean validateNotEmpty(String field, String value) {
		if (value.isEmpty()) {
			this.addError(field, "isEmpty");
			return false;
		}
		return true;
	}
	
	/**
	 * Valida que el valor pasado en value supera la longitud mínima.
	 * Si resulta que el valor es inválido, Se agrega al validationManager
	 * un error del tipo **tooShort** con el fieldname recibido.
	 * 
	 * @param field Nombre del campo
	 * @param value Valor a probar
	 * @param minLength
	 */
	public boolean validateMinLength(String field, String value, int minLength) {
		if (value.length() < minLength) {
			this.addError(field, "tooShort");
			return false;
		}
		return true;
	}
	
	/**
	 * Valida que el valor pasado en value no supera la longitud máxima.
	 * Si resulta que el valor es inválido, Se agrega al validationManager
	 * un error del tipo **tooLong** con el fieldname recibido.
	 * 
	 * @param field Nombre del campo
	 * @param value Valor a probar
	 * @param maxLength
	 */
	public boolean validateMaxLength(String field, String value, int maxLength) {
		if (value.length() > maxLength) {
			this.addError(field, "tooShort");
			return false;
		}
		return true;
	}
	
	/**
	 * Valida que el valor pasado en value es un e-mail válido.
	 * Si resulta que el valor es inválido, Se agrega al validationManager
	 * un error del tipo **invalidEmail** con el fieldname recibido.
	 * 
	 * @param field Nombre del campo
	 * @param value Valor a probar
	 */
	public boolean validateEmail(String field, String value) {
        java.util.regex.Pattern p = java.util.regex.Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
        java.util.regex.Matcher m = p.matcher(value);
        if (!m.matches()) {
			this.addError(field, "invalidEmail");
			return false;
		}
		return true;
	}

}
