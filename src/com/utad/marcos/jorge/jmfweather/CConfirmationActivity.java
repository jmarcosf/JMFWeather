/**************************************************************/
/*                                                            */
/* CConfirmationActivity.java                                 */
/* (c)2013 jmarcosf                                           */ 
/*                                                            */ 
/* Description: CConfirmationActivity Class                   */ 
/*              JmfWeather Project                            */ 
/*              Práctica asignatura Android Fundamental       */ 
/*              U-Tad - Master Apps                           */ 
/*              www.u-tad.com                                 */ 
/*                                                            */ 
/*        Date: December 2013                                 */ 
/*                                                            */
/**************************************************************/
package com.utad.marcos.jorge.jmfweather;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

/**************************************************************/
/*                                                            */ 
/*                                                            */ 
/*                                                            */
/* CConfirmationActivity class                                */ 
/*                                                            */ 
/*                                                            */
/*                                                            */
/**************************************************************/
public class CConfirmationActivity extends Activity implements OnClickListener
{
public static final String	 CONFIRMATION_QUESTION_PARAM = "";
public static final int		 CONFIRMATION_CANCELLED = 0;
public static final int        CONFIRMATION_ACCEPTED = 1;

     /*********************************************************/
     /*                                                       */ 
     /*                                                       */ 
     /* Activity Override Methods                             */ 
     /*                                                       */ 
     /*                                                       */ 
	/*********************************************************/
	/*                                                       */ 
	/* CConfirmationActivity.onCreate()                      */ 
	/*                                                       */ 
	/*********************************************************/
	protected void onCreate( Bundle savedInstanceState )
	{
		super.onCreate( savedInstanceState );
		setContentView( R.layout.layout_confirmation );
	
		TextView Question = (TextView)findViewById( R.id.IDC_TXT_QUESTION );
		Question.setText( getIntent().getCharSequenceExtra( CONFIRMATION_QUESTION_PARAM ) );
	
		findViewById( R.id.IDR_LAY_CONFIRMATION ).setOnClickListener( this );
		findViewById( R.id.IDC_BTN_CANCEL ).setOnClickListener( this );
		findViewById (R.id.IDC_BTN_OK ).setOnClickListener( this );
	}
	
     /*********************************************************/
     /*                                                       */ 
     /*                                                       */ 
     /* OnClickListener Interface Implementation              */ 
     /*                                                       */ 
     /*                                                       */ 
	/*********************************************************/
	/*                                                       */ 
	/* CConfirmationActivity.onClick()                       */ 
	/*                                                       */ 
	/*********************************************************/
	@Override
	public void onClick( View view )
	{
		switch( view.getId() )
		{
			case R.id.IDC_BTN_OK:
				setResult( CONFIRMATION_ACCEPTED );
				finish();
				break;
				
			default:
				setResult( CONFIRMATION_CANCELLED );
				finish();
				break;
		}
	}
}
