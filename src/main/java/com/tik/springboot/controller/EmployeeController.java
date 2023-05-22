package com.tik.springboot.controller;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tik.springboot.exception.ResourceNotFoundException;
import com.tik.springboot.model.Employee;
import com.tik.springboot.repository.EmployeeRepository;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/v1/")
public class EmployeeController {
	
	
	@Autowired
	private EmployeeRepository employeeRepository;
	
	

	//get all employees
	@GetMapping("/employees")
	public List<Employee> getAllEmployees(){
		return employeeRepository.findAll();
	}
	
	
	//Add an employee
	@PostMapping("/employees")
	//It will return a employer object as a JSON
	public Employee addEmployee(@RequestBody Employee emp) {
		return employeeRepository.save(emp);
	}
	
	//get employee by id
	@GetMapping("/employees/{id}")
	public ResponseEntity<Employee> getEmployeeById(@PathVariable Long id) {
		Employee emp = employeeRepository.findById(id).orElseThrow(
				() -> new ResourceNotFoundException("Employee with id " + id +" is not exist ll asaf"));
		
		return ResponseEntity.ok(emp);
	}
	
	
	
	
	//update employee
	@PutMapping("/employees/{id}")
	public ResponseEntity<Employee> updateEmployee(@PathVariable Long id ,@RequestBody Employee emp){
		
		Employee employee = employeeRepository.findById(id).orElseThrow(
				() -> new ResourceNotFoundException("Employee with id " + id +" is not exist ll asaf"));
		employee.setFirstName(emp.getFirstName());
		employee.setLastName(emp.getLastName());
		employee.setEmailId(emp.getEmailId());
		
		Employee updatedEmployee =  employeeRepository.save(employee);
		return ResponseEntity.ok(updatedEmployee);
		
	}
	
	//delete employee
	@DeleteMapping("/employees/{id}")
	public ResponseEntity<Map<String, Boolean>> deleteEmployee(@PathVariable Long id){
		Employee employee = employeeRepository.findById(id).orElseThrow(
				() -> new ResourceNotFoundException("Employee with id " + id + " is not exist ll asaf"));
	
		
		employeeRepository.delete(employee);
		Map<String , Boolean> response = new HashMap<>();
		response.put("Emplyee Deleted Successfully --> ", Boolean.TRUE);
		return ResponseEntity.ok(response);
	}
	
	
	
	
	
	
	
}
