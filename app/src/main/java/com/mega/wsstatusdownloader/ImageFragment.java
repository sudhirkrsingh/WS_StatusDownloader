package com.mega.wsstatusdownloader;

import android.Manifest;
import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Environment;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.mega.wsstatusdownloader.R;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import java.io.File;
import java.util.ArrayList;
import java.util.List;


public class ImageFragment extends Fragment {
    RecyclerView recyclerView;
    SwipeRefreshLayout swipeRefreshLayout;

    StoryAdapter adapter;
    File[] files;
    ArrayList<Object> filesList = new ArrayList<>();

    public ImageFragment(){

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_image, container, false);

        recyclerView = view.findViewById(R.id.recyclerview);
        swipeRefreshLayout = view.findViewById(R.id.swipeRecyclerView);

       getStatus();

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getStatus();
                swipeRefreshLayout.setRefreshing(false);
            }
        });


        return view;

    }

    private void getStatus() {

        if (Constant.STATUS_DIRECTORY.exists()) {

            filesList = new ArrayList<>();
            getData(Constant.STATUS_DIRECTORY);

        } else if (Constant.STATUS_DIRECTORY_NEW.exists()) {

            filesList = new ArrayList<>();
            getData(Constant.STATUS_DIRECTORY_NEW);

        }

    }
    private void getData(File link) {

        StoryModel f;
        File[] targetDirector;
        targetDirector = link.listFiles();

        if (targetDirector != null && targetDirector.length >0) {
            for (int i = 0; i <targetDirector.length; i++) {
                File file = targetDirector[i];
                f = new StoryModel();
                f.setUri(Uri.fromFile(file));
                f.setPath(targetDirector[i].getAbsolutePath());
                f.setFilename(file.getName());

                if (!f.getUri().toString().endsWith(".nomedia")) {
                    filesList.add(f);
                }


                recyclerView.setHasFixedSize(true);
                recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                adapter = new StoryAdapter(getActivity(), filesList);
                recyclerView.setAdapter(adapter);
                adapter.notifyDataSetChanged();


            }
        }
    }
}