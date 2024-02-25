package com.example.gozayaandemo.app;

import android.content.Intent;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.gozayaandemo.ContactDetailsActivity;
import com.example.gozayaandemo.R;

import java.util.List;

public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ViewHolder> {

    private List<Contact> contacts;
    protected Bitmap image;

    public ContactAdapter(List<Contact> contacts) {
        this.contacts = contacts;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.contact_item_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Contact contact = contacts.get(position);
        Glide.with(holder.itemView.getContext()).load(contact.getImage()).into(holder.imageView);
        holder.nameTextView.setText(contact.getName());
        holder.phoneTextView.setText(contact.getPhone());
        holder.emailTextView.setText(contact.getEmail());

        holder.contactCard.setOnClickListener(
                v -> {
                    Intent intent = new Intent(holder.contactCard.getContext(), ContactDetailsActivity.class);
                    holder.contactCard.getContext().startActivity(intent);
                }
        );

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(holder.itemView.getContext(), ContactDetailsActivity.class);
                intent.putExtra("contact_data", contact);
                holder.contactCard.getContext().startActivity(intent);

//                String jsonString = new Gson().toJson(contact);
//                intent.putExtra("contact_data", jsonString);
            }
        });
    }

    @Override
    public int getItemCount() {
        return contacts.size();
    }


    public void setContacts(List<Contact> newContacts) {
        this.contacts = newContacts;
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private CardView contactCard;
        ImageView imageView;
        TextView nameTextView;
        TextView phoneTextView;
        TextView emailTextView;

        public ViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.contact_image);
            nameTextView = itemView.findViewById(R.id.name_text_view);
            phoneTextView = itemView.findViewById(R.id.phone_text_view);
            emailTextView = itemView.findViewById(R.id.email_text_view);
            contactCard = itemView.findViewById(R.id.contact_card);
        }
    }
}

