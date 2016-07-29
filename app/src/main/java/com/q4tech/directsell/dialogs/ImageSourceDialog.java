package com.q4tech.directsell.dialogs;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.q4tech.directsell.R;

/**
 * Created by alex.perez on 29/07/2016.
 */
public class ImageSourceDialog extends NoTitleDialog {

    private ImageSourceListener listener;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_image_source, container, false);

        listener = (ImageSourceListener) getActivity();

        view.findViewById(R.id.camera_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.takePhoto();
                dismiss();
            }
        });

        view.findViewById(R.id.gallery_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.openImagesGallery();
                dismiss();
            }
        });

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    public interface ImageSourceListener {
        void openImagesGallery();

        void takePhoto();
    }
}
