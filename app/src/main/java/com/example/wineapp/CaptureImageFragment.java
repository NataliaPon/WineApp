package com.example.wineapp;

import androidx.camera.core.AspectRatio;
import androidx.camera.core.Camera;
import androidx.camera.core.CameraSelector;
import androidx.camera.core.ImageAnalysis;
import androidx.camera.core.ImageCapture;
import androidx.camera.core.ImageCaptureException;
import androidx.camera.core.Preview;
import androidx.camera.lifecycle.ProcessCameraProvider;
import androidx.camera.view.PreviewView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.ViewModelProvider;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.wineapp.models.OperationType;
import com.example.wineapp.models.Wine;
import com.google.common.util.concurrent.ListenableFuture;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class CaptureImageFragment extends Fragment {

    private static final int REQUEST_CODE_CAMERA_PERMISSION = 200;
    private static final String[] PERMISSIONS = {Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE};

    private Context context;
    private Executor executor;
    ProcessCameraProvider cameraProvider;
    PreviewView mPreviewView;
    ImageView captureImage;
    private static Wine wine;
    private static OperationType operationType;

    public static CaptureImageFragment newInstance(Wine wine, OperationType operationType) {
        Bundle args = new Bundle();
        args.putSerializable("wine",wine);
        args.putSerializable("operationType",operationType);
        CaptureImageFragment viewWineFragment = new CaptureImageFragment();
        viewWineFragment.setArguments(args);
        return viewWineFragment;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(cameraProvider != null)
            cameraProvider.unbindAll();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_capture_image, container, false);
        mPreviewView = rootView.findViewById(R.id.mPreviewView);
        captureImage = rootView.findViewById(R.id.captureImg);

        context = ((WineListActivity) requireActivity()).context;
        executor = Executors.newSingleThreadExecutor();

        if (getArguments() != null) {
            wine= (Wine) getArguments().getSerializable("wine");
            operationType = (OperationType) getArguments().getSerializable("operationType");
        }

        if(checkPermissions()) {
            startCamera();
        }
        return rootView;
    }


    private void startCamera() {

        final ListenableFuture<ProcessCameraProvider> cameraProviderFuture = ProcessCameraProvider.getInstance(context);

        cameraProviderFuture.addListener(new Runnable() {
            @Override
            public void run() {
                try {

                    cameraProvider = cameraProviderFuture.get();
                    bindPreview(cameraProvider);

                } catch (ExecutionException | InterruptedException e) {
                    // No errors need to be handled for this Future.
                    // This should never be reached.
                }
            }
        }, ContextCompat.getMainExecutor(context));
    }

    void bindPreview(@NonNull ProcessCameraProvider cameraProvider) {

        cameraProvider.unbindAll();

        CameraSelector cameraSelector = new CameraSelector.Builder()
                .requireLensFacing(CameraSelector.LENS_FACING_BACK)
                .build();

        Preview preview = new Preview.Builder().setTargetAspectRatio(AspectRatio.RATIO_4_3).build();
        preview.setSurfaceProvider(mPreviewView.getSurfaceProvider());

        final ImageCapture imageCapture = new ImageCapture.Builder()
                .setTargetRotation(((WineListActivity) requireActivity()).getWindowManager().getDefaultDisplay().getRotation())
                .build();

        cameraProvider.bindToLifecycle((LifecycleOwner)context, cameraSelector, preview, imageCapture);



        captureImage.setOnClickListener(v -> {

            SimpleDateFormat mDateFormat = new SimpleDateFormat("yyyyMMddHHmmss", Locale.US);
            File file = new File(getBatchDirectoryName(), mDateFormat.format(new Date())+ ".jpg");

            ImageCapture.OutputFileOptions outputFileOptions = new ImageCapture.OutputFileOptions.Builder(file).build();
            imageCapture.takePicture(outputFileOptions, executor, new ImageCapture.OnImageSavedCallback () {
                @Override
                public void onImageSaved(@NonNull ImageCapture.OutputFileResults outputFileResults) {
//                    getFragmentManager().beginTransaction().remove(CaptureImageFragment.this).commit();
                    //todo show hide fragment
                    wine.setPhoto(outputFileResults.getSavedUri().toString());
                    getActivity().getSupportFragmentManager().beginTransaction()
                            .replace(R.id.wineListContainer, AddWineFragment.newInstance(operationType,wine), "AddWineFragment")
                            .setReorderingAllowed(true)
//                            .addToBackStack(null)
                            .commit();

                    Log.e("Image Saved", outputFileResults.getSavedUri().toString());
                }
                @Override
                public void onError(@NonNull ImageCaptureException error) {
//                    Toast.makeText(context, "ImageCaptureException: "+error.getMessage(), Toast.LENGTH_SHORT).show();
                    Log.e("ImageCaptureException", error.getMessage());
                    error.printStackTrace();
                }
            });
        });

    }

    public String getBatchDirectoryName() {

        String app_folder_path = "";
        app_folder_path = Environment.getExternalStorageDirectory().toString() + "/wineappData/images";
        File dir = new File(app_folder_path);
        if (!dir.exists() && !dir.mkdirs()) {
//            return null;
            dir.mkdir();
        }

        return app_folder_path;
    }

    private boolean checkPermissions(){
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED
                || ActivityCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                    requireActivity(),
                    PERMISSIONS,
                    REQUEST_CODE_CAMERA_PERMISSION);
            return false;
        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == REQUEST_CODE_CAMERA_PERMISSION) {
            if (grantResults[0] == PackageManager.PERMISSION_DENIED) {
                Toast.makeText(
                                context,
                                "No permissions",
                                Toast.LENGTH_LONG)
                        .show();

            } else {
                startCamera();
            }
        }
    }

}