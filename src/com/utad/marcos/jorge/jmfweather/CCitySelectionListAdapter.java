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
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.utad.marcos.jorge.jmfweather.model.CCityList;

/**************************************************************/
/*                                                            */
/*                                                            */
/*                                                            */
/* CCityListAdapter Class                                     */
/*                                                            */
/*                                                            */
/*                                                            */
/**************************************************************/
public class CCitySelectionListAdapter extends BaseAdapter
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
	/* CCityListAdapter.CCityListAdapter()                   */ 
	/*                                                       */ 
	/*********************************************************/
	public CCitySelectionListAdapter( Activity context, CCityList CityList )
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
     /* CCitySelectionListAdapter.getCount()                  */ 
     /*                                                       */ 
     /*********************************************************/
     @Override
     public int getCount()
     {
          return m_CityList.getCityList().size();
     }

     /*********************************************************/
     /*                                                       */ 
     /* CCitySelectionListAdapter.getItem()                   */ 
     /*                                                       */ 
     /*********************************************************/
     @Override
     public Object getItem( int position )
     {
          return m_CityList.getCityList().get( position );
     }

     /*********************************************************/
     /*                                                       */ 
     /* CCitySelectionListAdapter.getItemId()                 */ 
     /*                                                       */ 
     /*********************************************************/
     @Override
     public long getItemId( int position )
     {
          return m_CityList.getCityList().get( position ).getId();
     }

     /*********************************************************/
     /*                                                       */ 
     /* CCitySelectionListAdapter.getView()                   */ 
     /*                                                       */ 
     /*********************************************************/
     @Override
     public View getView( int position, View ConvertView, ViewGroup Parent )
     {
          View ItemView = null;
          if( ConvertView == null )
          {
               LayoutInflater inflater = (LayoutInflater)m_Context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
               ItemView = inflater.inflate( R.layout.layout_city_selection_list_item, null );
          }
          else ItemView = ConvertView;
          TextView CityName = (TextView)ItemView.findViewById( R.id.IDC_TXT_CITY_NAME );
          CityName.setText( m_CityList.getCityList().get( position ).getName() );
          return ItemView;
     }
}