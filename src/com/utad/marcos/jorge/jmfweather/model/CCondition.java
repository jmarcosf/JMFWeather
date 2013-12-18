/**************************************************************/
/*                                                            */
/* CCondition.java                                            */
/* (c)2013 jmarcosf                                           */
/*                                                            */
/* Description: CCondition Class                              */
/*              JmfWeather Project                            */
/*              Práctica asignatura Android Fundamental       */ 
/*              U-Tad - Master Apps                           */ 
/*              www.u-tad.com                                 */ 
/*                                                            */ 
/*        Date: December 2013                                 */ 
/*                                                            */
/**************************************************************/
package com.utad.marcos.jorge.jmfweather.model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.database.Cursor;

import com.utad.marcos.jorge.jmfweather.db.CWeatherDBContract;

/**************************************************************/
/*                                                            */ 
/*                                                            */ 
/*                                                            */ 
/* CCondition Class                                           */ 
/*                                                            */ 
/*                                                            */ 
/*                                                            */ 
/**************************************************************/
public class CCondition
{
private long		m_Id;	
private long		m_CityId;	
private int		m_CloudCoverPercentage;
private Date		m_ObservationTime;
private int		m_Pressure;
private int		m_TemperatureCelsius;
private int		m_Visibility;
private int		m_TemperatureFahrenheit;
private int		m_WindSpeedMph;
private float		m_Precipitation;
private int		m_WindDirectionDegrees;
private String		m_WindDirectionCompass;
private String		m_IconUrl;
private int		m_Humidity;
private int		m_WindSpeedKmph;
private int		m_WeatherCode;
private String		m_WeatherDescription;

	/*********************************************************/
	/*                                                       */ 
	/*                                                       */ 
	/* Class Constructors                                    */ 
	/*                                                       */ 
	/*                                                       */ 
	/*********************************************************/
	/*                                                       */ 
	/* CCondition.CCondition()                               */ 
	/*                                                       */ 
	/*********************************************************/
	public CCondition( int CloudCoverPercentage, Date ObservationTime, int Pressure, int TemperatureCelsius, int Visibility,
	          int TemperatureFahrenheit, int WindSpeedMph, float Precipitation, int WindDirectionDegrees, String WindDirectionCompass,
	          String IconUrl, int Humidity, int WindSpeedKmph, int WeatherCode, String WeatherDescription )
	{
		this.m_Id = this.m_CityId = -1;
		this.m_CloudCoverPercentage = CloudCoverPercentage;
		this.m_ObservationTime = ObservationTime;
		this.m_Pressure = Pressure;
		this.m_TemperatureCelsius = TemperatureCelsius;
		this.m_Visibility = Visibility;
		this.m_TemperatureFahrenheit = TemperatureFahrenheit;
		this.m_WindSpeedMph = WindSpeedMph;
		this.m_Precipitation = Precipitation;
		this.m_WindDirectionDegrees = WindDirectionDegrees;
		this.m_WindDirectionCompass = WindDirectionCompass;
		this.m_IconUrl = IconUrl;
		this.m_Humidity = Humidity;
		this.m_WindSpeedKmph = WindSpeedKmph;
		this.m_WeatherCode = WeatherCode;
		this.m_WeatherDescription = WeatherDescription;
	}

	/*********************************************************/
	/*                                                       */ 
	/* CCondition.CCondition() JSON object constructor       */ 
	/*                                                       */ 
	/*********************************************************/
	@SuppressLint( "SimpleDateFormat" )
	public CCondition( JSONObject jsonObject ) throws JSONException, ParseException
	{
		this( 0, (Date)null, 0, 0, 0, 0, 0, 0, 0, null, null, 0, 0, 0, null );
		
		this.m_CloudCoverPercentage = jsonObject.optInt( "cloudcover" );
		SimpleDateFormat DateFormat = new SimpleDateFormat( "yyyy-MM-dd hh:mm aaa", Locale.US );
		String localObsDateTime = jsonObject.optString( "localObsDateTime" );
		if( localObsDateTime != null ) this.m_ObservationTime = DateFormat.parse( localObsDateTime );
		this.m_Pressure = jsonObject.optInt( "pressure" );
		this.m_TemperatureCelsius = jsonObject.optInt( "temp_C" );
		this.m_Visibility = jsonObject.optInt( "visibility" );
		this.m_TemperatureFahrenheit = jsonObject.optInt( "temp_F" );
		this.m_WindSpeedMph = jsonObject.optInt( "windspeedMiles" );
		this.m_Precipitation = (float)jsonObject.optDouble( "precipMM" );
		this.m_WindDirectionDegrees = jsonObject.optInt( "winddirDegree" );
		this.m_WindDirectionCompass = jsonObject.optString( "winddir16Point" );
		JSONArray iconUrlArray = jsonObject.optJSONArray( "weatherIconUrl" );
		if( iconUrlArray != null ) this.m_IconUrl = iconUrlArray.getJSONObject( 0 ).optString( "value" );
		this.m_Humidity = jsonObject.optInt( "humidity" );
		this.m_WindSpeedKmph = jsonObject.optInt( "windspeedKmph" );
		this.m_WeatherCode = jsonObject.optInt( "weatherCode" );
		JSONArray descriptionArray = jsonObject.optJSONArray( "weatherDesc" );
		if( descriptionArray != null ) this.m_WeatherDescription = descriptionArray.getJSONObject( 0 ).optString( "value" );
	}
	
	/*********************************************************/
	/*                                                       */ 
	/* CCondition.Condition() Cursor Constructor             */ 
	/*                                                       */ 
	/*********************************************************/
	public CCondition( Cursor cursor )
	{
		this( 0, (Date)null, 0, 0, 0, 0, 0, 0, 0, null, null, 0, 0, 0, null );
		
		this.m_Id = cursor.getLong( cursor.getColumnIndex( CWeatherDBContract.CConditionTable._ID ) );
		this.m_CityId = cursor.getLong( cursor.getColumnIndex( CWeatherDBContract.CConditionTable.COLUMN_NAME_CITY_ID ) );
		this.m_CloudCoverPercentage = cursor.getInt( cursor.getColumnIndex( CWeatherDBContract.CConditionTable.COLUMN_NAME_CLOUD_COVERAGE ) );
		if( !cursor.isNull( cursor.getColumnIndex( CWeatherDBContract.CConditionTable.COLUMN_NAME_OBSERVATION_TIME ) ) )
		{
			this.m_ObservationTime = new Date( cursor.getLong( cursor.getColumnIndex( CWeatherDBContract.CConditionTable.COLUMN_NAME_OBSERVATION_TIME ) ) );
		}
		this.m_Pressure = cursor.getInt( cursor.getColumnIndex( CWeatherDBContract.CConditionTable.COLUMN_NAME_PRESSURE ) );
		this.m_TemperatureCelsius = cursor.getInt( cursor.getColumnIndex( CWeatherDBContract.CConditionTable.COLUMN_NAME_TEMPERATURE_CELSIUS ) );
		this.m_Visibility = cursor.getInt( cursor.getColumnIndex( CWeatherDBContract.CConditionTable.COLUMN_NAME_VISIBILITY ) );
		this.m_TemperatureFahrenheit = cursor.getInt( cursor.getColumnIndex( CWeatherDBContract.CConditionTable.COLUMN_NAME_TEMPERATURE_FAHRENHEIT ) );
		this.m_WindSpeedMph = cursor.getInt( cursor.getColumnIndex( CWeatherDBContract.CConditionTable.COLUMN_NAME_WIND_SPEED_MPH ) );
		this.m_Precipitation = cursor.getFloat( cursor.getColumnIndex( CWeatherDBContract.CConditionTable.COLUMN_NAME_PRECIPITATION ) );
		this.m_WindDirectionDegrees = cursor.getInt( cursor.getColumnIndex( CWeatherDBContract.CConditionTable.COLUMN_NAME_WIND_DIRECTION_DEGREES ) );
		this.m_WindDirectionCompass = cursor.getString( cursor.getColumnIndex( CWeatherDBContract.CConditionTable.COLUMN_NAME_WIND_DIRECTION_COMPASS ) );
		this.m_IconUrl = cursor.getString( cursor.getColumnIndex( CWeatherDBContract.CConditionTable.COLUMN_NAME_ICON_URL ) );
		this.m_Humidity = cursor.getInt( cursor.getColumnIndex( CWeatherDBContract.CConditionTable.COLUMN_NAME_HUMIDITY ) );
		this.m_WindSpeedKmph = cursor.getInt( cursor.getColumnIndex( CWeatherDBContract.CConditionTable.COLUMN_NAME_WIND_SPEED_KMPH ) );
		this.m_WeatherCode = cursor.getInt( cursor.getColumnIndex( CWeatherDBContract.CConditionTable.COLUMN_NAME_WEATHER_CODE ) );
		this.m_WeatherDescription = cursor.getString( cursor.getColumnIndex( CWeatherDBContract.CConditionTable.COLUMN_NAME_WEATHER_DESCRIPTION ) );
	}	
	
	/*********************************************************/
	/*                                                       */ 
	/*                                                       */ 
	/* Class Methods                                         */ 
	/*                                                       */ 
	/*                                                       */ 
	/*********************************************************/
	/*                                                       */ 
	/* CCondition getters                                    */ 
	/*                                                       */ 
	/*********************************************************/
	public long 	getId()					{ return m_Id; }
	public long 	getCityId()				{ return m_CityId; }
	public int 	getCloudCoverPercentage() 	{ return m_CloudCoverPercentage; }
	public Date 	getObservationTime()		{ return m_ObservationTime; }
	public int 	getPressure()				{ return m_Pressure; }
	public int 	getTemperatureCelsius()		{ return m_TemperatureCelsius; }
	public int 	getVisibility()			{ return m_Visibility; }
	public int 	getTemperatureFahrenheit()	{ return m_TemperatureFahrenheit; }
	public int 	getWindSpeedMph()			{ return m_WindSpeedMph; }
	public float 	getPrecipitation()			{ return m_Precipitation; }
	public int	getWindDirectionDegrees()	{ return m_WindDirectionDegrees; }
	public String 	getWindDirectionCompass()	{ return m_WindDirectionCompass; }
	public String	getIconUrl()				{ return m_IconUrl; }
	public int	getHumidity()				{ return m_Humidity; }
	public int	getWindSpeedKmph()			{ return m_WindSpeedKmph; }
	public int 	getWeatherCode()			{ return m_WeatherCode; }
	public String 	getWeatherDescription()		{ return m_WeatherDescription; }

}


