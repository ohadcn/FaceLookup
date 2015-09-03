package facelookapp.facelookapp;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
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
            Bitmap bm = ImageLoader.decodeSampledBitmapFromUri(path, image.getWidth(), image.getHeight());

            BiometricFace[] faces = BiometricFace.facesFromImage(bm, DisplayImage.this);
            for (BiometricFace bFace:faces){
//                int x = bFace.ge
            }

            image.setImageBitmap(bm);
//            BiometricFace choosenFace


            Intent main_activity = new Intent(this, MainActivity.class);
            //put the arguments using putExtra
//            main_activity.putExtra("face",choosenFace);
            setResult( Activity.RESULT_OK,main_activity);
            finish();
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
