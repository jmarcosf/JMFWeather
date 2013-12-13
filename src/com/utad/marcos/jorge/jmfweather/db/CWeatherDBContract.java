/**************************************************************/
/*                                                            */
/* CWeatherDBContract.java                                    */
/* (c)2013 jmarcosf                                           */
/*                                                            */
/* Description: CWeatherDBContract Class                      */
/*              JmfWeather Project                            */
/*                                                            */
/**************************************************************/
package com.utad.marcos.jorge.jmfweather.db;

import android.provider.BaseColumns;

/**************************************************************/
/*                                                            */
/*                                                            */
/*                                                            */
/* CWeatherDBContract Class                                   */
/*                                                            */
/*                                                            */
/*                                                            */
/**************************************************************/
public class CWeatherDBContract
{
	/*********************************************************/
	/*                                                       */
	/*                                                       */
	/* Class Constructors                                    */
	/*                                                       */
	/*                                                       */
	/*********************************************************/
	/*                                                       */
	/* CWeatherDBContract.CWeatherDbContract()               */
	/* private constructor: no instances allowed             */
	/*                                                       */
	/*********************************************************/
	private CWeatherDBContract() {}
	
	/*********************************************************/
	/*                                                       */
	/*                                                       */
	/* Database Schema Classes                               */
	/*                                                       */
	/*                                                       */
	/*********************************************************/
	/*                                                       */
	/* CCWeatherDBContract.CCityTable()                      */
	/*                                                       */
	/*********************************************************/
	public static abstract class CCityTable implements BaseColumns
	{
		public static final String TABLE_NAME 							= "City";
		public static final String COLUMN_NAME_NAME 						= "Name";
		public static final String COLUMN_NAME_COUNTRY					= "Country";
		public static final String COLUMN_NAME_LATITUDE					= "Latitude";
		public static final String COLUMN_NAME_LONGITUDE					= "Longitude";
		public static final String COLUMN_NAME_POPULATION					= "Population";
		public static final String COLUMN_NAME_REGION					= "Region";
		public static final String COLUMN_NAME_URL						= "Url";
	}
	
	/*********************************************************/
	/*                                                       */
	/* CWeatherDBContract.CConditionTable()                  */
	/*                                                       */
	/*********************************************************/
	public static abstract class CConditionTable implements BaseColumns
	{
		public static final String TABLE_NAME 							= "Condition";
		public static final String COLUMN_NAME_CITY_ID					= "CityId";
		public static final String COLUMN_NAME_CLOUD_COVERAGE				= "CouldCoverage";
		public static final String COLUMN_NAME_OBSERVATION_TIME			= "ObservationTime";
		public static final String COLUMN_NAME_PRESSURE					= "Pressure";
		public static final String COLUMN_NAME_TEMPERATURE_CELSIUS			= "TemperatureCelsius";
		public static final String COLUMN_NAME_VISIBILITY					= "Visibility";
		public static final String COLUMN_NAME_TEMPERATURE_FAHRENHEIT		= "TemperatureFahrenheit";
		public static final String COLUMN_NAME_WIND_SPEED_MPH				= "WindSpeedMph";
		public static final String COLUMN_NAME_PRECIPITATION				= "Precipitation";
		public static final String COLUMN_NAME_WIND_DIRECTION_DEGREES		= "WindDirectionDegrees";
		public static final String COLUMN_NAME_WIND_DIRECTION_COMPASS		= "WindDirectionCompass";
		public static final String COLUMN_NAME_ICON_URL					= "IconUrl";
		public static final String COLUMN_NAME_HUMIDITY					= "Humidity";
		public static final String COLUMN_NAME_WIND_SPEED_KMPH				= "WindSpeedKmph";
		public static final String COLUMN_NAME_WEATHER_CODE				= "WeatherCode";
		public static final String COLUMN_NAME_WEATHER_DESCRIPTION			= "WeatherDescription";
	}
	
	/*********************************************************/
	/*                                                       */
	/* CWeatherDBContract.CForecastTable()                   */
	/*                                                       */
	/*********************************************************/
	public static abstract class CForecastTable implements BaseColumns
	{
		public static final String TABLE_NAME 							= "Forecast";
		public static final String COLUMN_NAME_CITY_ID					= "CityId";
		public static final String COLUMN_NAME_ICON_URL					= "IconUrl";
		public static final String COLUMN_NAME_MIN_TEMPERATURE_CELSIUS		= "MinTemperatureCelsius";
		public static final String COLUMN_NAME_WIND_SPEED_MPH				= "WindSpeedMph";
		public static final String COLUMN_NAME_WIND_SPEED_KMPH				= "WindSpeedKmph";
		public static final String COLUMN_NAME_WIND_DIRECTION				= "WindDirection";
		public static final String COLUMN_NAME_MAX_TEMPERATURE_CELSIUS		= "MaxTemperatureCelsius";
		public static final String COLUMN_NAME_FORECAST_DATE				= "ForecastDate";
		public static final String COLUMN_NAME_WEATHER_CODE				= "WeatherCode";
		public static final String COLUMN_NAME_MAX_TEMPERATURE_FAHRENHEIT	= "MaxTemperatureFahrenheit";
		public static final String COLUMN_NAME_PRECIPITATION				= "Precipitation";
		public static final String COLUMN_NAME_WIND_DIRECTION_DEGREES		= "WindDirectionDegrees";
		public static final String COLUMN_NAME_WIND_DIRECTION_COMPASS		= "WindDirectionCompass";
		public static final String COLUMN_NAME_WEATHER_DESCRIPTION			= "WeatherDescription";
		public static final String COLUMN_NAME_MIN_TEMPERATURE_FAHRENHEIT	= "MinTemperatureFahrenheit";
	}
}
