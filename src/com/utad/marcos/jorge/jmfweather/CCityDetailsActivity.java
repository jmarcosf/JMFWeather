/**************************************************************/
/*                                                            */
/* CCityDetailsActivity.java                                  */
/* (c)2013 jmarcosf                                           */
/*                                                            */
/* Description: CCityDetailsActivity Class                    */
/*              JmfWeather Project                            */
/*              Pr�ctica asignatura Android Fundamental       */ 
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

import com.utad.marcos.jorge.jmfweather.db.CWeatherDAO;

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
private   CWeatherDAO              m_WeatherDAO = null;
private   ArrayList< Long >        m_CityIdList;
private   int                      m_Position;
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
		long cityId = intent.getLongExtra( CCityDetailsFragment.IDS_CITY_ID_PARAM, 0 );
		
		m_WeatherDAO = new CWeatherDAO( this );
		m_CityIdList = m_WeatherDAO.SelectAllCityIds();
		
		m_Position = 0;
		for( Long CityId : m_CityIdList )
		{
		     if( CityId.longValue() == cityId )
		     {
		          m_Position = m_CityIdList.indexOf( CityId );
		          break;
		     }
		}

		setContentView( R.layout.layout_city_details );
		getSupportActionBar().setDisplayHomeAsUpEnabled( true );
		
		// Attach new PagerAdapter to ViewPager
		ViewPager viewPager = (ViewPager)findViewById( R.id.IDR_LAY_CITY_CONTAINER );
		m_PagerAdapter = new CCityDetailsPagerAdapter( getSupportFragmentManager(), m_CityIdList );
		viewPager.setAdapter( m_PagerAdapter );
		viewPager.setCurrentItem( m_Position );
	}
	
	/*********************************************************/
     /*                                                       */ 
     /* CCityDetailsActivity.onDestroy()                      */ 
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
	/* CCityDetailsActivity.onOptionsItemSelected()          */ 
	/*                                                       */ 
	/*********************************************************/
     @Override
     public boolean onOptionsItemSelected( MenuItem Item )
     {
     	switch( Item.getItemId() )
     	{
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

}