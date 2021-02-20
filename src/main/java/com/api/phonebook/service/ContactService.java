package com.api.phonebook.service;

import java.util.HashMap;

import javax.validation.ConstraintViolationException;

import com.api.phonebook.document.Contact;
import com.api.phonebook.exception.InvalidDetailsException;
import com.api.phonebook.exception.PhoneBookException;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ContactService {

    public Page<Contact> getAllContacts(Pageable pageRequest);
    public Contact createContact(Contact contact) throws InvalidDetailsException, ConstraintViolationException;
    public Contact removeContact(String email) throws PhoneBookException, InvalidDetailsException;
    public void updateContact(HashMap<String, String>newValues, String email) throws PhoneBookException, InvalidDetailsException;
    public Page<Contact> findContact(String name, Pageable pageRequest) throws PhoneBookException;

    
}
