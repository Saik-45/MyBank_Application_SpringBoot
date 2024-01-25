package com.sai.My_Bank_Experiment.Entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Entity
@Table(name = "Account_Creation_Table")
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "Accounted_Name", nullable = false)
    @Pattern(regexp = "^[a-zA-Z\\s]+$", message = "Full name must contain only alphanumeric characters and spaces")
    @Size(min = 3, max = 50, message = "Full name must be between 3 and 50 characters")
    private String fullName;

    @Column(name = "Mobile_Number", nullable = false, length = 10)
    @Pattern(regexp = "^[0-9]{10}$", message = "Mobile Number length Exact 10 Digits")
    private String mobileNumber;

    @Column(name = "Date_of_Birth", nullable = false, length = 10)
    @Pattern(regexp = "^\\d{2}-\\d{2}-\\d{4}$", message = "Invalid date of Birth format. Please use the format dd-MM-yyyy")
    private String dob;

    @Column(name = "Account_Open_Date", nullable = false)
    private String accountOpenDate;

    @Column(name = "ATM_Pin", nullable = false, length = 4)
    @Pattern(regexp = "^[0-9]{4}$", message = "Invalid ATM PIN Number Format. It must be a 4-digit number")
    private String atmPin;



    @Column(name = "Initial_Amount", nullable = false)
    @Min(value = 2500, message = "Invalid initial Amount. Minimum Amount 2500 Rs")
    private Double initialAmount;

    @Column(name = "Account_Number", unique = true, nullable = false, length = 11)
    @Digits(integer = 11, fraction = 0, message = "Invalid Account Number Format. It must be an 11-digit number and Unique..")
    private Long accountNumber;

    public Customer() {

    }

    public Customer(String fullName, String mobileNumber, String dob, String atmPin, Double initialAmount) {
        this.fullName = fullName.toUpperCase();
        this.mobileNumber = mobileNumber;
        this.dob = dob;

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        LocalDateTime currentDateTime = LocalDateTime.now();
        this.accountOpenDate = currentDateTime.format(formatter);

        this.atmPin = atmPin;
        this.initialAmount = initialAmount;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName.toUpperCase();
    }


    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getAccountOpenDate() {
        return accountOpenDate;
    }

    public void setAccountOpenDate(String accountOpenDate) {
        this.accountOpenDate = accountOpenDate;
    }

    public String getAtmPin() {
        return atmPin;
    }

    public void setAtmPin(String atmPin) {
        this.atmPin = atmPin;
    }

    public Double getInitialAmount() {
        return initialAmount;
    }

    public void setInitialAmount(Double initialAmount) {
        this.initialAmount = initialAmount;
    }

    public Long getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(Long accountNumber) {
        this.accountNumber = accountNumber;
    }
}
