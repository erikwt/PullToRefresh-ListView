package nl.saints;

import android.content.Context;
import android.os.AsyncTask;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

public class PullToRefreshListView extends ListView{

	private static final int	SCROLL_ANIMATION_DURATION	= 1000;

	private int					startY;
	private LinearLayout		header;

	public PullToRefreshListView(Context context){
		super(context);
		init();
	}

	public PullToRefreshListView(Context context, AttributeSet attrs){
		super(context, attrs);
		init();
	}

	public PullToRefreshListView(Context context, AttributeSet attrs, int defStyle){
		super(context, attrs, defStyle);
		init();
	}

	private void init(){
		header = (LinearLayout) LayoutInflater.from(getContext()).inflate(R.layout.pull_to_refresh_header, null);
		((TextView) header.findViewById(R.id.text)).setText("Pull to refresh");
		addHeaderView(header);
	}

	private void increaseHeaderHeightBy(int height){
		Log.i("PTR", "Increasing height by: " + height);
		android.view.ViewGroup.LayoutParams lp = header.getLayoutParams();
		lp.height += height;
		header.setLayoutParams(lp);
	}

	@Override
	public boolean onTouchEvent(MotionEvent event){
		switch(event.getAction()){
			case MotionEvent.ACTION_DOWN:
				startY = Math.round(event.getY());
				break;
			case MotionEvent.ACTION_UP:
				if(getFirstVisiblePosition() == 0){
					smoothScrollBy(header.getHeight(), SCROLL_ANIMATION_DURATION);

					new AsyncTask<Void, Void, Void>(){

						@Override
						protected Void doInBackground(Void... params){
							try{
								Thread.sleep(SCROLL_ANIMATION_DURATION);
							}catch(InterruptedException e){
								e.printStackTrace();
							}
							return null;
						}

						@Override
						protected void onPostExecute(Void result){
							increaseHeaderHeightBy(-header.getHeight());
						}
					}.execute();
				}
				break;
			case MotionEvent.ACTION_MOVE:
				int diff;
				if(getFirstVisiblePosition() == 0 && (diff = Math.round(event.getY()) - startY) > 0){
					increaseHeaderHeightBy(diff);
					startY = Math.round(event.getY());
					return true;
				}
				break;
		}

		return super.onTouchEvent(event);
	}
}
