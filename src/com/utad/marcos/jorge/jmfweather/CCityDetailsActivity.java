/**************************************************************/
/*                                                            */
/* CCityDetailsActivity.java                                  */
/* (c)2013 jmarcosf                                           */
/*                                                            */
/* Description: CCityDetailsActivity Class                    */
/*              JmfWeather Project                            */
/*              Práctica asignatura Android Fundamental       */ 
/*              U-Tad - Master Apps                           */ 
/*              www.u-tad.com                                 */ 
/*                                                            */ 
/*        Date: December 2013                                 */ 
/*                                                            */ 
/**************************************************************/
package com.utad.marcos.jorge.jmfweather;
import java.util.ArrayList;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v4.app.TaskStackBuilder;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;

/**************************************************************/
/*                                                            */
/*                                                            */
/*                                                            */
/* CCityDetailsActivity Class                                 */ 
/*                                                            */
/*                                                            */
/*                                                            */
/**************************************************************/
public class CCityDetailsActivity extends CBaseCityActivity
{
public static final String         IDS_CITY_ID_PARAM      = "CityIdParam";
public static final String         IDS_CELSIUS_PARAM      = "CelsiusParam";

private   ArrayList< Long >        m_CityIdList;
private   ViewPager                m_ViewPager = null;
private   CCityDetailsPagerAdapter m_PagerAdapter = null;

     /*********************************************************/
     /*                                                       */ 
     /*                                                       */ 
     /* Activity Override Methods                             */ 
     /*                                                       */ 
     /*                                                       */ 
	/*********************************************************/
	/*                                                       */ 
	/* CCityDetailsActivity.onCreate()                       */ 
	/*                                                       */ 
	/*********************************************************/
	@Override
	protected void onCreate( Bundle savedInstanceState )
	{
		super.onCreate( savedInstanceState );
		
		Intent intent = this.getIntent();
		long cityId = intent.getLongExtra( IDS_CITY_ID_PARAM, 0 );
          m_bCelsius = intent.getBooleanExtra( IDS_CELSIUS_PARAM, true );
          setContentView( R.layout.layout_city_details );
		
          int iPosition = 0;
		m_CityIdList = m_WeatherDAO.SelectAllCityIds();
		for( Long CityId : m_CityIdList )
		{
		     if( CityId.longValue() == cityId )
		     {
		          iPosition = m_CityIdList.indexOf( CityId );
		          break;
		     }
		}

		m_ViewPager = (ViewPager)findViewById( R.id.IDR_LAY_CITY_CONTAINER );
		m_PagerAdapter = new CCityDetailsPagerAdapter( getSupportFragmentManager(), m_CityIdList );
		m_ViewPager.setAdapter( m_PagerAdapter );
		m_ViewPager.setCurrentItem( iPosition );
	}
	
	/*********************************************************/
     /*                                                       */ 
     /* CCityDetailsActivity.onDestroy()                      */ 
     /*                                                       */ 
     /*********************************************************/
     @Override
     protected void onDestroy()
     {
          super.onDestroy();
     }
     
     /*********************************************************/
     /*                                                       */ 
     /* CCityDetailsActivity.onBackPressed()                  */ 
     /*                                                       */ 
     /*********************************************************/
     @Override
     public void onBackPressed()
     {
          int iPage = m_ViewPager.getCurrentItem();
          long CityId = m_CityIdList.get( iPage );
          getIntent().putExtra( IDS_CELSIUS_PARAM, m_bCelsius );
          setResult( (int)CityId, getIntent() );
          super.onBackPressed();
     }
     
	/*********************************************************/
	/*                                                       */ 
	/* CCityDetailsActivity.onOptionsItemSelected()          */ 
	/*                                                       */ 
	/*********************************************************/
     @Override
     public boolean onOptionsItemSelected( MenuItem Item )
     {
     	switch( Item.getItemId() )
     	{
               case R.id.IDM_DEGREES_CELSIUS:
                    m_bCelsius = true;
                    if( m_PagerAdapter != null ) m_PagerAdapter.notifyDataSetChanged();
                    return true;

               case R.id.IDM_DEGREES_FAHRENHEIT:
                    m_bCelsius = false;
                    if( m_PagerAdapter != null ) m_PagerAdapter.notifyDataSetChanged();
                    return true;
                    
     		case android.R.id.home:
     			Intent upIntent = NavUtils.getParentActivityIntent( this );
     			if( NavUtils.shouldUpRecreateTask( this, upIntent ) )
     			{
     				TaskStackBuilder.create( this ).addNextIntent( upIntent ).startActivities();
     			}
     			else NavUtils.navigateUpTo( this, upIntent );
     			return true;
     			
     		default:
     			return super.onOptionsItemSelected( Item );
     	}
     }
     
     /*********************************************************/
     /*                                                       */
     /* CCityDetailsActivity.onCreateOptionsMenu()            */
     /*                                                       */
     /*********************************************************/
     @Override
     public boolean onCreateOptionsMenu( Menu menu )
     {
          getMenuInflater().inflate( R.menu.menu_city_details, menu );
          return true;
     }

     /*********************************************************/
     /*                                                       */ 
     /* CCityDetailsActivity.onActivityResult()               */ 
     /*                                                       */ 
     /*********************************************************/
     @Override
     protected void onActivityResult( int RequestCode, int ResultCode, Intent intent )
     {
          switch( RequestCode )
          {
               case CApp.SETTINGS_MODIFY_REQUEST_ID:
                    super.onActivityResult( RequestCode, ResultCode, intent );
                    if( ( ResultCode & CSettingsActivity.PREF_FLAG_WEATHER_DEGREES_UNIT ) != 0 )
                    {
                         if( m_PagerAdapter != null ) m_PagerAdapter.notifyDataSetChanged();
                    }
                    break;
                    
               default:
                    super.onActivityResult( RequestCode, ResultCode, intent );
                    break;
          }
     }
}