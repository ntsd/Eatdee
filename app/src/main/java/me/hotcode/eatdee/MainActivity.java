package me.hotcode.eatdee;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
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

import me.hotcode.eatdee.activitys.SearchFoodActivity;
import me.hotcode.eatdee.activitys.SetProfileActivity;
import me.hotcode.eatdee.adapters.MainContainerPagerAdapter;

import com.firebase.client.Firebase;
import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import me.hotcode.eatdee.fatsecret.model.Food;
import me.hotcode.eatdee.models.ListFood;
import me.hotcode.eatdee.models.Profile;
import me.hotcode.eatdee.utils.FoodUtils;
import me.hotcode.eatdee.utils.ImageLoadTask;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private static final int RC_SIGN_IN = 1;
    private static final int RC_GETTHING_START = 2;
    NavigationView navigationView;
    TextView nav_header_name;
    TextView nav_header_email;
    ImageView nav_header_image;
    ViewPager mainContainerPager;
    MainContainerPagerAdapter containerPagerAdapter;

    //database
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference rootRef = database.getReference();
    DatabaseReference profilesRef = rootRef.child("profiles");
    DatabaseReference profileRef;
    Profile currentProfile;
    int canGetCurrentProfile = 0;

    DatabaseReference foodListsRef = rootRef.child("foodlists");
    List<ListFood> listOfListFood;

    FirebaseAuth auth;
    int isLogin = 0;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            if (resultCode == RESULT_OK) {
                // user is signed in!
                Intent intent = getIntent();
                finish();
                startActivity(intent);
            } else {
                // user is not signed in. Maybe just wait for the user to press
                // "sign in" again, or show a message
                setWhenNotSignIn();
            }
        }
        if (requestCode == RC_GETTHING_START) {
            if (resultCode == RESULT_OK) {
                //when setthing profile
                Profile profile = (Profile)data.getSerializableExtra("user_setting");
                Log.d("setting_result", profile.toString());
                profilesRef.child(auth.getCurrentUser().getUid()).setValue(profile);
            } else {

            }
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        FoodUtils foodUtils = new FoodUtils();
//        List<CompactFood> list_food = foodUtils.getFoodList("noodle",0);
//        for(int i=0;i<list_food.size();i++){
//            Log.e("food_print", list_food.get(i).getName());
//        }


        Firebase.setAndroidContext(this);
        //check firebase auth
        auth = FirebaseAuth.getInstance();
        if (auth.getCurrentUser() != null) {
            // already signed in
            isLogin = 1;

            setContentView(R.layout.activity_main);
            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);

            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

            ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                    this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
            drawer.setDrawerListener(toggle);
            toggle.syncState();


            navigationView = (NavigationView) findViewById(R.id.nav_view);
            navigationView.setNavigationItemSelectedListener(this);

            foodListsRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    listOfListFood = new ArrayList<>();
                    for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                        ListFood listFood = postSnapshot.getValue(ListFood.class);
                        listOfListFood.add(listFood);
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

            profilesRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    currentProfile = dataSnapshot.child(auth.getCurrentUser().getUid()).getValue(Profile.class);
                    try{
                        if(currentProfile.toString() == null){
                            Intent getthingstartintent = new Intent(getApplicationContext(), SetProfileActivity.class);
                            startActivityForResult(getthingstartintent, RC_GETTHING_START);
                        }
                        else {
                            Log.d("currentProfile:", currentProfile.toString());
                            canGetCurrentProfile = 1;
                            containerPagerAdapter = new MainContainerPagerAdapter(getSupportFragmentManager(), currentProfile, listOfListFood);

                            mainContainerPager = (ViewPager) findViewById(R.id.container);
                            mainContainerPager.setAdapter(containerPagerAdapter);
                        }
                    }
                    catch (Exception e){
                        Intent getthingstartintent = new Intent(getApplicationContext(), SetProfileActivity.class);
                        startActivityForResult(getthingstartintent, RC_GETTHING_START);
                    }
                }

                @Override
                public void onCancelled(DatabaseError error) {
                    // Failed to read value
                    Log.w("firebasetest", "Failed to read value.", error.toException());
                    if(auth.getCurrentUser() != null) {
                        Intent getthingstartintent = new Intent(getApplicationContext(), SetProfileActivity.class);
                        startActivityForResult(getthingstartintent, RC_GETTHING_START);
                    }
                }
            });

            View headerLayout = navigationView.getHeaderView(0);
            //View headerLayout = navigationView.inflateHeaderView(R.layout.nav_header_main);
            nav_header_name = (TextView) headerLayout.findViewById(R.id.header_nav_username);
            nav_header_email = (TextView) headerLayout.findViewById(R.id.header_nav_email);
            nav_header_image = (ImageView) headerLayout.findViewById(R.id.header_nav_image);
            //set header
            nav_header_name.setText(auth.getCurrentUser().getDisplayName());
            nav_header_email.setText(auth.getCurrentUser().getEmail());
            ImageLoadTask imagedownload = new ImageLoadTask("" + auth.getCurrentUser().getPhotoUrl());
            imagedownload.execute();
            nav_header_image.setImageBitmap(imagedownload.getBitmap());




        } else {
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
            mainContainerPager.setCurrentItem(0);
        } else if (id == R.id.nav_daily) {
            mainContainerPager.setCurrentItem(1);
        } else if (id == R.id.nav_list) {
            mainContainerPager.setCurrentItem(2);
        } else if (id == R.id.nav_search_food) {
          Intent intent = new Intent(getApplicationContext(), SearchFoodActivity.class);
            startActivity(intent);
        }
        else if (id == R.id.nav_setting) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_sync) {

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
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    void getCurrentProfile(){


    }

    void setWhenNotSignIn() {
        startActivityForResult(
                AuthUI.getInstance()
                        .createSignInIntentBuilder()
                        .setProviders(
                                AuthUI.EMAIL_PROVIDER
                                , AuthUI.GOOGLE_PROVIDER
                                , AuthUI.FACEBOOK_PROVIDER)
                        .setLogo(R.drawable.eatdee_logo)
                        //.setLogo()
                        .build(),
                RC_SIGN_IN);

    }
}
