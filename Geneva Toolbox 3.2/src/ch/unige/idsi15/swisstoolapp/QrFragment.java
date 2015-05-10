package ch.unige.idsi15.swisstoolapp;

import zxing.library.DecodeCallback;
import zxing.library.ZXingFragment;

import com.google.zxing.Result;

import android.support.v4.app.Fragment;
//import android.app.Fragment;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;




public class QrFragment extends ZXingFragment {
	
	public QrFragment(){
			
		
		this.setDecodeCallback(new DecodeCallback(){

	        @Override
	        public void handleBarcode(Result result, Bitmap arg1, float arg2) {
	        	
	        	MainActivity.setQrCodeId(result.getText());
	        	
	            Toast.makeText(getActivity(), "Application synchronisée: vos lieux d'intérêt sont disponibles sur 'Mes Lieux'" , Toast.LENGTH_LONG).show();
	            
	            //WhatsHotFragment.this.restartScanningIn(200);
	        }

	    });	
		
		
	}
	 /*
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
 
        View rootView = inflater.inflate(R.layout.fragment_whats_hot, container, false);
        
		//final ZXingFragment xf = (ZXingFragment) getFragmentManager().findFragmentById(R.id.scanner);
		
	   xf.setDecodeCallback(new DecodeCallback(){

	        @Override
	        public void handleBarcode(Result result, Bitmap arg1, float arg2) {
	            Toast.makeText(WhatsHotFragment.this.getActivity(), result.getText(), Toast.LENGTH_LONG).show();
	            xf.restartScanningIn(200);
	        }

	    });	
	   
        return rootView;
     }*/
}
