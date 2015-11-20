package me.aniruddhb.csapp;

import com.facebook.CallbackManager;
import com.facebook.FacebookSdk;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.model.SharePhoto;
import com.facebook.share.model.SharePhotoContent;
import com.facebook.share.widget.ShareDialog;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.net.Uri;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    static ArrayList<Bitmap> strat_list = new ArrayList<Bitmap>();

    // facebook stuff
    CallbackManager callbackManager;
    ShareDialog shareDialog;

    // Button
    Button fb, save;

    // create ImageViews
    ImageView mirage, inferno, cbble, train, nuke, season;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        callbackManager = CallbackManager.Factory.create();
        shareDialog = new ShareDialog(this);
        setContentView(R.layout.activity_main);

        fb = (Button) findViewById(R.id.facebook);
        save = (Button) findViewById(R.id.email);

        mirage = (ImageView) findViewById(R.id.map_mirage);
        inferno = (ImageView) findViewById(R.id.map_inferno);
        cbble = (ImageView) findViewById(R.id.map_cbble);
        train = (ImageView) findViewById(R.id.map_train);
        nuke = (ImageView) findViewById(R.id.map_nuke);
        season = (ImageView) findViewById(R.id.map_season);

        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // move to next state (open app)

                String map_name = (String) v.getTag();
                map_name += "_large";

                Intent new_screen = new Intent (getApplicationContext(), UserDraw.class);
                new_screen.putExtra("mapname", map_name);
                startActivity(new_screen);

            }
        };

        mirage.setOnClickListener(listener);
        inferno.setOnClickListener(listener);
        cbble.setOnClickListener(listener);
        train.setOnClickListener(listener);
        nuke.setOnClickListener(listener);
        season.setOnClickListener(listener);

        fb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TBA when I have an Android phone :(
                SharePhoto photo;
                SharePhotoContent content = null;
                Bitmap image;

                for (int i = 0; i < strat_list.size(); i++) {
                    image = strat_list.get(i);
                    photo = new SharePhoto.Builder().setBitmap(image).build();
                    content = new SharePhotoContent.Builder().addPhoto(photo).build();
                }

                // all photos added to content
                shareDialog.show(content);

            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TBA when I have an Android phone :(
                // cannot implement or test until I have Android phone
                // emulator giving "skip frames" error
            }
        });
    }
}
