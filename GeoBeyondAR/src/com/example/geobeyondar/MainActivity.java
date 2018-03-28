package com.example.geobeyondar;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import com.beyondar.android.fragment.BeyondarFragmentSupport;
import com.beyondar.android.view.OnClickBeyondarObjectListener;
import com.beyondar.android.world.BeyondarObject;
import com.beyondar.android.world.GeoObject;
import com.beyondar.android.world.World;
//import com.example.test.R;
import com.beyondar.android.view.BeyondarViewAdapter;
import com.beyondar.android.util.location.BeyondarLocationManager;

import android.app.Application.ActivityLifecycleCallbacks;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.Window;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;



public class MainActivity extends FragmentActivity implements OnClickBeyondarObjectListener, OnClickListener, LocationListener {

	File photo;	
	BeyondarFragmentSupport mBeyondarFragment;
	Button position;
	Button position1;
	Button distance;
	Location BeyondarLocationManager;
	LocationManager loc;
	GeoObject geo1;
	double lat;
	double lon;
	double nouvellelat=1;
	double nouvellelon=1;
	World mWorld;
	World bWorld;
	BeyondarLocationManager bn;
	TextView description;
	boolean gps_enabled;
	boolean network_enabled;
	static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 10; // 10 mètres
	static final long MIN_TIME_BW_UPDATES = 1000 * 60 * 1; //1 minutes
	WebView web;
	Button webButton;
	String resume;
	Button button;
	Date currentTime;
	SimpleDateFormat sdf;
	String nouvelleDate;
	ImageView imagerecup;
	
	private List<BeyondarObject> showViewOn;
	
	

	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);
		
		
		showViewOn = Collections.synchronizedList(new ArrayList<BeyondarObject>());
		
		sdf = new SimpleDateFormat("yyyy_MM_dd HH_mm_ss");
		
		imagerecup=(ImageView)findViewById(R.id.imagerecup);
		mBeyondarFragment=(BeyondarFragmentSupport)getSupportFragmentManager().findFragmentById(R.id.beyondarFragment);
		
		mWorld = new World(this);
		bWorld = new World(this);
		
		loc=(LocationManager)getSystemService(Context.LOCATION_SERVICE);
		gps_enabled = loc.isProviderEnabled(LocationManager.GPS_PROVIDER);
		network_enabled = loc.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
		
		if (gps_enabled)
		{
			loc.requestLocationUpdates(LocationManager.GPS_PROVIDER, MIN_TIME_BW_UPDATES,MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
		}
		else if (network_enabled) 
		{
			loc.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, MIN_TIME_BW_UPDATES,MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
		};
		
		mWorld = CustomWorldHelper2.generateObjects(this,lat,lon);
		bWorld = CustomWorldHelper2.deleteObjects(this,nouvellelat,nouvellelon);
		
		//mBeyondarFragment.setWorld(mWorld);
		
		mBeyondarFragment.setOnClickBeyondarObjectListener(this);
		CustomBeyondarViewAdapter customBeyondarViewAdapter= new CustomBeyondarViewAdapter(this);
		mBeyondarFragment.setBeyondarViewAdapter(customBeyondarViewAdapter);
		
		position=(Button)findViewById(R.id.myLocationButton);
		position.setOnClickListener(this);
		
		position1=(Button)findViewById(R.id.myLocationButton2);
		position1.setOnClickListener(this);
		
		distance=(Button)findViewById(R.id.myDistanceButton);
		distance.setOnClickListener(this);

	}
	
	public void onActivityResult(int requestCode, int resultCode, Intent data) 
	{
        super.onActivityResult(requestCode, resultCode, data);
        
        switch (requestCode) 
        {
        case 100:
            Toast.makeText(getApplicationContext(),"Je suis de retour -->"+requestCode+"-Avec le code--"+resultCode, Toast.LENGTH_LONG).show();
            
            switch (resultCode) 
            {
            case RESULT_OK:

                    Log.e("TOTO", "data n'est pas NULL");
                    Toast.makeText(getApplicationContext(),"data n'est pas NULL -->"+photo.getAbsolutePath(), Toast.LENGTH_LONG).show();

                    Log.e("TOTO", "Mon Fichier est OK");
                   // imagerecup.setVisibility(View.VISIBLE);
                        
                        try {
                        	ResizePic(photo);
                            Bitmap MaPhoto=android.provider.MediaStore.Images.Media.getBitmap(getContentResolver(), Uri.fromFile(photo));
                            Log.e("BitMap getHeight", "-->"+MaPhoto.getHeight());
                            Log.e("BitMap getWidth", "-->"+MaPhoto.getWidth());
                            imagerecup.setImageBitmap(MaPhoto);
                            Log.e("TOTO", "Affectation photo");
                            currentTime = Calendar.getInstance().getTime();
                            Toast.makeText(getApplicationContext(),"Mon Fichier "+photo, Toast.LENGTH_LONG).show();
                            
                        } catch (FileNotFoundException e) {
                            Log.e("TOTO", "IO FileNotFoundException -->"+e.toString());
                            e.printStackTrace();
                            Toast.makeText(getApplicationContext(),"IO FileNotFoundException -->"+e.toString(), Toast.LENGTH_LONG).show();
                            
                        } catch (IOException e) {
                            e.printStackTrace();
                            Log.e("TOTO", "IO Exception -->"+e.toString());
                            Toast.makeText(getApplicationContext(),"IO Exception -->"+e.toString(), Toast.LENGTH_LONG).show();
                        }
                        
                break;

            case RESULT_CANCELED:
                Toast.makeText(getApplicationContext(),"Je suis de retour -->RESULT_CANCELED", Toast.LENGTH_LONG).show();
                if(photo.exists())
                {
                    if(photo.delete())
                    {
                        imagerecup.setImageBitmap(null);
                        Toast.makeText(getApplicationContext(),"Supression OK", Toast.LENGTH_LONG).show();
                    }
                    else 
                    {
                        Toast.makeText(getApplicationContext(),"Impossible de supprimer", Toast.LENGTH_LONG).show();
                    }
                
            	 Toast.makeText(getApplicationContext(),"Je suis de retour -->default", Toast.LENGTH_LONG).show();
                 break;
                }
                break;
            default:
                break;
            }
	}
            
	}
	
	
	public void ResizePic(File photoFile)
	{
        int mOutputY = 400;
        int mOutputX = 400;

        BitmapFactory.Options opts = new BitmapFactory.Options();
        Bitmap croppedImage;
        Bitmap mBitmap = null;
        opts.inJustDecodeBounds = true;

        try 
        {

             mBitmap = BitmapFactory.decodeFile(photoFile.toString(), opts);

         } 
        
        catch (Exception e) 
        {
        }
        int i = opts.outHeight * opts.outWidth;
        opts.inJustDecodeBounds = false;

        if (i > 5E6) 
        {
              opts.inSampleSize = 2;
        } 
        else 
        {
              opts.inSampleSize = 1;
        }

        try 
        {
             mBitmap = BitmapFactory.decodeFile(photoFile.toString(), opts);
        } 
        catch (Exception e) 
        {
        }
        
        int mInputX = mBitmap.getWidth();
        int mInputY = mBitmap.getHeight();
        
        if (mInputX>mInputY)
        {
              i = mOutputY;
              mOutputY = mOutputX;
              mOutputX = i;
        }
        croppedImage = Bitmap.createBitmap(mOutputX, mOutputY, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(croppedImage);
        Rect srcRect = new Rect(0, 0, mInputX, mInputY);
        Rect dstRect = new Rect(0, 0, mOutputX, mOutputY);
        canvas.drawBitmap(mBitmap, srcRect, dstRect, null);
        mBitmap.recycle();
        OutputStream outputStream = null;

        try 
        {
              outputStream = getContentResolver().openOutputStream(Uri.fromFile(photoFile));

              if (outputStream != null) 
              {
                     croppedImage.compress(Bitmap.CompressFormat.JPEG, 65, outputStream);
              }

              outputStream.flush();
              outputStream.close();
              croppedImage.recycle();
        }
        
        catch (FileNotFoundException e) 
        {
              e.printStackTrace();
        }
        
        catch (IOException e) 
        {
              e.printStackTrace();
        }

     }

    public Bitmap decodeFile(String pathName) 
    {

       Bitmap bitmap = null;

       BitmapFactory.Options options = new BitmapFactory.Options();

       for (options.inSampleSize = 1; options.inSampleSize <= 32; options.inSampleSize++) 
       {

	       try 
	       {
		       bitmap = BitmapFactory.decodeFile(pathName, options);
		       Log.e("photo", "Decoded successfully for sampleSize " + options.inSampleSize);
	       break;
	       }
       
	       catch (OutOfMemoryError outOfMemoryError) 
	       {
	
	       // If an OutOfMemoryError occurred, we continue with for loop and next inSampleSize value
	
	    	   Log.e("photo", "outOfMemoryError while reading file for sampleSize " + options.inSampleSize + " retrying with higher value");
	       }
       }
       return bitmap;
    }
	
    
	@Override
	public void onLocationChanged(Location location) 
	{
		lat=location.getLatitude();
		lon=location.getLongitude();
		mBeyondarFragment.setWorld(mWorld);
		Toast.makeText(getApplicationContext(), "Position: " + lat + lon, Toast.LENGTH_LONG).show();
			
	}
	
	public float Distance(double lat, double lon, double lat2, double lon2) 
	{
		Location loc1 = new Location("");
		loc1.setLatitude(lat);
		loc1.setLongitude(lon);
		
		Location loc2 = new Location("");
		loc2.setLatitude(lat2);
		loc2.setLongitude(lon2);
		
		float distanceMeters=loc1.distanceTo(loc2);
		return distanceMeters;
		
	}
	
	@Override
	public void onClick(View v) 
	{
		switch (v.getId()) 
		{
		case R.id.myLocationButton:
			Log.e("Button1", "POSITION GPS BUTTON 1 OK ");
			Toast.makeText(getApplicationContext(), "Latitude: " + nouvellelat + ", Longitude: " + nouvellelon, Toast.LENGTH_SHORT).show();
			mBeyondarFragment.setWorld(bWorld);
			position.setVisibility(View.GONE);
			position1.setVisibility(View.VISIBLE);
			break;
			
		case R.id.myLocationButton2:
			Log.e("Button2", "POSITION GPS BUTTON 2 OK");
			Toast.makeText(getApplicationContext(), "Latitude: " + lat + ", Longitude: " + lon, Toast.LENGTH_SHORT).show();
			mBeyondarFragment.setWorld(mWorld);
			position1.setVisibility(View.GONE);
			position.setVisibility(View.VISIBLE);
			break;
			
		case R.id.myDistanceButton:
			Toast.makeText(getApplicationContext(),""+Distance(lat, lon, nouvellelat, nouvellelon)+"", Toast.LENGTH_SHORT).show();
			break;
			
		default:
			break;
		}
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event) 
	{
		return super.onTouchEvent(event);
	}
	
	@Override
	public void onClickBeyondarObject(ArrayList<BeyondarObject> beyondarObjects) 
	{
		if (beyondarObjects.size() == 0) 
		{
			return;
		}
		BeyondarObject beyondarObject = beyondarObjects.get(0);
		
		if (showViewOn.contains(beyondarObject)) 
		{
			showViewOn.remove(beyondarObject);
		} else 
		{
			showViewOn.add(beyondarObject);
		}
	}
	
	public class CustomBeyondarViewAdapter extends BeyondarViewAdapter implements OnClickListener
	{
		LayoutInflater inflater;

		public CustomBeyondarViewAdapter(Context context) 
		{
			super(context);
			inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
		}

		@Override
		public View getView(BeyondarObject beyondarObject, View recycledView, ViewGroup parent)
		{
			if (!showViewOn.contains(beyondarObject)) 
			{
				//return null;
			}
			if (recycledView==null)
			{
				recycledView=inflater.inflate(R.layout.beyondar_object_view, null);
			}
			
			TextView textView=(TextView)recycledView.findViewById(R.id.titleTextView);		
			textView.setText(beyondarObject.getName());
			ImageView image=(ImageView)findViewById(R.id.image);
			Button button=(Button)recycledView.findViewById(R.id.button);
			button.setOnClickListener(this);
			Button buttonPhoto=(Button)recycledView.findViewById(R.id.buttonPhoto);
			buttonPhoto.setOnClickListener(this);
			
			setPosition(beyondarObject.getScreenPositionTopRight());
			
			return recycledView;
		}
		
		
		@Override
		public void onClick(View v) 
		{
			switch (v.getId()) 
			{
			case R.id.button:
				Uri uri= Uri.parse("http://www.google.fr");
				Intent intent=new Intent(Intent.ACTION_VIEW,uri);
				startActivity(intent);
				break;
				
			case R.id.buttonPhoto:
				Intent intent2 = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
				currentTime = Calendar.getInstance().getTime();
				nouvelleDate = sdf.format(currentTime);
				photo = new File(Environment.getExternalStorageDirectory(), nouvelleDate+".jpg" );
			    intent2.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(photo));
			    startActivityForResult(intent2, 100);
				break;
			case R.id.imagerecup:
				SupprimerPhoto(photo,imagerecup);
				
			default:
				break;
			}
			
		}
		
		private void SupprimerPhoto(File MonFichier, ImageView MonImageView) 
		{
			if(MonFichier.exists()) 
			{
				if (MonFichier.delete()) 
				{
					imagerecup.setImageBitmap(null);
					Toast.makeText(getApplicationContext(),"Supression OK", Toast.LENGTH_LONG).show();
					
				}
			}
			
		}
		
	}
	

	
	protected void onResume(Bundle savedInstanceState) 
	{
		super.onResume();
		bn.enable();
	}
	
	protected void onPause(Bundle savedInstanceState) 
	{
		super.onPause();
		bn.disable();
		bn.addWorldLocationUpdate(mWorld);
		bn.addGeoObjectLocationUpdate(geo1);
	}

	
	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) 
	{
		Log.e("Latitude","status");
	}

	@Override
	public void onProviderEnabled(String provider) 
	{
		Log.e("Latitude","enable");
	}

	@Override
	public void onProviderDisabled(String provider) 
	{
		Log.e("Latitude","disable");
	}

}
