package com.augustovictor.contactlist.Model;

/**
 * Created by victoraweb on 5/28/16.
 */
public class Contact {
    private String mName;
    private boolean mOnline;
    private User user;
    private String mNumber;
    private static int sLastContact_id = 0;

    public Contact(User user, String number) {
        this.user = user;
        this.mNumber = number;
        this.mOnline = true;
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

    public String getmNumber() {
        return mNumber;
    }

    public void setmNumber(String mNumber) {
        this.mNumber = mNumber;
    }


}
