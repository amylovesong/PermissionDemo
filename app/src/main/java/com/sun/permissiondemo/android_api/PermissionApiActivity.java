package com.sun.permissiondemo.android_api;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.widget.Toast;

import com.sun.permissiondemo.BasePermissionActivity;

public class PermissionApiActivity extends BasePermissionActivity {
    private static final String TAG = "PermissionApiActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private String conventResultCode2Desc(int checkResult) {
        switch (checkResult) {
            case PackageManager.PERMISSION_GRANTED:
                return "PERMISSION_GRANTED";
            case PackageManager.PERMISSION_DENIED:
                return "PERMISSION_DENIED";
        }
        return "Unknown";
    }

    @Override
    protected void checkAndRequestReadStoragePermission() {
        final int readCheckResult = ContextCompat.checkSelfPermission(this, PERMISSION_READ_EXTERNAL_STORAGE);
        Log.d(TAG, "current read permission state: " + conventResultCode2Desc(readCheckResult));
        if (readCheckResult == PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(this.getApplicationContext(), "读外部存储的权限已授予～", Toast.LENGTH_SHORT).show();
            mReadPermissionStateTextView.setText("读外部存储的权限已授予～");
            final int writeCheckResult = ContextCompat.checkSelfPermission(this, PERMISSION_WRITE_EXTERNAL_STORAGE);
            Log.d(TAG, "current write permission state: " + conventResultCode2Desc(writeCheckResult));
            if (writeCheckResult == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this.getApplicationContext(), "写外部存储的权限也已被授予～", Toast.LENGTH_SHORT).show();
                mWritePermissionStateTextView.setText("写外部存储的权限也已被授予～");
            } else {// 请求同组的写权限——"您的应用仍需要明确请求其需要的每项权限，即使用户已向应用授予该权限组中的其他权限。"
                ActivityCompat.requestPermissions(this, new String[]{PERMISSION_WRITE_EXTERNAL_STORAGE}, REQUEST_CODE_PERMISSION_WRITE_STORAGE);
            }
            return;
        }
        Log.i(TAG, "checkAndRequestReadStoragePermission shouldShowRequestPermissionRationale: "
                + ActivityCompat.shouldShowRequestPermissionRationale(this, PERMISSION_READ_EXTERNAL_STORAGE));
        ActivityCompat.requestPermissions(this, new String[]{PERMISSION_READ_EXTERNAL_STORAGE}, REQUEST_CODE_PERMISSION_READ_STORAGE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        Log.d(TAG, "onRequestPermissionsResult requestCode: " + requestCode);
        switch (requestCode) {
            case REQUEST_CODE_PERMISSION_READ_STORAGE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    mReadPermissionStateTextView.setText("读外部存储的权限被授予～");
                } else {
                    mReadPermissionStateTextView.setText("用户未授予读外部存储的权限。。");
                }
                return;
            case REQUEST_CODE_PERMISSION_WRITE_STORAGE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    mWritePermissionStateTextView.setText("写外部存储的权限被授予～[onRequestPermissionsResult]");
//                    Toast.makeText(this.getApplicationContext(), "写外部存储的权限被授予～[onRequestPermissionsResult]", Toast.LENGTH_SHORT).show();
                } else {
                    mWritePermissionStateTextView.setText("糟糕！用户未授予写外部存储的权限！！");
//                    Toast.makeText(this.getApplicationContext(), "糟糕！用户未授予写外部存储的权限！！", Toast.LENGTH_SHORT).show();
                }
                return;
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}
