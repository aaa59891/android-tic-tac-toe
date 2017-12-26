package com.example.chongchenlearn901.tic_tac_toe;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    private enum tableStateE {
        YELLOW,
        RED,
        EMPTY
    }

    private List<ImageView> ivList;
    private Button btnRestart;

    private static final tableStateE[] oriTableState = {tableStateE.EMPTY, tableStateE.EMPTY, tableStateE.EMPTY, tableStateE.EMPTY, tableStateE.EMPTY, tableStateE.EMPTY, tableStateE.EMPTY, tableStateE.EMPTY, tableStateE.EMPTY};

    private tableStateE[] tableState = oriTableState.clone();
    private static int count = 0;
    private boolean gameState = true;
    private static boolean ox = true;

    private int[][] winSets = {
            {0, 1, 2},
            {3, 4, 5},
            {6, 7, 8},
            {0, 3, 6},
            {1, 4, 7},
            {2, 5, 8},
            {0, 4, 8},
            {2, 4, 6}
    };

    private View.OnClickListener listener = (v) -> {
        ImageView iv = (ImageView) v;

        if (iv.getDrawable() == null && this.gameState) {
            int resource = ox ? R.drawable.red : R.drawable.yellow;
            tableState[Integer.parseInt(iv.getTag().toString())] = ox ? tableStateE.RED : tableStateE.YELLOW;

            iv.setImageResource(resource);
            iv.setTranslationY(-1000);
            iv.animate().translationYBy(1000).setDuration(300);

            ox = !ox;
            count++;

            if (count > 4) {
                tableStateE winner = getWinner();
                if (winner != tableStateE.EMPTY || count == 9) {
                    String msg = count == 9 ? "No one is winner" : "Winner: " + winner.toString();
                    Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
                    this.gameState = false;
                    this.btnRestart.setVisibility(View.VISIBLE);
                    return;
                }
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initComponents();

        for (ImageView iv : this.ivList) {
            iv.setOnClickListener(this.listener);
        }
        this.btnRestart.setOnClickListener((v) -> {
            count = 0;
            ox = true;
            this.tableState = oriTableState.clone();
            this.gameState = true;
            for (ImageView iv : this.ivList) {
                iv.setImageDrawable(null);
            }
            v.setVisibility(View.INVISIBLE);
        });
    }

    private void initComponents() {
        ImageView iv1 = findViewById(R.id.imageView1);
        ImageView iv2 = findViewById(R.id.imageView2);
        ImageView iv3 = findViewById(R.id.imageView3);
        ImageView iv4 = findViewById(R.id.imageView4);
        ImageView iv5 = findViewById(R.id.imageView5);
        ImageView iv6 = findViewById(R.id.imageView6);
        ImageView iv7 = findViewById(R.id.imageView7);
        ImageView iv8 = findViewById(R.id.imageView8);
        ImageView iv9 = findViewById(R.id.imageView9);

        this.btnRestart = findViewById(R.id.btnRestart);

        this.ivList = new ArrayList<>(Arrays.asList(
                iv1,
                iv2,
                iv3,
                iv4,
                iv5,
                iv6,
                iv7,
                iv8,
                iv9
        ));
    }

    private tableStateE getWinner() {
        for (int[] set : this.winSets) {
            tableStateE first = this.tableState[set[0]];
            tableStateE second = this.tableState[set[1]];
            tableStateE third = this.tableState[set[2]];
            if (first == second && first == third && first != tableStateE.EMPTY) {
                return first;
            }
        }
        return tableStateE.EMPTY;
    }
}
