package com.example.itpmapp_second;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ListView mListView;
    private ArrayAdapter<String> mAdapter;
    //"ホーム","事業内容","企業情報","採用情報","お問い合わせ"

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.mainToolBar);
        setSupportActionBar(toolbar);

        mListView = findViewById(R.id.main_list);

        mAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, new ArrayList<String>());

        mListView.setAdapter(mAdapter);
        //通常クリックのリスナー
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d(MainActivity.class.getSimpleName(), position + "項目目のリストアイテムが押されました");
                String title = String.valueOf(parent.getItemAtPosition(position));
                Intent intent = EditActivity.createIntent(MainActivity.this, title);
                startActivity(intent);
            }
        });
        //長押しクリックのリスナー
        mListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d(MainActivity.class.getSimpleName(), position + "項目目のリストアイテムが長押しされました");
                String  title = String.valueOf(parent.getItemAtPosition(position));
                mAdapter.remove(title);
                return true;
            }
        });

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, EditActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        displayDataList();
    }

    private void displayDataList() {
        mAdapter.clear();

        List<String> dataList = Arrays.asList("ホーム","事業内容","企業情報","採用情報","お問い合わせ");
        mAdapter.addAll(dataList);

        mAdapter.notifyDataSetChanged();
    }
}
