package com.api.phonebook.service;

import java.util.HashMap;
import java.util.List;

import javax.validation.ConstraintViolationException;

import com.api.phonebook.document.Contact;
import com.api.phonebook.exception.InvalidDetailsException;
import com.api.phonebook.exception.PhoneBookException;

public interface ContactService {

    public List<Contact> getAllContacts();
    public Contact createContact(Contact contact) throws InvalidDetailsException, ConstraintViolationException;
    public Contact removeContact(String email) throws PhoneBookException, InvalidDetailsException;
    public void updateContact(HashMap<String, String>newValues, String email) throws PhoneBookException, InvalidDetailsException;
    public Contact searchContact(String name, String email);

    
}
