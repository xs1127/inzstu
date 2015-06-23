package cn.edu.zstu.ui;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
//import cn.edu.zstu.PullRefreshListViewActivity.GetDataTask;
import android.widget.ListView;
import android.widget.Toast;
import cn.edu.pullfresh.PullToRefreshBase;
import cn.edu.pullfresh.PullToRefreshBase.OnRefreshListener;
import cn.edu.pullfresh.PullToRefreshListView;
import cn.edu.zstu.DB.ConnectionService;
import cn.edu.zstu.adapter.MyListViewAdapter_Info;
import cn.edu.zstu.tools.Beans;
import cn.edu.zstu.tools.JsonTools;
import cn.edu.zstu.tools.MyAppliction;
import cn.edu.zstu.tools.MyMessage;

import com.google.gson.Gson;

public class Product_Info_Fragment extends Fragment {

	private PullToRefreshListView mPullListView;
	private ListView mListView;
	private boolean fromHeader = true;
	private boolean hasMore = true;
	private String requestString;
	private SimpleDateFormat format = new SimpleDateFormat("MM-dd HH:mm");
	private MyAppliction appliction;

	public Product_Info_Fragment() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// TODO Auto-generated method stub

		mPullListView = new PullToRefreshListView(getActivity());
		mPullListView.setPullLoadEnabled(false);
		mPullListView.setScrollLoadEnabled(true);
		mListView = mPullListView.getRefreshableView();
		appliction = (MyAppliction) getActivity().getApplication();

		mPullListView.setOnRefreshListener(new OnRefreshListener<ListView>() {

			@Override
			public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
				// TODO Auto-generated method stub
				fromHeader = true;
				new LoadTask().execute();
			}

			@Override
			public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
				// TODO Auto-generated method stub
				fromHeader = false;
				new LoadTask().execute();
			}
		});

		setLastUpdateTime();

		mPullListView.doPullRefreshing(true, 500);
		return mPullListView;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
	}

	class LoadTask extends AsyncTask<Void, Void, ArrayList<Beans>> {

		@Override
		protected ArrayList<Beans> doInBackground(Void... params) {
			// TODO Auto-generated method stub
			requestString = String.valueOf(JsonTools.getJsonObject("new",
					appliction.getCurrentIndex()));
			String requestUri = appliction.url_path + "/servlet/GetdataAction";
			ConnectionService con = new ConnectionService(requestString, requestUri);
			String responseString = con.connect();
			System.out.println(":responseString:" + responseString);
			Gson gson = new Gson();
			MyMessage msg = gson.fromJson(responseString, MyMessage.class);
			if (msg.responseCode == 200 && msg.hasMore) {
				hasMore = true;
				appliction.setCurrentIndex(msg.currentIndex);
				for (int i = 0; i < msg.list.size(); i++) {
					appliction.messageList.add(i, msg.list.get(i));
				}
			} else if (msg.responseCode == 400) {
				// Toast.makeText(getActivity(), "网络连接错误",
				// Toast.LENGTH_LONG).show();
			} else {
				hasMore = false;
			}
			return appliction.messageList;
		}

		@Override
		protected void onPostExecute(ArrayList<Beans> result) {
			// TODO Auto-generated method stub
			// super.onPostExecute(result);
			MyListViewAdapter_Info adapter_Info = new MyListViewAdapter_Info(getActivity(), result,
					appliction.serverPath);
			mListView.setAdapter(adapter_Info);
			adapter_Info.notifyDataSetChanged();
			mPullListView.onPullDownRefreshComplete();
			mPullListView.onPullUpRefreshComplete();
			mPullListView.setHasMoreData(hasMore);
			setLastUpdateTime();
		}

	}

	private void setLastUpdateTime() {
		String text = formatDateTime(System.currentTimeMillis());
		mPullListView.setLastUpdatedLabel(text);
	}

	private String formatDateTime(long time) {
		if (0 == time) {
			return "";
		}
		return format.format(new Date(time));
	}

}
