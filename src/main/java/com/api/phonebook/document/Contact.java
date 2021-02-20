package com.api.phonebook.document;

import javax.validation.constraints.NotNull;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import io.swagger.annotations.ApiModelProperty;

@Document
public class Contact {
   
    @ApiModelProperty(notes = "Email Address of the Contact", required = true)
    @NotNull(message = "Name cannot be Empty")
    @Id
    private String email;

    @ApiModelProperty(notes = "Name of the Contact", required = true)
    @NotNull(message = "Name cannot be Empty")
    private String name;

    @ApiModelProperty(notes = "Phone Number of the Contact")
    private String phoneNumber;

    public Contact(String email, String name, String phoneNumber) {
        this.email = email;
        this.name = name;
        this.phoneNumber = phoneNumber;
    }

    public String getemail() {
        return email;
    }

    public void setemail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getphoneNumber() {
        return phoneNumber;
    }

    public void setphoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @Override
    public String toString() {
        return "Contact [email=" + email + ", name=" + name + ", phoneNumber=" + phoneNumber + "]";
    }
    
    
}
