package net.texala.employee.address.web.rest;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import net.texala.employee.address.model.Address;
import net.texala.employee.address.service.AddressService;
import net.texala.employee.address.vo.AddressVo;
import net.texala.employee.restresponse.RestResponse;
import net.texala.employee.reststatus.RestStatus;
import net.texala.employee.vo.EmployeeVo;

import static net.texala.employee.constants.Constants.*;

@RestController
@RequestMapping("/add")
@AllArgsConstructor
public class AddressController {
    
    private AddressService addressService;

    @GetMapping("/search")
    public ResponseEntity<RestResponse<Page<AddressVo>>> search(
            @RequestParam(name = PAGE_NO, required = false, defaultValue = "0") Integer pageNo,
            @RequestParam(name = PAGE_SIZE, required = false, defaultValue = "" + Integer.MAX_VALUE) Integer pageSize,
            @RequestParam(name = SORT_BY, required = false, defaultValue = "createdDate:asc") String sortBy,
            @RequestParam(name = FILTER_BY, required = false, defaultValue = "") String filterBy,
            @RequestParam(name = SEARCH_TEXT, required = false) String searchText) {
        final RestStatus<?> restStatus = new RestStatus<>(HttpStatus.OK, RECORD_FETCH_SUCCESS_MESSAGE);
        final Page<AddressVo> search = addressService.search(pageNo, pageSize, sortBy, filterBy, searchText);
        final RestResponse<Page<AddressVo>> response = new RestResponse<>(search, restStatus);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/records")
    public ResponseEntity<RestResponse<List<AddressVo>>> findAll() {

        RestStatus<List<AddressVo>> restStatus = new RestStatus<>(HttpStatus.OK, RECORD_FETCH_SUCCESS_MESSAGE);
        List<AddressVo> list = addressService.findAll();
        if (CollectionUtils.isEmpty(list))
            restStatus = new RestStatus<>(HttpStatus.OK, NO_RECORD_FOUND_MESSAGE);
        final RestResponse<List<AddressVo>> response = new RestResponse<>(list, restStatus);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/records/{id}")
    public ResponseEntity<RestResponse<Address>> findById(@PathVariable(name = "id", required = true) Long id) {
        RestStatus<Address> restStatus = new RestStatus<>(HttpStatus.OK, RECORD_FETCH_SUCCESS_MESSAGE);
        Address address = addressService.findById(id);
        final RestResponse<Address> response = new RestResponse<>(address, restStatus);
        return new ResponseEntity<>(response, HttpStatus.OK);    
    }

    @PostMapping("/records")
    public ResponseEntity<RestResponse<AddressVo>> add(@RequestBody(required = true) AddressVo addressVo) {
    	Long employeeId = addressVo.getId();
    	addressVo.setId(employeeId);
        RestStatus<?> restStatus = new RestStatus<>(HttpStatus.OK, RECORD_ADD_SUCCESS_MESSAGE);
        final RestResponse<AddressVo> response = new RestResponse<>(addressService.add(addressVo), restStatus);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/records/{id}")
    public ResponseEntity<RestResponse<AddressVo>> update(@PathVariable(name = "id", required = true) Long id,
            @RequestBody(required = true) AddressVo addressVo) {
    	addressVo.setId(id);
    	AddressVo updatedAddress = addressService.update(addressVo, id, false);
        RestStatus<?> restStatus = new RestStatus<>(HttpStatus.OK, RECORD_UPDATE_SUCCESS_MESSAGE);
        final RestResponse<AddressVo> response = new RestResponse<>(updatedAddress,
                restStatus);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
  
    @PatchMapping("/records/{id}")
    public ResponseEntity<RestResponse<AddressVo>> updatePatch(@PathVariable(name = "id", required = true) Long id,
            @RequestBody(required = true) AddressVo addressVo) {
        RestStatus<?> restStatus = new RestStatus<>(HttpStatus.OK, RECORD_UPDATE_SUCCESS_MESSAGE);
        final RestResponse<AddressVo> response = new RestResponse<>(addressService.update(addressVo, id, true),
                restStatus);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PatchMapping("/records/{id}/activate")
    public ResponseEntity<RestResponse<Void>> activate(@PathVariable(name = "id", required = true) Long id) {
        RestStatus<?> restStatus = new RestStatus<>(HttpStatus.OK, RECORD_ACTIVE_SUCCESS_MESSAGE);
        addressService.active(id);
        final RestResponse<Void> response = new RestResponse<>(null, restStatus);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PatchMapping("/records/{id}/deactivate")
    public ResponseEntity<RestResponse<Void>> deactivate(@PathVariable(name = "id", required = true) Long id) {
        RestStatus<?> restStatus = new RestStatus<>(HttpStatus.OK, RECORD_DEACTIVE_SUCCESS_MESSAGE);
        addressService.deactive(id);
        final RestResponse<Void> response = new RestResponse<>(null, restStatus);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/records/{id}")
    public ResponseEntity<RestResponse<Void>> delete(@PathVariable(name = "id", required = true) Long id) {
        RestStatus<?> restStatus = new RestStatus<>(HttpStatus.OK, RECORD_DELETED_SUCCESS_MESSAGE);
        addressService.delete(id);
        final RestResponse<Void> response = new RestResponse<>(null, restStatus);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/download")
	public ResponseEntity<ByteArrayResource> downloadCsv() {
		String csvContent = addressService.generateCsvContent();
		ByteArrayResource resource = new ByteArrayResource(csvContent.getBytes());
		HttpHeaders headers = new HttpHeaders();
		headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"address-data.csv\"");
		return ResponseEntity.ok().headers(headers).contentType(MediaType.parseMediaType("text/csv")).body(resource);
	}
}
