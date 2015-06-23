package cn.edu.zstu.dialogbuilder;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager.LayoutParams;
import android.widget.Button;
import android.widget.TextView;
import cn.edu.zstu.R;

/**
 * @author sjtu DIY dialog对话框 仅需输入内容便可重复利用
 */
public class FasteDialogBuilder {
	private String title;
	private String message;
	private String negative_button;
	private String positive_button;
	private String[] items;
	private Context context;

	/**
	 * DIY基本对话框
	 * 
	 * @param title
	 * @param content
	 * @param negative_button
	 * @param positive_button
	 */
	public FasteDialogBuilder(Context context, String title, String message,
			String negative_button, String positive_button) {
		this.context = context;
		this.title = title;
		this.message = message;
		this.negative_button = negative_button;
		this.positive_button = positive_button;
	}

	/**
	 * DIYitems对话框
	 * 
	 * @param title
	 * @param items
	 */
	public FasteDialogBuilder(Context context, String title, String[] items) {
		this.context = context;
		this.title = title;
		this.items = items;
	}

	public void createButtonDialog() {
		
		AlertDialog.Builder builder = new AlertDialog.Builder(context,R.style.dialogstyle);
		View view = LayoutInflater.from(context).inflate(R.layout.buttondialog,null);
		TextView title = (TextView)view.findViewById(R.id.customebutton_title);
		TextView message = (TextView)view.findViewById(R.id.customebutton_message);
		Button left = (Button)view.findViewById(R.id.customebutton_left);
		Button right = (Button)view.findViewById(R.id.customebutton_right);
		title.setText(this.title);
		message.setText(this.message);
		left.setText(this.negative_button);
		right.setText(this.positive_button);
		DisplayMetrics Metrics = new DisplayMetrics();
	    ((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(Metrics);
		builder.setView(view);
//		builder.setView(view);
//		
//		builder.setTitle(title);
//		builder.setMessage(message);
//		builder.setPositiveButton(positive_button, new DialogInterface.OnClickListener() {
//
//			@Override
//			public void onClick(DialogInterface dialog, int which) {
//				// TODO Auto-generated method stub
//				((Activity) context).finish();
//			}
//		});
//		builder.setNegativeButton(negative_button, new DialogInterface.OnClickListener() {
//
//			@Override
//			public void onClick(DialogInterface dialog, int which) {
//				// TODO Auto-generated method stub
//				dialog.dismiss();
//			}
//		});
		builder.create().show();
	}

	public void createItemsDialog() {
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		//builder.setTitle(title);
		builder.setItems(items, new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				switch (which) {
				case 0:

					break;

				case 1:

					break;
				}
			}
		});
		builder.create().show();
	}

}
