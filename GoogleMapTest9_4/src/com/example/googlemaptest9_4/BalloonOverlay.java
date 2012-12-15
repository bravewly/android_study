package com.example.googlemaptest9_4;

import java.util.ArrayList;
import java.util.List;

import android.app.Service;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Point;
import android.graphics.RectF;
import android.sax.StartElementListener;
import android.util.Log;
import android.util.MonthDisplayHelper;
import android.view.MotionEvent;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;
import com.google.android.maps.Projection;

public class BalloonOverlay extends Overlay {
	boolean showWindow = false;
	private static final int picWidth = 20;
	private static final int picHeight = 34;
	private static final int arcR = 8;
	private static final String TAG = "balloonOverlay";
	private static BalloonOverlay myballoonOverlay = null; // 表示当前选中的气球
	private String balloonMessage;
	private GeoPoint geoPoint;
	private MapView mapView;
	private Point point;
	private int xStart, yStart;
	Service service;

	public BalloonOverlay(GeoPoint geoPoint, String string, MapView mapView) {
		this.geoPoint = geoPoint;
		balloonMessage = string;
		this.mapView = mapView;
		Log.d(TAG, "test_this: this1 --> " + this);
		Projection projection = mapView.getProjection();
		point = projection.toPixels(geoPoint, new Point());
		xStart = point.x - picWidth / 2;
		yStart = point.y - picHeight;

		
	}

	@Override
	public void draw(Canvas canvas, MapView mapView, boolean b) {
		// TODO Auto-generated method stub
		// 画气球
		Point point = getBalloonPoint(mapView);
		xStart = point.x - picWidth / 2;
		yStart = point.y - picHeight;
		canvas.drawBitmap(MapActivityDemo.bitmap, xStart, yStart, null);

		Log.d(TAG, "print xStart--> " + xStart);
		Log.d(TAG, "print yStart--> " + yStart);
		// 画经纬度信息显示框
		if (showWindow) {
			drawInfoWindow(canvas, 160);
		}
		super.draw(canvas, mapView, b);
	}

	private Point getBalloonPoint(MapView mapView) {
		// TODO Auto-generated method stub
		Projection projection = mapView.getProjection();
		Point point = new Point();
		projection.toPixels(geoPoint, point);
		return point;
	}

	private void drawInfoWindow(Canvas canvas, int winWidth) {
		// TODO Auto-generated method stub
		int charSize = 16;
		int textSize = 15;
		int leftRightPadding = 3;

		// 有字行宽
		int width = winWidth - 2 * leftRightPadding;
		// 每行最多容纳字符个数
		int maxCharNum = width / charSize;
		// 扫描信息字符串进行分行
		String currentString = "";
		// 记录所有行的ArrayList
		ArrayList<String> strings = new ArrayList<String>();
		balloonMessage = balloonMessage.trim();
		for (int i = 0; i < balloonMessage.length(); i++) {
			char c = balloonMessage.charAt(i);
			if (c != '\n' && currentString.length() < maxCharNum) {
				currentString = currentString + c;
			} else {
				strings.add(currentString);
				currentString = "";
			}
		}
		// 将剩余字符串(最后的不够一行的)存入ArrayList
		if (currentString.length() > 0) {
			strings.add(currentString);
		}

		// 获取总行数
		int totalLines = strings.size();

		// 计算窗口高度
		int height = totalLines * charSize + 2 * arcR;

		// 创建paint对象
		Paint paint = new Paint();
		// 打开抗锯齿，设置文字大小， 绘制圆角矩形()，
		paint.setAntiAlias(true);
		paint.setTextSize(textSize);
		paint.setARGB(255, 255, 251, 239);
		
		Point point = getBalloonPoint(mapView);
		int left = point.x - winWidth / 2;
		int top = point.y - height - picHeight;
		int right = left + winWidth;
		int bottom = top + height;
		RectF rectF = new RectF(left, top, right, bottom);
		canvas.drawRoundRect(rectF, arcR, arcR, paint);

		// 绘制圆角边框
		paint.setColor(Color.YELLOW);
		paint.setStyle(Style.STROKE);
		paint.setStrokeWidth(3);
		canvas.drawRoundRect(rectF, arcR, arcR, paint);

		// 设置字符paint
		paint.setColor(Color.RED);
		paint.setStrokeWidth(0);

		// 循环绘制每行的字符
		int xChar = left + leftRightPadding + 3;
		int yChar = top + arcR + 10;
		for (String tempString : strings) {
			for (int i = 0; i < tempString.length(); i++) {
				String charString = tempString.charAt(i) + "";
				canvas.drawText(charString, xChar, yChar, paint);
				xChar = xChar + charSize;
			}
			xChar = left + leftRightPadding + 3;
			yChar += charSize;
		}

	}

	@Override
	public boolean onTouchEvent(MotionEvent event, MapView mapView) {
		// TODO Auto-generated method stub
		Log.d(TAG, "onTouchEvent.getAction--> " + event.getAction());
		int xScreen, yScreen;
		if (myballoonOverlay != null && myballoonOverlay != this) {
			// 如果当前气球不是自己，什么也不做
			return false;
		}

		if (event.getAction() == MotionEvent.ACTION_DOWN) {
			xScreen = (int) event.getX();
			yScreen = (int) event.getY();

			// 气球左上角的坐标

			if ((xScreen > xStart) && (xScreen < xStart + picWidth)
					&& (yScreen > yStart) && (yScreen < yStart + picHeight)) {
				Log.d(TAG,
						"MotionEvent.ACTION_DOWN --> current object is the balloon");
				myballoonOverlay = this;
				return true;
			}
		} else if (event.getAction() == MotionEvent.ACTION_MOVE) {
			Log.d(TAG, "MotionEvent.ACTION_MOVE --> ");
			return myballoonOverlay != null;
		} else if (event.getAction() == MotionEvent.ACTION_UP) {
			xScreen = (int) event.getX();
			yScreen = (int) event.getY();

			// 如果触点抬起时在 balloon 之上
			if ((xScreen > xStart) && (xScreen < xStart + picWidth)
					&& (yScreen > yStart) && (yScreen < yScreen + picHeight)) {
				Log.d(TAG, "am I executed?");
				myballoonOverlay = null;
				showWindow = !showWindow;
				List<Overlay> overlays = mapView.getOverlays();
				overlays.remove(this);
				overlays.add(this);

				for (Overlay overlay : overlays) {
					if (overlay instanceof BalloonOverlay && overlay != this)
						((BalloonOverlay) overlay).showWindow = false;
				}
				return true;
			} else {
				myballoonOverlay = null;
				return true;
			}
		}

		return false;
	}

}
