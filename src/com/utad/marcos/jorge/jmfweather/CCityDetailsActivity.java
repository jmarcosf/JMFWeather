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
		long cityId = intent.getLongExtra( CCityDetailsFragment.IDS_CITY_ID_PARAM, 0 );
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
          setResult( m_ViewPager.getCurrentItem() );
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
//               case R.id.IDM_DEGREES_CELSIUS:
//                    if( !CApp.getCelsius() )
//                    {
//                         CApp.setCelsius( true );
//                         m_ViewPager.invalidate();
//                         if( m_PagerAdapter != null ) m_PagerAdapter.notifyDataSetChanged();
//                    }
//                    return true;
//
//               case R.id.IDM_DEGREES_FAHRENHEIT:
//                    if( CApp.getCelsius() )
//                    {
//                         CApp.setCelsius( false );
//                         m_ViewPager.invalidate();
//                         m_ViewPager.refreshDrawableState();
//                         if( m_PagerAdapter != null ) m_PagerAdapter.notifyDataSetChanged();
//                    }
//                    return true;
//                    
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