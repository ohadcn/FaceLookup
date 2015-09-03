package facelookapp.facelookapp;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;

import java.util.List;

import facelookapp.facedetectionlib.FaceDbCreatorThread;
import facelookapp.images.ImageList;


public class MainActivity extends Activity {
    private static final int CAM_REQUEST = 1313;
    ImageAdapter myImageAdapter;
    ImageButton takeAPicBtn;
    ImageView displayImg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        Thread dbCreator = new Thread(new FaceDbCreatorThread(this));
        dbCreator.start();

        ImageButton galleryButton = (ImageButton) findViewById(R.id.gallery);
        galleryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Log.w();
                loadGalleryGrid();
            }
        });
        takeAPicBtn = (ImageButton) findViewById(R.id.camera);
        displayImg = (ImageView) findViewById(R.id.image_view);

        takeAPicBtn.setOnClickListener(new takeAPicClicker());
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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CAM_REQUEST) {
            Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
            displayImg.setImageBitmap(thumbnail);
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

    class takeAPicClicker implements Button.OnClickListener {
        @Override
        public void onClick(View view) {
            Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(cameraIntent, CAM_REQUEST);
        }
    }
}
