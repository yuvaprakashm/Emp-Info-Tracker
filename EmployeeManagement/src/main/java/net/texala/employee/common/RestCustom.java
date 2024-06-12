package net.texala.employee.common;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class RestCustom {

	private String cause;

	public void setCause(Exception cause) {
		this.cause = ExceptionUtility.setMessage(cause);
	}

}