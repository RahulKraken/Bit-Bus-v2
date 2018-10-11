package com.example.kraken.bit_bus_v2;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;

import com.example.kraken.bit_bus_v2.Fragments.AutoFragment;
import com.example.kraken.bit_bus_v2.Fragments.BusFragment;
import com.example.kraken.bit_bus_v2.Fragments.AboutFragment;
import com.example.kraken.bit_bus_v2.Utils.DataManager;
import com.example.kraken.bit_bus_v2.Utils.DateProvider;
import com.example.kraken.bit_bus_v2.Utils.FirebaseHelper;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    // view objects
    private FrameLayout frameLayout;
    private BottomNavigationView navigationView;

    // fragment objects
    private BusFragment busFragment;
    private AutoFragment autoFragment;
    private AboutFragment aboutFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FirebaseHelper helper = new FirebaseHelper(this);
        setContentView(R.layout.activity_main);

        // reference to the views
        frameLayout = findViewById(R.id.frameLayout);
        navigationView = findViewById(R.id.bottomNavigationView);

        // init the fragments
        busFragment = new BusFragment();
        autoFragment = new AutoFragment();
        aboutFragment = new AboutFragment();

        setupNavigationBar();
    }

    private void setupNavigationBar() {
        /*
         * set default fragment to the bus fragment
         */
        setFragment(busFragment);

        /*
         * set click listener to the navigation menu items
         */
        navigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.nav_bus:
                        setFragment(busFragment);
                        return true;
                    case R.id.nav_auto:
                        setFragment(autoFragment);
                        return true;
                    case R.id.nav_about:
                        setFragment(aboutFragment);
                        return true;
                }
                return false;
            }
        });
    }

    /**
     * set new fragment in place of frame layout
     * @param fragment
     */
    private void setFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frameLayout, fragment);
        transaction.commit();
    }

    public void contactBusDepo(View view) {
        String uri = getString(R.string.bus_depo_contact);
        Intent callIntent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + uri));
        startActivity(callIntent);
    }

    public void shareApp(View view) {
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("text/plain");
        String shareString = "google play app link";
        shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Share");
        shareIntent.putExtra(Intent.EXTRA_TEXT, shareString);
        startActivity(Intent.createChooser(shareIntent, "Share using"));
    }

    public void sendFeedback(View view) {
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("text/email");
        shareIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{"www.rahulchoudhary7@gmail.com"});
        shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Feedback");
        startActivity(Intent.createChooser(shareIntent, "Send Feedback:"));
    }
}
