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
    }

    public void addBoardPanel(View view) {
        startActivity(new Intent(this, AddBoardPanel.class));
    }

    public void stockPanel(View view) {
        startActivity(new Intent(this, StockPanel.class));
    }
}
