/**************************************************************/
/*                                                            */
/* CMessageBoxActivity.java                                   */
/* (c)2013 jmarcosf                                           */ 
/*                                                            */ 
/* Description: CMessageBoxActivity Class                     */ 
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
import android.view.Window;
import android.widget.TextView;

/**************************************************************/
/*                                                            */ 
/*                                                            */ 
/*                                                            */
/* CMessageBoxActivity class                                  */ 
/*                                                            */ 
/*                                                            */
/*                                                            */
/**************************************************************/
public class CMessageBoxActivity extends Activity implements OnClickListener
{
public static final int        MESSAGEBOX_TYPE_OKONLY       = 0;
public static final int        MESSAGEBOX_TYPE_OKCANCEL     = 1;
public static final int        MESSAGEBOX_TYPE_YESNO        = 2;
public static final int        MESSAGEBOX_TYPE_RETRYONLY    = 3;
public static final int        MESSAGEBOX_TYPE_RETRYCANCEL  = 4;
public static final String     MESSAGEBOX_PARAM_TYPE        = "MessageBoxParamType";
public static final String     MESSAGEBOX_PARAM_TITLE       = "MessageBoxParamTitle";
public static final String     MESSAGEBOX_PARAM_MESSAGE     = "MessageBoxParamMessage";
public static final String     MESSAGEBOX_PARAM_ICON_ID     = "MessageBoxParamIconId";
public static final String     MESSAGEBOX_PARAM_OBJECT_ID   = "MessageBoxParamObjectId";
public static final String     MESSAGEBOX_PARAM_USER_DATA1  = "MessageBoxParamUserData1";
public static final String     MESSAGEBOX_PARAM_USER_DATA2  = "MessageBoxParamUserData2";
public static final int        MESSAGEBOX_RESULT_CANCELLED  = 0;
public static final int        MESSAGEBOX_RESULT_ACCEPTED   = 1;

     /*********************************************************/
     /*                                                       */ 
     /*                                                       */ 
     /* Activity Override Methods                             */ 
     /*                                                       */ 
     /*                                                       */ 
     /*********************************************************/
     /*                                                       */ 
     /* CMessageBoxActivity.onCreate()                        */ 
     /*                                                       */ 
     /*********************************************************/
     protected void onCreate( Bundle savedInstanceState )
     {
          super.onCreate( savedInstanceState );
          requestWindowFeature( Window.FEATURE_LEFT_ICON );

          setContentView( R.layout.layout_message_box );
  
          int IconId = getIntent().getExtras().getInt( MESSAGEBOX_PARAM_ICON_ID, -1 );
          if( IconId != -1 ) setFeatureDrawableResource( Window.FEATURE_LEFT_ICON, IconId );
          
          setTitle( getIntent().getExtras().getCharSequence( MESSAGEBOX_PARAM_TITLE ) );

          findViewById( R.id.IDR_LAY_MESSAGE_BOX ).setOnClickListener( this );
          
          TextView MessageText = (TextView)findViewById( R.id.IDC_TXT_MESSAGE );
          MessageText.setText( getIntent().getExtras().getCharSequence( MESSAGEBOX_PARAM_MESSAGE ) );
     
          findViewById( R.id.IDR_LAY_MESSAGE_BOX ).setOnClickListener( this );
          int Type = getIntent().getExtras().getInt( MESSAGEBOX_PARAM_TYPE, MESSAGEBOX_TYPE_OKONLY );
          switch( Type )
          {
               case MESSAGEBOX_TYPE_OKONLY:
                    findViewById( R.id.IDR_LAY_MESSAGEBOX_OKONLY_BUTTON ).setVisibility( View.VISIBLE );
                    findViewById( R.id.IDC_BTN_OKONLY_OK ).setOnClickListener( this );
                    break;
                    
               case MESSAGEBOX_TYPE_OKCANCEL:
                    findViewById( R.id.IDR_LAY_MESSAGEBOX_OKCANCEL_BUTTONS ).setVisibility( View.VISIBLE );
                    findViewById( R.id.IDC_BTN_OKCANCEL_OK ).setOnClickListener( this );
                    findViewById( R.id.IDC_BTN_OKCANCEL_CANCEL ).setOnClickListener( this );
                    break;
                    
               case MESSAGEBOX_TYPE_YESNO:
                    findViewById( R.id.IDR_LAY_MESSAGEBOX_YESNO_BUTTONS ).setVisibility( View.VISIBLE );
                    findViewById( R.id.IDC_BTN_YESNO_YES ).setOnClickListener( this );
                    findViewById( R.id.IDC_BTN_YESNO_NO ).setOnClickListener( this );
                    break;
                    
               case MESSAGEBOX_TYPE_RETRYONLY:
                    findViewById( R.id.IDR_LAY_MESSAGEBOX_RETRYONLY_BUTTON ).setVisibility( View.VISIBLE );
                    findViewById( R.id.IDC_BTN_RETRYONLY_RETRY ).setOnClickListener( this );
                    break;
                    
               case MESSAGEBOX_TYPE_RETRYCANCEL:
                    findViewById( R.id.IDR_LAY_MESSAGEBOX_RETRYCANCEL_BUTTONS ).setVisibility( View.VISIBLE );
                    findViewById( R.id.IDC_BTN_RETRYCANCEL_RETRY ).setOnClickListener( this );
                    findViewById( R.id.IDC_BTN_RETRYCANCEL_CANCEL ).setOnClickListener( this );
                    break;
          }
     }
     
     /*********************************************************/
     /*                                                       */ 
     /*                                                       */ 
     /* OnClickListener Interface Implementation              */ 
     /*                                                       */ 
     /*                                                       */ 
     /*********************************************************/
     /*                                                       */ 
     /* CMessageBoxActivity.onClick()                         */ 
     /*                                                       */ 
     /*********************************************************/
     @Override
     public void onClick( View view )
     {
          switch( view.getId() )
          {
               case R.id.IDC_BTN_OKONLY_OK:
               case R.id.IDC_BTN_OKCANCEL_OK:
               case R.id.IDC_BTN_YESNO_YES:
               case R.id.IDC_BTN_RETRYONLY_RETRY:
               case R.id.IDC_BTN_RETRYCANCEL_RETRY:
                    setResult( MESSAGEBOX_RESULT_ACCEPTED, getIntent() );
                    break;
                    
               default:
                    setResult( MESSAGEBOX_RESULT_CANCELLED, getIntent() );
                    break;
          }
          finish();
     }
}
