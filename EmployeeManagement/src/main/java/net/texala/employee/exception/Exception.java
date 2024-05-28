package net.texala.employee.exception;

public class Exception {

	public static class ServiceException extends RuntimeException {

		public ServiceException(String message) {
			super(message);
		}

	}

//	public static class DepartmentNotFoundException extends RuntimeException {
//
//		public DepartmentNotFoundException(String message) {
//			super(message);
//		}
//	}
//
//	public static class AddressNotFoundException extends RuntimeException {
//		public AddressNotFoundException(String message) {
//			super(message);
//		}
//	}
}
