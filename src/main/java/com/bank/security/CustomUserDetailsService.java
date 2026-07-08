package com.bank.security;

import com.bank.entity.Customer;
import com.bank.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private CustomerRepository customerRepository;

    @Override
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {

        Customer customer =
                customerRepository.findByEmail(username)
                        .orElseThrow(() ->
                                new UsernameNotFoundException("User not found"));

        return User.builder()
                .username(customer.getEmail())
                .password(customer.getPassword())
                .authorities("USER")
                .build();
    }
}