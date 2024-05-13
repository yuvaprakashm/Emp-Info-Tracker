package net.texala.employee.web.rest;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.opencsv.CSVWriter;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;

import net.texala.employee.model.Employee;
import net.texala.employee.restresponse.RestResponse;
import net.texala.employee.reststatus.RestStatus;
import net.texala.employee.service.EmployeeService;
import net.texala.employee.vo.EmployeeVo;

@RestController
public class EmployeeController {
	@Autowired
	private EmployeeService employeeService;

	@GetMapping("/search")
	public ResponseEntity<RestResponse<Page<EmployeeVo>>> search(
			@RequestParam(name = "pageNo", required = false, defaultValue = "0") Integer pageNo,
			@RequestParam(name = "pageSize", required = false, defaultValue = "" + Integer.MAX_VALUE) Integer pageSize,
			@RequestParam(name = "sortBy", required = false, defaultValue = "createdDate:asc") String sortBy,
			@RequestParam(name = "filterBy", required = false, defaultValue = "") String filterBy,
			@RequestParam(name = "searchText", required = false) String searchText) {

		final RestStatus<?> restStatus = new RestStatus<>(HttpStatus.OK, "Record fetch Succesfully");
		final Page<EmployeeVo> search = employeeService.search(pageNo, pageSize, sortBy, filterBy, searchText);
		final RestResponse<Page<EmployeeVo>> response = new RestResponse<>(search, restStatus);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@GetMapping("/api/employee")
	public ResponseEntity<List<EmployeeVo>> findAll() {
		List<EmployeeVo> employeeVo = employeeService.findAll();
		return ResponseEntity.ok(employeeVo);
	}

	@GetMapping("/api/employee/{id}")
	public ResponseEntity<Employee> findById(@PathVariable(name = "id", required = true) Long id) {
		Employee employee = employeeService.findById(id);
		return ResponseEntity.ok(employee);
	}

	@PostMapping("/add")
	public ResponseEntity<RestResponse<EmployeeVo>> add(@RequestBody(required = true) EmployeeVo employeeVo) {

		RestStatus<?> restStatus = new RestStatus<>(HttpStatus.OK, "Record add Succesfully");
		final RestResponse<EmployeeVo> response = new RestResponse<>(employeeService.add(employeeVo), restStatus);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<RestResponse<Void>> delete(@PathVariable(name = "id", required = true) Long id) {

		RestStatus<?> restStatus = new RestStatus<>(HttpStatus.OK, "Record Deleted Succesfully");
		employeeService.delete(id);
		final RestResponse<Void> response = new RestResponse<>(null, restStatus);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@PutMapping("/{id}")
	public ResponseEntity<RestResponse<EmployeeVo>> update(@PathVariable(name = "id", required = true) Long id,
			@RequestBody(required = true) EmployeeVo employeeVo) {
		RestStatus<?> restStatus = new RestStatus<>(HttpStatus.OK, "Record updated successfully");
		employeeVo.setId(id);
		final RestResponse<EmployeeVo> response = new RestResponse<>(employeeService.update(employeeVo, id, false),
				restStatus);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@PatchMapping("/{id}")
	public ResponseEntity<RestResponse<EmployeeVo>> updatePatch(@PathVariable(name = "id", required = true) Long id,
			@RequestBody(required = true) EmployeeVo employeeVo) {
		RestStatus<?> restStatus = new RestStatus<>(HttpStatus.OK, "Record updated successfully");
		final RestResponse<EmployeeVo> response = new RestResponse<>(employeeService.update(employeeVo, id, true),
				restStatus);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@PatchMapping("/activate/{id}")
	public ResponseEntity<RestResponse<Void>> activate(@PathVariable(name = "id", required = true) Long id) {

		RestStatus<?> restStatus = new RestStatus<>(HttpStatus.OK, "Record activate Succesfully");
		employeeService.active(id);
		final RestResponse<Void> response = new RestResponse<>(null, restStatus);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@PatchMapping("/deactivate/{id}")
	public ResponseEntity<RestResponse<Void>> deactivate(@PathVariable(name = "id", required = true) Long id) {

		RestStatus<?> restStatus = new RestStatus<>(HttpStatus.OK, "Record deactivate Succesfully");
		employeeService.deactive(id);
		final RestResponse<Void> response = new RestResponse<>(null, restStatus);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@PostMapping("/exportData")
	public ResponseEntity<String> exportData(@RequestParam("fileName") String filename)
			throws IOException, CsvException {
		employeeService.save(filename);
		return ResponseEntity.status(HttpStatus.OK).body("Sucess");
	}

//	@GetMapping("/exportcsv")
//	public void downloadCsv(HttpServletResponse response) throws IOException, CsvException {
//	    String fileName = "employee-data.csv";
//	    response.setContentType("text/csv");
//	    response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName + "\"");
//
//	    StatefulBeanToCsv<EmployeeVo> writer = new StatefulBeanToCsvBuilder<EmployeeVo>(response.getWriter())
//	            .withSeparator(CSVWriter.DEFAULT_SEPARATOR)
//	            .withOrderedResults(false)
//	            .build();
//	    
//	    List<EmployeeVo> employeeList = employeeService.findAll();
//	    if (employeeList != null && !employeeList.isEmpty()) {
//	        writer.write(employeeList);
//	    }
//	}
	@GetMapping("/exportcsv")
	public void downloadCsv(HttpServletResponse response) throws IOException, CsvException {
		String fileName = "employee-data.csv";
		response.setContentType("text/csv");
		response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName + "\"");

		List<EmployeeVo> employeeList = employeeService.findAll();
		try (PrintWriter writer = response.getWriter()) {
			StatefulBeanToCsv<Employee> beanToCsv = new StatefulBeanToCsvBuilder<Employee>(writer)
					.withSeparator(CSVWriter.DEFAULT_SEPARATOR).withOrderedResults(false).build();

			beanToCsv.write((Employee) employeeList);
		} catch (CsvDataTypeMismatchException | CsvRequiredFieldEmptyException e) {
			// Handle CSV exception
			e.printStackTrace();
			throw new CsvException("Error writing CSV");
		}
	}
}
