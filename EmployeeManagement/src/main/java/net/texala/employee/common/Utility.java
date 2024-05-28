package net.texala.employee.common;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@UtilityClass
public class Utility {

	public static List<Order> sortByValues(String sortBy) {
		List<Order> orders = new ArrayList<>();
		String[] sortByArr = sortBy.split(",");
		for (String filterByArr : sortByArr) {
			String[] tempSortByArr = filterByArr.split(":", 2);
			orders.add(new Order(tempSortByArr.length > 1
					? (tempSortByArr[1].equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC)
					: Sort.Direction.DESC, tempSortByArr[0]));
		}
		return orders;
	}

	public static String getContainsLikePattern(String searchTerm) {
		if (StringUtils.isBlank(searchTerm))
			return "%";
		else {
			return "%" + searchTerm.toLowerCase() + "%";
		}
	}

	public static HashMap<String, String> prepareFilterByMap(String filterBy) {
		HashMap<String, String> filterByMap = new HashMap<>();
		String[] filterByArrs = filterBy.split(";");
		for (String filterByArr : filterByArrs) {
			String[] tempFilterByArr = filterByArr.split(":", 2);
			filterByMap.put(tempFilterByArr[0], tempFilterByArr[1]);
		}
		return filterByMap;
	}
}
