package com.example.coderion.app.panels;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.coderion.app.R;
import com.example.coderion.app.model.Board;
import com.example.coderion.app.model.ImageConverter;
import com.example.coderion.app.model.Values;
import com.example.coderion.app.service.RestService;
import com.example.coderion.app.service.Validator;

import org.springframework.http.HttpStatus;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


/**
 * Created by coderion on 20.11.17.
 */

public class AddBoardPanel extends AppCompatActivity implements ImageConverter {

    static final int REQUEST_IMAGE_CAPTURE = 1;
    private Uri imageUri;
    String mCurrentPhotoPath;

    Validator validation;

    Button add;
    ImageButton image;
    EditText material;
    EditText thickness;
    EditText size;
    EditText place;
    Boolean hasImage;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_board_layout);

        add = findViewById(R.id.addABP);
        image = findViewById(R.id.imageABP);
        material = findViewById(R.id.materialABP);
        thickness = findViewById(R.id.thicknessABP);
        size = findViewById(R.id.sizeABP);
        place = findViewById(R.id.placeABP);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addBoard(view);
            }
        });

        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                takeAPicture(view);
            }
        });
    }

    public void addBoard(View view) {
        validation = new Validator(size.getText().toString());
        List<String> validationResponse = validation.validate();
        if (!validationResponse.isEmpty()) {
            Toast.makeText(getApplicationContext(), validationResponse.get(0), Toast.LENGTH_LONG).show();
        } else {
            Board plyta = new Board();
            plyta.setMaterial(material.getText().toString());
            plyta.setSize(size.getText().toString());
            plyta.setThickness(Double.valueOf(thickness.getText().toString()));
            plyta.setPlace(place.getText().toString());
            plyta.setImage(hasImage);

            byte[] array = encode(((BitmapDrawable) image.getDrawable()).getBitmap());
            HttpStatus httpStatus = null;
            try {
                httpStatus = new RestService(plyta, array, Values.ADD_NEW_BOARD).execute(Values.MAIN_LINK).get().getStatusCode();
            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(this, "Nie udało się dodać płyty", Toast.LENGTH_SHORT);
            }
            if (HttpStatus.CREATED.equals(httpStatus)) {
                Toast.makeText(this, "Płyta zapisana", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this, "Nie udało się dodać płyty", Toast.LENGTH_LONG).show();
            }
        }
    }

    public void takeAPicture(View view) {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                ex.printStackTrace();
                Toast.makeText(this, "Nie udało się zapisać obrazka", Toast.LENGTH_SHORT).show();
            }
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(this, "com.example.android.fileprovider", photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            galleryAddPic();
            setPic();
            hasImage = true;
        }
    }

    private File createImageFile() throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(imageFileName, ".jpg", storageDir);

        mCurrentPhotoPath = image.getAbsolutePath();
        return image;
    }

    public void galleryAddPic() {
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        File f = new File(mCurrentPhotoPath);
        Uri contentUri = Uri.fromFile(f);
        mediaScanIntent.setData(contentUri);
        this.sendBroadcast(mediaScanIntent);
    }

    private void setPic() {
        int targetW = image.getWidth();
        int targetH = image.getHeight();

        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(mCurrentPhotoPath, bmOptions);
        int photoW = bmOptions.outWidth;
        int photoH = bmOptions.outHeight;

        int scaleFactor = Math.min(photoW/targetW, photoH/targetH);

        bmOptions.inJustDecodeBounds = false;
        bmOptions.inSampleSize = scaleFactor;
        bmOptions.inPurgeable = true;

        Bitmap bitmap = BitmapFactory.decodeFile(mCurrentPhotoPath, bmOptions);
        image.setImageBitmap(bitmap);
    }

    @Override
    public byte[] encode(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 90, byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }

    @Override
    public Bitmap decode(byte[] array) {
        return BitmapFactory.decodeByteArray(array, 0, array.length);
    }
}
