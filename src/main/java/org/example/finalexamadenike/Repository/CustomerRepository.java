package org.example.finalexamadenike.Repository;


import org.example.finalexamadenike.entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Integer> {
    boolean existsBySeatno(String seatno);
    Customer findBySeatno(String seatno);
}