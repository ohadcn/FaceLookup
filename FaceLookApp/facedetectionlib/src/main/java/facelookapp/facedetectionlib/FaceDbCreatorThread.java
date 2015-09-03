//package facelookapp.facedetectionlib;
//
//import android.content.Context;
//import android.os.Looper;
//import android.widget.Toast;
//
//import com.google.android.gms.vision.face.FaceDetector;
//
//import java.util.List;
//
//import facelookapp.images.ImageList;
//
//public class FaceDbCreatorThread implements Runnable {
//
//    Context context;
//
//    public FaceDbCreatorThread(Context context) {
//        this.context = context;
//    }
//
//    @Override
//    public void run() {
//        Looper.prepare();
//        FaceDetector detector = new FaceDetector.Builder(context)
//                .setTrackingEnabled(false)
//                .setLandmarkType(FaceDetector.ALL_LANDMARKS)
//                .setClassificationType(FaceDetector.ALL_CLASSIFICATIONS)
//                .build();
//
//        if (!detector.isOperational())
//            Toast.makeText(context, "Google play services is not installed on your system," +
//                    " please install it and restart FaceLookApp!", Toast.LENGTH_LONG).show();
//
//        List<String> ls = ImageList.imagesOnDevice(context);
//
//        detector.release();
//    }
//}
