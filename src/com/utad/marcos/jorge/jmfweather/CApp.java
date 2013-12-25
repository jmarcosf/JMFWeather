/**************************************************************/
/*                                                            */
/* CApp.java                                                  */
/* (c)2013 jmarcosf                                           */ 
/*                                                            */ 
/* Description: CApp Class                                    */ 
/*              JmfWeather Project                            */ 
/*              Práctica asignatura Android Fundamental       */ 
/*              U-Tad - Master Apps                           */ 
/*              www.u-tad.com                                 */ 
/*                                                            */ 
/*        Date: December 2013                                 */ 
/*                                                            */ 
/**************************************************************/
package com.utad.marcos.jorge.jmfweather;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**************************************************************/
/*                                                            */
/*                                                            */
/* CApp Class                                                 */
/* NOTE: This Class is just to catch Application context      */
/* and use it where there is not other way to acquire it.     */
/*                                                            */
/*                                                            */
/**************************************************************/
public class CApp extends Application
{
public final static int  MSGBOX_READ_CITIES_ERROR_REQUEST_ID               = 100;
public final static int  MSGBOX_CITY_TABLE_EMPTY_REQUEST_ID                = 101;
public final static int  MSGBOX_DELETE_CITY_REQUEST_ID                     = 102;
public final static int  MSGBOX_NO_CITIES_FOUND_ERROR_REQUEST_ID           = 103;
public final static int  MSGBOX_INSERT_CITY_ERROR_REQUEST_ID               = 104;

private static Context   m_Context = null;
private static boolean   m_bCelsius = true;

     /*********************************************************/
     /*                                                       */ 
     /*                                                       */ 
     /* Application Override Methods                          */ 
     /*                                                       */ 
     /*                                                       */ 
     /*********************************************************/
     /*                                                       */ 
     /* CApp.onCreate()                                       */ 
     /*                                                       */ 
     /*********************************************************/
     @Override
     public void onCreate()
     {
          super.onCreate();
          CApp.m_Context = getApplicationContext();
     }
     
     /*********************************************************/
     /*                                                       */ 
     /*                                                       */ 
     /* Class Methods                                         */ 
     /*                                                       */ 
     /*                                                       */ 
     /*********************************************************/
     /*                                                       */ 
     /* CApp.IsLoadCurrentLocationOnStartupEnabled()          */ 
     /*                                                       */ 
     /*********************************************************/
     public static boolean IsLoadCurrentLocationOnStartupEnabled()
     {
          SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences( m_Context );
          boolean bEnabled = preferences.getBoolean( "IncludeCurrentLocationOnStartup", true );
          return bEnabled;
     }
     
     /*********************************************************/
     /*                                                       */ 
     /* CApp.IsDivideScreenOnTabletsEnabled()                 */ 
     /*                                                       */ 
     /*********************************************************/
     public static boolean IsDivideScreenOnTabletsEnabled()
     {
          SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences( m_Context );
          boolean bEnabled = preferences.getBoolean( "DivideScreenOnTablets", false );
          return bEnabled;
     }
     
     /*********************************************************/
     /*                                                       */ 
     /* CApp.getWeatherSyncFrequecyTimeout()                  */ 
     /*                                                       */ 
     /*********************************************************/
     public static int getWeatherSyncFrequecyTimeout()
     {
          SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences( m_Context );
          int DefaultValue = m_Context.getResources().getInteger( R.integer.g_WeatherSyncFrequencyDefault );
          String TimeoutPreference  = preferences.getString( "WeatherSyncFrequency", "" + DefaultValue );
          int Timeout = Integer.parseInt( TimeoutPreference ) * 60 * 1000;
          return Timeout;
     }
     
     /*********************************************************/
     /*                                                       */ 
     /* CApp.getWeatherDegreesType()                          */ 
     /*                                                       */ 
     /*********************************************************/
     public static boolean getWeatherDegreesType()
     {
          SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences( m_Context );
          int DefaultValue = m_Context.getResources().getInteger( R.integer.g_WeatherDegreesTypeDefault );
          String DegreesPreference = preferences.getString( "WeatherDegreesType", "" + DefaultValue );
          int Value = Integer.parseInt( DegreesPreference );
          CApp.m_bCelsius = ( Value == 1 );
          return CApp.m_bCelsius;
     }
     
     /*********************************************************/
     /*                                                       */ 
     /* Class getters                                         */ 
     /*                                                       */ 
     /*********************************************************/
     public static Context getAppContext()   { return CApp.m_Context; }
     public static boolean getCelsius()      { return CApp.m_bCelsius; }
     
     /*********************************************************/
     /*                                                       */ 
     /* Class setters                                         */ 
     /*                                                       */ 
     /*********************************************************/
     public static void setCelsius( boolean bValue )   { CApp.m_bCelsius = bValue; }
}