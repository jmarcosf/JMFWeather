/**************************************************************/
/*                                                            */
/* CCitySearchAdapter.java                                    */
/* (c)2013 jmarcosf                                           */
/*                                                            */
/* Description: CCitySearchAdapter Class                      */
/*              JmfWeather Project                            */
/*              Práctica asignatura Android Fundamental       */ 
/*              U-Tad - Master Apps                           */ 
/*              www.u-tad.com                                 */ 
/*                                                            */ 
/*        Date: December 2013                                 */ 
/*                                                            */
/**************************************************************/
package com.utad.marcos.jorge.jmfweather;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.utad.marcos.jorge.jmfweather.model.CCity;
import com.utad.marcos.jorge.jmfweather.model.CCityList;

/**************************************************************/
/*                                                            */
/*                                                            */
/*                                                            */
/* CCitySearchAdapter Class                                   */
/*                                                            */
/*                                                            */
/*                                                            */
/**************************************************************/
public class CCitySearchAdapter extends BaseAdapter
{
private Activity    m_Context;
private CCityList   m_CityList;
	
     /*********************************************************/
     /*                                                       */ 
     /*                                                       */ 
     /* Class Constructors                                    */ 
     /*                                                       */ 
     /*                                                       */ 
     /*********************************************************/
	/*                                                       */ 
	/* CCitySearchAdapter.CCitySearchAdapter()               */ 
	/*                                                       */ 
	/*********************************************************/
	public CCitySearchAdapter( Activity context, CCityList CityList )
	{
		this.m_Context = context;
		this.m_CityList = CityList;
	}
	
     /*********************************************************/
     /*                                                       */ 
     /*                                                       */ 
     /* BaseAdapter Override Methods                          */ 
     /*                                                       */ 
     /*                                                       */ 
	/*********************************************************/
     /*                                                       */ 
     /* CCitySearchAdapter.getCount()                         */ 
     /*                                                       */ 
     /*********************************************************/
     @Override
     public int getCount()
     {
          return m_CityList.getSize();
     }

     /*********************************************************/
     /*                                                       */ 
     /* CCitySearchAdapter.getItem()                          */ 
     /*                                                       */ 
     /*********************************************************/
     @Override
     public Object getItem( int position )
     {
          return m_CityList.getAt( position );
     }

     /*********************************************************/
     /*                                                       */ 
     /* CCitySearchAdapter.getItemId()                        */ 
     /*                                                       */ 
     /*********************************************************/
     @Override
     public long getItemId( int position )
     {
          return m_CityList.getAt( position ).getId();
     }

     /*********************************************************/
     /*                                                       */ 
     /* CCitySearchAdapter.getView()                          */ 
     /*                                                       */ 
     /*********************************************************/
     @Override
     public View getView( int position, View ConvertView, ViewGroup Parent )
     {
          View ItemView = null;
          if( ConvertView == null )
          {
               LayoutInflater inflater = (LayoutInflater)m_Context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
               ItemView = inflater.inflate( R.layout.layout_city_search_item, null );
          }
          else ItemView = ConvertView;
          TextView CityName = (TextView)ItemView.findViewById( R.id.IDC_TXT_CITY_NAME );
          
          CCity City = m_CityList.getAt( position );
          if( City == null || City.getName() == null ) return null;
          
          // Concatenate Name, Region & Country whereas not null & Region not equal to Country
          String CityInfo = City.getName();
          if( City.getCountry() != null || City.getRegion() != null )
          {
               CityInfo += " ( ";
               if( City.getRegion() != null ) CityInfo += City.getRegion();
               if( City.getCountry() != null && ( City.getRegion() == null || City.getCountry() != City.getRegion() ) )
               {
                    if( City.getRegion() != null ) CityInfo += " - ";
                    CityInfo += City.getCountry();
               }
               CityInfo += " )";
          }
          
          CityName.setText( CityInfo );
          return ItemView;
     }
}