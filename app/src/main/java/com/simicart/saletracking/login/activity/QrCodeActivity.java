package com.simicart.saletracking.login.activity;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.ViewGroup;

import com.google.zxing.Result;
import com.simicart.saletracking.R;
import com.simicart.saletracking.common.AppPreferences;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class QrCodeActivity extends AppCompatActivity implements ZXingScannerView.ResultHandler {

    protected ZXingScannerView mScannerView;
    protected String[] CAMERA_PERM = {Manifest.permission.CAMERA};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qr_code);

        initCameraView();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (hasPermission(Manifest.permission.CAMERA) == false) {
                requestPermissions(CAMERA_PERM, 753);
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (isAccessCamera()) {
            mScannerView.setResultHandler(this);
            mScannerView.startCamera();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (isAccessCamera()) {
            mScannerView.stopCamera();
        }
    }

    @Override
    public void handleResult(Result result) {
        Intent intent = new Intent();
        Bundle bundle = new Bundle();
        bundle.putString("result", result.getText());

        intent.putExtras(bundle);
        setResult(1, intent);
        finish();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (isAccessCamera()) {
            initCameraView();
        } else {
            showErrorMessage(shouldShowRequestPermissionRationale(Manifest.permission.CAMERA));
        }
    }

    protected void initCameraView() {
        ViewGroup contentFrame = (ViewGroup) findViewById(R.id.content_frame);
        mScannerView = new ZXingScannerView(this);
        contentFrame.addView(mScannerView);
    }

    protected boolean isAccessCamera() {
        if (hasPermission(Manifest.permission.CAMERA)) {
            return true;
        }
        return false;
    }

    protected boolean hasPermission(String perm) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            return (PackageManager.PERMISSION_GRANTED == checkSelfPermission(perm));
        }
        return true;
    }

    protected void showErrorMessage(final boolean isUnCheckNeverAsk) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setTitle("Permission Denied");
        alertDialogBuilder.setMessage("Without CAMERA permission you are unable to use QR code login feature!");
        if (isUnCheckNeverAsk) {
            alertDialogBuilder.setPositiveButton("Try again",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface arg0, int arg1) {
                            requestPermissions(CAMERA_PERM, 753);
                        }
                    });
        } else {
            alertDialogBuilder.setPositiveButton("Go to Setting",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface arg0, int arg1) {
                            Intent intent = new Intent();
                            intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                            Uri uri = Uri.fromParts("package", getPackageName(), null);
                            intent.setData(uri);
                            startActivity(intent);
                        }
                    });
        }
        alertDialogBuilder.setNegativeButton("I'm sure", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                finish();
            }
        });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

}
