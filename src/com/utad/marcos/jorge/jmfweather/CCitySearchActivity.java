/**************************************************************/
/*                                                            */
/* CCitySearchActivity.java                                   */
/* (c)2013 jmarcosf                                           */
/*                                                            */
/* Description: CCitySearchActivity Class                     */
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
import java.text.ParseException;

import org.json.JSONException;

import android.app.Activity;
import android.app.Dialog;
import android.app.SearchManager;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;

import com.utad.marcos.jorge.jmfweather.db.CWeatherDAO;
import com.utad.marcos.jorge.jmfweather.model.CCity;
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
public class CCitySearchActivity extends Activity implements OnItemClickListener, OnCancelListener
{
private   CWeatherDAO         m_WeatherDAO = null;
private   Dialog              m_Dialog;
private   View                m_Frame;
private   TextView            m_Message;
private   ListView            m_ListView;
private   CCitySearchAdapter  m_Adapter;
private   CCityList           m_CityList;
     
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
          m_WeatherDAO = new CWeatherDAO( this );
          m_CityList = null;
          CApp.setSelectedCityId( -1 );
          
          m_Dialog = new Dialog( this );
          m_Dialog.setContentView( R.layout.layout_city_search_activity );
          m_Dialog.setCanceledOnTouchOutside( true );
          m_Dialog.setOnCancelListener( this );
     
          m_ListView = (ListView)m_Dialog.findViewById( R.id.IDC_LV_SEARCH_CITY_LIST );
          m_ListView.setChoiceMode( ListView.CHOICE_MODE_SINGLE );
          m_ListView.setOnItemClickListener( this );
          
          m_Frame = m_Dialog.findViewById( R.id.IDR_DLG_SEARCH );
          m_Message = (TextView)m_Dialog.findViewById( R.id.IDC_TXT_MESSAGE );
     
          HandleIntent( getIntent() );
     }

     /*********************************************************/
     /*                                                       */ 
     /* CCitySearchActivity.onDestroy()                       */ 
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
     /* CCitysearchActivity.onActivityResult()                */ 
     /*                                                       */ 
     /*********************************************************/
     @Override
     protected void onActivityResult( int RequestCode, int ResultCode, Intent intent )
     {
          switch( RequestCode )
          {
               case CApp.MSGBOX_NO_CITIES_FOUND_ERROR_REQUEST_ID:
               case CApp.MSGBOX_INSERT_CITY_ERROR_REQUEST_ID:
                    m_Dialog.cancel();
                    finish();
                    break;
                    
               default:
                    super.onActivityResult( RequestCode, ResultCode, intent );
                    break;
          }
     }
     
     /*********************************************************/
     /*                                                       */
     /*                                                       */
     /* OnItemClickListener Interface Methods                 */
     /*                                                       */
     /*                                                       */
     /*********************************************************/
     /*                                                       */ 
     /* CCitySearchActivity.onItemClick()                     */ 
     /*                                                       */ 
     /*********************************************************/
     @Override
     public void onItemClick( AdapterView< ? > ParentView, View view, int iPosition, long id )
     {
          CCity City = m_CityList.getCityList().get( iPosition );
          m_Dialog.setTitle( R.string.IDS_WRITING_CITY_TITLE );
          m_Message.setText( Html.fromHtml( getString( R.string.IDS_WRITING_CITY_TEXT, City.getName() ) ) );
          
          long CityId = m_WeatherDAO.GetCityId( City );
          if( CityId != -1 )
          {
               CApp.setSelectedCityId( CityId );
               m_Dialog.cancel();
               finish();
          }
          else new CInsertCity().execute( City );
     }
     
     /*********************************************************/
     /*                                                       */ 
     /*                                                       */ 
     /* OnCancelListener Interface Methods                    */ 
     /*                                                       */ 
     /*                                                       */ 
     /*********************************************************/
     /*                                                       */ 
     /* CCitySearchActivity.onCancel()                        */ 
     /*                                                       */ 
     /*********************************************************/
     @Override
     public void onCancel( DialogInterface arg0 )
     {
          this.finish();
     }
     
     /*********************************************************/
     /*                                                       */
     /*                                                       */
     /* Class Methods                                         */
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
         m_Dialog.setTitle( R.string.IDS_SEARCHING_CITY_TITLE );
         m_Message.setText( Html.fromHtml( getString( R.string.IDS_SEARCHING_CITY_TEXT, Query ) ) );
         m_Dialog.show();
         new CSearchCity().execute( Query );
     }
     
     /*********************************************************/
     /*                                                       */
     /*                                                       */
     /* CCitySearchActivity.CSearchCity AsyncTask Class       */
     /*                                                       */
     /*                                                       */
     /*********************************************************/
     private class CSearchCity extends AsyncTask< String, Void, Void >
     {
          /****************************************************/
          /*                                                  */
          /* CSearchCity.onPreExecute()                       */
          /*                                                  */
          /****************************************************/
          @Override
          protected void onPreExecute()
          {
               m_Frame.setVisibility( View.VISIBLE );
               m_ListView.setVisibility( View.GONE );
               m_CityList = null;
          }

          /****************************************************/
          /*                                                  */
          /* CSearchCity.doInBackground()                     */
          /*                                                  */
          /****************************************************/
          @Override
          protected Void doInBackground( String... params )
          {
               if( params.length != 1 || params[ 0 ] == null ) return null;
               CWorldWeatherApi WorldWeatherApi = new CWorldWeatherApi();
               try
               {
                    m_CityList = WorldWeatherApi.SearchCity( params[ 0 ] );
               }
               catch( IOException exception ) { exception.printStackTrace(); }
               catch( JSONException exception ) { exception.printStackTrace(); }
               return null;
          }

          /****************************************************/
          /*                                                  */
          /* CSearchCity.onPostExecute()                      */
          /*                                                  */
          /****************************************************/
          @Override
          protected void onPostExecute( Void Param )
          {
               m_Frame.setVisibility( View.GONE );
               if( m_CityList != null && m_CityList.getSize() > 0 )
               {
                    m_Adapter = new CCitySearchAdapter( CCitySearchActivity.this, m_CityList );
                    m_ListView.setAdapter( m_Adapter );
                    m_Dialog.setTitle( getString( R.string.IDS_SELECT_CITY ) );
                    m_ListView.setVisibility( View.VISIBLE );
               }
               else
               {
                    Bundle Params = new Bundle();
                    Params.putInt( CMessageBoxActivity.MESSAGEBOX_PARAM_TYPE, CMessageBoxActivity.MESSAGEBOX_TYPE_OKONLY );
                    Params.putString( CMessageBoxActivity.MESSAGEBOX_PARAM_TITLE, getString( R.string.IDS_ERROR ) );
                    String MessageText = getString( R.string.IDS_NO_CITIES_FOUND_ERROR_MESSAGE );
                    Params.putCharSequence( CMessageBoxActivity.MESSAGEBOX_PARAM_MESSAGE, Html.fromHtml( MessageText  ) );
                    Params.putInt( CMessageBoxActivity.MESSAGEBOX_PARAM_ICON_ID, android.R.drawable.ic_dialog_alert );
                    Intent intent = new Intent( CCitySearchActivity.this, CMessageBoxActivity.class );
                    intent.putExtras( Params );
                    startActivityForResult( intent, CApp.MSGBOX_NO_CITIES_FOUND_ERROR_REQUEST_ID );
               }
          }
     }

     /*********************************************************/
     /*                                                       */
     /*                                                       */
     /* CCitySearchActivity.CInsertCity AsyncTask Class       */
     /*                                                       */
     /*                                                       */
     /*********************************************************/
     private class CInsertCity extends AsyncTask< CCity, Void, Long >
     {
          /****************************************************/
          /*                                                  */
          /* CInsertCity.onPreExecute()                       */
          /*                                                  */
          /****************************************************/
          @Override
          protected void onPreExecute()
          {
               m_Frame.setVisibility( View.VISIBLE );
               m_ListView.setVisibility( View.GONE );
               m_CityList = null;
          }

          /****************************************************/
          /*                                                  */
          /* CInsertCity.doInBackground()                     */
          /*                                                  */
          /****************************************************/
          @Override
          protected Long doInBackground( CCity... params )
          {
               if( params.length != 1 || params[ 0 ] == null ) return Long.valueOf( -1 );
               CWorldWeatherApi WorldWeatherApi = new CWorldWeatherApi();
               try
               {
                    CCity City = params[ 0 ];
                    WorldWeatherApi.getCityWeather( City );
                    return Long.valueOf( m_WeatherDAO.Insert( City ) );
               }
               catch( IOException exception )    { exception.printStackTrace(); }
               catch( JSONException exception )  { exception.printStackTrace(); }
               catch( ParseException exception ) { exception.printStackTrace(); }
               return Long.valueOf( -1 );
          }

          /****************************************************/
          /*                                                  */
          /* CInsertCity.onPostExecute()                      */
          /*                                                  */
          /****************************************************/
          @Override
          protected void onPostExecute( Long Param )
          {
               m_Frame.setVisibility( View.GONE );
               CApp.setSelectedCityId( Param.longValue() );
               if( Param.longValue() == -1 )
               {
                    Bundle Params = new Bundle();
                    Params.putInt( CMessageBoxActivity.MESSAGEBOX_PARAM_TYPE, CMessageBoxActivity.MESSAGEBOX_TYPE_OKONLY );
                    Params.putString( CMessageBoxActivity.MESSAGEBOX_PARAM_TITLE, getString( R.string.IDS_ERROR ) );
                    String MessageText = getString( R.string.IDS_WRITE_CITY_ERROR_MESSAGE );
                    Params.putCharSequence( CMessageBoxActivity.MESSAGEBOX_PARAM_MESSAGE, Html.fromHtml( MessageText  ) );
                    Params.putInt( CMessageBoxActivity.MESSAGEBOX_PARAM_ICON_ID, android.R.drawable.ic_dialog_alert );
                    Intent intent = new Intent( CCitySearchActivity.this, CMessageBoxActivity.class );
                    intent.putExtras( Params );
                    startActivityForResult( intent, CApp.MSGBOX_INSERT_CITY_ERROR_REQUEST_ID );
               }
               else
               {
                    m_Dialog.cancel();
                    CCitySearchActivity.this.finish();
               }
          }
     }
}

