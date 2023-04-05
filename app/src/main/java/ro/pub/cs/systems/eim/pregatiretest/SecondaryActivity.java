package ro.pub.cs.systems.eim.pregatiretest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

public class SecondaryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        String str = intent.getStringExtra("str");
        str = str.replace("+", ",");
        String[] strArray = str.split(",");
        int s = 0;
        for (int i = 0; i < strArray.length; i++) {
            s += Integer.parseInt(strArray[i]);
        }

        Intent intent1 = new Intent(getApplicationContext(), MainActivity.class);
        intent1.putExtra("suma", s);
        setResult(1, intent1);
        finish();
    }
}