package com.example.lion.tetris.activity;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;
import android.widget.TextView;

import com.example.lion.tetris.R;
import com.example.lion.tetris.database.DatabaseHighscoreHelper;
import com.example.lion.tetris.database.DatabaseHighscoreInfo;
import com.example.lion.tetris.highscore.Highscore;
import com.example.lion.tetris.highscore.HighscoreAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class HighscoresActivity extends AppCompatActivity {
    private List<Highscore> localList, worldList;
    private RecyclerView recyclerView;
    private HighscoreAdapter hAdapter;
    private boolean currentScreenLocal = true;
    private float xStart;
    static final int minSwipeDistance = 77;
    FirebaseAuth auth;
    FirebaseDatabase database;
    DatabaseReference reference;
    Highscore header;
    TextView textHeader, textHeaderInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_highscores);

        auth = FirebaseAuth.getInstance();

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        localList = new ArrayList<>();
        worldList = new ArrayList<>();
        header = new Highscore("Name", "Score", "Level", "Date");
        textHeader = (TextView) findViewById(R.id.highscoreHeader);
        textHeaderInfo = (TextView) findViewById(R.id.highscoreHeaderInfo);

        hAdapter = new HighscoreAdapter(localList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        recyclerView.setAdapter(hAdapter);

        if (auth.getCurrentUser() == null) {
            textHeaderInfo.setText(R.string.highscore_header_notloggedin_info);
        }

        prepareLocalHighscoreData();
        prepareWorldHighscoreData();



    }

    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                xStart = event.getX();
                break;
            case MotionEvent.ACTION_UP:
                if (xStart - event.getX() > minSwipeDistance || event.getX() - xStart > minSwipeDistance) {
                    swiped();
                }

        }
        return super.onTouchEvent(event);
    }

    private void swiped() {
        if (!currentScreenLocal) {
            hAdapter.setHighscoreList(localList);
            hAdapter.notifyDataSetChanged();
            textHeader.setText(R.string.highscore_header_local);
            textHeaderInfo.setText(R.string.highscore_header_local_info);
            currentScreenLocal = !currentScreenLocal;
        }
        else if (auth.getCurrentUser() != null) {
            hAdapter.setHighscoreList(worldList);
            hAdapter.notifyDataSetChanged();
            textHeader.setText(R.string.highscore_header_global);
            textHeaderInfo.setText(R.string.highscore_header_global_info);
            currentScreenLocal = !currentScreenLocal;
        }
    }

    private void prepareLocalHighscoreData() {
        Highscore Highscore;
        String name, score, level, date;
        DatabaseHighscoreHelper dbHelper = DatabaseHighscoreHelper.getHelper(this);
        Cursor rs = dbHelper.query(DatabaseHighscoreInfo.LocalHighscoreTables.LOCALHIGHSCORE, new String[]{"*"}, null, null, null, null, null);
        rs.moveToFirst();
        localList.add(header);
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
                    worldList.add(header);
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
            for (int j = 2; j < (n - i); j++) {
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
