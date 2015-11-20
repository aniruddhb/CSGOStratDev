package me.aniruddhb.csapp;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.media.Image;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.Display;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

public class UserDraw extends AppCompatActivity implements View.OnTouchListener{

    // shtuff
    Button clear, save;
    ImageView backdrop;
    Bitmap bmp, mut, temp;
    Canvas tempCanvas;
    Paint paint;
    float downx = 0, downy = 0, upx = 0, upy = 0, currx = 0, curry = 0;

    private String checker(String map_name) {
        if (map_name.equals("cbble_large")) {
            return "Cobble";
        } else if (map_name.equals("inferno_large")) {
            return "Inferno";
        } else if (map_name.equals("mirage_large")) {
            return "Mirage";
        } else if (map_name.equals("nuke_large")) {
            return "Nuke";
        } else if (map_name.equals("season_large")) {
            return "Season";
        } else {
            return "Train";
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_draw);

        clear = (Button) findViewById(R.id.clear);
        save = (Button) findViewById(R.id.save_map);

        // intent stuff
        Intent intent = getIntent();
        String map_name = intent.getExtras().getString("mapname");

        backdrop = (ImageView) findViewById(R.id.background_map);
        Button ender = (Button) findViewById(R.id.save_map);

        // gets true name and sets button to true name (IMPORTANT!)
        String true_name = "Save " + checker(map_name) + " to strat-book!";
        ender.setText(true_name);

        int resID = getResources().getIdentifier(map_name, "drawable", getPackageName());

        // good bmp
        bmp = BitmapFactory.decodeResource(getResources(), resID);

        // editable copy
        mut = Bitmap.createBitmap(bmp.getWidth(), bmp.getHeight(), Bitmap.Config.RGB_565);

        // canvas
        tempCanvas = new Canvas(mut);
        tempCanvas.drawBitmap(bmp, 0, 0, null);

        // set imageview baby!
        backdrop.setImageBitmap(bmp);

        // paint is good
        paint = new Paint();
        paint.setColor(Color.YELLOW);
        paint.setStrokeWidth((float) 7.0);

        backdrop.setOnTouchListener(this);

        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // set some onclick stuff
                mut = Bitmap.createBitmap(bmp.getWidth(), bmp.getHeight(), Bitmap.Config.RGB_565);
                tempCanvas = new Canvas(mut);
                tempCanvas.drawBitmap(bmp, 0, 0, null);
                backdrop.setImageBitmap(bmp);
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.strat_list.add(mut);
                Toast.makeText(getApplicationContext(), "Saved!", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }

    public boolean onTouch(View v, MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            downx = event.getX(); downy = event.getY();
        } else if (event.getAction() == MotionEvent.ACTION_MOVE) {
            currx = event.getX(); curry = event.getY();
            tempCanvas.drawLine(downx, downy, currx, curry, paint);
            backdrop.setImageDrawable(new BitmapDrawable(getResources(), mut));
            backdrop.invalidate();
            downx = currx; downy = curry;
        } else if (event.getAction() == MotionEvent.ACTION_UP) {
            upx = event.getX(); upy = event.getY();
            tempCanvas.drawLine(currx, curry, upx, upy, paint);
            backdrop.setImageDrawable(new BitmapDrawable(getResources(), mut));
            backdrop.invalidate();
            currx = upx; curry = upy;
        }
        return true;
    }

}
