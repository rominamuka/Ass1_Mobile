package com.romina.employeeinfo;

import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient.ConnectionCallbacks;
import com.google.android.gms.common.GooglePlayServicesClient.OnConnectionFailedListener;
import com.google.android.gms.location.LocationClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMyLocationButtonClickListener;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;

public class Map extends FragmentActivity
implements OnMyLocationButtonClickListener,
ConnectionCallbacks,
OnConnectionFailedListener, com.google.android.gms.location.LocationListener{

private GoogleMap mMap;
private LocationClient mLocationClient;

private static final LocationRequest REQUEST = LocationRequest.create()
 .setInterval(5000)         
 .setFastestInterval(16)   
 .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

@Override
protected void onCreate(Bundle savedInstanceState) {
super.onCreate(savedInstanceState);
setContentView(R.layout.map);
setUpMapIfNeeded();

}
@Override

protected void onResume() {
super.onResume();
setUpMapIfNeeded();
setUpLocationClientIfNeeded();
mLocationClient.connect();
}
@Override
public void onPause() {
 super.onPause();
 if (mLocationClient != null) {
     mLocationClient.disconnect();
 }
}

private void setUpMapIfNeeded() {
 
 if (mMap == null) {
    
     mMap = ((SupportMapFragment)getSupportFragmentManager().findFragmentById(R.id.map)).getMap();
     
     if (mMap != null) {
         mMap.setMyLocationEnabled(true);
         mMap.setOnMyLocationButtonClickListener(this);
     }
 }
}
public boolean onMyLocationButtonClick() {
 return false;
}
@Override
public void onLocationChanged(Location loc) {
	LatLng latLng = new LatLng(loc.getLatitude(),loc.getLongitude());
	CameraUpdate camU = CameraUpdateFactory.newLatLngZoom(latLng,15);
	mMap.animateCamera(camU);
}


@Override
public void onConnectionFailed(ConnectionResult result) {


}
@Override
public void onDisconnected() {
	

}
@Override
public void onConnected(Bundle connectionHint) {
	mLocationClient.requestLocationUpdates(REQUEST,this);  
}
private void setUpLocationClientIfNeeded() {
 if (mLocationClient == null) { mLocationClient = new LocationClient(getApplicationContext(),
             this,  
             this); 
 }
}


}