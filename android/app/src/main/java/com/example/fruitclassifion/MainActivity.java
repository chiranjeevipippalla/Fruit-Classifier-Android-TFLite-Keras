package com.example.fruitclassifion;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import java.io.IOException;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    Button classify_button;
    Button clear_button;
    private ImageView imageView;
    private TextView resultTextView;
    private Classifier classifier;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        classify_button = findViewById(R.id.button_classify);
        clear_button = findViewById(R.id.button_clear);
        imageView = findViewById(R.id.imageView);
        resultTextView = findViewById(R.id.result);

        classify_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, 100);
            }
        });
        clear_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageView.setImageBitmap(null);
                resultTextView.setText("0");
            }
        });
        try {
            classifier = new Classifier(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100) {
            Bitmap captureImage = (Bitmap) data.getExtras().get("data");
            Bitmap scaledBitmap = captureImage.createScaledBitmap(captureImage, 50, 50, false);
            int digit = classifier.classify(scaledBitmap);
            imageView.setImageBitmap(captureImage);
            if(digit==0){
                resultTextView.setText("Apple!");
            }
            if(digit==1){
                resultTextView.setText("Banana!");
            }
        }
    }
}