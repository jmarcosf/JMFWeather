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
          super.onCreate(savedInstanceState);
          
//          PreferenceCategory prefCategory = new PreferenceCategory( this );
//          prefCategory.setTitle( R.string.IDS_GENERAL );
//          getPreferenceScreen().addPreference( prefCategory );
          addPreferencesFromResource( R.xml.preferences );
          
          BindPreferenceSummaryToValue( findPreference( "WeatherSyncFrequency" ) );
          BindPreferenceSummaryToValue( findPreference( "WeatherDegreesType" ) );
     }

     /*********************************************************/
     /*                                                       */ 
     /*                                                       */ 
     /* PreferenceActivity Abstract Methods                   */ 
     /*                                                       */ 
     /*                                                       */ 
     /*********************************************************/
     /*                                                       */ 
     /* CSettingsActivity.onPreferenceChangeListener()        */ 
     /*                                                       */ 
     /*********************************************************/
     private static Preference.OnPreferenceChangeListener sBindPreferenceSummaryToValueListener = new Preference.OnPreferenceChangeListener()
     {
          @Override
          public boolean onPreferenceChange( Preference preference, Object value )
          {
               String stringValue = value.toString();
     
               if( preference instanceof ListPreference )
               {
                    ListPreference listPreference = (ListPreference)preference;
                    int index = listPreference.findIndexOfValue( stringValue );
                    preference.setSummary( index >= 0 ? listPreference.getEntries()[ index ] : null);
     
               }
               else preference.setSummary( stringValue );
     
               if( preference.getKey().equals( "WeatherSyncFrequency" ) )
               {
                    int timeout = Integer.parseInt( stringValue ) * 60 * 1000;
                    CWeatherRetrieverService.StartAlarm( CApp.getAppContext(), timeout );
               }
               else if( preference.getKey().equals( "WeatherDegreesType" ) )
               {
                    int Value = Integer.parseInt( stringValue );
                    CApp.setCelsius( Value == 1 );
               }
               return true;
          }
     };
     
     /*********************************************************/
     /*                                                       */ 
     /*                                                       */ 
     /* Class Methods                                         */ 
     /*                                                       */ 
     /*                                                       */ 
     /*********************************************************/
     /*                                                       */ 
     /* CSettingsActivity.BindPreferenceSummaryToValue()      */ 
     /*                                                       */ 
     /*********************************************************/
     private static void BindPreferenceSummaryToValue( Preference preference )
     {
         preference.setOnPreferenceChangeListener( sBindPreferenceSummaryToValueListener );
         sBindPreferenceSummaryToValueListener.onPreferenceChange(
              preference,
              PreferenceManager
                   .getDefaultSharedPreferences( preference.getContext() )
                   .getString(preference.getKey(), "" ) );
     }
}
