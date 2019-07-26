package com.contact_view.dao;

import com.contact_view.entity.CallUsage;
import com.contact_view.entity.Contact;
import com.contact_view.entity.MonthlyReport;
import com.contact_view.serializers.MongoClientFactory;
import com.mongodb.MongoExecutionTimeoutException;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationOptions;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.List;

//import org.springframework.data.mongodb.core.aggregation.AggregationOptions;

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
            Sort sort = new Sort(Sort.Direction.DESC, "callTime");
            query.with(sort);
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

    public List<MonthlyReport> usagesReportByMonth(String month) throws  Exception {
        Query query = new Query();
        List<MonthlyReport> monthlyReports = null;
        try {
            Criteria criteria = Criteria.where("month").is(month);
            AggregationOptions aggregationOptions = Aggregation.newAggregationOptions().cursor(new Document()).build();
            Aggregation aggregation = Aggregation.newAggregation(Aggregation.match(criteria),
                    Aggregation.group("name", "number").sum("duration").as("totalDuration").count().as("count"),
                    Aggregation.sort(Sort.Direction.DESC, "totalDuration")).withOptions(aggregationOptions);
            System.out.println("Aggregation:: "+aggregation);

//( new AggregationOptions(true,false,new Document()));
//  Aggregation aggregation = newAggregation(unwind, group).withOptions(newAggregationOptions().cursor(new Document()).build());

            AggregationResults<MonthlyReport> reports = getMongoOperations().aggregate(aggregation, CallUsage.class, MonthlyReport.class);
            monthlyReports = reports.getMappedResults();
            System.out.println("MonthlyReports: "+ reports.getMappedResults().toString());
        } catch (MongoExecutionTimeoutException e) {
            throw new Exception("Error While fetching all contacts:: "+ e.getMessage());
        }
        return  monthlyReports;
    }
}