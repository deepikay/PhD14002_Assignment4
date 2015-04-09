package com.example.uploadpdf;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class ShowActivity extends ActionBarActivity {

	TextView tx;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_show);
		
		
		System.out.println("Get Activity entered");
		Log.d("abc","GetActivity onCreate");
		tx = (TextView)findViewById(R.id.textView1);
		String output = null;
		
		Thread thread = new Thread(new Runnable() {
			@Override
			public void run() {
				try {

					Log.d("abc", "inside thread");
					System.out.println("inside threa");
					String output = null;
					try{
					HttpClient httpclient = new DefaultHttpClient();
					HttpGet httpget = new HttpGet("http://192.168.21.49:8080/Spring3MVC_Multiple_File_Upload_example/showfiles.html");
					try {
						HttpResponse response = httpclient.execute(httpget);

						Log.d("abc", "response code" + response.getStatusLine());
						Log.d("abc", "response:" + response.toString());
						
						if( response.getStatusLine().getStatusCode() ==  HttpStatus.SC_OK)
						{
							
							Log.d("abc", "HttpStatus.SC_OK");
		                	HttpEntity httpEntity = response.getEntity();
		                	ByteArrayOutputStream out = new ByteArrayOutputStream();
		                	httpEntity.writeTo(out);
		                	out.close();
		                	//output = EntityUtils.toString(httpEntity);
		                	
		                	output = out.toString();
		                	System.out.println(output);
		                	Log.d("abc", output);
							tx.setText(output);
							
							
						}
						
					} catch (ClientProtocolException e) {
							// handle exception
							Log.d("abc","ClientProtocolException");
						} catch (IOException e) {
							// handle exception
							Log.d("abc","exception"+e.getMessage()+", "+e.getStackTrace());
						}
					} catch (Exception ex) {
						ex.printStackTrace();
						Log.d("abc", "exception");
						System.out.println("exception"+ex.getMessage());
					}
						
					}catch (Exception ex) {
						ex.printStackTrace();
						Log.d("abc", "exception");
						System.out.println("exception"+ex.getMessage());
					}
			}
		});
		thread.start();
		
	}
		
		
		
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.show, menu);
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
