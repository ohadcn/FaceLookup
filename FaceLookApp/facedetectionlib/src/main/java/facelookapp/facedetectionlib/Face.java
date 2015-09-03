package facelookapp.facedetectionlib;

import android.graphics.Bitmap;

/**
 * Created by ohad on 9/3/15.
 */
public class Face {
    private Face() {
    }

    /**
     * gets you a @Face instance, from an bitmap image
     * see also @facesFromImage
     * @param bitmap a @bitmap image, of the face
     * @return @Face instance
     * @see @facesFromImage
     */
    public static Face faceFromBitmap(Bitmap bitmap) {
        //TODO
        return null;
    }

    /**
     * returns an array of faces in a given image.
     *
     * @param image the image to look in
     * @return an array of faces
     */
    public static Face[] facesFromImage(Bitmap image) {
        //TODO
        return null;
    }

    /**
     * returns a number between 0 and 1 that presents the similarity of two faces
     * 0 means the faces are totally different, 1 means they are the same
     *
     * @param a the first Face
     * @param b the second Face
     * @return a number between 0 and 1 as big as the similarity of two faces
     */
    public static double compareFaces(Face a, Face b) {
        //TODO
        return 1;
    }

    /**
     * returns a face instance, described by a given string
     *
     * @param s a string, describing a face
     * @return a face instance
     */
    public Face fromString(String s) {
        //TODO
        return null;
    }

    @Override
    public String toString() {
        return super.toString();
    }

}

