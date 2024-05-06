package net.texala.employee.rest.custom;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * Wrapped in Rest Entity, contains case of exception to be send as response
 * 
 * @author satyam.kumar
 *
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class RestCustom {

	private String cause;

	public void setCause(Exception cause) {
		this.cause = Utility.setMessage(cause);
	}

}