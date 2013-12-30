/**************************************************************/
/*                                                            */
/* CSplashActivity.java                                       */
/* (c)2013 jmarcosf                                           */ 
/*                                                            */ 
/* Description: CSplashActivity Class                         */ 
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
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;

/**************************************************************/
/*                                                            */ 
/*                                                            */ 
/*                                                            */ 
/* CSplashActivity Class                                      */ 
/*                                                            */ 
/*                                                            */ 
/*                                                            */ 
/**************************************************************/
public class CSplashActivity extends Activity
{
private static int  SPLASH_TIME_OUT = 2000;
     
     /*********************************************************/
     /*                                                       */ 
     /* CSplashActivity.onCreate()                            */ 
     /*                                                       */ 
     /*********************************************************/
     @Override
     protected void onCreate(Bundle savedInstanceState)
     {
          requestWindowFeature( Window.FEATURE_NO_TITLE );
          super.onCreate( savedInstanceState );
     
          setContentView( R.layout.layout_splash_activity );
          new Handler().postDelayed( new Runnable()
          {
               @Override
               public void run()
               {
                    Intent intent = new Intent( CSplashActivity.this, CCityListActivity.class );
                    startActivity( intent );
                    finish();
               }
          }, SPLASH_TIME_OUT );
     }
}
