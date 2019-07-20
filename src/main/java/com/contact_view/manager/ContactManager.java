package com.contact_view.manager;

import com.contact_view.dao.ContactDAO;
import com.contact_view.entity.Contact;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ContactManager {

    @Autowired
    private ContactDAO contactDAO;

    public List<Contact> getAllContacts() throws Exception{
        List<Contact> contacts = null;
        try {
            contacts = contactDAO.getAllContacts();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return contacts;
    }
}
