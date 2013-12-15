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
private static Context m_Context = null;
     
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
     /* CApp.getAppContext()                                  */ 
     /*                                                       */ 
     /*********************************************************/
     public static Context getAppContext()
     {
          return m_Context;
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
     /* CApp.IsLoadCurrentLocationOnStartupEnabled()          */ 
     /*                                                       */ 
     /*********************************************************/
     public static boolean IsLoadCurrentLocationOnStartupEnabled()
     {
          SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences( m_Context );
          boolean bEnabled = preferences.getBoolean( "IncludeCurrentLocationOnStartup", true );
          return bEnabled;
     }
}
