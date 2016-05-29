package com.augustovictor.contactlist.Activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import com.augustovictor.contactlist.Adapter.ContactsAdapter;
import com.augustovictor.contactlist.Helper.DividerItemDecoration;
import com.augustovictor.contactlist.Model.Contact;
import com.augustovictor.contactlist.R;

import java.util.ArrayList;

/**
 * Created by victoraweb on 5/28/16.
 */
public class UserListActivity extends AppCompatActivity {

    // STEP 1
    ArrayList<Contact> contacts;
    Button mAddContactsButton;


    // STEP 2
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        final RecyclerView rvContacts = (RecyclerView) findViewById(R.id.recycler_view);

        contacts = Contact.createContactList(20);

        final ContactsAdapter adapter = new ContactsAdapter(contacts);

        rvContacts.setAdapter(adapter);

        LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(this);
        mLinearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        rvContacts.setLayoutManager(mLinearLayoutManager);

        // STEP 4
//        rvContacts.setOnScrollListener(new EndlessRecyclerViewScrollListener(mLinearLayoutManager) {
//            @Override
//            public void onLoadMore(int page, int totalItemsCount) {
//                loadNewContacts(adapter, rvContacts);
//            }
//        });

        // STEP 5
        rvContacts.setHasFixedSize(true);

        // STEP 6
        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST);
        rvContacts.addItemDecoration(itemDecoration);
        // STEP 3
        mAddContactsButton = (Button) findViewById(R.id.add_contacts_button);

        mAddContactsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Adding one contact
//                contacts.add(contacts.size(), new Contact("Victor", true));
//                adapter.notifyItemInserted(contacts.size());

                // Updating existing list - Adding many contacts
                loadNewContacts(adapter, rvContacts);

            }
        });
    }

    private void loadNewContacts(ContactsAdapter adapter, RecyclerView rvContacts) {
        int currentSize = adapter.getItemCount();
        ArrayList<Contact> newItems = Contact.createContactList(20);
        contacts.addAll(newItems);
        adapter.notifyItemRangeInserted(currentSize, newItems.size());
        rvContacts.scrollToPosition(currentSize);
    }
}
