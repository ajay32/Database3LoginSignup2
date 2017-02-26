package com.hackingbuzz.databaseloginsignup.activites;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import com.hackingbuzz.databaseloginsignup.R;
import com.hackingbuzz.databaseloginsignup.adapters.CustomAdapter;
import com.hackingbuzz.databaseloginsignup.model.User;
import com.hackingbuzz.databaseloginsignup.sql.DatabaseHelper;

import java.util.ArrayList;
import java.util.List;



/**
 * Created by Avi Hacker on 2/26/2017.
 */

public class UserScreen extends AppCompatActivity {

    private AppCompatActivity activity = UserScreen.this;  // so anyclass that need database we creating and passing that class object..

    DatabaseHelper databaseHelper;

    private List<User> arrayListUsers;

    ListView lv_userAccounts;
    CustomAdapter customAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_screen);

        initViews();
        initObjects();
    }

    private void initViews() {

        lv_userAccounts = (ListView) findViewById(R.id.lv_userAccounts);


    }



    private void initObjects() {

        getDataFromSQLite();
        arrayListUsers = new ArrayList<>();
        databaseHelper = new DatabaseHelper(activity);
         customAdapter = new CustomAdapter(getApplicationContext(), R.layout.custom_design, arrayListUsers);
        lv_userAccounts.setAdapter(customAdapter);


    }


    // This method is to fetch all user records from SQLite

    private void getDataFromSQLite() {
        // AsyncTask is used that SQLite operation not blocks the UI Thread.
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                arrayListUsers.clear();
                arrayListUsers.addAll(databaseHelper.getAllUser());  // all the user got returned in this arraylist

                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                customAdapter.notifyDataSetChanged();
            }
        }.execute();
    }






}
