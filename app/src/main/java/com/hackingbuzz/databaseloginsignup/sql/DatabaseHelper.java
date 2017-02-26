package com.hackingbuzz.databaseloginsignup.sql;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import com.hackingbuzz.databaseloginsignup.activites.LeftNavigationActivity;
import com.hackingbuzz.databaseloginsignup.activites.UserScreen;
import com.hackingbuzz.databaseloginsignup.model.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Avi Hacker on 2/25/2017.
 */

public class DatabaseHelper extends SQLiteOpenHelper {
    Context myContext;

    // database version
    public static int Database_Version = 1;

    // database name
    public static String Database_name = "userbase.db";

    // table name
    private static final String TABLE_USER = "user";

    // column names
    private static final String COLUMN_USER_ID = "user_id";
    private static final String COLUMN_USER_NAME = "user_name";
    private static final String COLUMN_USER_EMAIL = "user_email";
    private static final String COLUMN_USER_PASSWORD = "user_password";

    // create table sql query
    private String CREATE_USER_TABLE = "CREATE TABLE " + TABLE_USER + "("
            + COLUMN_USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + COLUMN_USER_NAME + " TEXT,"
            + COLUMN_USER_EMAIL + " TEXT," + COLUMN_USER_PASSWORD + " TEXT" + ")";

    // drop table sql query
    private String DROP_USER_TABLE = "DROP TABLE IF EXISTS " + TABLE_USER;






    public DatabaseHelper(Context context) {    // giving on Paremter to this constructor and calling super constructor from here and giving super class constructor few paramenters
        super(context, Database_name, null, Database_Version);
        myContext = context;   // got this context for Toast in CheckUser method ( for checking admin email )
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(CREATE_USER_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        // for updating table   // if you want to change columns or somthing user ALter Command
/*
        db.execSQL(DROP_USER_TABLE);

        onCreate(db);*/

    }


// insert user to database // we stroing user data with the help of ContentValues coz our contentResolver can get n proess data from contentValues

    public void addUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();   // coz we have to put data

        ContentValues values = new ContentValues();
        values.put(COLUMN_USER_NAME, user.getName());
        values.put(COLUMN_USER_EMAIL, user.getEmail());
        values.put(COLUMN_USER_PASSWORD, user.getPassword());

        // Inserting Row
        db.insert(TABLE_USER, null, values);
        db.close();
    }


    // check if user exit in database or not..if not then create an account

    public boolean checkUser(String email) {

        // array of columns to fetch
        String[] columns = {
                COLUMN_USER_ID
        };
        SQLiteDatabase db = this.getReadableDatabase();

        // selection criteria
        String selection = COLUMN_USER_EMAIL + " = ?";

        // selection argument
        String[] selectionArgs = {email};

        // query user table with condition
        /*
         * Here query function is used to fetch records from user table this function works like we use sql query.
         * SQL query equivalent to this query function is
         * SELECT user_id FROM user WHERE user_email = 'yoyo@gmail.com';
         */
        Cursor cursor = db.query(TABLE_USER, //Table to query
                columns,                    //columns to return
                selection,                  //columns for the WHERE clause
                selectionArgs,              //The values for the WHERE clause
                null,                       //group the rows
                null,                      //filter by row groups
                null);                      //The sort order
        int cursorCount = cursor.getCount();
        cursor.close();
        db.close();

        if (cursorCount > 0) {
            return true;
        }

        return false;
    }

// check user if exists then login and send to next activity or fragment


    public boolean checkUser(String email, String password) {

        // array of columns to fetch
        String[] columns = {
                COLUMN_USER_ID,
                COLUMN_USER_EMAIL
        };
        SQLiteDatabase db = this.getReadableDatabase();
        // selection criteria
        String selection = COLUMN_USER_EMAIL + " = ?" + " AND " + COLUMN_USER_PASSWORD + " = ?";

        // selection arguments
        String[] selectionArgs = {email, password};

        // query user table with conditions
        /**
         * Here query function is used to fetch records from user table this function works like we use sql query.
         * SQL query equivalent to this query function is
         * SELECT user_id FROM user WHERE user_email = 'yoyou@gmail.com' AND user_password = 'abcde';
         */

        Cursor cursor = db.query(TABLE_USER, //Table to query
                columns,                    //columns to return
                selection,                  //columns for the WHERE clause
                selectionArgs,              //The values for the WHERE clause
                null,                       //group the rows
                null,                       //filter by row groups
                null);                      //The sort order



        if (cursor.moveToFirst()) {
            do {


              String adminEmail  = (cursor.getString(cursor.getColumnIndex(COLUMN_USER_EMAIL)));
                if(adminEmail.equals("admin@gmail.com")) {
              //  Toast.makeText(myContext, "Yes Admin got it",Toast.LENGTH_LONG).show();
                    Log.i("Admin","Yes");
                    Intent i = new Intent(myContext, UserScreen.class);
                    myContext.startActivity(i);
                    return  false;
            }
            } while (cursor.moveToNext());
        }


        int cursorCount = cursor.getCount();

        cursor.close();
        db.close();
        if (cursorCount > 0) {
            return true;
        }
        Toast.makeText(myContext, "Wrong Email or Password ",Toast.LENGTH_SHORT).show();
        return false;
    }



// see we have to set user data 2 time and get we getting it 2 times...
    // first setting it before inserting ...n for inserting wer getting it..

    // 2nd time.....1 when quering databaser and setting.... n getting it .where we need to print it..

     //This method is to fetch all user and return the list of user records

    public List<User> getAllUser() {
        // array of columns to fetch
        String[] columns = {
                COLUMN_USER_ID,
                COLUMN_USER_EMAIL,
                COLUMN_USER_NAME,
                COLUMN_USER_PASSWORD
        };
        // sorting orders
        String sortOrder =
                COLUMN_USER_NAME + " ASC";
        List<User> userList = new ArrayList<User>();

        SQLiteDatabase db = this.getReadableDatabase();

        // query the user table
        /**
         * Here query function is used to fetch records from user table this function works like we use sql query.
         * SQL query equivalent to this query function is
         * SELECT user_id,user_name,user_email,user_password FROM user ORDER BY user_name;
         */
        Cursor cursor = db.query(TABLE_USER, //Table to query
                columns,    //columns to return
                null,        //columns for the WHERE clause
                null,        //The values for the WHERE clause
                null,       //group the rows
                null,       //filter by row groups
                sortOrder); //The sort order


        // Traversing through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                User user = new User();
                user.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_USER_ID))));
                user.setName(cursor.getString(cursor.getColumnIndex(COLUMN_USER_NAME)));
                user.setEmail(cursor.getString(cursor.getColumnIndex(COLUMN_USER_EMAIL)));
                user.setPassword(cursor.getString(cursor.getColumnIndex(COLUMN_USER_PASSWORD)));
                // Adding user record to list
                userList.add(user);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();

        // return user list
        return userList;
    }




}
