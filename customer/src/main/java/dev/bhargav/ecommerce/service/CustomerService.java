package dev.bhargav.ecommerce.service;

import dev.bhargav.ecommerce.dto.CustomerRequest;
import dev.bhargav.ecommerce.dto.CustomerResponse;
import dev.bhargav.ecommerce.entity.Customer;
import dev.bhargav.ecommerce.exception.CustomerNotFoundException;
import dev.bhargav.ecommerce.mapper.CustomerMapper;
import dev.bhargav.ecommerce.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;

    private final CustomerMapper customerMapper;

    public String createCustomer(CustomerRequest customerRequest) {
        if (customerRepository.findByEmail(customerRequest.email()).isPresent()) {
            throw new IllegalArgumentException("A customer with this email already exists.");
        }

        Customer customer = this.customerRepository
                .save(customerMapper.toCustomer(customerRequest));

        return customer.getId();
    }

    public void updateCustomer(CustomerRequest customerRequest) {
        if (customerRequest.id() == null) {
            throw new IllegalArgumentException("Customer ID must be provided for update.");
        }

        var customer = this.customerRepository
                .findById(customerRequest.id())
                .orElseThrow(() -> new CustomerNotFoundException(
                        String.format("Cannot update customer:: No customer found with the provided ID: %s", customerRequest.id())
                ));
        mergeCustomer(customer, customerRequest);
        this.customerRepository.save(customer);
    }

    private void mergeCustomer(Customer customer, CustomerRequest customerRequest) {
        if (StringUtils.isNotBlank(customerRequest.firstname())) {
            customer.setFirstname(customerRequest.firstname());
        }
        if (StringUtils.isNotBlank(customerRequest.lastname())) {
            customer.setLastname(customerRequest.lastname());
        }
        if (StringUtils.isNotBlank(customerRequest.email())) {
            customer.setEmail(customerRequest.email());
        }
        if (customerRequest.address() != null) {
            customer.setAddress(customerRequest.address());
        }
    }

    public List<CustomerResponse> findAllCustomers() {
        return  this.customerRepository.findAll()
                .stream()
                .map(this.customerMapper::fromCustomer)
                .collect(Collectors.toList());
    }

    public CustomerResponse findById(String id) {
        return this.customerRepository.findById(id)
                .map(customerMapper::fromCustomer)
                .orElseThrow(() -> new CustomerNotFoundException(String.format("No customer found with the provided ID: %s", id)));
    }

    public boolean existsById(String id) {
        return this.customerRepository.findById(id)
                .isPresent();
    }

    public void deleteCustomer(String id) {
        this.customerRepository.deleteById(id);
    }

}
