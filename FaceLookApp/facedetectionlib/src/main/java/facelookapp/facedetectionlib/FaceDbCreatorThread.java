package facelookapp.facedetectionlib;

import android.content.Context;
import android.os.Looper;
import android.widget.Toast;

import com.google.android.gms.vision.face.FaceDetector;

import java.util.List;

import facelookapp.images.ImageList;

public class FaceDbCreatorThread implements Runnable {

    Context context;

    public FaceDbCreatorThread(Context context) {
        this.context = context;
    }

    @Override
    public void run() {
        Looper.prepare();
        BiometricFace.initDetector(context);

        List<String> ls = ImageList.imagesOnDevice(context);
        for(String s:ls) {

        }
    }

    
}
