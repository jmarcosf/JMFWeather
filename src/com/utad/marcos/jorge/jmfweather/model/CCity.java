/**************************************************************/
/*                                                            */
/* CCity.java                                                 */
/* (c)2013 jmarcosf                                           */
/*                                                            */
/* Description: CCity Class                                   */
/*              JmfWeather Project                            */
/*                                                            */
/**************************************************************/
package com.utad.marcos.jorge.jmfweather.model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**************************************************************/
/*                                                            */ 
/*                                                            */ 
/* CCity Class                                                */ 
/*                                                            */ 
/*                                                            */ 
/**************************************************************/
public class CCity
{
private long		m_Id;	
private String		m_Name;
private String		m_Country;
private String		m_Latitude;
private String		m_Longitude;
private long		m_Population;
private String		m_Region;
private String		m_WeatherUrl;
private CCondition	m_CurrentCondition;

	/*********************************************************/
	/*                                                       */ 
	/* CCity.CCity()                                         */ 
	/*                                                       */ 
	/*********************************************************/
	public CCity( long Id, String Name, String Country, String Latitude, String Longitude, long Population, String Region, String WeatherUrl )
	{
		this.m_Id = Id;
		this.m_Name = Name;
		this.m_Country = Country;
		this.m_Latitude = Latitude;
		this.m_Longitude = Longitude;
		this.m_Population = Population;
		this.m_Region = Region;
		this.m_WeatherUrl = WeatherUrl;
		this.m_CurrentCondition = null;
	}
	
	/*********************************************************/
	/*                                                       */ 
	/* CCity.CCity() JSON Object constructor                 */ 
	/*                                                       */ 
	/*********************************************************/
	public CCity( JSONObject jsonObject ) throws JSONException
	{
		this( -1, null, null, null, null,0,  null, null );

		JSONArray areaNameArray = jsonObject.getJSONArray( "areaName" );
		m_Name = areaNameArray.getJSONObject( 0 ).getString( "value" );

		JSONArray countryArray = jsonObject.getJSONArray( "country" );
		m_Country = countryArray.getJSONObject( 0 ).getString( "value" );
		
		m_Latitude = jsonObject.getString( "latitude" );
		m_Longitude = jsonObject.getString( "longitude" );
		m_Population = jsonObject.getLong( "population" );

		JSONArray regionArray = jsonObject.getJSONArray( "region" );
		m_Region = regionArray.getJSONObject( 0 ).getString( "value" );
		
		JSONArray weatherUrlArray = jsonObject.getJSONArray( "weatherUrl" );
		m_WeatherUrl = weatherUrlArray.getJSONObject( 0 ).getString( "value" );
	}
	
	/*********************************************************/
	/*                                                       */ 
	/* CCity getters                                         */ 
	/*                                                       */ 
	/*********************************************************/
	public long 		getId() 			{ return m_Id; }
	public String 		getName() 		{ return m_Name; }
	public String 		getCountry()		{ return m_Country; }
	public String 		getLatitude()		{ return m_Latitude; }
	public String 		getLongitude()		{ return m_Longitude; }
	public long 		getPopulation()	{ return m_Population; }
	public String		getRegion()		{ return m_Region; }
	public String 		getWeatherUrl()	{ return m_WeatherUrl; }
	public CCondition 	getCondition()		{ return m_CurrentCondition; }

	/*********************************************************/
	/*                                                       */ 
	/* CCity setters                                         */ 
	/*                                                       */ 
	/*********************************************************/
	public void setCurrentCondition( CCondition condition ) { m_CurrentCondition = condition; }
}
