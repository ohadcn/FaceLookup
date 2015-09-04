package facelookapp.facelookapp;

import android.app.Activity;
import android.content.Intent;
<<<<<<< HEAD
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

import facelookapp.facedetectionlib.FaceDbCreatorThread;

=======
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageButton;
import java.util.List;

import facelookapp.facedetectionlib.FaceDbCreatorThread;
import facelookapp.facedetectionlib.FacesStore;
import facelookapp.images.ImageList;
>>>>>>> 09c6a381a036650786411690ae8691808ed27615

public class MainActivity extends Activity {

<<<<<<< HEAD
    ImageButton takeAPicBtn;
    ImageView displayImg;

    private static final int CAM_REQUEST = 1313;
=======
public class MainActivity extends Activity {
    private static final int CAM_REQUEST = 1313;
    ImageAdapter myImageAdapter;
    ImageButton takeAPicBtn;
>>>>>>> 09c6a381a036650786411690ae8691808ed27615

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
<<<<<<< HEAD
        //Thread dbCreator = new Thread(new FaceDbCreatorThread(this));
        //dbCreator.start();

        takeAPicBtn = (ImageButton) findViewById(R.id.camera);
        displayImg = (ImageView) findViewById(R.id.image_view);

        takeAPicBtn.setOnClickListener(new takeAPicClicker());
=======

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
//        displayImg = (ImageView) findViewById(R.id.image_view);

        takeAPicBtn.setOnClickListener(new takeAPicClicker());
    }

    public void loadGalleryGrid() {
        // initialize the grid gallery adapter
        GridView gridview = (GridView) findViewById(R.id.galleryView);
        myImageAdapter = new ImageAdapter(this);
        gridview.setAdapter(myImageAdapter);
        final List<String> paths = ImageList.imagesOnDevice(this);
        for (String path : paths) {
            myImageAdapter.add(path);
        }

        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String imagePath = paths.get(position);
                Intent displayImageIntent = new Intent(MainActivity.this, DisplayImage.class);
                displayImageIntent.putExtra("imagePath", imagePath);
                startActivityForResult(displayImageIntent, RESULT_OK);
            }
        });

>>>>>>> 09c6a381a036650786411690ae8691808ed27615
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
<<<<<<< HEAD

        if (requestCode == CAM_REQUEST) {
            Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
            displayImg.setImageBitmap(thumbnail);
=======
        if (data != null) {
            if (requestCode == CAM_REQUEST) {
                final Uri contentUri = data.getData();
                final String[] proj = {MediaStore.Images.Media.DATA};
                final Cursor cursor = managedQuery(contentUri, proj, null, null, null);
                final int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                cursor.moveToLast();
                final String imagePath = cursor.getString(column_index);

                Intent displayImageIntent = new Intent(MainActivity.this, DisplayImage.class);
                displayImageIntent.putExtra("imagePath", imagePath);
                startActivityForResult(displayImageIntent, RESULT_OK);
            }

            if (requestCode == RESULT_OK) {
                GridView gridview = (GridView) findViewById(R.id.galleryView);
                myImageAdapter = new ImageAdapter(this);
                gridview.setAdapter(myImageAdapter);
                final List<String> results = ImageList.imagesOnDevice(this,FacesStore.getFilter(data.getDataString()));
                for (String path : results) {
                    myImageAdapter.add(path);
                }

            }
>>>>>>> 09c6a381a036650786411690ae8691808ed27615
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
