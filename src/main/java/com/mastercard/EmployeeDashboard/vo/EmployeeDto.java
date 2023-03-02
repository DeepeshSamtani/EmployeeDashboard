package com.mastercard.EmployeeDashboard.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EmployeeDto {
    private Long id;
    private String fname;
    private String lname;
    private String emailId;
    private String department;
    private String designation;
    private double salary;
    private String manager;
    private Date dateOfJoining;
    private Date dateOfLeaving;
}
