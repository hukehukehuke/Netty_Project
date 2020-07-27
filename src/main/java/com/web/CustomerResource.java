package com.web;


import com.dao.CustomerRepository;
import com.domain.Customer;
import com.service.CustomerServiceTxInAnnotation;
import com.service.CustomerServiceTxInCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by mavlarn on 2018/1/20.
 */
@RestController
@RequestMapping("/api/customer")
public class CustomerResource {
    private static final Logger LOG = LoggerFactory.getLogger(CustomerResource.class);

    @Autowired
    private CustomerServiceTxInAnnotation customerService;
    @Autowired
    private CustomerServiceTxInCode customerServiceInCode;
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private JmsTemplate jmsTemplate;

    @PostMapping("/annotation")
    public Customer createInAnnotation(@RequestBody Customer customer) {
        LOG.info("CustomerResource create in annotation create customer:{}", customer.getUsername());
        return customerService.create(customer);
    }
    @PostMapping("/code")
    public Customer createInCode(@RequestBody Customer customer) {
        LOG.info("CustomerResource create in code create customer:{}", customer.getUsername());
        return customerServiceInCode.create(customer);
    }
    @Transactional(rollbackFor = Exception.class)
    @PostMapping("message/code")
    public void createInCodeByLisenner(@RequestBody Customer customer) {
        LOG.info("CustomerResource create in code create customer:{}", customer.getUsername());
        jmsTemplate.convertAndSend("customer jsm2: name",customer);
    }
    @Transactional(rollbackFor = Exception.class)
    @PostMapping("message/annotation")
    public void createInAnnotationByLisenter(@RequestBody Customer customer) {
        LOG.info("CustomerResource create in annotation create customer:{}", customer.getUsername());
        jmsTemplate.convertAndSend("customer jsm1: name",customer);
    }


    @GetMapping("")
    public List<Customer> getAll() {
        return customerRepository.findAll();
    }

}
