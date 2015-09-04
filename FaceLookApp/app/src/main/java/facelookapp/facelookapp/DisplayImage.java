package facelookapp.facelookapp;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.PointF;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsoluteLayout;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;

import facelookapp.facedetectionlib.BiometricFace;


public class DisplayImage extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_image);
        ImageView image = (ImageView)findViewById(R.id.image);
        Bundle extra = getIntent().getExtras();
        if (extra !=null) {
            String path = extra.getString("imagePath");
//            int width = image.getMaxWidth();
//            int height = image.getMaxHeight();
            Bitmap bm = ImageLoader.decodeSampledBitmapFromUri(path, 1000, 1000);
            image.setImageBitmap(bm);
            BiometricFace[] faces = BiometricFace.facesFromImage(bm, DisplayImage.this);
            FrameLayout frame = (FrameLayout) findViewById(R.id.my_frame);
            if (faces.length >0) {
                for (final BiometricFace bFace:faces){

                    PointF p = bFace.getPosition();
                    float x = p.x;
                    float y = p.y;
                    float width = bFace.getWidth();
                    float height = bFace.getHeight();
                    Log.w("####### x ", Float.toString(x));
                    Log.w("####### y ", Float.toString(y));
                    Log.w("####### width ", Float.toString(width));
                    Log.w("####### height ", Float.toString(height));

                    Button bt = new Button(DisplayImage.this);
                    bt.setBackgroundColor(Color.WHITE);
                    bt.getBackground().setAlpha(128);
                    bt.setPadding(15, 15, 15, 15);
                    bt.setX(x);
                    bt.setY(y);
                    bt.setWidth(Math.round(width));
                    bt.setHeight(Math.round(height));
                    bt.bringToFront();
                    frame.addView(bt);
                    bt.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent main_activity = new Intent(DisplayImage.this, MainActivity.class);
                            //put the arguments using putExtra
                            main_activity.putExtra("face", bFace.toString());
                            setResult(Activity.RESULT_OK, main_activity);

                        }
                    });
                }
            } else {
                Intent main_activity = new Intent(DisplayImage.this, MainActivity.class);
                //put the arguments using putExtra
                setResult(Activity.RESULT_OK, main_activity);
            }

//            finish();

//            BiometricFace choosenFace

//            image.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    Intent main_activity = new Intent(DisplayImage.this, MainActivity.class);
//                    //put the arguments using putExtra
//                //            main_activity.putExtra("face",choosenFace);
//                    setResult( Activity.RESULT_OK,main_activity);
//                    finish();
//                }
//            });

        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_display_image, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
