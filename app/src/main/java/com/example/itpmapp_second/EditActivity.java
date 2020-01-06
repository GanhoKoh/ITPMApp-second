package com.example.itpmapp_second;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import com.example.itpmapp_second.databases.ITPMDatabaseOpenHelper;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class EditActivity extends AppCompatActivity {

    private static final String KEY_ID  = "key_id";
    private static final String KEY_TITLE = "key_title";
    private EditText mTitleEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mTitleEditText = findViewById(R.id.titleEditText);

        final int selectId = getIntent().getIntExtra(KEY_ID, -1);

        ActionBar actionBar = getSupportActionBar();

        if(selectId == -1 && actionBar != null) {
            actionBar.setTitle(R.string.a_new);
        } else {
            actionBar.setTitle(R.string.edit);
        }

        String title = getIntent().getStringExtra(KEY_TITLE);

        if(title != null) {
            mTitleEditText.setText(title);
        } else {
            mTitleEditText.setText("");
        }

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = mTitleEditText.getText().toString();
                if(title.isEmpty()) {
                    Toast toast = Toast.makeText(EditActivity.this,
                            getString(R.string.error_no_title), Toast.LENGTH_SHORT);
                    toast.show();
                } else {
                    ContentValues contentValues = new ContentValues();
                    SQLiteDatabase db = new ITPMDatabaseOpenHelper(EditActivity.this).getWritableDatabase();

                    if(selectId == -1) {
                        contentValues.put(ITPMDatabaseOpenHelper.COLUMN_TITLE, mTitleEditText.getText().toString());
                        db.insert(ITPMDatabaseOpenHelper.TABLE_NAME, null, contentValues);
                    } else {
                        contentValues.put(ITPMDatabaseOpenHelper._ID, selectId);
                        contentValues.put(ITPMDatabaseOpenHelper.COLUMN_TITLE, mTitleEditText.getText().toString());
                        db.update(ITPMDatabaseOpenHelper.TABLE_NAME,
                                contentValues,
                                ITPMDatabaseOpenHelper._ID + "=" + selectId,
                                null);

                    }
                    db.close();
                    finish();
                }
            }
        });

    }

    public static Intent createIntent(Context context, int id, String title) {
        Intent intent = new Intent(context, EditActivity.class);
        intent.putExtra(KEY_ID, id);
        intent.putExtra(KEY_TITLE, title);
        return intent;
    }
}
