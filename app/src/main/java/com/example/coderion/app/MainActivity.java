package com.example.coderion.app;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.example.coderion.app.panels.AddBoardPanel;
import com.example.coderion.app.panels.StockPanel;

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
