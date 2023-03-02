package com.mastercard.EmployeeDashboard.dao;

import com.mastercard.EmployeeDashboard.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    @Query("from Employee e where e.dateOfJoining BETWEEN :startDate AND :endDate AND e.status=false")
    List<Employee> getEmployeeJoinedLastQuarter(Date startDate, Date endDate);

    @Query("from Employee e where e.dateOfLeaving BETWEEN :startDate AND :endDate AND e.status=true")
    List<Employee> fetchEmployeesLeftlastQuarter(Date startDate, Date endDate);

    @Query("from Employee e where e.status= false")
    List<Employee> fetchCurrentEmployeesList();

    @Query("from Employee e where e.status= true")
    List<Employee> fetchEmployeesLeft();

    @Query("from Employee e, Employee m WHERE e.manager=m.id AND e.salary>m.salary")
    List<Employee> fetchEmployeeWithHigherSalaryFromManager();


    @Modifying
    @Transactional
    @Query("update Employee e set e.status=true, e.dateOfLeaving=CURRENT_DATE where e.id=:id")
    void deleteEmployee(long id);

    @Query("SELECT CASE WHEN COUNT(e) > 0 THEN true ELSE false END FROM Employee e WHERE e.emailId = :id")
    boolean checkEmployeeExists(String id);
}
