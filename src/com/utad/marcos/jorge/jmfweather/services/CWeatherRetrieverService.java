/**************************************************************/
/*                                                            */
/* CWeatherRetrieverService.java                              */
/* (c)2013 jmarcosf                                           */
/*                                                            */
/* Description: CWeatherRetrieverService Class                */
/*              JmfWeather Project                            */
/*                                                            */
/**************************************************************/
package com.utad.marcos.jorge.jmfweather.services;

import java.io.IOException;
import java.text.ParseException;

import org.json.JSONException;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.location.Location;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import com.utad.marcos.jorge.jmfweather.db.CWeatherDAO;
import com.utad.marcos.jorge.jmfweather.model.CCity;
import com.utad.marcos.jorge.jmfweather.model.CCityList;
import com.utad.marcos.jorge.jmfweather.model.CForecastList;
import com.utad.marcos.jorge.jmfweather.utility.CWorldWeatherApi;

/**************************************************************/
/*                                                            */ 
/*                                                            */ 
/*                                                            */ 
/* CWeatherRetrieverService Class                             */ 
/*                                                            */ 
/*                                                            */ 
/*                                                            */ 
/**************************************************************/
public class CWeatherRetrieverService extends Service
{
private IWeatherRetrieverListener	m_Listener = null;

private static final int PERIODIC_TASK_REQUEST_CODE = 0x4777;
private static final int PERIODIC_TASK_TRIGGER_TIMEOUT = ( 10 * 1000 );         // 10 Seconds
//private static final int PERIODIC_TASK_INTERVAL_TIMEOUT = ( 60 * 1000 * 5 );  // Every 5 minutes
private static final int PERIODIC_TASK_INTERVAL_TIMEOUT = ( 30 * 1000 );        // Every 5 minutes

	/*********************************************************/
	/*                                                       */
	/*                                                       */
	/* CWeatherRetrieverService Listener inner Interface     */
	/*                                                       */
	/*                                                       */
	/*********************************************************/
	public interface IWeatherRetrieverListener
	{
		public void onWeatherLoaded();
	}

	/*********************************************************/
	/*                                                       */
	/*                                                       */
	/* CWeatherRetrieverBinder inner Class                   */
	/*                                                       */
	/*                                                       */
	/*********************************************************/
	public class CWeatherRetrieverBinder extends Binder
	{
		public CWeatherRetrieverService getService()
		{
			return CWeatherRetrieverService.this;
		}
	}
	
	/*********************************************************/
	/*                                                       */
	/*                                                       */
	/* Service Override Methods                              */
	/*                                                       */
	/*                                                       */
	/*********************************************************/
	/*                                                       */ 
	/* CWeatherRetrieverService.onCreate()                   */ 
	/*                                                       */ 
	/*********************************************************/
	@Override
	public void onCreate()
	{
	     Log.d( CWeatherRetrieverService.class.getSimpleName(), "onCreate()" );
          super.onCreate();
	}

     /*********************************************************/
     /*                                                       */ 
     /* CWeatherRetrieverService.onDestroy()                  */ 
     /*                                                       */ 
     /*********************************************************/
     @Override
     public void onDestroy()
     {
          Log.d( CWeatherRetrieverService.class.getSimpleName(), "onDestroy()" );
          super.onDestroy();
     }

	/*********************************************************/
	/*                                                       */
	/* CWeatherRetrieverService.onBind()                     */
	/*                                                       */
	/*********************************************************/
	@Override
	public IBinder onBind( Intent arg0 )
	{
	     Log.d( CWeatherRetrieverService.class.getSimpleName(), "onBind()" );
          new CInetLoader().execute( true );
          StartAlarm( getBaseContext() );
		return new CWeatherRetrieverBinder();
	}

	/*********************************************************/
	/*                                                       */ 
	/* CWeatherRetrieverService.onUnbind()                   */ 
	/*                                                       */ 
	/*********************************************************/
	@Override
	public boolean onUnbind( Intent intent )
	{
          Log.d( CWeatherRetrieverService.class.getSimpleName(), "onUnbind()" );
          StopAlarm( getBaseContext() );
		m_Listener = null;
		return super.onUnbind( intent );
	}
	
     /*********************************************************/
     /*                                                       */ 
     /* CWeatherRetrieverService.onStartCommand()             */ 
     /*                                                       */ 
     /*********************************************************/
     @Override
     public int onStartCommand( Intent intent, int flags, int startId )
     {
          Log.d( CWeatherRetrieverService.class.getSimpleName(), "onStartCommand()" );
          LoadWeather();
          return START_STICKY;
     }
     
	/*********************************************************/
	/*                                                       */
	/*                                                       */
	/* Class Methods                                         */
	/*                                                       */
	/*                                                       */
	/*********************************************************/
	/*                                                       */ 
	/* CWeatherRetrieverService.setListener()                */ 
	/*                                                       */ 
	/*********************************************************/
	public void setListener( IWeatherRetrieverListener listener )
	{
          Log.d( CWeatherRetrieverService.class.getSimpleName(), "SetListener()" );
		this.m_Listener = listener;
	}
	
     /*********************************************************/
     /*                                                       */ 
     /* CWeatherRetrieverService.getPendingIntent()           */ 
     /*                                                       */ 
     /*********************************************************/
     public static PendingIntent getPendingIntent( Context context )
     {
          Intent TaskIntent = new Intent( context, CWeatherRetrieverService.class );
          PendingIntent pendingIntent = PendingIntent.getService( context, PERIODIC_TASK_REQUEST_CODE, TaskIntent, PendingIntent.FLAG_CANCEL_CURRENT );
          return pendingIntent;
     }

     /*********************************************************/
     /*                                                       */ 
     /* CWeatherRetrieverService.StartAlarm()                 */ 
     /*                                                       */ 
     /*********************************************************/
     public static void StartAlarm( Context context )
     {
          PendingIntent pendingIntent = CWeatherRetrieverService.getPendingIntent( context );
          AlarmManager alarmManager = (AlarmManager)context.getSystemService( Context.ALARM_SERVICE );
          alarmManager.setInexactRepeating( AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + PERIODIC_TASK_TRIGGER_TIMEOUT, PERIODIC_TASK_INTERVAL_TIMEOUT, pendingIntent );
     }

     /*********************************************************/
     /*                                                       */ 
     /* CWeatherRetrieverService.StopAlarm()                  */ 
     /*                                                       */ 
     /*********************************************************/
     public static void StopAlarm( Context context )
     {
          PendingIntent pendingIntent = CWeatherRetrieverService.getPendingIntent( context );
          AlarmManager alarmManager = (AlarmManager)context.getSystemService( Context.ALARM_SERVICE );
          alarmManager.cancel( pendingIntent );
     }

     /*********************************************************/
     /*                                                       */ 
     /* CWeatherRetrieverService.AddCurrentLocation()         */ 
     /*                                                       */ 
     /*********************************************************/
     public static void AddCurrentLocation( CWorldWeatherApi WorldWeatherApi, CWeatherDAO WeatherDAO, Context context )
     {
          LocationManager locationManager = (LocationManager)context.getSystemService( Context.LOCATION_SERVICE );
          Location location = locationManager.getLastKnownLocation( LocationManager.GPS_PROVIDER );
          if( location != null )
          {
               try
               {
                    CCityList CityList = WorldWeatherApi.SearchLocation( "" + location.getLatitude(), "" + location.getLongitude() );
                    for( CCity City : CityList.getCityList() )
                    {
                         if( !WeatherDAO.ExistCity( City ) )
                         {
                              CForecastList ForecastList = WorldWeatherApi.getCityWeather( City ); 
                              WeatherDAO.Insert( City );
                              WeatherDAO.Insert( ForecastList, City );
                         }
                    }
               }
               catch( IOException exception )
               {
                    exception.printStackTrace();
               }
               catch( JSONException exception )
               {
                    exception.printStackTrace();
               }
               catch( ParseException exception )
               {
                    exception.printStackTrace();
               }
          }
     }
     
	/*********************************************************/
	/*                                                       */ 
	/* CWeatherRetrieverService.LoadWeather()                */ 
	/*                                                       */ 
	/*********************************************************/
	public boolean LoadWeather()
	{
          Log.d( CWeatherRetrieverService.class.getSimpleName(), "LoadWeather()" );
		ConnectivityManager ConnManager = (ConnectivityManager)getSystemService( Context.CONNECTIVITY_SERVICE );
		NetworkInfo NetInfo = ConnManager.getActiveNetworkInfo();

		if( NetInfo != null && NetInfo.isConnected() && m_Listener != null )
		{
			new CInetLoader().execute( false );
			return true;
		}
		else	return false;
	}
	
	/*********************************************************/
	/*                                                       */ 
	/*                                                       */ 
	/* CWeatherRetrieverService.CInetLoader inner Class      */ 
	/*                                                       */ 
	/*                                                       */ 
	/*********************************************************/
	private class CInetLoader extends AsyncTask< Boolean, Void, Void >
	{
		/****************************************************/
		/*                                                  */ 
		/* CInetLoader.doInBackground()                     */ 
		/*                                                  */ 
		/****************************************************/
          @Override
          protected Void doInBackground( Boolean... bAddCurrentLocation )
          {
          	CWorldWeatherApi WorldWeatherApi = new CWorldWeatherApi();
          	CWeatherDAO WeatherDAO = new CWeatherDAO( CWeatherRetrieverService.this );
          	if( bAddCurrentLocation[ 0 ] ) AddCurrentLocation( WorldWeatherApi, WeatherDAO, CWeatherRetrieverService.this );
          	CCityList CityList = new CCityList();

          	Cursor cursor = WeatherDAO.SelectAllCities();
          	if( cursor.moveToFirst() )
          	{
          		do
          		{
          			CityList.add( new CCity( cursor ) );
          		} while( cursor.moveToNext() );
          	}

          	try
          	{
          		for( CCity City : CityList.getCityList() )
          		{
	                    CForecastList ForecastList = WorldWeatherApi.getCityWeather( City );
    	                    WeatherDAO.Update( City, ForecastList );
          		}
          	}
               catch( IOException exception )
               {
	               exception.printStackTrace();
               }
               catch( JSONException exception )
               {
                    exception.printStackTrace();
               }
               catch( ParseException exception )
               {
                    exception.printStackTrace();
               }
               finally
               {
                    try
                    {
                         WorldWeatherApi.Close();
                    }
                    catch( IOException exception )
                    {
                         exception.printStackTrace();
                    }
                    WeatherDAO.Close();
               }
     		return null;
          }
		
          /*********************************************************/
          /*                                                       */ 
          /* CInetLoader.onPostExecute()                           */ 
          /*                                                       */ 
          /*********************************************************/
          @Override
          protected void onPostExecute( Void result )
          {
     		if( m_Listener != null )	m_Listener.onWeatherLoaded();
          }
	}
}
