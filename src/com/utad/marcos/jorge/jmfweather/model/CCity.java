/**************************************************************/
/*                                                            */
/* CCity.java                                                 */
/* (c)2013 jmarcosf                                           */
/*                                                            */
/* Description: CCity Class                                   */
/*              JmfWeather Project                            */
/*              Práctica asignatura Android Fundamental       */ 
/*              U-Tad - Master Apps                           */ 
/*              www.u-tad.com                                 */ 
/*                                                            */ 
/*        Date: December 2013                                 */ 
/*                                                            */
/**************************************************************/
package com.utad.marcos.jorge.jmfweather.model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.widget.ImageView;

import com.utad.marcos.jorge.jmfweather.R;
import com.utad.marcos.jorge.jmfweather.db.CWeatherDBContract;

/**************************************************************/
/*                                                            */ 
/*                                                            */ 
/*                                                            */ 
/* CCity Class                                                */ 
/*                                                            */ 
/*                                                            */ 
/*                                                            */ 
/**************************************************************/
public class CCity
{
private long   		m_Id;	
private String 		m_Name;
private String 		m_Country;
private String 		m_Latitude;
private String	          m_Longitude;
private long	       	m_Population;
private String 		m_Region;
private String		     m_WeatherUrl;
private CCondition	     m_CurrentCondition;
private CForecastList    m_ForecastList;

	/*********************************************************/
	/*                                                       */ 
	/*                                                       */ 
	/* Class Constructors                                    */ 
	/*                                                       */ 
	/*                                                       */ 
	/*********************************************************/
	/*                                                       */ 
	/* CCity.CCity()                                         */ 
	/*                                                       */ 
	/*********************************************************/
	public CCity( long Id, String Name, String Country, String Latitude, String Longitude, long Population, String Region, String IconUrl )
	{
		this.m_Id = Id;
		this.m_Name = Name;
		this.m_Country = Country;
		this.m_Latitude = Latitude;
		this.m_Longitude = Longitude;
		this.m_Population = Population;
		this.m_Region = Region;
		this.m_WeatherUrl = IconUrl;
		this.m_CurrentCondition = null;
		this.m_ForecastList = null;
	}
	
	/*********************************************************/
	/*                                                       */ 
	/* CCity.CCity()                                         */ 
	/*                                                       */ 
	/*********************************************************/
	public CCity( long Id, JSONObject jsonObject ) throws JSONException
	{
		this( Id, null, null, null, null, 0, null, null );

		JSONArray areaNameArray = jsonObject.optJSONArray( "areaName" );
		if( areaNameArray != null ) this.m_Name = areaNameArray.getJSONObject( 0 ).optString( "value" );

		JSONArray countryArray = jsonObject.optJSONArray( "country" );
		if( countryArray != null ) this.m_Country = countryArray.getJSONObject( 0 ).optString( "value" );
		
		this.m_Latitude = jsonObject.optString( "latitude" );
		this.m_Longitude = jsonObject.optString( "longitude" );
		this.m_Population = jsonObject.optLong( "population" );

		JSONArray regionArray = jsonObject.optJSONArray( "region" );
		if( regionArray != null ) this.m_Region = regionArray.getJSONObject( 0 ).optString( "value" );
		
		JSONArray weatherUrlArray = jsonObject.optJSONArray( "weatherUrl" );
		if( weatherUrlArray != null ) this.m_WeatherUrl = weatherUrlArray.getJSONObject( 0 ).optString( "value" );
	}

	/*********************************************************/
	/*                                                       */ 
	/* CCity.CCity() Cursor Constructor                      */ 
	/*                                                       */ 
	/*********************************************************/
	public CCity( Cursor cursor )
	{
		this( -1, null, null, null, null, 0, null, null );

		this.m_Id = cursor.getLong( cursor.getColumnIndex( CWeatherDBContract.CCityTable._ID ) );
		this.m_Name = cursor.getString( cursor.getColumnIndex( CWeatherDBContract.CCityTable.COLUMN_NAME_NAME ) );
		this.m_Country = cursor.getString( cursor.getColumnIndex( CWeatherDBContract.CCityTable.COLUMN_NAME_COUNTRY ) );
		this.m_Latitude = cursor.getString( cursor.getColumnIndex( CWeatherDBContract.CCityTable.COLUMN_NAME_LATITUDE ) );
		this.m_Longitude = cursor.getString( cursor.getColumnIndex( CWeatherDBContract.CCityTable.COLUMN_NAME_LONGITUDE ) );
		this.m_Population = cursor.getLong( cursor.getColumnIndex( CWeatherDBContract.CCityTable.COLUMN_NAME_POPULATION ) );
		this.m_Region = cursor.getString( cursor.getColumnIndex( CWeatherDBContract.CCityTable.COLUMN_NAME_REGION ) );
		this.m_WeatherUrl = cursor.getString( cursor.getColumnIndex( CWeatherDBContract.CCityTable.COLUMN_NAME_URL ) );
	}	
	
	/*********************************************************/
	/*                                                       */ 
	/*                                                       */ 
	/* Class Methods                                         */ 
	/*                                                       */ 
	/*                                                       */ 
	/*********************************************************/
	/*                                                       */ 
	/* CCity getters                                         */ 
	/*                                                       */ 
	/*********************************************************/
	public long             getId()             { return m_Id; }
	public String 		    getName()           { return m_Name; }
	public String           getCountry()        { return m_Country; }
	public String           getLatitude()       { return m_Latitude; }
	public String           getLongitude()      { return m_Longitude; }
	public long             getPopulation()     { return m_Population; }
	public String		    getRegion()         { return m_Region; }
	public String           getWeatherUrl()     { return m_WeatherUrl; }
	public CCondition  	    getCondition()      { return m_CurrentCondition; }
	public CForecastList    getForecastList()   { return m_ForecastList; }

	/*********************************************************/
	/*                                                       */ 
	/* CCity setters                                         */ 
	/*                                                       */ 
	/*********************************************************/
     public void setId( long Id )                            { m_Id = Id; }
	public void setCurrentCondition( CCondition Condition ) { m_CurrentCondition = Condition; }
     public void setForecastList( CForecastList ForecastList ) { m_ForecastList = ForecastList; }
     
     /*********************************************************/
     /*                                                       */ 
     /* CCity.SetViewIcon()                                   */ 
     /*                                                       */ 
     /*********************************************************/
     public void SetViewIcon( Context context, ImageView view )
     {
          if( context != null && view != null && getCondition() != null )
          {
               File CacheDirectory = context.getCacheDir();
               Uri uri = Uri.parse( getCondition().getIconUrl() );
               String ImagePath = uri.getLastPathSegment();
               File ImageFile = new File( CacheDirectory, ImagePath );
               try
               {
                    Bitmap IconBitmap = BitmapFactory.decodeStream( new FileInputStream( ImageFile ) );
                    view.setImageBitmap( IconBitmap );
               }
               catch( FileNotFoundException exception )
               {
                    view.setImageResource( R.drawable.icon_main );
               }
          }
     }
}
