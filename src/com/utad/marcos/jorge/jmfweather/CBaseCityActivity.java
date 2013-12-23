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
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

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
          outState.putBoolean( "Celsius", CApp.getCelsius() );
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
          if( savedInstanceState != null ) CApp.setCelsius( savedInstanceState.getBoolean( "Celsius" ) );
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
		          startActivity( intent );
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
			default:
				super.onActivityResult( RequestCode, ResultCode, Data );
				break;
		}
	}
}
