package com.example.mcwrapper;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class MainActivity extends AppCompatActivity {

    private Button startButton, stopButton;
    private TextView statusText;
    private static final int PERMISSION_REQUEST_CODE = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        startButton = findViewById(R.id.startButton);
        stopButton = findViewById(R.id.stopButton);
        statusText = findViewById(R.id.statusText);

        // Request permissions
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,
                                Manifest.permission.READ_EXTERNAL_STORAGE},
                        PERMISSION_REQUEST_CODE);
            }
        }

        startButton.setOnClickListener(v -> startServer());
        stopButton.setOnClickListener(v -> stopServer());

        updateStatus();
    }

    private void startServer() {
        Intent serviceIntent = new Intent(this, ServerService.class);
        serviceIntent.setAction("START_SERVER");
        ContextCompat.startForegroundService(this, serviceIntent);
        statusText.setText("Starting Minecraft Server...");
        Toast.makeText(this, "Server starting...", Toast.LENGTH_SHORT).show();
    }

    private void stopServer() {
        Intent serviceIntent = new Intent(this, ServerService.class);
        serviceIntent.setAction("STOP_SERVER");
        stopService(serviceIntent);
        statusText.setText("Server Stopped");
        Toast.makeText(this, "Server stopped", Toast.LENGTH_SHORT).show();
    }

    private void updateStatus() {
        statusText.setText("Ready to start server");
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Permissions granted", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
