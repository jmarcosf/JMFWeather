/*************************************************************/
/*                                                           */
/* CCityDetailsFragment.java                                 */
/* (c)2013 jmarcosf                                          */
/*                                                           */
/* Description: CCityDetailsFragment Class                   */
/*              JmfWeather Project                           */
/*                                                           */
/*************************************************************/
package com.utad.marcos.jorge.jmfweather;

import java.io.IOException;
import java.net.URL;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.utad.marcos.jorge.jmfweather.db.CWeatherDAO;
import com.utad.marcos.jorge.jmfweather.model.CCity;
import com.utad.marcos.jorge.jmfweather.utility.CWorldWeatherApi;

/*************************************************************/
/*                                                           */
/*                                                           */
/*                                                           */
/* CCityDetailsFragment Class                                */ 
/*                                                           */
/*                                                           */
/*                                                           */
/*************************************************************/
public class CCityDetailsFragment extends Fragment
{
public static final String IDS_CITY_ID_PARAM = "CityIdParam";

     /*********************************************************/
     /*                                                       */ 
     /*                                                       */ 
     /* Fragment Override Methods                             */ 
     /*                                                       */ 
     /*                                                       */ 
     /*********************************************************/
	/*                                                       */ 
	/* CCityDetailsFragment.onCreateView()                   */ 
	/*                                                       */ 
	/*********************************************************/
	@Override
	public View onCreateView( LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState )
	{
          getActivity().supportInvalidateOptionsMenu();
          
		View view = inflater.inflate( R.layout.layout_city_details_fragment, container, false );
		long CityId = getArguments().getLong( IDS_CITY_ID_PARAM );
		
		CWeatherDAO WeatherDAO = new CWeatherDAO( getActivity() );
		CCity City = WeatherDAO.SelectCity( CityId );
		WeatherDAO.SetCityCondition( City );
		WeatherDAO.SetCityForecast( City );
		WeatherDAO.Close();

		TextView CityName = (TextView)view.findViewById( R.id.IDC_TXT_CITY_NAME );
		TextView CityCountry = (TextView)view.findViewById( R.id.IDC_TXT_CITY_COUNTRY );
          TextView CityGeoPosition = (TextView)view.findViewById( R.id.IDC_TXT_CITY_GEOPISITION );
          TextView CityTemp = (TextView)view.findViewById( R.id.IDC_TXT_CITY_TEMPERATURE );
          TextView CityPressure = (TextView)view.findViewById( R.id.IDC_TXT_CITY_PRESSURE );
          TextView CityHumitidy = (TextView)view.findViewById( R.id.IDC_TXT_CITY_HUMIDITY );
	
		CityName.setText( City.getName() );
		CityCountry.setText( City.getCountry() );
          CityGeoPosition.setText( City.getLatitude() + " : " + City.getLongitude() );
		CityTemp.setText( "" + City.getCondition().getTemperatureCelsius() + "ºC" );
          CityPressure.setText( "Pressure: " + City.getCondition().getPressure() );
          CityHumitidy.setText( "Humidity: " + City.getCondition().getHumidity() );
          
		final ImageView CityIcon = (ImageView)view.findViewById( R.id.IDP_ICO_CITY_ICON );
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
		                    getActivity().runOnUiThread( new Runnable()
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
		                    getActivity().runOnUiThread( new Runnable()
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
		     imageThread.start();
		}
		else CityIcon.setImageResource( R.drawable.app_main_icon );
		
		return view;
	}
}



