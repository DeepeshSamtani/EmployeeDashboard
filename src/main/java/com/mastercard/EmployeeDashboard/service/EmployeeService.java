package com.mastercard.EmployeeDashboard.service;

import com.mastercard.EmployeeDashboard.entity.Employee;
import com.mastercard.EmployeeDashboard.vo.EmployeeDto;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

public interface EmployeeService {
    List<Employee> getAllEmployees();

    void saveEmployee(Employee employee) ;

    Employee getEmployeeById(long id);

    void deleteEmployeeById(long id);

    List<Employee> getEmployeeJoinedLastQuarter() throws ParseException;

    List<Employee> fetchEmployeesLeftlastQuarter() throws ParseException;

    List<Employee> fetchEmployeesLeft();

    List<Employee> fetchEmployeeWithHigherSalaryFromManager();

    List<Employee> getAllCurrentEmployees();

    boolean checkEmployeeExists(String id);
}
