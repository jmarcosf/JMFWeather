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

import android.os.Bundle;
import android.support.v4.app.Fragment;
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
/* CCityDetailsFragment Class                                 */ 
/*                                                            */
/*                                                            */
/*                                                            */
/**************************************************************/
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
          
          ImageView CityIcon = (ImageView)view.findViewById( R.id.IDP_ICO_CITY_ICON );
          City.SetViewIcon( getActivity(), CityIcon );
          
		return view;
	}
}



