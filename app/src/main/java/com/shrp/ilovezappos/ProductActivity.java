package com.shrp.ilovezappos;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.shrp.ilovezappos.databinding.ActivityProductBinding;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import pojo.Product;

/**
 * Created by shriroop on 10-Feb-17.
 */

public class ProductActivity extends AppCompatActivity implements View.OnClickListener {

    Product product;
    ImageView ivProductPicture;
    FloatingActionButton fabBrowser;
    RelativeLayout rlPicture;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);
        ivProductPicture = (ImageView) findViewById(R.id.ivThumbnail);
        rlPicture = (RelativeLayout) findViewById(R.id.rlImage);
        fabBrowser = (FloatingActionButton) findViewById(R.id.fabBrowser);
        // I tried opening URL in System browser. This has worked previously. Still trying to figure out
        // why is it not working. As far as I know, control is not entering OnClickListener
        fabBrowser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("[CLICK]", "Clikced: " + product.productUrl);
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(product.productUrl));
                startActivity(intent);
            }
        });
        product = getIntent().getParcelableExtra("product");
        new DownloadPicture().execute();
        ActivityProductBinding binding = DataBindingUtil.setContentView(ProductActivity.this, R.layout.activity_product);
        binding.setProduct(product);
        //fabBrowser.performClick();
    }

    @Override
    public void onClick(View v) {
        Log.d("[CLICK]", "Clikced: " + product.productUrl);
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(product.productUrl));
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        ProductActivity.this.finish();
    }

    class DownloadPicture extends AsyncTask<Void, Void, Bitmap> {

        @Override
        protected Bitmap doInBackground(Void... params) {
            try {
                HttpURLConnection conn = (HttpURLConnection) new URL(product.thumbnailImageUrl).openConnection();
                conn.setDoInput(true);
                conn.connect();
                InputStream stream = conn.getInputStream();
                Bitmap bitmap = BitmapFactory.decodeStream(stream);
                conn.disconnect();
                return bitmap;
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
            /*
             * Tried adding the thumbnail to the Activity. Image is not showing up.
             * Tried adding an image to ImageView, and adding a Imageview to a layout. Still not getting it.
             */
            ImageView im = new ImageView(ProductActivity.this);
            im.setLayoutParams(new FrameLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            im.setImageBitmap(bitmap);
            im.refreshDrawableState();
            rlPicture.refreshDrawableState();
            if(bitmap != null) {
                Log.d("[ASYNCTASK]", "postexecute: " + bitmap.getByteCount());
                ivProductPicture.setImageBitmap(bitmap);
                ivProductPicture.refreshDrawableState();
                //fabBrowser.performClick();
            }
            else
                Toast.makeText(ProductActivity.this, "Unable to download picture", Toast.LENGTH_SHORT).show();
        }
    }
}
