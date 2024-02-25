package com.example.gozayaandemo.modules.contactList;

import com.example.gozayaandemo.dataModels.Contact;

import java.util.List;

public interface ContactListContract {
    interface View {
        void onContactsReceived(List<Contact> contacts);
        void onContactFetchError(Throwable error);
    }

    interface Controller {
        void getContactListFromServer();
        List<Contact> getFilteredResult(List<Contact> allContacts, String searchString);
    }
}