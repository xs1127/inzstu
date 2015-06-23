package cn.edu.zstu.adapter;

import java.util.List;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class MyFragmentPagerAdapter extends FragmentPagerAdapter {

	private FragmentManager manager;
	private List<Fragment> listfragment;
	private List<String> listtitle;
	public MyFragmentPagerAdapter(FragmentManager fm,List<Fragment> listfragment,List<String> listtitle) {
		super(fm);
		// TODO Auto-generated constructor stub
		manager = fm;
		this.listfragment = listfragment;
		this.listtitle = listtitle;
	}

	@Override
	public Fragment getItem(int arg0) {
		// TODO Auto-generated method stub
		return listfragment.get(arg0);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return listfragment.size();
	}
	
	@Override
	public CharSequence getPageTitle(int position) {
		// TODO Auto-generated method stub
		return listtitle.get(position);
	}

}
