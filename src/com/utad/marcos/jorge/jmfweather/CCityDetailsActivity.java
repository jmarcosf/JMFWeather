/*************************************************************/
/*                                                           */
/* CCityDetailsActivity                                      */
/* (c)2013 jmarcosf                                          */
/*                                                           */
/* Description: CCityDetailsActivity Class                   */
/*              JmfWeather Project                           */
/*                                                           */
/*************************************************************/
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

/*************************************************************/
/*                                                           */
/*                                                           */
/*                                                           */
/* CCityDetailsActivity Class                                */ 
/*                                                           */
/*                                                           */
/*                                                           */
/*************************************************************/
public class CCityDetailsActivity extends CBaseCityActivity
{
private   ArrayList< Long >   m_CityIdList;
private   int                 m_Position;

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
		
		CWeatherDAO WeatherDAO = new CWeatherDAO( CCityDetailsActivity.this );
		m_CityIdList = WeatherDAO.SelectAllCityIds();
		WeatherDAO.Close();
		
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
		CCityDetailsPagerAdapter pageAdapter = new CCityDetailsPagerAdapter( getSupportFragmentManager(), m_CityIdList );
		viewPager.setAdapter( pageAdapter );
		viewPager.setCurrentItem( m_Position );
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
          getMenuInflater().inflate( R.menu.city_details_menu, menu );
          return true;
     }

}