package com.example.coderion.app.panels;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.coderion.app.R;
import com.example.coderion.app.model.Board;
import com.example.coderion.app.model.Values;
import com.example.coderion.app.service.RestService;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by coderion on 20.11.17.
 */

public class StockPanel extends AppCompatActivity {

    Button showStock;
    Button delete;
    ListView boardList;
    TextView materialLabel;
    TextView thicknessLabel;
    TextView sizeLabel;
    TextView placeLabel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.stock_layout);

        showStock = findViewById(R.id.showStockSP);
        delete = findViewById(R.id.deleteSP);
        boardList = findViewById(R.id.boardListSP);
        materialLabel = findViewById(R.id.materialSP);
        thicknessLabel = findViewById(R.id.thicknessSP);
        sizeLabel = findViewById(R.id.sizeSP);
        placeLabel = findViewById(R.id.placeSP);

        materialLabel.setTextColor(Color.BLACK);
        sizeLabel.setTextColor(Color.BLACK);
        thicknessLabel.setTextColor(Color.BLACK);
        placeLabel.setTextColor(Color.BLACK);

        showStock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                List<Board> boardsList = null;
                try {
                    boardsList = (List<Board>) new RestService(Values.FIND_ALL).execute(Values.MAIN_LINK).get().getBody();
                } catch (Exception e) {
                    Toast.makeText(getBaseContext(), "Nie udało się znaleźć płyty", Toast.LENGTH_SHORT).show();
                    System.out.println(e.getMessage());
                }
                boardList.setAdapter(new StockAdapter(getBaseContext(), R.layout.board_layout, boardsList));
            }
        });
    }

    private class StockAdapter extends ArrayAdapter<Board> {

        private int layout;
        List<Board> boards = new ArrayList<>();

        public StockAdapter(@NonNull Context context, int resource, @NonNull List<Board> objects) {
            super(context, resource, objects);
            this.layout = resource;
            this.boards = objects;
        }

        @Override
        public int getViewTypeCount() {
            return getCount();
        }

        @Override
        public int getItemViewType(int position) {
            return position;
        }

        @SuppressLint("SetTextI18n")
        @NonNull
        @Override
        public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            final ViewHolder viewHolder;
            if (convertView == null) {
                viewHolder = new ViewHolder();
                LayoutInflater inflater = LayoutInflater.from(getContext());
                convertView = inflater.inflate(layout, parent, false);

                viewHolder.material = convertView.findViewById(R.id.materialViewSP);
                viewHolder.thickness = convertView.findViewById(R.id.thicknessViewSP);
                viewHolder.size = convertView.findViewById(R.id.sizeViewSP);
                viewHolder.place = convertView.findViewById(R.id.placeViewSP);
                viewHolder.tableRow = convertView.findViewById(R.id.tableRowSP);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }

            viewHolder.tableRow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getContext(), EditBoardPanel.class);
                    Bundle bundle = new Bundle();
                    bundle.putLong("ID", boards.get(position).getId());
                    intent.putExtras(bundle);
                    startActivity(intent);
                }
            });
            viewHolder.material.setText(boards.get(position).getMaterial());
            viewHolder.thickness.setText(""+boards.get(position).getThickness());
            viewHolder.size.setText(boards.get(position).getSize());
            viewHolder.place.setText(boards.get(position).getPlace());

            return convertView;
        }
    }

    public class ViewHolder {
        TextView material, size, thickness, place;
        CheckBox check;
        TableRow tableRow;
    }
}
