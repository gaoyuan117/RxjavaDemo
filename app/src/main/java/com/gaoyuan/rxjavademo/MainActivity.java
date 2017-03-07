package com.gaoyuan.rxjavademo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    public void click(View view) {
        switch (view.getId()){
            case R.id.bt_main_basic:
                startActivity(new Intent(this,BasicActivity.class));
                break;
            case R.id.bt_main_example:
                startActivity(new Intent(this,ExampleActivity.class));
                break;
            case R.id.bt_main_czf:
                startActivity(new Intent(this,OperatorsActivity.class));
                break;
            case R.id.bt_main_flowable:
                startActivity(new Intent(this, FlowableActivity.class));
                break;
            case R.id.bt_main_example2:
                startActivity(new Intent(this, FlowableExampleActivity.class));
                break;
        }
    }
}
