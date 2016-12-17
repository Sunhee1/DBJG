package com.example.sunhee.dogcat;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class userinfo extends AppCompatActivity implements View.OnClickListener{

    EditText name, birth, we;
    TextView recom;
    Button register, recomm;
    String NAME, BIRTH;
    int WE;
    SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userinfo);

        name = (EditText) findViewById(R.id.et_name);
        birth = (EditText) findViewById(R.id.et_birth);
        we = (EditText) findViewById(R.id.et_we);
        recom = (TextView) findViewById(R.id.tv_recom);
        register = (Button) findViewById(R.id.bt_info_register);
        recomm = (Button) findViewById(R.id.bt_recom);

        register.setOnClickListener(this);
        recomm.setOnClickListener(this);

        sp = getSharedPreferences("dogcat", MODE_PRIVATE);
        NAME = sp.getString("NAME", "이름");
        BIRTH = sp.getString("BIRTH", "생일");
        WE = sp.getInt("WE", 0);

        name.setText(NAME);
        birth.setText(BIRTH);
        String in_we = Integer.toString(WE);
        we.setText(in_we);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.bt_info_register:
                NAME = name.getText().toString();
                BIRTH = birth.getText().toString();
                WE = Integer.parseInt(we.getText().toString());
                SharedPreferences.Editor sp_editor = sp.edit();
                sp_editor.putString("NAME", NAME);
                sp_editor.putString("BIRTH", BIRTH);
                sp_editor.putInt("WE", WE);
                sp_editor.commit();

                Toast.makeText(getApplicationContext(), "반려동물 정보가 등록되었습니다.", Toast.LENGTH_LONG).show();
                finish();
                break;
            case R.id.bt_recom :
                if(WE == 0){
                    recom.setText("몸무게를 정확하게 입력해 주세요.");
                }else{
                    int recom_we;
                    recom_we = (WE * 55);
                    recom.setText(recom_we + "g의 사료량이 적당합니다.");
                }
                break;
        }
    }
}
