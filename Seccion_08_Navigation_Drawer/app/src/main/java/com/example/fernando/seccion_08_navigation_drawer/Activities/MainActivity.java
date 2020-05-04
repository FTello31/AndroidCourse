package com.example.fernando.seccion_08_navigation_drawer.Activities;

import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.fernando.seccion_08_navigation_drawer.Fragments.AlertsFragment;
import com.example.fernando.seccion_08_navigation_drawer.Fragments.EmailFragment;
import com.example.fernando.seccion_08_navigation_drawer.Fragments.InfoFragment;
import com.example.fernando.seccion_08_navigation_drawer.R;


// cuando se usa toolbar se tiene que ir a styles y cambiar a ...NoActionBar
// crear la hamburguesa, file new image asset --> Actionbar and icons
// para perzonalizar el statusBar, crear un nuevo styles > agregar version mayor a la 21 y en el layout agregar android:fitsSystemWindows="true"
public class MainActivity extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setToolbar();

        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.navView);

        setFragmentbyDefault();

        drawerLayout.addDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(@NonNull View view, float v) {

            }

            @Override
            public void onDrawerOpened(@NonNull View view) {
                Toast.makeText(MainActivity.this, "Opened", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onDrawerClosed(@NonNull View view) {
                Toast.makeText(MainActivity.this, "Closed", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onDrawerStateChanged(int i) {

            }
        });

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                boolean fragmentTransaction = false;
                Fragment fragment = null;

                switch (menuItem.getItemId()) {

                    case R.id.menu_mail:
                        fragment = new EmailFragment();
                        fragmentTransaction = true;
                        break;
                    case R.id.menu_alert:
                        fragment = new AlertsFragment();
                        fragmentTransaction = true;
                        break;
                    case R.id.menu_info:
                        fragment = new InfoFragment();
                        fragmentTransaction = true;
                        break;
                    case R.id.menu_option_1:
                        Toast.makeText(MainActivity.this, "You have clicked option 1", Toast.LENGTH_SHORT).show();
                        break;

                }

                if (fragmentTransaction) {
                    changeFragment(fragment, menuItem);
                    drawerLayout.closeDrawers();
                }
                return false;
            }
        });
    }

    private void setToolbar() {
        Toolbar myToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_home);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }


    private void setFragmentbyDefault() {
        changeFragment(new EmailFragment(), navigationView.getMenu().getItem(0));

    }

    private void changeFragment(Fragment fragment, MenuItem menuItem) {
        getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, fragment).commit();
        menuItem.setChecked(true);
        getSupportActionBar().setTitle(menuItem.getTitle());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
