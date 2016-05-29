package com.augustovictor.contactlist.Helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.augustovictor.contactlist.Model.Contact;
import com.augustovictor.contactlist.Model.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by victoraweb on 5/29/16.
 */
public class ContactsDatabaseHelper extends SQLiteOpenHelper {

    private static final String TAG = "ContactsDatabaseHelper";
    // STEP 5.1
    private static ContactsDatabaseHelper sDb;

    // STEP 1
    // Database info
    private static final String DATABASE_NAME = "contactsDatabase";
    private static final int DATABASE_VERSION = 1;

    // Table names
    private static final String TABLE_CONTACTS = "contacts";
    private static final String TABLE_USERS = "users";

    // Contacts table columns
    private static final String KEY_CONTACT_ID = "id";
    private static final String KEY_CONTACT_USER_ID_FK = "userId";
    private static final String KEY_CONTACT_NUMBER = "number";

    // Users table columns
    private static final String KEY_USER_ID = "id";
    private static final String KEY_USER_NAME = "userName";
    private static final String KEY_USER_PICTURE_URL = "pictureUrl";

    public ContactsDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // STEP 2
    @Override
    public void onConfigure(SQLiteDatabase db) {
        super.onConfigure(db);
        db.setForeignKeyConstraintsEnabled(true);
    }

    // STEP 3

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_CONTACTS + "(" +
                KEY_CONTACT_ID + " INTEGER PRIMARY KEY, " +
                KEY_CONTACT_USER_ID_FK + " INTEGER REFERENCES " + TABLE_USERS + ", " +
                KEY_CONTACT_NUMBER + " TEXT" +
                ")";

        String CREATE_USERS_TABLE = "CREATE TABLE " + TABLE_USERS + "(" +
                KEY_USER_ID + " INTEGER PRIMARY KEY, " +
                KEY_USER_NAME + " TEXT, " +
                KEY_USER_PICTURE_URL + " TEXT" +
                ")";

        db.execSQL(CREATE_CONTACTS_TABLE);
        db.execSQL(CREATE_USERS_TABLE);
    }

    // STEP 4

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if(oldVersion != newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_CONTACTS);
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
            onCreate(db);
        }
    }

    // STEP 5.2
    public static synchronized ContactsDatabaseHelper getInstance(Context context) {
        if (sDb == null) {
            sDb = new ContactsDatabaseHelper(context.getApplicationContext());
        }
        return sDb;
    }

    // STEP 6
    public void addContact(Contact contact) {
        SQLiteDatabase db = getWritableDatabase();

        db.beginTransaction();
        try {
            long userId = addOrUpdateUser(contact.getUser());
            ContentValues values = new ContentValues();
            values.put(KEY_CONTACT_USER_ID_FK, userId);
            values.put(KEY_CONTACT_NUMBER, contact.getmNumber());

            db.insertOrThrow(TABLE_CONTACTS, null, values);
            db.setTransactionSuccessful();
        } catch (Exception e) {
            Log.d(TAG, "Error while trying to add contact to database");
        } finally {
            db.endTransaction();
        }
    }

    // STEP 7
    private long addOrUpdateUser(User user) {
        SQLiteDatabase db = getWritableDatabase();
        long userId = -1;

        db.beginTransaction();
        try {
            ContentValues values = new ContentValues();
            values.put(KEY_USER_NAME, user.getUserName());
            values.put(KEY_USER_PICTURE_URL, user.getPictureUrl());

            int rows = db.update(TABLE_USERS, values, KEY_USER_NAME + " = ?", new String[] { user.getUserName() });

            if(rows == 1) {
                String usersSelectQuery = String.format("SELECT %s FROM %s WHERE %s = ?", KEY_USER_ID, TABLE_USERS, KEY_USER_NAME);
                Cursor cursor = db.rawQuery(usersSelectQuery, new String[] { String.valueOf(user.getUserName()) });

                try {
                    if(cursor.moveToFirst()) {
                        userId = cursor.getInt(0);
                        db.setTransactionSuccessful();
                    }
                } finally {
                    if(cursor != null && !cursor.isClosed()) {
                        cursor.close();
                    }
                }

            } else {
                // If the userName did not already exist insert one
                userId = db.insertOrThrow(TABLE_USERS, null, values);
                db.setTransactionSuccessful();
            }
        } catch (Exception e) {
            Log.d(TAG, "Error while trying to add or update user");
        } finally {
            db.endTransaction();
        }

        return userId;
    }

    // STEP 8
    public List<Contact> getAllContacts() {
        List<Contact> contacts = new ArrayList<>();

        String CONTACTS_SELECT_QUERY = String.format("SELECT * FROM %s LEFT OUTER JOIN %s ON %s.%s = %s.%s",
                TABLE_CONTACTS,
                TABLE_USERS,
                TABLE_CONTACTS, KEY_CONTACT_USER_ID_FK,
                TABLE_USERS, KEY_USER_ID);

        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(CONTACTS_SELECT_QUERY, null);
        try {
            if(cursor.moveToFirst()) {
                do {
                    User user = new User();
                    user.setUserName(cursor.getString(cursor.getColumnIndex(KEY_USER_NAME)));
                    user.setPictureUrl(cursor.getString(cursor.getColumnIndex(KEY_USER_PICTURE_URL)));

                    Contact contact = new Contact(user, cursor.getString(cursor.getColumnIndex(KEY_CONTACT_NUMBER)));
                    contacts.add(contact);

                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.d(TAG, "Error while trying to get contacts from database");
        } finally {
            if(cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
        }

        return contacts;
    }
}
