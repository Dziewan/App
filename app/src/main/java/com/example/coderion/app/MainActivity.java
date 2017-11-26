package com.example.coderion.app;

import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.coderion.app.model.Board;
import com.example.coderion.app.model.Values;
import com.example.coderion.app.panels.AddBoardPanel;
import com.example.coderion.app.panels.StockPanel;
import com.example.coderion.app.service.RestService;

import org.springframework.http.HttpStatus;

public class MainActivity extends AppCompatActivity {

    ImageButton newBoard;
    ImageButton stock;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        newBoard = findViewById(R.id.newBoard);
        stock = findViewById(R.id.stock);

        String[] a = {"Tekstolit", "Zielone szkło", "Brązowe szkło", "Czerwone szkło", "Delmat Epoxy", "Czarne szkło", "Żółte szkło"};
        String[] w = {"2000x1000", "2000x1500", "1000x1000", "2500x1500", "1000x500", "2000x2000", "1500x500"};
        Double[] g = {5.0, 2.3, 13.0, 14.3, 10.0, 6.5, 7.0};
        String[] p = {"23F", "2G", "13BA", "10A", "11A", "12B", "8G"};
//        for (int i = 0; i < 7; ++i) {
//            Board plyta = new Board();
//            plyta.setMaterial(a[i]);
//            plyta.setSize(w[i]);
//            plyta.setThickness(g[i]);
//            plyta.setPlace(p[i]);
//
//            byte[] array = null;
//            HttpStatus httpStatus = null;
//            try {
//                httpStatus = new RestService(plyta, array, Values.ADD_NEW_BOARD).execute(Values.MAIN_LINK).get().getStatusCode();
//            } catch (Exception e) {
//                e.printStackTrace();
//                Toast.makeText(this, "Nie udało się dodać płyty", Toast.LENGTH_SHORT);
//            }
//        }
    }

    public void addBoardPanel(View view) {
        startActivity(new Intent(this, AddBoardPanel.class));
    }

    public void stockPanel(View view) {
        startActivity(new Intent(this, StockPanel.class));
    }
}
