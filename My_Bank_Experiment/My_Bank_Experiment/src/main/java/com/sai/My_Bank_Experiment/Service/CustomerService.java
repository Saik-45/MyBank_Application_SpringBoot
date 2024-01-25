package com.sai.My_Bank_Experiment.Service;

import com.sai.My_Bank_Experiment.Entity.Customer;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public interface CustomerService {
    void createAccount(Customer customer);
    List<Customer> getAllCustomers();
    Customer getCustomerByAccountNumber(Long accountNumber);
    boolean deleteCustomerByAccountNumber(Long accountNumber);
    boolean updateCustomerByAccountNumber(Long accountNumber, Customer updatedCustomer);
    boolean existsByAccountNumber(Long accountNumber);
    @Transactional(readOnly = true)
    Long getLastStoredAccountNumber();
}
