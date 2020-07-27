package com.hava.trips;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import com.hava.trips.databinding.MainActivityBinding;

import java.util.Objects;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class MainActivity extends AppCompatActivity {

    private NavController navController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MainActivityBinding binding = MainActivityBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_host);
        navController = Objects.requireNonNull(navHostFragment).getNavController();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        return NavigationUI.onNavDestinationSelected(item, navController) | super.onOptionsItemSelected(item);
    }
}