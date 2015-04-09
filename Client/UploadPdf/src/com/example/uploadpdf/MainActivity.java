package com.example.uploadpdf;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.ContentBody;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;




import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends ActionBarActivity  {

	
	private String srcPath = "/storage/emulated/0/Download/subj-1.pdf";
	Button b1, b2;
	TextView t1; 
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		System.out.println("On Create");
		
		t1 = (TextView)findViewById(R.id.textView2);
		t1.setText("/storage/emulated/0/Download/subj-1.pdf");
		Button b1 = (Button)findViewById(R.id.button1);
		Button b2 = (Button)findViewById(R.id.button2);
		
		b1.setOnClickListener(new OnClickListener() {
		
			@Override
			            public void onClick(View view) {
			
			                Toast.makeText(MainActivity.this, "Button Clicked", Toast.LENGTH_SHORT).show();
			                Thread thread = new Thread(new Runnable() {
			        			@Override
			        			public void run() {
			        				try {

			        					Log.d("abc", "inside thread");
			        					System.out.println("inside threa");
			        					
			        					try{
			        					HttpClient httpclient = new DefaultHttpClient();
			        					//httpclient.getParams().setParameter(CoreProtocolPNames.PROTOCOL_VERSION,
			        					//		HttpVersion.HTTP_1_1);

			        					HttpPost httppost = new HttpPost(
			        							"http://192.168.21.49:8080/Spring3MVC_Multiple_File_Upload_example/upload.html");
			        					
			        					//HttpGet httpget = new HttpGet(
			        					//	"http://192.168.21.49:8080/Spring3MVC_Multiple_File_Upload_example/pdf.html");
			        					
			        					try{
			        						File file = new File("/storage/emulated/0/Download/subj-1.pdf");
			        					
			        					MultipartEntity mpEntity = new MultipartEntity();
			        					ContentBody cbFile = new FileBody(file, "multipart/form-data");
			        					mpEntity.addPart("file", cbFile);
			        					//mpEntity.addPart();

			        					Log.d("abc", "httppost reuest is " + httppost.toString());

			        					httppost.setEntity(mpEntity);
			        					
			        					}catch (Exception e) {
			        						// handle exception
			        						Log.d("abc","exception in multipart or file"+e.getMessage()+", "+e.getStackTrace());
			        					}
			        					Log.d("abc",
			        							"executing request " + httppost.getRequestLine());
			        					System.out.println("executing request "
			        							+ httppost.getRequestLine());
			        					try {
			        						HttpResponse response = httpclient.execute(httppost);

			        						Log.d("abc", "response code" + response.getStatusLine());
			        						Log.d("abc", "response:" + response.toString());
			        						
			        						if( response.getStatusLine().getStatusCode() ==  HttpStatus.SC_OK)
			        						{
			        							Toast.makeText(getApplicationContext(),response.toString(),Toast.LENGTH_SHORT).show();
			        						
			        							/*HttpEntity entity = response.getEntity();
			    								ByteArrayOutputStream out = new ByteArrayOutputStream();
			    								entity.writeTo(out);
			    								out.close();
			    								String responseStr = out.toString();*/
			    								
			        							HttpEntity resEntity = response.getEntity();
			        	
			        							//Log.d("abc", "response string ::::::" + responseStr);
			        							if (resEntity != null) {
			        								Log.d("abc",
			        										"response entity "
			        												+ EntityUtils.toString(resEntity));
			        								System.out.println("response entity "
			        										+ EntityUtils.toString(resEntity));
			        							}
			        							if (resEntity != null) {
			        								resEntity.consumeContent();
			        							}
			        	
			        							httpclient.getConnectionManager().shutdown();
			        						}
			        					} catch (ClientProtocolException e) {
			        						// handle exception
			        						Log.d("abc","ClientProtocolException");
			        					} catch (IOException e) {
			        						// handle exception
			        						Log.d("abc","exception in reading respopnse"+e.getMessage()+", "+e.getStackTrace());
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

		});
		b2.setOnClickListener(new OnClickListener() {
				
			public void onClick(View view) {
				Log.d("abc", "show button pressed");
				Log.d("abc","Calling ShowActivity");
				Intent intent = new Intent(getApplicationContext(),ShowActivity.class);
				
				//intent.putExtra("Msg", "Hey");
				startActivity(intent);
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

	/*@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		Log.d("abc", "on click" + v.toString());
		
		if(v == b1){

		Thread thread = new Thread(new Runnable() {
			@Override
			public void run() {
				try {

					Log.d("abc", "inside thread");
					System.out.println("inside threa");
					
					try{
					HttpClient httpclient = new DefaultHttpClient();
					//httpclient.getParams().setParameter(CoreProtocolPNames.PROTOCOL_VERSION,
					//		HttpVersion.HTTP_1_1);

					HttpPost httppost = new HttpPost(
							"http://192.168.21.49:8080/Spring3MVC_Multiple_File_Upload_example/upload.html");
					
					//HttpGet httpget = new HttpGet(
					//	"http://192.168.21.49:8080/Spring3MVC_Multiple_File_Upload_example/pdf.html");
					
					try{
						File file = new File("/storage/emulated/0/Download/subj-1.pdf");
					
					MultipartEntity mpEntity = new MultipartEntity();
					ContentBody cbFile = new FileBody(file, "multipart/form-data");
					mpEntity.addPart("file", cbFile);
					//mpEntity.addPart();

					Log.d("abc", "httppost reuest is " + httppost.toString());

					httppost.setEntity(mpEntity);
					
					}catch (Exception e) {
						// handle exception
						Log.d("abc","exception in multipart or file"+e.getMessage()+", "+e.getStackTrace());
					}
					Log.d("abc",
							"executing request " + httppost.getRequestLine());
					System.out.println("executing request "
							+ httppost.getRequestLine());
					try {
						HttpResponse response = httpclient.execute(httppost);

						Log.d("abc", "response code" + response.getStatusLine());
						Log.d("abc", "response:" + response.toString());
						
						if( response.getStatusLine().getStatusCode() ==  HttpStatus.SC_OK)
						{
							Toast.makeText(getApplicationContext(),response.toString(),Toast.LENGTH_SHORT).show();
						
						
							HttpEntity resEntity = response.getEntity();
	
							//Log.d("abc", "response code" + response.getStatusLine());
							if (resEntity != null) {
								Log.d("abc",
										"response entity "
												+ EntityUtils.toString(resEntity));
								System.out.println("response entity "
										+ EntityUtils.toString(resEntity));
							}
							if (resEntity != null) {
								resEntity.consumeContent();
							}
	
							httpclient.getConnectionManager().shutdown();
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

		// if(v==upload){
			  
		/*	try{
				System.out.println("clicked upload button");
				String uploadUrl = "http://192.168.1.102:8080/SpringMVC_Multi_File_Upload_example/upload.html";
				
				
				File file = new File("/storage/emulated/0/Download/subj-1.pdf") ;
		        //Upload the file
		       // fileUpload.executeMultiPartRequest("http://localhost:8080/RESTfulDemoApplication/user-management/image-upload",
		               // file, file.getName(), "File Uploaded :: Tulips.jpg") ;
		        HttpClient client = new DefaultHttpClient() ;
		        HttpPost postRequest = new HttpPost (uploadUrl) ;
		        try
		        {
		            //Set various attributes
		            MultipartEntity multiPartEntity = new MultipartEntity () ;
		            multiPartEntity.addPart("fileDescription", new StringBody("File Uploaded :: subj-1.pdf")) ;
		            multiPartEntity.addPart("fileName", new StringBody("subj-1.pdf")) ;
		  
		            FileBody fileBody = new FileBody(file, "application/octect-stream") ;
		            //Prepare payload
		            multiPartEntity.addPart("attachment", fileBody) ;
		  
		            //Set to request body
		            postRequest.setEntity(multiPartEntity) ;
		             
		            //Send request
		            HttpResponse response = client.execute(postRequest) ;
		             
		            //Verify response if any
		            if (response != null)
		            {
		                System.out.println(response.getStatusLine().getStatusCode());
		            }
		        }
		        catch (Exception ex)
		        {
		            ex.printStackTrace() ;
		        }
				*/
				
				/*File file = new File("/storage/emulated/0/Download/subj-1.pdf");
				try {
				         HttpClient client = new DefaultHttpClient();  
				         HttpPost post = new HttpPost(uploadUrl); 
				         FileBody bin = new FileBody(file);
				         MultipartEntity reqEntity = new MultipartEntity(HttpMultipartMode.BROWSER_COMPATIBLE);  
				     reqEntity.addPart("myFile", bin);
				     post.setEntity(reqEntity);  
				     HttpResponse response = client.execute(post);  
				     HttpEntity resEntity = response.getEntity();
				     System.out.println(response.toString());
				     if (resEntity != null) {    
				               Log.i("RESPONSE",EntityUtils.toString(resEntity));
				              System.out.println(EntityUtils.toString(resEntity));
				         }
				} catch (Exception e) {
				    e.printStackTrace();
				}*/
			
			
			
//s}	
		//}
		
	/*	
		if(v == b2){
			Log.d("abc", "show button pressed");
			Log.d("abc","Calling ShowActivity");
			Intent intent = new Intent(getApplicationContext(),ShowActivity.class);
			
			//intent.putExtra("Msg", "Hey");
			startActivity(intent);
			
			
		}
			*/
			
			
		
	
	//}
}
