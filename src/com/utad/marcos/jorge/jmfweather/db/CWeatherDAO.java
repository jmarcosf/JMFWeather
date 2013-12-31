/**************************************************************/
/*                                                            */
/* CWeatherDAO.java                                           */
/* (c)2013 jmarcosf                                           */
/*                                                            */
/* Description: CWeatherDAO Class                             */
/*              JmfWeather Project                            */
/*              Práctica asignatura Android Fundamental       */ 
/*              U-Tad - Master Apps                           */ 
/*              www.u-tad.com                                 */ 
/*                                                            */ 
/*        Date: December 2013                                 */ 
/*                                                            */
/**************************************************************/
package com.utad.marcos.jorge.jmfweather.db;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDoneException;
import android.location.Location;
import android.location.LocationManager;

import com.utad.marcos.jorge.jmfweather.model.CCity;
import com.utad.marcos.jorge.jmfweather.model.CCondition;
import com.utad.marcos.jorge.jmfweather.model.CForecast;
import com.utad.marcos.jorge.jmfweather.model.CForecastList;

/**************************************************************/
/*                                                            */ 
/*                                                            */ 
/*                                                            */ 
/* CWeatherDAO Class                                          */ 
/*                                                            */ 
/*                                                            */ 
/*                                                            */ 
/**************************************************************/
public class CWeatherDAO
{
private Context               m_Context;
private CWeatherDBHelper		m_dbHelper;
private SQLiteDatabase		m_db;

	/*********************************************************/
	/*                                                       */ 
	/*                                                       */ 
	/* Class Constructors                                    */ 
	/*                                                       */ 
	/*                                                       */ 
	/*********************************************************/
	/*                                                       */ 
	/* CWeatherDAO.CWeatherDAO()                             */ 
	/*                                                       */ 
	/*********************************************************/
	public CWeatherDAO( Context context )
	{
	     this.m_Context = context;
		this.m_dbHelper = new CWeatherDBHelper( m_Context );
	}
	
	/*********************************************************/
	/*                                                       */ 
	/*                                                       */ 
	/* Class Methods                                         */ 
	/*                                                       */ 
	/*                                                       */ 
	/*********************************************************/
	/*                                                       */ 
	/* CWeatherDAO.Insert()                                  */ 
	/*                                                       */ 
	/*********************************************************/
	public long Insert( CCity City ) 
	{
		if( m_db == null || m_db.isReadOnly() )
		{
			this.Close();
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
		CityRecord.put( CWeatherDBContract.CCityTable.COLUMN_NAME_REGION, 		City.getRegion() );
		CityRecord.put( CWeatherDBContract.CCityTable.COLUMN_NAME_URL, 			City.getWeatherUrl() );

		try
		{
			RecordId = m_db.insert( CWeatherDBContract.CCityTable.TABLE_NAME, null, CityRecord );
			if( RecordId != -1 )
			{
			     City.setId( RecordId );
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
				
				if( City.getForecastList() != null && City.getForecastList().getSize() > 0 ) Insert( City.getForecastList(), City );
				
				m_db.setTransactionSuccessful();
			}
		}
		finally
		{
			m_db.endTransaction();
		}

		return RecordId;
	}

	/*********************************************************/
	/*                                                       */ 
	/* CWeatherDAO.Insert()                                  */ 
	/*                                                       */ 
	/*********************************************************/
	public long Insert( CForecast Forecast, CCity City )
	{
		if( m_db == null || m_db.isReadOnly() )
		{
			this.Close();
			m_db = m_dbHelper.getWritableDatabase();
		}
		
		ContentValues ForecastRecord = new ContentValues();
		ForecastRecord.put( CWeatherDBContract.CForecastTable.COLUMN_NAME_CITY_ID, 				City.getId() );
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

          long ret = m_db.insert( CWeatherDBContract.CForecastTable.TABLE_NAME, null, ForecastRecord );
		return ret;
	}

     /*********************************************************/
     /*                                                       */ 
     /* CWeatherDAO.Insert()                                  */ 
     /*                                                       */ 
     /*********************************************************/
     public void Insert( CForecastList ForecastList, CCity City )
     {
          if( m_db == null || m_db.isReadOnly() )
          {
               this.Close();
               m_db = m_dbHelper.getWritableDatabase();
          }
          
          for( CForecast Forecast : ForecastList.getForecastList() )
          {
               Insert( Forecast, City );
          }
     }

     /*********************************************************/
     /*                                                       */ 
     /* CWeatherDAO.GetLocationId()                           */ 
     /*                                                       */ 
     /*********************************************************/
     public long GetLocationId( String Latitude, String Longitude )
     {
          if( Latitude == null || Longitude == null ) return -1;
          if( m_db == null ) m_db = m_dbHelper.getReadableDatabase();

          String Query = "SELECT * FROM " + CWeatherDBContract.CCityTable.TABLE_NAME + 
                         " WHERE " + CWeatherDBContract.CCityTable.COLUMN_NAME_LATITUDE + " = ? AND " + CWeatherDBContract.CCityTable.COLUMN_NAME_LONGITUDE + " = ?";
          String[] QueryArgs = new String[] { Latitude, Longitude };
          long LocationId = -1;
          try
          {
               LocationId = DatabaseUtils.longForQuery( m_db, Query, QueryArgs );
          }
          catch( SQLiteDoneException e ) {}
          return LocationId;
     }

     /*********************************************************/
     /*                                                       */ 
     /* CWeatherDAO.GetCurrentLocationId()                    */ 
     /*                                                       */ 
     /*********************************************************/
     public long GetCurrentLocationId()
     {
          if( m_db == null ) m_db = m_dbHelper.getReadableDatabase();

          long LocationId = -1;
          LocationManager locationManager = (LocationManager)m_Context.getSystemService( Context.LOCATION_SERVICE );
          Location location = locationManager.getLastKnownLocation( LocationManager.GPS_PROVIDER );
          if( location != null )
          {
               String strLatitude = Double.valueOf( location.getLatitude() ).toString();
               String strLongitude = Double.valueOf( location.getLongitude() ).toString();
               int idx1 = strLatitude.indexOf( '.' );
               int idx2 = strLongitude.indexOf( '.' );
     
               if( idx1 != -1 && idx2 != -1 )
               {
                    String Query = "SELECT * FROM " + CWeatherDBContract.CCityTable.TABLE_NAME + " WHERE " + 
                              CWeatherDBContract.CCityTable.COLUMN_NAME_LATITUDE + " LIKE '" + strLatitude.substring( 0, idx1 + 2 ) + "%'" + " AND " +
                              CWeatherDBContract.CCityTable.COLUMN_NAME_LONGITUDE + " LIKE '" + strLongitude.substring( 0, idx2 + 2 ) + "%'";
                    try
                    {
                         LocationId = DatabaseUtils.longForQuery( m_db, Query, null );
                    }
                    catch( SQLiteDoneException e ) {}
               }
          }
          return LocationId;          
     }

     /*********************************************************/
     /*                                                       */ 
     /* CWeatherDAO.GetCityId()                               */ 
     /*                                                       */ 
     /*********************************************************/
     public long GetCityId( CCity City )
     {
          if( City == null ) return -1;
          return GetLocationId( City.getLatitude(), City.getLongitude() );
     }

     /*********************************************************/
     /*                                                       */ 
     /* CWeatherDAO.ExistLocation()                           */ 
     /*                                                       */ 
     /*********************************************************/
     public boolean ExistLocation( String Latitude, String Longitude )
     {
          if( Latitude == null || Longitude == null ) return false;
          return( GetLocationId( Latitude, Longitude ) != -1 );
     }

     /*********************************************************/
     /*                                                       */ 
     /* CWeatherDAO.ExistCity()                               */ 
     /*                                                       */ 
     /*********************************************************/
     public boolean ExistCity( CCity City )
     {
          if( City == null ) return false;
          return ( GetCityId( City ) != -1 );
     }

     /*********************************************************/
     /*                                                       */ 
     /* CWeatherDAO.SelectCity()                              */ 
     /*                                                       */ 
     /*********************************************************/
     public CCity SelectCity( long cityId )
     {
          if( m_db == null ) m_db = m_dbHelper.getReadableDatabase();

          String[] Projection = { "*" };
          String Where = CWeatherDBContract.CCityTable._ID + " = ?";
          String[] WhereArgs = new String[] { "" + cityId };

          Cursor cursor = m_db.query( CWeatherDBContract.CCityTable.TABLE_NAME, Projection, Where, WhereArgs, null, null, null );
          CCity City = null;
          if( cursor != null && cursor.moveToFirst() ) City = new CCity( cursor );
          this.Close();
          return City;
     }
     
     /*********************************************************/
     /*                                                       */ 
     /* CWeatherDAO.SelectAllCityIds()                        */ 
     /*                                                       */ 
     /*********************************************************/
     public ArrayList< Long > SelectAllCityIds()
     {
          if( m_db == null ) m_db = m_dbHelper.getReadableDatabase();
          
          String[] Projection = { CWeatherDBContract.CCityTable._ID };
          String orderBy = CWeatherDBContract.CCityTable.COLUMN_NAME_NAME + " ASC";
          Cursor cursor = m_db.query( CWeatherDBContract.CCityTable.TABLE_NAME, Projection, null, null, null, null, orderBy );
          if( cursor == null || !cursor.moveToFirst() ) return null;
          
          ArrayList< Long > CityIdList = new ArrayList< Long >();
          do
          {
               CityIdList.add( Long.valueOf( cursor.getLong( cursor.getColumnIndex( CWeatherDBContract.CCityTable._ID ) ) ) );
          }
          while( cursor.moveToNext() );
          
          this.Close();
          return CityIdList;
     }

     /*********************************************************/
	/*                                                       */ 
	/* CWeatherDAO.SelectAllCities()                         */ 
	/*                                                       */ 
	/*********************************************************/
	public Cursor SelectAllCities()
	{
		if( m_db == null ) m_db = m_dbHelper.getReadableDatabase();
		
		String[] Projection = { "*" };
		
		String orderBy = CWeatherDBContract.CCityTable.COLUMN_NAME_NAME + " ASC";
		return m_db.query( CWeatherDBContract.CCityTable.TABLE_NAME, Projection, null, null, null, null, orderBy );                               
	}

	/*********************************************************/
	/*                                                       */ 
	/* CWeatherDAO.SelectCityCondition()                     */ 
	/*                                                       */ 
	/*********************************************************/
	public Cursor SelectCityCondition( CCity City )
	{
		if( m_db == null ) m_db = m_dbHelper.getReadableDatabase();

		String[] Projection = { "*" };
		String Where = CWeatherDBContract.CConditionTable.COLUMN_NAME_CITY_ID + " = ?";
		String[] WhereArgs = new String[] { "" + City.getId() };

		return m_db.query( CWeatherDBContract.CConditionTable.TABLE_NAME, Projection, Where, WhereArgs, null, null, null );                               
	}
	
     /*********************************************************/
     /*                                                       */ 
     /* CWeatherDAO.SetCityCondition()                        */ 
     /*                                                       */ 
     /*********************************************************/
     public void SetCityCondition( CCity City )
     {
          Cursor cursor = SelectCityCondition( City );
          if( cursor.moveToFirst() )
          {
               CCondition Condition = new CCondition( cursor );
               City.setCurrentCondition( Condition );
          }
     }
     
     /*********************************************************/
	/*                                                       */ 
	/* CWeatherDAO.SelectCityForecast()                      */ 
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
     /* CWeatherDAO.SetCityForecast()                         */ 
     /*                                                       */ 
     /*********************************************************/
     public void SetCityForecast( CCity City )
     {
          Cursor cursor = selectCityForecast( City );
          CForecastList ForecastList = new CForecastList();
          if( cursor.moveToFirst() )
          {
               do
               {
                    CForecast Forecast = new CForecast( cursor );
                    ForecastList.add( Forecast );
               }
               while( cursor.moveToNext() );
          }
          City.setForecastList( ForecastList );
     }
     
     /*********************************************************/
	/*                                                       */ 
	/* CWeatherDAO.DeleteCity()                              */ 
	/*                                                       */ 
	/*********************************************************/
	public int DeleteCity( long cityId )
	{
		if( m_db == null || m_db.isReadOnly() )
		{
			this.Close();
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
	/* CWeatherDAO.DeleteCityForecast()                      */ 
	/*                                                       */ 
	/*********************************************************/
	public int DeleteCityForecast( long cityId )
	{
		if( m_db == null || m_db.isReadOnly() )
		{
			this.Close();
			m_db = m_dbHelper.getWritableDatabase();
		}
		
		String ForecastWhere = CWeatherDBContract.CForecastTable.COLUMN_NAME_CITY_ID + " = ?";
		String[] ForecastWhereArgs = new String[] { "" + cityId };
		return m_db.delete( CWeatherDBContract.CForecastTable.TABLE_NAME, ForecastWhere, ForecastWhereArgs );
	}
	
	/*********************************************************/
	/*                                                       */ 
	/* CWeatherDAO.DeleteAll()                               */ 
	/*                                                       */ 
	/*********************************************************/
	public int DeleteAll()
	{
		if( m_db == null || m_db.isReadOnly() )
		{
			this.Close();
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
	/* CWeatherDAO.Update()                                  */ 
	/*                                                       */ 
	/*********************************************************/
	public int Update( CCity City )
	{
	     if( City == null || City.getForecastList() == null ) return -1;
	     
		if( m_db == null || m_db.isReadOnly() )
		{
			this.Close();
			m_db = m_dbHelper.getWritableDatabase();
		}
		
		int ret = -1;
		m_db.beginTransaction();
		
		try
		{
			DeleteCityForecast( City.getId() );
			for( CForecast Forecast : City.getForecastList().getForecastList() )
			{
				Insert( Forecast, City );
			}
			
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
			CityRecord.put( CWeatherDBContract.CCityTable.COLUMN_NAME_REGION, 		City.getRegion() );
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
	/* CWeatherDAO.Close()                                   */ 
	/*                                                       */ 
	/*********************************************************/
	public void Close()
	{
		if( m_db != null )
		{
			m_db.close();
			m_db = null;
		}
	}
}
