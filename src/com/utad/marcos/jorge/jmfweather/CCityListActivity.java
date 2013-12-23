/**************************************************************/
/*                                                            */
/* CCityListActivity.java                                     */
/* (c)2013 jmarcosf                                           */
/*                                                            */
/* Description: CCityListActivity Class                       */
/*              JmfWeather Project                            */
/*              Pr�ctica asignatura Android Fundamental       */ 
/*              U-Tad - Master Apps                           */ 
/*              www.u-tad.com                                 */ 
/*                                                            */ 
/*        Date: December 2013                                 */ 
/*                                                            */
/**************************************************************/
package com.utad.marcos.jorge.jmfweather;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

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
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.text.Html;
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
protected final static int         MSGBOX_READ_CITIES_ERROR_REQUEST_ID     = 100;
protected final static int         MSGBOX_DELETE_CITY_REQUEST_ID           = 101;
     
private   boolean                  m_bTablet = false;
private   DrawerLayout             m_Drawer = null;
private   ActionBarDrawerToggle    m_DrawerToggle = null;
private   ListView                 m_ListView = null;
private   CCityListAdapter         m_Adapter = null;
private   ProgressBar              m_WaitClock = null;
private   ServiceConnection        m_ServiceConnection = null;
private   CWeatherRetrieverBinder  m_ServiceBinder = null;

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
          Log.d( CCityListActivity.class.getSimpleName(), "OnCreate()" );
          if( savedInstanceState != null ) Log.d( CBaseCityActivity.class.getSimpleName(), "savedInstanceState != null" );
          setContentView( R.layout.layout_city_list_activity );
          
          m_bTablet = ( getResources().getBoolean( R.bool.g_bTablet ) && CApp.IsDivideScreenOnTabletsEnabled() );
          CApp.setOrientation( getResources().getConfiguration().orientation );
          Log.d( CCityListActivity.class.getSimpleName(), ( CApp.getOrientation() == Configuration.ORIENTATION_LANDSCAPE ) ? "LANDSCAPE" : "PORTRAIT" );
          setContentView( m_bTablet ? R.layout.layout_city_list_activity_tablet : R.layout.layout_city_list_activity );
          
          m_WaitClock = (ProgressBar)findViewById( R.id.IDC_PB_WAIT_CLOCK );

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
          ( (TextView)findViewById( R.id.IDC_TXT_HELP ) ).setText( Html.fromHtml( GetHelpText() ) );

          ServiceConnect();
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
          Log.d( CCityListActivity.class.getSimpleName(), "OnPostCreate()" );
          m_DrawerToggle.syncState();
          if( savedInstanceState != null ) CApp.setCelsius( savedInstanceState.getBoolean( "Celsius" ) );
          else CApp.getWeatherDegreesType();
     }
     
     /*********************************************************/
     /*                                                       */
     /* CCityListActivity.onResume()                          */
     /*                                                       */
     /*********************************************************/
     @Override
     protected void onResume()
     {
          super.onResume();
          Log.d( CCityListActivity.class.getSimpleName(), "OnResume()" );
          LoadCityList();
     }

     /*********************************************************/
     /*                                                       */ 
     /* CCityListActivity.onDestroy()                         */ 
     /*                                                       */ 
     /*********************************************************/
     @Override
     protected void onDestroy()
     {
          Log.d( CCityListActivity.class.getSimpleName(), "OnDestroy()" );
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

          switch( Item.getItemId() )
          {
               case R.id.IDM_DEGREES_CELSIUS:
                    if( !CApp.getCelsius() )
                    {
                         CApp.setCelsius( true );
                         if( m_Adapter != null ) m_Adapter.notifyDataSetChanged();
                    }
                    return true;

               case R.id.IDM_DEGREES_FAHRENHEIT:
                    if( CApp.getCelsius() )
                    {
                         CApp.setCelsius( false );
                         if( m_Adapter != null ) m_Adapter.notifyDataSetChanged();
                    }
                    return true;
     
               default:
                    return super.onOptionsItemSelected( Item );
          }
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
     protected void onActivityResult( int RequestCode, int ResultCode, Intent intent )
     {
          switch( RequestCode )
          {
               case MSGBOX_DELETE_CITY_REQUEST_ID:
                    if( ResultCode == CMessageBoxActivity.MESSAGEBOX_RESULT_ACCEPTED )
                    {
                         long CityId = intent.getLongExtra( CMessageBoxActivity.MESSAGEBOX_PARAM_OBJECT_ID, -1 );
                         if( CityId > 0 )
                         {
                              CWeatherDAO WeatherDAO = new CWeatherDAO( this );
                              int Result = WeatherDAO.DeleteCity( CityId );
                              WeatherDAO.Close();
                              if( Result != -1 ) Log.d( CCityListActivity.class.getName(), "Delete City!" );
                              if( Result != -1 ) LoadCityList();
                         }
                    }
                    break;
                    
               case MSGBOX_READ_CITIES_ERROR_REQUEST_ID:
                    if( ResultCode == CMessageBoxActivity.MESSAGEBOX_RESULT_ACCEPTED ) Log.d( CCityListActivity.class.getName(), "Read Cities Error!" );
                    if( ResultCode == CMessageBoxActivity.MESSAGEBOX_RESULT_ACCEPTED ) LoadCityList();
                    else finish();
                    break;
                    
               default:
                    super.onActivityResult( RequestCode, ResultCode, intent );
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
          if( m_bTablet )
          {
               m_ListView.setItemChecked( iPosition, true );
               
               // Build Tablet Fragment
               CCityDetailsFragment Fragment = new CCityDetailsFragment();
               Bundle Params = new Bundle();
               Params.putLong( CCityDetailsFragment.IDS_CITY_ID_PARAM, id );
               Fragment.setArguments( Params );

               FragmentManager frgManager = getSupportFragmentManager();
               FragmentTransaction tx = frgManager.beginTransaction();
               tx.replace( R.id.IDR_LAY_CITY_CONTAINER, Fragment );
               tx.commit();
          }
          else
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
          Intent intent = new Intent( this, CMessageBoxActivity.class );
          intent.putExtra( CMessageBoxActivity.MESSAGEBOX_PARAM_TYPE, CMessageBoxActivity.MESSAGEBOX_TYPE_YESNO );
          intent.putExtra( CMessageBoxActivity.MESSAGEBOX_PARAM_TITLE, getString( R.string.IDS_DELETE_CITY_TITLE ) );
          String Question = getString( R.string.IDS_DELETE_CITY_QUESTION, (String)view.getTag() );
          intent.putExtra( CMessageBoxActivity.MESSAGEBOX_PARAM_TEXT, Html.fromHtml( Question  ) );
          intent.putExtra( CMessageBoxActivity.MESSAGEBOX_PARAM_OBJECT_ID, id );
          startActivityForResult( intent, MSGBOX_DELETE_CITY_REQUEST_ID );
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
          Log.d( CCityListActivity.class.getSimpleName(), "LoadCityList()" );
          new CDBLoader().execute();
     }

     /*********************************************************/
     /*                                                       */
     /* CCityListActivity.GetHelpText()                       */
     /*                                                       */
     /*********************************************************/
     public String GetHelpText()
     {
          InputStream inputStream = getResources().openRawResource( R.raw.raw_jmfweather_help );
          ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
          int i;
          try
          {
               i = inputStream.read();
               while( i != -1 )
               {
                    byteArrayOutputStream.write( i );
                    i = inputStream.read();
               }
               inputStream.close();
          }
          catch (IOException e) { e.printStackTrace(); }
          return byteArrayOutputStream.toString();
     }

     /*********************************************************/
     /*                                                       */
     /*                                                       */
     /* Service Methods                                       */
     /*                                                       */
     /*                                                       */
     /*********************************************************/
     /*                                                       */
     /* CCityListActivity.ServiceConnect()                    */
     /*                                                       */
     /*********************************************************/
     private void ServiceConnect()
     {
          Log.d( CCityListActivity.class.getName(), "ServiceConnect()" );
          Intent service = new Intent( this, CWeatherRetrieverService.class );
          m_ServiceConnection = ( new ServiceConnection()
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
                              Log.d( CCityListActivity.class.getName(), "onWeatherLoaded()" );
                              LoadCityList();
                         }
                    } );
                    
                    //Program service to update Weather.
                    Log.d( CCityListActivity.class.getName(), "onServiceConnected()" );
                    m_ServiceBinder.getService().LoadWeather();
               }
               @Override
               public void onServiceDisconnected( ComponentName name )
               {
                    m_ServiceBinder = null;
               }
          } );

          bindService( service, m_ServiceConnection, Service.BIND_AUTO_CREATE );
     }

     /*********************************************************/
     /*                                                       */
     /* CCityListActivity.ServiceDisconnect()                 */
     /*                                                       */
     /*********************************************************/
     private void ServiceDisconnect()
     {
          Log.d( CCityListActivity.class.getName(), "ServiceDisconnect()" );
          if( m_ServiceConnection != null ) unbindService( m_ServiceConnection );
          m_ServiceConnection = null;
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
               Log.d( CCityListActivity.class.getSimpleName(), "OnPreExecute()" );
               m_WaitClock.setVisibility( View.VISIBLE );
          }

          /****************************************************/
          /*                                                  */
          /* CDBLoader.doInBackground()                       */
          /*                                                  */
          /****************************************************/
          @Override
          protected Cursor doInBackground( Void... params )
          {
               Log.d( CCityListActivity.class.getSimpleName(), "doInBackround()" );
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
               Log.d( CCityListActivity.class.getSimpleName(), "OnPostExecute()" );
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
                    Intent intent = new Intent( CCityListActivity.this, CMessageBoxActivity.class );
                    intent.putExtra( CMessageBoxActivity.MESSAGEBOX_PARAM_TYPE, CMessageBoxActivity.MESSAGEBOX_TYPE_RETRYCANCEL );
                    intent.putExtra( CMessageBoxActivity.MESSAGEBOX_PARAM_TITLE, getString( R.string.IDS_ERROR ) );
                    String MessageText = getString( R.string.IDS_READ_CITIES_ERROR_MESSAGE );
                    intent.putExtra( CMessageBoxActivity.MESSAGEBOX_PARAM_TEXT, Html.fromHtml( MessageText  ) );
                    startActivityForResult( intent, MSGBOX_READ_CITIES_ERROR_REQUEST_ID );
               }
          }
     }
}
