package com.augustovictor.contactlist.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.augustovictor.contactlist.Model.Contact;
import com.augustovictor.contactlist.R;

import java.util.List;

/**
 * Created by victoraweb on 5/28/16.
 */
public class ContactsAdapter extends RecyclerView.Adapter<ContactsAdapter.ViewHolder> {

    // STEP 3
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View contactView = inflater.inflate(R.layout.list_item_contact, parent, false);

        ViewHolder viewHolder = new ViewHolder(context, contactView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Contact contact = mContacts.get(position);

        TextView textView = holder.nameTextView;
        textView.setText(contact.getUser().getUserName());

        Button button = holder.messageButton;

        if(contact.ismOnline()) {
            button.setText("Message");
            button.setEnabled(true);
        } else {
            button.setText("Offline");
            button.setEnabled(false);
        }
    }

    @Override
    public int getItemCount() {
        return mContacts.size();
    }

    // STEP 1
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView nameTextView;
        public Button messageButton;
        private Context context;

        public ViewHolder(Context context, View itemView) {
            super(itemView);
            nameTextView = (TextView) itemView.findViewById(R.id.contact_name);
            messageButton = (Button) itemView.findViewById(R.id.message_button);

            this.context = context;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int position = getLayoutPosition();
            Contact contact = mContacts.get(position);
            Toast.makeText(context, contact.getmName(), Toast.LENGTH_SHORT).show();
        }
    }

    // STEP 2
    private List<Contact> mContacts;

    public ContactsAdapter(List<Contact> contacts) {
        this.mContacts = contacts;
    }
}
