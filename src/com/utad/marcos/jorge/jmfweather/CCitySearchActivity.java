/**************************************************************/
/*                                                            */
/* CCitySearchActivity.java                                   */
/* (c)2013 jmarcosf                                           */
/*                                                            */
/* Description: CCityListActivity Class                       */
/*              JmfWeather Project                            */
/*              Práctica asignatura Android Fundamental       */ 
/*              U-Tad - Master Apps                           */ 
/*              www.u-tad.com                                 */ 
/*                                                            */ 
/*        Date: December 2013                                 */ 
/*                                                            */
/**************************************************************/
package com.utad.marcos.jorge.jmfweather;

import java.io.IOException;

import org.json.JSONException;

import android.app.Activity;
import android.app.SearchManager;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.utad.marcos.jorge.jmfweather.model.CCityList;
import com.utad.marcos.jorge.jmfweather.utility.CWorldWeatherApi;

/**************************************************************/
/*                                                            */
/*                                                            */
/*                                                            */
/* CCitySearchActivity Class                                  */
/*                                                            */
/*                                                            */
/*                                                            */
/**************************************************************/
public class CCitySearchActivity extends Activity
{
private ListView                   m_ListView;
private CCitySelectionListAdapter  m_Adapter;
private ProgressBar                m_WaitClock;
     
     /*********************************************************/
     /*                                                       */
     /*                                                       */
     /* Activity Override Methods                             */
     /*                                                       */
     /*                                                       */
     /*********************************************************/
     /*                                                       */ 
     /* CCitySearchActivity.onCreate()                        */ 
     /*                                                       */ 
     /*********************************************************/
     @Override
     public void onCreate( Bundle savedInstanceState )
     {
          super.onCreate( savedInstanceState );
          setContentView( R.layout.layout_city_search_activity );
          m_ListView = (ListView)findViewById( R.id.IDC_LV_SELECT_CITY_LIST );
          m_WaitClock = (ProgressBar)findViewById( R.id.IDC_PB_WAIT_CLOCK );
          HandleIntent( getIntent() );
     }

     /*********************************************************/
     /*                                                       */ 
     /* CCitySearchActivity.onNewIntent()                     */ 
     /*                                                       */ 
     /*********************************************************/
     @Override
     protected void onNewIntent( Intent intent )
     {
          HandleIntent(intent);
     }

     /*********************************************************/
     /*                                                       */
     /*                                                       */
     /* Activity Override Methods                             */
     /*                                                       */
     /*                                                       */
     /*********************************************************/
     /*                                                       */ 
     /* CCitySearchActivity.HandleIntent()                    */ 
     /*                                                       */ 
     /*********************************************************/
     private void HandleIntent( Intent intent )
     {
         if( !Intent.ACTION_SEARCH.equals( intent.getAction() ) ) return;
         String Query = intent.getStringExtra( SearchManager.QUERY );
         Toast.makeText( this, "City selected: " + Query, Toast.LENGTH_SHORT ).show();
         new CCityLoader().execute( Query );
     }
     
     /*********************************************************/
     /*                                                       */
     /*                                                       */
     /* CCityListActivity.CCityLoader nested Class            */
     /*                                                       */
     /*                                                       */
     /*********************************************************/
     private class CCityLoader extends AsyncTask< String, Void, CCityList >
     {
          /****************************************************/
          /*                                                  */
          /* CCityLoader.onPreExecute()                       */
          /*                                                  */
          /****************************************************/
          @Override
          protected void onPreExecute()
          {
               m_WaitClock.setVisibility( View.VISIBLE );
          }

          /****************************************************/
          /*                                                  */
          /* CDBLoader.doInBackground()                       */
          /*                                                  */
          /****************************************************/
          @Override
          protected CCityList doInBackground( String... params )
          {
               if( params.length != 1 || params[ 0 ] == null ) return null;
               CWorldWeatherApi WorldWeatherApi = new CWorldWeatherApi();
               CCityList CityList = null;
               try
               {
                    CityList = WorldWeatherApi.SearchCity( params[ 0 ] );
               }
               catch( IOException exception ) { exception.printStackTrace(); }
               catch( JSONException exception ) { exception.printStackTrace(); }
               return CityList;
          }

          /*********************************************************/
          /*                                                       */
          /* CDBLoader.onPostExecute()                             */
          /*                                                       */
          /*********************************************************/
          @Override
          protected void onPostExecute( CCityList CityList )
          {
               m_WaitClock.setVisibility( View.GONE );
               if( CityList != null )
               {
                    m_Adapter = new CCitySelectionListAdapter( CCitySearchActivity.this, CityList );
                    m_ListView.setAdapter( m_Adapter );
               }
//               else m_NoCitiesFound.setVisibility( View.VISIBLE );
          }
     }
}

