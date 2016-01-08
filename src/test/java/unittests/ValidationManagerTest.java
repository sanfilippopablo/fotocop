package unittests;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import common.ValidationManager;

public class ValidationManagerTest {
	
	ValidationManager vm;
	
	@Before
	public void setUp() {
		vm = new ValidationManager();
	}

	@Test
	public void testAddError() {
		
		vm.addError("hola", "isEmpty");
		assertNotNull("Debería haber un error con campo hola", vm.getErrors().get("hola"));
		
		vm.addError("field", "randomlySelectedErrorType");
		assertNotNull("Debería haber un error con ese campo igual", vm.getErrors().get("field"));
		
		vm.addError("hola", "tooLong");
		assertEquals("El error en el campo debería ser el primero insertado", "Este campo no puede estar vacío.", vm.errorForField("hola"));
		
	}
	
	@Test
	public void testAddCustomError() {
		
		vm.addCustomError("hola2", "Custom Error");
		assertNotNull("Debería haber un error con campo hola2", vm.getErrors().get("hola2"));
		assertEquals("El error en ese campo debería tener el mensaje seteado", vm.getErrors().get("hola2"), "Custom Error");
		
	}
	
	@Test
	public void testIsValid() {
		
		assertTrue("Sin errores, isValid debería devolver True", vm.isValid());
		
		vm.addError("test", "isEmpty");
		assertFalse("Con errores, isValid debería devolver False", vm.isValid());
		
	}
	
	@Test
	public void testFieldHasError() {
		
		assertFalse("Campo hola no debería tener errores", vm.fieldHasError("hola"));
		
		vm.addError("hola", "isEmpty");
		assertTrue("Campo hola debe tener error ahora", vm.fieldHasError("hola"));
		
	}
	
	@Test
	public void testErrorForField() {
		
		assertEquals("Campo hola no debería tener errores", vm.errorForField("hola"), "");
		
		vm.addError("hola", "isEmpty");
		assertNotNull("Campo hola debe tener error ahora", vm.errorForField("hola"));
		
	}
	
	@Test
	public void testValidateNotEmpty() {
		
		vm.validateNotEmpty("hola", "Hey");
		assertFalse("No debería haber errores en hola", vm.fieldHasError("hola"));
		
		vm.validateNotEmpty("hola", "");
		assertTrue("Debería haber errores en hola", vm.fieldHasError("hola"));	
		
	}
	
	@Test
	public void testValidateMinLength() {
		
		vm.validateMinLength("hola", "Heyheyhey", 4);
		assertFalse("No debería haber errores en hola", vm.fieldHasError("hola"));
		
		vm.validateMinLength("hola", "as", 4);
		assertTrue("Debería haber errores en hola", vm.fieldHasError("hola"));	
		
	}
	
	@Test
	public void testValidateMaxLength() {
		
		vm.validateMaxLength("hola", "Heyheyhey", 10);
		assertFalse("No debería haber errores en hola", vm.fieldHasError("hola"));
		
		vm.validateMaxLength("hola", "asaawfawgewgergreg", 10);
		assertTrue("Debería haber errores en hola", vm.fieldHasError("hola"));	
		
	}
	
	@Test
	public void testValidateEmail() {
		
		vm.validateEmail("hola", "hola@gmail.com");
		assertFalse("No debería haber errores en hola", vm.fieldHasError("hola"));
		
		vm.validateEmail("hola", "asaawfawgewgergreg");
		assertTrue("Debería haber errores en hola", vm.fieldHasError("hola"));	
		
	}

}
