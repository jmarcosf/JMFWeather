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

import java.io.IOException;
import java.util.Arrays;
import java.util.Locale;

import android.annotation.SuppressLint;
import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.res.Configuration;
import android.database.Cursor;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.text.Html;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ListView;
import android.widget.ProgressBar;

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
public static final String         CURRENT_SELECTED_CITY_STRING = "CurrentSelectedCity";

private   boolean                  m_bEmptyTableWarningAlreadyShowed = false;
private   DrawerLayout             m_Drawer = null;
private   ActionBarDrawerToggle    m_DrawerToggle = null;
private   ListView                 m_ListView = null;
private   CCityListAdapter         m_Adapter = null;
private   ProgressBar              m_WaitClock = null;
private   ServiceConnection        m_ServiceConnection = null;
private   CWeatherRetrieverBinder  m_ServiceBinder = null;
private   int                      m_CurrentSelectedCity = -1;

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
          
          setContentView( m_bTablet ? R.layout.layout_city_list_activity_tablet : R.layout.layout_city_list_activity );
          m_WaitClock = (ProgressBar)findViewById( R.id.IDC_PB_WAIT_CLOCK );
          
          m_ListView = (ListView)findViewById( R.id.IDC_LV_CITY_LIST );
          m_ListView.setChoiceMode( ListView.CHOICE_MODE_SINGLE );
          m_ListView.setOnItemClickListener( this );
          m_ListView.setOnItemLongClickListener( this );

          m_Drawer = (DrawerLayout)findViewById( R.id.IDC_LAY_DRAWER );
          m_DrawerToggle = new ActionBarDrawerToggle( this, m_Drawer, R.drawable.icon_drawer, R.string.IDS_DRAWER_OPEN_TEXT, R.string.IDS_DRAWER_CLOSE_TEXT )
          {
               public void onDrawerClosed( View drawerView ) {};
               public void onDrawerOpened( View drawerView ) {};
          };
          m_Drawer.setDrawerListener( m_DrawerToggle );
          
          //Set WebView Help File
          String DefaultFile = "jmfweather_help.html";
          String LocaleFile = "jmfweather_help-" + Locale.getDefault().getLanguage() + ".html";
          boolean bLocaleExists = false;
          try { bLocaleExists = Arrays.asList( getResources().getAssets().list( "" ) ).contains( LocaleFile ); }
          catch( IOException exception ) { exception.printStackTrace(); }
          
          String FileName = "file:///android_asset/" + ( ( bLocaleExists ) ? LocaleFile : DefaultFile );
          WebView webView = (WebView)findViewById( R.id.IDC_TXT_HELP );
          if( webView != null ) webView.loadUrl( FileName );
          
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
          if( savedInstanceState != null ) m_bCelsius = savedInstanceState.getBoolean( "Celsius" );
          else CApp.getWeatherDegreesUnit();
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

          //if just inserted a new city, selected it. If not try current location nearby
          long CityId = CApp.getSelectedCityId();
          CApp.setSelectedCityId( -1 );
          if( CityId == -2 ) CityId = m_WeatherDAO.GetCurrentLocationId();
          if( CityId != -1 ) SelectCity( CityId );
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
     /* CCityListActivity.onBackPressed()                     */ 
     /*                                                       */ 
     /*********************************************************/
     @Override
     public void onBackPressed()
     {
          int iSelectedItem = m_ListView.getCheckedItemPosition();
          if( iSelectedItem != -1 ) CApp.setSelectedCityId( m_ListView.getItemIdAtPosition( iSelectedItem ) );
          super.onBackPressed();
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
                    if( !m_bCelsius )
                    {
                         m_bCelsius = true;
                         if( m_Adapter != null ) m_Adapter.notifyDataSetChanged();
                         if( m_bTablet )
                         {
                              int iSelectedItem = m_ListView.getCheckedItemPosition();
                              if( iSelectedItem != -1 ) m_ListView.performItemClick( m_ListView, iSelectedItem, m_ListView.getItemIdAtPosition( iSelectedItem ) );
                         }
                    }
                    return true;

               case R.id.IDM_DEGREES_FAHRENHEIT:
                    if( m_bCelsius )
                    {
                         m_bCelsius = false;
                         if( m_Adapter != null ) m_Adapter.notifyDataSetChanged();
                         if( m_bTablet )
                         {
                              int iSelectedItem = m_ListView.getCheckedItemPosition();
                              if( iSelectedItem != -1 ) m_ListView.performItemClick( m_ListView, iSelectedItem, m_ListView.getItemIdAtPosition( iSelectedItem ) );
                         }
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
     /* CCityListActivity.onSaveInstanceState()               */ 
     /*                                                       */ 
     /*********************************************************/
     @Override
     protected void onSaveInstanceState( Bundle outState )
     {
          Log.d( CCityListActivity.class.getSimpleName(), "OnSaveInstanceState()" );
          outState.putInt( CURRENT_SELECTED_CITY_STRING, m_ListView.getCheckedItemPosition() );
          super.onSaveInstanceState( outState );
     }

     /*********************************************************/
     /*                                                       */ 
     /* CCityListActivity.onRestoreInstanceState()            */ 
     /*                                                       */ 
     /*********************************************************/
     @Override
     protected void onRestoreInstanceState( Bundle savedInstanceState )
     {
          super.onRestoreInstanceState( savedInstanceState );
          Log.d( CCityListActivity.class.getSimpleName(), "OnRestoreInstanceState()" );
          if( savedInstanceState == null ) Log.d( CBaseCityActivity.class.getSimpleName(), "savedInstanceState = null" );
          if( savedInstanceState != null ) m_CurrentSelectedCity = savedInstanceState.getInt( CURRENT_SELECTED_CITY_STRING );
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
               case CApp.MSGBOX_READ_CITIES_ERROR_REQUEST_ID:
                    Log.d( CCityListActivity.class.getSimpleName(), "Read Cities Error!" );
                    if( ResultCode == CMessageBoxActivity.MESSAGEBOX_RESULT_ACCEPTED ) LoadCityList();
                    else finish();
                    break;
                    
               case CApp.MSGBOX_CITY_TABLE_EMPTY_REQUEST_ID:
                    m_bEmptyTableWarningAlreadyShowed = true;
                    break;
                    
               case CApp.MSGBOX_DELETE_CITY_REQUEST_ID:
                    if( ResultCode == CMessageBoxActivity.MESSAGEBOX_RESULT_ACCEPTED )
                    {
                         long CityId = intent.getLongExtra( CMessageBoxActivity.MESSAGEBOX_PARAM_OBJECT_ID, -1 );
                         Boolean bDeleteSelectedCity = intent.getBooleanExtra( CMessageBoxActivity.MESSAGEBOX_PARAM_USER_DATA1, Boolean.valueOf( false ) ); 
                         Integer iDeletedPosition = intent.getIntExtra( CMessageBoxActivity.MESSAGEBOX_PARAM_USER_DATA2, Integer.valueOf( -1 ) ); 
                         if( CityId > 0 && m_WeatherDAO.DeleteCity( CityId ) != -1 )
                         {
                              Log.d( CCityListActivity.class.getSimpleName(), "Delete City!" );
                              LoadCityList();
                              // On tablets, select the previous city
                              if( bDeleteSelectedCity && m_bTablet )
                              {
                                   int iPosition = ( iDeletedPosition > 0 ) ? iDeletedPosition - 1 : 0;
                                   m_ListView.performItemClick( m_ListView, iPosition, m_ListView.getItemIdAtPosition( iPosition ) );
                                   m_ListView.setSelection( iPosition );
                              }
                         }
                    }
                    break;
                    
               case CApp.VIEWPAGER_RETURN_SELECTED_REQUEST_ID:
                    CApp.setSelectedCityId( ResultCode );
                    if( intent != null )
                    {
                         boolean bValue = intent.getBooleanExtra( CCityDetailsActivity.IDS_CELSIUS_PARAM, m_bCelsius );
                         m_bCelsius = bValue;
                    }
                    break;
                    
               case CApp.SETTINGS_MODIFY_REQUEST_ID:
                    super.onActivityResult( RequestCode, ResultCode, intent );
                    if( ( ResultCode & CSettingsActivity.PREF_FLAG_DIVIDE_SCREEN_ON_TABLETS ) != 0 )
                    {
                         this.recreate();
                    }
                    else if( ( ResultCode & CSettingsActivity.PREF_FLAG_WEATHER_DEGREES_UNIT ) != 0 )
                    {
                         if( m_bTablet )
                         {
                              FragmentManager frgManager = getSupportFragmentManager();
                              Fragment fragment = frgManager.findFragmentByTag( "WeatherFragment" );
                              FragmentTransaction tx = frgManager .beginTransaction();
                              tx.detach( fragment );
                              tx.attach( fragment );
                              tx.commitAllowingStateLoss();
                         }
                    }
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
               Params.putLong( CCityDetailsActivity.IDS_CITY_ID_PARAM, id );
               Fragment.setArguments( Params );

               FragmentManager frgManager = getSupportFragmentManager();
               FragmentTransaction tx = frgManager.beginTransaction();
               tx.replace( R.id.IDR_LAY_CITY_CONTAINER, Fragment, "WeatherFragment" );
               tx.commitAllowingStateLoss();
          }
          else
          {
               Intent intent = new Intent( CCityListActivity.this, CCityDetailsActivity.class );
               intent.putExtra( CCityDetailsActivity.IDS_CITY_ID_PARAM, id );
               intent.putExtra( CCityDetailsActivity.IDS_CELSIUS_PARAM, m_bCelsius );
               startActivityForResult( intent, CApp.VIEWPAGER_RETURN_SELECTED_REQUEST_ID );
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
          Bundle Params = new Bundle();
          Params.putBoolean( CMessageBoxActivity.MESSAGEBOX_PARAM_USER_DATA1, Boolean.valueOf( iPosition == m_ListView.getCheckedItemPosition() ) );        
          Params.putInt( CMessageBoxActivity.MESSAGEBOX_PARAM_USER_DATA2, Integer.valueOf( iPosition ) );
          MessageBox( CApp.MSGBOX_DELETE_CITY_REQUEST_ID,
                      CMessageBoxActivity.MESSAGEBOX_TYPE_YESNO,
                      getString( R.string.IDS_DELETE_CITY_TITLE ),
                      Html.fromHtml( getString( R.string.IDS_DELETE_CITY_QUESTION, (String)view.getTag() ) ),
                      android.R.drawable.ic_delete,
                      id,
                      Params );
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
          m_WaitClock.setVisibility( View.VISIBLE );
          
          Cursor cityCursor = m_WeatherDAO.SelectAllCities();
          m_WaitClock.setVisibility( View.GONE );

          if( cityCursor != null )
          {
               if( cityCursor.getCount() > 0 )
               {
                    if( cityCursor.moveToFirst() )
                    {
                         if( m_Adapter == null )
                         {
                              m_Adapter = new CCityListAdapter( CCityListActivity.this, m_WeatherDAO, cityCursor );
                              m_ListView.setAdapter( m_Adapter );
                              
                              int iPosition = ( m_CurrentSelectedCity == -1 ) ? 0 : m_CurrentSelectedCity;
                              if( m_bTablet ) m_ListView.performItemClick( m_ListView, iPosition, m_ListView.getItemIdAtPosition( iPosition ) );
                              else if( m_CurrentSelectedCity != -1 ) m_ListView.setItemChecked( iPosition, true );
                              m_ListView.setSelection( iPosition );
                         }
                         else
                         {
                              m_Adapter.changeCursor( cityCursor );
                              m_Adapter.notifyDataSetChanged();
                         }
                    }
               }
               else if( !m_bEmptyTableWarningAlreadyShowed )
               {
                    MessageBox( CApp.MSGBOX_CITY_TABLE_EMPTY_REQUEST_ID,
                                CMessageBoxActivity.MESSAGEBOX_TYPE_OKONLY,
                                getString( R.string.IDS_APP_NAME ),
                                Html.fromHtml( getString( R.string.IDS_CITY_TABLE_EMPTY_ERROR_MESSAGE ) ),
                                R.drawable.icon_main );
               }
          }
          else
          {
               MessageBox( CApp.MSGBOX_READ_CITIES_ERROR_REQUEST_ID,
                           CMessageBoxActivity.MESSAGEBOX_TYPE_RETRYCANCEL,
                           getString( R.string.IDS_ERROR ),
                           Html.fromHtml( getString( R.string.IDS_READ_CITIES_ERROR_MESSAGE ) ),
                           android.R.drawable.ic_dialog_alert );
          }
     }

     /*********************************************************/
     /*                                                       */ 
     /* CCityListActivity.SelectCity()                        */ 
     /*                                                       */ 
     /*********************************************************/
     public void SelectCity( long CityId )
     {
          if( CityId == -1 ) return;
          
          int iPosition = -1;
          long Id = -1;
          for( int i = 0; i < m_ListView.getCount(); i++ )
          {
               Id = m_ListView.getItemIdAtPosition( i );
               if( Id == CityId )
               {
                    iPosition = i;
                    break;
               }
          }
          
          if( iPosition != -1 )
          {
               if( m_bTablet )
               {
                    m_ListView.performItemClick( m_ListView, iPosition, CityId );
                    m_ListView.setSelection( iPosition );
               }
               else
               {
                    m_ListView.setItemChecked( iPosition, true );
                    int iFirst = m_ListView.getFirstVisiblePosition();
                    int iLast = m_ListView.getLastVisiblePosition();
                    if( iPosition < iFirst || iPosition > iLast ) m_ListView.setSelection( iPosition );
               }
          }
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
          Log.d( CCityListActivity.class.getSimpleName(), "ServiceConnect()" );
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
                              Log.d( CCityListActivity.class.getSimpleName(), "onWeatherLoaded()" );
                              LoadCityList();
                         }
                    } );
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
          Log.d( CCityListActivity.class.getSimpleName(), "ServiceDisconnect()" );
          if( m_ServiceConnection != null ) unbindService( m_ServiceConnection );
          m_ServiceConnection = null;
     }
}
