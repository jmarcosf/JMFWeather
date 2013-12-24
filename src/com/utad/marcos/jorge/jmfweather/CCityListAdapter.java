/**************************************************************/
/*                                                            */
/* CCityListAdapter.java                                      */
/* (c)2013 jmarcosf                                           */
/*                                                            */
/* Description: CCityListAdapter Class                        */
/*              JmfWeather Project                            */
/*              Práctica asignatura Android Fundamental       */ 
/*              U-Tad - Master Apps                           */ 
/*              www.u-tad.com                                 */ 
/*                                                            */ 
/*        Date: December 2013                                 */ 
/*                                                            */
/**************************************************************/
package com.utad.marcos.jorge.jmfweather;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.database.Cursor;
import android.support.v4.widget.CursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.utad.marcos.jorge.jmfweather.db.CWeatherDAO;
import com.utad.marcos.jorge.jmfweather.model.CCity;

/**************************************************************/
/*                                                            */
/*                                                            */
/*                                                            */
/* CCityListAdapter Class                                     */
/*                                                            */
/*                                                            */
/*                                                            */
/**************************************************************/
public class CCityListAdapter extends CursorAdapter
{
private	Activity       m_Context;
private   CWeatherDAO    m_WeatherDAO = null;
private   boolean        m_bTablet = false;
	
     /*********************************************************/
     /*                                                       */ 
     /*                                                       */ 
     /* Class Constructors                                    */ 
     /*                                                       */ 
     /*                                                       */ 
     /*********************************************************/
	/*                                                       */ 
	/* CCityListAdapter.CCityListAdapter()                   */ 
	/*                                                       */ 
	/*********************************************************/
	public CCityListAdapter( Activity context, CWeatherDAO WeatherDAO, Cursor cityCursor )
	{
	     super( context, cityCursor, false );
		this.m_Context = context;
		this.m_WeatherDAO = WeatherDAO;
          this.m_bTablet = ( m_Context.getResources().getBoolean( R.bool.g_bTablet ) && CApp.IsDivideScreenOnTabletsEnabled() && CApp.getOrientation() != Configuration.ORIENTATION_PORTRAIT );
	}

     /*********************************************************/
     /*                                                       */ 
     /*                                                       */ 
     /* CursorAdapter Override Methods                        */ 
     /*                                                       */ 
     /*                                                       */ 
     /*********************************************************/
     /*                                                       */ 
     /* CCityListAdapter.newView()                            */ 
     /*                                                       */ 
     /*********************************************************/
     @Override
     public View newView( Context context, Cursor cityCursor, ViewGroup parent )
     {
          View ItemView = null;
          LayoutInflater inflater = (LayoutInflater)m_Context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
          ItemView = inflater.inflate( m_bTablet ? R.layout.layout_city_list_item_tablet : R.layout.layout_city_list_item, null );
          return ItemView;
     }
     
     /*********************************************************/
     /*                                                       */ 
     /* CCityListAdapter.bindView()                           */ 
     /*                                                       */ 
     /*********************************************************/
     @SuppressLint( "SimpleDateFormat" )
     @Override
     public void bindView( View ItemView, Context context, Cursor cityCursor )
     {
          CCity City = new CCity( cityCursor );
          ItemView.setId( (int)City.getId() );
          ItemView.setTag( City.getName() );

          m_WeatherDAO.SetCityCondition( City );
          if( City.getCondition() == null ) return;
          
          TextView CityName = (TextView)ItemView.findViewById( R.id.IDC_TXT_CITY_NAME );
          CityName.setText( City.getName() );
     
          if( !m_bTablet )
          {
               TextView CityCountry = (TextView)ItemView.findViewById( R.id.IDC_TXT_CITY_COUNTRY );
               CityCountry.setText( "" + City.getCountry() );
          
               TextView CityTemp = (TextView)ItemView.findViewById( R.id.IDC_TXT_CITY_TEMPERATURE );
               if( CApp.getCelsius() ) CityTemp.setText( "" + City.getCondition().getTemperatureCelsius() + "ºC" );
               else CityTemp.setText( "" + City.getCondition().getTemperatureFahrenheit() + "ºF" );
               
               TextView CityUpdate = (TextView)ItemView.findViewById( R.id.IDC_TXT_CITY_WEATHER_LAST_UPDATE );
               Date upDate = City.getCondition().getObservationTime();
               SimpleDateFormat DateFormat = new SimpleDateFormat( "MMM d, kk:mm" );
               String LastUpdate = ( upDate == null ) ? "null date" : DateFormat.format( upDate );
               CityUpdate.setText( LastUpdate );
          }
          
          ImageView CityIcon = (ImageView)ItemView.findViewById( R.id.IDP_ICO_CITY_ICON );
          City.SetViewIcon( context, CityIcon );
     }
}
