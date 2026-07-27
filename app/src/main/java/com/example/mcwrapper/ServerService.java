package com.example.mcwrapper;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;

import androidx.core.app.NotificationCompat;

public class ServerService extends Service {

    private static final String CHANNEL_ID = "MCServerChannel";
    private static final int NOTIFICATION_ID = 1;
    private static final String TAG = "MCServerService";
    private boolean isServerRunning = false;

    static {
        System.loadLibrary("mcwrapper");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent != null) {
            String action = intent.getAction();
            if ("START_SERVER".equals(action)) {
                startMinecraftServer();
            } else if ("STOP_SERVER".equals(action)) {
                stopMinecraftServer();
            }
        }
        return START_STICKY;
    }

    private void startMinecraftServer() {
        if (!isServerRunning) {
            createNotificationChannel();
            Notification notification = buildNotification("Minecraft Server Running");
            startForeground(NOTIFICATION_ID, notification);

            // Start server in a separate thread
            new Thread(() -> {
                try {
                    Log.d(TAG, "Starting native Minecraft server...");
                    nativeStartServer();
                    isServerRunning = true;
                } catch (Exception e) {
                    Log.e(TAG, "Error starting server", e);
                }
            }).start();
        }
    }

    private void stopMinecraftServer() {
        if (isServerRunning) {
            try {
                Log.d(TAG, "Stopping native Minecraft server...");
                nativeStopServer();
                isServerRunning = false;
            } catch (Exception e) {
                Log.e(TAG, "Error stopping server", e);
            }
            stopForeground(STOP_FOREGROUND_REMOVE);
            stopSelf();
        }
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel serviceChannel = new NotificationChannel(
                    CHANNEL_ID,
                    "Minecraft Server Service",
                    NotificationManager.IMPORTANCE_DEFAULT
            );
            NotificationManager manager = getSystemService(NotificationManager.class);
            if (manager != null) {
                manager.createNotificationChannel(serviceChannel);
            }
        }
    }

    private Notification buildNotification(String text) {
        return new NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle("MCWrapper")
                .setContentText(text)
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .build();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    // Native methods
    public native void nativeStartServer();
    public native void nativeStopServer();
}
