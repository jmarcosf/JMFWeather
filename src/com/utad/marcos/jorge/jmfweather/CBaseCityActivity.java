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
	     super.onSaveInstanceState( outState );
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
			case R.id.IDM_SHARE:
			{
//				Intent intent = new Intent();
//				intent.setAction( Intent.ACTION_SEND );
//				intent.setType( "text/plain" );
//				intent.putExtra( Intent.EXTRA_SUBJECT, m_City.getTitle() );
//				intent.putExtra( Intent.EXTRA_TEXT, m_City.getImageUrl() );
//				startActivity( intent );
				return true;
			}

			case R.id.IDM_SETTINGS:
			     Intent intent = new Intent( this, CSettingsActivity.class );
			     startActivity( intent );
			     return true;
	
			default:
				super.onOptionsItemSelected( Item );
				return false;
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
