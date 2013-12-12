/**************************************************************/
/*                                                            */
/* CCityListActivity.java                                     */
/* (c)2013 jmarcosf                                           */
/*                                                            */
/* Description: CCityListActivity Class                       */
/*              JmfWeather Project                            */
/*                                                            */
/**************************************************************/
package com.utad.marcos.jorge.jmfweather;

import android.app.Activity;
import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.IBinder;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.ProgressBar;

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
/* CCityListActivity Class                                    */
/*                                                            */
/*                                                            */
/*                                                            */
/**************************************************************/
public class CCityListActivity extends Activity implements OnClickListener, OnItemClickListener
{
private   boolean                  m_bTablet = false;
private   CCityListAdapter         m_Adapter;
private   ListView                 m_ListView;
private   ProgressBar              m_WaitClock;
private   View                     m_NetworkErrorView;
private   ServiceConnection        m_ServiceConnection;
private   CWeatherRetrieverBinder  m_Binder;

     /*********************************************************/
     /*                                                       */
     /*                                                       */
     /* Activity Override Methods                             */
     /*                                                       */
     /*                                                       */
     /*********************************************************/
     /*                                                       */
     /* CCityListActivity.onCreate()                          */
     /*                                                       */
     /*********************************************************/
     @Override
     protected void onCreate( Bundle savedInstanceState )
     {
          super.onCreate( savedInstanceState );
          setContentView( R.layout.layout_city_list_activity );
          
          m_bTablet = getResources().getBoolean( R.bool.g_bTablet );
//          setContentView( m_bTablet ? R.layout.layout_city_list_activity_tablet : R.layout.layout_city_list_activity );
          setContentView( R.layout.layout_city_list_activity );
          
          m_WaitClock = (ProgressBar)findViewById( R.id.IDC_PB_WAIT_CLOCK );
          m_NetworkErrorView = findViewById( R.id.IDC_LAY_NETWORK_ERROR_MESSAGE );

          m_ListView = (ListView)findViewById( R.id.IDC_LV_CITY_LIST );
          m_ListView.setChoiceMode( m_bTablet ? ListView.CHOICE_MODE_SINGLE : ListView.CHOICE_MODE_NONE );
          m_ListView.setOnItemClickListener( this );

          getActionBar().setDisplayHomeAsUpEnabled( true );
          findViewById( R.id.IDC_BTN_RETRY ).setOnClickListener( this );
     }

     /*********************************************************/
     /*                                                       */
     /* CCityListActivity.onStart()                           */
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
     /* CCityListActivity.onStop()                            */
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
     /* CCityListActivity.onCreateOptionsMenu()               */
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
     /* OnItemClickListener Interface Implementation          */
     /*                                                       */
     /*                                                       */
     /*********************************************************/
     /*                                                       */ 
     /* CCityListActivity.onItemClick()                       */ 
     /*                                                       */ 
     /*********************************************************/
     @Override 
     public void onItemClick( AdapterView< ? > ParentView, View view, int iPosition, long id )
     {
//          if( m_bTablet )
//          {
//               m_Post = m_PostList.get( id );
//               m_ListView.setItemChecked( iPosition, true );
//               
//               // Build Tablet Fragment
//               CPostDetailsFragment Fragment = new CPostDetailsFragment();
//               Bundle Params = new Bundle();
//               Params.putParcelable( CPostDetailsFragment.IDS_POST_PARAM, m_Post );
//               Fragment.setArguments( Params );
//
//               FragmentManager frgManager = getSupportFragmentManager();
//               FragmentTransaction tx = frgManager.beginTransaction();
//               tx.replace( R.id.IDR_LAY_POST_CONTAINER, Fragment );
//               tx.commit();
//          }
//          else
//          {
//               Intent intent = new Intent( CPostListActivity.this, CPostDetailsActivity.class );
//               intent.putExtra( CPostDetailsActivity.IDS_POSTLIST_PARAM, m_PostList );
//               intent.putExtra( CPostDetailsActivity.IDS_POSTINDEX_PARAM, iPosition );
//               startActivity( intent );
//          }
     }
     
     /*********************************************************/
     /*                                                       */
     /*                                                       */
     /* OnClickListener Interface Implementation              */
     /*                                                       */
     /*                                                       */
     /*********************************************************/
     /*                                                       */ 
     /* CCityListActivity.onClick()                           */ 
     /*                                                       */ 
     /*********************************************************/
     @Override
     public void onClick( View view )
     {
          switch( view.getId() )
          {
               case R.id.IDC_BTN_RETRY:
                    m_NetworkErrorView.setVisibility( View.GONE );
//                    LoadCityList();
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
     /* CCityListActivity.LoadCityList()                      */
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
     /* CCityListActivity.ServiceConnect()                    */
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
                         m_NetworkErrorView.setVisibility(View.VISIBLE);
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
     /* CCityListActivity.ServiceDisconnect()                 */
     /*                                                       */
     /*********************************************************/
     private void ServiceDisconnect()
     {
          if( m_ServiceConnection != null ) unbindService( m_ServiceConnection );
     }

     /*********************************************************/
     /*                                                       */
     /*                                                       */
     /* CCityListActivity.CDBLoader nested Class              */
     /*                                                       */
     /*                                                       */
     /*********************************************************/
     private class CDBLoader extends AsyncTask< Void, Void, CCityList >
     {
          /****************************************************/
          /*                                                  */
          /* CDBLoader.onPreExecute()                         */
          /*                                                  */
          /****************************************************/
          @Override
          protected void onPreExecute()
          {
               m_WaitClock.setVisibility( View.VISIBLE );
               m_NetworkErrorView.setVisibility( View.GONE );
          }

          /****************************************************/
          /*                                                  */
          /* CDBLoader.doInBackground()                       */
          /*                                                  */
          /****************************************************/
          @Override
          protected CCityList doInBackground( Void... param )
          {
               CWeatherDAO WeatherDAO = new CWeatherDAO( CCityListActivity.this );
               Cursor cityCursor = WeatherDAO.SelectAllCities();
               CCityList CityList = new CCityList();
               
               if( cityCursor.moveToFirst() )
               {
                    do
                    {
                         CCity NewCity = new CCity( cityCursor );
                         Cursor conditionCursor = WeatherDAO.selectCityCondition( NewCity );
                         if( conditionCursor.moveToFirst() )
                         {
                              CCondition Condition = new CCondition( conditionCursor );
                              NewCity.setCurrentCondition( Condition );
                         }
                         CityList.add( NewCity );
                         
                         Cursor forecastCursor = WeatherDAO.selectCityForecast( NewCity );
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

               return CityList;
          }

          /*********************************************************/
          /*                                                       */
          /* CDBLoader.onPostExecute()                             */
          /*                                                       */
          /*********************************************************/
          @Override
          protected void onPostExecute( CCityList CityList )
          {
               m_WaitClock.setVisibility( View.GONE );
               m_Adapter = new CCityListAdapter( CCityListActivity.this, CityList );
               m_ListView.setAdapter( m_Adapter );

               if( CityList.getCityList().size() == 0 )
               {
                    m_NetworkErrorView.setVisibility( View.VISIBLE );
               }
          }
     }
}
