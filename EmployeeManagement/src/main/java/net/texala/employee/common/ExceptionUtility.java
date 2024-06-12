package net.texala.employee.common;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

public class ExceptionUtility  {
	public static String setMessage(Exception e) {

		final ByteArrayOutputStream baos = new ByteArrayOutputStream();

		final PrintStream ps = new PrintStream(baos);

		if (e != null) {

			e.printStackTrace(ps);

		}

		ps.close();

		return baos.toString();

	}
}
