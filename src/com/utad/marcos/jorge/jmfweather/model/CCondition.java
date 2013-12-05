
/**************************************************************/
/*                                                            */
/* CCondition.java                                            */
/* (c)2013 jmarcosf                                           */
/*                                                            */
/* Description: CCondition Class                              */
/*              JmfWeather Project                            */
/*                                                            */
/**************************************************************/
package com.utad.marcos.jorge.jmfweather.model;

import android.annotation.SuppressLint;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**************************************************************/
/*                                                            */ 
/*                                                            */ 
/* CCondition Class                                           */ 
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
		
		m_CloudCoverPercentage = jsonObject.getInt( "cloudcover" );
		SimpleDateFormat DateFormat = new SimpleDateFormat( "HH:mm" );
		m_ObservationTime = DateFormat.parse( jsonObject.getString( "observation_time" ) );
		m_Pressure = jsonObject.getInt( "pressure" );
		m_TemperatureCelsius = jsonObject.getInt( "temp_C" );
		m_Visibility = jsonObject.getInt( "visibility" );
		m_TemperatureFahrenheit = jsonObject.getInt( "temp_F" );
		m_WindSpeedMph = jsonObject.getInt( "windspeedMiles" );
		m_Precipitation = (float)jsonObject.getDouble( "precipMM" );
		m_WindDirectionDegrees = jsonObject.getInt( "winddirDegree" );
		m_WindDirectionCompass = jsonObject.getString( "winddir16Point" );
		JSONArray iconUrlArray = jsonObject.getJSONArray( "weatherIconUrl" );
		m_IconUrl = iconUrlArray.getJSONObject( 0 ).getString( "value" );
		m_Humidity = jsonObject.getInt( "humidity" );
		m_WindSpeedKmph = jsonObject.getInt( "windspeedKmph" );
		m_WeatherCode = jsonObject.getInt( "weatherCode" );
		JSONArray descriptionArray = jsonObject.getJSONArray( "weatherDesc" );
		m_WeatherDescription = descriptionArray.getJSONObject( 0 ).getString( "value" );
	}

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


