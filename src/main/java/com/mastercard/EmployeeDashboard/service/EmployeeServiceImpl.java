package com.mastercard.EmployeeDashboard.service;

import com.mastercard.EmployeeDashboard.dao.EmployeeRepository;
import com.mastercard.EmployeeDashboard.entity.Employee;
import com.mastercard.EmployeeDashboard.vo.EmployeeDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.temporal.IsoFields;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static java.time.temporal.IsoFields.QUARTER_OF_YEAR;

@Service
public class EmployeeServiceImpl implements EmployeeService {
    @Autowired
    private EmployeeRepository employeeRepository;

    @Override
    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    @Override
    public void saveEmployee(@RequestBody Employee employee) {
        employeeRepository.save(employee);
    }

    @Override
    public Employee getEmployeeById(long id) {
        Optional<Employee> optional = employeeRepository.findById(id);
        Employee employee = null;
        if (optional.isPresent()) {
            employee = optional.get();
        } else {
            throw new RuntimeException(" Employee not found for id :: " + id);
        }
        return employee;
    }

    @Override
    public void deleteEmployeeById(long id) {
//        Date date = new Date();
//        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
//        String currentDate = formatter.format(date);
        employeeRepository.deleteEmployee(id);
    }

    @Override
    public List<Employee> getEmployeeJoinedLastQuarter() throws ParseException {
        LocalDate date = LocalDate.now(); // can be any other date
        int previousQuarter = getPreviousQuarter(date.get(QUARTER_OF_YEAR));
        int year = previousQuarter!=4?date.getYear():date.getYear()-1;
        Date startDate = new SimpleDateFormat("yyyy-MM-dd").parse(String.valueOf(YearMonth.of(year, 1).with(QUARTER_OF_YEAR, previousQuarter).atDay(1)));
        Date endDate = new SimpleDateFormat("yyyy-MM-dd").parse(String.valueOf(YearMonth.of(year, 3).with(QUARTER_OF_YEAR, previousQuarter).atEndOfMonth()));

        return employeeRepository.getEmployeeJoinedLastQuarter(startDate,endDate);
    }

    private int getPreviousQuarter(int i) {
        return i > 1 ? i - 1 : 4;
    }

    @Override
    public List<Employee> fetchEmployeesLeftlastQuarter() throws ParseException {
        LocalDate date = LocalDate.now(); // can be any other date
        int previousQuarter = getPreviousQuarter(date.get(QUARTER_OF_YEAR));
        int year = previousQuarter!=4?date.getYear():date.getYear()-1;
        Date startDate = new SimpleDateFormat("yyyy-MM-dd").parse(String.valueOf(YearMonth.of(year, 1).with(QUARTER_OF_YEAR, previousQuarter).atDay(1)));
        Date endDate = new SimpleDateFormat("yyyy-MM-dd").parse(String.valueOf(YearMonth.of(year, 3).with(QUARTER_OF_YEAR, previousQuarter).atEndOfMonth()));

        return employeeRepository.fetchEmployeesLeftlastQuarter(startDate,endDate);
    }

    @Override
    public List<Employee> fetchEmployeesLeft() {
        return employeeRepository.fetchEmployeesLeft();
    }

    @Override
    public List<Employee> fetchEmployeeWithHigherSalaryFromManager() {
        return employeeRepository.fetchEmployeeWithHigherSalaryFromManager();
    }

    @Override
    public List<Employee> getAllCurrentEmployees() {
        return employeeRepository.fetchCurrentEmployeesList();
    }

    @Override
    public boolean checkEmployeeExists(String id) {
        return employeeRepository.checkEmployeeExists(id);
    }

    public static LocalDate getCurrentQuarterStartDay(LocalDate date) {
        return date.with(IsoFields.DAY_OF_QUARTER, 1L);
    }

    public static LocalDate getCurrentQuarterEndDate(LocalDate date) {
        return date.with(IsoFields.DAY_OF_QUARTER, 92L);
    }
}
