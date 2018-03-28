/*
 * Copyright (C) 2014 BeyondAR
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.geobeyondar;

import android.annotation.SuppressLint;
import android.content.Context;
import android.location.Location;
import android.webkit.WebView;
import android.widget.Toast;

import com.beyondar.android.world.GeoObject;
import com.beyondar.android.world.World;

@SuppressLint("SdCardPath")
public class CustomWorldHelper2 {
	public static final int LIST_TYPE_EXAMPLE_1 = 1;

	public static World sharedWorld;

	public static Location location2() 
	{
		Location loc = new Location("");
		loc.getLatitude();
		loc.getLatitude();
		return loc;
	}
	
	public static World generateObjects(Context context, double lat, double lon) 
	{
		
		if (sharedWorld != null) 
		{
			return sharedWorld;
		}
		sharedWorld = new World(context);

		// The user can set the default bitmap. This is useful if you are
		// loading images form Internet and the connection get lost
		sharedWorld.setDefaultImage(D.drawable.map);

		// User position (you can change it using the GPS listeners form Android
		// API)
		sharedWorld.setGeoPosition(41.90533734214473d, 2.565848038959814d);
		//sharedWorld.setGeoPosition(lat,lon);

		// Create an object with an image in the app resources.
//		GeoObject go1 = new GeoObject(1l);
//		go1.setGeoPosition(41.90523339794433d, 2.565036406654116d);
//		go1.setGeoPosition(lat, lon);
//		go1.setImageResource(R.drawable.ic_launcher);
//		go1.setName("Creature 1");

		// Is it also possible to load the image asynchronously form internet
//		GeoObject go2 = new GeoObject(2l);
//		go2.setGeoPosition(41.90518966360719d, 2.56582424468222d);
//		//go2.setGeoPosition(lat, lon);
//		go2.setImageUri("http://beyondar.github.io/beyondar/images/logo_512.png");
//		go2.setName("Informations: \n Nom: Online image");
//		
//		GeoObject map = new GeoObject(3l);
//		map.setGeoPosition(41.90550959641445d, 2.565873388087619d);
//		//map.setGeoPosition(lat, lon);
//		map.setImageUri("http://www.ville-martigues.fr/uploads/tx_stratisopenlayers/picto_or_2.png");
//		map.setName("Informations: \n Nom: Map");
		
		GeoObject asap = new GeoObject(4l);
		//asap.setGeoPosition(41.90534261025682d, 2.566164369775198d);
		asap.setGeoPosition(41.90518966360719d, 2.56582424468222d);
		asap.setImageUri("http://www.asap-info.com/media/logo_asap.png");
		asap.setName("Informations: \n Nom: ASAP");
//		asap.setLocation(location2());

//		// Also possible to get images from the SDcard
//		GeoObject go3 = new GeoObject(3l);
//		go3.setGeoPosition(41.90550959641445d, 2.565873388087619d);
//		go3.setImageResource(R.drawable.ic_launcher);
//		go3.setName("IronMan from sdcard");
//
//		// And the same goes for the app assets
//		GeoObject go4 = new GeoObject(4l);
//		go4.setGeoPosition(41.90518862002349d, 2.565662767707665d);
//		go4.setImageResource(R.drawable.ic_launcher);
//		go4.setName("Image from assets");
//		
//		GeoObject go5 = new GeoObject(5l);
//		go5.setGeoPosition(41.90553066234138d, 2.565777906882577d);
//		go5.setImageResource(R.drawable.ic_launcher);
//		go5.setName("Creature 5");

//		GeoObject go6 = new GeoObject(6l);
//		go6.setGeoPosition(41.90596218466268d, 2.565250806050688d);
//		go6.setImageResource(R.drawable.ic_launcher);
//		go6.setName("Creature 6");
//
//		GeoObject go7 = new GeoObject(7l);
//		go7.setGeoPosition(41.90581776104766d, 2.565932313852319d);
//		go7.setImageResource(R.drawable.ic_launcher);
//		go7.setName("Creature 2");
//
//		GeoObject go8 = new GeoObject(8l);
//		go8.setGeoPosition(41.90534261025682d, 2.566164369775198d);
//		go8.setImageResource(R.drawable.ic_launcher);
//		go8.setName("Object 8");
//
//		GeoObject go9 = new GeoObject(9l);
//		go9.setGeoPosition(41.90530734214473d, 2.565808038959814d);
//		go9.setImageResource(R.drawable.ic_launcher);
//		go9.setName("Creature 4");
		
//		
//		GeoObject go10 = new GeoObject(10l);
//		go10.setGeoPosition(42.006667d, 2.705d);
//		go10.setImageResource(R.drawable.ic_launcher);
//		go10.setName("Far away");
		
		// Add the GeoObjects to the world
//		sharedWorld.addBeyondarObject(go1);
//		sharedWorld.addBeyondarObject(go2, LIST_TYPE_EXAMPLE_1);
//		sharedWorld.addBeyondarObject(map);
		sharedWorld.addBeyondarObject(asap);
//		sharedWorld.addBeyondarObject(go3);
//		sharedWorld.addBeyondarObject(go4);
//		sharedWorld.addBeyondarObject(go5);
//		sharedWorld.addBeyondarObject(go6);
//		sharedWorld.addBeyondarObject(go7);
//		sharedWorld.addBeyondarObject(go8);
//		sharedWorld.addBeyondarObject(go9);
//		sharedWorld.addBeyondarObject(go10);
		
		return sharedWorld;
	}
	
	public static World deleteObjects(Context context, double latitude, double longitude) 
	{
		Location loc = new Location("");
		double lat=loc.getLatitude();
		double lon=loc.getLongitude();
		
		if (latitude!=lat && longitude!=lon) 
//		if (latitude!=0.0 && longitude!=0.0)
		{
			sharedWorld = new World(context);
			return sharedWorld;
		}
		else 
		{
			return sharedWorld;
		}
		
	}

}
