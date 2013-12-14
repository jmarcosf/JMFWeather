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

import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.res.Configuration;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.utad.marcos.jorge.jmfweather.db.CWeatherDAO;
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
public class CCityListActivity extends CBaseCityActivity implements OnClickListener, OnItemClickListener
{
private   boolean                  m_bTablet = false;
private   CCityListAdapter         m_Adapter;
private   DrawerLayout             m_Drawer;
private   ActionBarDrawerToggle    m_DrawerToggle;
private   ListView                 m_ListView;
private   ProgressBar              m_WaitClock;
private   View                     m_NetworkErrorView;
private   ServiceConnection        m_ServiceConnection;
private   CWeatherRetrieverBinder  m_ServiceBinder;

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

          m_Drawer = (DrawerLayout)findViewById( R.id.IDC_LAY_DRAWER );
          m_DrawerToggle = new ActionBarDrawerToggle( this, m_Drawer, R.drawable.icon_drawer, R.string.IDS_DRAWER_OPEN_TEXT, R.string.IDS_DRAWER_CLOSE_TEXT )
          {
               public void onDrawerClosed( View drawerView ) {};
               public void onDrawerOpened( View drawerView ) {};
          };
          m_Drawer.setDrawerListener( m_DrawerToggle );
          
          getActionBar().setDisplayHomeAsUpEnabled( true );
          findViewById( R.id.IDR_LAY_LEFT_DRAWER ).setOnClickListener( this );
          findViewById( R.id.IDC_BTN_RETRY ).setOnClickListener( this );
     }
     
     /*********************************************************/
     /*                                                       */ 
     /* CCityListActivity.onPostCreate()                      */ 
     /*                                                       */ 
     /*********************************************************/
     @Override
     protected void onPostCreate( Bundle savedInstanceState )
     {
          super.onPostCreate( savedInstanceState );
          m_DrawerToggle.syncState();
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
          LoadCityList();
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
     /* CCityListActivity.onDestroy()                         */ 
     /*                                                       */ 
     /*********************************************************/
     @Override
     protected void onDestroy()
     {
          if( m_ServiceBinder != null ) m_ServiceBinder.getService().StopLoadingImages();
          super.onDestroy();
     }
     
     /*********************************************************/
     /*                                                       */ 
     /* CCityListActivity.onOptionsItemSelected()             */ 
     /*                                                       */ 
     /*********************************************************/
     @Override
     public boolean onOptionsItemSelected( MenuItem Item )
     {
          if( m_DrawerToggle.onOptionsItemSelected( Item ) ) return true;
          else return super.onOptionsItemSelected( Item );
     }
     
     /*********************************************************/
     /*                                                       */
     /* CCityListActivity.onCreateOptionsMenu()               */
     /*                                                       */
     /*********************************************************/
     @Override
     public boolean onCreateOptionsMenu( Menu menu )
     {
          getMenuInflater().inflate( R.menu.menu_city_list, menu );
          return true;
     }

     /*********************************************************/
     /*                                                       */
     /*                                                       */
     /* ActionBarActivity Override Methods                    */
     /*                                                       */
     /*                                                       */
     /*********************************************************/
     /*                                                       */ 
     /* CCityListActivity.onConfigurationChanged()            */ 
     /*                                                       */ 
     /*********************************************************/
     @Override
     public void onConfigurationChanged( Configuration newConfig )
     {
          super.onConfigurationChanged( newConfig );
          m_DrawerToggle.onConfigurationChanged( newConfig );
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
          Long cityId = (Long)view.getTag();
          
//          if( m_bTablet )
//          {
//               m_ListView.setItemChecked( iPosition, true );
//               
//               // Build Tablet Fragment
//               CCityDetailsFragment Fragment = new CCityDetailsFragment();
//               Bundle Params = new Bundle();
//               Params.putParcelable( CCityDetailsFragment.IDS_CITY_ID_PARAM, City );
//               Fragment.setArguments( Params );
//
//               FragmentManager frgManager = getSupportFragmentManager();
//               FragmentTransaction tx = frgManager.beginTransaction();
//               tx.replace( R.id.IDR_LAY_CITY_CONTAINER, Fragment );
//               tx.commit();
//          }
//          else
          {
               Intent intent = new Intent( CCityListActivity.this, CCityDetailsActivity.class );
               intent.putExtra( CCityDetailsFragment.IDS_CITY_ID_PARAM, cityId );
               startActivity( intent );
          }
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
                    LoadCityList();
                    break;
                    
               case R.id.IDR_LAY_LEFT_DRAWER:
                    break;              //this is just to avoid clicking thru drawer
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
                    m_ServiceBinder = (CWeatherRetrieverBinder)binder;
                    m_ServiceBinder.getService().setListener( new IWeatherRetrieverListener()
                    {
                         @Override
                         public void onWeatherLoaded()
                         {
                              LoadCityList();
                         }
                    } );
                    
                    //Load List from DB then program service to update Weather.
                    LoadCityList();
                    m_ServiceBinder.getService().LoadWeather();
               }
               @Override
               public void onServiceDisconnected( ComponentName name )
               {
                    m_ServiceBinder = null;
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
               m_WaitClock.setVisibility( View.VISIBLE );
               m_NetworkErrorView.setVisibility( View.GONE );
          }

          /****************************************************/
          /*                                                  */
          /* CDBLoader.doInBackground()                       */
          /*                                                  */
          /****************************************************/
          @Override
          protected Cursor doInBackground( Void... param )
          {
               CWeatherDAO WeatherDAO = new CWeatherDAO( CCityListActivity.this );
               Cursor cityCursor = WeatherDAO.SelectAllCities();
               return cityCursor;
          }

          /*********************************************************/
          /*                                                       */
          /* CDBLoader.onPostExecute()                             */
          /*                                                       */
          /*********************************************************/
          @Override
          protected void onPostExecute( Cursor cityCursor )
          {
               m_WaitClock.setVisibility( View.GONE );
               m_Adapter = new CCityListAdapter( CCityListActivity.this, cityCursor );
               m_ListView.setAdapter( m_Adapter );

               if( !cityCursor.moveToFirst() || cityCursor.getCount() == 0 )
               {
                    m_NetworkErrorView.setVisibility( View.VISIBLE );
               }
          }
     }
}