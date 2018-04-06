package com.example.lion.tetris.activity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.lion.tetris.R;
import com.example.lion.tetris.database.DatabaseSettingsHelper;
import com.example.lion.tetris.database.DatabaseSettingsInfo;
import com.example.lion.tetris.model.Settings;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;

import java.io.Console;
import java.util.ArrayList;

public class SettingsActivity extends AppCompatActivity {
    private PieChart mChart;
    private Settings settings;
    private EditText i, j, l, o, s, t, z;
    private Button updateButton, resetButton;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        mChart = (PieChart) findViewById(R.id.chart);
        mChart.setDescription("");
        mChart.setUsePercentValues(true);
        mChart.setHoleRadius(32);
        mChart.setTransparentCircleRadius(0);
        mChart.setHoleColorTransparent(true);
        mChart.setTouchEnabled(true);
        mChart.setDrawSliceText(true);
        mChart.getLegend().setEnabled(true);
        mChart.animateY(900, Easing.EasingOption.EaseInOutQuad);
        settings = new Settings();
        getLocalSettingsValues();
        setInputValues();

        updateButton = (Button) findViewById(R.id.updateOddsButton);
        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateValuesFromInput();
            }
        });

        resetButton = (Button) findViewById(R.id.resetOddsButton);
        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resetOddsValues();
                setInputValues();
            }
        });
    }

    private void resetOddsValues() {
        settings.setAll(10, 10, 10, 10, 10, 10, 10);
        submitLocal();
        Log.e("am I working", "yes");
    }

    private void setInputValues() {
        i = (EditText) findViewById(R.id.iInput);
        i.setText(String.valueOf(settings.getI()));
        j = (EditText) findViewById(R.id.jInput);
        j.setText(String.valueOf(settings.getJ()));
        l = (EditText) findViewById(R.id.lInput);
        l.setText(String.valueOf(settings.getL()));
        o = (EditText) findViewById(R.id.oInput);
        o.setText(String.valueOf(settings.getO()));
        s = (EditText) findViewById(R.id.sInput);
        s.setText(String.valueOf(settings.getS()));
        t = (EditText) findViewById(R.id.tInput);
        t.setText(String.valueOf(settings.getT()));
        z = (EditText) findViewById(R.id.zInput);
        z.setText(String.valueOf(settings.getZ()));
    }

    private void updateValuesFromInput() {
        settings.setAll(
                Integer.parseInt(i.getText().toString()),
                Integer.parseInt(j.getText().toString()),
                Integer.parseInt(l.getText().toString()),
                Integer.parseInt(o.getText().toString()),
                Integer.parseInt(s.getText().toString()),
                Integer.parseInt(t.getText().toString()),
                Integer.parseInt(z.getText().toString())
        );
        submitLocal();

    }

    private void setData() {
        ArrayList<Entry> yValues = new ArrayList<>();
        ArrayList<String> xValues = new ArrayList<>();
        ArrayList<Integer> colors = new ArrayList<>();
        int indexCount = 0;

        if(settings.getI() > 0) {
            yValues.add(new Entry(settings.getI(), indexCount));
            xValues.add("I");
            colors.add(Color.rgb(219, 0, 0));
            indexCount++;
        }

        if(settings.getJ() > 0) {
            yValues.add(new Entry(settings.getJ(), indexCount));
            xValues.add("J");
            colors.add(Color.rgb(254, 255, 59));
            indexCount++;
        }

        if(settings.getL() > 0) {
            yValues.add(new Entry(settings.getL(), indexCount));
            xValues.add("L");
            colors.add(Color.rgb(88, 0, 149));
            indexCount++;
        }

        if(settings.getO() > 0) {
            yValues.add(new Entry(settings.getO(), indexCount));
            xValues.add("O");
            colors.add(Color.rgb(33, 0, 250));
            indexCount++;
        }

        if(settings.getS() > 0) {
            yValues.add(new Entry(settings.getS(), indexCount));
            xValues.add("S");
            colors.add(Color.rgb(115, 203, 253));
            indexCount++;
        }

        if(settings.getT() > 0) {
            yValues.add(new Entry(settings.getT(), indexCount));
            xValues.add("T");
            colors.add(Color.rgb(141, 248, 58));
            indexCount++;
        }

        if(settings.getZ() > 0) {
            yValues.add(new Entry(settings.getZ(), indexCount));
            xValues.add("Z");
            colors.add(Color.rgb(231, 152, 62));
        }

        PieDataSet dataSet = new PieDataSet(yValues, "Tetraminos");
        dataSet.setColors(colors);

        PieData data = new PieData(xValues, dataSet);
        mChart.setData(data); // bind dataset aan chart.
        mChart.invalidate();  // Aanroepen van een redraw
    }

    public void getLocalSettingsValues() {
        int i=10, j=10, l=10, o=10, s=10, t=10, z=10;
        DatabaseSettingsHelper dbHelper = DatabaseSettingsHelper.getHelper(this);
        Cursor rs = dbHelper.query(DatabaseSettingsInfo.LocalSettingsTables.LOCALSETTINGS, new String[]{"*"}, null, null, null, null, null);
        rs.moveToFirst();
        if (rs.getCount() > 0) {

            i = Integer.parseInt(rs.getString(rs.getColumnIndex("I")));
            j = Integer.parseInt((String) rs.getString(rs.getColumnIndex("J")));
            l = Integer.parseInt((String) rs.getString(rs.getColumnIndex("L")));
            o = Integer.parseInt((String) rs.getString(rs.getColumnIndex("O")));
            s = Integer.parseInt((String) rs.getString(rs.getColumnIndex("S")));
            t = Integer.parseInt((String) rs.getString(rs.getColumnIndex("T")));
            z = Integer.parseInt((String) rs.getString(rs.getColumnIndex("Z")));
        }
        rs.close();
        settings.setAll(i, j, l, o, s, t, z);
        setData();
    }

    private void submitLocal() {
        if (settings != null) {
            DatabaseSettingsHelper dbHelper = DatabaseSettingsHelper.getHelper(this);

            ContentValues values = new ContentValues();
            values.put(DatabaseSettingsInfo.LocalSettingsColumn.I, settings.getI());
            values.put(DatabaseSettingsInfo.LocalSettingsColumn.J, settings.getJ());
            values.put(DatabaseSettingsInfo.LocalSettingsColumn.L, settings.getL());
            values.put(DatabaseSettingsInfo.LocalSettingsColumn.O, settings.getO());
            values.put(DatabaseSettingsInfo.LocalSettingsColumn.S, settings.getS());
            values.put(DatabaseSettingsInfo.LocalSettingsColumn.T, settings.getT());
            values.put(DatabaseSettingsInfo.LocalSettingsColumn.Z, settings.getZ());

            Cursor rs = dbHelper.query(DatabaseSettingsInfo.LocalSettingsTables.LOCALSETTINGS, new String[]{"*"}, null, null, null, null, null);
            rs.moveToFirst();
            if (rs.getCount() > 0) {
                dbHelper.update(DatabaseSettingsInfo.LocalSettingsTables.LOCALSETTINGS, "1=1", values);

            } else {
                dbHelper.insert(DatabaseSettingsInfo.LocalSettingsTables.LOCALSETTINGS, null, values);
            }
        }
        getLocalSettingsValues();
    }
}