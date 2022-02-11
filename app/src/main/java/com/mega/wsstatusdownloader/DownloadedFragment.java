package com.mega.wsstatusdownloader;

import android.Manifest;
import android.net.Uri;
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
import android.widget.TextView;
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


public class DownloadedFragment extends Fragment {
    RecyclerView recyclerView;
    SwipeRefreshLayout swipeRefreshLayout;

    DownloadAdapter downloadAdapter;
    File[] files;
    ArrayList<Object> filesList = new ArrayList<>();



    public DownloadedFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

            View view = inflater.inflate(R.layout.fragment_downloaded, container, false);

            recyclerView = view.findViewById(R.id.recyclerview);
            swipeRefreshLayout = view.findViewById(R.id.swipeRecyclerView);

            filesList = new ArrayList<>();
            getData();



        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                filesList = new ArrayList<>();
                getData();
                swipeRefreshLayout.setRefreshing(false);
            }
        });



        return view;

    }

    private void   getData() {


        DownloadModel f;
        String targetPath = Environment.getExternalStorageDirectory().getAbsolutePath() + Constant.DOWNLOAD_FOLDER_NAME;
        File targetDirector = new File(targetPath);

        files = targetDirector.listFiles();

        if (files != null) {
            for (int i = 0; i <files.length; i++) {
                File file = files[i];
                f = new DownloadModel();
                f.setDownloadUri(Uri.fromFile(file));
                f.setDownloadPath(files[i].getAbsolutePath());
                f.setDownloadFilename(file.getName());

                if (!f.getDownloadUri().toString().endsWith(".nomedia")) {
                    filesList.add(f);
                }


                recyclerView.setHasFixedSize(true);
                recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                downloadAdapter = new DownloadAdapter(getActivity(), filesList);
                recyclerView.setAdapter(downloadAdapter);
                downloadAdapter.notifyDataSetChanged();


            }
        }
    }

}
