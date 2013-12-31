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

	     // Get Location Layout
          ImageView LocationImage = (ImageView)view.findViewById( R.id.IDP_ICO_LOCATION_IMAGE );
		TextView LocationName = (TextView)view.findViewById( R.id.IDC_TXT_LOCATION_NAME );
		TextView LocationCountry = (TextView)view.findViewById( R.id.IDC_TXT_LOCATION_COUNTRY );
          TextView LocationGeoPosition = (TextView)view.findViewById( R.id.IDC_TXT_LOCATION_GEOPISITION );
          
          // Get Data Layout
          TextView DataPressure = (TextView)view.findViewById( R.id.IDC_TXT_DATA_PRESSURE );
          TextView DataHumitidy = (TextView)view.findViewById( R.id.IDC_TXT_DATA_HUMIDITY );
          TextView DataVisibility = (TextView)view.findViewById( R.id.IDC_TXT_DATA_VISIBILITY );
          TextView DataWindDirection = (TextView)view.findViewById( R.id.IDC_TXT_DATA_WIND_DIRECTION );
          TextView DataWindSpeed = (TextView)view.findViewById( R.id.IDC_TXT_DATA_WIND_SPEED );
     
          // Get Condition Layout
          TextView ConditionLastUpdate = (TextView)view.findViewById( R.id.IDC_TXT_CONDITION_LAST_UPDATE );
          TextView ConditionTemperature = (TextView)view.findViewById( R.id.IDC_TXT_CONDITION_TEMPERATURE );
          TextView ConditionDescription = (TextView)view.findViewById( R.id.IDC_TXT_CONDITION_DESCRIPTION );
          
          // Set Location Info
          if( LocationImage != null ) City.SetViewIcon( getActivity(), LocationImage );
		if( LocationName != null ) LocationName.setText( City.getName() );
		if( LocationCountry != null ) LocationCountry.setText( City.getCountry() );
          if( LocationGeoPosition != null ) LocationGeoPosition.setText( City.getLatitude() + " : " + City.getLongitude() );

          // Set Data Info
          if( DataPressure != null ) DataPressure.setText( "" + City.getCondition().getPressure() + " hPa" );
          if( DataHumitidy != null ) DataHumitidy.setText( "" + City.getCondition().getHumidity() + "%");
          if( DataVisibility != null ) DataVisibility.setText( "" + City.getCondition().getVisibility() + " Km" );
          if( DataWindDirection != null ) DataWindDirection.setText( "" + City.getCondition().getWindDirectionDegrees() + "º " + City.getCondition().getWindDirectionCompass() );
          if( DataWindSpeed != null ) DataWindSpeed.setText( "" + City.getCondition().getWindSpeedKmph() + " Kmph" );
          
          // Set Condition Info
          Date upDate = City.getCondition().getObservationTime();
          SimpleDateFormat DateFormat = new SimpleDateFormat( "MMM d, kk:mm" );
          String LastUpdate = ( upDate == null ) ? "null date" : DateFormat.format( upDate );
          if( ConditionLastUpdate != null ) ConditionLastUpdate.setText( LastUpdate );
          if( ConditionTemperature != null )
          {
               if( ( (CBaseCityActivity)getActivity() ).m_bCelsius ) ConditionTemperature.setText( "" + City.getCondition().getTemperatureCelsius() + "ºC" );
     		else ConditionTemperature.setText( "" + City.getCondition().getTemperatureFahrenheit() + "ºF" );
          }
          if( ConditionDescription != null ) ConditionDescription.setText( City.getCondition().getWeatherCodeDescription( getActivity() ) );

          // Set Forecast Info
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
