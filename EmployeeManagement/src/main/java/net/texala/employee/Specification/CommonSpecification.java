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
import net.texala.employee.department.model.Department;
import net.texala.employee.model.Employee;

public class CommonSpecification {

	public static Specification<Address> searchAddress(String searchTerm, String filterBy) {
		return (root, query, cb) -> {
			List<Predicate> predicates = new ArrayList<>();
			if (StringUtils.isNotBlank(searchTerm)) {
				String containsLikePattern = Utility.getContainsLikePattern(searchTerm);
				predicates.add(cb.or(cb.like(cb.lower(root.<String>get("street")), containsLikePattern),
						cb.like(cb.lower(root.<String>get("city")), containsLikePattern),
						cb.like(cb.lower(root.<String>get("state")), containsLikePattern),
						cb.like(cb.lower(root.<String>get("zipcode")), containsLikePattern),
						cb.like(cb.lower(root.<String>get("country")), containsLikePattern),
						cb.like(cb.lower(root.<String>get("doorNumber")), containsLikePattern),
						cb.like(cb.lower(root.<String>get("landMark")), containsLikePattern)
						));
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
 
	public static Specification<Department> searchDepartment(String searchTerm, String filterBy) {
	    return (root, query, cb) -> {
	        List<Predicate> predicates = new ArrayList<>();
	        if (StringUtils.isNotBlank(searchTerm)) {
	            String containsLikePattern = Utility.getContainsLikePattern(searchTerm);
	            predicates.add(cb.or(cb.like(cb.lower(root.<String>get("deptName")), containsLikePattern),
	                                 cb.like(cb.lower(root.<String>get("deptContactNumber")), containsLikePattern),
	                                 cb.like(cb.lower(root.<String>get("emailAddress")), containsLikePattern)));
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

	public static Specification<Employee> searchEmployee(String searchTerm, String filterBy) {
		return (root, query, cb) -> {
			List<Predicate> predicates = new ArrayList<>();
			if (StringUtils.isNotBlank(searchTerm)) {              
				String containsLikePattern = Utility.getContainsLikePattern(searchTerm);
				predicates.add(cb.or(cb.like(cb.lower(root.<String>get("firstName")), containsLikePattern),
						cb.like(cb.lower(root.<String>get("lastName")), containsLikePattern),
						cb.like(cb.lower(root.<String>get("jobTitle")), containsLikePattern),
						cb.like(cb.lower(root.<String>get("email")), containsLikePattern),
						cb.like(cb.lower(root.<String>get("contactNumber")), containsLikePattern)));
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
