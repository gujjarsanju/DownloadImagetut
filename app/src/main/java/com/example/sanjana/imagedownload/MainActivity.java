package com.example.sanjana.imagedownload;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity {
    public static final String PIC_URL = "http://www.planwallpaper.com/static/images/desktop-year-of-the-tiger-images-wallpaper.jpg";
    public static final String DOWNLOAD_URL = "http://www.gettyimages.ca/gi-resources/images/Homepage/Hero/UK/CMS_Creative_164657191_Kingfisher.jpg";
    ImageView picasoImage;
    Button button;
    CircleImageView imageView;
    ProgressDialog mProgressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button = (Button) findViewById(R.id.download_button);
        picasoImage = (ImageView) findViewById(R.id.picasso_image);
        imageView = (CircleImageView) findViewById(R.id.action_image);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Execute downloadImage Asynctask
               // new DownloadImage().execute(DOWNLOAD_URL);
                Picasso.with(MainActivity.this)
                        .load(PIC_URL)
                        .fit()
                        .into(picasoImage);
                Picasso.with(MainActivity.this)
                        .load(DOWNLOAD_URL)
                        .into(imageView);

            }
        });
    }

    //Downloading Image using Aysntask

    private class DownloadImage extends AsyncTask<String,Void,Bitmap>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            //create a progress dialog
            mProgressDialog = new ProgressDialog(MainActivity.this);
            //set progress dialog title
            mProgressDialog.setTitle("Download Image");
            //set message
            mProgressDialog.setMessage("Loading");
            mProgressDialog.setIndeterminate(false);
            mProgressDialog.show();

        }

        @Override
        protected Bitmap doInBackground(String... url) {
          String imageUrl = url[0];
            try {
                Bitmap myBitmap = null;
                URL download_url = new URL(imageUrl);
                HttpURLConnection connection = (HttpURLConnection) download_url.openConnection();
                connection.setDoInput(true);
                connection.connect();
                InputStream input = connection.getInputStream();
                myBitmap = BitmapFactory.decodeStream(input);
                return myBitmap;
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }


        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);

            imageView.setImageBitmap(bitmap);

            mProgressDialog.dismiss();
        }
    }

}
