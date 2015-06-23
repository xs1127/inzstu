package cn.edu.zstu.ui;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import cn.edu.zstu.R;

public class MessageShow extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.messageshow);
		((TextView) this.findViewById(R.id.messageshow)).setText(getIntent().getExtras().getString(
				"message"));

	}

	public void back(View v) {
		this.finish();
	}

}
