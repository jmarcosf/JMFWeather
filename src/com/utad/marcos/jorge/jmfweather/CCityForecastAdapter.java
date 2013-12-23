/**************************************************************/
/*                                                            */
/* CCityForecastAdapter.java                                  */
/* (c)2013 jmarcosf                                           */
/*                                                            */
/* Description: CCityForecastAdapter Class                    */
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
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.utad.marcos.jorge.jmfweather.model.CForecast;
import com.utad.marcos.jorge.jmfweather.model.CForecastList;

/**************************************************************/
/*                                                            */
/*                                                            */
/*                                                            */
/* CCityForecastAdapter Class                                 */
/*                                                            */
/*                                                            */
/*                                                            */
/**************************************************************/
public class CCityForecastAdapter extends BaseAdapter
{
private Activity         m_Context;
private CForecastList    m_ForecastList;
	
     /*********************************************************/
     /*                                                       */ 
     /*                                                       */ 
     /* Class Constructors                                    */ 
     /*                                                       */ 
     /*                                                       */ 
     /*********************************************************/
	/*                                                       */ 
	/* CCityForecastAdapter.CCityForecastAdapter()           */ 
	/*                                                       */ 
	/*********************************************************/
	public CCityForecastAdapter( Activity context, CForecastList ForecastList )
	{
		this.m_Context = context;
		this.m_ForecastList = ForecastList;
	}
	
     /*********************************************************/
     /*                                                       */ 
     /*                                                       */ 
     /* BaseAdapter Override Methods                          */ 
     /*                                                       */ 
     /*                                                       */ 
	/*********************************************************/
     /*                                                       */ 
     /* CCityForecastAdapter.getCount()                       */ 
     /*                                                       */ 
     /*********************************************************/
     @Override
     public int getCount()
     {
          return m_ForecastList.getSize();
     }

     /*********************************************************/
     /*                                                       */ 
     /* CCityForecastAdapter.getItem()                        */ 
     /*                                                       */ 
     /*********************************************************/
     @Override
     public Object getItem( int position )
     {
          return m_ForecastList.getAt( position );
     }

     /*********************************************************/
     /*                                                       */ 
     /* CCityForecastAdapter.getItemId()                      */ 
     /*                                                       */ 
     /*********************************************************/
     @Override
     public long getItemId( int position )
     {
          return m_ForecastList.getAt( position ).getId();
     }

     /*********************************************************/
     /*                                                       */ 
     /* CCityForecastAdapter.getView()                        */ 
     /*                                                       */ 
     /*********************************************************/
     @SuppressLint( "SimpleDateFormat" )
     @Override
     public View getView( int position, View ConvertView, ViewGroup Parent )
     {
          View ItemView = null;
          if( ConvertView == null )
          {
               LayoutInflater inflater = (LayoutInflater)m_Context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
               ItemView = inflater.inflate( R.layout.layout_city_forecast_item, null );
          }
          else ItemView = ConvertView;
          
          CForecast Forecast = m_ForecastList.getAt( position );
          
          TextView ForecastDate = (TextView)ItemView.findViewById( R.id.IDC_TXT_FORECAST_DATE );
          Date fcDate = Forecast.getForecastDate();
          SimpleDateFormat DateFormat = new SimpleDateFormat( "EEE" );
          String DayOfWeek = ( fcDate == null ) ? "null date" : DateFormat.format( fcDate );
          ForecastDate.setText( DayOfWeek );

          ImageView ForecastIcon = (ImageView)ItemView.findViewById( R.id.IDP_ICO_FORECAST_ICON );
          Forecast.SetViewIcon( m_Context, ForecastIcon );
          
          TextView ForecastTemperature = (TextView)ItemView.findViewById( R.id.IDC_TXT_FORECAST_TEMPERATURE );
          if( CApp.getCelsius() ) ForecastTemperature.setText( "" + Forecast.getMinTemperatureCelsius() + "/" + Forecast.getMaxTemperatureCelsius() + "ºC" );
          else ForecastTemperature.setText( "" + Forecast.getMinTemperatureFahrenheit() + "/" + Forecast.getMaxTemperatureFahrenheit() + "ºF" );

          TextView ForecastDescription = (TextView)ItemView.findViewById( R.id.IDC_TXT_FORECAST_DESCRIPTION );
          ForecastDescription.setText( Forecast.getWeatherDescription() );

          return ItemView;
     }
}