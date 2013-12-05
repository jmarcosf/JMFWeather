
/**************************************************************/
/*                                                            */
/* CForecast.java                                             */
/* (c)2013 jmarcosf                                           */
/*                                                            */
/* Description: CForecast Class                               */
/*              JmfWeather Project                            */
/*                                                            */
/**************************************************************/
package com.utad.marcos.jorge.jmfweather.model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;

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
		
		JSONArray iconUrlArray = jsonObject.getJSONArray( "weatherIconUrl" );
		m_IconUrl = iconUrlArray.getJSONObject( 0 ).getString( "value" );
		m_MinTemperatureCelsius = jsonObject.getInt( "tempMin_C" );
		m_WindSpeedMph = jsonObject.getInt( "windspeedMiles" );
		m_WindSpeedKmph = jsonObject.getInt( "windspeedKmph" );
		m_WindDirection = jsonObject.getString( "winddirection" );
		m_MaxTemperatureCelsius = jsonObject.getInt( "tempMax_C" );
		SimpleDateFormat DateFormat = new SimpleDateFormat( "yyyy-MM-dd" );
		m_ForecastDate = DateFormat.parse( jsonObject.getString( "date" ) );
		m_WeatherCode = jsonObject.getInt( "weatherCode" );
		m_MaxTemperatureFahrenheit = jsonObject.getInt( "tempMax_F" );
		m_Precipitation = (float)jsonObject.getDouble( "precipMM" );
		m_WindDirectionDegrees = jsonObject.getInt( "winddirDegree" );
		m_WindDirectionCompass = jsonObject.getString( "winddir16Point" );
		JSONArray descriptionArray = jsonObject.getJSONArray( "weatherDesc" );
		m_WeatherDescription = descriptionArray.getJSONObject( 0 ).getString( "value" );
		m_MinTemperatureFahrenheit = jsonObject.getInt( "tempMin_F" );
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
	public long getId()						{ return m_Id; }
	public long getCityId()					{ return m_CityId; }
	public String getIconUrl()				{ return m_IconUrl; }
	public int getMinTemperatureCelsius()		{ return m_MinTemperatureCelsius; }
	public int getWindSpeedMph()				{ return m_WindSpeedMph; }
	public int getWindSpeedKmph()				{ return m_WindSpeedKmph; }
	public String getWindDirection()			{ return m_WindDirection; }
	public int getMaxTemperatureCelsius()		{ return m_MaxTemperatureCelsius; }
	public Date getForecastDate()				{ return m_ForecastDate; }
	public int getWeatherCode()				{ return m_WeatherCode; }
	public int getMaxTemperatureFahrenheit()	{ return m_MaxTemperatureFahrenheit; }
	public float getPrecipitation()			{ return m_Precipitation; }
	public int getWindDirectionDegrees()		{ return m_WindDirectionDegrees; }
	public String getWindDirectionCompass()		{ return m_WindDirectionCompass; }
	public String getWeatherDescription()		{ return m_WeatherDescription; }
	public int getMinTemperatureFahrenheit()	{ return m_MinTemperatureFahrenheit; }


}




