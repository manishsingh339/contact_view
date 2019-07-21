package com.contact_view.controller;

import com.contact_view.entity.CallUsage;
import com.contact_view.entity.Contact;
import com.contact_view.manager.ContactManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
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

    @RequestMapping(value = "/usagesByNumber", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<CallUsage> usagesByNumber(@RequestParam(value = "number", required = true) Long number){
        return contactManager.getCallUsagesByNumber(number);
    }

    @RequestMapping(value = "/usagesByMonth", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<CallUsage> usagesByMonth(@RequestParam(value = "month", required = true) String month){
        return contactManager.getCallUsagesByMonth(month);
    }
}
