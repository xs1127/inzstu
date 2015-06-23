package cn.edu.zstu.customview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.EditText;
import cn.edu.zstu.R;

/**
 * 自定义EditText输入框   右面带有删功能的按键   左边有pho图片
 * @author sjtu
 *
 */
public class EditText_Pho_Del extends EditText {
	
	private Context context;
	private Drawable imgInable;
	private Drawable imgable;
	private Drawable pho;
	private final String TAG = "EditText_Pho_Del";
	public EditText_Pho_Del(Context context, AttributeSet attrs) {
		// TODO Auto-generated constructor stub
		super(context,attrs);
		this.context = context;
		TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.EditText_Pho_Del);
		pho = typedArray.getDrawable(R.styleable.EditText_Pho_Del_drawable);
		imgInable = context.getResources().getDrawable(R.drawable.edittext_pho_del_delete_gray);
		imgable = context.getResources().getDrawable(R.drawable.edittext_pho_del_delete);
		
		//setCompoundDrawablesWithIntrinsicBounds(pho, null, null, null);
		addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
			}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
			}
			
			@Override
			public void afterTextChanged(Editable s) {
				setdrawable();
			}
		});
		setdrawable();
		typedArray.recycle();
		
	}
	protected void setdrawable() {
		// TODO Auto-generated method stub
		if(this.length()<1){
			setCompoundDrawablesWithIntrinsicBounds(pho,null,imgInable,null);
		}
		else {
			setCompoundDrawablesWithIntrinsicBounds(pho,null,imgable,null);
		}
	}	
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// TODO Auto-generated method stub
		//Toast.makeText(getContext(), "ontouchevent", 1).show();
		if(length()>0 && event.getAction()==MotionEvent.ACTION_UP){
			int x = (int)event.getRawX();
			int y = (int)event.getRawY();
			Rect rect = new Rect();
			getGlobalVisibleRect(rect);
			//Toast.makeText(getContext(), ""+this.getWidth(),1).show();
			rect.left = rect.right - 40;
			rect.bottom = rect.bottom + 40;
			if(rect.contains(x, y)){
				setText("");
			}			
		}
		return super.onTouchEvent(event);
	}
}
