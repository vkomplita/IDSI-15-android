package ch.unige.idsi15.swisstoolapp;

import ch.unige.idsi15.swisstoolapp.R;
import android.support.v4.app.Fragment;
//import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.*;
import com.google.android.gms.maps.model.*;

public class HomeFragment extends Fragment {

	 static final LatLng HAMBURG = new LatLng(53.558, 9.927);
     static final LatLng KIEL = new LatLng(53.551, 9.993);
     MapView mMapView; 
     private GoogleMap googleMap; 
	
	public HomeFragment(){}
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
 
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);
         
       // http://stackoverflow.com/questions/16978190/add-google-maps-api-v2-in-a-fragment
        MapView mMapView =  (MapView) rootView.findViewById(R.id.map);
        
        mMapView.onCreate(savedInstanceState); 
        
        mMapView.onResume();// needed to get the map to display immediately 
 
        try { 
            MapsInitializer.initialize(getActivity().getApplicationContext()); 
        } catch (Exception e) { 
            e.printStackTrace(); 
        } 
      
        /*
        Marker hamburg = googleMap.addMarker(new MarkerOptions().position(HAMBURG)
                    .title("Hamburg"));
                
        
        Marker kiel = googleMap.addMarker(new MarkerOptions()
                    .position(KIEL)
                    .title("Kiel")
                    .snippet("Kiel is cool")
                    .icon(BitmapDescriptorFactory
                        .fromResource(R.drawable.ic_launcher)));

                // Move the camera instantly to hamburg with a zoom of 15.
                googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(HAMBURG, 15));

                // Zoom in, animating the camera.
                googleMap.animateCamera(CameraUpdateFactory.zoomTo(10), 2000, null);
        */
        
        
        return rootView;
    }
}
