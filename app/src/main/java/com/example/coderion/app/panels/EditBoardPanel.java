package com.example.coderion.app.panels;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.coderion.app.R;
import com.example.coderion.app.model.Board;
import com.example.coderion.app.model.ExtendedBoard;
import com.example.coderion.app.model.ImageConverter;
import com.example.coderion.app.model.Values;
import com.example.coderion.app.service.RestService;
import com.example.coderion.app.service.Validator;

import org.springframework.http.HttpStatus;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by coderion on 20.11.17.
 */

public class EditBoardPanel extends AddBoardPanel implements ImageConverter, AdapterView.OnItemSelectedListener {

    List<String> categoriesEdit = new ArrayList<>(Arrays.asList("Tekstolit", "Gips", "Kupa"));
    Button save;
    ImageButton imageEdit;
    Spinner materialEdit;
    EditText sizeEdit;
    EditText thicknessEdit;
    EditText placeEdit;
    Long ID;
    ExtendedBoard extendedBoard;
    String itemEdit;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_board_layout);
        ID = getIntent().getExtras().getLong("ID");

        save = findViewById(R.id.addABP);
        imageEdit = findViewById(R.id.imageABP);
        materialEdit = findViewById(R.id.materialABP);
        sizeEdit = findViewById(R.id.sizeABP);
        thicknessEdit = findViewById(R.id.thicknessABP);
        placeEdit = findViewById(R.id.placeABP);
        save.setText("Zapisz");

        materialEdit.setOnItemSelectedListener(this);
        addItemsOnSpinner();

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveBoard(view);
            }
        });

        try {
            extendedBoard = (ExtendedBoard) new RestService(Values.FIND_BY_ID).execute(Values.MAIN_LINK+ID).get().getBody();
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "Nie udało się znaleźć płyty", Toast.LENGTH_SHORT);
        }
        int pos = 0;
        for (String x : categoriesEdit) {
            if (extendedBoard.getBoard().getMaterial().equals(x)) break;
            ++pos;
        }
        if (extendedBoard.getImg() != null) imageEdit.setImageBitmap(decode(extendedBoard.getImg()));
        materialEdit.setSelection(pos);
        sizeEdit.setText(extendedBoard.getBoard().getSize());
        thicknessEdit.setText(extendedBoard.getBoard().getThickness()+"");
        placeEdit.setText(extendedBoard.getBoard().getPlace());

        imageEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                takeAPicture(view);
            }
        });
    }

    private void saveBoard(View view) {
        Validator validator = new Validator(sizeEdit.getText().toString());
        List<String> validatorResponse = validator.validate();
        if (!validatorResponse.isEmpty()) {
            Toast.makeText(this, validatorResponse.get(0), Toast.LENGTH_LONG).show();
        } else {
            Board board = new Board();
            board.setMaterial(itemEdit);
            board.setSize(sizeEdit.getText().toString());
            board.setThickness(Double.valueOf(thicknessEdit.getText().toString()));
            board.setPlace(placeEdit.getText().toString());
            board.setImage(hasImage);

            byte[] array = encode(((BitmapDrawable) imageEdit.getDrawable()).getBitmap());
            HttpStatus httpStatus = null;
            try {
                httpStatus = new RestService(board, array, Values.ADD_NEW_BOARD).execute(Values.MAIN_LINK+"/"+ID).get().getStatusCode();
            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(this, "Nie udało się dodać płyty", Toast.LENGTH_SHORT);

            }
            if (HttpStatus.CREATED.equals(httpStatus)) {
                Toast.makeText(this, "Płyta zapisana", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this, "Nie udało się zapisać płyty", Toast.LENGTH_LONG).show();
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

    private void setPic() {
        int targetW = imageEdit.getWidth();
        int targetH = imageEdit.getHeight();

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
        imageEdit.setImageBitmap(bitmap);
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        itemEdit = adapterView.getItemAtPosition(i).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
    }

    private void addItemsOnSpinner() {
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, categoriesEdit);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        materialEdit.setAdapter(dataAdapter);
    }
}
