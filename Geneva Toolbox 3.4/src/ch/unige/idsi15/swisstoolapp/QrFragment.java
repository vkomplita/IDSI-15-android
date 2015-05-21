package ch.unige.idsi15.swisstoolapp;

import zxing.library.DecodeCallback;
import zxing.library.ZXingFragment;
import com.google.zxing.Result;
import android.graphics.Bitmap;
import android.widget.Toast;

public class QrFragment extends ZXingFragment {
	public QrFragment(){
		this.setDecodeCallback(new DecodeCallback(){
	        @Override
	        public void handleBarcode(Result result, Bitmap arg1, float arg2) {
	        	//Renvoi du resultat de lecture du qrcode � la main Activity
	        	MainActivity.setQrCodeId(result.getText());
	            Toast.makeText(getActivity(), "Application synchronis�e: vos lieux d'int�r�t sont disponibles sur 'Mes Lieux'" , Toast.LENGTH_LONG).show();
	        }
	    });	
	}
}
