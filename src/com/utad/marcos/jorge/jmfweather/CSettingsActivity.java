/**************************************************************/
/*                                                            */
/* CSettingsActivity.java                                     */
/* (c)2013 jmarcosf                                           */ 
/*                                                            */ 
/* Description: CBaseCityActivity Class                       */ 
/*              JmfWeather Project                            */ 
/*              Práctica asignatura Android Fundamental       */ 
/*              U-Tad - Master Apps                           */ 
/*              www.u-tad.com                                 */ 
/*                                                            */ 
/*        Date: December 2013                                 */ 
/*                                                            */
/**************************************************************/
package com.utad.marcos.jorge.jmfweather;

import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;

import com.utad.marcos.jorge.jmfweather.services.CWeatherRetrieverService;

/**************************************************************/
/*                                                            */
/*                                                            */
/*                                                            */
/* CSettingsActivity Class                                    */
/*                                                            */
/*                                                            */
/*                                                            */
/**************************************************************/
public class CSettingsActivity extends PreferenceActivity
{
public static final int  FLAG_PREF_INCLUDE_CURRENT_LOCALTION_ON_STARTUP    =    0x00000001;     
public static final int  FLAG_PREF_DIVIDE_SCREEN_ON_TABLETS                =    0x00000002;     
public static final int  FLAG_PREF_WEATHER_SYNC_FREQUECY                   =    0x00000004;
public static final int  FLAG_PREF_WEATHER_DEGREES_TYPE                    =    0x00000008;

private static int       ResultCode = 0;

     /*********************************************************/
     /*                                                       */ 
     /*                                                       */ 
     /* Activity Override Methods                             */ 
     /*                                                       */ 
     /*                                                       */ 
     /*********************************************************/
     /*                                                       */ 
     /* CSettingsActivity.onCreate()                          */ 
     /*                                                       */ 
     /*********************************************************/
     @SuppressWarnings( "deprecation" )
     @Override
     protected void onCreate( Bundle savedInstanceState )
     {
          super.onCreate( savedInstanceState );
          
          addPreferencesFromResource( R.xml.preferences );
          
          CheckBoxPreference Preference1 = (CheckBoxPreference)findPreference( "IncludeCurrentLocationOnStartup" );
          Preference1.setOnPreferenceChangeListener( PreferenceChangeListener );

          CheckBoxPreference Preference2 = (CheckBoxPreference)findPreference( "DivideScreenOnTablets" );
          Preference2.setOnPreferenceChangeListener( PreferenceChangeListener );

          ListPreference Preference3 = (ListPreference)findPreference( "WeatherSyncFrequency" );
          Preference3.setOnPreferenceChangeListener( PreferenceChangeListener );
          SetDefaultListSummary( Preference3 );

          ListPreference Preference4 = (ListPreference)findPreference( "WeatherDegreesType" );
          Preference4.setOnPreferenceChangeListener( PreferenceChangeListener );
          SetDefaultListSummary( Preference4 );
          
          ResultCode = 0;
     }

     /*********************************************************/
     /*                                                       */ 
     /* CSettingsActivity.onBackPressed()                     */ 
     /*                                                       */ 
     /*********************************************************/
     @Override
     public void onBackPressed()
     {
          setResult( ResultCode );
          super.onBackPressed();
     }
     
     /*********************************************************/
     /*                                                       */ 
     /*                                                       */ 
     /* Class Methods                                         */ 
     /*                                                       */ 
     /*                                                       */ 
     /*********************************************************/
     /*                                                       */ 
     /* CSettingsActivity.SetDefaultListSummary()             */ 
     /*                                                       */ 
     /*********************************************************/
     private static void SetDefaultListSummary( ListPreference preference )
     {
          PreferenceChangeListener.onPreferenceChange( preference, PreferenceManager.getDefaultSharedPreferences( preference.getContext() ).getString( preference.getKey(), "" ) );
     }

     /*********************************************************/
     /*                                                       */ 
     /*                                                       */ 
     /* Preference Change Callback                            */ 
     /*                                                       */ 
     /*                                                       */ 
     /*********************************************************/
     /*                                                       */ 
     /* CSettingsActivity.PreferenceChangeListener()          */ 
     /*                                                       */ 
     /*********************************************************/
     private static Preference.OnPreferenceChangeListener PreferenceChangeListener = new Preference.OnPreferenceChangeListener()
     {
          public boolean onPreferenceChange( Preference preference, Object value )
          {
               String stringValue = value.toString();
               
               if( preference instanceof ListPreference )
               {
                    ListPreference listPreference = (ListPreference)preference;
                    int index = listPreference.findIndexOfValue( stringValue );
                    preference.setSummary( index >= 0 ? listPreference.getEntries()[ index ] : null );
     
               }
               else preference.setSummary( stringValue );
     
               if( preference.getKey().equals( "WeatherSyncFrequency" ) )
               {
                    int timeout = Integer.parseInt( stringValue ) * 60 * 1000;
                    CWeatherRetrieverService.StartAlarm( CApp.getAppContext(), timeout );
                    ResultCode |= FLAG_PREF_WEATHER_SYNC_FREQUECY;
               }
               else if( preference.getKey().equals( "WeatherDegreesType" ) )
               {
                    ResultCode |= FLAG_PREF_WEATHER_DEGREES_TYPE;
               }
               else if( preference.getKey().equals( "DivideScreenOnTablets" ) )
               {
                    ResultCode |= FLAG_PREF_DIVIDE_SCREEN_ON_TABLETS;
               }
               else if( preference.getKey().equals( "IncludeCurrentLocationOnStartup" ) )
               {
                    ResultCode |= FLAG_PREF_INCLUDE_CURRENT_LOCALTION_ON_STARTUP;
               }
               return true;
          }
      };
}
