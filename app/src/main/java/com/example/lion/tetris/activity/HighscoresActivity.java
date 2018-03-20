package com.example.lion.tetris.activity;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.lion.tetris.R;
import com.example.lion.tetris.database.DatabaseHelper;
import com.example.lion.tetris.database.DatabaseInfo;
import com.example.lion.tetris.highscore.Highscore;
import com.example.lion.tetris.highscore.HighscoreAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;


public class HighscoresActivity extends AppCompatActivity {
    private List<Highscore> localList, worldList;
    private RecyclerView recyclerView;
    private HighscoreAdapter hAdapter;
    private Button highScoreSwitchButton;
    private boolean currentScreenLocal = true;
    FirebaseAuth auth;
    FirebaseDatabase database;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_highscores);

        auth = FirebaseAuth.getInstance();

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        localList = new ArrayList<>();
        worldList = new ArrayList<>();

        hAdapter = new HighscoreAdapter(localList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        recyclerView.setAdapter(hAdapter);

        prepareLocalHighscoreData();
        prepareWorldHighscoreData();

        highScoreSwitchButton = (Button) findViewById(R.id.highscoreSwitchButton);
        if (auth.getCurrentUser() == null) {
            highScoreSwitchButton.setVisibility(View.GONE);
        } else highScoreSwitchButton.setVisibility(View.VISIBLE);
        highScoreSwitchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (currentScreenLocal) {
                    hAdapter.setHighscoreList(worldList);
                    highScoreSwitchButton.setText(R.string.to_local_score);
                } else {
                    hAdapter.setHighscoreList(localList);
                    highScoreSwitchButton.setText(R.string.to_world_score);
                }
                hAdapter.notifyDataSetChanged();
                currentScreenLocal = !currentScreenLocal;
            }
        });


    }

    private void prepareLocalHighscoreData() {
        Highscore Highscore;
        String name, score, level, date;
        DatabaseHelper dbHelper = DatabaseHelper.getHelper(this);
        Cursor rs = dbHelper.query(DatabaseInfo.LocalHighscoreTables.LOCALHIGHSCORE, new String[]{"*"}, null, null, null, null, null);
        rs.moveToFirst();
        if (rs.getCount() > 0) {
            do {
                name = (String) rs.getString(rs.getColumnIndex("name"));
                score = (String) rs.getString(rs.getColumnIndex("score"));
                level = (String) rs.getString(rs.getColumnIndex("level"));
                date = (String) rs.getString(rs.getColumnIndex("date"));
                Highscore = new Highscore(name, score, level, date);
                localList.add(Highscore);
            } while (rs.moveToNext());
        }
        rs.close();
        localList = bubbleSort(localList);
        hAdapter.notifyDataSetChanged();
    }

    private void prepareWorldHighscoreData() {
        if (auth.getCurrentUser() != null) {
            database = FirebaseDatabase.getInstance();
            reference = database.getReference("highScores");
            reference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    worldList.clear();
                    for (DataSnapshot data : dataSnapshot.getChildren()) {
                        worldList.add(data.getValue(Highscore.class));
                    }
                    worldList = bubbleSort(worldList);
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }
    }

    private List<Highscore> bubbleSort(List<Highscore> list) {
        int n = list.size();
        Highscore temp;
        for (int i = 0; i < n; i++) {
            for (int j = 1; j < (n - i); j++) {
                if (Integer.valueOf(list.get(j - 1).getScore()) < Integer.valueOf(list.get(j).getScore())) {
                    temp = list.get(j - 1);
                    list.set(j - 1, list.get(j));
                    list.set(j, temp);
                }
            }
        }
        return list;
    }
}
