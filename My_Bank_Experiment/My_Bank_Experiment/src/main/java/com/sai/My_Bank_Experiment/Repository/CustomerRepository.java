package com.sai.My_Bank_Experiment.Repository;

import com.sai.My_Bank_Experiment.Entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {


    Customer findByAccountNumber(Long accountNumber);

    void deleteByAccountNumber(Long accountNumber);

    boolean existsByAccountNumber(Long accountNumber);

    @Query(value = "SELECT MAX(accountNumber) FROM Customer")
    Long getLastStoredAccountNumber();
}
