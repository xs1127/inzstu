package cn.edu.zstu.dialogbuilder;

import java.util.ArrayList;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager.LayoutParams;
import android.widget.Button;
import android.widget.TextView;
import cn.edu.zstu.R;

public class DIYDialog extends Dialog {

	private Context context;
	private String title;
	private String message;
	private String left;
	private String right;
	private TextView customebutton_title, customebutton_message;
	private Button customebutton_left, customebutton_right;
	private callbackListener callback = null;

	public DIYDialog(Context context, ArrayList<String> params, callbackListener callback) {
		super(context, R.style.dialogstyle);
		this.context = context;
		this.title = params.get(0);
		this.message = params.get(1);
		this.left = params.get(2);
		this.right = params.get(3);
		this.callback = callback;
	}

	public interface callbackListener {
		public abstract void cancel();

		public abstract void yes();
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		View view = LayoutInflater.from(context).inflate(R.layout.buttondialog, null);
		customebutton_title = (TextView) view.findViewById(R.id.customebutton_title);
		customebutton_message = (TextView) view.findViewById(R.id.customebutton_message);
		customebutton_left = (Button) view.findViewById(R.id.customebutton_left);
		customebutton_right = (Button) view.findViewById(R.id.customebutton_right);
		customebutton_left.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				callback.cancel();
			}
		});

		customebutton_right.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				callback.yes();
			}
		});

		customebutton_title.setText(title);
		customebutton_message.setText(message);
		customebutton_left.setText(left);
		customebutton_right.setText(right);

		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(view);
		DisplayMetrics Metrics = new DisplayMetrics();
		((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(Metrics);
		LayoutParams params = getWindow().getAttributes();
		params.width = (int) (Metrics.widthPixels * 0.7);
		getWindow().setAttributes(params);
		getWindow().setGravity(Gravity.CENTER);
	}
}
