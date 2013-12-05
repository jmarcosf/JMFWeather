/**************************************************************/
/*                                                            */ 
/* CMainActivity.java                                         */ 
/* (c)2013 jmarcosf                                           */ 
/*                                                            */ 
/* Description: CMainActivity Class                           */ 
/*              JmfWeather Project                            */ 
/*                                                            */ 
/**************************************************************/
package com.utad.marcos.jorge.jmfweather;

import android.app.Activity;
import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.IBinder;
import android.view.Menu;

import com.utad.com.jorge.marcos.jmfweather.R;
import com.utad.marcos.jorge.jmfweather.db.CWeatherDAO;
import com.utad.marcos.jorge.jmfweather.model.CCity;
import com.utad.marcos.jorge.jmfweather.model.CCityList;
import com.utad.marcos.jorge.jmfweather.services.CWeatherRetrieverService;
import com.utad.marcos.jorge.jmfweather.services.CWeatherRetrieverService.CWeatherRetrieverBinder;
import com.utad.marcos.jorge.jmfweather.services.CWeatherRetrieverService.IWeatherRetrieverListener;

/**************************************************************/
/*                                                            */ 
/*                                                            */ 
/*                                                            */ 
/* CMainActivity Class                                        */ 
/*                                                            */ 
/*                                                            */ 
/*                                                            */ 
/**************************************************************/
public class CMainActivity extends Activity
{
private	ServiceConnection		m_ServiceConnection;
private	CWeatherRetrieverBinder	m_Binder;

private	CCityList				m_CityList;

	/*********************************************************/
	/*                                                       */ 
	/*                                                       */ 
	/* Activity Override Methods                             */ 
	/*                                                       */ 
	/*                                                       */ 
	/*********************************************************/
	/*                                                       */ 
	/* CMainActivity.onCreate()                              */ 
	/*                                                       */ 
	/*********************************************************/
	@Override
	protected void onCreate( Bundle savedInstanceState )
	{
		super.onCreate( savedInstanceState );
		setContentView( R.layout.jmfweather_activity_main );
	}
	
	/*********************************************************/
	/*                                                       */ 
	/* CMainActivity.onStart()                               */ 
	/*                                                       */ 
	/*********************************************************/
     @Override
     protected void onStart()
     {
	     super.onStart();
	     ServiceConnect();
	     LoadCityList();
     }

	/*********************************************************/
	/*                                                       */ 
	/* CMainActivity.onStop()                                */ 
	/*                                                       */ 
	/*********************************************************/
     @Override
     protected void onStop()
     {
	     ServiceDisconnect();
	     super.onStop();
     }
     
	/*********************************************************/
	/*                                                       */ 
	/* CMainActivity.onCreateOptionsMenu()                   */ 
	/*                                                       */ 
	/*********************************************************/
	@Override
	public boolean onCreateOptionsMenu( Menu menu )
	{
		getMenuInflater().inflate( R.menu.cmain, menu );
		return true;
	}

	/*********************************************************/
	/*                                                       */ 
	/*                                                       */ 
	/* Class Methods                                         */ 
	/*                                                       */ 
	/*                                                       */ 
	/*********************************************************/
	/*                                                       */ 
	/* CMainActivity.LoadCityList()                          */ 
	/*                                                       */ 
	/*********************************************************/
	public void LoadCityList()
	{
		new CDBLoader().execute();
	}
	
	/*********************************************************/
	/*                                                       */ 
	/*                                                       */ 
	/* Service Binding Methods                               */ 
	/*                                                       */ 
	/*                                                       */ 
	/*********************************************************/
     /*                                                       */
     /* CMainActivity.ServiceConnect()                        */
     /*                                                       */
     /*********************************************************/
     private void ServiceConnect()
     {
		Intent service = new Intent( this, CWeatherRetrieverService.class );
		m_ServiceConnection = new ServiceConnection()
		{
			@Override
			public void onServiceConnected( ComponentName name, IBinder binder )
			{
				m_Binder = (CWeatherRetrieverBinder)binder;
				m_Binder.getService().setListener( new IWeatherRetrieverListener()
				{
					@Override
					public void onWeatherLoaded()
					{
						LoadCityList();
					}
				} );
				if( !m_Binder.getService().LoadWeather() )
				{
//					m_NetworkErrorView.setVisibility(View.VISIBLE);
				}
			}
			@Override
			public void onServiceDisconnected( ComponentName name )
			{
				m_Binder = null;
			}
		};
		
		bindService( service, m_ServiceConnection, Service.BIND_AUTO_CREATE );
     }
	
     /*********************************************************/
     /*                                                       */
     /* CMainActivity.ServiceDisconnect()                     */
     /*                                                       */
     /*********************************************************/
     private void ServiceDisconnect()
     {
     	if( m_ServiceConnection != null ) unbindService( m_ServiceConnection );
     }	

	/*********************************************************/
	/*                                                       */ 
	/*                                                       */ 
	/* CMainActivity.CDBLoader nested Class                  */ 
	/*                                                       */ 
	/*                                                       */ 
	/*********************************************************/
	private class CDBLoader extends AsyncTask< Void, Void, Cursor >
	{
		/****************************************************/
		/*                                                  */ 
		/* CDBLoader.onPreExecute()                         */ 
		/*                                                  */ 
		/****************************************************/
		@Override
		protected void onPreExecute()
		{
//		     m_WaitClock.setVisibility( View.VISIBLE );
//		     m_NetworkErrorView.setVisibility( View.GONE );
		}
		
		/****************************************************/
		/*                                                  */ 
		/* CDBLoader.doInBackground()                       */ 
		/*                                                  */ 
		/****************************************************/
          @Override
          protected Cursor doInBackground( Void... param )
          {
          	CWeatherDAO WeatherDAO = new CWeatherDAO( CMainActivity.this );
          	Cursor cursor = WeatherDAO.selectAllCities();
          	
          	m_CityList = new CCityList();
          	if( cursor.moveToFirst() )
          	{
          		do
          		{
          			m_CityList.add( new CCity( cursor ) );
          		} while( cursor.moveToNext() );
          	}
          	
          	return cursor;
          }
		
          /*********************************************************/
          /*                                                       */ 
          /* CDBLoader.onPostExecute()                             */ 
          /*                                                       */ 
          /*********************************************************/
          @Override
          protected void onPostExecute( Cursor cursor )
          {
//          	m_WaitClock.setVisibility( View.GONE );
//          	m_Adapter = new CCityListAdapter( CMainActivity.this, cursor );
//          	m_ListView.setAdapter( m_Adapter );
//          	
//          	if( !cursor.moveToFirst() || cursor.getCount() == 0 )
//     		{
//          		m_NetworkErrorView.setVisibility( View.VISIBLE );
//          	}
          }
	}
}
