/**************************************************************/
/*                                                            */
/* CMainActivity.java                                         */
/* (c)2013 jmarcosf                                           */
/*                                                            */
/* Description: CMainActivity Class                           */
/*              JmfWeather Project                            */
/*                                                            */
/**************************************************************/
package com.utad.marcos.jorge.jmfweather;

import android.app.Activity;
import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.database.Cursor;
import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.IBinder;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.utad.marcos.jorge.jmfweather.db.CWeatherDAO;
import com.utad.marcos.jorge.jmfweather.model.CCity;
import com.utad.marcos.jorge.jmfweather.model.CCityList;
import com.utad.marcos.jorge.jmfweather.model.CCondition;
import com.utad.marcos.jorge.jmfweather.model.CForecast;
import com.utad.marcos.jorge.jmfweather.model.CForecastList;
import com.utad.marcos.jorge.jmfweather.services.CWeatherRetrieverService;
import com.utad.marcos.jorge.jmfweather.services.CWeatherRetrieverService.CWeatherRetrieverBinder;
import com.utad.marcos.jorge.jmfweather.services.CWeatherRetrieverService.IWeatherRetrieverListener;

/**************************************************************/
/*                                                            */
/*                                                            */
/*                                                            */
/* CMainActivity Class                                        */
/*                                                            */
/*                                                            */
/*                                                            */
/**************************************************************/
public class CMainActivity extends Activity
{
private ServiceConnection          m_ServiceConnection;
private CWeatherRetrieverBinder    m_Binder;
private CCityList                  m_CityList;
private TextView                   m_Text;

     /*********************************************************/
     /*                                                       */
     /*                                                       */
     /* Activity Override Methods                             */
     /*                                                       */
     /*                                                       */
     /*********************************************************/
     /*                                                       */
     /* CMainActivity.onCreate()                              */
     /*                                                       */
     /*********************************************************/
     @Override
     protected void onCreate( Bundle savedInstanceState )
     {
          super.onCreate( savedInstanceState );
          setContentView( R.layout.layout_activity_main );
          
          m_Text = (TextView)findViewById( R.id.IDC_TXT_MAIN_ACTIVITY_TEXT );
          m_Text.setText( "Hola don Pepito" );
          
          
//        LoadCityList();
          findViewById( R.id.IDC_BTN_REFRESH ).setOnClickListener( new OnClickListener()
          {
               @Override
               public void onClick( View arg0 )
               {
                    LoadCityList();
               }
          } );
     }

     /*********************************************************/
     /*                                                       */
     /* CMainActivity.onStart()                               */
     /*                                                       */
     /*********************************************************/
     @Override
     protected void onStart()
     {
          super.onStart();
          ServiceConnect();
//          LoadCityList();
     }

     /*********************************************************/
     /*                                                       */
     /* CMainActivity.onStop()                                */
     /*                                                       */
     /*********************************************************/
     @Override
     protected void onStop()
     {
          ServiceDisconnect();
          super.onStop();
     }

     /*********************************************************/
     /*                                                       */
     /* CMainActivity.onCreateOptionsMenu()                   */
     /*                                                       */
     /*********************************************************/
     @Override
     public boolean onCreateOptionsMenu( Menu menu )
     {
          getMenuInflater().inflate( R.menu.main_menu, menu );
          return true;
     }

     /*********************************************************/
     /*                                                       */
     /*                                                       */
     /* Class Methods                                         */
     /*                                                       */
     /*                                                       */
     /*********************************************************/
     /*                                                       */
     /* CMainActivity.LoadCityList()                          */
     /*                                                       */
     /*********************************************************/
     public void LoadCityList()
     {
          new CDBLoader().execute();
     }

     /*********************************************************/
     /*                                                       */
     /*                                                       */
     /* Service Binding Methods                               */
     /*                                                       */
     /*                                                       */
     /*********************************************************/
     /*                                                       */
     /* CMainActivity.ServiceConnect()                        */
     /*                                                       */
     /*********************************************************/
     private void ServiceConnect()
     {
          Intent service = new Intent( this, CWeatherRetrieverService.class );
          m_ServiceConnection = new ServiceConnection()
          {
               @Override
               public void onServiceConnected( ComponentName name, IBinder binder )
               {
                    m_Binder = (CWeatherRetrieverBinder)binder;
                    m_Binder.getService().setListener( new IWeatherRetrieverListener()
                    {
                         @Override
                         public void onWeatherLoaded()
                         {
                              LoadCityList();
                         }
                    } );
                    if( !m_Binder.getService().LoadWeather() )
                    {
//                       m_NetworkErrorView.setVisibility(View.VISIBLE);
                    }
               }
               @Override
               public void onServiceDisconnected( ComponentName name )
               {
                    m_Binder = null;
               }
          };

          bindService( service, m_ServiceConnection, Service.BIND_AUTO_CREATE );
     }

     /*********************************************************/
     /*                                                       */
     /* CMainActivity.ServiceDisconnect()                     */
     /*                                                       */
     /*********************************************************/
     private void ServiceDisconnect()
     {
          if( m_ServiceConnection != null ) unbindService( m_ServiceConnection );
     }

     /*********************************************************/
     /*                                                       */
     /*                                                       */
     /* CMainActivity.CDBLoader nested Class                  */
     /*                                                       */
     /*                                                       */
     /*********************************************************/
     private class CDBLoader extends AsyncTask< Void, Void, Cursor >
     {
          /****************************************************/
          /*                                                  */
          /* CDBLoader.onPreExecute()                         */
          /*                                                  */
          /****************************************************/
          @Override
          protected void onPreExecute()
          {
//             m_WaitClock.setVisibility( View.VISIBLE );
//             m_NetworkErrorView.setVisibility( View.GONE );
          }

          /****************************************************/
          /*                                                  */
          /* CDBLoader.doInBackground()                       */
          /*                                                  */
          /****************************************************/
          @Override
          protected Cursor doInBackground( Void... param )
          {
               CWeatherDAO WeatherDAO = new CWeatherDAO( CMainActivity.this );
//               CCondition FirstCondition = new CCondition( 0, (Date)null, 0, 0, 0, 0, 0, 0, 0, null, null, 0, 0, 0, null );
//               CCity City = new CCity( 0, "Madrid", "Spain", "40.400", "-3.683", 0, "Madrid", "" );
//               City.setCurrentCondition( FirstCondition );
//               WeatherDAO.Insert( City );

               Cursor cityCursor = WeatherDAO.SelectAllCities();
               int cityCount = cityCursor.getCount();

               m_CityList = new CCityList();
               if( cityCursor.moveToFirst() )
               {
                    do
                    {
                         CCity NewCity = new CCity( cityCursor );
                         Cursor conditionCursor = WeatherDAO.selectCityCondition( NewCity );
                         int conditionCount = conditionCursor.getCount();
                         if( conditionCursor.moveToFirst() )
                         {
                              CCondition Condition = new CCondition( conditionCursor );
                              NewCity.setCurrentCondition( Condition );
                         }
                         m_CityList.add( NewCity );
                         
                         Cursor forecastCursor = WeatherDAO.selectCityForecast( NewCity );
                         int forecastCount = forecastCursor.getCount();
                         if( forecastCursor.moveToFirst() )
                         {
                              CForecastList ForecastList = new CForecastList();
                              do
                              {
                                   CForecast Forecast = new CForecast( forecastCursor );
                                   ForecastList.add( Forecast );
                                   
                              } while( forecastCursor.moveToNext() );
                              NewCity.setForecastList( ForecastList );
                         }
                    } while( cityCursor.moveToNext() );
               }

               return cityCursor;
          }

          /*********************************************************/
          /*                                                       */
          /* CDBLoader.onPostExecute()                             */
          /*                                                       */
          /*********************************************************/
          @Override
          protected void onPostExecute( Cursor cursor )
          {
               StringBuffer Buffer = new StringBuffer();
               for( CCity City : m_CityList.getCityList() )
               {
                    Buffer.append( "********************************************" );
                    Buffer.append( "\nID: " + City.getId() );
                    Buffer.append( "\nCity: " + City.getName() );
                    Buffer.append( "\nCountry: " + City.getCountry() );
                    Buffer.append( "\nLatitude: " + City.getLatitude() );
                    Buffer.append( "\nLongitude: " + City.getLongitude() );
                    Buffer.append( "\nPopulation: " + City.getPopulation() );
                    Buffer.append( "\nRegion: " + City.getRegion() );
                    Buffer.append( "\nURL: " + City.getWeatherUrl() );
                    
                    if( City.getCondition() != null )
                    {
                         Buffer.append( "\n--Current Conditions-------------------------" );
                         Buffer.append( "\nCloud Cover %: " + City.getCondition().getCloudCoverPercentage() );
                         Buffer.append( "\nTime: " + City.getCondition().getObservationTime() );
                         Buffer.append( "\nPressure: " + City.getCondition().getPressure() );
                         Buffer.append( "\n�C: " + City.getCondition().getTemperatureCelsius() );
                         Buffer.append( "\nVisibility: " + City.getCondition().getVisibility() );
                         Buffer.append( "\n�F: " + City.getCondition().getTemperatureFahrenheit() );
                         Buffer.append( "\nWind Speed MPH: " + City.getCondition().getWindSpeedMph() );
                         Buffer.append( "\nPrecipitation: " + City.getCondition().getPrecipitation() );
                         Buffer.append( "\nWind Direction �: " + City.getCondition().getWindDirectionDegrees() );
                         Buffer.append( "\nWind Direction: " + City.getCondition().getWindDirectionCompass() );
                         Buffer.append( "\nIcon URL: " + City.getCondition().getIconUrl() );
                         Buffer.append( "\nHumidity: " + City.getCondition().getHumidity() );
                         Buffer.append( "\nWind Spped (KMPH): " + City.getCondition().getWindSpeedKmph() );
                         Buffer.append( "\nCode: " + City.getCondition().getWeatherCode() );
                         Buffer.append( "\nDescription: " + City.getCondition().getWeatherDescription() );
                         Buffer.append( "\n" );
                    }
                    
                    if( City.getForecastList() != null )
                    {
                         for( CForecast Forecast : City.getForecastList().getForecastList() )
                         {
                              Buffer.append( "\n--Forcast------------------------------------" );
                              Buffer.append( "\nIcon URL: " + Forecast.getIconUrl() );
                              Buffer.append( "\nMin �C: " + Forecast.getMinTemperatureCelsius() );
                              Buffer.append( "\nWind Speed (MPH): " + Forecast.getWindSpeedMph() );
                              Buffer.append( "\nWind Speed (KMPH): " + Forecast.getWindSpeedKmph() );
                              Buffer.append( "\nWind Direction: " + Forecast.getWindDirection() );
                              Buffer.append( "\nMax �C: " + Forecast.getMaxTemperatureCelsius() );
                              Buffer.append( "\nDate: " + Forecast.getForecastDate() );
                              Buffer.append( "\nCode: " + Forecast.getWeatherCode() );
                              Buffer.append( "\nMax �F: " + Forecast.getMaxTemperatureFahrenheit() );
                              Buffer.append( "\nPrecipitation: " + Forecast.getPrecipitation() );
                              Buffer.append( "\nWind Direction �: " + Forecast.getWindDirectionDegrees() );
                              Buffer.append( "\nWind Direction Compass: " + Forecast.getWindDirectionCompass() );
                              Buffer.append( "\nDescription: " + Forecast.getWeatherDescription() );
                              Buffer.append( "\nMin �F: " + Forecast.getMinTemperatureFahrenheit() );
                         }
                    }
                    Buffer.append( "\n********************************************\n\n" );
               }
               Buffer.append( "\n********************************************" );
               m_Text.setText( Buffer ); 
               
//               m_WaitClock.setVisibility( View.GONE );
//               m_Adapter = new CCityListAdapter( CMainActivity.this, cursor );
//               m_ListView.setAdapter( m_Adapter );
//
//               if( !cursor.moveToFirst() || cursor.getCount() == 0 )
//               {
//                    m_NetworkErrorView.setVisibility( View.VISIBLE );
//               }
          }
     }
}
