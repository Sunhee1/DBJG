package com.example.sunhee.dogcat;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Register extends AppCompatActivity implements View.OnClickListener{

    Button ip_register;
    EditText ip_text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        ip_register = (Button) findViewById(R.id.ip_register);
        ip_text = (EditText) findViewById(R.id.IP_TEXT);

        ip_register.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        if(ip_text.getText().toString() != null) {
            String Inputdata = ip_text.getText().toString();
            SharedPreferences sp = getSharedPreferences("dogcat", MODE_PRIVATE);
            SharedPreferences.Editor editor = sp.edit();

            editor.putString("IP", Inputdata);
            editor.commit();

            Toast.makeText(this, "Success Register!", Toast.LENGTH_LONG);

            Intent i = new Intent(Register.this, MainActivity.class);
            i.putExtra("splash", "splash");
            startActivity(i);
            finish();
        }else{
            Toast.makeText(this, "Input IP address!", Toast.LENGTH_LONG);
        }
    }
}
