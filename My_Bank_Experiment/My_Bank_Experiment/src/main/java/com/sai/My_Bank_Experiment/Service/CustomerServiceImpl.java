package com.sai.My_Bank_Experiment.Service;

import com.sai.My_Bank_Experiment.Entity.Customer;
import com.sai.My_Bank_Experiment.Repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.logging.Logger;

@Service
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private final CustomerRepository customerRepository;
   @Autowired
    private final AccountNumberGenerationService accountNumberGenerationService;

    private static final Logger LOGGER = Logger.getLogger(CustomerServiceImpl.class.getName());

    @Autowired
    public CustomerServiceImpl(CustomerRepository customerRepository, AccountNumberGenerationService accountNumberGenerationService) {
        this.customerRepository = customerRepository;
        this.accountNumberGenerationService = accountNumberGenerationService;
    }

    @Override
    @Transactional
    public void createAccount(Customer customer) {
        LOGGER.info("Creating a new customer: " + customer.getFullName());

        Long newAccountNumber = accountNumberGenerationService.generateNextAccountNumber();
        customer.setAccountNumber(newAccountNumber);

        customerRepository.save(customer);
        LOGGER.info("Account Created Successfully...");
    }

    @Override
    @Transactional(readOnly = true)
    public List<Customer> getAllCustomers() {
        LOGGER.info("Retrieving all customers.");
        return customerRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Customer getCustomerByAccountNumber(Long accountNumber) {
        LOGGER.info("Retrieving customer by account number: " + accountNumber);
        return customerRepository.findByAccountNumber(accountNumber);
    }

    @Override
    @Transactional
    public boolean deleteCustomerByAccountNumber(Long accountNumber) {
        LOGGER.info("Deleting customer by account number: " + accountNumber);
        customerRepository.deleteByAccountNumber(accountNumber);
        LOGGER.info("Account Deleted Successfully...");
        return true;
    }

    @Override
    @Transactional
    public boolean updateCustomerByAccountNumber(Long accountNumber, Customer updatedCustomer) {
        LOGGER.info("Updating customer by account number: " + accountNumber);
        Customer existingCustomer = customerRepository.findByAccountNumber(accountNumber);

        if (existingCustomer != null) {
            existingCustomer.setFullName(updatedCustomer.getFullName());
            existingCustomer.setMobileNumber(updatedCustomer.getMobileNumber());
            existingCustomer.setDob(updatedCustomer.getDob());
            existingCustomer.setAtmPin(updatedCustomer.getAtmPin());

            customerRepository.save(existingCustomer);
            LOGGER.info("Customer updated successfully.");
            return true;
        } else {
            LOGGER.warning("Customer not found for updating with account number: " + accountNumber);
            return false;
        }
    }


    @Override
    public boolean existsByAccountNumber(Long accountNumber) {
        return customerRepository.existsByAccountNumber(accountNumber);
    }


    @Override
    @Transactional(readOnly = true)
    public Long getLastStoredAccountNumber() {
        return customerRepository.getLastStoredAccountNumber();
    }
}
