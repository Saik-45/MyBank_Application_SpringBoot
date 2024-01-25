package com.sai.My_Bank_Experiment.Controller;


import com.sai.My_Bank_Experiment.Entity.Customer;
import com.sai.My_Bank_Experiment.Service.CustomerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.logging.Logger;

@RestController
@RequestMapping("/bank")
public class CustomerController {

    private final CustomerService customerService;

    private static final Logger LOGGER = Logger.getLogger(CustomerController.class.getName());

    public CustomerController(CustomerService customerService) {

        this.customerService = customerService;
    }


    @GetMapping("/home")
    public String home() {
        LOGGER.info("Endpoint: /bank/home accessed.");
        return "Home Of Bank-Application";
    }

    @PostMapping("/new")
    public ResponseEntity<?> createAccount(@Validated @RequestBody Customer customer) {
        LOGGER.info("Endpoint: /bank/new accessed. Creating a new customer.");

        Customer existingCustomer = customerService.getCustomerByAccountNumber(customer.getAccountNumber());

        if (existingCustomer != null) {
            LOGGER.warning("You Entered Account already exists in the database: " + customer.getAccountNumber());
            return ResponseEntity.status(HttpStatus.CONFLICT).body("You Entered Account Number already exists in the database.");
        }

        // Set the account open date before saving the customer
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        LocalDateTime currentDateTime = LocalDateTime.now();
        customer.setAccountOpenDate(currentDateTime.format(formatter));

        customerService.createAccount(customer);

        LOGGER.info("Account Created Successfully...");

        return ResponseEntity.status(HttpStatus.CREATED).body("Account Created Successfully...\nYour Account Number : " + customer.getAccountNumber());
    }




    @GetMapping("/all")
    public ResponseEntity<String> getAllCustomers() {
        LOGGER.info("Endpoint: /bank/All accessed. Retrieving all customers.");
        List<Customer> customers = customerService.getAllCustomers();

        StringBuilder response = new StringBuilder();

        if (customers.isEmpty()) {
            response.append("\t\tNo Accounts In a Database...");
        } else {
             response = new StringBuilder("\t\tExisted  Accounter's Details In A DataBase\n");

            for (Customer customer : customers) {
                response.append("\n- Customer ID       : ").append(customer.getId()+"\n")
                        .append("- Full Name         : ").append(customer.getFullName()+"\n")
                        .append("- Account Number    : ").append(customer.getAccountNumber()+"\n")
                        .append("- Mobile Number     : ").append(customer.getMobileNumber()+"\n")
                        .append("- Date of Birth     : ").append(customer.getDob()+"\n")
                        .append("- Account Open Date : ").append(customer.getAccountOpenDate()+"\n")
                        .append("- ATM PIN           : ").append(customer.getAtmPin()+"\n")
                        .append("- Amount            : ").append(customer.getInitialAmount()+"\n\n")
                        .append("-----------------------------------------------------\n\n");
            }
        }

        return ResponseEntity.ok(response.toString());
    }



    @GetMapping("/get/{accountNumber}")
    public ResponseEntity<?> getCustomerByAccountNumber(@PathVariable Long accountNumber) {
        LOGGER.info("Endpoint: /bank/get/{accountNumber} accessed. Retrieving customer by account number: " + accountNumber);
        Customer customer = customerService.getCustomerByAccountNumber(accountNumber);

        if (customer != null) {
            LOGGER.info("Customer found for account number: " + accountNumber);
            return ResponseEntity.ok(customer);
        } else {
            LOGGER.warning("Customer not found for account number: " + accountNumber);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Entered Account Number Not found In Database : " + accountNumber);
        }
    }

    @DeleteMapping("/delete/{accountNumber}")
    public ResponseEntity<String> deleteCustomerByAccountNumber(@PathVariable Long accountNumber) {
        LOGGER.info("Endpoint: /bank/delete/" + accountNumber + " accessed. Deleting customer by account number.");

        boolean customerExists = customerService.existsByAccountNumber(accountNumber);

        if (!customerExists) {
            LOGGER.warning("Customer not found for deletion with account number: " + accountNumber);
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("You entered Account Number Does Not Exist in the database: " + accountNumber);
        }

        boolean deleted = customerService.deleteCustomerByAccountNumber(accountNumber);

        if (deleted) {
            LOGGER.info("Customer deleted successfully for account number: " + accountNumber);
            return ResponseEntity.ok("\t\t"+ accountNumber+" Account deleted Successfully...");
        } else {
            LOGGER.warning("Failed to delete customer for account number: " + accountNumber);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to delete customer. Please try again later...");
        }
    }

    @PutMapping("/update/{accountNumber}")
    public ResponseEntity<String> updateCustomerByAccountNumber(
            @PathVariable Long accountNumber,
            @Validated @RequestBody Customer updatedCustomer
    ) {
        LOGGER.info("Endpoint: /bank/update/" + accountNumber + " Accessed. Updated Customer By Account Number...");

        boolean updated = customerService.updateCustomerByAccountNumber(accountNumber, updatedCustomer);

        if (updated) {
            LOGGER.info("Customer Updated Successfully for Account Number: " + accountNumber);
            return ResponseEntity.ok("\t\t Customer updated successfully...");
        } else {
            LOGGER.warning("Customer not found for updating with account number: " + accountNumber);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("\t" + accountNumber + " Account Number Does Not Exist In DataBase...");
        }
    }


    @GetMapping("/LastAccountNumberInBd")
    public ResponseEntity<String> getLastStoredAccountNumber() {
        LOGGER.info("Endpoint: /bank/lastStoredAccountNumber accessed. Retrieving last stored account number.");
        Long lastStoredAccountNumber = customerService.getLastStoredAccountNumber();
        return ResponseEntity.ok("Last Generated Account Number In DataBase : "+lastStoredAccountNumber);
    }


}
