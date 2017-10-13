package com.sun.permissiondemo.easypermissions;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.sun.permissiondemo.BasePermissionActivity;
import com.sun.permissiondemo.R;

import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

import static com.sun.permissiondemo.BasePermissionActivity.REQUEST_CODE_PERMISSION_READ_STORAGE;
import static com.sun.permissiondemo.BasePermissionActivity.REQUEST_CODE_PERMISSION_WRITE_STORAGE;

public class EasyPermissionsActivity extends BasePermissionActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void checkAndRequestReadStoragePermission() {
        if (EasyPermissions.hasPermissions(this, PERMISSION_READ_EXTERNAL_STORAGE)) {
            Toast.makeText(this.getApplicationContext(), "读外部存储的权限已授予～", Toast.LENGTH_SHORT).show();
            mReadPermissionStateTextView.setText("读外部存储的权限已授予～");
            if (EasyPermissions.hasPermissions(this, PERMISSION_WRITE_EXTERNAL_STORAGE)) {
                Toast.makeText(this.getApplicationContext(), "写外部存储的权限也已被授予～", Toast.LENGTH_SHORT).show();
                mWritePermissionStateTextView.setText("写外部存储的权限也已被授予～");
            } else {
                EasyPermissions.requestPermissions(this, "请求授予写外部存储的权限～",
                        REQUEST_CODE_PERMISSION_WRITE_STORAGE, PERMISSION_WRITE_EXTERNAL_STORAGE);
            }
            return;
        }
        EasyPermissions.requestPermissions(this, "请求授予读外部存储的权限～", REQUEST_CODE_PERMISSION_READ_STORAGE, PERMISSION_READ_EXTERNAL_STORAGE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    @AfterPermissionGranted(REQUEST_CODE_PERMISSION_READ_STORAGE)
    private void methodRequiresReadPermission() {
        if (EasyPermissions.hasPermissions(this, PERMISSION_READ_EXTERNAL_STORAGE)) {
            mReadPermissionStateTextView.setText("读外部存储的权限被授予～");
        } else {
            mReadPermissionStateTextView.setText("用户未授予读外部存储的权限。。");
        }
    }

    @AfterPermissionGranted(REQUEST_CODE_PERMISSION_WRITE_STORAGE)
    private void methodRequiresWritePermission() {
        if (EasyPermissions.hasPermissions(this, PERMISSION_WRITE_EXTERNAL_STORAGE)) {
            mWritePermissionStateTextView.setText("写外部存储的权限被授予～[AfterPermissionGranted]");
        } else {
            mWritePermissionStateTextView.setText("糟糕！用户未授予写外部存储的权限！！");
        }
    }
}
