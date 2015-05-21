package ch.unige.idsi15.swisstoolapp;

import ch.unige.idsi15.swisstoolapp.MainActivity;
import ch.unige.idsi15.swisstoolapp.model.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.io.*;

import org.xmlpull.v1.XmlPullParserException;
import ch.unige.idsi15.swisstoolapp.R;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.google.android.gms.maps.*;
import com.google.android.gms.maps.model.*;


public class HomeFragment extends Fragment {
	Marker navMarker;
	MapView mMapView;
	 private static final String LOGTAG = "KMLActivity";
	
	 /*Liste pour classifier les kml par leur nom. La selection de la liste est faite par la MainActivity */
	 private static final ArrayList<String> kmlList = new ArrayList<String>(
				Arrays.asList("", "pharma.kml",  "police.kml", "poste.kml", "taxi.kml", "sante.kml")
				); 
	
	private GoogleMap googleMap;
	private int kmlLayer;
	
	public HomeFragment(int check){
		kmlLayer = check;
	}
	
	
/*Lecteur/chargeur du Kml*/
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
	
	/*Parseur du KML + extracteur des informations +placement sur la map*/
	private KmlReader.Callback kmlReaderCallback = new KmlReader.Callback() {
        @Override
        public void onDocumentParsed(List<Placemark> placemarks) {
            Log.d(LOGTAG, "onDocumentParsed");
            LatLng latLng = null;
            for(Placemark placemark : placemarks) {
            	double latitude = placemark.getPoint().getLatitude();
            	double longitude = placemark.getPoint().getLongitude();
            	
            	/*Placement markers*/
                latLng = new LatLng(latitude,longitude);
                if(null == googleMap) {
                    throw new NullPointerException("map is null");
                }
                String address =MainActivity.getAddress(getActivity(),latitude, longitude);
                googleMap.addMarker(new MarkerOptions()
                        .title( address)
                      //  .snippet(placemark.getDescription())
                        .position(latLng)
                );
            }
        }
    };
    
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
	        Bundle savedInstanceState) {
	    // inflat and return the layout with the map
		
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
	    
	    // Controle si la valeur soumise est majeur de 0 et garde le centre de la visuelle

		if (kmlLayer>0){
			 Log.d(LOGTAG, kmlList.get(kmlLayer));
		startReadingKml(kmlList.get(kmlLayer));
		
		}
		LatLng posCam = new LatLng(46.201695904168325, 6.140153110027313);
	    CameraPosition cameraPosition = new CameraPosition.Builder()
	            .target(posCam).zoom(12).build();
	    googleMap.animateCamera(CameraUpdateFactory
	            .newCameraPosition(cameraPosition));
	    
	    //creation de un markeur selon l'endroit touché de la carte
	    googleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {	
			@Override
			public void onMapClick(LatLng point) {
				String address =MainActivity.getAddress(getActivity(),point.latitude, point.longitude);
				if(navMarker !=null){
					navMarker.remove();
					navMarker = null;
				}
				navMarker= googleMap.addMarker(new MarkerOptions()
									.title( address)
			                        .position(point)
			                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE))
			    );
			}
	    }); 
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
