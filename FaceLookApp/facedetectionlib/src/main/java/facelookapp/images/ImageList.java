package facelookapp.images;

import android.content.Context;
import android.database.Cursor;
import android.provider.MediaStore;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ohad on 9/3/15.
 */
public class ImageList {


    public static List<String> imagesOnDevice(Context context) {
        return imagesOnDevice(context, new FileFilter() {
            @Override
            public boolean accept(File pathname) {
                return true;
            }
        });
    }

    public static List<String> imagesOnDevice(Context context, FileFilter filter) {
        //TODO filter
        List<String> results = new ArrayList<>();

        String[] projections = {MediaStore.Images.Media._ID,
                MediaStore.Images.Media.BUCKET_DISPLAY_NAME,
                MediaStore.Images.Media.DISPLAY_NAME,
                MediaStore.Images.Media.DATE_MODIFIED
        };
        Cursor cur = context.getContentResolver().query(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                null,
                null, null, null);


        System.out.println(cur.getCount() + " images found");

        if (cur.moveToFirst()) {
            String data;

            int dataColumn = cur.getColumnIndex(MediaStore.Images.Media.DATA);

            do {
                data = cur.getString(dataColumn);
                results.add(data);
                System.out.println(data);
            } while (cur.moveToNext());
        }
        return results;
    }

}
