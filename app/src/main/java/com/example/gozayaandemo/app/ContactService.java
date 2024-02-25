package com.example.gozayaandemo.app;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ContactService {

    @GET("/api/user_journey/contact_list/")
    Call<ContactDetails> getContacts();
}
