/*************************************************************/
/*                                                           */ 
/* CBaseCityActivity                                         */ 
/* (c)2013 jmarcosf                                          */ 
/*                                                           */ 
/* Description: CBaseCityActivity Class                      */ 
/*              BlogReader Project                           */ 
/*                                                           */ 
/*************************************************************/
package com.utad.marcos.jorge.jmfweather;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.utad.marcos.jorge.jmfweather.model.CCity;

/*************************************************************/
/*                                                           */ 
/*                                                           */ 
/*                                                           */ 
/* CBaseCityActivity Class                                   */ 
/*                                                           */ 
/*                                                           */ 
/*                                                           */ 
/*************************************************************/
public class CBaseCityActivity extends ActionBarActivity
{
//private final static String	IDS_SAVED_INSTANCE_CITY_KEY = "SavedCityInstance";

protected CCity               m_City = null;

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
	     if( savedInstanceState != null )
	     {
//	     	m_City = savedInstanceState.getParcelable( IDS_SAVED_INSTANCE_CITY_KEY );
	     }
     }

     /*********************************************************/
	/*                                                       */ 
	/* CBaseCityActivity.onSaveInstanceState()               */ 
	/*                                                       */ 
	/*********************************************************/
     @Override
     protected void onSaveInstanceState( Bundle outState )
     {
     	if( m_City != null )
     	{
//     		outState.putParcelable( IDS_SAVED_INSTANCE_CITY_KEY, m_City );
     	}
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
		// Inflate the menu; this adds items to the action bar if it is present.
		if( m_City != null )
		{
			getMenuInflater().inflate( R.menu.city_details_menu, menu );
			return true;
		}
		else return super.onCreateOptionsMenu( menu );
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
