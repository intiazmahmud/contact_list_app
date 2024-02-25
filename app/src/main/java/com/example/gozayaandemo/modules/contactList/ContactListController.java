package com.example.gozayaandemo.modules.contactList;

import android.text.TextUtils;

import com.example.gozayaandemo.dataModels.Contact;
import com.example.gozayaandemo.communication.ApiClient;
import com.example.gozayaandemo.dataModels.ContactDetails;
import com.example.gozayaandemo.communication.DataProviderService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ContactListController implements ContactListContract.Controller {
    private ContactListContract.View view;

    public ContactListController(ContactListContract.View view) {
        this.view = view;
    }

    public void getContactListFromServer() {
        DataProviderService service = ApiClient.getContactService();
        service.getContacts().enqueue(new Callback<ContactDetails>() {
            @Override
            public void onResponse(Call<ContactDetails> call, Response<ContactDetails> response) {
                if (response.isSuccessful()) {
                    ContactDetails details = response.body();
                    if (details != null && details.getStatus()) {
                        view.onContactsReceived(details.getResult());
                    }
                }
            }

            @Override
            public void onFailure(Call<ContactDetails> call, Throwable error) {
                view.onContactFetchError(error);
            }
        });
    }

    public List<Contact> getFilteredResult(List<Contact> allContacts, String searchString) {
        if (TextUtils.isEmpty(searchString)) {
            return allContacts;
        }

        List<Contact> filteredContacts = new ArrayList<>();
        for (Contact contact : allContacts) {
            if (contact.getName().toLowerCase().contains(searchString)) {
                filteredContacts.add(contact);
            }
        }
        return filteredContacts;
    }
}
