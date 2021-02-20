package com.api.phonebook.repository;

import java.util.List;

import com.api.phonebook.document.Contact;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface ContactRepository extends MongoRepository<Contact, String> {
    List<Contact> findByName(String name);
}