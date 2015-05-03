package ch.unige.idsi15.swisstoolapp;


import ch.unige.idsi15.swisstoolapp.model.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.io.*;

import org.xmlpull.v1.XmlPullParserException;

import ch.unige.idsi15.swisstoolapp.R;
import android.support.v4.app.Fragment;
import android.app.FragmentManager;
//import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.maps.*;
import com.google.android.gms.maps.model.*;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.SupportMapFragment;

import org.xmlpull.v1.XmlPullParserException;


public class HomeFragment extends Fragment {

	MapView mMapView;
	 private static final String LOGTAG = "KMLActivity";
	//private ArrayList<Placemark> markerSet = null;
	
	 private static final ArrayList<String> kmlList = new ArrayList<String>(
				Arrays.asList("culture.kml", "pharma.kml", "police.kml", "poste.kml", "sante.kml")
				); 
	
	private GoogleMap googleMap;
	private int kmlLayer;
	
	public HomeFragment(int check){
		kmlLayer = check;
	}
	
	

	private void startReadingKml(String kmlName) {
        try {
            KmlReader reader = new KmlReader(kmlReaderCallback);
            InputStream fs = getActivity().getAssets().open(kmlName);
            reader.read(fs);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        }
    }
	private KmlReader.Callback kmlReaderCallback = new KmlReader.Callback() {
        @Override
        public void onDocumentParsed(List<Placemark> placemarks) {
            Log.d(LOGTAG, "onDocumentParsed");
            LatLng latLng = null;

            for(Placemark placemark : placemarks) {
                latLng = new LatLng(placemark.getPoint().getLatitude(),
                        placemark.getPoint().getLongitude());

                if(null == googleMap) {
                    throw new NullPointerException("map is null");
                }
                googleMap.addMarker(new MarkerOptions()
                        .title(placemark.getName())
                        .snippet(placemark.getDescription())
                        .position(latLng)
                );
            }

            if(null != latLng) {
                googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 13));
            }
        }
    };
	/*private void getMarkerSet(String url){
		
		//markerSet = MapService.getNavigationDataSet(url);//.getPlacemarks();
		Toast.makeText(getActivity(), MapService.getNavigationDataSet(url).toString(), Toast.LENGTH_LONG).show();		
		
	}*/
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
	        Bundle savedInstanceState) {
	    // inflat and return the layout
		
	    View v = inflater.inflate(R.layout.fragment_home, container,
	            false);
	    mMapView = (MapView) v.findViewById(R.id.mapView);
	    mMapView.onCreate(savedInstanceState);

	    mMapView.onResume();// needed to get the map to display immediately

	    try {
	        MapsInitializer.initialize(getActivity().getApplicationContext());
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	
	    googleMap = mMapView.getMap();
	    // latitude and longitude
	    LatLng pos = new LatLng(46.201695904168325, 6.140153110027313);

	    // create marker
	   MarkerOptions marker = new MarkerOptions().position(pos).title("Hello Maps");

	    // Changing marker icon
	    marker.icon(BitmapDescriptorFactory
	            .defaultMarker(BitmapDescriptorFactory.HUE_ROSE));

	    // adding marker
	    googleMap.addMarker(marker);
	    
	    
		if (kmlLayer>0){
			 Log.d(LOGTAG, kmlList.get(kmlLayer));
		startReadingKml(kmlList.get(kmlLayer));
		
		}
	  
	    
	    
	   
	    
		LatLng posCam = new LatLng(46.201695904168325, 6.140153110027313);
	    CameraPosition cameraPosition = new CameraPosition.Builder()
	            .target(posCam).zoom(12).build();
	    googleMap.animateCamera(CameraUpdateFactory
	            .newCameraPosition(cameraPosition));

	    // Perform any camera updates here
	    return v;
	}

	@Override
	public void onResume() {
	    super.onResume();
	    mMapView.onResume();
	}

	@Override
	public void onPause() {
	    super.onPause();
	    mMapView.onPause();
	}

	@Override
	public void onDestroy() {
	    super.onDestroy();
	    mMapView.onDestroy();
	}

	@Override
	public void onLowMemory() {
	    super.onLowMemory();
	    mMapView.onLowMemory();
	}

}
