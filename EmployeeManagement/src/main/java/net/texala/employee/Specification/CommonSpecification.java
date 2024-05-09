package net.texala.employee.Specification;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.criteria.Predicate;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;

import net.texala.employee.Util.Utility;
import net.texala.employee.address.model.Address;

public class CommonSpecification {

	public static Specification<Address> searchAddress(String searchTerm, String filterBy) {
		return (root, query, cb) -> {
			List<Predicate> predicates = new ArrayList<>();
			if (StringUtils.isNotBlank(searchTerm)) {
				String containsLikePattern = Utility.getContainsLikePattern(searchTerm);
				predicates.add(cb.or(cb.like(cb.lower(root.<String>get("street")), containsLikePattern),
						cb.like(cb.lower(root.<String>get("city")), containsLikePattern),
						cb.like(cb.lower(root.<String>get("state")), containsLikePattern),
						cb.like(cb.lower(root.<String>get("zipcode")), containsLikePattern)));
			}

			if (StringUtils.isNotBlank(filterBy)) {
				HashMap<String, String> filterByMap = Utility.prepareFilterByMap(filterBy);
				for (Map.Entry<String, String> filterByEntry : filterByMap.entrySet()) {
					predicates.add(cb.equal(root.<String>get(filterByEntry.getKey()).as(String.class),
							filterByEntry.getValue()));
				}
			}
			return cb.and(predicates.toArray(new Predicate[0]));
		};
	}
}
