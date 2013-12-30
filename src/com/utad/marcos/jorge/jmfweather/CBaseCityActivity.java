/**************************************************************/
/*                                                            */ 
/* CBaseCityActivity.java                                     */ 
/* (c)2013 jmarcosf                                           */ 
/*                                                            */ 
/* Description: CBaseCityActivity Class                       */ 
/*              JmfWeather Project                            */ 
/*              Práctica asignatura Android Fundamental       */ 
/*              U-Tad - Master Apps                           */ 
/*              www.u-tad.com                                 */ 
/*                                                            */ 
/*        Date: December 2013                                 */ 
/*                                                            */ 
/**************************************************************/
package com.utad.marcos.jorge.jmfweather;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.utad.marcos.jorge.jmfweather.db.CWeatherDAO;

/**************************************************************/
/*                                                            */ 
/*                                                            */ 
/*                                                            */ 
/* CBaseCityActivity Class                                    */ 
/*                                                            */ 
/*                                                            */ 
/*                                                            */ 
/**************************************************************/
public class CBaseCityActivity extends ActionBarActivity
{
protected CWeatherDAO    m_WeatherDAO                  = null;
protected boolean        g_bTablet                     = false;
protected boolean        m_bTablet                     = false;
protected int            m_Orientation                 = -1;
protected boolean        m_bCelsius                    = false;
protected boolean        m_bDivideScreeenOnTablets     = false;

     /*********************************************************/
     /*                                                       */ 
     /*                                                       */ 
     /* ActionBarActivity Override Methods                    */ 
     /*                                                       */ 
     /*                                                       */ 
	/*********************************************************/
	/*                                                       */ 
	/* CBaseCityActivity.onCreate()                          */ 
	/*                                                       */ 
	/*********************************************************/
     @Override
     protected void onCreate( Bundle savedInstanceState )
     {
	     super.onCreate( savedInstanceState );
          m_Orientation = getResources().getConfiguration().orientation;
          Log.d( CBaseCityActivity.class.getSimpleName(), ( m_Orientation == Configuration.ORIENTATION_LANDSCAPE ) ? "LANDSCAPE" : "PORTRAIT" );
          m_WeatherDAO = new CWeatherDAO( this );
          g_bTablet = getResources().getBoolean( R.bool.g_bTablet );
          m_bDivideScreeenOnTablets = CApp.IsDivideScreenOnTabletsEnabled();
          m_bTablet = ( g_bTablet && m_bDivideScreeenOnTablets && m_Orientation != Configuration.ORIENTATION_PORTRAIT );
          m_bCelsius = ( CApp.getWeatherDegreesUnit() == 1 );
          getSupportActionBar().setDisplayHomeAsUpEnabled( true );
     }

     /*********************************************************/
     /*                                                       */ 
     /* CBaseCityActivity.onDestroy()                         */ 
     /*                                                       */ 
     /*********************************************************/
     @Override
     protected void onDestroy()
     {
          m_WeatherDAO.Close();
          super.onDestroy();
     }     
     
     /*********************************************************/
	/*                                                       */ 
	/* CBaseCityActivity.onSaveInstanceState()               */ 
	/*                                                       */ 
	/*********************************************************/
     @Override
     protected void onSaveInstanceState( Bundle outState )
     {
          Log.d( CBaseCityActivity.class.getSimpleName(), "OnSaveInstanceState()" );
          outState.putBoolean( "Celsius", m_bCelsius );
          super.onSaveInstanceState( outState );
     }

     /*********************************************************/
     /*                                                       */ 
     /* CBaseCityActivity.onRestoreInstanceState()            */ 
     /*                                                       */ 
     /*********************************************************/
     @Override
     protected void onRestoreInstanceState( Bundle savedInstanceState )
     {
          super.onRestoreInstanceState( savedInstanceState );
          Log.d( CBaseCityActivity.class.getSimpleName(), "OnRestoreInstanceState()" );
          if( savedInstanceState == null ) Log.d( CBaseCityActivity.class.getSimpleName(), "savedInstanceState = null" );
          if( savedInstanceState != null ) m_bCelsius = savedInstanceState.getBoolean( "Celsius" );
     }
     
	/*********************************************************/
	/*                                                       */ 
	/* CBaseCityActivity.onCreateOptionsMenu()               */ 
	/*                                                       */ 
	/*********************************************************/
	@Override
	public boolean onCreateOptionsMenu( Menu menu )
	{
		return super.onCreateOptionsMenu( menu );
	}
	
	/*********************************************************/
	/*                                                       */ 
	/* CBaseCityActivity.onOptionsItemSelected()             */ 
	/*                                                       */ 
	/*********************************************************/
	@Override
	public boolean onOptionsItemSelected( MenuItem Item )
	{
		switch( Item.getItemId() )
		{
               case R.id.IDM_ADD_CITY:
               {
                    onSearchRequested();
                    return true;
               }
     
			case R.id.IDM_SETTINGS:
			     Intent intent = new Intent( this, CSettingsActivity.class );
		          startActivityForResult( intent, CApp.SETTINGS_MODIFY_REQUEST_ID );
			     return true;
	
			default:
				return super.onOptionsItemSelected( Item );
		}
	}
	
	/*********************************************************/
	/*                                                       */ 
	/* CBaseCityActivity.onActivityResult()                  */ 
	/*                                                       */ 
	/*********************************************************/
	@Override
	protected void onActivityResult( int RequestCode, int ResultCode, Intent Data )
	{
		switch( RequestCode )
		{
		     case CApp.SETTINGS_MODIFY_REQUEST_ID:
		          if( ( ResultCode & CSettingsActivity.PREF_FLAG_WEATHER_DEGREES_UNIT ) != 0 )
		          {
		               m_bCelsius = ( CApp.getWeatherDegreesUnit() == 1 );
		          }
                    
		          if( ( ResultCode & CSettingsActivity.PREF_FLAG_DIVIDE_SCREEN_ON_TABLETS ) != 0 )
	               {
		               m_bDivideScreeenOnTablets = CApp.IsDivideScreenOnTabletsEnabled();
	                    m_bTablet = ( g_bTablet && m_bDivideScreeenOnTablets && m_Orientation != Configuration.ORIENTATION_PORTRAIT );
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
     /* Class Methods                                         */ 
     /*                                                       */ 
     /*                                                       */ 
     /*********************************************************/
     /*                                                       */ 
     /* CBaseCityActivity.MessageBox()                        */ 
     /*                                                       */ 
     /*********************************************************/
	public void MessageBox( int RequestId, int Type, CharSequence Title, CharSequence Message, int IconId, long ObjectId, Bundle OptionalParams )
	{
          Intent intent = new Intent( this, CMessageBoxActivity.class );
          Bundle Params = new Bundle();
          Params.putInt( CMessageBoxActivity.MESSAGEBOX_PARAM_TYPE, Type );
          Params.putCharSequence( CMessageBoxActivity.MESSAGEBOX_PARAM_TITLE, Title );
          Params.putCharSequence( CMessageBoxActivity.MESSAGEBOX_PARAM_MESSAGE, Message );
          Params.putInt( CMessageBoxActivity.MESSAGEBOX_PARAM_ICON_ID, IconId );
          Params.putLong( CMessageBoxActivity.MESSAGEBOX_PARAM_OBJECT_ID, ObjectId );
          if( OptionalParams != null ) Params.putAll( OptionalParams );
          intent.putExtras( Params );
          startActivityForResult( intent, RequestId );
	}
	
     /*********************************************************/
     /*                                                       */ 
     /* CBaseCityActivity.MessageBox()                        */ 
     /*                                                       */ 
     /*********************************************************/
     public void MessageBox( int RequestId, int Type, CharSequence Title, CharSequence Message, int IconId, long ObjectId )
     {
          MessageBox( RequestId, Type, Title, Message, IconId, ObjectId, null );
     }
     
     /*********************************************************/
     /*                                                       */ 
     /* CBaseCityActivity.MessageBox()                        */ 
     /*                                                       */ 
     /*********************************************************/
     public void MessageBox( int RequestId, int Type, CharSequence Title, CharSequence Message, int IconId )
     {
          MessageBox( RequestId, Type, Title, Message, IconId, -1, null );
     }
     
     /*********************************************************/
     /*                                                       */ 
     /* CBaseCityActivity.MessageBox()                        */ 
     /*                                                       */ 
     /*********************************************************/
     public void MessageBox( int RequestId, int Type, CharSequence Title, CharSequence Message )
     {
          MessageBox( RequestId, Type, Title, Message, -1, -1, null );
     }
}
                    