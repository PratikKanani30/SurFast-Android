package com.example.surfast;

import java.util.ArrayList;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageButton;
import android.widget.SearchView;
import android.widget.SearchView.OnQueryTextListener;
import android.widget.Toast;

public class MainActivity extends Activity {
	ImageButton b1;
	SearchView s1;
	WebView w1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		b1=(ImageButton)findViewById(R.id.imageButton1);
        s1=(SearchView)findViewById(R.id.searchView1);
        w1=(WebView)findViewById(R.id.webView1);
         
      	 b1.setOnClickListener(new OnClickListener() {
      	        @Override
      	        public void onClick(View arg0) {			
				Intent i=new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);	
				i.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
				i.putExtra(RecognizerIntent.EXTRA_PROMPT,"please say something");			
				startActivityForResult(i, 1);		
	}
		});
      	 
      	 
      	 s1.setQueryHint("Search here");
      	 s1.setOnQueryTextListener(new OnQueryTextListener() {
			
			@Override
			public boolean onQueryTextSubmit(String arg0) {
				Toast.makeText(getApplicationContext(), "Here you go", Toast.LENGTH_SHORT).show();
				w1.loadUrl("http://192.168.56.1:8080/surfast/web/searching.jsp?txtnm="+s1.getQuery());
				WebViewClient wvc=new WebViewClient()
				{
					public void onPageFinished(WebView w,String u)
					{
						super.onPageFinished(w,u);
						//String t=w.getTitle();
						//setTitle(t);					
					}
					
					public  void onPageStarted(WebView view, String url, Bitmap favicon)
					{
						super.onPageStarted(view, url, favicon);
					}
				};
				w1.setWebViewClient(wvc);			
				return false;			
			}

			@Override
			public boolean onQueryTextChange(String arg0) {
				// TODO Auto-generated method stub
				return false;
			}
		});   	 
	}


@Override
public void onActivityResult(int requestcode,int resultcode,Intent i)
{
	if(requestcode==1 && resultcode==RESULT_OK)
	{
		ArrayList<String> al= i.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
		String t=al.get(0).toString();
	    s1.setQuery(t,true);
	    //CharSequence q=s1.getQuery();
	    //Toast.makeText(getApplicationContext(), q, Toast.LENGTH_LONG).show();  
	}
	
	else
	{
		Toast.makeText(getApplicationContext(), "Oopps...Couldn't recognize you", Toast.LENGTH_LONG).show();	
	}
	
}
}
