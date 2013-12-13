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
import android.support.v4.widget.CursorAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.utad.marcos.jorge.jmfweather.db.CWeatherDAO;
import com.utad.marcos.jorge.jmfweather.model.CCity;
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
public class CCityListAdapter extends CursorAdapter
{
private	Activity 			m_Context;
private	HashSet< Thread >	m_ThreadSet;
	
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
		this.m_ThreadSet = new HashSet< Thread >();
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
          CityTemp.setText( "" + City.getCondition().getTemperatureCelsius() + "ºC" );
     
          final ImageView CityIcon = (ImageView)ItemView.findViewById( R.id.IDP_ICO_CITY_ICON );
          final String IconUrl = ( City.getCondition() != null ) ? City.getCondition().getIconUrl() : null; 
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
