package com.example.gozayaandemo;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.example.gozayaandemo.app.Contact;

public class ContactDetailsActivity extends AppCompatActivity {
    private Contact contact;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_details);
        contact = getIntent().getParcelableExtra("contact_data");
        assert contact != null;
        updateUi(contact);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Contact Details");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ImageButton callButton = findViewById(R.id.call_button);
        ImageButton messageButton = findViewById(R.id.message_button);
        ImageButton emailButton = findViewById(R.id.email_button);


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
        onBackPressed();
        return true;
    }

    private void updateUi(Contact contact) {
        ImageView imageView = findViewById(R.id.contact_image);
        TextView nameTextView = findViewById(R.id.name_text_view);
        TextView phoneTextView = findViewById(R.id.phone_text_view);
        TextView emailTextView = findViewById(R.id.email_text_view);


        Glide.with(imageView.getContext()).load(contact.getImage()).into(imageView);
        nameTextView.setText(contact.getName());
        phoneTextView.setText(contact.getPhone());
        emailTextView.setText(contact.getEmail());

//        if (contact.getImage() != null) {
//            // Load image using Glide or Picasso
//        } else {
//            // Handle cases where image is unavailable
//        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_contact_details, menu);
        return true;
    }

}