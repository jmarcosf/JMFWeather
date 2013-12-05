package com.utad.marcos.jorge.jmfweather;

import com.utad.com.jorge.marcos.jmfweather.R;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class CMainActivity extends Activity
{

	@Override
	protected void onCreate( Bundle savedInstanceState )
	{
		super.onCreate( savedInstanceState );
		setContentView( R.layout.jmfweather_activity_main );
	}

	@Override
	public boolean onCreateOptionsMenu( Menu menu )
	{
		// Inflate the menu; this adds items to the action bar if it is
		// present.
		getMenuInflater().inflate( R.menu.cmain, menu );
		return true;
	}

}
