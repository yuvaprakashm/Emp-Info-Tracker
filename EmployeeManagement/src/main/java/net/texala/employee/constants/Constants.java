package net.texala.employee.constants;

public class Constants {
	public static final String ADDRESS_MASTER = "address_master";
	public static final String ID = "id";
	public static final String STREET = "street";
	public static final String CITY = "city";
	public static final String STATE = "state";
	public static final String ZIPCODE = "zipcode";
	public static final String STATUS = "status";
	public static final String TIMESTAMP = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP";
	public static final String UPDATE_ADDRESS_STATUS = "update Address a set a.status=:status where a.id=:id";
	public static final String ADDRESS_NOT_FOUND = "Address not found with id: ";
	public static final String[] HEADER = { "ID", "STREET", "CITY", "STATE", "ZIPCODE", "STATUS", "CREATEDDATE" };
	public static final String STREET_REQUIRED = "Street is required";
	public static final String CITY_REQUIRED = "City is required";
	public static final String STATE_REQUIRED = "State is required";
	public static final String ZIPCODE_REQUIRED = "Zipcode is required";
	public static final String STATUS_REQUIRED = "Status is required";
	public static final int MAX_STREET_LENGTH = 100;
	public static final String STREET_LENGTH_ERROR_MESSAGE = "Street length must be less than or equal to 100 characters";
	public static final String CITY_LENGTH_ERROR_MESSAGE = "City must contain only alphabetic characters";
	public static final String STATE_LENGTH_ERROR_MESSAGE = "State must contain only alphabetic characters";
	public static final String ZIPCODE_LENGTH_ERROR_MESSAGE = "Zipcode must be 6 digits";
	public static final String REGEX = "^[a-zA-Z]+$";
	public static final String ZIPCODE_REGEX = "\\d{6}";
	public static final String RECORD_FETCH_SUCCESS_MESSAGE = "Record fetch Succesfully";
	public static final String RECORD_UPDATE_SUCCESS_MESSAGE = "Record update Succesfully";
	public static final String RECORD_ACTIVE_SUCCESS_MESSAGE = "Record activate Succesfully";
	public static final String RECORD_DEACTIVE_SUCCESS_MESSAGE = "Record deactivate Succesfully";
	public static final String RECORD_DELETED_SUCCESS_MESSAGE = "Record Deleted Succesfully";
	public static final String NO_RECORD_FOUND_MESSAGE = "No record Found";
	public static final String RECORD_ADD_SUCCESS_MESSAGE = "Record add Succesfully";
	public static final String PAGE_NO = "pageNo";
	public static final String PAGE_SIZE = "pageSize";
	public static final String SORT_BY = "sortBy";
	public static final String FILTER_BY = "filterBy";
	public static final String SEARCH_TEXT ="searchText";

}
