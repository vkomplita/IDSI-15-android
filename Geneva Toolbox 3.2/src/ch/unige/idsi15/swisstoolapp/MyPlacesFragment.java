package ch.unige.idsi15.swisstoolapp;

import ch.unige.idsi15.swisstoolapp.MainActivity;


/*MySql JDBC*/
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;

import ch.unige.idsi15.swisstoolapp.R;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.maps.*;
import com.google.android.gms.maps.model.*;



public class MyPlacesFragment extends Fragment {
	
	MapView mMapView;
	private GoogleMap googleMap;
	
	
	
	public MyPlacesFragment(){
		
		
		
	}
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
		
 
        View v  = inflater.inflate(R.layout.fragment_myplaces, container, false);
      
        
        mMapView = (MapView) v.findViewById(R.id.mapView);
	    mMapView.onCreate(savedInstanceState);

	    mMapView.onResume();
	    

	    try {
	        MapsInitializer.initialize(getActivity().getApplicationContext());
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    
	    googleMap = mMapView.getMap();
    
	    testDB(MainActivity.getQrCodeId());
	    
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
	
	
	private static final String url = "jdbc:mysql://us-cdbr-iron-east-02.cleardb.net:3306/ad_28963a515d51815";
    private static final String user = "b76c1913ef8b0d";
    private static final String pass = "bb35e718";


    public void testDB(String qrCodeId) {
    	
    	//TextView tv = (TextView)getActivity().findViewById(R.id.txtLabel);
        String result  = null;
    	try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection(url, user, pass);
            /* System.out.println("Database section success"); */

           result = "Database connection success\n";
            Statement st = con.createStatement();
            String query="select * from marker where qr_code_id = '"+ qrCodeId +"'";
            ResultSet rs = st.executeQuery(query);
            
            
            Toast.makeText(getActivity(), "Mes Lieux chargées! Si la mappe est vide, réessayez le scan du qrCode", Toast.LENGTH_LONG).show();
            
            ResultSetMetaData rsmd = rs.getMetaData();

            while(rs.next()) {
                LatLng latLng = null;
            	
            	result += rsmd.getColumnName(1) + ": " + rs.getInt(1) + "\n";
            	result += rsmd.getColumnName(2) + ": " + rs.getString(2) + "\n";
            	result += rsmd.getColumnName(3) + ": " + rs.getDouble(3) + "\n";
            	result += rsmd.getColumnName(4) + ": " + rs.getDouble(4) + "\n";
            	result += rsmd.getColumnName(5) + ": " + rs.getString(5) + "\n";
                latLng = new LatLng(rs.getDouble(3),rs.getDouble(4));

                if(null == googleMap) {
                    throw new NullPointerException("map is null");
                }
                String address =MainActivity.getAddress(getActivity(),rs.getDouble(3), rs.getDouble(4));
                googleMap.addMarker(new MarkerOptions()
                        .title(address)
                        .snippet(rs.getString(2))
                        .position(latLng)
                );
               
                
                
            }
            LatLng pos = new LatLng(46.201695904168325, 6.140153110027313);
            CameraPosition cameraPosition = new CameraPosition.Builder()
            .target(pos).zoom(12).build();
    googleMap.animateCamera(CameraUpdateFactory
            .newCameraPosition(cameraPosition));
            
            
            
            
            
           // tv.setText(result);
           // Toast.makeText(getActivity(), result, Toast.LENGTH_LONG).show();
        }
        catch(Exception e) {
            e.printStackTrace();
            Log.e("DB", result);
            
        }   

    }
	
	
	
	
	
}
