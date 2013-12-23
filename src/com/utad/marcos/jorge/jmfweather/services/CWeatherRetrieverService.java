/**************************************************************/
/*                                                            */
/* CWeatherRetrieverService.java                              */
/* (c)2013 jmarcosf                                           */
/*                                                            */
/* Description: CWeatherRetrieverService Class                */
/*              JmfWeather Project                            */
/*              Práctica asignatura Android Fundamental       */ 
/*              U-Tad - Master Apps                           */ 
/*              www.u-tad.com                                 */ 
/*                                                            */ 
/*        Date: December 2013                                 */ 
/*                                                            */
/**************************************************************/
package com.utad.marcos.jorge.jmfweather.services;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.util.HashSet;

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
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import com.utad.marcos.jorge.jmfweather.CApp;
import com.utad.marcos.jorge.jmfweather.db.CWeatherDAO;
import com.utad.marcos.jorge.jmfweather.model.CCity;
import com.utad.marcos.jorge.jmfweather.model.CCityList;
import com.utad.marcos.jorge.jmfweather.model.CForecast;
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
private static final int ALARM_REQUEST_CODE     = 0x4777;
private static final int ALARM_INITIAL_TIMEOUT  = ( 5 * 1000 );        // 5 Seconds

private CWeatherDAO                                    m_WeatherDAO = null;
private IWeatherRetrieverListener	                    m_Listener = null;
private HashSet< AsyncTask< String, Void, Void > >     m_LoadImageTaskSet;

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
          super.onCreate();
          Log.d( CWeatherRetrieverService.class.getSimpleName(), "onCreate()" );
          m_WeatherDAO = new CWeatherDAO( getBaseContext() );
          this.m_LoadImageTaskSet = new HashSet< AsyncTask< String, Void, Void > >();
          StartAlarm( getBaseContext() );
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
          StopAlarm( getBaseContext() );
          m_WeatherDAO.Close();
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
          Log.d( CWeatherRetrieverService.class.getSimpleName(), "onSetListener()" );
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
          PendingIntent pendingIntent = PendingIntent.getService( context, ALARM_REQUEST_CODE, TaskIntent, PendingIntent.FLAG_CANCEL_CURRENT );
          return pendingIntent;
     }

     /*********************************************************/
     /*                                                       */ 
     /* CWeatherRetrieverService.StartAlarm()                 */ 
     /*                                                       */ 
     /*********************************************************/
     public static void StartAlarm( Context context )
     {
          Log.d( CWeatherRetrieverService.class.getSimpleName(), "StartAlarm()" );
          int timeout = CApp.getWeatherSyncFrequecyTimeout();
          PendingIntent pendingIntent = CWeatherRetrieverService.getPendingIntent( context );
          AlarmManager alarmManager = (AlarmManager)context.getSystemService( Context.ALARM_SERVICE );
          alarmManager.setInexactRepeating( AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + ALARM_INITIAL_TIMEOUT, timeout, pendingIntent );
     }

     /*********************************************************/
     /*                                                       */ 
     /* CBlogRetrieverService.StartAlarm()                    */ 
     /*                                                       */ 
     /*********************************************************/
     public static void StartAlarm( Context context, int Timeout )
     {
          Log.d( CWeatherRetrieverService.class.getSimpleName(), "StartAlarm( timeout )" );
          StopAlarm( context );
          AlarmManager alarmManager = (AlarmManager)context.getSystemService( Context.ALARM_SERVICE );
          alarmManager.setInexactRepeating( AlarmManager.RTC_WAKEUP, ALARM_INITIAL_TIMEOUT, Timeout, getPendingIntent( context ) );
     }
     
     /*********************************************************/
     /*                                                       */ 
     /* CWeatherRetrieverService.StopAlarm()                  */ 
     /*                                                       */ 
     /*********************************************************/
     public static void StopAlarm( Context context )
     {
          Log.d( CWeatherRetrieverService.class.getSimpleName(), "StopAlarm()" );
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
          Log.d( CWeatherRetrieverService.class.getSimpleName(), "AddCurrentLocation()" );
          boolean bLoadCurrentLocationOnStartup = CApp.IsLoadCurrentLocationOnStartupEnabled();
          if( !bLoadCurrentLocationOnStartup ) return;

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
			new CInetLoader().execute();
			return true;
		}
		else	return false;
	}
	
     /*********************************************************/
     /*                                                       */ 
     /* CWeatherRetrieverService.LoadCityImages()             */ 
     /*                                                       */ 
     /*********************************************************/
     public void LoadCityImages( CCity City, CForecastList ForecastList )
     {
          Log.d( CWeatherRetrieverService.class.getSimpleName(), "LoadCityImages()" );
          if( City.getCondition() != null ) new CLoadImageTask().execute( City.getCondition().getIconUrl() );
          for( CForecast Forecast : ForecastList.getForecastList() )
          {
               new CLoadImageTask().execute( Forecast.getIconUrl() );
          }
     }
     
	/*********************************************************/
	/*                                                       */
	/* CWeatherRetrieverService.StopLoadingImages()          */
	/*                                                       */
	/*********************************************************/
	public void StopLoadingImages()
	{
          Log.d( CWeatherRetrieverService.class.getSimpleName(), "StopLoadingImages()" );
	     HashSet< AsyncTask< String, Void, Void > > tmpSet = new HashSet< AsyncTask< String, Void, Void > >();
	     tmpSet.addAll( m_LoadImageTaskSet );
	     for( AsyncTask< String, Void, Void > Task : tmpSet )
	     {
	          Task.cancel( true );
	     }
	     m_LoadImageTaskSet = new HashSet< AsyncTask< String, Void, Void > >();
	}

	/*********************************************************/
	/*                                                       */ 
	/*                                                       */ 
	/* CWeatherRetrieverService CInetLoader inner Class      */ 
	/*                                                       */ 
	/*                                                       */ 
	/*********************************************************/
	private class CInetLoader extends AsyncTask< Void, Void, Void >
	{
     CWorldWeatherApi WorldWeatherApi = null;
	     
	     /****************************************************/
	     /*                                                  */ 
	     /* CInetLoader.onPreExecute()                       */ 
	     /*                                                  */ 
	     /****************************************************/
	     @Override
	     protected void onPreExecute()
	     {
	          super.onPreExecute();
               Log.d( CWeatherRetrieverService.class.getSimpleName(), "CInetLoader().onPreExecute()" );
               WorldWeatherApi = new CWorldWeatherApi();
	     }
	     
		/****************************************************/
		/*                                                  */ 
		/* CInetLoader.doInBackground()                     */ 
		/*                                                  */ 
		/****************************************************/
          @Override
          protected Void doInBackground( Void... params )
          {
               Log.d( CWeatherRetrieverService.class.getSimpleName(), "CInetLoader().doInBackground()" );
               if( CApp.getFirstCall() )
               {
                    AddCurrentLocation( WorldWeatherApi, m_WeatherDAO, CWeatherRetrieverService.this );
                    CApp.setFirstCall( false );
               }
          	
          	CCityList CityList = new CCityList();
          	Cursor cursor = m_WeatherDAO.SelectAllCities();
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
          		     m_WeatherDAO.SetCityCondition( City );
	                    CForecastList ForecastList = WorldWeatherApi.getCityWeather( City );
    	                    m_WeatherDAO.Update( City, ForecastList );
  	                    LoadCityImages( City, ForecastList );
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
               Log.d( CWeatherRetrieverService.class.getSimpleName(), "CInetLoader().onPostExecute()" );
               try
               {
                    WorldWeatherApi.Close();
               }
               catch( IOException exception )
               {
                    exception.printStackTrace();
               }
     		if( m_Listener != null )	m_Listener.onWeatherLoaded();
          }
	}
	
	/*********************************************************/
	/*                                                       */
	/*                                                       */
	/* CWeatherRetrieverService CLoadImageTask inner Class   */
	/*                                                       */
	/*                                                       */
	/*********************************************************/
	private class CLoadImageTask extends AsyncTask< String, Void, Void >
	{
	     /****************************************************/
	     /*                                                  */ 
	     /* CLoadImageTask.onPreExecute()                    */ 
	     /*                                                  */ 
	     /****************************************************/
	     @Override
	     protected void onPreExecute()
	     {
	          Log.d( CWeatherRetrieverService.class.getSimpleName(), "CLoadImageTask().onPreExecute()" );
	          super.onPreExecute();
	          m_LoadImageTaskSet.add( this );
	     }

	     /****************************************************/
	     /*                                                  */
	     /* CLoadImageTask.doInBackground()                  */
	     /*                                                  */
	     /****************************************************/
	     @Override
	     protected Void doInBackground( String... params )
	     {
               Log.d( CWeatherRetrieverService.class.getSimpleName(), "CLoadImageTask().doInBackground()" );
	          if( params.length != 1 ) return null;
	          
               String ImageUrl = params[ 0 ];
	          Uri ImageUri = Uri.parse( ImageUrl );
	          File CacheDirectory = CWeatherRetrieverService.this.getCacheDir();
	          String FileName = ImageUri.getLastPathSegment();
	          File ImageFile = new File( CacheDirectory, FileName );
	          
	          if( ImageFile.exists() ) return null;
               
	          CWorldWeatherApi Request = new CWorldWeatherApi();
               FileOutputStream Output = null;
               try
               {
                    Request.Connect( new URL( ImageUrl ) );
                    byte[] imageBinary = Request.getBytes();
                    Output = new FileOutputStream( ImageFile );
                    Output.write( imageBinary );
               }
               catch( MalformedURLException exception )
               {
                    exception.printStackTrace();
               }
               catch( IOException exception )
               {
                    exception.printStackTrace();
               }
               catch( InterruptedException exception )
               {
                    exception.printStackTrace();
               }
               finally
               {
                    try
                    {
                         Output.close();
                         Request.Close();
                    }
                    catch( IOException exception )
                    {
                         exception.printStackTrace();
                    }
               }

	          return null;
	     }

	     /****************************************************/
	     /*                                                  */
	     /* CLoadImageTask.onPostExecute()                   */
	     /*                                                  */
	     /****************************************************/
	     @Override
	     protected void onPostExecute( Void result )
	     {
               Log.d( CWeatherRetrieverService.class.getSimpleName(), "CLoadImageTask().onPostExecute()" );
	          m_LoadImageTaskSet.remove( this );
	     }

	     /****************************************************/
	     /*                                                  */
	     /* CLoadImageTask.onCancelled()                     */
	     /*                                                  */
	     /****************************************************/
	     @Override
	     protected void onCancelled( Void result )
	     {
               Log.d( CWeatherRetrieverService.class.getSimpleName(), "CLoadImageTask().onCancelled()" );
               m_LoadImageTaskSet.remove( this );
	     }
	}
}
	
