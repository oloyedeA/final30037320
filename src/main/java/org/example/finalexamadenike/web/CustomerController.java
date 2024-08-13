package org.example.finalexamadenike.web;

import org.example.finalexamadenike.entities.Customer;
import org.example.finalexamadenike.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Controller
@RestController
@RequestMapping("/customers")
public class CustomerController {
    @Autowired
    private CustomerRepository customerRepository;

    // Get all customers
    @GetMapping(path ="/")
    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    // Get a specific customer by ID
    @GetMapping("/{id}")
    public ResponseEntity<Customer> getCustomerById(@PathVariable Integer id) {
        Optional<Customer> customer = customerRepository.findById(id);
        return customer.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Reserve a seat
    @PostMapping
    public ResponseEntity<?> reserveSeat(@RequestBody Customer customer) {
        // Validate seat number
        if (customerRepository.existsBySeatno(customer.getSeatno())) {
            return ResponseEntity.badRequest().body("Seat already reserved");
        }

        // Generate a transaction number
        customer.setTransactionNumber(UUID.randomUUID().toString());

        // Save the customer
        Customer savedCustomer = customerRepository.save(customer);

        return ResponseEntity.ok(savedCustomer);
    }

    // Update a customer's information
    @PutMapping("/{id}")
    public ResponseEntity<Customer> updateCustomer(@PathVariable Integer id, @RequestBody Customer customerDetails) {
        Optional<Customer> customerOptional = customerRepository.findById(id);

        if (customerOptional.isPresent()) {
            Customer customer = customerOptional.get();
            customer.setCName(customerDetails.getCName());
            customer.setSeatno(customerDetails.getSeatno());
            customer.setDate(customerDetails.getDate());

            return ResponseEntity.ok(customerRepository.save(customer));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Delete a customer
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCustomer(@PathVariable Integer id) {
        Optional<Customer> customer = customerRepository.findById(id);

        if (customer.isPresent()) {
            customerRepository.delete(customer.get());
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
