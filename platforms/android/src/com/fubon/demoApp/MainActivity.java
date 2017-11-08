package com.fubon.demoApp;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.ibm.MFPApplication;
import com.worklight.androidgap.api.WL;
import com.worklight.androidgap.api.WLInitWebFrameworkListener;
import com.worklight.androidgap.api.WLInitWebFrameworkResult;
import com.worklight.wlclient.api.WLResourceRequest;

import org.apache.cordova.CordovaActivity;

import java.net.URI;
import java.net.URISyntaxException;

public class MainActivity extends CordovaActivity implements WLInitWebFrameworkListener {

	public static MainActivity thisApp;

	@Override
	public void onCreate(Bundle savedInstanceState){

		super.onCreate(savedInstanceState);

        if (!((MFPApplication)this.getApplication()).hasCordovaSplashscreen()) {
            WL.getInstance().showSplashScreen(this);
        }

        MainActivity.thisApp = this;

        init();

		WL.getInstance().initializeWebFramework(getApplicationContext(), this);
	}

	/**
	 * The IBM MobileFirst Platform calls this method after its initialization is complete and web resources are ready to be used.
	 */
 	public void onInitWebFrameworkComplete(WLInitWebFrameworkResult result){
		if (result.getStatusCode() == WLInitWebFrameworkResult.SUCCESS) {
            super.loadUrl(WL.getInstance().getMainHtmlFilePath());

		} else {
			handleWebFrameworkInitFailure(result);
		}
	}

	private void handleWebFrameworkInitFailure(WLInitWebFrameworkResult result){
		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
		alertDialogBuilder.setNegativeButton(R.string.close, new OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which){
				finish();
			}
		});

		alertDialogBuilder.setTitle(R.string.error);
		alertDialogBuilder.setMessage(result.getMessage());
		alertDialogBuilder.setCancelable(false).create().show();
	}

	public static void containerAppMethod(){

		try{
			URI adapterPath = new URI("/adapters/httpAdapter/unprotected");

			WLResourceRequest request = new WLResourceRequest(adapterPath,WLResourceRequest.GET);

			request.send(new MyInvokeListener(thisApp));

		}
		catch(URISyntaxException error){
			Log.v("URISyntaxException", error.getMessage());
		}

	}

	@Override
	protected void onResume() {
		super.onResume();
	}

	@Override
	protected void onNewIntent(Intent intent)
	{
		super.onNewIntent(intent);

		if( intent.getExtras()!= null ) {
			Log.v("NewIntent", intent.getExtras().getString("param"));
			thisApp.containerAppMethod();
		}
	}
}
