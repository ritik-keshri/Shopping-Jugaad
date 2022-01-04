package com.example.shoppingjugaad.Fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.shoppingjugaad.LoginPage;
import com.example.shoppingjugaad.Profile;
import com.example.shoppingjugaad.R;
import com.google.firebase.auth.FirebaseAuth;

public class ShoesFragment extends Fragment {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_shoes, container, false);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.nav_header, menu);
        super.onCreateOptionsMenu(menu,inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // User clicked on a menu option in the app bar overflow menu
        switch (item.getItemId()) {
            // Respond to a click on the "Insert dummy data" menu option
            case R.id.whislist:

                return true;
            // Respond to a click on the "Delete all entries" menu option
            case R.id.cart:

                return true;
            case R.id.profile:
                startActivity(new Intent(getActivity(), Profile.class));
                return true;

            case R.id.logout:
                FirebaseAuth.getInstance().signOut();
                Intent i = new Intent(getActivity(), LoginPage.class);
                startActivity(i);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}