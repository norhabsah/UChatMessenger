package com.example.uchat.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.BottomSheetDialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.uchat.MapsActivity;
import com.example.uchat.QRActivity;
import com.example.uchat.R;
import com.example.uchat.listener.LocationListener;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @project: UChat
 * @package: com.example.uchat.Fragments
 * @version: 1.0
 * @author: Habsah <nor.habsah97@gmail.com>
 * @description: description
 * @since: 2019-05-20 15:01
 */

public class BottomSheetFragment extends BottomSheetDialogFragment implements View.OnClickListener {

    private ImageView ivCamera, ivLocation, ivQr, ivGallery;
    private LocationListener listener;
    private int LOCATION_REQUEST = 1001;
    private int QR_REQUEST = 1002;

    public BottomSheetFragment() {
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_bottom_sheet, container, false);

        ivCamera = view.findViewById(R.id.iv_camera);
        ivLocation = view.findViewById(R.id.iv_location);
        ivQr = view.findViewById(R.id.iv_qr);
        ivGallery = view.findViewById(R.id.iv_gallery);

        ivCamera.setOnClickListener(this);
        ivLocation.setOnClickListener(this);
        ivQr.setOnClickListener(this);
        ivGallery.setOnClickListener(this);


        return view;
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){

            case R.id.iv_camera:
                takePicture();
                dismiss();
                break;

            case R.id.iv_location:

                Intent mapActivity = new Intent(getActivity(), MapsActivity.class);
                getActivity().startActivityForResult(mapActivity, LOCATION_REQUEST);
                dismiss();
                break;

            case R.id.iv_qr:
                Intent qrActivity = new Intent(getActivity(), QRActivity.class);
                getActivity().startActivityForResult(qrActivity, QR_REQUEST);
                dismiss();
                break;

            case R.id.iv_gallery:
                break;
        }
    }


    public void takePicture() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//        Uri file = Uri.fromFile(getOutputMediaFile());
//        intent.putExtra(MediaStore.EXTRA_OUTPUT, file);
//        Application.picUri = file;
        getActivity().startActivityForResult(intent, 1003);
    }

    private static File getOutputMediaFile(){
        File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES), "UChat");

        if (!mediaStorageDir.exists()){
            if (!mediaStorageDir.mkdirs()){
                Log.d("UChat", "failed to create directory");
                return null;
            }
        }

        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());

        File file = new File(mediaStorageDir.getPath() + File.separator +
                "IMG_"+ timeStamp + ".jpg");

        return  file;
    }

}