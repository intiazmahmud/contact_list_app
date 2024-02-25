package com.example.gozayaandemo.modules.contactDetails;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.gozayaandemo.R;
import com.example.gozayaandemo.dataModels.Contact;
import com.example.gozayaandemo.modules.contactList.ContactAdapter;
import com.example.gozayaandemo.modules.contactList.ContactListContract;
import com.example.gozayaandemo.modules.contactList.ContactListController;

import java.util.List;

public class ContactDetailsView extends AppCompatActivity implements ContactDetailsContract.View {
    private ContactDetailsContract.Controller controller;
    private Contact contact;
    private ImageView imageView;
    private TextView nameTextView;
    private TextView phoneTextView;
    private TextView emailTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.controller = new ContactDetailsController(this);
        initializeUi();
    }

    private void initializeUi() {
        setContentView(R.layout.activity_contact_details);
        contact = getIntent().getParcelableExtra("contact_data");
        Toolbar toolbar = findViewById(R.id.toolbar);
        imageView = findViewById(R.id.contact_image);
        nameTextView = findViewById(R.id.name_text_view);
        phoneTextView = findViewById(R.id.phone_text_view);
        emailTextView = findViewById(R.id.email_text_view);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Contact Details");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        initializeCallButtonAction();
        initializeMessageButtonAction();
        initializeEmailButtonAction();
        updateUi();
    }

    private void updateUi() {
        Glide.with(imageView.getContext()).load(contact.getImage()).into(imageView);
        nameTextView.setText(contact.getName());
        phoneTextView.setText(contact.getPhone());
        emailTextView.setText(contact.getEmail());
    }

    private void initializeCallButtonAction() {
        Button callButton = findViewById(R.id.call_button);
        callButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (contact != null) {
                    String phoneNum = contact.getPhone();
                    if (phoneNum != null && !phoneNum.isEmpty()) {
                        Intent intent = new Intent(Intent.ACTION_DIAL);
                        intent.setData(Uri.parse("tel:" + phoneNum));
                        startActivity(intent);
                    } else {
                        Toast.makeText(getBaseContext(), "Contact has no phone number.", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getBaseContext(), "Please select a contact first.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void initializeMessageButtonAction() {
        Button messageButton = findViewById(R.id.message_button);

        messageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (contact != null) {
                    String phoneNum = contact.getPhone();
                    if (phoneNum != null && !phoneNum.isEmpty()) {
                        Intent intent = new Intent(Intent.ACTION_SENDTO);
                        intent.setData(Uri.parse("smsto:" + phoneNum));
                        startActivity(intent);
                    } else {
                        Toast.makeText(getBaseContext(), "Contact has no phone number.", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getBaseContext(), "Please select a contact first.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void initializeEmailButtonAction() {
        Button emailButton = findViewById(R.id.email_button);

        emailButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (contact != null) {
                    String email = contact.getEmail();
                    if (email != null && !email.isEmpty()) {
                        Intent intent = new Intent(Intent.ACTION_SEND);
                        intent.setType("message/rfc822");
                        intent.putExtra(Intent.EXTRA_EMAIL, new String[]{email});
                        startActivity(intent);
                    } else {
                        Toast.makeText(getBaseContext(), "Contact has no email address.", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        // Handle back button press
        onBackPressed();
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_contact_details, menu);
        return true;
    }

}