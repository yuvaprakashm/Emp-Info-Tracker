package net.texala.employee.exception;

public class Exception {

	public static class EmployeeNotFoundException extends RuntimeException {

		public EmployeeNotFoundException(String message) {
			super(message);
		}

	}

	public static class DepartmentNotFoundException extends RuntimeException {

		public DepartmentNotFoundException(String message) {
			super(message);
		}
	}

	public static class AddressNotFoundException extends RuntimeException {
		public AddressNotFoundException(String message) {
			super(message);
		}
	}
}
