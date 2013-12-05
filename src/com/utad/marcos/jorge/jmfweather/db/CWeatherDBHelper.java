/**************************************************************/
/*                                                            */
/* CWeatherDBHelper.java                                      */
/* (c)2013 jmarcosf                                           */
/*                                                            */
/* Description: CWeatherDBHelper Class                        */
/*              JmfWeather Project                            */
/*                                                            */
/**************************************************************/
package com.utad.marcos.jorge.jmfweather.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**************************************************************/
/*                                                            */ 
/*                                                            */ 
/*                                                            */ 
/* CWeatherDBHelper Class                                     */ 
/*                                                            */ 
/*                                                            */ 
/*                                                            */ 
/**************************************************************/
public class CWeatherDBHelper extends SQLiteOpenHelper
{
public static final String	DATABASE_NAME		= "JmfWeather.db";
public static final int		DATABASE_VERSION	= 1;

	/*********************************************************/
	/* Create Table SQL statements                           */
	/*********************************************************/
	private static final String SQL_JMFWEATHER_CREATE_CITY_TABLE_STMT = "CREATE TABLE " + 
		CWeatherDBContract.CCityTable.TABLE_NAME + 
	" (" +
		CWeatherDBContract.CCityTable._ID									+	" INTEGER PRIMARY KEY AUTOINCREMENT, " +
		CWeatherDBContract.CCityTable.COLUMN_NAME_NAME						+ 	" TEXT, " +
		CWeatherDBContract.CCityTable.COLUMN_NAME_COUNTRY						+ 	" TEXT, " +
		CWeatherDBContract.CCityTable.COLUMN_NAME_LATITUDE					+ 	" TEXT, " +
		CWeatherDBContract.CCityTable.COLUMN_NAME_LONGITUDE					+ 	" TEXT, " +
		CWeatherDBContract.CCityTable.COLUMN_NAME_POPULATION					+ 	" LONG, " +
		CWeatherDBContract.CCityTable.COLUMN_NAME_REGION						+ 	" TEXT, " +
		CWeatherDBContract.CCityTable.COLUMN_NAME_URL						+ 	" TEXT"   +
	" );";

	private static final String SQL_JMFWEATHER_CREATE_CONDITION_TABLE_STMT = "CREATE TABLE " + 
		CWeatherDBContract.CConditionTable.TABLE_NAME + 
	" (" +
		CWeatherDBContract.CConditionTable._ID								+	" INTEGER PRIMARY KEY AUTOINCREMENT, " +
		CWeatherDBContract.CConditionTable.COLUMN_NAME_CITY_ID					+ 	" INTEGER UNIQUE, " +
		CWeatherDBContract.CConditionTable.COLUMN_NAME_CLOUD_COVERAGE			+ 	" INTEGER, " 	+
		CWeatherDBContract.CConditionTable.COLUMN_NAME_OBSERVATION_TIME			+ 	" LONG, " 	+
		CWeatherDBContract.CConditionTable.COLUMN_NAME_PRESSURE				+ 	" INTEGER, " 	+
		CWeatherDBContract.CConditionTable.COLUMN_NAME_TEMPERATURE_CELSIUS		+ 	" INTEGER, " 	+
		CWeatherDBContract.CConditionTable.COLUMN_NAME_VISIBILITY				+ 	" INTEGER, " 	+
		CWeatherDBContract.CConditionTable.COLUMN_NAME_TEMPERATURE_FAHRENHEIT		+ 	" INTEGER, "  	+
		CWeatherDBContract.CConditionTable.COLUMN_NAME_WIND_SPEED_MPH			+ 	" INTEGER, "  	+
		CWeatherDBContract.CConditionTable.COLUMN_NAME_PRECIPITATION			+ 	" REAL, "   	+
		CWeatherDBContract.CConditionTable.COLUMN_NAME_WIND_DIRECTION_DEGREES		+ 	" INTEGER, "  	+
		CWeatherDBContract.CConditionTable.COLUMN_NAME_WIND_DIRECTION_COMPASS		+ 	" TEXT, "   	+
		CWeatherDBContract.CConditionTable.COLUMN_NAME_ICON_URL				+ 	" TEXT, "   	+
		CWeatherDBContract.CConditionTable.COLUMN_NAME_HUMIDITY				+ 	" INTEGER, "  	+
		CWeatherDBContract.CConditionTable.COLUMN_NAME_WIND_SPEED_KMPH			+ 	" INTEGER, "  	+
		CWeatherDBContract.CConditionTable.COLUMN_NAME_WEATHER_CODE				+ 	" INTEGER, "  	+
		CWeatherDBContract.CConditionTable.COLUMN_NAME_WEATHER_DESCRIPTION		+ 	" TEXT"   	+
	" );";

	private static final String SQL_JMFWEATHER_CREATE_FORECAST_TABLE_STMT = "CREATE TABLE " + 
		CWeatherDBContract.CForecastTable.TABLE_NAME + 
	" (" +
		CWeatherDBContract.CForecastTable._ID								+	" INTEGER PRIMARY KEY AUTOINCREMENT, " +
		CWeatherDBContract.CForecastTable.COLUMN_NAME_CITY_ID					+ 	" INTEGER, " 	+
		CWeatherDBContract.CForecastTable.COLUMN_NAME_ICON_URL					+ 	" TEXT, "   	+
		CWeatherDBContract.CForecastTable.COLUMN_NAME_MIN_TEMPERATURE_CELSIUS		+ 	" INTEGER, " 	+
		CWeatherDBContract.CForecastTable.COLUMN_NAME_WIND_SPEED_MPH			+ 	" INTEGER, "  	+
		CWeatherDBContract.CForecastTable.COLUMN_NAME_WIND_SPEED_KMPH			+ 	" INTEGER, "  	+
		CWeatherDBContract.CForecastTable.COLUMN_NAME_WIND_DIRECTION			+ 	" TEXT, "   	+
		CWeatherDBContract.CForecastTable.COLUMN_NAME_MAX_TEMPERATURE_CELSIUS		+ 	" INTEGER, " 	+
		CWeatherDBContract.CForecastTable.COLUMN_NAME_FORECAST_DATE				+ 	" LONG, " 	+
		CWeatherDBContract.CForecastTable.COLUMN_NAME_WEATHER_CODE				+ 	" INTEGER, "  	+
		CWeatherDBContract.CForecastTable.COLUMN_NAME_MAX_TEMPERATURE_FAHRENHEIT	+ 	" INTEGER, "  	+
		CWeatherDBContract.CForecastTable.COLUMN_NAME_PRECIPITATION				+ 	" REAL, "   	+
		CWeatherDBContract.CForecastTable.COLUMN_NAME_WIND_DIRECTION_DEGREES		+ 	" INTEGER, "  	+
		CWeatherDBContract.CForecastTable.COLUMN_NAME_WIND_DIRECTION_COMPASS		+ 	" TEXT, "   	+
		CWeatherDBContract.CForecastTable.COLUMN_NAME_WEATHER_DESCRIPTION		+ 	" TEXT, "   	+
		CWeatherDBContract.CForecastTable.COLUMN_NAME_MIN_TEMPERATURE_FAHRENHEIT	+ 	" INTEGER"   	+
	" );";

	/*********************************************************/
	/* Drop Table SQL statements                             */
	/*********************************************************/
	private static final String SQL_JMFWEATHER_DROP_CITY_TABLE_STMT = 
		"DROP TABLE IF EXISTS " + CWeatherDBContract.CCityTable.TABLE_NAME + ";";

	private static final String SQL_JMFWEATHER_DROP_CONDITION_TABLE_STMT = 
			"DROP TABLE IF EXISTS " + CWeatherDBContract.CConditionTable.TABLE_NAME + ";";
		
	private static final String SQL_JMFWEATHER_DROP_FORECAST_TABLE_STMT = 
			"DROP TABLE IF EXISTS " + CWeatherDBContract.CForecastTable.TABLE_NAME + ";";
		
	/*********************************************************/
	/*                                                       */ 
	/*                                                       */ 
	/* Class Constructors                                    */ 
	/*                                                       */ 
	/*                                                       */ 
	/*********************************************************/
	/*                                                       */ 
	/* CWeatherDBHelper.CWeatherDBHelper()                   */ 
	/*                                                       */ 
	/*********************************************************/
     public CWeatherDBHelper( Context context )
     {
	     super( context, DATABASE_NAME, null, DATABASE_VERSION );
     }

	/*********************************************************/
	/*                                                       */ 
	/*                                                       */ 
	/* SQLiteOpenHelper Override Methods                     */ 
	/*                                                       */ 
	/*                                                       */ 
	/*********************************************************/
	/*                                                       */
	/* CWeatherDBHelper.onCreate()                           */
	/*                                                       */
	/*********************************************************/
	@Override
	public void onCreate( SQLiteDatabase db )
	{
		db.execSQL( SQL_JMFWEATHER_CREATE_CITY_TABLE_STMT );
		db.execSQL( SQL_JMFWEATHER_CREATE_CONDITION_TABLE_STMT );
		db.execSQL( SQL_JMFWEATHER_CREATE_FORECAST_TABLE_STMT );
	}

	/*********************************************************/
	/*                                                       */
	/* CWeatherDBHelper.onUpgrade()                          */
	/*                                                       */
	/*********************************************************/
	@Override
	public void onUpgrade( SQLiteDatabase db, int oldVersion, int newVersion )
	{
		db.execSQL( SQL_JMFWEATHER_DROP_CITY_TABLE_STMT );
		db.execSQL( SQL_JMFWEATHER_DROP_CONDITION_TABLE_STMT );
		db.execSQL( SQL_JMFWEATHER_DROP_FORECAST_TABLE_STMT );
	}
}
