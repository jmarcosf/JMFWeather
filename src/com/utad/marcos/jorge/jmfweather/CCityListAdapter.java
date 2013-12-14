/**************************************************************/
/*                                                            */
/* CCityListAdapter.java                                      */
/* (c)2013 jmarcosf                                           */
/*                                                            */
/* Description: CCityListAdapter Class                        */
/*              JmfWeather Project                            */
/*                                                            */
/**************************************************************/
package com.utad.marcos.jorge.jmfweather;
import android.app.Activity;
import android.content.Context;
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
private	Activity 			                         m_Context;
	
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
	public CCityListAdapter( Activity context, Cursor cityCursor )
	{
	     super( context, cityCursor, false );
		this.m_Context = context;
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
          ItemView = inflater.inflate( R.layout.layout_city_list_item, null );
          return ItemView;
     }
     
     /*********************************************************/
     /*                                                       */ 
     /* CCityListAdapter.bindView()                           */ 
     /*                                                       */ 
     /*********************************************************/
     @Override
     public void bindView( View ItemView, Context context, Cursor cityCursor )
     {
          CCity City = new CCity( cityCursor );
          ItemView.setTag( Long.valueOf( City.getId() ) );

          TextView CityName = (TextView)ItemView.findViewById( R.id.IDC_TXT_CITY_NAME );
          CityName.setText( City.getName() );
     
          TextView CityCountry = (TextView)ItemView.findViewById( R.id.IDC_TXT_CITY_COUNTRY );
          CityCountry.setText( "" + City.getCountry() );
     
          CWeatherDAO WeatherDAO = new CWeatherDAO( context );
          WeatherDAO.SetCityCondition( City );
          WeatherDAO.Close();
          
          if( City.getCondition() == null ) return;
          
          TextView CityTemp = (TextView)ItemView.findViewById( R.id.IDC_TXT_CITY_TEMPERATURE );
          CityTemp.setText( "" + City.getCondition().getTemperatureCelsius() + "�C" );
          
          ImageView CityIcon = (ImageView)ItemView.findViewById( R.id.IDP_ICO_CITY_ICON );
          City.SetViewIcon( context, CityIcon );
     }
}