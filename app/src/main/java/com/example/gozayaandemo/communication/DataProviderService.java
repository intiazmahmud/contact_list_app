package com.example.gozayaandemo.communication;

import com.example.gozayaandemo.dataModels.ContactDetails;

import retrofit2.Call;
import retrofit2.http.GET;

public interface DataProviderService {

    @GET("/api/user_journey/contact_list/")
    Call<ContactDetails> getContacts();
}
