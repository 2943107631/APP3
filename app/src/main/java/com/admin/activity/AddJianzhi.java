package com.admin.activity;

import android.Manifest;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;

import com.example.R;
import com.sqlite.DBHelper1;
import com.sqlite.DBHelper2;

public class AddJianzhi extends AppCompatActivity {

    private EditText title;
    private EditText price;
    private EditText content;
    private EditText location;
    private EditText druation;
    private EditText type;
    private AppCompatButton add;
    private ImageView image;

    private String imagePath;
    private String userId;
    private String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_jianzhi);
        userId = getIntent().getStringExtra("userId");
        initView();
        initData();

        image.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(intent, 100);
        });

        add.setOnClickListener(v -> {
            DBHelper2 dbHelper2 = new DBHelper2(this);
            SQLiteDatabase db = dbHelper2.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put("userId", userId);
            values.put("username", username);
            values.put("title", title.getText().toString());
            values.put("content", content.getText().toString());
            values.put("image", imagePath);
            values.put("location", location.getText().toString());
            values.put("price", price.getText().toString());
            values.put("druation", druation.getText().toString());
            values.put("type", type.getText().toString());
            long insert = db.insert("jianzhi", null, values);
            if (insert != -1) {
                Toast.makeText(this, "添加成功", Toast.LENGTH_SHORT).show();
                finish();
            } else {
                Toast.makeText(this, "添加失败", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initData() {
        DBHelper1 dbHelper1 = new DBHelper1(this);
        SQLiteDatabase db = dbHelper1.getReadableDatabase();
        String sqlQuery = "SELECT * FROM user WHERE id = ?";
        Cursor cursor = db.rawQuery(sqlQuery, new String[]{userId});
        if (cursor.moveToFirst()) {
            username = cursor.getString(cursor.getColumnIndex("username"));
        }
    }

    private void initView() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.getChildAt(0).setOnClickListener(v -> finish());
        ((TextView) toolbar.getChildAt(1)).setText("添加兼职");
        toolbar.getChildAt(2).setVisibility(View.GONE);

        title = findViewById(R.id.title);
        price = findViewById(R.id.price);
        content = findViewById(R.id.content);
        location = findViewById(R.id.location);
        druation = findViewById(R.id.druation);
        type = findViewById(R.id.type);
        add = findViewById(R.id.add);
        image = findViewById(R.id.image);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable @org.jetbrains.annotations.Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == 100) {
                Uri selectedImageUri = data.getData();
                imagePath = getPath(selectedImageUri);
                image.setImageURI(Uri.parse(imagePath));
            }
        }
    }

    private String getPath(Uri uri) {
        String[] projection = {MediaStore.Images.Media.DATA};
        Cursor cursor = getContentResolver().query(uri, projection, null, null, null);
        if (cursor != null) {
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            String path = cursor.getString(column_index);
            cursor.close();
            return path;
        } else {
            return null;
        }
    }
}