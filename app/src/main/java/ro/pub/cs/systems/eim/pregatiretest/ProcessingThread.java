package ro.pub.cs.systems.eim.pregatiretest;

import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import java.util.Date;

public class ProcessingThread extends Thread {
    private Context context = null;
    private boolean isRunning = true;
    private int suma;

    public ProcessingThread(Context context, int suma) {
        this.context = context;
        this.suma = suma;
    }

    public void run() {
        while(isRunning) {
            sleep();
            sendMessage();
            stopThread();
        }
    }

    private void sendMessage() {
        Intent intent = new Intent();
        intent.putExtra("mesaj", new Date(System.currentTimeMillis()) + " " + String.valueOf(suma));
        context.sendBroadcast(intent);
    }

    private void sleep() {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException interruptedException) {
            interruptedException.printStackTrace();
        }
    }

    public void stopThread() {
        isRunning = false;
    }
}
