package com.user.activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.Toolbar;

import android.content.ContentValues;
import android.content.Intent;
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

import com.example.R;
import com.sqlite.DBHelper1;
import com.sqlite.DBHelper2;
import com.sqlite.DBHelper3;

public class AddXiaoyuan extends AppCompatActivity {

    private EditText title;
    private EditText price;
    private EditText content;
    private EditText degree;
    private AppCompatButton add;
    private ImageView image;

    private String imagePath;
    private String userId;
    private String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_xiaoyuan);
        userId = getIntent().getStringExtra("userId");
        initView();
        initData();

        // 点击从相册里面选择图片
        image.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(intent, 100);
        });

        // 添加校园内容
        add.setOnClickListener(v -> {
            DBHelper3 dbHelper3 = new DBHelper3(this);
            SQLiteDatabase db = dbHelper3.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put("userId", userId);
            values.put("username", username);
            values.put("title", title.getText().toString());
            values.put("content", content.getText().toString());
            values.put("image", imagePath);
            values.put("degree", degree.getText().toString());
            values.put("price", price.getText().toString());
            long insert = db.insert("xiaoyuan", null, values);
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
        ((TextView) toolbar.getChildAt(1)).setText("添加物品");
        toolbar.getChildAt(2).setVisibility(View.GONE);

        title = findViewById(R.id.title);
        price = findViewById(R.id.price);
        content = findViewById(R.id.content);
        degree = findViewById(R.id.degree);
        add = findViewById(R.id.add);
        image = findViewById(R.id.image);
    }

    // 生命周期 如果选择了 图片 就会进行到这里
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

    // 将图片转换为为String类型
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