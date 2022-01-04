package com.example.shoppingjugaad;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class addItemActivity extends AppCompatActivity {
    private EditText name, price, category, detail, quantity;
    private Button increase, decrease, submit;
    private ImageView imageView;
    private Uri imageUri;
    private FirebaseStorage storage;
    private StorageReference storageReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);
        init();

        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                choosePicture();
            }
        });

        increase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int currentValue = Integer.parseInt(quantity.getText().toString());
                int value = currentValue + 1;
                quantity.setText(String.valueOf(value));
            }
        });

        decrease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int currentValue = Integer.parseInt(quantity.getText().toString());
                int value = currentValue - 1;
                if (value > 0)
                    quantity.setText(String.valueOf(value));
                else
                    Toast.makeText(getApplicationContext(), "Can't be less then 0", Toast.LENGTH_SHORT).show();
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String stockName = name.getText().toString().trim();
                String stockQuantity = quantity.getText().toString().trim();
                String stockPrice = price.getText().toString().trim();
                String stockCategory = category.getText().toString().trim();
                String stockDetail = detail.getText().toString().trim();

                if (TextUtils.isEmpty(stockName)) {
                    name.setError("Name can't be empty");
                    return;
                }
                if (TextUtils.isEmpty(stockQuantity)) {
                    quantity.setError("Quantity can't be empty");
                    return;
                }
                if (TextUtils.isEmpty(stockPrice)) {
                    price.setError("Price can't be empty");
                    return;
                }
                if (TextUtils.isEmpty(stockCategory)) {
                    category.setError("Category can't be empty");
                    return;
                }
                if (TextUtils.isEmpty(stockDetail)) {
                    detail.setError("Details can't be empty");
                    return;
                }
            }
        });
    }

    private void init() {
        imageView = findViewById(R.id.product_image_view);
        name = findViewById(R.id.stock_unit_name);
        decrease = findViewById(R.id.subtract_quantity_button);
        quantity = findViewById(R.id.stock_unit_quantity);
        increase = findViewById(R.id.add_quantity_button);
        price = findViewById(R.id.stock_unit_price);
        category = findViewById(R.id.stock_category);
        detail = findViewById(R.id.stock_detail);
        submit = findViewById(R.id.submit);
    }

    private void choosePicture() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK && data != null && data.getData() != null) {
            imageUri = data.getData();
            imageView.setImageURI(imageUri);
        }
    }
}