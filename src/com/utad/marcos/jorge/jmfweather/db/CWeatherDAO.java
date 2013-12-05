/**************************************************************/
/*                                                            */
/* CWeatherDAO.java                                           */
/* (c)2013 jmarcosf                                           */
/*                                                            */
/* Description: CWeatherDAO Class                             */
/*              JmfWeather Project                            */
/*                                                            */
/**************************************************************/
package com.utad.marcos.jorge.jmfweather.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.utad.marcos.jorge.jmfweather.model.CCity;
import com.utad.marcos.jorge.jmfweather.model.CForecast;

/**************************************************************/
/*                                                            */ 
/*                                                            */ 
/* CWeatherDAO Class                                          */ 
/*                                                            */ 
/*                                                            */ 
/**************************************************************/
public class CWeatherDAO
{
private CWeatherDBHelper		m_dbHelper;
private SQLiteDatabase		m_db;

	/********************************************************/
	/*                                                      */ 
	/* CWeatherDAO.CWeatherDAO()                            */ 
	/*                                                      */ 
	/********************************************************/
	public CWeatherDAO( Context context )
	{
		m_dbHelper = new CWeatherDBHelper( context );
	}
	
	/********************************************************/
	/*                                                      */ 
	/* CWeatherDAO.insert()                                 */ 
	/*                                                      */ 
	/********************************************************/
	public long insert( CCity City ) 
	{
		if( m_db == null || m_db.isReadOnly() )
		{
			this.close();
			m_db = m_dbHelper.getWritableDatabase();
		}
		
		long RecordId = -1;
		m_db.beginTransaction();
		
		ContentValues CityRecord = new ContentValues();
		CityRecord.put( CWeatherDBContract.CCityTable.COLUMN_NAME_NAME, 			City.getName() );
		CityRecord.put( CWeatherDBContract.CCityTable.COLUMN_NAME_COUNTRY, 		City.getCountry() );
		CityRecord.put( CWeatherDBContract.CCityTable.COLUMN_NAME_LATITUDE, 		City.getLatitude() );
		CityRecord.put( CWeatherDBContract.CCityTable.COLUMN_NAME_LONGITUDE,		City.getLongitude() );
		CityRecord.put( CWeatherDBContract.CCityTable.COLUMN_NAME_POPULATION,		City.getPopulation() );
		CityRecord.put( CWeatherDBContract.CCityTable.COLUMN_NAME_URL, 			City.getWeatherUrl() );

		try
		{
			RecordId = m_db.insert( CWeatherDBContract.CCityTable.TABLE_NAME, null, CityRecord );
			if( RecordId == -1 )
			{
				ContentValues ConditionRecord = new ContentValues();
				ConditionRecord.put( CWeatherDBContract.CConditionTable.COLUMN_NAME_CITY_ID, 				RecordId );
				ConditionRecord.put( CWeatherDBContract.CConditionTable.COLUMN_NAME_CLOUD_COVERAGE, 			City.getCondition().getCloudCoverPercentage() );
				ConditionRecord.put( CWeatherDBContract.CConditionTable.COLUMN_NAME_OBSERVATION_TIME,		City.getCondition().getObservationTime() == null ? null : City.getCondition().getObservationTime().getTime() );
				ConditionRecord.put( CWeatherDBContract.CConditionTable.COLUMN_NAME_PRESSURE, 				City.getCondition().getPressure() );
				ConditionRecord.put( CWeatherDBContract.CConditionTable.COLUMN_NAME_TEMPERATURE_CELSIUS, 		City.getCondition().getTemperatureCelsius() );
				ConditionRecord.put( CWeatherDBContract.CConditionTable.COLUMN_NAME_VISIBILITY, 			City.getCondition().getVisibility() );			
				ConditionRecord.put( CWeatherDBContract.CConditionTable.COLUMN_NAME_TEMPERATURE_FAHRENHEIT,	City.getCondition().getTemperatureFahrenheit() );
				ConditionRecord.put( CWeatherDBContract.CConditionTable.COLUMN_NAME_WIND_SPEED_MPH,			City.getCondition().getWindSpeedMph() );
				ConditionRecord.put( CWeatherDBContract.CConditionTable.COLUMN_NAME_PRECIPITATION, 			City.getCondition().getPrecipitation() );
				ConditionRecord.put( CWeatherDBContract.CConditionTable.COLUMN_NAME_WIND_DIRECTION_DEGREES, 	City.getCondition().getWindDirectionDegrees() );
				ConditionRecord.put( CWeatherDBContract.CConditionTable.COLUMN_NAME_WIND_DIRECTION_COMPASS, 	City.getCondition().getWindDirectionCompass() );
				ConditionRecord.put( CWeatherDBContract.CConditionTable.COLUMN_NAME_ICON_URL, 				City.getCondition().getIconUrl() );
				ConditionRecord.put( CWeatherDBContract.CConditionTable.COLUMN_NAME_HUMIDITY, 				City.getCondition().getHumidity() );
				ConditionRecord.put( CWeatherDBContract.CConditionTable.COLUMN_NAME_WIND_SPEED_KMPH, 		City.getCondition().getWindSpeedKmph() );
				ConditionRecord.put( CWeatherDBContract.CConditionTable.COLUMN_NAME_WEATHER_CODE, 			City.getCondition().getWeatherCode() );
				ConditionRecord.put( CWeatherDBContract.CConditionTable.COLUMN_NAME_WEATHER_DESCRIPTION, 		City.getCondition().getWeatherDescription() );
	
				m_db.insert( CWeatherDBContract.CConditionTable.TABLE_NAME, null, ConditionRecord );
				m_db.setTransactionSuccessful();
			}
		}
		finally
		{
			m_db.endTransaction();
		}

		return RecordId;
	}

	/********************************************************/
	/*                                                      */ 
	/* CWeatherDAO.insert()                                 */ 
	/*                                                      */ 
	/********************************************************/
	public long insert( CForecast Forecast )
	{
		if( m_db == null || m_db.isReadOnly() )
		{
			this.close();
			m_db = m_dbHelper.getWritableDatabase();
		}
		
		ContentValues ForecastRecord = new ContentValues();
		ForecastRecord.put( CWeatherDBContract.CForecastTable.COLUMN_NAME_CITY_ID, 				Forecast.getCityId() );
		ForecastRecord.put( CWeatherDBContract.CForecastTable.COLUMN_NAME_ICON_URL, 				Forecast.getIconUrl() );
		ForecastRecord.put( CWeatherDBContract.CForecastTable.COLUMN_NAME_MIN_TEMPERATURE_CELSIUS, 	Forecast.getMinTemperatureCelsius() );
		ForecastRecord.put( CWeatherDBContract.CForecastTable.COLUMN_NAME_WIND_SPEED_MPH,			Forecast.getWindSpeedMph() );
		ForecastRecord.put( CWeatherDBContract.CForecastTable.COLUMN_NAME_WIND_SPEED_KMPH,			Forecast.getWindSpeedKmph() );
		ForecastRecord.put( CWeatherDBContract.CForecastTable.COLUMN_NAME_WIND_DIRECTION, 			Forecast.getWindDirection() );
		ForecastRecord.put( CWeatherDBContract.CForecastTable.COLUMN_NAME_MAX_TEMPERATURE_CELSIUS,	Forecast.getMaxTemperatureCelsius() );
		ForecastRecord.put( CWeatherDBContract.CForecastTable.COLUMN_NAME_FORECAST_DATE, 			Forecast.getForecastDate() == null ? null : Forecast.getForecastDate().getTime() );
		ForecastRecord.put( CWeatherDBContract.CForecastTable.COLUMN_NAME_WEATHER_CODE,				Forecast.getWeatherCode() );
		ForecastRecord.put( CWeatherDBContract.CForecastTable.COLUMN_NAME_MAX_TEMPERATURE_FAHRENHEIT,	Forecast.getMaxTemperatureFahrenheit() );
		ForecastRecord.put( CWeatherDBContract.CForecastTable.COLUMN_NAME_PRECIPITATION,			Forecast.getPrecipitation() );
		ForecastRecord.put( CWeatherDBContract.CForecastTable.COLUMN_NAME_WIND_DIRECTION_DEGREES, 	Forecast.getWindDirectionDegrees() );
		ForecastRecord.put( CWeatherDBContract.CForecastTable.COLUMN_NAME_WIND_DIRECTION_COMPASS,		Forecast.getWindDirectionCompass() );
		ForecastRecord.put( CWeatherDBContract.CForecastTable.COLUMN_NAME_WEATHER_DESCRIPTION, 		Forecast.getWeatherDescription() );
		ForecastRecord.put( CWeatherDBContract.CForecastTable.COLUMN_NAME_MIN_TEMPERATURE_FAHRENHEIT, 	Forecast.getMinTemperatureFahrenheit() );

		return m_db.insert( CWeatherDBContract.CForecastTable.TABLE_NAME, null, ForecastRecord );
	}

	/*********************************************************/
	/*                                                       */ 
	/* CWeatherDAO.selectAllCities()                         */ 
	/*                                                       */ 
	/*********************************************************/
	public Cursor selectAllCities()
	{
		if( m_db == null ) m_db = m_dbHelper.getReadableDatabase();
		
		String[] Projection = { "*" };
		
		String orderBy = CWeatherDBContract.CCityTable.COLUMN_NAME_NAME + " ASC";
		return m_db.query( CWeatherDBContract.CCityTable.TABLE_NAME, Projection, null, null, null, null, orderBy );                               
	}

	/*********************************************************/
	/*                                                       */ 
	/* CWeatherDAO.selectCityForecast()                      */ 
	/*                                                       */ 
	/*********************************************************/
	public Cursor selectCityForecast( CCity City )
	{
		if( m_db == null ) m_db = m_dbHelper.getReadableDatabase();

		String[] Projection = { "*" };
		String Where = CWeatherDBContract.CForecastTable.COLUMN_NAME_CITY_ID + " = ?";
		String[] WhereArgs = new String[] { "" + City.getId() };

		String orderBy = CWeatherDBContract.CForecastTable.COLUMN_NAME_FORECAST_DATE + " ASC";
		return m_db.query( CWeatherDBContract.CForecastTable.TABLE_NAME, Projection, Where, WhereArgs, null, null, orderBy );                               
	}
	
	/*********************************************************/
	/*                                                       */ 
	/* CWeatherDAO.deleteCity()                              */ 
	/*                                                       */ 
	/*********************************************************/
	public int deleteCity( long cityId )
	{
		if( m_db == null || m_db.isReadOnly() )
		{
			this.close();
			m_db = m_dbHelper.getWritableDatabase();
		}
		
		int ret = -1;
		m_db.beginTransaction();
		
		try
		{
			String ForecastWhere = CWeatherDBContract.CForecastTable.COLUMN_NAME_CITY_ID + " = ?";
			String[] ForecastWhereArgs = new String[] { "" + cityId };
			ret = m_db.delete( CWeatherDBContract.CForecastTable.TABLE_NAME, ForecastWhere, ForecastWhereArgs );
			
			String ConditionWhere = CWeatherDBContract.CConditionTable.COLUMN_NAME_CITY_ID + " = ?";
			String[] ConditionWhereArgs = new String[] { "" + cityId };
			ret = m_db.delete( CWeatherDBContract.CConditionTable.TABLE_NAME, ConditionWhere, ConditionWhereArgs );
			
			String CityWhere = CWeatherDBContract.CCityTable._ID + " = ?";
			String[] CityWhereArgs = new String[] { "" + cityId };
			ret = m_db.delete( CWeatherDBContract.CCityTable.TABLE_NAME, CityWhere, CityWhereArgs );
			
			m_db.setTransactionSuccessful();
		}
		finally
		{
			m_db.endTransaction();
		}
		
		return ret;
	}
	
	/*********************************************************/
	/*                                                       */ 
	/* CWeatherDAO.deleteCityForecast()                      */ 
	/*                                                       */ 
	/*********************************************************/
	public int deleteCityForecast( long cityId )
	{
		if( m_db == null || m_db.isReadOnly() )
		{
			this.close();
			m_db = m_dbHelper.getWritableDatabase();
		}
		
		String ForecastWhere = CWeatherDBContract.CForecastTable.COLUMN_NAME_CITY_ID + " = ?";
		String[] ForecastWhereArgs = new String[] { "" + cityId };
		return m_db.delete( CWeatherDBContract.CForecastTable.TABLE_NAME, ForecastWhere, ForecastWhereArgs );
	}
	
	/*********************************************************/
	/*                                                       */ 
	/* CWeatherDAO.deleteAll()                               */ 
	/*                                                       */ 
	/*********************************************************/
	public int deleteAll()
	{
		if( m_db == null || m_db.isReadOnly() )
		{
			this.close();
			m_db = m_dbHelper.getWritableDatabase();
		}
		
		int ret = -1;
		m_db.beginTransaction();
		
		try
		{
			ret = m_db.delete( CWeatherDBContract.CForecastTable.TABLE_NAME, null, null );
			ret = m_db.delete( CWeatherDBContract.CConditionTable.TABLE_NAME, null, null );
			ret = m_db.delete( CWeatherDBContract.CCityTable.TABLE_NAME, null, null );
			m_db.setTransactionSuccessful();
		}
		finally
		{
			m_db.endTransaction();
		}
		
		return ret;
	}
	
	/*********************************************************/
	/*                                                       */ 
	/* CWeatherDAO.update()                                  */ 
	/*                                                       */ 
	/*********************************************************/
	public int update( CCity City )
	{
		if( m_db == null || m_db.isReadOnly() )
		{
			this.close();
			m_db = m_dbHelper.getWritableDatabase();
		}
		
		int ret = -1;
		m_db.beginTransaction();
		
		try
		{
			ContentValues ConditionRecord = new ContentValues();
			ConditionRecord.put( CWeatherDBContract.CConditionTable.COLUMN_NAME_CLOUD_COVERAGE, 			City.getCondition().getCloudCoverPercentage() );
			ConditionRecord.put( CWeatherDBContract.CConditionTable.COLUMN_NAME_OBSERVATION_TIME,		City.getCondition().getObservationTime() == null ? null : City.getCondition().getObservationTime().getTime() );
			ConditionRecord.put( CWeatherDBContract.CConditionTable.COLUMN_NAME_PRESSURE, 				City.getCondition().getPressure() );
			ConditionRecord.put( CWeatherDBContract.CConditionTable.COLUMN_NAME_TEMPERATURE_CELSIUS, 		City.getCondition().getTemperatureCelsius() );
			ConditionRecord.put( CWeatherDBContract.CConditionTable.COLUMN_NAME_VISIBILITY, 			City.getCondition().getVisibility() );			
			ConditionRecord.put( CWeatherDBContract.CConditionTable.COLUMN_NAME_TEMPERATURE_FAHRENHEIT,	City.getCondition().getTemperatureFahrenheit() );
			ConditionRecord.put( CWeatherDBContract.CConditionTable.COLUMN_NAME_WIND_SPEED_MPH,			City.getCondition().getWindSpeedMph() );
			ConditionRecord.put( CWeatherDBContract.CConditionTable.COLUMN_NAME_PRECIPITATION, 			City.getCondition().getPrecipitation() );
			ConditionRecord.put( CWeatherDBContract.CConditionTable.COLUMN_NAME_WIND_DIRECTION_DEGREES, 	City.getCondition().getWindDirectionDegrees() );
			ConditionRecord.put( CWeatherDBContract.CConditionTable.COLUMN_NAME_WIND_DIRECTION_COMPASS, 	City.getCondition().getWindDirectionCompass() );
			ConditionRecord.put( CWeatherDBContract.CConditionTable.COLUMN_NAME_ICON_URL, 				City.getCondition().getIconUrl() );
			ConditionRecord.put( CWeatherDBContract.CConditionTable.COLUMN_NAME_HUMIDITY, 				City.getCondition().getHumidity() );
			ConditionRecord.put( CWeatherDBContract.CConditionTable.COLUMN_NAME_WIND_SPEED_KMPH, 		City.getCondition().getWindSpeedKmph() );
			ConditionRecord.put( CWeatherDBContract.CConditionTable.COLUMN_NAME_WEATHER_CODE, 			City.getCondition().getWeatherCode() );
			ConditionRecord.put( CWeatherDBContract.CConditionTable.COLUMN_NAME_WEATHER_DESCRIPTION, 		City.getCondition().getWeatherDescription() );
			
			String ConditionWhere = CWeatherDBContract.CConditionTable.COLUMN_NAME_CITY_ID + " = ?";
			String[] ConditionWhereArgs = new String[] { "" + City.getId() };
			ret = m_db.update( CWeatherDBContract.CConditionTable.TABLE_NAME, ConditionRecord, ConditionWhere, ConditionWhereArgs );
		
			ContentValues CityRecord = new ContentValues();
			CityRecord.put( CWeatherDBContract.CCityTable.COLUMN_NAME_NAME, 			City.getName() );
			CityRecord.put( CWeatherDBContract.CCityTable.COLUMN_NAME_COUNTRY, 		City.getCountry() );
			CityRecord.put( CWeatherDBContract.CCityTable.COLUMN_NAME_LATITUDE, 		City.getLatitude() );
			CityRecord.put( CWeatherDBContract.CCityTable.COLUMN_NAME_LONGITUDE,		City.getLongitude() );
			CityRecord.put( CWeatherDBContract.CCityTable.COLUMN_NAME_POPULATION,		City.getPopulation() );
			CityRecord.put( CWeatherDBContract.CCityTable.COLUMN_NAME_URL, 			City.getWeatherUrl() );
			
			String CityWhere = CWeatherDBContract.CCityTable._ID + " = ?";
			String[] CityWhereArgs = new String[] { "" + City.getId() };
			ret = m_db.update( CWeatherDBContract.CCityTable.TABLE_NAME, CityRecord, CityWhere, CityWhereArgs );
			
			m_db.setTransactionSuccessful();
		}
		finally
		{
			m_db.endTransaction();
		}
		
		return ret;
	}
	
	/*********************************************************/
	/*                                                       */ 
	/* CWeatherDAO.close()                                   */ 
	/*                                                       */ 
	/*********************************************************/
	public void close()
	{
		if( m_db != null )
		{
			m_db.close();
			m_db = null;
		}
	}
}
