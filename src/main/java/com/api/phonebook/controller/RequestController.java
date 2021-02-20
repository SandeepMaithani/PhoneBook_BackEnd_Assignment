package com.api.phonebook.controller;

import java.util.HashMap;
import java.util.List;

import javax.validation.ConstraintViolationException;

import com.api.phonebook.document.Contact;
import com.api.phonebook.exception.InvalidDetailsException;
import com.api.phonebook.exception.PhoneBookException;
import com.api.phonebook.service.ContactService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(value = "Xmeme Endpoints", description = "Information Regarding All Available Endpoints for Xmeme")
@RestController
public class RequestController {

    @Autowired
    private ContactService contactService;

    @GetMapping("/")
    public RedirectView swaggerMapping() {  
        return new RedirectView("/swagger-ui.html");
    }

    @GetMapping("/contacts")
    public List<Contact> getAllContacts() {
        return contactService.getAllContacts();
    }

    @ApiOperation(value = "Let you Add new Contact")
    @ApiResponses(
        value = {
            @ApiResponse(code = 201, message = "The request has succeeded and a new Contact has been created as a result."),
            @ApiResponse(code = 401, message = "client need to authenticate itself to create a New Contact."),
            @ApiResponse(code = 403, message = "The client does not have access rights to Create new Contact."),
            @ApiResponse(code = 404, message = "The server can not find the requested Contact."),
            @ApiResponse(code = 500, message = "The server has encountered a situation it doesn't know how to handle.")
        }
    )
    @PostMapping("/contacts")
    public ResponseEntity<?> createMeme(@RequestBody Contact contact) {
        try {
            Contact response = this.contactService.createContact(contact);
            return new ResponseEntity<>(response, HttpStatus.CREATED);
            
        } catch(ConstraintViolationException exception) {
            return new ResponseEntity<>(exception.getMessage(), HttpStatus.UNPROCESSABLE_ENTITY);

        } catch(InvalidDetailsException exception) {
            return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);

        } catch (Exception exception) {
            return new ResponseEntity<>(exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);

        }
    }

    @ApiOperation(value = "Let You Update the Existing Contact corresponds to provided Email Id")
    @ApiResponses(
        value = {
            @ApiResponse(code = 401, message = "client are not allowed to update Contact."),
            @ApiResponse(code = 404, message = "The server can not find the requested Contact to update."),
            @ApiResponse(code = 500, message = "The server has encountered a situation it doesn't know how to handle.")
        }
    )
    @PatchMapping("/contacts/{email}")
    public ResponseEntity<?> editContact(@RequestBody HashMap<String, String>newValues, @PathVariable String email) {
        try {
            contactService.updateContact(newValues, email);
            return new ResponseEntity<String>(String.valueOf(HttpStatus.OK.value()), HttpStatus.OK);

        } catch(PhoneBookException exception) {
            return new ResponseEntity<>(exception.getMessage(), HttpStatus.NOT_FOUND);

        } catch (InvalidDetailsException exception) {
            return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
        
        } catch (Exception exception) {
            return new ResponseEntity<>(exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        
        }

    }

    @DeleteMapping("/contacts/{email}")
    public ResponseEntity<?> removeCourse(@PathVariable String email) {
        try {
            Contact deletedContact =  this.contactService.removeContact(email);
            return new ResponseEntity<>(deletedContact, HttpStatus.ACCEPTED); 
             
        } catch(PhoneBookException exception) {
            return new ResponseEntity<>(exception.getMessage(), HttpStatus.NOT_FOUND);

        } catch (InvalidDetailsException exception) {
            return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
        
        } catch (Exception exception) {
            return new ResponseEntity<>(exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        
        }
    }


    
}
