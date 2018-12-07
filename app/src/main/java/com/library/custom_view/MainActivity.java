package com.library.custom_view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.library.depending.view.ImageActivity;
import com.library.depending.view.PlusImageActivity;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ImageActivity.show(this,"http://b-ssl.duitang.com/uploads/item/201407/23/20140723083033_jwNEm.png");
    }
}
