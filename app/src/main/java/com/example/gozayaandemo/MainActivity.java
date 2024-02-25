package com.example.gozayaandemo;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gozayaandemo.app.ApiClient;
import com.example.gozayaandemo.app.Contact;
import com.example.gozayaandemo.app.ContactAdapter;
import com.example.gozayaandemo.app.ContactDetails;
import com.example.gozayaandemo.app.ContactService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    private ContactAdapter contactAdapter;
    private EditText searchBar;
    private List<Contact> originalContacts;
    private List<Contact> filteredContacts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.contact_list_recycler_view);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("My Contacts");
        progressBar = findViewById(R.id.progress_bar);
        searchBar = findViewById(R.id.search_bar);
        progressBar.setVisibility(View.VISIBLE);


        searchBar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String newText = s.toString().toLowerCase();
                if (TextUtils.isEmpty(newText)) {
                    filteredContacts = originalContacts;
                } else {
                    filteredContacts = new ArrayList<>();
                    for (Contact contact : originalContacts) {
                        if (contact.getName().toLowerCase().contains(newText)) {
                            filteredContacts.add(contact);
                        }
                    }
                }
                contactAdapter.setContacts(filteredContacts);
//                recyclerView.setAdapter(contactAdapter);
            }

            @Override
            public void afterTextChanged(Editable s) {
                //
            }
        });


        // Fetching contacts here
        getContactListFromServer();
    }

    private void getContactListFromServer() {
        ContactService service = ApiClient.getContactService();
        service.getContacts().enqueue(new Callback<ContactDetails>() {
            @Override
            public void onResponse(Call<ContactDetails> call, Response<ContactDetails> response) {
                if (response.isSuccessful()) {
                    ContactDetails details = response.body();
                    assert details != null;
                    if (details.getStatus()) {
                        LinearLayoutManager layoutManager = new LinearLayoutManager(getBaseContext());
                        contactAdapter = new ContactAdapter(details.getResult());
                        originalContacts = details.getResult();
                        recyclerView.setLayoutManager(layoutManager);
                        recyclerView.setAdapter(contactAdapter);
                        progressBar.setVisibility(View.GONE);
                    } else {
                        try {
                            Toast.makeText(getBaseContext(), "Failed to fetch contacts!",
                                    Toast.LENGTH_LONG).show();
                            throw new Exception("Failed to fetch contacts");
                        } catch (Exception e) {
                            throw new RuntimeException(e);
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<ContactDetails> call, Throwable t) {
                try {
                    Toast.makeText(getBaseContext(), "Failed to fetch contacts!",
                            Toast.LENGTH_LONG).show();
                    throw new Exception("Failed to fetch contacts");
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }

}