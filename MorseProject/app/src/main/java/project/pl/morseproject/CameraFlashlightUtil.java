package project.pl.morseproject;

import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.Camera;

public class CameraFlashlightUtil {

    private Context context;
    private Camera camera;

    public CameraFlashlightUtil(Context context) {
        this.context = context;
    }

    public void turnFlashlightOn() {
        if (camera == null) {
            setupCameraFlashlight();
        }

        camera.startPreview();
    }

    private void setupCameraFlashlight() {
        context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH);

        camera = Camera.open();
        Camera.Parameters parameters = camera.getParameters();
        parameters.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
        camera.setParameters(parameters);
    }

    public void turnFlashlightOff(){
        if (camera != null) {
            camera.stopPreview();
        }
    }

    public void releaseCamera() {
        if (camera != null) {
            camera.release();
            camera = null;
        }
    }
}
