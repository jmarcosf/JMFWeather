/**************************************************************/
/*                                                            */
/* CForecastList.java                                         */
/* (c)2013 jmarcosf                                           */
/*                                                            */
/* Description: CForecastList Class                           */
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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.TreeMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**************************************************************/
/*                                                            */ 
/*                                                            */ 
/*                                                            */ 
/* CForecastList Class                                        */ 
/*                                                            */ 
/*                                                            */ 
/*                                                            */ 
/**************************************************************/
public class CForecastList
{
private	ArrayList< CForecast > 		m_ForecastList;
private	TreeMap< Long, CForecast> 	m_ForecastMap;

	/*********************************************************/
	/*                                                       */ 
	/*                                                       */ 
	/* Class Constructors                                    */ 
	/*                                                       */ 
	/*                                                       */ 
	/*********************************************************/
	/*                                                       */ 
	/* CForecastList.CForecastList()                         */ 
	/*                                                       */ 
	/*********************************************************/
	public CForecastList()
	{
		m_ForecastList = new ArrayList< CForecast >();
		m_ForecastMap = new TreeMap< Long, CForecast >();
	}
	
	/*********************************************************/
	/*                                                       */ 
	/* CForecastList.CForecastList()                         */ 
	/*                                                       */ 
	/*********************************************************/
	public CForecastList( JSONArray Entries ) throws JSONException, ParseException
	{
		this();
		
		for( int i = 0; i < Entries.length(); i++ )
		{
			JSONObject objForecast = Entries.getJSONObject( i );
			CForecast Forecast = new CForecast( objForecast );
			this.add( Forecast );
		}
	}

	/*********************************************************/
	/*                                                       */ 
	/*                                                       */ 
	/* Class Methods                                         */ 
	/*                                                       */ 
	/*                                                       */ 
	/*********************************************************/
	/*                                                       */ 
	/* CForecastList.getters()                               */ 
	/*                                                       */ 
	/*********************************************************/
	public CForecast                  get( long id )      { return m_ForecastMap.get( id ); }
	public ArrayList< CForecast >     getForecastList()   { return m_ForecastList; }
	public int                        getSize()           { return m_ForecastList.size(); }
	public CForecast                  getAt( int Index )  { return m_ForecastList.get( Index ); }
	
	/*********************************************************/
	/*                                                       */ 
	/* CForecastList.add()                                   */ 
	/*                                                       */ 
	/*********************************************************/
	public void add( CForecast Forecast )
	{
		add( new CForecast[] { Forecast } );
	}
	
	/*********************************************************/
	/*                                                       */ 
	/* CForecastList.add()                                   */ 
	/*                                                       */ 
	/*********************************************************/
	public void add( CForecast... ForecastArray )
	{
		this.m_ForecastList.addAll( Arrays.asList( ForecastArray ) );
		for( CForecast Forecast : ForecastArray )
		{
			this.m_ForecastMap.put( Forecast.getId(), Forecast );
		}
	}
	
	/*********************************************************/
	/*                                                       */ 
	/* CForecastList.getPosition()                           */ 
	/*                                                       */ 
	/*********************************************************/
	public int getPosition( long id )
	{
		CForecast Forecast = get( id );
		return m_ForecastList.indexOf( Forecast );
	}
}