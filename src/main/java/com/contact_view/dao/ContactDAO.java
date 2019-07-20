package com.contact_view.dao;

import com.contact_view.entity.Contact;
import com.contact_view.serializers.MongoClientFactory;
import com.mongodb.MongoExecutionTimeoutException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ContactDAO extends MongoClientFactory{

    @Autowired
    private MongoClientFactory mongoClientFactory;

    ContactDAO() {
        super();
    }

    public List<Contact> getAllContacts() throws Exception {
        Query query = new Query();
        List<Contact> contacts = null;
        try {
            contacts = getMongoOperations().find(query, Contact.class, Contact.class.getSimpleName());
        } catch (MongoExecutionTimeoutException e) {
            throw new Exception("Error While fetching all contacts:: "+ e.getMessage());
        }
        return  contacts;
    }

}