package com.exemplo.myapplication;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class MeuServico extends Service {
    private Handler handler = new Handler();
    private int segundos = 0;

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        segundos = 0;
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Log.i("Service", "Executando...");
                segundos++;
                if (segundos < 10) {
                    handler.postDelayed(this, 1000);
                } else {
                    Log.i("Service", "10 segundos se passaram.");
                    Toast.makeText(getApplicationContext(), "10 segundos se passaram!", Toast.LENGTH_LONG).show();
                    stopSelf();
                }
            }
        }, 1000);
        return START_NOT_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
