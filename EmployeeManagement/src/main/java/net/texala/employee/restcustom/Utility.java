package net.texala.employee.restcustom;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

public class Utility {
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
