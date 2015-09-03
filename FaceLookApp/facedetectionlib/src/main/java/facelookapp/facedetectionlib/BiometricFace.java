package facelookapp.facedetectionlib;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.PointF;
import android.util.SparseArray;
import android.widget.Toast;

import com.google.android.gms.vision.Frame;
import com.google.android.gms.vision.face.Face;
import com.google.android.gms.vision.face.FaceDetector;
import com.google.android.gms.vision.face.Landmark;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ohad on 9/3/15.
 */
public class BiometricFace {

    //***************** static staff
    private static FaceDetector detector;
    Face face;

    private BiometricFace() {
    }

    /**
     * create a new biometric face object, out of an existing face object
     *
     * @param face the face to analyze for data
     */
    public BiometricFace(Face face) {
        //TODO
        this.face = face;
    }

    /**
     * initialize the static detector to be used by @BiometricFace static methods
     * do not use them before calling that method!
     *
     * @param context a context (or activity) to init with
     */
    public static void initDetector(Context context) {
        if (detector != null)
            return;
        detector = new FaceDetector.Builder(context)
                .setTrackingEnabled(false)
                .setLandmarkType(FaceDetector.ALL_LANDMARKS)
                .setClassificationType(FaceDetector.ALL_CLASSIFICATIONS)
                .build();

        if (!detector.isOperational())
            Toast.makeText(context, "Google play services is not installed on your system," +
                    " please install it and restart FaceLookApp!", Toast.LENGTH_LONG).show();


    }

    /**
     * gets you a @Face instance, from an bitmap image
     * see also @facesFromImage
     * make sure you call @initDetector before calling this method
     * @param bitmap a @bitmap image, of the face
     * @return @BiometricFace instance
     * @see @facesFromImage
     */
//    public static BiometricFace faceFromBitmap(Bitmap bitmap) {
//        Frame frame = new Frame.Builder().setBitmap(bitmap).build();
//        SparseArray<Face> faces = detector.detect(frame);
//        if (faces.size() == 0)
//            return null;
//        return new BiometricFace(faces.get(0));
//    }

    /**
     * returns an array of faces in a given image.
     * make sure you call @initDetector before calling this method
     *
     * @param image the image to look in
     * @return an array of faces
     */
    public static BiometricFace[] facesFromImage(Bitmap image, Context context) {
        ArrayList<BiometricFace> result = new ArrayList<>();

        Frame frame = new Frame.Builder().setBitmap(image).build();
        SparseArray<Face> faces = detector.detect(frame);
        for (int i = 0; i < faces.size(); ++i) {
            Face face = faces.valueAt(i);

            result.add(new BiometricFace(face));
        }

        return result.toArray(new BiometricFace[0]);
    }

    /**
     * returns a number between 0 and 1 that presents the similarity of two faces
     * 0 means the faces are totally different, 1 means they are the same
     *
     * @param a the first Face
     * @param b the second Face
     * @return a number between 0 and 1 as big as the similarity of two faces
     */
    public static double compareFaces(BiometricFace a, BiometricFace b) {
        //TODO
        return 1;
    }

    private PointF getPosition() {
        return face.getPosition();
    }

    private List<Landmark> getLandmarks() {
        return face.getLandmarks();
    }

    private float getIsSmilingProbability() {
        return face.getIsSmilingProbability();
    }

    private float getIsRightEyeOpenProbability() {
        return face.getIsRightEyeOpenProbability();
    }

    private float getIsLeftEyeOpenProbability() {
        return face.getIsLeftEyeOpenProbability();
    }

    private int getId() {
        return face.getId();
    }

    private float getHeight() {
        return face.getHeight();
    }

    private float getEulerZ() {
        return face.getEulerZ();
    }

    private float getEulerY() {
        return face.getEulerY();
    }

    private float getWidth() {
        return face.getWidth();
    }

    /**
     * returns a face instance, described by a given string
     *
     * @param s a string, describing a face
     * @return a face instance
     */
    public BiometricFace fromString(String s) {
        //TODO
        return null;
    }

    @Override
    public String toString() {
        return super.toString();
    }

}

