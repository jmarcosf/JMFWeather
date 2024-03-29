/**************************************************************/
/*                                                            */
/* HorzListView.java                                          */
/* (c)2013 jmarcosf                                           */
/*                                                            */
/* Description: HorzListView Class                            */
/*              JmfWeather Project                            */
/*              Pr�ctica asignatura Android Fundamental       */ 
/*              U-Tad - Master Apps                           */ 
/*              www.u-tad.com                                 */ 
/*                                                            */ 
/*        Date: December 2013                                 */ 
/*                                                            */
/**************************************************************/
/* NOTE: extracted from https://github.com/dinocore1          */
/**************************************************************/
package com.utad.marcos.jorge.jmfweather.utility;

import java.util.LinkedList;
import java.util.Queue;

import android.content.Context;
import android.database.DataSetObserver;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.GestureDetector.OnGestureListener;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.Scroller;

/**************************************************************/
/*                                                            */ 
/*                                                            */ 
/*                                                            */ 
/* HorzListView Class                                         */ 
/*                                                            */ 
/*                                                            */ 
/*                                                            */ 
/**************************************************************/
public class HorzListView extends AdapterView< ListAdapter >
{
private   int                      mLeftViewIndex = -1;
private   int                      mRightViewIndex = 0;
private   int                      mMaxX = Integer.MAX_VALUE;
private   int                      mDisplayOffset = 0;
protected ListAdapter              mAdapter;
protected int                      mCurrentX;
protected int                      mNextX;
protected Scroller                 mScroller;
private   GestureDetector          mGesture;
private   Queue< View >            mRemovedViewQueue = new LinkedList< View >();
private   OnItemSelectedListener   mOnItemSelected;
private   OnItemClickListener      mOnItemClicked;
private   OnItemLongClickListener  mOnItemLongClicked;
private   boolean                  mDataChanged = false;
public    boolean                  mAlwaysOverrideTouch = true;

     /*********************************************************/
     /*                                                       */ 
     /*                                                       */ 
     /* Constructors                                          */ 
     /*                                                       */ 
     /*                                                       */ 
     /*********************************************************/
     /*                                                       */ 
     /* HorzListView.HorzListView()                           */ 
     /*                                                       */ 
     /*********************************************************/
     public HorzListView( Context context, AttributeSet attrs )
     {
         super(context, attrs);
         initView();
     }
    
     /*********************************************************/
     /*                                                       */ 
     /*                                                       */ 
     /* AdapterView Overrides                                 */ 
     /*                                                       */ 
     /*                                                       */ 
     /*********************************************************/
     /*                                                       */ 
     /* HorzListView.setOnItemSelectedListener()              */ 
     /*                                                       */ 
     /*********************************************************/
     @Override
     public void setOnItemSelectedListener( AdapterView.OnItemSelectedListener listener )
     {
          mOnItemSelected = listener;
     }

     /*********************************************************/
     /*                                                       */ 
     /* HorzListView.setOnItemClickListener()                 */ 
     /*                                                       */ 
     /*********************************************************/
     @Override
     public void setOnItemClickListener( AdapterView.OnItemClickListener listener )
     {
          mOnItemClicked = listener;
     }

     /*********************************************************/
     /*                                                       */ 
     /* HorzListView.setOnItemLongClickListener()             */ 
     /*                                                       */ 
     /*********************************************************/
     @Override
     public void setOnItemLongClickListener( AdapterView.OnItemLongClickListener listener )
     {
          mOnItemLongClicked = listener;
     }
     
     /*********************************************************/
     /*                                                       */ 
     /* HorzListView.getAdapter()                             */ 
     /*                                                       */ 
     /*********************************************************/
     @Override
     public ListAdapter getAdapter()
     {
          return mAdapter;
     }

     /*********************************************************/
     /*                                                       */ 
     /* HorzListView.getSelectedView()                        */ 
     /*                                                       */ 
     /*********************************************************/
     @Override
     public View getSelectedView()
     {
          return null;
     }

     /*********************************************************/
     /*                                                       */ 
     /* HorzListView.setAdapter()                             */ 
     /*                                                       */ 
     /*********************************************************/
     @Override
     public void setAdapter( ListAdapter adapter )
     {
          if( mAdapter != null ) mAdapter.unregisterDataSetObserver( mDataObserver );
          mAdapter = adapter;
          mAdapter.registerDataSetObserver( mDataObserver );
          reset();
     }

     /*********************************************************/
     /*                                                       */ 
     /* HorzListView.setSelection()                           */ 
     /*                                                       */ 
     /*********************************************************/
     @Override
     public void setSelection( int position )
     {
          //TODO: implement
     }

     /*********************************************************/
     /*                                                       */ 
     /* HorzListView.onLayout()                               */ 
     /*                                                       */ 
     /*********************************************************/
     @Override
     protected synchronized void onLayout( boolean changed, int left, int top, int right, int bottom )
     {
          super.onLayout( changed, left, top, right, bottom );

          if( mAdapter == null ) return;

          if( mDataChanged )
          {
               int oldCurrentX = mCurrentX;
               initView();
               removeAllViewsInLayout();
               mNextX = oldCurrentX;
               mDataChanged = false;
          }

          if( mScroller.computeScrollOffset() )
          {
               int scrollx = mScroller.getCurrX();
               mNextX = scrollx;
          }

          if( mNextX <= 0 )
          {
               mNextX = 0;
               mScroller.forceFinished( true );
          }
          
          if( mNextX >= mMaxX )
          {
               mNextX = mMaxX;
               mScroller.forceFinished( true );
          }

          int dx = mCurrentX - mNextX;
          removeNonVisibleItems( dx );
          fillList( dx );
          positionItems( dx );
          mCurrentX = mNextX;

          if( !mScroller.isFinished() )
          {
               post( new Runnable()
               {
                    @Override
                    public void run() { requestLayout(); }
               } );
          }
     }

     /*********************************************************/
     /*                                                       */ 
     /*                                                       */ 
     /* ViewGroup Overrides                                   */ 
     /*                                                       */ 
     /*                                                       */ 
     /*********************************************************/
     /*                                                       */ 
     /* HorzListView.dispatchTouchEvent()                     */ 
     /*                                                       */ 
     /*********************************************************/
     @Override
     public boolean dispatchTouchEvent( MotionEvent ev )
     {
          boolean handled = super.dispatchTouchEvent( ev );
          handled |= mGesture.onTouchEvent( ev );
          return handled;
     }

     /*********************************************************/
     /*                                                       */ 
     /*                                                       */ 
     /* Class Private Methods                                 */ 
     /*                                                       */ 
     /*                                                       */ 
     /*********************************************************/
     /*                                                       */ 
     /* HorzListView.initView()                               */ 
     /*                                                       */ 
     /*********************************************************/
     private synchronized void initView()
     {
          mLeftViewIndex = -1;
          mRightViewIndex = 0;
          mDisplayOffset = 0;
          mCurrentX = 0;
          mNextX = 0;
          mMaxX = Integer.MAX_VALUE;
          mScroller = new Scroller( getContext() );
          mGesture = new GestureDetector( getContext(), mOnGesture );
      }
     
     /*********************************************************/
     /*                                                       */ 
     /* HorzListView.reset()                                  */ 
     /*                                                       */ 
     /*********************************************************/
     private synchronized void reset()
     {
          initView();
          removeAllViewsInLayout();
          requestLayout();
     }

     /*********************************************************/
     /*                                                       */ 
     /* HorzListView.addAndMeasureChild()                     */ 
     /*                                                       */ 
     /*********************************************************/
     private void addAndMeasureChild( final View child, int viewPos )
     {
          LayoutParams params = child.getLayoutParams();
          if( params == null ) params = new LayoutParams( LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT );

          addViewInLayout( child, viewPos, params, true );
          child.measure( MeasureSpec.makeMeasureSpec( getWidth(), MeasureSpec.AT_MOST ), MeasureSpec.makeMeasureSpec( getHeight(), MeasureSpec.AT_MOST ) );
     }

     /*********************************************************/
     /*                                                       */ 
     /* HorzListView.fillList()                               */ 
     /*                                                       */ 
     /*********************************************************/
     private void fillList( final int dx )
     {
          int edge = 0;
          View child = getChildAt( getChildCount() -1 );
          if( child != null ) edge = child.getRight();
          fillListRight( edge, dx );

          edge = 0;
          child = getChildAt( 0 );
          if( child != null ) edge = child.getLeft();
          fillListLeft( edge, dx );
     }

     /*********************************************************/
     /*                                                       */ 
     /* HorzListView.fillListRight()                          */ 
     /*                                                       */ 
     /*********************************************************/
     private void fillListRight( int rightEdge, final int dx )
     {
          while( rightEdge + dx < getWidth() && mRightViewIndex < mAdapter.getCount() )
          {
               View child = mAdapter.getView( mRightViewIndex, mRemovedViewQueue.poll(), this );
               addAndMeasureChild( child, -1 );
               rightEdge += child.getMeasuredWidth();

               if( mRightViewIndex == mAdapter.getCount() -1 ) mMaxX = mCurrentX + rightEdge - getWidth();
               if( mMaxX < 0 ) mMaxX = 0;
               mRightViewIndex++;
          }
     }

     /*********************************************************/
     /*                                                       */ 
     /* HorzListView.fillListLeft()                           */ 
     /*                                                       */ 
     /*********************************************************/
     private void fillListLeft( int leftEdge, final int dx )
     {
          while( leftEdge + dx > 0 && mLeftViewIndex >= 0 )
          {
               View child = mAdapter.getView( mLeftViewIndex, mRemovedViewQueue.poll(), this );
               addAndMeasureChild( child, 0 );
               leftEdge -= child.getMeasuredWidth();
               mLeftViewIndex--;
               mDisplayOffset -= child.getMeasuredWidth();
          }
     }

     /*********************************************************/
     /*                                                       */ 
     /* HorzListView.removeNonVisibleItems()                  */ 
     /*                                                       */ 
     /*********************************************************/
     private void removeNonVisibleItems( final int dx )
     {
          View child = getChildAt( 0 );
          while( child != null && child.getRight() + dx <= 0 )
          {
               mDisplayOffset += child.getMeasuredWidth();
               mRemovedViewQueue.offer( child );
               removeViewInLayout( child );
               mLeftViewIndex++;
               child = getChildAt( 0 );
          }

          child = getChildAt( getChildCount() -1 );
          while( child != null && child.getLeft() + dx >= getWidth() )
          {
               mRemovedViewQueue.offer( child );
               removeViewInLayout( child );
               mRightViewIndex--;
               child = getChildAt( getChildCount() -1 );
          }
     }

     /*********************************************************/
     /*                                                       */ 
     /* HorzListView.positionItems()                          */ 
     /*                                                       */ 
     /*********************************************************/
     private void positionItems( final int dx )
     {
          if( getChildCount() > 0 )
          {
               mDisplayOffset += dx;
               int left = mDisplayOffset;
               for( int i = 0; i < getChildCount(); i++ )
               {
                    View child = getChildAt( i );
                    int childWidth = child.getMeasuredWidth();
                    child.layout( left, 0, left + childWidth, child.getMeasuredHeight() );
                    left += childWidth + child.getPaddingRight();
               }
          }
     }

     /*********************************************************/
     /*                                                       */ 
     /*                                                       */ 
     /* Class Protected Methods()                             */ 
     /*                                                       */ 
     /*                                                       */ 
     /*********************************************************/
     /*                                                       */ 
     /* HorzListView.onFling()                                */ 
     /*                                                       */ 
     /*********************************************************/
     protected boolean onFling( MotionEvent e1, MotionEvent e2, float velocityX, float velocityY )
     {
          synchronized( HorzListView.this )
          {
               mScroller.fling( mNextX, 0, (int)-velocityX, 0, 0, mMaxX, 0, 0 );
          }
          requestLayout();
          return true;
     }

     /*********************************************************/
     /*                                                       */ 
     /* HorzListView.onDown()                                 */ 
     /*                                                       */ 
     /*********************************************************/
     protected boolean onDown( MotionEvent e )
     {
          mScroller.forceFinished( true );
          return true;
     }

     /*********************************************************/
     /*                                                       */ 
     /*                                                       */ 
     /* Class Public Methods()                                */ 
     /*                                                       */ 
     /*                                                       */ 
     /*********************************************************/
     /*                                                       */ 
     /* HorzListView.scrollTo()                               */ 
     /*                                                       */ 
     /*********************************************************/
     public synchronized void scrollTo( int x )
     {
          mScroller.startScroll( mNextX, 0, x - mNextX, 0 );
          requestLayout();
     }

     /*********************************************************/
     /*                                                       */ 
     /*                                                       */ 
     /* Class DataSetObserver                                 */ 
     /*                                                       */ 
     /*                                                       */ 
     /*********************************************************/
     private DataSetObserver mDataObserver = new DataSetObserver()
     {
          @Override
          public void onChanged()
          {
               synchronized( HorzListView.this )
               {
                    mDataChanged = true;
               }
               invalidate();
               requestLayout();
          }

          @Override
          public void onInvalidated()
          {
               reset();
               invalidate();
               requestLayout();
          }
     };

     /*********************************************************/
     /*                                                       */ 
     /*                                                       */ 
     /* Class Gesture Listener                                */ 
     /*                                                       */ 
     /*                                                       */ 
     /*********************************************************/
     private OnGestureListener mOnGesture = new GestureDetector.SimpleOnGestureListener()
     {
          @Override
          public boolean onDown( MotionEvent e )
          {
               return HorzListView.this.onDown(e);
          }

          @Override
          public boolean onFling( MotionEvent e1, MotionEvent e2, float velocityX, float velocityY )
          {
               return HorzListView.this.onFling( e1, e2, velocityX, velocityY );
          }

          @Override
          public boolean onScroll( MotionEvent e1, MotionEvent e2, float distanceX, float distanceY )
          {
               synchronized( HorzListView.this )
               {
                    mNextX += (int)distanceX;
               }
               requestLayout();
               return true;
          }

          @Override
          public boolean onSingleTapConfirmed( MotionEvent e )
          {
               for( int i = 0; i < getChildCount(); i++ )
               {
                    View child = getChildAt( i );
                    if( isEventWithinView( e, child ) )
                    {
                         if( mOnItemClicked != null ) mOnItemClicked.onItemClick( HorzListView.this, child, mLeftViewIndex + 1 + i, mAdapter.getItemId( mLeftViewIndex + 1 + i ) );
                         if( mOnItemSelected != null ) mOnItemSelected.onItemSelected( HorzListView.this, child, mLeftViewIndex + 1 + i, mAdapter.getItemId( mLeftViewIndex + 1 + i ) );
                         break;
                    }
               }
               return true;
          }

          @Override
          public void onLongPress( MotionEvent e )
          {
               int childCount = getChildCount();
               for( int i = 0; i < childCount; i++ )
               {
                    View child = getChildAt( i );
                    if( isEventWithinView( e, child ) )
                    {
                         if( mOnItemLongClicked != null ) mOnItemLongClicked.onItemLongClick( HorzListView.this, child, mLeftViewIndex + 1 + i, mAdapter.getItemId( mLeftViewIndex + 1 + i ) );
                         break;
                    }
               }
          }

          private boolean isEventWithinView( MotionEvent e, View child )
          {
               Rect viewRect = new Rect();
               int[] childPosition = new int[ 2 ];
               child.getLocationOnScreen( childPosition );
               int left = childPosition[ 0 ];
               int right = left + child.getWidth();
               int top = childPosition[ 1 ];
               int bottom = top + child.getHeight();
               viewRect.set( left, top, right, bottom );
               return viewRect.contains( (int)e.getRawX(), (int)e.getRawY() );
          }
     };
 }

