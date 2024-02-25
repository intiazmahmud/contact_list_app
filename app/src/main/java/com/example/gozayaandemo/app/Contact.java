package com.example.gozayaandemo.app;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class Contact implements Parcelable {
    private String full_name;
    private String phone_number;
    private String email;
    private String image;

    protected Contact(Parcel in) {
        full_name = in.readString();
        phone_number = in.readString();
        email = in.readString();
        image = in.readString();
    }

    public static final Creator<Contact> CREATOR = new Creator<Contact>() {
        @Override
        public Contact createFromParcel(Parcel in) {
            return new Contact(in);
        }

        @Override
        public Contact[] newArray(int size) {
            return new Contact[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeString(full_name);
        dest.writeString(phone_number);
        dest.writeString(email);
        dest.writeString(image);
    }

    public String getName() {
        return full_name;
    }

    public String getPhone() {
        return phone_number;
    }

    public String getEmail() {
        return email;
    }

    public String getImage() {
        return image;
    }
}
