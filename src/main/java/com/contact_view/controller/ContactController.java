package com.contact_view.controller;

import com.contact_view.entity.Contact;
import com.contact_view.manager.ContactManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("contact")
public class ContactController {
    @Autowired
    private ContactManager contactManager;

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    @ResponseBody
    public List<Contact> getAllContacts() throws Throwable {
        return contactManager.getAllContacts();
    }
}
