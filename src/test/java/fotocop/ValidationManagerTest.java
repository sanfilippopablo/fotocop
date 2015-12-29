package fotocop;

import static org.junit.Assert.*;

import org.junit.Test;

import common.ValidationManager;

public class ValidationManagerTest {

	@Test
	public void testAddError() {
		ValidationManager vm = new ValidationManager();
		
		vm.addError("hola", "isEmpty");
		assertNotNull("Debería haber un error con campo hola", vm.getErrors().get("hola"));
		
		vm.addError("field", "randomlySelectedErrorType");
		assertNotNull("Debería haber un error con ese campo igual", vm.getErrors().get("field"));
		
	}
	
	@Test
	public void testAddCustomError() {
		ValidationManager vm = new ValidationManager();
		
		vm.addCustomError("hola2", "Custom Error");
		assertNotNull("Debería haber un error con campo hola2", vm.getErrors().get("hola2"));
		assertEquals("El error en ese campo debería tener el mensaje seteado", vm.getErrors().get("hola2"), "Custom Error");
		
	}
	
	@Test
	public void testIsValid() {
		ValidationManager vm = new ValidationManager();
		
		assertTrue("Sin errores, isValid debería devolver True", vm.isValid());
		
		vm.addError("test", "isEmpty");
		assertFalse("Con errores, isValid debería devolver False", vm.isValid());
		
	}
	
	@Test
	public void testFieldHasError() {
		ValidationManager vm = new ValidationManager();
		
		assertFalse("Campo hola no debería tener errores", vm.fieldHasError("hola"));
		
		vm.addError("hola", "isEmpty");
		assertFalse("Campo hola debe tener error ahora", vm.fieldHasError("hola"));
		
	}
	
	@Test
	public void testErrorForField() {
		ValidationManager vm = new ValidationManager();
		
		assertEquals("Campo hola no debería tener errores", vm.errorForField("hola"), "");
		
		vm.addError("hola", "isEmpty");
		assertNotNull("Campo hola debe tener error ahora", vm.errorForField("hola"));
		
	}

}
