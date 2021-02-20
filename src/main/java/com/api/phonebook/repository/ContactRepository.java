package com.api.phonebook.repository;

import java.util.List;

import com.api.phonebook.document.Contact;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ContactRepository extends MongoRepository<Contact, String> {
    Page<Contact> findByName(String name, Pageable pagerequest);
    List<Contact> findByName(String name);
}
