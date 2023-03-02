package com.mastercard.EmployeeDashboard.entity;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor
@Entity
@Data
@ToString
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    @NonNull
    private String fname;
    @Column
    @NonNull
    private String lname;
    @NonNull
    @Column
    private String emailId;
    @NonNull
    @Column
    private String department;
    @Column
    private String designation;
    @Column
    @NonNull
    private double salary;
    @Column
    private String manager;
    @Column
    @NonNull
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date dateOfJoining;
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Column
    private Date dateOfLeaving;
    @Column
    @NonNull
    private boolean status;
}

