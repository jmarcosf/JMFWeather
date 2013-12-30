/**************************************************************/
/*                                                            */
/* CCityDetailsFragment.java                                  */
/* (c)2013 jmarcosf                                           */
/*                                                            */
/* Description: CCityDetailsFragment Class                    */
/*              JmfWeather Project                            */
/*              Pr�ctica asignatura Android Fundamental       */ 
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
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.ImageView;
import android.widget.TextView;

import com.utad.marcos.jorge.jmfweather.db.CWeatherDAO;
import com.utad.marcos.jorge.jmfweather.model.CCity;
import com.utad.marcos.jorge.jmfweather.utility.HorzListView;

/**************************************************************/
/*                                                            */
/*                                                            */
/*                                                            */
/* CCityDetailsFragment Class                                 */ 
/*                                                            */
/*                                                            */
/*                                                            */
/**************************************************************/
public class CCityDetailsFragment extends Fragment implements OnTouchListener
{

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
		long CityId = getArguments().getLong( CCityDetailsActivity.IDS_CITY_ID_PARAM );
		
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

          WeatherDesc.setText( City.getCondition().getWeatherCodeDescription( getActivity() ) );
          if( ( (CBaseCityActivity)getActivity() ).m_bCelsius ) CityTemp.setText( "" + City.getCondition().getTemperatureCelsius() + "�C" );
		else CityTemp.setText( "" + City.getCondition().getTemperatureFahrenheit() + "�F" );
          Date upDate = City.getCondition().getObservationTime();
          SimpleDateFormat DateFormat = new SimpleDateFormat( "MMM d, kk:mm" );
          String LastUpdate = ( upDate == null ) ? "null date" : DateFormat.format( upDate );
          LastWeatherUpdate.setText( LastUpdate );

          if( Pressure != null ) Pressure.setText( "" + City.getCondition().getPressure() + " hPa" );
          if( Humitidy != null ) Humitidy.setText( "" + City.getCondition().getHumidity() + "%");
          if( Visibility != null ) Visibility.setText( "" + City.getCondition().getVisibility() + " Km" );
          if( WindDirection != null ) WindDirection.setText( "" + City.getCondition().getWindDirectionDegrees() + "� " + City.getCondition().getWindDirectionCompass() );
          if( WindSpeed != null ) WindSpeed.setText( "" + City.getCondition().getWindSpeedKmph() + " Kmph" );
          
          ImageView WeatherIcon = (ImageView)view.findViewById( R.id.IDP_ICO_WEATHER_IMAGE );
          City.SetViewIcon( getActivity(), WeatherIcon );
          
          HorzListView ForecastList = (HorzListView)view.findViewById( R.id.IDC_LV_FORECAST );
          ForecastList.setOnTouchListener( this );
          ForecastList.setAdapter( new CCityForecastAdapter( getActivity(), City.getForecastList() ) );
          
		return view;
	}

     /*********************************************************/
     /*                                                       */ 
     /* CCityDetailsFragment.onTouch()                        */ 
     /*                                                       */ 
     /*********************************************************/
     @Override
     public boolean onTouch( View view, MotionEvent event )
     {
          //Free World Weather Online Api supplies Forecast up to 5 days so they fix on landscape modes.
          if( ( (CBaseCityActivity)getActivity() ).m_Orientation != Configuration.ORIENTATION_LANDSCAPE )
          {
               ( (ViewParent)view.getParent() ).requestDisallowInterceptTouchEvent( true );
          }
          return view.onTouchEvent( event );
     }
}
