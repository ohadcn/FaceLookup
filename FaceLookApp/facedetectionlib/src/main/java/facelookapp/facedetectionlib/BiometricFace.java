package facelookapp.facedetectionlib;

import android.content.ContentValues;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by ohad on 9/3/15.
 */
public class BiometricFace {

    private static FaceDetector detector;
    private final double rightEyeAngel, leftEyeAngle, cheekAngle, cheekDist, isSmiling;
    Map<Integer, PointF> landmarks = new HashMap<>();
    private Face face;

    public BiometricFace(double rightEyeAngel, double leftEyeAngle, double cheekAngle,
                         double cheekDist, double isSmiling) {
        this.rightEyeAngel = rightEyeAngel;
        this.leftEyeAngle = leftEyeAngle;
        this.cheekAngle = cheekAngle;
        this.cheekDist = cheekDist;
        this.isSmiling = isSmiling;
    }

    /**
     * create a new biometric face object, out of an existing face object
     *
     * @param face the face to analyze for data
     */
    public BiometricFace(Face face) {
        this.face = face;
        isSmiling = face.getIsSmilingProbability();
        for(Landmark mark:face.getLandmarks())
            landmarks.put(mark.getType(), mark.getPosition());

        float angle = getEulerY();
        if (Math.abs(angle) > 36) {
            rightEyeAngel = 0;
            leftEyeAngle = 0;
            cheekAngle = 0;
            cheekDist = 0;
            return;
        }

        PointF rightEye = landmarks.get(Landmark.RIGHT_EYE);
        PointF leftEye = landmarks.get(Landmark.LEFT_EYE);
        PointF nose = landmarks.get(Landmark.NOSE_BASE);
        double zAng = getEulerZ(),
                eyesDist = sqr(rightEye.x - nose.x) + sqr(rightEye.y - nose.y) +
                        sqr(leftEye.y - nose.y) + sqr(leftEye.x - nose.x);
        rightEyeAngel = Math.atan2(rightEye.y - nose.y, rightEye.x - nose.x) + zAng;
        leftEyeAngle = Math.atan2(leftEye.y - nose.y, leftEye.x - nose.x) + zAng;
        if (Math.abs(angle) < 12) {
            PointF rightCheek = landmarks.get(Landmark.RIGHT_CHEEK);
            PointF leftCheek = landmarks.get(Landmark.LEFT_CHEEK);
            cheekDist = (sqr(rightCheek.x - nose.x) + sqr(rightCheek.y - nose.y) +
                    sqr(leftCheek.y - nose.y) + sqr(leftCheek.x - nose.x)) / (2 * eyesDist);

            cheekAngle = (Math.atan2(rightCheek.y - nose.y, rightCheek.x - nose.x) -
                    Math.atan2(leftCheek.y - nose.y, leftCheek.x - nose.x)) / 2;
        } else if (angle > 0) {
            PointF rightCheek = landmarks.get(Landmark.RIGHT_CHEEK);
            cheekDist = sqr(rightCheek.x - nose.x) + sqr(rightCheek.y - nose.y) / eyesDist;
            cheekAngle = Math.atan2(rightCheek.y - nose.y, rightCheek.x - nose.x);
        } else {
            PointF leftCheek = landmarks.get(Landmark.LEFT_CHEEK);
            cheekDist = sqr(leftCheek.y - nose.y) + sqr(leftCheek.x - nose.x) / eyesDist;
            cheekAngle = Math.atan2(leftCheek.y - nose.y, leftCheek.x - nose.x);
        }
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
//                .setMode(FaceDetector.ACCURATE_MODE)
                .setTrackingEnabled(false)
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
    public static BiometricFace[] facesFromImage(Bitmap image) {
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
     * returns a number between 0 and 1 that presents the difference of two faces
     * 0 means the faces are the same, 1 means they are totally differ
     *
     * @param a the first Face
     * @param b the second Face
     * @return a number between 0 and 1 as big as the similarity of two faces
     */
    public static double compareFaces(BiometricFace a, BiometricFace b) {
        return Math.min(Math.abs(a.cheekDist - b.cheekDist) * 3 + (Math.abs(a.cheekAngle - b.cheekAngle)
                + Math.abs(a.leftEyeAngle - b.leftEyeAngle) + Math.abs(a.rightEyeAngel - b.rightEyeAngel)) / (3 * Math.PI), 1);
    }

    /**
     * returns a face instance, described by a given string
     *
     * @param s a string, describing a face
     * @return a face instance
     */
    public static BiometricFace fromString(String s) {
        String[] data = s.split(" ");

        if (data.length != 5)
            return null;

        return new BiometricFace(Double.valueOf(data[0]), Double.valueOf(data[1]),
                Double.valueOf(data[2]), Double.valueOf(data[3]), Double.valueOf(data[4]));
    }

    public PointF getPosition() {
        return face.getPosition();
    }

    public List<Landmark> getLandmarks() {
        return face.getLandmarks();
    }

    public float getIsSmilingProbability() {
        return face.getIsSmilingProbability();
    }

    public float getIsRightEyeOpenProbability() {
        return face.getIsRightEyeOpenProbability();
    }

    public float getIsLeftEyeOpenProbability() {
        return face.getIsLeftEyeOpenProbability();
    }

    public int getId() {
        return face.getId();
    }

    public float getHeight() {
        return face.getHeight();
    }

    public float getEulerZ() {
        return face.getEulerZ();
    }

    public float getEulerY() {
        return face.getEulerY();
    }

    public float getWidth() {
        return face.getWidth();
    }

    @Override
    public String toString() {
        return rightEyeAngel + " " + leftEyeAngle + " " + cheekAngle + " " +
                cheekDist + " " + getIsSmilingProbability();
    }

    private double sqr(double n) {
        return n * n;
    }

    public ContentValues getValues() {
        System.out.println(this);
        ContentValues values = new ContentValues();
        values.put(FacesStore.CHEEK_ANGLE, cheekAngle);
        values.put(FacesStore.CHEEK_DIST, cheekDist);
        values.put(FacesStore.IS_SMILING, getIsSmilingProbability() > 0.5);
        values.put(FacesStore.LEFT_EYE_ANGLE, leftEyeAngle);
        values.put(FacesStore.RIGHT_EYE_ANGLE, rightEyeAngel);
        return values;
    }

    public static enum SmilingStatus {
        SMILING, NOT_SMILING, NO_CARE
    }
}

