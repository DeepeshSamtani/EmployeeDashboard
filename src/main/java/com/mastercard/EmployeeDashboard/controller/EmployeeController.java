package com.mastercard.EmployeeDashboard.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.mastercard.EmployeeDashboard.entity.Employee;
import com.mastercard.EmployeeDashboard.service.EmployeeService;
import com.mastercard.EmployeeDashboard.vo.EmployeeDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.text.ParseException;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/employee")
@CrossOrigin(origins = "http://localhost:4200/")
public class EmployeeController {
    @Autowired
    private EmployeeService employeeService;

    @Autowired
    @GetMapping("/")
    @ResponseStatus(HttpStatus.OK)
    public List<EmployeeDto> getAllEmployees() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        return employeeService.getAllEmployees().stream().
                map(emp -> EmployeeDto.builder()
                        .id(emp.getId())
                        .fname(emp.getFname())
                        .lname(emp.getLname())
                        .manager(emp.getManager())
                        .salary(emp.getSalary())
                        .dateOfJoining(emp.getDateOfJoining())
                        .emailId(emp.getEmailId())
                        .department(emp.getDepartment())
                        .designation(emp.getDesignation())
                        .dateOfLeaving(emp.getDateOfLeaving())
                        .build())
                .collect(Collectors.toList());
    }

    @GetMapping("/current")
    public List<Employee> getAllCurrentEmployees() {
        return employeeService.getAllCurrentEmployees();
    }

    @PatchMapping("/delete/{id}")
    public void deleteEmployee(@PathVariable String id) {
        employeeService.deleteEmployeeById(Long.valueOf(id));
    }

    @PostMapping("/save")
    public ResponseEntity<String> saveEmployee(@RequestBody @Valid Employee employee) {
        try {
            employeeService.saveEmployee(employee);
        } catch (Exception ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(HttpStatus.OK);

    }

    @GetMapping("/{id}")
    public Employee getEmployeeById(@PathVariable Long id) {
        Employee employee = employeeService.getEmployeeById(id);
        if (employee == null) {
            return null;
        }
        return employee;
    }

    @GetMapping("/check/{id}")
    public ResponseEntity<Boolean> checkEmployee(@PathVariable String id) {
        boolean employeeExists = employeeService.checkEmployeeExists(id);
        return new ResponseEntity<>(employeeExists, HttpStatus.OK);
    }

    @GetMapping("/joined")
    public ResponseEntity<List<Employee>> getEmployeeJoinedLastQuarter() throws ParseException {
        List<Employee> employeesList = employeeService.getEmployeeJoinedLastQuarter();
        return new ResponseEntity<List<Employee>>(employeesList, HttpStatus.OK);
    }

    @GetMapping("/left")
    public ResponseEntity<List<Employee>> getEmployeeLeftLastQuarter() throws ParseException {
        List<Employee> employeeList = employeeService.fetchEmployeesLeftlastQuarter();
        return new ResponseEntity<List<Employee>>(employeeList, HttpStatus.OK);
    }

    @GetMapping("/leftAll")
    public ResponseEntity<List<Employee>> getEmployeeLeft() {
        List<Employee> employeeList = employeeService.fetchEmployeesLeft();
        return new ResponseEntity<List<Employee>>(employeeList, HttpStatus.OK);
    }

    @GetMapping("/employeeSalaryGreater")
    public ResponseEntity<List<Employee>> fetchEmployeeWithHigherSalary() {
        List<Employee> employeeList = employeeService.fetchEmployeeWithHigherSalaryFromManager();
        return new ResponseEntity<List<Employee>>(employeeList, HttpStatus.OK);
    }
}
