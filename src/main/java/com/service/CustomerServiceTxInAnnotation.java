package com.service;


import com.dao.CustomerRepository;
import com.domain.Customer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by mavlarn on 2018/1/24.
 */
@Service
public class CustomerServiceTxInAnnotation {

    private static final Logger LOG = LoggerFactory.getLogger(CustomerServiceTxInAnnotation.class);

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private JmsTemplate jmsTemplate;
    @Transactional
    public Customer create(Customer customer) {
        LOG.info("CustomerService In Annotation create customer:{}", customer.getUsername());
        if (customer.getId() != null) {
            throw new RuntimeException("用户已经存在");
        }
        customer.setUsername("Annotation:" + customer.getUsername());
        return customerRepository.save(customer);
    }
    @JmsListener(destination = "customer jsm2: name")
    private void create(String username){
        LOG.info("");
        Customer customer = new Customer();
        customer.setId(324L);
        customer.setPassword("232432");
        customer.setUsername("dddd");
        jmsTemplate.convertAndSend("customer : name",customer);
    }
}
