package com.mega.wsstatusdownloader;

import static android.os.Build.VERSION.SDK_INT;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.mega.wsstatusdownloader.R;
import com.google.android.material.tabs.TabLayout;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    TabLayout tabLayout;
    ViewPager viewPager;
    MainAdpter adapter;
    ImageView whatsApp,shareButton;
    Toolbar toolbar;
    int requestCode=1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar =(Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        checkPermission();
        checkConnection();


        tabLayout=(TabLayout) findViewById(R.id.tablayout1);
        viewPager=(ViewPager) findViewById(R.id.vpager);
        whatsApp=(ImageView)findViewById(R.id.whatsApp);
        shareButton=(ImageView)findViewById(R.id.app_share);

        whatsApp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isWhatsappInstalled()){

               Intent intent = getPackageManager().getLaunchIntentForPackage("com.whatsapp");
               startActivity(intent);

                }else {
                    Toast.makeText(MainActivity.this, "whatsapp is not installed", Toast.LENGTH_SHORT).show();
                }
            }
        });

        shareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent  = new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                String body = "Check out this cool App...";
                String linkOfApp = "https://play.google.com/store/apps/details?id=com.mega.wsstatusdownloader";
                intent.putExtra(Intent.EXTRA_SUBJECT,body);
                intent.putExtra(Intent.EXTRA_TEXT,linkOfApp);
                startActivity(Intent.createChooser(intent,"Share-Via"));
            }
        });

        adapter = new MainAdpter(getSupportFragmentManager());

        adapter.AddFragment(new ImageFragment(),"Status");
        adapter.AddFragment(new DownloadedFragment(),"Downloaded");
        adapter.AddFragment(new ChatFragment(),"Chat");

        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);



    }

    private void checkPermission() {
        if(SDK_INT>23){
            if(checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)== PackageManager.PERMISSION_GRANTED)
            {
                
            }
            else
            {
                ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},requestCode);
            }
            
        }
        else {
            Toast.makeText(MainActivity.this, "", Toast.LENGTH_SHORT).show();
        }
    }


    private class MainAdpter  extends FragmentPagerAdapter {
        ArrayList<Fragment> fragmentArrayList = new ArrayList<>();
        ArrayList<String> stringArrayList = new ArrayList<>();

        public void AddFragment(Fragment fragment,String s){

            fragmentArrayList.add(fragment);
            stringArrayList.add(s);
        }
        public MainAdpter(@NonNull FragmentManager fm) {
            super(fm);
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
           return fragmentArrayList.get(position);
        }

        @Override
        public int getCount() {
           return fragmentArrayList.size();
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return stringArrayList.get(position);
        }
    }
    private Boolean isWhatsappInstalled(){
        PackageManager packageManager = getPackageManager();
        boolean whatsAppInstalled;

        try {
            packageManager.getPackageInfo("com.whatsapp",PackageManager.GET_ACTIVITIES);
            whatsAppInstalled = true;

        }
        catch (PackageManager.NameNotFoundException e){
            whatsAppInstalled = false;

        }
        return whatsAppInstalled;
    }
    public void checkConnection(){
        ConnectivityManager manager = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = manager.getActiveNetworkInfo();
        if(null!=activeNetwork){
            if(activeNetwork.getType()==ConnectivityManager.TYPE_WIFI) {

            }  else if (activeNetwork.getType()==ConnectivityManager.TYPE_MOBILE) {

            }
              else {
                Toast.makeText(this, "No Internet Connection", Toast.LENGTH_SHORT).show();
            }
        }
        else {
            Toast.makeText(this, "No Internet Connection", Toast.LENGTH_SHORT).show();
        }

    }
}