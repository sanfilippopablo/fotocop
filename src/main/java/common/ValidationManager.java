package common;

import java.util.HashMap;
import java.util.Map;

/**
 * Helper Class para cuestiones de validación. Es un pequeño wrapper around Map.
 * Provee métodos útles para llevar registros de errores en diferentes campos.
 * El caso de uso normal es durante la validación de un formulario ir registrando
 * los errores en una instancia de esta clase y luego, en el JSP, mostrar los errores
 * para cada campo.
 */
public class ValidationManager {
	
	private static Map<String, String> defaultMessages = new HashMap<String, String>();
	
	static {
		defaultMessages.put("isEmpty", "Este campo no puede estar vacío.");
		defaultMessages.put("unknownError", "Error desconocido.");
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
		errors.put(field, defaultMessages.get(errorType));
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
		errors.put(field, errorMessage);
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

}
