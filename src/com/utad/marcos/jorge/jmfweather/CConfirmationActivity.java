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
import android.app.Dialog;
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
public static final String     DIALOG_PARAM_TITLE      = "DialogParamTitle";
public static final String	 DIALOG_PARAM_QUESTION   = "DialogParamQuestion";
public static final String     DIALOG_PARAM_OBJECT_ID  = "DialogParamObjectId";
public static final int		 DIALOG_CANCELLED        = 0;
public static final int        DIALOG_ACCEPTED         = 1;

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
		
		Dialog dialog = new Dialog( this );
		dialog.setContentView( R.layout.layout_confirmation );
		dialog.setTitle( getIntent().getCharSequenceExtra( DIALOG_PARAM_TITLE ) );
	
		TextView Question = (TextView)dialog.findViewById( R.id.IDC_TXT_QUESTION );
		Question.setText( getIntent().getCharSequenceExtra( DIALOG_PARAM_QUESTION ) );
	
		dialog.findViewById( R.id.IDR_LAY_CONFIRMATION ).setOnClickListener( this );
		dialog.findViewById( R.id.IDC_BTN_NO ).setOnClickListener( this );
		dialog.findViewById (R.id.IDC_BTN_YES ).setOnClickListener( this );
		dialog.show();
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
			case R.id.IDC_BTN_YES:
				setResult( DIALOG_ACCEPTED, getIntent() );
				finish();
				break;
				
			default:
				setResult( DIALOG_CANCELLED, getIntent() );
				finish();
				break;
		}
	}
}
