package com.fajieyefu.jiuzhou.Bean;

import java.lang.reflect.Field;

import android.app.DatePickerDialog;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;

public class MyDatePickerDialog extends DatePickerDialog {
	final String TAG = this.getClass().getSimpleName();
	final String DAY_FROYO = "mDayPicker";
	final String DAY_ICS = "mDaySpinner";
	final String MONTH_FROYO = "mMonthPicker";
	final String MONTH_ICS = "mMonthSpinner";
	final String YEAR_FROYO = "mYearPicker";
	final String YEAR_ICS = "mYearSpinner";
	private boolean isHideYear = false;
	private boolean isHideMonth = false;
	private boolean isHideDay = false;

	public MyDatePickerDialog(Context context, OnDateSetListener callBack,
			int year, int monthOfYear, int dayOfMonth) {
		super(context, callBack, year, monthOfYear, dayOfMonth);
	}

//	@Override
//	public void onDateChanged(DatePicker view, int year, int month, int day) {
//		super.onDateChanged(view, year, month, day);
//		this.setTitle((isHideYear?"":(year + "年")) + (isHideMonth?"":((month + 1) + "月"))+(isHideDay?"":(day+"日")));
//	}

	private DatePicker findDatePicker(ViewGroup group) {
		if (group != null) {
			for (int i = 0, j = group.getChildCount(); i < j; i++) {
				View child = group.getChildAt(i);
				if (child instanceof DatePicker) {
					return (DatePicker) child;
				} else if (child instanceof ViewGroup) {
					DatePicker result = findDatePicker((ViewGroup) child);
					if (result != null)
						return result;
				}
			}
		}
		return null;
	}

	private void doHide() {
		DatePicker datePicker = findDatePicker((ViewGroup) this.getWindow().getDecorView());
		if (isHideYear) {
			hideView(datePicker, YEAR_FROYO, YEAR_ICS);
		}
		if (isHideMonth) {
			hideView(datePicker, MONTH_FROYO, MONTH_ICS);
		}
		if (isHideDay) {
			hideView(datePicker, DAY_FROYO, DAY_ICS);
		}

	}

	private void hideView(DatePicker datePicker,String nameFroyo,String nameIcs) {
		try {
			Class pickerClass = datePicker.getClass();
			Field[] fields = pickerClass.getDeclaredFields();
			for (Field field : fields) {
				String fieldName = field.getName();
				if ("mDayPicker".equals(fieldName) || "mDaySpinner".equals(fieldName)) {
					field.setAccessible(true);
					View dayView = (View) field.get(datePicker);
					dayView.setVisibility(View.GONE);
					break;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}	
	}

	@Override
	public void show() {
		super.show();
		if (isHideDay || isHideMonth || isHideYear) {
			doHide();
		}
		
	}

	public void hideWhich(boolean isHideYear,boolean isHideMonth,boolean isHideDay) {
		if (this.isShowing()) {
			Log.e(TAG, "you should hide before the datepickerdialog is showing");
		} else {
			this.isHideYear = isHideYear;
			this.isHideMonth = isHideMonth;
			this.isHideDay = isHideDay;
		}
	}

}
