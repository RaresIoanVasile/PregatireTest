package ro.pub.cs.systems.eim.pregatiretest;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;




public class MainActivity extends AppCompatActivity {


    private class MessageBroadcastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            Toast.makeText(MainActivity.this, intent.getStringExtra("mesaj"), Toast.LENGTH_SHORT).show();
        }
    }
    int currentSum = 0;
    int once = 0;

    private MessageBroadcastReceiver messageBroadcastReceiver = new MessageBroadcastReceiver();
    private IntentFilter intentFilter = new IntentFilter();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState != null) {
            currentSum = savedInstanceState.getInt("suma");
        }


        Button add, compute;
        EditText nextTerm;
        TextView allTerms;

        add = findViewById(R.id.buttonAdd);
        compute = findViewById(R.id.compute);
        nextTerm = findViewById(R.id.nextTerm);
        allTerms = findViewById(R.id.allTerms);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!String.valueOf(nextTerm.getText()).equals("") && String.valueOf(nextTerm.getText()).matches("-?\\d+(\\.\\d+)?")) {
                    String currentTerms = String.valueOf(allTerms.getText());
                    if (currentTerms.equals("")) {
                        currentTerms += String.valueOf(nextTerm.getText());
                    } else {
                        currentTerms += "+" + String.valueOf(nextTerm.getText());
                    }
                    nextTerm.setText("");
                    allTerms.setText(currentTerms);
                    once = 0;
                }
            }
        });

        compute.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (once == 1) {
                    Toast.makeText(MainActivity.this, String.valueOf(currentSum) + "de aici", Toast.LENGTH_SHORT).show();
                    return;
                }
                String str = String.valueOf(allTerms.getText());
                Intent intent = new Intent(getApplicationContext(), SecondaryActivity.class);
                intent.putExtra("str", str);
                startActivityForResult(intent, 123);
                //if (currentSum > 10) {
                    Intent intent1 = new Intent(getApplicationContext(), MyService.class);
                    intent1.putExtra("suma", currentSum);
                    getApplicationContext().startService(intent1);
                //}
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(messageBroadcastReceiver, intentFilter);
    }

    @Override
    protected void onPause() {
        unregisterReceiver(messageBroadcastReceiver);
        super.onPause();
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putInt("suma", currentSum);

    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        currentSum = savedInstanceState.getInt("suma");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 1) {
            currentSum = data.getIntExtra("suma", 0);
            once = 1;
            Toast.makeText(this, String.valueOf(data.getIntExtra("suma", 0)), Toast.LENGTH_LONG).show();
        }

    }

    @Override
    protected void onDestroy() {
        Intent intent = new Intent(this, MyService.class);
        stopService(intent);
        super.onDestroy();
    }
}