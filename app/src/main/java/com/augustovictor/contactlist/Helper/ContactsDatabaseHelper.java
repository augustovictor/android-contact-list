package com.augustovictor.contactlist.Helper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by victoraweb on 5/29/16.
 */
public class ContactsDatabaseHelper extends SQLiteOpenHelper {

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
                KEY_CONTACT_ID + " INTEGER PRIMARY KEY" +
                KEY_CONTACT_USER_ID_FK + " INTEGER REFERENCES" + TABLE_USERS +
                KEY_CONTACT_NUMBER + " TEXT" +
                ")";

        String CREATE_USERS_TABLE = "CREATE TABLE " + TABLE_USERS +
                KEY_USER_ID + " INTEGER PRIMARY KEY" +
                KEY_USER_NAME + " TEXT" +
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
}
