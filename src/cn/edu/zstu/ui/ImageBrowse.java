package cn.edu.zstu.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.widget.TextView;
import cn.edu.zstu.R;
import cn.edu.zstu.customview.MyViewPager;
import cn.edu.zstu.tools.MyAppliction;

public class ImageBrowse extends FragmentActivity {

	private static final String POSITION = "POSITION";
	public static final String INDEX = "INDEX";
	public static final String URLS = "URLS";
	private MyAppliction appliction;
	private MyViewPager viewpager;
	private int pagerPosition;
	private TextView direct;
	private String[] urls;

	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		setContentView(R.layout.imagebrowse);
		appliction = (MyAppliction) getApplication();
		pagerPosition = getIntent().getIntExtra(INDEX, 0);
		urls = getIntent().getStringArrayExtra(URLS);
		viewpager = (MyViewPager) this.findViewById(R.id.imagebrowse_viewpager);
		direct = (TextView) this.findViewById(R.id.direct);
		MyViewPagerAdapter adapter = new MyViewPagerAdapter(getSupportFragmentManager(), urls);
		viewpager.setAdapter(adapter);
		String text = getString(R.string.viewpager_direct, 1, viewpager.getAdapter().getCount());
		direct.setText(text);
		viewpager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

			@Override
			public void onPageSelected(int arg0) {
				// TODO Auto-generated method stub
				CharSequence text = getString(R.string.viewpager_direct, arg0 + 1, viewpager
						.getAdapter().getCount());
				direct.setText(text);
			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				// TODO Auto-generated method stub
			}

			@Override
			public void onPageScrollStateChanged(int arg0) {
				// TODO Auto-generated method stub
			}
		});
	}

	/**
	 * 可查看相关api文档了解更多
	 * 
	 * @author sjtu
	 *
	 */
	class MyViewPagerAdapter extends FragmentStatePagerAdapter {

		String[] urls;

		public MyViewPagerAdapter(FragmentManager fm, String[] urls) {
			super(fm);
			// TODO Auto-generated constructor stub
			this.urls = urls;
		}

		@Override
		public Fragment getItem(int arg0) {
			// TODO Auto-generated method stub

			return new GalleryFragment(urls[arg0], appliction.serverPath);
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return urls == null ? 0 : urls.length;
		}

	}
}
