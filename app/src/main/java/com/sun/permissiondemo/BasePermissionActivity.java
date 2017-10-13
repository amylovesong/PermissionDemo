package com.sun.permissiondemo;

import android.Manifest;
import android.content.pm.PackageManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public abstract class BasePermissionActivity extends AppCompatActivity {
    private static final String TAG = "BasePermissionActivity";

    public static final String PERMISSION_READ_EXTERNAL_STORAGE = Manifest.permission.READ_EXTERNAL_STORAGE;
    public static final String PERMISSION_WRITE_EXTERNAL_STORAGE = Manifest.permission.WRITE_EXTERNAL_STORAGE;
    public static final int REQUEST_CODE_PERMISSION_READ_STORAGE = 1;
    public static final int REQUEST_CODE_PERMISSION_WRITE_STORAGE = 2;

    protected TextView mReadPermissionStateTextView;
    protected TextView mWritePermissionStateTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_permission);

        mReadPermissionStateTextView = (TextView) findViewById(R.id.textView_read_permission);
        mWritePermissionStateTextView = (TextView) findViewById(R.id.textView_write_permission);

        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAndRequestReadStoragePermission();
            }
        });
    }

    protected abstract void checkAndRequestReadStoragePermission();
}
