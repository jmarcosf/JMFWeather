/**************************************************************/
/*                                                            */
/* CCityListActivity.java                                     */
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

import android.annotation.SuppressLint;
import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.res.Configuration;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

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
public class CCityListActivity extends CBaseCityActivity implements OnClickListener, OnItemClickListener, OnItemLongClickListener
{
protected final static int         DELETE_CITY_CONFIRMATION_REQUEST_ID = 4777;
     
private   boolean                  m_bTablet = false;
private   DrawerLayout             m_Drawer;
private   ActionBarDrawerToggle    m_DrawerToggle;
private   ListView                 m_ListView;
private   CCityListAdapter         m_Adapter;
private   ProgressBar              m_WaitClock;
private   View                     m_ErrorView;
private   TextView                 m_ErrorMessage;
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
          m_ErrorView = findViewById( R.id.IDC_LAY_ERROR_MESSAGE );
          m_ErrorMessage = (TextView)findViewById( R.id.IDC_TXT_ERROR_MESSAGE );

          m_ListView = (ListView)findViewById( R.id.IDC_LV_CITY_LIST );
          m_ListView.setChoiceMode( m_bTablet ? ListView.CHOICE_MODE_SINGLE : ListView.CHOICE_MODE_NONE );
          m_ListView.setOnItemClickListener( this );
          m_ListView.setOnItemLongClickListener( this );

          m_Drawer = (DrawerLayout)findViewById( R.id.IDC_LAY_DRAWER );
          m_DrawerToggle = new ActionBarDrawerToggle( this, m_Drawer, R.drawable.icon_drawer, R.string.IDS_DRAWER_OPEN_TEXT, R.string.IDS_DRAWER_CLOSE_TEXT )
          {
               public void onDrawerClosed( View drawerView ) {};
               public void onDrawerOpened( View drawerView ) {};
          };
          m_Drawer.setDrawerListener( m_DrawerToggle );
          
          getSupportActionBar().setDisplayHomeAsUpEnabled( true );
          findViewById( R.id.IDR_LAY_LEFT_DRAWER ).setOnClickListener( this );
          findViewById( R.id.IDC_BTN_OK ).setOnClickListener( this );
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
          LoadCityList();
          ServiceConnect();
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
     }

     /*********************************************************/
     /*                                                       */
     /* CCityListActivity.onStop()                            */
     /*                                                       */
     /*********************************************************/
     @Override
     protected void onStop()
     {
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
          ServiceDisconnect();
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
     @SuppressLint( "NewApi" )
     @Override
     public boolean onCreateOptionsMenu( Menu menu )
     {
          getMenuInflater().inflate( R.menu.menu_city_list, menu );
          return true;
     }

     /*********************************************************/
     /*                                                       */ 
     /* CCityListActivity.onActivityResult()                  */ 
     /*                                                       */ 
     /*********************************************************/
     @Override
     protected void onActivityResult( int RequestCode, int ResultCode, Intent Data )
     {
          switch( RequestCode )
          {
               case DELETE_CITY_CONFIRMATION_REQUEST_ID:
                    if( ResultCode == CConfirmationActivity.CONFIRMATION_ACCEPTED )
                    {
                         Toast.makeText( this, "Adding to favorites...", Toast.LENGTH_SHORT ).show();
                    }
                    else
                    {
                         Toast.makeText( this, "User cancelled!", Toast.LENGTH_SHORT ).show();
                    }
                    break;
                    
               default:
                    super.onActivityResult( RequestCode, ResultCode, Data );
                    break;
          }
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
               case R.id.IDC_BTN_OK:
                    m_ErrorView.setVisibility( View.GONE );
                    LoadCityList();
                    break;
                    
               case R.id.IDR_LAY_LEFT_DRAWER:
                    break;              //this is just to avoid clicking thru drawer
          }
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
               intent.putExtra( CCityDetailsFragment.IDS_CITY_ID_PARAM, id );
               startActivity( intent );
          }
     }
     
     /*********************************************************/
     /*                                                       */
     /*                                                       */
     /* OnItemLongClickListener Interface Implementation      */
     /*                                                       */
     /*                                                       */
     /*********************************************************/
     /*                                                       */ 
     /* CCityListActivity.onItemLongClick()                   */ 
     /*                                                       */ 
     /*********************************************************/
     @Override
     public boolean onItemLongClick( AdapterView< ? > ParentView, View view, int iPosition, long id )
     {
          Log.d( CCityListActivity.class.getName(), "OnLongPress(): " + view.getId() );
          Intent intent = new Intent( this, CConfirmationActivity.class );
          String Param = getString( R.string.IDS_DELETE_CITY_QUESTION ) + iPosition;
          intent.putExtra( CConfirmationActivity.CONFIRMATION_QUESTION_PARAM, Param );
          startActivityForResult( intent, DELETE_CITY_CONFIRMATION_REQUEST_ID );
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
                    m_ServiceBinder.getService().LoadWeather( true );
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
     /* CCityListActivity.CDBLoader AsyncTask Class           */
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
               m_ErrorView.setVisibility( View.GONE );
          }

          /****************************************************/
          /*                                                  */
          /* CDBLoader.doInBackground()                       */
          /*                                                  */
          /****************************************************/
          @Override
          protected Cursor doInBackground( Void... params )
          {
               CWeatherDAO WeatherDAO = new CWeatherDAO( CCityListActivity.this );
               Cursor cityCursor = WeatherDAO.SelectAllCities();
               return cityCursor;
          }

          /****************************************************/
          /*                                                  */
          /* CDBLoader.onPostExecute()                        */
          /*                                                  */
          /****************************************************/
          @Override
          protected void onPostExecute( Cursor cityCursor )
          {
               m_WaitClock.setVisibility( View.GONE );
               if( cityCursor.getCount() > 0 && cityCursor.moveToFirst() )
               {
                    if( m_Adapter == null )
                    {
                         m_Adapter = new CCityListAdapter( CCityListActivity.this, cityCursor );
                         m_ListView.setAdapter( m_Adapter );
                    }
                    else
                    {
                         m_Adapter.changeCursor( cityCursor );
                         m_Adapter.notifyDataSetChanged();
                    }
               }
               else
               {
                    m_ErrorMessage.setText( R.string.IDS_READ_CITIES_ERROR_MESSAGE );
                    m_ErrorView.setVisibility( View.VISIBLE );
               }
          }
     }
}
