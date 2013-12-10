
/**************************************************************/
/*                                                            */
/* CWorldWeatherApi.java                                      */
/* (c)2013 jmarcosf                                           */
/*                                                            */
/* Description: CWorldWeatherApi Class                        */
/*              JmfWeather Project                            */
/*                                                            */
/**************************************************************/
package com.utad.marcos.jorge.jmfweather.utility;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import java.text.ParseException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.utad.marcos.jorge.jmfweather.model.CCity;
import com.utad.marcos.jorge.jmfweather.model.CCityList;
import com.utad.marcos.jorge.jmfweather.model.CCondition;
import com.utad.marcos.jorge.jmfweather.model.CForecast;
import com.utad.marcos.jorge.jmfweather.model.CForecastList;

/**************************************************************/
/*                                                            */ 
/*                                                            */ 
/*                                                            */ 
/* CWorldWeatherApi Class                                     */ 
/*                                                            */ 
/*                                                            */ 
/*                                                            */ 
/**************************************************************/
public class CWorldWeatherApi
{
private static final int		HTTP_CONNECT_TIMEOUT_MS = 15000; // millisecons
private static final int		HTTP_READ_TIMEOUT_MS = 10000; // millisecons
private static final String	WORLD_WEATHER_GET_WEATHER_URL = "http://api.worldweatheronline.com/free/v1/weather.ashx";
private static final String	WORLD_WEATHER_SEARCH_CITY_URL = "http://api.worldweatheronline.com/free/v1/search.ashx";
private static final String	WORLD_WEATHER_KEY = "hk3bn55kb4g7hjwhxqghx22g";

private Charset 			m_Charset;
private HttpURLConnection	m_Connection;
private InputStream 		m_InputStream;

	/*********************************************************/
	/*                                                       */ 
	/*                                                       */ 
	/* Class Constructors                                    */ 
	/*                                                       */ 
	/*                                                       */ 
	/*********************************************************/
	/*                                                       */ 
	/* CWorldWeatherApi.CWorldWeatherApi()                   */ 
	/*                                                       */ 
	/*********************************************************/
	public CWorldWeatherApi()
	{
		this( Charset.forName( "UTF-8" ) );
	}	
	
	/*********************************************************/
	/*                                                       */ 
	/* CWorldWeatherApi.CWorldWeatherApi()                   */ 
	/*                                                       */ 
	/*********************************************************/
	public CWorldWeatherApi( Charset Charset )
	{
		super();
		this.m_Charset = Charset;
	}
	
	/*********************************************************/
	/*                                                       */ 
	/*                                                       */ 
	/* Class Methods                                         */ 
	/*                                                       */ 
	/*                                                       */ 
	/*********************************************************/
	/*                                                       */ 
	/* CWorldWeatherApi.SearchCity()                         */ 
	/*                                                       */ 
	/*********************************************************/
	public CCityList SearchCity( String Name ) throws IOException, JSONException
	{
		if( Name == null ) return null;
		String Url = WORLD_WEATHER_SEARCH_CITY_URL + "?q=" + Name + "&format=json&key=" + WORLD_WEATHER_KEY;
		Connect( new URL( Url ) );
		
		JSONObject jsonResponse = getJSONObject();
		JSONObject Root = jsonResponse.getJSONObject( "search_api" );
		JSONArray Entries = Root.getJSONArray( "result" );

		CCityList CityList = new CCityList();
		for( int i = 0; i < Entries.length(); i++ )
		{
			JSONObject jsonObject = Entries.getJSONObject( i );
			CCity City = new CCity( jsonObject );
			CityList.add( City );
		}

		Close();
		return CityList; 
	}

	/*********************************************************/
	/*                                                       */ 
	/* CWorldWeatherApi.getCityWeather()                     */ 
	/*                                                       */ 
	/*********************************************************/
	public CForecastList getCityWeather( CCity City ) throws IOException, JSONException, ParseException
	{
		if( City == null ) return null;
		String Url = WORLD_WEATHER_GET_WEATHER_URL + "?q=" + City.getLatitude() + "+" + City.getLongitude() + "&format=json&extra=localObsTime&num_of_days=5&key=" + WORLD_WEATHER_KEY;
		Connect( new URL( Url ) );
		
		JSONObject jsonResponse = getJSONObject();
		JSONObject Root = jsonResponse.getJSONObject( "data" );
		JSONArray currentConditionArray = Root.getJSONArray( "current_condition" );
		
		CCondition CurrentCondition = new CCondition( currentConditionArray.getJSONObject( 0 ) );
		City.setCurrentCondition( CurrentCondition );
						
		JSONArray requestArray = Root.getJSONArray( "request" );
		JSONArray weatherArray = Root.getJSONArray( "weather" );

		CForecastList ForecastList = new CForecastList();
		for( int i = 0; i < weatherArray.length(); i++ )
		{
			JSONObject jsonObject = weatherArray.getJSONObject( i );
			CForecast Forecast = new CForecast( jsonObject );
			ForecastList.add( Forecast );
		}

		Close();
		return ForecastList; 
	}

	/*********************************************************/
	/*                                                       */ 
	/* CWorldWeatherApi.Connect()                            */ 
	/*                                                       */ 
	/*********************************************************/
	public void Connect( URL Url ) throws IOException
	{
		if( Url != null )
		{
			m_Connection = (HttpURLConnection)Url.openConnection();
			m_Connection.setConnectTimeout( HTTP_CONNECT_TIMEOUT_MS );
			m_Connection.setReadTimeout( HTTP_READ_TIMEOUT_MS );
			m_Connection.setRequestMethod( "GET" );
			m_Connection.setDoInput( true );
			
			// Start connection
			m_Connection.connect();
			m_InputStream = m_Connection.getInputStream();
		}
	}
	
	/*********************************************************/
	/*                                                       */ 
	/* CWorldWeatherApi.getString()                          */ 
	/*                                                       */ 
	/*********************************************************/
	public String getString() throws IOException
	{
		StringBuffer retBuffer = new StringBuffer();
		char[] Buffer = new char[ 2048 ];
		int BytesRead;
	
		InputStreamReader Reader = new InputStreamReader( m_InputStream, m_Charset );
		
		while( ( BytesRead = Reader.read( Buffer ) ) != -1)
		{
			retBuffer.append( Buffer, 0, BytesRead );
		}
	
		return retBuffer.toString();
	}
	
	/*********************************************************/
	/*                                                       */ 
	/* CWorldWeatherApi.getJSONObject()                      */ 
	/*                                                       */ 
	/*********************************************************/
	public JSONObject getJSONObject() throws JSONException, IOException
	{
		return new JSONObject( getString() );
	}
	
	/*********************************************************/
	/*                                                       */ 
	/* CWorldWeatherApi.Close()                              */ 
	/*                                                       */ 
	/*********************************************************/
	public void Close() throws IOException
	{
		if( m_InputStream != null ) m_InputStream.close();
		if( m_Connection != null ) m_Connection.disconnect();
	}
}
