package me.hotcode.eatdee;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import me.hotcode.eatdee.adapters.MainContainerPagerAdapter;

import com.firebase.client.Firebase;
import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

import me.hotcode.eatdee.utils.ImageLoadTask;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private static final int RC_SIGN_IN = 1;

    NavigationView navigationView;
    TextView nav_header_name;
    TextView nav_header_email;
    ImageView nav_header_image;
    ViewPager maincontainer_pager;


    FirebaseAuth auth;
    int isLogin=0;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            if (resultCode == RESULT_OK) {
                // user is signed in!
                setWhenSignIn();
                //finish();
            } else {
                // user is not signed in. Maybe just wait for the user to press
                // "sign in" again, or show a message
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
    });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        maincontainer_pager = (ViewPager) findViewById(R.id.container);
        maincontainer_pager.setAdapter(new MainContainerPagerAdapter(getSupportFragmentManager()));

        View headerLayout = navigationView.getHeaderView(0);
        //View headerLayout = navigationView.inflateHeaderView(R.layout.nav_header_main);
        nav_header_name = (TextView) headerLayout.findViewById(R.id.header_nav_username);
        nav_header_email = (TextView) headerLayout.findViewById(R.id.header_nav_email);
        nav_header_image = (ImageView)  headerLayout.findViewById(R.id.header_nav_image);


        Firebase.setAndroidContext(this);
        //check firebase auth
        auth = FirebaseAuth.getInstance();
        if (auth.getCurrentUser() != null) {
            // already signed in
            setWhenSignIn();
            isLogin = 1;
        } else {
            // not signed in
            setWhenNotSignIn();
            isLogin = 0;
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            maincontainer_pager.setCurrentItem(0);
        } else if (id == R.id.nav_daily) {
            maincontainer_pager.setCurrentItem(1);
        } else if (id == R.id.nav_list) {

        } else if (id == R.id.nav_setting) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_sync){

        } else if (id == R.id.nav_signout) {
            AuthUI.getInstance()
                    .signOut(this)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        public void onComplete(@NonNull Task<Void> task) {
                            // user is now signed out
                            startActivity(new Intent(MainActivity.this, MainActivity.class));
                            finish();
                        }
                    });
        } else if (id == R.id.nav_signin) {
            startActivityForResult(
                    AuthUI.getInstance()
                            .createSignInIntentBuilder()
                            .setProviders(
                                    AuthUI.EMAIL_PROVIDER
                                    ,AuthUI.GOOGLE_PROVIDER
                                    ,AuthUI.FACEBOOK_PROVIDER)
                            .setLogo(R.drawable.eatdee_logo)
                            //.setLogo()
                            .build(),
                    RC_SIGN_IN);

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    void setWhenSignIn(){
        //set user
        nav_header_name.setText(auth.getCurrentUser().getDisplayName());
        nav_header_email.setText(auth.getCurrentUser().getEmail());
        ImageLoadTask imagedownload = new ImageLoadTask(""+auth.getCurrentUser().getPhotoUrl());
        imagedownload.execute();
        nav_header_image.setImageBitmap(imagedownload.getBitmap());

        //set menu
        navigationView.getMenu().findItem(R.id.nav_sync).setVisible(true);
        navigationView.getMenu().findItem(R.id.nav_signout).setVisible(true);
        navigationView.getMenu().findItem(R.id.nav_signin).setVisible(false);
    }

    void setWhenNotSignIn(){
        //set menu
        navigationView.getMenu().findItem(R.id.nav_sync).setVisible(false);
        navigationView.getMenu().findItem(R.id.nav_signout).setVisible(false);
        navigationView.getMenu().findItem(R.id.nav_signin).setVisible(true);
    }
}
