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
          
          ( (WebView)findViewById( R.id.IDC_TXT_HELP ) ).loadData( GetHelpText(), "text/html; charset=UTF-8", "UTF-8" );

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

          //if just inserted a new city, selected it
          long CityId = CApp.getNewCityId();
          CApp.setNewCityId( -1 );
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
                         if( m_bTablet )
                         {
                              int SelectedItem = m_ListView.getCheckedItemPosition();
                              if( SelectedItem != -1 ) m_ListView.performItemClick( m_ListView, SelectedItem, m_ListView.getItemIdAtPosition( SelectedItem ) );
                         }
                    }
                    return true;

               case R.id.IDM_DEGREES_FAHRENHEIT:
                    if( CApp.getCelsius() )
                    {
                         CApp.setCelsius( false );
                         if( m_Adapter != null ) m_Adapter.notifyDataSetChanged();
                         if( m_bTablet )
                         {
                              int SelectedItem = m_ListView.getCheckedItemPosition();
                              if( SelectedItem != -1 ) m_ListView.performItemClick( m_ListView, SelectedItem, m_ListView.getItemIdAtPosition( SelectedItem ) );
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
                    m_ListView.setItemChecked( ResultCode, true );
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
               tx.commitAllowingStateLoss();
          }
          else
          {
               Intent intent = new Intent( CCityListActivity.this, CCityDetailsActivity.class );
               intent.putExtra( CCityDetailsFragment.IDS_CITY_ID_PARAM, id );
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
          Intent intent = new Intent( this, CMessageBoxActivity.class );
          intent.putExtra( CMessageBoxActivity.MESSAGEBOX_PARAM_TYPE, CMessageBoxActivity.MESSAGEBOX_TYPE_YESNO );
          intent.putExtra( CMessageBoxActivity.MESSAGEBOX_PARAM_TITLE, getString( R.string.IDS_DELETE_CITY_TITLE ) );
          String Question = getString( R.string.IDS_DELETE_CITY_QUESTION, (String)view.getTag() );
          intent.putExtra( CMessageBoxActivity.MESSAGEBOX_PARAM_TEXT, Html.fromHtml( Question  ) );
          intent.putExtra( CMessageBoxActivity.MESSAGEBOX_PARAM_OBJECT_ID, id );
          intent.putExtra( CMessageBoxActivity.MESSAGEBOX_PARAM_USER_DATA1, Boolean.valueOf( iPosition == m_ListView.getCheckedItemPosition() ) );        
          intent.putExtra( CMessageBoxActivity.MESSAGEBOX_PARAM_USER_DATA2, Integer.valueOf( iPosition ) );        
          startActivityForResult( intent, CApp.MSGBOX_DELETE_CITY_REQUEST_ID );
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
               else
               {
                    Intent intent = new Intent( CCityListActivity.this, CMessageBoxActivity.class );
                    intent.putExtra( CMessageBoxActivity.MESSAGEBOX_PARAM_TYPE, CMessageBoxActivity.MESSAGEBOX_TYPE_OKONLY );
                    intent.putExtra( CMessageBoxActivity.MESSAGEBOX_PARAM_TITLE, getString( R.string.IDS_WARNING ) );
                    String MessageText = getString( R.string.IDS_CITY_TABLE_EMPTY_ERROR_MESSAGE );
                    intent.putExtra( CMessageBoxActivity.MESSAGEBOX_PARAM_TEXT, Html.fromHtml( MessageText  ) );
                    startActivityForResult( intent, CApp.MSGBOX_CITY_TABLE_EMPTY_REQUEST_ID );
               }
          }
          else
          {
               Intent intent = new Intent( CCityListActivity.this, CMessageBoxActivity.class );
               intent.putExtra( CMessageBoxActivity.MESSAGEBOX_PARAM_TYPE, CMessageBoxActivity.MESSAGEBOX_TYPE_RETRYCANCEL );
               intent.putExtra( CMessageBoxActivity.MESSAGEBOX_PARAM_TITLE, getString( R.string.IDS_ERROR ) );
               String MessageText = getString( R.string.IDS_READ_CITIES_ERROR_MESSAGE );
               intent.putExtra( CMessageBoxActivity.MESSAGEBOX_PARAM_TEXT, Html.fromHtml( MessageText  ) );
               startActivityForResult( intent, CApp.MSGBOX_READ_CITIES_ERROR_REQUEST_ID );
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
               if( m_bTablet ) m_ListView.performItemClick( m_ListView, iPosition, CityId );
               else m_ListView.setItemChecked( iPosition, true );
               m_ListView.setSelection( iPosition );
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
