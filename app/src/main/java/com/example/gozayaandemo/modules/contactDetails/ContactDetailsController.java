package com.example.gozayaandemo.modules.contactDetails;

public class ContactDetailsController implements ContactDetailsContract.Controller {
    private ContactDetailsContract.View view;
    public ContactDetailsController(ContactDetailsContract.View view) {
        this.view = view;
    }
}
