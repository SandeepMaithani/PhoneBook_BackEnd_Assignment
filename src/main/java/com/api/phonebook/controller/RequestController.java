package com.api.phonebook.controller;

import java.util.HashMap;
import java.util.List;

import javax.validation.ConstraintViolationException;

import com.api.phonebook.document.Contact;
import com.api.phonebook.exception.InvalidDetailsException;
import com.api.phonebook.exception.PhoneBookException;
import com.api.phonebook.service.ContactService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
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

@Api(value = "PhoneBook Endpoints", description = "Information Regarding All Available Endpoints for PhoneBook")
@RestController
public class RequestController {

    @Autowired
    private ContactService contactService;

    @ApiOperation(value = "Provide swagger ui based documentaion to get familier with API endpoints.")
    @GetMapping("/")
    public RedirectView swaggerMapping() {  
        return new RedirectView("/swagger-ui.html");
    }


    @ApiOperation(value = "Provide 10 contacts per page by default")
    @ApiResponses(
        value = {
            @ApiResponse(code = 204, message = "There is no contacts  to send for this request."),
            @ApiResponse(code = 401, message = "client need to authenticate itself to get the requested Contact."),
            @ApiResponse(code = 403, message = "The client does not have access rights to the Contact."),
            @ApiResponse(code = 404, message = "The server can not find the requested Contact."),
            @ApiResponse(code = 500, message = "The server has encountered a situation it doesn't know how to handle.")
        }
    )
    @GetMapping("/contacts")
    public ResponseEntity<?>getAllContacts(@PageableDefault(page = 0, size = 10) Pageable pageRequest) {
        try {
            Page<Contact>requiredContactPage = contactService.getAllContacts(pageRequest);
            List<Contact>CurrentPageContactList = requiredContactPage.getContent();
    
            return new ResponseEntity<>(CurrentPageContactList, CurrentPageContactList.size() > 0 ? HttpStatus.OK : HttpStatus.NO_CONTENT);
            
        } catch (Exception exception) {
            return new ResponseEntity<>(exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);  
        }
       
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

    @ApiOperation(value = "Search a contact by name or email address (10 results per page by default)")
    @GetMapping("/contacts/{name}")
    public ResponseEntity<?>searchOldContacts(@PathVariable String name, @PageableDefault(page = 0, size = 10) Pageable pageRequest) {
        try {
            Page<Contact> requiredContacts = contactService.findContact(name, pageRequest);
            List<Contact> requiredContactList = requiredContacts.getContent();
            return new ResponseEntity<>(requiredContactList, HttpStatus.OK);

        } catch (PhoneBookException exception) {
            return new ResponseEntity<>(exception.getMessage(), HttpStatus.NOT_FOUND);

        }
        catch (Exception exception) {
            return new ResponseEntity<>(exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);  
        }
        
    }

    @ApiOperation(value = "Let you Delete a Contact")
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
