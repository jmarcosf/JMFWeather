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
import java.io.IOException;
import java.net.URL;
import java.util.HashSet;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.utad.marcos.jorge.jmfweather.model.CCity;
import com.utad.marcos.jorge.jmfweather.model.CCityList;
import com.utad.marcos.jorge.jmfweather.utility.CWorldWeatherApi;

/**************************************************************/
/*                                                            */
/*                                                            */
/*                                                            */
/* CCityListAdapter Class                                     */
/*                                                            */
/*                                                            */
/*                                                            */
/**************************************************************/
public class CCityListAdapter extends BaseAdapter
{
private	Activity 			m_Context;
private	HashSet< Thread >	m_ThreadSet;
private   CCityList           m_CityList;
	
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
	public CCityListAdapter( Activity context, CCityList CityList )
	{
		this.m_Context = context;
		this.m_CityList = CityList;
		this.m_ThreadSet = new HashSet< Thread >();
	}

     /*********************************************************/
     /*                                                       */ 
     /*                                                       */ 
     /* BaseAdapter Override Methods                          */ 
     /*                                                       */ 
     /*                                                       */ 
     /*********************************************************/
     /*                                                       */ 
     /* CCityListAdapter.getCount()                           */ 
     /*                                                       */ 
     /*********************************************************/
     @Override
     public int getCount()
     {
          return m_CityList.getCityList().size();
     }

     /*********************************************************/
     /*                                                       */ 
     /* CCityListAdapter.getItem()                            */ 
     /*                                                       */ 
     /*********************************************************/
     @Override
     public Object getItem( int index )
     {
          return m_CityList.getCityList().get( index );
     }

     /*********************************************************/
     /*                                                       */ 
     /* CCityListAdapter.getItemId()                          */ 
     /*                                                       */ 
     /*********************************************************/
     @Override
     public long getItemId( int index )
     {
          return m_CityList.getCityList().get( index ).getId();
     }

     /*********************************************************/
     /*                                                       */ 
     /* CCityListAdapter.getView()                            */ 
     /*                                                       */ 
     /*********************************************************/
     @Override
     public View getView( int position, View convertView, ViewGroup parent )
     {
     	View ItemLayout = null;
     	if( convertView == null )
     	{
               LayoutInflater inflater = (LayoutInflater)m_Context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
     	     ItemLayout = inflater.inflate( R.layout.layout_city_list_item, null);
     	}
     	else ItemLayout = convertView;

		TextView CityName = (TextView)ItemLayout.findViewById( R.id.IDC_TXT_CITY_NAME );
		CityName.setText( m_CityList.getCityList().get( position ).getName() );
	
		final ImageView CityIcon = (ImageView)ItemLayout.findViewById( R.id.IDP_ICO_CITY_ICON );
          CityIcon.setImageResource( R.drawable.app_main_icon );
          
		final String IconUrl = ( m_CityList.getCityList().get( position ).getCondition() != null ) ? m_CityList.getCityList().get( position ).getCondition().getIconUrl() : null; 
		if( IconUrl != null )
		{
               Thread imageThread = new Thread()
               {
                    @Override
                    public void run()
                    {
                         CWorldWeatherApi Request = new CWorldWeatherApi();
                         try
                         {
                              Request.Connect( new URL( IconUrl ) );
                              final Bitmap image = Request.getImage();
                              m_Context.runOnUiThread( new Runnable()
                              {
                                   @Override
                                   public void run()
                                   {
                                        CityIcon.setImageBitmap( image );
                                   }
                              } );
                         }
                         catch( IOException e )
                         {
                              m_Context.runOnUiThread( new Runnable()
                              {
                                   @Override
                                   public void run()
                                   {
                                        CityIcon.setImageResource( R.drawable.app_main_icon );
                                   }
                              } );
                         }
                         catch( InterruptedException exception )
                         {
                              Log.d( CCityListAdapter.class.getSimpleName(), "Image Loading interrupted" );
                         }
                         finally
                         {
                              try
                              {
                                   Request.Close();
                              }
                              catch( IOException exception )
                              {
                                   exception.printStackTrace();
                              }
                         }
                    }
               };
               m_ThreadSet.add( imageThread );
               imageThread.start();
		}
		else CityIcon.setImageResource( R.drawable.app_main_icon );
		
          return ItemLayout;
     }
     
     /*********************************************************/
     /*                                                       */ 
     /*                                                       */ 
     /* Class Methods                                         */ 
     /*                                                       */ 
     /*                                                       */ 
     /*********************************************************/
     /*                                                       */
     /* CCityListAdapter.StopLoadingImages()                  */
     /*                                                       */
     /*********************************************************/
     public void StopLoadingImages()
     {
     	HashSet< Thread > tmpSet = new HashSet< Thread >();
     	tmpSet.addAll( m_ThreadSet );
     	for( Thread thread : tmpSet )
     	{
     		Log.d( CCityListAdapter.class.getSimpleName(), "Interrupt called!" );
     		thread.interrupt();
     	}
     	
     	m_ThreadSet = new HashSet< Thread >();
     }


}
