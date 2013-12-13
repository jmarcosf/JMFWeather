/*************************************************************/
/*                                                           */
/* CCityDetailsPagerAdapter                                  */
/* (c)2013 jmarcosf                                          */
/*                                                           */
/* Description: CCityDetailsPagerAdapter Class               */
/*              JmfWeather Project                           */
/*                                                           */
/*************************************************************/
package com.utad.marcos.jorge.jmfweather;
import java.util.ArrayList;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

/*************************************************************/
/*                                                           */
/*                                                           */
/*                                                           */
/* CCityDetailsPagerAdapter Class                            */
/*                                                           */
/*                                                           */
/*                                                           */
/*************************************************************/
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
		Params.putLong( CCityDetailsFragment.IDS_CITY_ID_PARAM, CityId );
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
}
