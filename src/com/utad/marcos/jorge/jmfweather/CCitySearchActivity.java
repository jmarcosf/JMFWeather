/**************************************************************/
/*                                                            */
/* CCitySearchActivity.java                                   */
/* (c)2013 jmarcosf                                           */
/*                                                            */
/* Description: CCityListActivity Class                       */
/*              JmfWeather Project                            */
/*              Práctica asignatura Android Fundamental       */ 
/*              U-Tad - Master Apps                           */ 
/*              www.u-tad.com                                 */ 
/*                                                            */ 
/*        Date: December 2013                                 */ 
/*                                                            */
/**************************************************************/
package com.utad.marcos.jorge.jmfweather;

import java.io.IOException;
import java.text.ParseException;

import org.json.JSONException;

import android.app.Activity;
import android.app.SearchManager;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.utad.marcos.jorge.jmfweather.db.CWeatherDAO;
import com.utad.marcos.jorge.jmfweather.model.CCity;
import com.utad.marcos.jorge.jmfweather.model.CCityList;
import com.utad.marcos.jorge.jmfweather.utility.CWorldWeatherApi;

/**************************************************************/
/*                                                            */
/*                                                            */
/*                                                            */
/* CCitySearchActivity Class                                  */
/*                                                            */
/*                                                            */
/*                                                            */
/**************************************************************/
public class CCitySearchActivity extends Activity implements OnItemClickListener, OnClickListener
{
private   ListView            m_ListView;
private   CCitySearchAdapter  m_Adapter;
private   ProgressBar         m_WaitClock;
private   View                m_ErrorView;
private   TextView            m_ErrorMessage;
private   CCityList           m_CityList;
     
     /*********************************************************/
     /*                                                       */
     /*                                                       */
     /* Activity Override Methods                             */
     /*                                                       */
     /*                                                       */
     /*********************************************************/
     /*                                                       */ 
     /* CCitySearchActivity.onCreate()                        */ 
     /*                                                       */ 
     /*********************************************************/
     @Override
     public void onCreate( Bundle savedInstanceState )
     {
          super.onCreate( savedInstanceState );
          setContentView( R.layout.layout_city_search_activity );
          m_CityList = null;
          
          m_ListView = (ListView)findViewById( R.id.IDC_LV_SEARCH_CITY_LIST );
          m_ListView.setChoiceMode( ListView.CHOICE_MODE_SINGLE );
          m_ListView.setOnItemClickListener( this );
          m_WaitClock = (ProgressBar)findViewById( R.id.IDC_PB_WAIT_CLOCK );
          m_ErrorView = findViewById( R.id.IDC_LAY_ERROR_MESSAGE );
          m_ErrorMessage = (TextView)findViewById( R.id.IDC_TXT_ERROR_MESSAGE );

          HandleIntent( getIntent() );
     }

     /*********************************************************/
     /*                                                       */ 
     /* CCitySearchActivity.onNewIntent()                     */ 
     /*                                                       */ 
     /*********************************************************/
     @Override
     protected void onNewIntent( Intent intent )
     {
          HandleIntent(intent);
     }

     /*********************************************************/
     /*                                                       */
     /*                                                       */
     /* OnItemClickListener Interface Methods                 */
     /*                                                       */
     /*                                                       */
     /*********************************************************/
     /*                                                       */ 
     /* CCitySearchActivity.onItemClick()                     */ 
     /*                                                       */ 
     /*********************************************************/
     @Override
     public void onItemClick( AdapterView< ? > ParentView, View view, int iPosition, long id )
     {
          CCity City = m_CityList.getCityList().get( iPosition );
          new CInsertCity().execute( City );
     }
     
     /*********************************************************/
     /*                                                       */
     /*                                                       */
     /* OnClickListener Interface Methods                     */
     /*                                                       */
     /*                                                       */
     /*********************************************************/
     /*                                                       */ 
     /* CCitySearchActivity.onClick()                         */ 
     /*                                                       */ 
     /*********************************************************/
     @Override
     public void onClick( View view )
     {
          switch( view.getId() )
          {
               case R.id.IDC_BTN_OK:
                    m_ErrorView.setVisibility( View.GONE );
                    this.finish();
                    break;
          }
     }

     /*********************************************************/
     /*                                                       */
     /*                                                       */
     /* Class Methods                                         */
     /*                                                       */
     /*                                                       */
     /*********************************************************/
     /*                                                       */ 
     /* CCitySearchActivity.HandleIntent()                    */ 
     /*                                                       */ 
     /*********************************************************/
     private void HandleIntent( Intent intent )
     {
         if( !Intent.ACTION_SEARCH.equals( intent.getAction() ) ) return;
         String Query = intent.getStringExtra( SearchManager.QUERY );
         new CSearchCity().execute( Query );
     }
     
     /*********************************************************/
     /*                                                       */
     /*                                                       */
     /* CCityListActivity.CSearchCity AsyncTask Class         */
     /*                                                       */
     /*                                                       */
     /*********************************************************/
     private class CSearchCity extends AsyncTask< String, Void, Void >
     {
          /****************************************************/
          /*                                                  */
          /* CSearchCity.onPreExecute()                       */
          /*                                                  */
          /****************************************************/
          @Override
          protected void onPreExecute()
          {
               m_WaitClock.setVisibility( View.VISIBLE );
               m_ErrorView.setVisibility( View.GONE );
               m_ListView.setVisibility( View.GONE );
               m_CityList = null;
          }

          /****************************************************/
          /*                                                  */
          /* CSearchCity.doInBackground()                     */
          /*                                                  */
          /****************************************************/
          @Override
          protected Void doInBackground( String... params )
          {
               if( params.length != 1 || params[ 0 ] == null ) return null;
               CWorldWeatherApi WorldWeatherApi = new CWorldWeatherApi();
               try
               {
                    m_CityList = WorldWeatherApi.SearchCity( params[ 0 ] );
               }
               catch( IOException exception ) { exception.printStackTrace(); }
               catch( JSONException exception ) { exception.printStackTrace(); }
               return null;
          }

          /****************************************************/
          /*                                                  */
          /* CSearchCity.onPostExecute()                      */
          /*                                                  */
          /****************************************************/
          @Override
          protected void onPostExecute( Void Param )
          {
               m_WaitClock.setVisibility( View.GONE );
               if( m_CityList != null && m_CityList.getCityList().size() > 0 )
               {
                    m_Adapter = new CCitySearchAdapter( CCitySearchActivity.this, m_CityList );
                    m_ListView.setAdapter( m_Adapter );
                    m_ListView.setVisibility( View.VISIBLE );
               }
               else
               {
                    m_ErrorMessage.setText( R.string.IDS_NO_CITIES_FOUND_ERROR_MESSAGE );
                    m_ErrorView.setVisibility( View.VISIBLE );
               }
          }
     }

     /*********************************************************/
     /*                                                       */
     /*                                                       */
     /* CCityListActivity.CInsertCity AsyncTask Class         */
     /*                                                       */
     /*                                                       */
     /*********************************************************/
     private class CInsertCity extends AsyncTask< CCity, Void, Boolean >
     {
          /****************************************************/
          /*                                                  */
          /* CInsertCity.onPreExecute()                       */
          /*                                                  */
          /****************************************************/
          @Override
          protected void onPreExecute()
          {
               m_WaitClock.setVisibility( View.VISIBLE );
               m_ErrorView.setVisibility( View.GONE );
               m_ListView.setVisibility( View.GONE );
               m_CityList = null;
          }

          /****************************************************/
          /*                                                  */
          /* CInsertCity.doInBackground()                     */
          /*                                                  */
          /****************************************************/
          @Override
          protected Boolean doInBackground( CCity... params )
          {
               if( params.length != 1 || params[ 0 ] == null ) return false;
               CWorldWeatherApi WorldWeatherApi = new CWorldWeatherApi();
               CWeatherDAO WeatherDAO = new CWeatherDAO( CCitySearchActivity.this );
               try
               {
                    CCity City = params[ 0 ];
                    City.setForecastList( WorldWeatherApi.getCityWeather( City ) );
                    WeatherDAO.Insert( City );
                    return true;
               }
               catch( IOException exception )    { exception.printStackTrace(); }
               catch( JSONException exception )  { exception.printStackTrace(); }
               catch( ParseException exception ) { exception.printStackTrace(); }
               return false;
          }

          /****************************************************/
          /*                                                  */
          /* CSearchCity.onPostExecute()                      */
          /*                                                  */
          /****************************************************/
          @Override
          protected void onPostExecute( Boolean Param )
          {
               m_WaitClock.setVisibility( View.GONE );
               if( !Param )
               {
                    m_ErrorMessage.setText( R.string.IDS_WRITE_CITY_ERROR_MESSAGE );
                    m_ErrorView.setVisibility( View.VISIBLE );
               }
               else CCitySearchActivity.this.finish();
          }
     }
}

