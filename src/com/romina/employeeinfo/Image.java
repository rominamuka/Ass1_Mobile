package com.romina.employeeinfo;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ImageView;

public class Image extends Activity {
	
	final static String IMAGE_URL = "http://static.flickr.com/54/106463808_1479e42f68_b.jpg";
		
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.image);
			
        try {
              new DownloadImageTask().execute(new URL(IMAGE_URL));
        	} catch (MalformedURLException e) {
              e.printStackTrace();
        	}
	}
	
    private class DownloadImageTask extends AsyncTask<URL, Void, Bitmap> {
    	@Override
    	protected Bitmap doInBackground(URL... urls) {
    		assert urls.length == 1; 
            return downloadImage(urls[0]);
        }
    	@Override
    	protected void onPostExecute(Bitmap bitmap) {
           
      final ImageView img = (ImageView) findViewById(R.id.image);
       img.setImageBitmap(bitmap);
    	}
    
    
	   private Bitmap downloadImage(final URL url) {
	        Bitmap bitmap = null;
	        InputStream in = null;        
	        try {
	            in = openHttpConnection(url);
	            bitmap = BitmapFactory.decodeStream(in);
	            in.close();
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	        return bitmap;                
	    }
    }
	   private InputStream openHttpConnection(final URL url) throws IOException {
	        InputStream in = null;
	        int response = -1;
	        final URLConnection conn = url.openConnection();
	                 
	        if (!(conn instanceof HttpURLConnection)) {                     
	            throw new IOException("Not an HTTP connection");
	        }
	        
	        try {
	            final HttpURLConnection httpConn = (HttpURLConnection) conn;
	            httpConn.setAllowUserInteraction(false);
	            httpConn.setInstanceFollowRedirects(true);
	            httpConn.setRequestMethod("GET");
	            httpConn.connect(); 

	            response = httpConn.getResponseCode();                 
	            if (response == HttpURLConnection.HTTP_OK) {
	                in = httpConn.getInputStream();                                 
	            }                     
	        } catch (Exception ex) {
	        	ex.printStackTrace();            
	        }
	        return in;     
	    }
}