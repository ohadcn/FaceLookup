package facelookapp.facedetectionlib;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Looper;

import java.util.List;

import facelookapp.images.ImageList;

public class FaceDbCreatorThread implements Runnable {

    Context context;

    public FaceDbCreatorThread(Context context) {
        this.context = context;
        BiometricFace.initDetector(context);
        FacesStore.initDB(context);
    }

    @Override
    public void run() {
        Looper.prepare();
        SQLiteDatabase db = FacesStore.getWritableDB();

        List<String> ls = ImageList.imagesOnDevice(context);
        for(String s:ls) {
            Bitmap image = BitmapFactory.decodeFile(s);
            BiometricFace[] faces = BiometricFace.facesFromImage(image);
            System.out.println(s + " " + faces.length);
            for (BiometricFace face : faces) {
                ContentValues vals = face.getValues();
                vals.put(FacesStore.FILE_NAME, s);
                FacesStore.insert(null, vals);
            }
        }
    }

    
}
