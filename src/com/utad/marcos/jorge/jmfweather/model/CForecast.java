/**************************************************************/
/*                                                            */
/* CForecast.java                                             */
/* (c)2013 jmarcosf                                           */
/*                                                            */
/* Description: CForecast Class                               */
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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.utad.marcos.jorge.jmfweather.R;
import com.utad.marcos.jorge.jmfweather.db.CWeatherDBContract;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.widget.ImageView;

/**************************************************************/
/*                                                            */ 
/*                                                            */ 
/*                                                            */ 
/* CForecast Class                                            */ 
/*                                                            */ 
/*                                                            */ 
/*                                                            */ 
/**************************************************************/
public class CForecast
{
private long		m_Id;	
private long		m_CityId;	
private String		m_IconUrl;
private int		m_MinTemperatureCelsius;
private int		m_WindSpeedMph;
private int		m_WindSpeedKmph;
private String		m_WindDirection;
private int		m_MaxTemperatureCelsius;
private Date		m_ForecastDate;
private int		m_WeatherCode;
private int		m_MaxTemperatureFahrenheit;
private float		m_Precipitation;
private int		m_WindDirectionDegrees;
private String		m_WindDirectionCompass;
private String		m_WeatherDescription;
private int		m_MinTemperatureFahrenheit;

	/*********************************************************/
	/*                                                       */ 
	/*                                                       */ 
	/* Class Constructors                                    */ 
	/*                                                       */ 
	/*                                                       */ 
	/*********************************************************/
	/*                                                       */ 
	/* CForecast.CForecast()                                 */ 
	/*                                                       */ 
	/*********************************************************/
	public CForecast( String IconUrl, int MinTemperatureCelsius, int WindSpeedMph, int WindSpeedKmph, String WindDirection,
	          int MaxTemperatureCelsius, Date ForecastDate, int WeatherCode, int MaxTemperatureFahrenheit, float Precipitation,
	          int WindDirectionDegrees, String WindDirectionCompass, String WeatherDescription, int MinTemperatureFahrenheit )
	{
		this.m_Id = this.m_CityId = -1;
		this.m_IconUrl = IconUrl;
		this.m_MinTemperatureCelsius = MinTemperatureCelsius;
		this.m_WindSpeedMph = WindSpeedMph;
		this.m_WindSpeedKmph = WindSpeedKmph;
		this.m_WindDirection = WindDirection;
		this.m_MaxTemperatureCelsius = MaxTemperatureCelsius;
		this.m_ForecastDate = ForecastDate;
		this.m_WeatherCode = WeatherCode;
		this.m_MaxTemperatureFahrenheit = MaxTemperatureFahrenheit;
		this.m_Precipitation = Precipitation;
		this.m_WindDirectionDegrees = WindDirectionDegrees;
		this.m_WindDirectionCompass = WindDirectionCompass;
		this.m_WeatherDescription = WeatherDescription;
		this.m_MinTemperatureFahrenheit = MinTemperatureFahrenheit;
	}

	/*********************************************************/
	/*                                                       */ 
	/* CForecast.CForecast() JSON object constructor         */ 
	/*                                                       */ 
	/*********************************************************/
	@SuppressLint( "SimpleDateFormat" )
	public CForecast( JSONObject jsonObject ) throws JSONException, ParseException
	{
		this( null, 0, 0, 0, null, 0, (Date)null, 0, 0, 0, 0, null, null, 0 );
		
		JSONArray iconUrlArray = jsonObject.optJSONArray( "weatherIconUrl" );
		if( iconUrlArray != null ) this.m_IconUrl = iconUrlArray.getJSONObject( 0 ).optString( "value" );
		this.m_MinTemperatureCelsius = jsonObject.optInt( "tempMinC" );
		this.m_WindSpeedMph = jsonObject.optInt( "windspeedMiles" );
		this.m_WindSpeedKmph = jsonObject.optInt( "windspeedKmph" );
		this.m_WindDirection = jsonObject.optString( "winddirection" );
		this.m_MaxTemperatureCelsius = jsonObject.optInt( "tempMaxC" );
		SimpleDateFormat DateFormat = new SimpleDateFormat( "yyyy-MM-dd" );
          String strDate = jsonObject.optString( "date" );
		if( strDate != null ) this.m_ForecastDate = DateFormat.parse( strDate );
		this.m_WeatherCode = jsonObject.optInt( "weatherCode" );
		this.m_MaxTemperatureFahrenheit = jsonObject.optInt( "tempMaxF" );
		this.m_Precipitation = (float)jsonObject.optDouble( "precipMM" );
		this.m_WindDirectionDegrees = jsonObject.optInt( "winddirDegree" );
		this.m_WindDirectionCompass = jsonObject.optString( "winddir16Point" );
		JSONArray descriptionArray = jsonObject.optJSONArray( "weatherDesc" );
		if( descriptionArray != null ) this.m_WeatherDescription = descriptionArray.getJSONObject( 0 ).optString( "value" );
		this.m_MinTemperatureFahrenheit = jsonObject.optInt( "tempMinF" );
	}

	/*********************************************************/
     /*                                                       */ 
     /* CForecast.Forecast() Cursor Constructor               */ 
     /*                                                       */ 
     /*********************************************************/
     public CForecast( Cursor cursor )
     {
          this( null, 0, 0, 0, null, 0, (Date)null, 0, 0, 0, 0, null, null, 0 );
          
          this.m_Id = cursor.getLong( cursor.getColumnIndex( CWeatherDBContract.CForecastTable._ID ) );
          this.m_CityId = cursor.getLong( cursor.getColumnIndex( CWeatherDBContract.CForecastTable.COLUMN_NAME_CITY_ID ) );
          this.m_IconUrl = cursor.getString( cursor.getColumnIndex( CWeatherDBContract.CForecastTable.COLUMN_NAME_ICON_URL ) );
          this.m_MinTemperatureCelsius = cursor.getInt( cursor.getColumnIndex( CWeatherDBContract.CForecastTable.COLUMN_NAME_MIN_TEMPERATURE_CELSIUS ) );
          this.m_WindSpeedMph = cursor.getInt( cursor.getColumnIndex( CWeatherDBContract.CForecastTable.COLUMN_NAME_WIND_SPEED_MPH ) );
          this.m_WindSpeedKmph = cursor.getInt( cursor.getColumnIndex( CWeatherDBContract.CForecastTable.COLUMN_NAME_WIND_SPEED_KMPH ) );
          this.m_WindDirection = cursor.getString( cursor.getColumnIndex( CWeatherDBContract.CForecastTable.COLUMN_NAME_WIND_DIRECTION ) );
          this.m_MaxTemperatureCelsius = cursor.getInt( cursor.getColumnIndex( CWeatherDBContract.CForecastTable.COLUMN_NAME_MAX_TEMPERATURE_CELSIUS ) );
          if( !cursor.isNull( cursor.getColumnIndex( CWeatherDBContract.CForecastTable.COLUMN_NAME_FORECAST_DATE ) ) )
          {
               this.m_ForecastDate = new Date( cursor.getLong( cursor.getColumnIndex( CWeatherDBContract.CForecastTable.COLUMN_NAME_FORECAST_DATE ) ) );
          }
          this.m_WeatherCode = cursor.getInt( cursor.getColumnIndex( CWeatherDBContract.CForecastTable.COLUMN_NAME_WEATHER_CODE ) );
          this.m_MaxTemperatureFahrenheit = cursor.getInt( cursor.getColumnIndex( CWeatherDBContract.CForecastTable.COLUMN_NAME_MAX_TEMPERATURE_FAHRENHEIT ) );
          this.m_Precipitation = cursor.getInt( cursor.getColumnIndex( CWeatherDBContract.CForecastTable.COLUMN_NAME_PRECIPITATION ) );
          this.m_WindDirectionDegrees = cursor.getInt( cursor.getColumnIndex( CWeatherDBContract.CForecastTable.COLUMN_NAME_WIND_DIRECTION_DEGREES ) );
          this.m_WindDirectionCompass = cursor.getString( cursor.getColumnIndex( CWeatherDBContract.CForecastTable.COLUMN_NAME_WIND_DIRECTION_COMPASS ) );
          this.m_WeatherDescription = cursor.getString( cursor.getColumnIndex( CWeatherDBContract.CForecastTable.COLUMN_NAME_WEATHER_DESCRIPTION ) );
          this.m_MinTemperatureFahrenheit = cursor.getInt( cursor.getColumnIndex( CWeatherDBContract.CForecastTable.COLUMN_NAME_MIN_TEMPERATURE_FAHRENHEIT ) );
     }    
     
	/*********************************************************/
	/*                                                       */ 
	/*                                                       */ 
	/* Class Methods                                         */ 
	/*                                                       */ 
	/*                                                       */ 
	/*********************************************************/
	/*                                                       */ 
	/* CForecast getters                                     */ 
	/*                                                       */ 
	/*********************************************************/
	public long   getId()					{ return m_Id; }
	public long   getCityId()				{ return m_CityId; }
	public String getIconUrl()				{ return m_IconUrl; }
	public int    getMinTemperatureCelsius()	{ return m_MinTemperatureCelsius; }
	public int    getWindSpeedMph()			{ return m_WindSpeedMph; }
	public int    getWindSpeedKmph()			{ return m_WindSpeedKmph; }
	public String getWindDirection()			{ return m_WindDirection; }
	public int    getMaxTemperatureCelsius()	{ return m_MaxTemperatureCelsius; }
	public Date   getForecastDate()			{ return m_ForecastDate; }
	public int    getWeatherCode()			{ return m_WeatherCode; }
	public int    getMaxTemperatureFahrenheit()	{ return m_MaxTemperatureFahrenheit; }
	public float  getPrecipitation()			{ return m_Precipitation; }
	public int    getWindDirectionDegrees()		{ return m_WindDirectionDegrees; }
	public String getWindDirectionCompass()		{ return m_WindDirectionCompass; }
	public String getWeatherDescription()		{ return m_WeatherDescription; }
	public int    getMinTemperatureFahrenheit()	{ return m_MinTemperatureFahrenheit; }

     /*********************************************************/
     /*                                                       */ 
     /* CForecast.getWeatherCodeDescription()                 */ 
     /*                                                       */ 
     /*********************************************************/
     public String getWeatherCodeDescription( Context context )
     {
          String packageName = context.getPackageName();
          int ResourceId = context.getResources().getIdentifier( "IDS_WEATHER_DESC_" + getWeatherCode(), "string", packageName );
          return context.getString( ( ResourceId == 0 ) ? R.string.IDS_UNKNOWN : ResourceId );
     }
     
     /*********************************************************/
     /*                                                       */ 
     /* CForecast.SetViewIcon()                               */ 
     /*                                                       */ 
     /*********************************************************/
     public void SetViewIcon( Context context, ImageView view )
     {
          if( context != null && view != null )
          {
               File CacheDirectory = context.getCacheDir();
               Uri uri = Uri.parse( getIconUrl() );
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




