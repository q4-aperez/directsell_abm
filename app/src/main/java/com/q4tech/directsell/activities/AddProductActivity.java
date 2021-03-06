package com.q4tech.directsell.activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.FileProvider;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.GridLayout;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.q4tech.directsell.R;
import com.q4tech.directsell.dialogs.ImageSourceDialog;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by alex.perez on 28/07/2016.
 */
public class AddProductActivity extends CustomActivity implements NestedScrollView.OnScrollChangeListener, ImageSourceDialog.ImageSourceListener {

    private final static int DESCRIPTION_CODE = 101;
    private static final int REQUEST_CAMERA = 102;
    private static final int SELECT_FILE = 103;
    public final static String DESCRIPTION = "DESCRIPTION";

    private Drawable mActionBarBackgroundDrawable;
    private Toolbar mToolBar;
    private TextView mDescription;
    private View mHeader;
    private ImageView productImage;
    private GridLayout gridLayout;

    private int currentAlpha;
    private String mCurrentPhotoPath;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_add_product);

        mToolBar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolBar);

        mDescription = (TextView) findViewById(R.id.description);

        gridLayout = (GridLayout) findViewById(R.id.images_grid);

        mActionBarBackgroundDrawable = mToolBar.getBackground();
        mActionBarBackgroundDrawable.setAlpha(0);

        NestedScrollView scrollView = (NestedScrollView) findViewById(R.id.details_scroll_view);
        scrollView.setOnScrollChangeListener(this);

        findViewById(R.id.description).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AddProductActivity.this, DescriptionActivity.class);
                startActivityForResult(intent, DESCRIPTION_CODE);
            }
        });

        productImage = (ImageView) findViewById(R.id.product_image);

        mHeader = findViewById(R.id.product_header);
        mHeader.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ImageSourceDialog dialog = new ImageSourceDialog();
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                dialog.show(transaction, getClass().toString());
            }
        });

        setImages();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mActionBarBackgroundDrawable != null) {
            mActionBarBackgroundDrawable.setAlpha(255);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mActionBarBackgroundDrawable != null) {
            mActionBarBackgroundDrawable.setAlpha(currentAlpha);
        }
    }

    @Override
    public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
        final int headerHeight = mHeader.getHeight() - mToolBar.getHeight();
        final float ratio = (float) Math.min(Math.max(scrollY, 0), headerHeight) / headerHeight;
        final int newAlpha = (int) (ratio * 255);
        mActionBarBackgroundDrawable.setAlpha(newAlpha);
        currentAlpha = newAlpha;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case SELECT_FILE:
                    onSelectFromGalleryResult(data);
                    break;
                case REQUEST_CAMERA:
                    onCaptureImageResult(data);
                    break;
                case DESCRIPTION_CODE:
                    String description = data.getExtras().getString(DESCRIPTION);
                    mDescription.setText(description);
                    break;
            }
        }
    }

    @SuppressWarnings("deprecation")
    private void onSelectFromGalleryResult(Intent data) {

        Bitmap bm = null;
        if (data != null) {
            try {
                bm = MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(), data.getData());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        productImage.setImageBitmap(bm);
    }

    private void onCaptureImageResult(Intent data) {
//        Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
//        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
//        thumbnail.compress(Bitmap.CompressFormat.JPEG, 0, bytes);

//        File destination = new File(Environment.getExternalStorageDirectory(),
//                System.currentTimeMillis() + ".jpg");
//
//        FileOutputStream fo;
//        try {
//            destination.createNewFile();
//            fo = new FileOutputStream(destination);
//            fo.write(bytes.toByteArray());
//            fo.close();
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        Bitmap myBitmap = BitmapFactory.decodeFile(mCurrentPhotoPath);
        productImage.setImageBitmap(myBitmap);
    }


    @Override
    public void openImagesGallery() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select File"), SELECT_FILE);
    }

    @Override
    public void takePhoto() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        try {
            File imageFile = createImageFile();
            Uri photoURI = FileProvider.getUriForFile(this,
                    "com.q4tech.directsell.fileprovider",
                    imageFile);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
            startActivityForResult(intent, REQUEST_CAMERA);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        mCurrentPhotoPath = image.getAbsolutePath();

        return image;
    }

    private void setImages() {
//        View availableView = getLayoutInflater().inflate(R.layout.add_image_view, gridLayout, false);
//        GridLayout.LayoutParams param =new GridLayout.LayoutParams();
//        param.height = GridLayout.LayoutParams.WRAP_CONTENT;
//        param.width = GridLayout.LayoutParams.WRAP_CONTENT;
//        param.columnSpec = GridLayout.spec(0);
//        param.rowSpec = GridLayout.spec(0);
//        availableView.setLayoutParams (param);
//        gridLayout.addView(availableView);
//        TextView availableString = (TextView) availableView.findViewById(R.id.images_available);
//        availableString.setText(getResources().getQuantityString(R.plurals.images_available, 4, 4));
    }
}
