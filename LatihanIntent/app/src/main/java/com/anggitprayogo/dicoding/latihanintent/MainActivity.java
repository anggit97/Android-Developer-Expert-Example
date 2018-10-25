package com.anggitprayogo.dicoding.latihanintent;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    Button btnMove, btnMoveWithData, btnMoveWithObject, btnDialNumber, btnMoveWithResult;
    TextView tvResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnMove = findViewById(R.id.btn_move);
        btnMoveWithData = findViewById(R.id.btn_move_with_data);
        btnMoveWithObject = findViewById(R.id.btn_move_with_object);
        btnDialNumber = findViewById(R.id.btn_dial_number);
        btnMoveWithResult = findViewById(R.id.btn_move_with_result);
        tvResult = findViewById(R.id.tv_result);

        btnMove.setOnClickListener(this);
        btnMoveWithData.setOnClickListener(this);
        btnMoveWithObject.setOnClickListener(this);
        btnMoveWithResult.setOnClickListener(this);
        btnDialNumber.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_move:
                Intent toMove = new Intent(MainActivity.this, MoveActivity.class);
                startActivity(toMove);
                break;
            case R.id.btn_move_with_data:
                Intent toMoveWithData = new Intent(MainActivity.this, ActivityWithDataActivity.class);
                toMoveWithData.putExtra(ActivityWithDataActivity.NAME, "Anggit Prayogo Wahid");
                toMoveWithData.putExtra(ActivityWithDataActivity.AGE, 21);
                startActivity(toMoveWithData);
                break;
            case R.id.btn_move_with_object:
                Person person = new Person();
                person.setAge(21);
                person.setCity("Tangerang");
                person.setEmail("anggitprayogo@gmail.com");
                person.setName("Anggit Prayogo");

                Intent toMoveWithObject = new Intent(MainActivity.this, MoveDataWithObjectActivity.class);
                toMoveWithObject.putExtra(MoveDataWithObjectActivity.DataObjectValue, person);
                startActivity(toMoveWithObject);
                break;
            case R.id.btn_move_with_result:
                break;
            case R.id.btn_dial_number:
                break;
        }
    }
}
