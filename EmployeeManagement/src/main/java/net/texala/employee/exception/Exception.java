package net.texala.employee.exception;

public class Exception {

	public static class ServiceException extends RuntimeException {

		public ServiceException(String message) {
			super(message);
		}

	}

}
