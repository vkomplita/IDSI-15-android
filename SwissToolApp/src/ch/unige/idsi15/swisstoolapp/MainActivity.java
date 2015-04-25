package ch.unige.idsi15.swisstoolapp;

import com.google.zxing.Result;

import zxing.library.DecodeCallback;
import zxing.library.ZXingFragment;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;
import android.graphics.Bitmap;

public class MainActivity extends ActionBarActivity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		final ZXingFragment xf = (ZXingFragment) getSupportFragmentManager().findFragmentById(R.id.scanner);
		
	    xf.setDecodeCallback(new DecodeCallback(){

	        @Override
	        public void handleBarcode(Result result, Bitmap arg1, float arg2) {
	            Toast.makeText(MainActivity.this, result.getText(), Toast.LENGTH_LONG).show();
	            xf.restartScanningIn(200);
	        }

	    });		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
