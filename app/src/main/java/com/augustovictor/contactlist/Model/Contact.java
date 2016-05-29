package com.augustovictor.contactlist.Model;

import java.util.ArrayList;

/**
 * Created by victoraweb on 5/28/16.
 */
public class Contact {
    private String mName;
    private boolean mOnline;
    private User user;
    private static int sLastContact_id = 0;

    public Contact(String mName, boolean mOnline) {
        this.mName = mName;
        this.mOnline = mOnline;
    }

    public String getmName() {
        return mName;
    }

    public void setmName(String mName) {
        this.mName = mName;
    }

    public boolean ismOnline() {
        return mOnline;
    }

    public void setmOnline(boolean mOnline) {
        this.mOnline = mOnline;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public static ArrayList<Contact> createContactList(int numContacts) {
        ArrayList<Contact> contacts = new ArrayList<>();

        for (int i = 0; i < numContacts; i++) {
            contacts.add(new Contact("Person " + ++sLastContact_id, i <= numContacts/2));
        }
        return contacts;
    }
}
