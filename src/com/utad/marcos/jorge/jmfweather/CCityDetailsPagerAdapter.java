/**************************************************************/
/*                                                            */
/* CCityDetailsPagerAdapter.java                              */
/* (c)2013 jmarcosf                                           */
/*                                                            */
/* Description: CCityDetailsPagerAdapter Class                */
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

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

/**************************************************************/
/*                                                            */
/*                                                            */
/*                                                            */
/* CCityDetailsPagerAdapter Class                             */
/*                                                            */
/*                                                            */
/*                                                            */
/**************************************************************/
public class CCityDetailsPagerAdapter extends FragmentStatePagerAdapter
{
private ArrayList< Long >     m_CityIdList;     
	
     /*********************************************************/
     /*                                                       */ 
     /*                                                       */ 
     /* Constructors                                          */ 
     /*                                                       */ 
     /*                                                       */ 
     /*********************************************************/
	/*                                                       */ 
	/* CCityDetailsPagerAdapter.CCityDetailsPagerAdapter()   */ 
	/*                                                       */ 
	/*********************************************************/
     public CCityDetailsPagerAdapter( FragmentManager fragmentMgr, ArrayList< Long > CityIdList  )
     {
	     super( fragmentMgr );
	     this.m_CityIdList = CityIdList;
     }

     /*********************************************************/
     /*                                                       */ 
     /*                                                       */ 
     /* FragmentStatePagerAdapter Override Methods            */ 
     /*                                                       */ 
     /*                                                       */ 
     /*********************************************************/
	/*                                                       */
	/* CCityDetailsPagerAdapter.getItem()                    */
	/*                                                       */
	/*********************************************************/
	@Override
	public Fragment getItem( int iPosition )
	{
		CCityDetailsFragment Fragment = new CCityDetailsFragment();
		Bundle Params = new Bundle();
		long CityId = m_CityIdList.get( iPosition ).longValue();
		Params.putLong( CCityDetailsActivity.IDS_CITY_ID_PARAM, CityId );
		Fragment.setArguments( Params );
		return Fragment;
	}

	/*********************************************************/
	/*                                                       */
	/* CCityDetailsPagerAdapter.getCount()                   */
	/*                                                       */
	/*********************************************************/
	@Override
	public int getCount()
	{
	     return m_CityIdList.size();
	}
	
	/*********************************************************/
	/*                                                       */ 
	/* CCityDetailsPagerAdapter.getItemPosition()            */ 
	/*                                                       */ 
	/*********************************************************/
     @Override
	public int getItemPosition( Object item )
     {
          return POSITION_NONE;
     }	
}
