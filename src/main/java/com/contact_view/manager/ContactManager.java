package com.contact_view.manager;

import com.contact_view.dao.ContactDAO;
import com.contact_view.entity.CallUsage;
import com.contact_view.entity.Contact;
import com.contact_view.entity.MonthlyReport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ContactManager {

    @Autowired
    private ContactDAO contactDAO;
    public static Map<String, String> userNameNumberMap = null;

    public List<Contact> getAllContacts() throws Exception{
        List<Contact> contacts = null;
        try {
            contacts = contactDAO.getAllContacts();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return contacts;
    }

    public List<CallUsage> getCallUsagesByNumber(Long number) {
        List<CallUsage> callUsages = null;
        try {
            System.out.println("number:: "+ number);
            callUsages = contactDAO.getCallUsagesByNumber(number);
            if(callUsages != null && !callUsages.isEmpty()) {
                Collections.sort(callUsages, new Comparator<CallUsage>() {
                    @Override
                    public int compare(CallUsage o1, CallUsage o2) {
                        return o2.getDuration().compareTo(o1.getDuration());
                    }
                });
                for (CallUsage callUsage: callUsages) {
                    callUsage.setName(getUserNameByNumber(callUsage.getNumber()));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return  callUsages;
    }

    public List<CallUsage> getCallUsagesByMonth(String month) {
        List<CallUsage> callUsages = null;
        try {
            System.out.println("number:: "+ month);
            callUsages = contactDAO.getCallUsagesByMonth(month);
//            if(callUsages != null && !callUsages.isEmpty()) {
//                for (CallUsage callUsage: callUsages) {
//                    callUsage.setName(getUserNameByNumber(callUsage.getNumber()));
//                }
//            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return  callUsages;
    }

    public List<MonthlyReport> usagesReportByMonth(String month) {
        List<MonthlyReport> reports = null;
        try {
            reports = contactDAO.usagesReportByMonth(month);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return reports;
    }

    public String getUserNameByNumber(Long number) throws Exception{
        String name = null;
        if(ContactManager.userNameNumberMap == null) {
            ContactManager.userNameNumberMap = new HashMap<String, String>();
            List<Contact> contacts = contactDAO.getAllContacts();
            if(contacts != null && !contacts.isEmpty()) {
                for (Contact contact: contacts) {
                    ContactManager.userNameNumberMap.put("91"+contact.getPhone1Value(), contact.getName());
                }
            }
        }
        name = ContactManager.userNameNumberMap.get(number.toString());
        if(name == null) {
            name = "";
        }
        return name;
    }

}
