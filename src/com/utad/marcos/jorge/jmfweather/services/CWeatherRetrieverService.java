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

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Binder;
import android.os.IBinder;

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
	}

	/*********************************************************/
	/*                                                       */
	/* CWeatherRetrieverService.onBind()                     */
	/*                                                       */
	/*********************************************************/
	@Override
	public IBinder onBind( Intent arg0 )
	{
		return new CWeatherRetrieverBinder();
	}

	/*********************************************************/
	/*                                                       */ 
	/* CWeatherRetrieverService.onStartCommand()             */ 
	/*                                                       */ 
	/*********************************************************/
	@Override
	public int onStartCommand( Intent intent, int flags, int startId )
	{
		LoadWeather();
		return START_STICKY;
	}
	
	/*********************************************************/
	/*                                                       */ 
	/* CWeatherRetrieverService.onUnbind()                   */ 
	/*                                                       */ 
	/*********************************************************/
	@Override
	public boolean onUnbind( Intent intent )
	{
		m_Listener = null;
		return super.onUnbind( intent );
	}
	
	/*********************************************************/
	/*                                                       */ 
	/* CWeatherRetrieverService.onDestroy()                  */ 
	/*                                                       */ 
	/*********************************************************/
	@Override
	public void onDestroy()
	{
		super.onDestroy();
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
		this.m_Listener = listener;
	}
	
	/*********************************************************/
	/*                                                       */ 
	/* CWeatherRetrieverService.LoadWeather()                */ 
	/*                                                       */ 
	/*********************************************************/
	public boolean LoadWeather()
	{
		ConnectivityManager ConnManager = (ConnectivityManager)getSystemService( Context.CONNECTIVITY_SERVICE );
		NetworkInfo NetInfo = ConnManager.getActiveNetworkInfo();

		if( NetInfo != null && NetInfo.isConnected() )
		{
			new CInetLoader().execute();
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
	private class CInetLoader extends AsyncTask< Void, Void, Void >
	{
		/****************************************************/
		/*                                                  */ 
		/* CInetLoader.doInBackground()                     */ 
		/*                                                  */ 
		/****************************************************/
          @Override
          protected Void doInBackground( Void... _Void )
          {
          	CWorldWeatherApi WorldWeatherApi = new CWorldWeatherApi();
          	CWeatherDAO WeatherDAO = new CWeatherDAO( CWeatherRetrieverService.this );
          	CCityList CityList = new CCityList();

          	Cursor cursor = WeatherDAO.selectAllCities();
          	if( cursor.moveToFirst() )
          	{
          		do
          		{
          			CityList.add( new CCity( cursor ) );
          		} while( cursor.moveToNext() );
          	}

     		for( CCity City : CityList.getCityList() )
     		{
     			try
                    {
	                    CForecastList ForecastList = WorldWeatherApi.getCityWeather( City );
	                    WeatherDAO.update( City, ForecastList );
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
