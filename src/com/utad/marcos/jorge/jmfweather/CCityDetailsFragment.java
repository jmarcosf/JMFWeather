/**************************************************************/
/*                                                            */
/* CCityDetailsFragment.java                                  */
/* (c)2013 jmarcosf                                           */
/*                                                            */
/* Description: CCityDetailsFragment Class                    */
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
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.utad.marcos.jorge.jmfweather.db.CWeatherDAO;
import com.utad.marcos.jorge.jmfweather.model.CCity;

/**************************************************************/
/*                                                            */
/*                                                            */
/*                                                            */
/* CCityDetailsFragment Class                                 */ 
/*                                                            */
/*                                                            */
/*                                                            */
/**************************************************************/
public class CCityDetailsFragment extends Fragment
{
public static final String IDS_CITY_ID_PARAM      = "CityIdParam";

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
	@SuppressLint( "SimpleDateFormat" )
     @Override
	public View onCreateView( LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState )
	{
          getActivity().supportInvalidateOptionsMenu();
          
          int ResourceId = ( getActivity().getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT )
                           ? R.layout.layout_city_details_fragment_vert
                           : R.layout.layout_city_details_fragment_horz;
		View view = inflater.inflate( ResourceId, container, false );
		long CityId = getArguments().getLong( IDS_CITY_ID_PARAM );
		
		CWeatherDAO WeatherDAO = new CWeatherDAO( getActivity() );
		CCity City = WeatherDAO.SelectCity( CityId );
		WeatherDAO.SetCityCondition( City );
		WeatherDAO.SetCityForecast( City );
		WeatherDAO.Close();

		TextView CityName = (TextView)view.findViewById( R.id.IDC_TXT_CITY_NAME );
		TextView CityCountry = (TextView)view.findViewById( R.id.IDC_TXT_CITY_COUNTRY );
          TextView CityGeoPosition = (TextView)view.findViewById( R.id.IDC_TXT_CITY_GEOPISITION );
          TextView WeatherDesc = (TextView)view.findViewById( R.id.IDC_TXT_WEATHER_DESCRIPTION );
          TextView CityTemp = (TextView)view.findViewById( R.id.IDC_TXT_CITY_TEMPERATURE );
          TextView LastWeatherUpdate = (TextView)view.findViewById( R.id.IDC_TXT_LAST_WEATHER_UPDATE );
          TextView Pressure = (TextView)view.findViewById( R.id.IDC_TXT_PRESSURE );
          TextView Humitidy = (TextView)view.findViewById( R.id.IDC_TXT_HUMIDITY );
          TextView Visibility = (TextView)view.findViewById( R.id.IDC_TXT_VISIBILITY );
          TextView WindDirection = (TextView)view.findViewById( R.id.IDC_TXT_WIND_DIRECTION );
          TextView WindSpeed = (TextView)view.findViewById( R.id.IDC_TXT_WIND_SPEED );
	
		CityName.setText( City.getName() );
		CityCountry.setText( City.getCountry() );
          CityGeoPosition.setText( City.getLatitude() + " : " + City.getLongitude() );
		
          WeatherDesc.setText( City.getCondition().getWeatherDescription() );
          if( CApp.getCelsius() ) CityTemp.setText( "" + City.getCondition().getTemperatureCelsius() + "ºC" );
		else CityTemp.setText( "" + City.getCondition().getTemperatureFahrenheit() + "ºF" );
          Date upDate = City.getCondition().getObservationTime();
          SimpleDateFormat DateFormat = new SimpleDateFormat( "MMM d, kk:mm" );
          String LastUpdate = ( upDate == null ) ? "null date" : DateFormat.format( upDate );
          LastWeatherUpdate.setText( LastUpdate );

          if( Pressure != null ) Pressure.setText( "" + City.getCondition().getPressure() + " hPa" );
          if( Humitidy != null ) Humitidy.setText( "" + City.getCondition().getHumidity() + "%");
          if( Visibility != null ) Visibility.setText( "" + City.getCondition().getVisibility() + " Km" );
          if( WindDirection != null ) WindDirection.setText( "" + City.getCondition().getWindDirectionDegrees() + "º " + City.getCondition().getWindDirectionCompass() );
          if( WindSpeed != null ) WindSpeed.setText( "" + City.getCondition().getWindSpeedKmph() + " Kmph" );
          
          ImageView WeatherIcon = (ImageView)view.findViewById( R.id.IDP_ICO_WEATHER_IMAGE );
          City.SetViewIcon( getActivity(), WeatherIcon );
          
          GridView ForecastGrid = (GridView)view.findViewById( R.id.IDC_GRID_FORECAST );
          ForecastGrid.setAdapter( new CCityForecastAdapter( getActivity(), City.getForecastList() ) );
          
		return view;
	}
}
