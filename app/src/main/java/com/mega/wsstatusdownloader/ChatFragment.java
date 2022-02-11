package com.mega.wsstatusdownloader;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.mega.wsstatusdownloader.R;
import com.hbb20.CountryCodePicker;


public class ChatFragment extends Fragment {

    CountryCodePicker countryCodePicker;
    EditText phone, message ;
    Button sendText;
    String messageStr , phoneStr = "";



    public ChatFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_chat, container, false);

        countryCodePicker = view.findViewById(R.id.countryCode);
        phone = view.findViewById(R.id.phoneNo);
        message = view.findViewById(R.id.message);
        sendText = view.findViewById(R.id.send);


        sendText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                messageStr = message.getText().toString();
                phoneStr = phone.getText().toString();

                if(!messageStr.isEmpty() && !phoneStr.isEmpty()){

                    countryCodePicker.registerCarrierNumberEditText(phone);
                    phoneStr = countryCodePicker.getFullNumber();

                    if(isWhatsappInstalled()){

                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://api.whatsapp.com/send?phone="+phoneStr+
                                "&text="+messageStr));
                        startActivity(intent);
                        message.setText("");
                        phone.setText("");
                    }else {
                        Toast.makeText(getContext(), "whatsapp is not installed", Toast.LENGTH_SHORT).show();
                    }

                }else
                {
                    Toast.makeText(getContext(), "Please fill in the Phone no. and message it can't be empty", Toast.LENGTH_SHORT).show();

                }
            }
        });




        return view;
    }
    private Boolean isWhatsappInstalled(){
        PackageManager packageManager = getContext().getPackageManager();
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
}