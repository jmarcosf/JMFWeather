/**************************************************************/
/*                                                            */
/* CWidgetProvider.java                                       */
/* (c)2014 jmarcosf                                           */ 
/*                                                            */ 
/* Description: CWidgetProvider Class                         */ 
/*              JmfWeather Project                            */ 
/*              Práctica asignatura Android Fundamental       */ 
/*              U-Tad - Master Apps                           */ 
/*              www.u-tad.com                                 */ 
/*                                                            */ 
/*        Date: December 2014                                 */ 
/*                                                            */ 
/**************************************************************/
package com.utad.marcos.jorge.jmfweather;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.widget.RemoteViews;

import com.utad.marcos.jorge.jmfweather.db.CWeatherDAO;
import com.utad.marcos.jorge.jmfweather.model.CCity;

/**************************************************************/
/*                                                            */ 
/*                                                            */ 
/*                                                            */ 
/* CWidgetProvider Class                                      */ 
/*                                                            */ 
/*                                                            */ 
/*                                                            */ 
/**************************************************************/
public class CWidgetProvider extends AppWidgetProvider
{
     
     /*********************************************************/
     /*                                                       */ 
     /*                                                       */ 
     /* AppWidgetProvider Override Methods                    */ 
     /*                                                       */ 
     /*                                                       */ 
     /*********************************************************/
     /*                                                       */ 
     /* CWidgetProvider.onReceive()                           */ 
     /*                                                       */ 
     /*********************************************************/
     @Override
     public void onReceive( Context context, Intent intent )
     {
          Log.i( CWidgetProvider.class.getSimpleName(), "OnReceive()" );
          super.onReceive( context, intent );
     }
     
     /*********************************************************/
     /*                                                       */ 
     /* CWidgetProvider.onUpdate()                            */ 
     /*                                                       */ 
     /*********************************************************/
     @Override
     public void onUpdate( Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds )
     {
          Log.i( CWidgetProvider.class.getSimpleName(), "OnUpdate()" );
          CWeatherDAO WeatherDAO = new CWeatherDAO( context );
          long CityId = WeatherDAO.GetCurrentLocationId();
          if( CityId != -1 )
          {
               CCity City = WeatherDAO.SelectCity( CityId );
               WeatherDAO.SetCityCondition( City );
                              
               RemoteViews remoteViews = new RemoteViews( context.getPackageName(), R.layout.layout_widget_short );
               
               Bitmap bitmap = City.GetViewIcon( context );
               if( bitmap != null ) remoteViews.setImageViewBitmap( R.id.IDP_ICO_CITY_ICON, bitmap );
               else remoteViews.setImageViewResource( R.id.IDP_ICO_CITY_ICON, R.drawable.icon_main );

               int DegreesUnit = CApp.getWeatherDegreesUnit();
               String TempText = null;
               TempText = ( DegreesUnit == 1 ) ? "" + City.getCondition().getTemperatureCelsius() + "ºC" : "" + City.getCondition().getTemperatureFahrenheit() + "ºF";
               remoteViews.setTextViewText( R.id.IDC_TXT_CITY_TEMPERATURE, TempText );
               
               remoteViews.setTextViewText( R.id.IDC_TXT_CITY_NAME, City.getName() );

               Intent intent = new Intent( context, CCityListActivity.class );
               PendingIntent pendingIntent = PendingIntent.getActivity( context, 0, intent, 0 );
               remoteViews.setOnClickPendingIntent( R.id.IDR_LAY_WIDGET, pendingIntent );
               appWidgetManager.updateAppWidget( appWidgetIds[ 0 ], remoteViews );
          }
     }
     
     /*********************************************************/
     /*                                                       */ 
     /* CWidgetProvider.onAppWidgetOptionsChanged()           */ 
     /*                                                       */ 
     /*********************************************************/
     @Override
     public void onAppWidgetOptionsChanged( Context context, AppWidgetManager appWidgetManager, int appWidgetId, Bundle Params )
     {
          Log.i( CWidgetProvider.class.getSimpleName(), "OnAppOptionschanged()" );
          int MinWidth = Params.getInt( AppWidgetManager.OPTION_APPWIDGET_MIN_WIDTH, -1 );
          int MaxWidth = Params.getInt( AppWidgetManager.OPTION_APPWIDGET_MAX_WIDTH, -1 );
          Log.i( CWidgetProvider.class.getSimpleName(), "Min: "+ MinWidth );
          Log.i( CWidgetProvider.class.getSimpleName(), "Max: "+ MaxWidth );
          
          super.onAppWidgetOptionsChanged( context, appWidgetManager, appWidgetId, Params );
     }
}
