package nl.saints;

import android.content.Context;
import android.os.AsyncTask;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.AbsListView.OnScrollListener;

public class PullToRefreshListView extends ListView implements OnScrollListener{

	private static final int	SCROLL_ANIMATION_DURATION	= 700;
	private static final float	RESISTANCE					= 1.7f;

	private enum State{
		PULL_TO_REFRESH, RELEASE_TO_REFRESH, REFRESHING
	}

	private OnRefreshListener	onRefreshListener;

	private int					previousY;
	private int					scrollState;
	private LinearLayout		headerLayout;
	private RelativeLayout		header;
	private RotateAnimation		flipAnimation;
	private RotateAnimation		reverseFlipAnimation;
	private ImageView			image;
	private ProgressBar			spinner;
	private TextView			text;
	private State				state;

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
		setVerticalFadingEdgeEnabled(false);

		headerLayout = (LinearLayout) LayoutInflater.from(getContext()).inflate(R.layout.pull_to_refresh_header, null);
		header = (RelativeLayout) headerLayout.findViewById(R.id.header);
		text = (TextView) header.findViewById(R.id.text);
		image = (ImageView) header.findViewById(R.id.image);
		spinner = (ProgressBar) header.findViewById(R.id.spinner);

		flipAnimation = new RotateAnimation(0, -180, RotateAnimation.RELATIVE_TO_SELF, 0.5f, RotateAnimation.RELATIVE_TO_SELF, 0.5f);
		flipAnimation.setInterpolator(new LinearInterpolator());
		flipAnimation.setDuration(250);
		flipAnimation.setFillAfter(true);
		
		reverseFlipAnimation = new RotateAnimation(-180, 0, RotateAnimation.RELATIVE_TO_SELF, 0.5f, RotateAnimation.RELATIVE_TO_SELF, 0.5f);
		reverseFlipAnimation.setInterpolator(new LinearInterpolator());
		reverseFlipAnimation.setDuration(250);
		reverseFlipAnimation.setFillAfter(true);

		addHeaderView(headerLayout);
		setState(State.PULL_TO_REFRESH);
	}

	public void setOnRefreshListener(OnRefreshListener onRefreshListener){
		this.onRefreshListener = onRefreshListener;
	}

	private void setHeaderPadding(int height){
		headerLayout.setPadding(
				headerLayout.getPaddingLeft(),
				height,
				headerLayout.getPaddingRight(),
				headerLayout.getPaddingBottom());
	}

	@Override
	public boolean onTouchEvent(MotionEvent event){
		if(state == State.REFRESHING) return true;
		if(scrollState == SCROLL_STATE_FLING) previousY = -1;

		switch(event.getAction()){
			case MotionEvent.ACTION_DOWN:
				if(getFirstVisiblePosition() == 0) previousY = Math.round(event.getY());
				else previousY = -1;
				break;

			case MotionEvent.ACTION_UP:
				if(previousY != -1 && (state == State.RELEASE_TO_REFRESH || getFirstVisiblePosition() == 0)){
					switch(state){
						case RELEASE_TO_REFRESH:
							setState(State.REFRESHING);
							smoothScrollBy(headerLayout.getHeight() - header.getHeight(), SCROLL_ANIMATION_DURATION);
							break;

						case PULL_TO_REFRESH:
							resetHeader();
							break;
					}
				}
				break;

			case MotionEvent.ACTION_MOVE:
				if(previousY != -1 && getFirstVisiblePosition() == 0){
					int y = Math.round(event.getY());
					int diff = y - previousY;
					if(diff > 0) diff /= RESISTANCE;
					setHeaderPadding(headerLayout.getPaddingTop() + diff);
					previousY = y;

					if(state == State.PULL_TO_REFRESH && headerLayout.getPaddingTop() + header.getHeight() > header.getHeight()){
						setState(State.RELEASE_TO_REFRESH);

						image.clearAnimation();
						image.startAnimation(flipAnimation);
					}else if(state == State.RELEASE_TO_REFRESH && headerLayout.getPaddingTop() + header.getHeight() < header.getHeight()){
						setState(State.PULL_TO_REFRESH);

						image.clearAnimation();
						image.startAnimation(reverseFlipAnimation);
					}
				}

				break;
		}

		return super.onTouchEvent(event);
	}

	private void resetHeader(){
		if(headerLayout.getPaddingTop() == -header.getHeight()){
			return;
		}
		
		final int amount = state == State.REFRESHING ? header.getHeight() : headerLayout.getHeight();
		smoothScrollBy(amount, SCROLL_ANIMATION_DURATION);

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
				setHeaderPadding(-header.getHeight());
				if(getFirstVisiblePosition() == 0) setSelectionFromTop(0, 0);
			}
		}.execute();
	}

	public void setState(State state){
		this.state = state;
		switch(state){
			case PULL_TO_REFRESH:
				spinner.setVisibility(View.INVISIBLE);
				image.setVisibility(View.VISIBLE);
				text.setText(R.string.ptr_pull_to_refresh);
				break;

			case RELEASE_TO_REFRESH:
				spinner.setVisibility(View.INVISIBLE);
				image.setVisibility(View.VISIBLE);
				text.setText(R.string.ptr_release_to_refresh);
				break;

			case REFRESHING:
				spinner.setVisibility(View.VISIBLE);
				image.clearAnimation();
				image.setVisibility(View.INVISIBLE);
				text.setText(R.string.ptr_refreshing);

				if(onRefreshListener == null){
					setState(State.PULL_TO_REFRESH);
				}else{
					onRefreshListener.onRefresh();
				}

				break;
		}
	}

	public void onScrollStateChanged(AbsListView view, int scrollState){
		this.scrollState = scrollState;
	}

	public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount){}

	public void setRefreshing(){
		setState(State.REFRESHING);
		setHeaderPadding(header.getHeight());
	}
	
	public void onRefreshComplete(){
		resetHeader();
		setState(State.PULL_TO_REFRESH);
	}

	public interface OnRefreshListener{
		public void onRefresh();
	}
}
