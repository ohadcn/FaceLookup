package facelookapp.facelookapp;

import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageButton;
import java.io.File;
import java.util.List;

import android.widget.Toast;

import facelookapp.images.ImageList;


//import facelookapp.facedetectionlib.FaceDbCreatorThread;


public class MainActivity extends Activity {
    ImageAdapter myImageAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
//        Thread dbCreator = new Thread(new FaceDbCreatorThread(this));
//        dbCreator.start();

        ImageButton galleryButton = (ImageButton) findViewById(R.id.gallery);
        galleryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Log.w();
                loadGalleryGrid();
            }
        });
    }

    public void loadGalleryGrid() {
        // initialize the grid gallery adapter
        GridView gridview = (GridView) findViewById(R.id.galleryView);
        myImageAdapter = new ImageAdapter(this);
        gridview.setAdapter(myImageAdapter);
//        String ExternalStorageDirectoryPath = Environment
//                .getExternalStorageDirectory()
//                .getAbsolutePath();
//        String targetPath = ExternalStorageDirectoryPath + "/test/";
//        String targetPath = ExternalStorageDirectoryPath;
//        Toast.makeText(getApplicationContext(), targetPath, Toast.LENGTH_LONG).show();
//        File targetDirector = new File(targetPath);
        List<String> paths = ImageList.imagesOnDevice(this);
//        File[] files = targetDirector.listFiles();
        for (String path : paths){
            myImageAdapter.add(path);
        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
