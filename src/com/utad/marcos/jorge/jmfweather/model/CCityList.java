/**************************************************************/
/*                                                            */
/* CCityList.java                                             */
/* (c)2013 jmarcosf                                           */
/*                                                            */
/* Description: CCityList Class                               */
/*              JmfWeather Project                            */
/*              Pr�ctica asignatura Android Fundamental       */ 
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
/* CCityList Class                                            */ 
/*                                                            */ 
/*                                                            */ 
/*                                                            */ 
/**************************************************************/
public class CCityList
{
private	ArrayList< CCity > 		m_CityList;
private	TreeMap< Long, CCity> 	m_CityMap;

	/*********************************************************/
	/*                                                       */ 
	/*                                                       */ 
	/* Class Constructors                                    */ 
	/*                                                       */ 
	/*                                                       */ 
	/*********************************************************/
	/*                                                       */ 
	/* CCityList.CCityList()                                 */ 
	/*                                                       */ 
	/*********************************************************/
	public CCityList()
	{
		m_CityList = new ArrayList< CCity >();
		m_CityMap = new TreeMap< Long, CCity >();
	}
	
	/*********************************************************/
	/*                                                       */ 
	/* CCityList.CCityList()                                 */ 
	/*                                                       */ 
	/*********************************************************/
	public CCityList( JSONObject jsonObject ) throws JSONException, ParseException
	{
		this();
		
		JSONObject Search = jsonObject.getJSONObject( "search_api" );
		JSONArray Entries = Search.getJSONArray( "result" );
		
		for( int i = 0; i < Entries.length(); i++ )
		{
			JSONObject objCity = Entries.getJSONObject( i );
			CCity City = new CCity( (long)i + 1, objCity );
			this.add( City );
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
	/* CCityList.getters()                                   */ 
	/*                                                       */ 
	/*********************************************************/
	public CCity                 get( long id )      { return m_CityMap.get( id ); }
	public ArrayList< CCity >    getCityList()       { return m_CityList; }
	public int                   getSize()           { return m_CityList.size(); }
	public CCity                 getAt( int Index )  { return m_CityList.get( Index ); }
	
	/*********************************************************/
	/*                                                       */ 
	/* CCityList.add()                                       */ 
	/*                                                       */ 
	/*********************************************************/
	public void add( CCity City )
	{
		add( new CCity[] { City } );
	}
	
	/*********************************************************/
	/*                                                       */ 
	/* CCityList.add()                                       */ 
	/*                                                       */ 
	/*********************************************************/
	public void add( CCity... CityArray )
	{
		this.m_CityList.addAll( Arrays.asList( CityArray ) );
		for( CCity City : CityArray )
		{
			this.m_CityMap.put( City.getId(), City );
		}
	}
	
	/*********************************************************/
	/*                                                       */ 
	/* CCityList.getPosition()                               */ 
	/*                                                       */ 
	/*********************************************************/
	public int getPosition( long id )
	{
		CCity City = get( id );
		return m_CityList.indexOf( City );
	}
}
