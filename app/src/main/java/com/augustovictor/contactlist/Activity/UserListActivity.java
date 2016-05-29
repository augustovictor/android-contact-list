package com.augustovictor.contactlist.Activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.augustovictor.contactlist.Adapter.ContactsAdapter;
import com.augustovictor.contactlist.Model.Contact;
import com.augustovictor.contactlist.R;

import java.util.ArrayList;

/**
 * Created by victoraweb on 5/28/16.
 */
public class UserListActivity extends AppCompatActivity {

    // STEP 1
    ArrayList<Contact> contacts;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView rvContacts = (RecyclerView) findViewById(R.id.recycler_view);

        contacts = Contact.createContactList(20);

        ContactsAdapter adapter = new ContactsAdapter(contacts);

        rvContacts.setAdapter(adapter);

        LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(this);
        mLinearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        rvContacts.setLayoutManager(mLinearLayoutManager);
    }
}
