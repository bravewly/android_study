package com.example.googlemaptest9_5;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Paint.Style;
import android.graphics.drawable.shapes.RoundRectShape;
import android.os.Bundle;
import android.util.Log;
import android.util.MonthDisplayHelper;
import android.view.MotionEvent;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;
import com.google.android.maps.Projection;

public class BalloonOverlay extends Overlay {
	private static final String TAG = "WangBalloonOverlay";
	boolean showWindow;
	final static int picWidth = 20; // 气球图的宽
	final static int picHeight = 34; // 气球图的高度
	final static int arcR = 8;// 信息窗口的圆角半
	static BalloonOverlay currentBallonOverlay = null;
	private String balloonShowMsg;
	private String inputPlaceName;
	GeoPoint mGeoPoint;
	private MapView mapView;
	private int xScreen, yScreen;

	public BalloonOverlay(GeoPoint geoPoint, String balloonShowMsg,
			String inputPlaceName, MapView mapView) {
		mGeoPoint = geoPoint;
		this.balloonShowMsg = balloonShowMsg;
		this.inputPlaceName = inputPlaceName;
		this.mapView = mapView;
	}

	@Override
	public void draw(Canvas canvas, MapView mapView, boolean b) {
		// TODO Auto-generated method stub
		// 画balloon
		Paint paint = new Paint();
		Point point = getPoint(mapView);
		int left = point.x - picWidth / 2;
		int top = point.y - picHeight;
		canvas.drawBitmap(MainActivity.bitmap, left, top, paint);

		// 画窗口
		if (showWindow) {
			drawShowWindow(canvas, paint, 160);
		}

		super.draw(canvas, mapView, b);
	}

	private void drawShowWindow(Canvas canvas, Paint paint, int winWidth) {
		// TODO Auto-generated method stub
		int charSize = 16;
		int textSize = 15;
		int leftRightPadding = 3;
		int textFieldWidth = winWidth - 2 * leftRightPadding; // 可以写字符的行宽
		int maxCharPerLine = textFieldWidth / charSize; // 每行容纳最多字符
		String tempString = "";
		Log.d(TAG, "balloonShowMsg --> " + balloonShowMsg);
		Log.d(TAG, "maxCharPerLine --> " + maxCharPerLine);
		// 将字符串拆成若干行
		ArrayList<String> strings = new ArrayList<String>();
		balloonShowMsg = balloonShowMsg.trim();
		for (int i = 0; i < balloonShowMsg.length(); i++) {
			char c = balloonShowMsg.charAt(i);
			if (c != '\n' && (maxCharPerLine > tempString.length())) {
				tempString += c;
			} else {
				strings.add(tempString);
				tempString = "";
			}

		}
		strings.add(tempString);
		int countLines = strings.size();
		Log.d(TAG, "countLines --> " + countLines);

		Point point = getPoint(mapView);
		int winHeight = 2 * arcR + textSize * countLines;

		// 画圆角矩形框实体
		paint.setColor(Color.GRAY);
		int left = point.x - textFieldWidth / 2;
		int top = point.y - picHeight - winHeight;
		int right = point.x + textFieldWidth / 2;
		int bottom = point.y - picHeight;
		RectF rectFEntity = new RectF(left, top, right, bottom);
		canvas.drawRoundRect(rectFEntity, arcR, arcR, paint);

		// 画圆角矩形框
		paint.setColor(Color.YELLOW);
		paint.setAntiAlias(true);
		paint.setStyle(Style.STROKE);
		paint.setStrokeWidth(3);
		RectF rectFFrame = new RectF();
		rectFFrame.set(left, top, right, bottom);
		canvas.drawRoundRect(rectFFrame, arcR, arcR, paint);

		// 写内容
		paint.setColor(Color.RED);
		paint.setStrokeWidth(0);
		paint.setTextSize(textSize);
		paint.setAntiAlias(true);
		int xChar = left + 3;
		int yChar = point.y - picHeight - winHeight + arcR + 3;
		for (String string : strings) {
			for (int i = 0; i < string.length(); i++) {
				String s = String.valueOf(string.charAt(i));
				canvas.drawText(s, xChar, yChar, paint);
				xChar += charSize;
			}
			xChar = left + 3;
			yChar += charSize;
		}

	}

	@Override
	public boolean onTouchEvent(MotionEvent event, MapView mapView) {
		// TODO Auto-generated method stub
		int xStart;
		int yStart;
		if (currentBallonOverlay != null && currentBallonOverlay != this) {
			return false;
		}

		if (event.getAction() == MotionEvent.ACTION_DOWN) {
			
			xScreen = (int) event.getX();
			yScreen = (int) event.getY();
			Point point = getPoint(mapView);
			xStart = point.x - picWidth / 2;
			yStart = point.y - picHeight;
			if ((xScreen > xStart) && (xScreen < xStart + picWidth)
					&& (yScreen > yStart) && (yScreen < yStart + picWidth)) {
				currentBallonOverlay = this;
				return true;
			}
		} else if (event.getAction() == MotionEvent.ACTION_MOVE) {
			return currentBallonOverlay != null;
		} else if (event.getAction() == MotionEvent.ACTION_UP) {
			Point point = getPoint(mapView);
			xStart = point.x - picWidth / 2;
			yStart = point.y - picHeight;
			if ((xScreen > xStart) && (xScreen < xStart + picWidth)
					&& (yScreen > yStart) && (yScreen < yStart + picWidth)) {
				currentBallonOverlay = null;
				showWindow = !showWindow;
				List<Overlay> overlays = mapView.getOverlays();
				overlays.remove(this);
				overlays.add(this);
				for (Overlay overlay : overlays) {
					if (overlay != this && overlay instanceof BalloonOverlay) {
						((BalloonOverlay) overlay).showWindow = false;
					}
				}
				return true;
			}
		}
		return false;
	}

	private Point getPoint(MapView mapView2) {
		// TODO Auto-generated method stub
		Projection projection = mapView2.getProjection();
		Point point = new Point();
		point = projection.toPixels(mGeoPoint, point);
		return point;
	}
}
