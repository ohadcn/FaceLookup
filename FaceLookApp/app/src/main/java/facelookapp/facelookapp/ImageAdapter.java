package facelookapp.facelookapp;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import java.util.ArrayList;

/**
 * Created by gali on 03-Sep-15.
 */
public class ImageAdapter extends BaseAdapter {

    private Context myContext;
    ArrayList<String> itemList = new ArrayList<>();

    public ImageAdapter(Context context) {
        myContext = context;
    }

    public void add(String path) {
        itemList.add(path);
    }

    @Override
    public int getCount() {
        return itemList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Log.w("########", "Get View");
        ImageView imageView;
        if (convertView == null) {
            imageView = new ImageView(myContext);
            imageView.setLayoutParams(new GridView.LayoutParams(220, 220));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setPadding(8,8,8,8);
        } else {
            imageView = (ImageView) convertView;
        }

        Bitmap bm = ImageLoader.decodeSampledBitmapFromUri(itemList.get(position), 220, 220);
        imageView.setImageBitmap(bm);
        return imageView;
    }



}
