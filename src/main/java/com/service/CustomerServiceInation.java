package com.service;

import com.dao.CustomerRepository;
import com.domian.Customer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CustomerServiceInation {
    private static final Logger LOG = LoggerFactory.getLogger(CustomerRepository.class);
    @Autowired
    private CustomerRepository customerRepository;

    @Transactional(rollbackFor = Exception.class)
    public Customer save(Customer customer) {
        return this.customerRepository.save(customer);
    }
}
