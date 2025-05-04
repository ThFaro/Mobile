package com.exemplo.medicamento;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;

public class AlarmeReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        String nome = intent.getStringExtra("nome");

        NotificationManager nm = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        String canal = "canal_med";

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel nc = new NotificationChannel(canal, "Medicamentos", NotificationManager.IMPORTANCE_HIGH);
            nm.createNotificationChannel(nc);
        }

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, canal)
                .setSmallIcon(android.R.drawable.ic_dialog_info)
                .setContentTitle("Hora do Medicamento")
                .setContentText("Tomar: " + nome)
                .setPriority(NotificationCompat.PRIORITY_HIGH);

        nm.notify((int) System.currentTimeMillis(), builder.build());

        Toast.makeText(context, "Notificação enviada: " + nome, Toast.LENGTH_LONG).show();
    }
}
