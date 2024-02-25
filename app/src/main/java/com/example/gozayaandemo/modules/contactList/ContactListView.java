package com.example.gozayaandemo.modules.contactList;


import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gozayaandemo.R;
import com.example.gozayaandemo.dataModels.Contact;

import java.util.List;

public class ContactListView extends AppCompatActivity implements ContactListContract.View {
    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    private ContactAdapter contactAdapter;
    private EditText searchBar;
    private List<Contact> originalContacts;
    private List<Contact> filteredContacts;

    private ContactListContract.Controller controller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.controller = new ContactListController(this);
        this.controller.getContactListFromServer();
        initializeUi();
        setupSearchBar();
    }

    private void initializeUi() {
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("My Contacts");
        recyclerView = findViewById(R.id.contact_list_recycler_view);
        progressBar = findViewById(R.id.progress_bar);
        progressBar.setVisibility(View.VISIBLE);
        searchBar = findViewById(R.id.search_bar);
    }

    private void setupSearchBar() {
        searchBar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                contactAdapter.setContacts(controller.getFilteredResult(originalContacts, s.toString().toLowerCase()));
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }

    @Override
    public void onContactsReceived(List<Contact> contacts) {
        if (contacts.size() > 0) {
            LinearLayoutManager layoutManager = new LinearLayoutManager(getBaseContext());
            contactAdapter = new ContactAdapter(contacts);
            originalContacts = contacts;
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.setAdapter(contactAdapter);
            progressBar.setVisibility(View.GONE);
            return;
        }
        Toast.makeText(getBaseContext(), "No contacts found", Toast.LENGTH_LONG).show();

    }

    @Override
    public void onContactFetchError(Throwable error) {
        Toast.makeText(getBaseContext(), "Failed to fetch contacts!", Toast.LENGTH_LONG).show();
    }
}