package com.hackingbuzz.databaseloginsignup.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.hackingbuzz.databaseloginsignup.R;
import com.hackingbuzz.databaseloginsignup.model.User;

import java.util.List;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;

/**
 * Created by Avi Hacker on 2/26/2017.
 */

public class CustomAdapter extends ArrayAdapter {

    int resource;
    LayoutInflater inflater;
    List<User> arrayListUsers;

    public CustomAdapter(Context context, int resource, List<User> objects) {
        super(context, resource, objects);
        arrayListUsers = objects;
        this.resource = resource;
        inflater = (LayoutInflater) context.getSystemService(LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {


        if (convertView == null) {

            convertView = inflater.inflate(resource, null);


        }


        TextView userName;
        TextView userEmail;
        TextView userPassword;
        TextView userId;


        userId = (TextView) convertView.findViewById(R.id.user_id);
        userName = (TextView) convertView.findViewById(R.id.user_name);
        userEmail = (TextView) convertView.findViewById(R.id.user_email);
        userPassword = (TextView) convertView.findViewById(R.id.user_password);

        userId.setText(String.valueOf(arrayListUsers.get(position).getId()));
        userName.setText(arrayListUsers.get(position).getName());
        Log.i("NameIs",""+  arrayListUsers.get(position).getName());
        userEmail.setText(arrayListUsers.get(position).getEmail());
        userPassword.setText(arrayListUsers.get(position).getPassword());


        return convertView;
    }
}