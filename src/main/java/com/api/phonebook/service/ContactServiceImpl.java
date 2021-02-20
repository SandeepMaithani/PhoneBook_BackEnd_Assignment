package com.api.phonebook.service;

import java.util.HashMap;
import java.util.Optional;
import java.util.regex.Pattern;

import javax.validation.ConstraintViolationException;

import com.api.phonebook.document.Contact;
import com.api.phonebook.exception.InvalidDetailsException;
import com.api.phonebook.exception.PhoneBookException;
import com.api.phonebook.repository.ContactRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ContactServiceImpl implements ContactService {

    @Autowired
    private ContactRepository contactRepository;

    public static boolean isNullOrEmpty(String string) 
	{ 	
		if (string == null || string.isEmpty()) {
            return true;
        } 
		else {
            return false;
        }
	} 

    public static boolean isValidEmail(String email) 
    { 
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\." + "[a-zA-Z0-9_+&*-]+)*@" + "(?:[a-zA-Z0-9-]+\\.)+[a-z" + "A-Z]{2,7}$"; 
                              
        Pattern pattern = Pattern.compile(emailRegex);

        if (email == null) {
            return false; 
        }
        else {
            return pattern.matcher(email).matches();
        } 
    } 

    @Override
    public Page<Contact> getAllContacts(Pageable pageRequest) {
        return contactRepository.findAll(pageRequest);
    }

    @Override
    public Contact createContact(Contact contact) throws InvalidDetailsException, ConstraintViolationException {
        if(isValidEmail(contact.getemail()) != true) {
            throw new InvalidDetailsException(InvalidDetailsException.InvalidEmailAddress());
        }

        String phoneNumber = contact.getphoneNumber();

        if(isNullOrEmpty(phoneNumber) == true) {
            contact.setphoneNumber("");
        }

        contactRepository.save(contact);

        return contact;
    }

    @Override
    public Contact removeContact(String email) throws PhoneBookException, InvalidDetailsException {
        
        if(isValidEmail(email) != true) {
            throw new InvalidDetailsException(InvalidDetailsException.InvalidEmailAddress());
        }

        Optional<Contact> removedCourse = contactRepository.findById(email);

        if (removedCourse.isPresent() == true) {
            contactRepository.deleteById(email);
            return removedCourse.get();

        } else {
            throw new PhoneBookException(PhoneBookException.NotFoundException(email));
        }
    }

    @Override
    public void updateContact(HashMap<String, String> newValues, String email) throws PhoneBookException, InvalidDetailsException {

        if(isValidEmail(email) != true) {
            throw new InvalidDetailsException(InvalidDetailsException.InvalidEmailAddress());
        }

        Optional<Contact> requiredContactWithId = contactRepository.findById(email);

        if (requiredContactWithId.isPresent() == true) {

            String newName = newValues.get("name");
            String newPhoneNumber = newValues.get("phoneNumber");
           
            Contact oldContactToUpdate = requiredContactWithId.get();

            newName = isNullOrEmpty(newName) ? oldContactToUpdate.getName() : newName;
            newPhoneNumber = isNullOrEmpty(newPhoneNumber) ? oldContactToUpdate.getphoneNumber() : newPhoneNumber;

            oldContactToUpdate.setName(newName);
            oldContactToUpdate.setphoneNumber(newPhoneNumber);

            this.contactRepository.save(oldContactToUpdate);
        } else {
            throw new PhoneBookException(PhoneBookException.NotFoundException(email));
        }

    }

    @Override
    public Contact searchContact(String name, String email) {
        return null;
    }
    
}
