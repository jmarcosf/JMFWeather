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
private static Context   m_Context = null;
private static boolean   m_bFirstCall = true;
private static boolean   m_bCelsius = true;
private static int       m_Orientation = -1;
     
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
          CApp.m_bFirstCall = true;
     }
     
     /*********************************************************/
     /*                                                       */ 
     /*                                                       */ 
     /* Class Methods                                         */ 
     /*                                                       */ 
     /*                                                       */ 
     /*********************************************************/
     /*                                                       */ 
     /* CApp.getAppContext()                                  */ 
     /*                                                       */ 
     /*********************************************************/
     
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
     public static Context getAppContext()   { return m_Context; }
     public static boolean getFirstCall()    { return CApp.m_bFirstCall; }
     public static boolean getCelsius()      { return CApp.m_bCelsius; }
     public static int     getOrientation()  { return CApp.m_Orientation; }
     
     /*********************************************************/
     /*                                                       */ 
     /* Class setters                                         */ 
     /*                                                       */ 
     /*********************************************************/
     public static void setFirstCall( boolean bValue ) { CApp.m_bFirstCall = bValue; }
     public static void setCelsius( boolean bValue )   { CApp.m_bCelsius = bValue; }
     public static void setOrientation( int iValue )   { CApp.m_Orientation = iValue; }
}