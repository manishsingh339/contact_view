package com.contact_view.dao;

import com.contact_view.entity.CallUsage;
import com.contact_view.entity.Contact;
import com.contact_view.serializers.MongoClientFactory;
import com.mongodb.MongoExecutionTimeoutException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.query.Criteria;
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

    public List<CallUsage> getCallUsagesByNumber(Long number) throws Exception {
        Query query = new Query();
        List<CallUsage> callUsages = null;
        try {
            query.addCriteria(Criteria.where("number").is(number));
            callUsages = getMongoOperations().find(query, CallUsage.class, CallUsage.class.getSimpleName());
            System.out.println("callUsages:: "+ callUsages);
        } catch (MongoExecutionTimeoutException e) {
            throw new Exception("Error While fetching all contacts:: "+ e.getMessage());
        }
        return  callUsages;
    }

    public List<CallUsage> getCallUsagesByMonth(String month) throws Exception {
        Query query = new Query();
        List<CallUsage> callUsages = null;
        try {
            query.addCriteria(Criteria.where("month").is(month));
            Sort sort = new Sort(Sort.Direction.ASC, "callTime");
            query.with(sort);
            callUsages = getMongoOperations().find(query, CallUsage.class, CallUsage.class.getSimpleName());
            System.out.println("callUsages:: "+ callUsages);
        } catch (MongoExecutionTimeoutException e) {
            throw new Exception("Error While fetching all contacts:: "+ e.getMessage());
        }
        return  callUsages;
    }
}