/*
 * Copyright (C) 2007 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.android.snake;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * TileView: a View-variant designed for handling arrays of "icons" or other
 * drawables.
 * 
 */
public class TileView extends View {
	private static final String TAG = "WangTileView";

	/**
	 * Parameters controlling the size of the tiles and their range within view.
	 * Width/Height are in pixels, and Drawables will be scaled to fit to these
	 * dimensions. X/Y Tile Counts are the number of tiles that will be drawn.
	 */

	protected static int mTileSize;

	protected static int mXTileCount;
	protected static int mYTileCount;

	protected static int mXOffset;
	protected static int mYOffset;

	/**
	 * A hash that maps integer handles specified by the sub classer to the draw
	 * able that will be used for that reference. 存放游戏中需要显示内容的
	 * tiles。包括，墙壁、蛇身和随机出现的苹果.
	 */
	private Bitmap[] mTileArray;

	/**
	 * A two-dimensional array of integers in which the number represents the
	 * index of the tile that should be drawn at that locations. 存放游戏中需要显示内容的
	 * tiles 的坐标。包括，墙壁、蛇身和随机出现的苹果.
	 */
	private int[][] mTileGrid;

	private final Paint mPaint = new Paint();

	public TileView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);

		TypedArray a = context.obtainStyledAttributes(attrs,
				R.styleable.TileView);

		mTileSize = a.getInt(R.styleable.TileView_tileSize, 12);

		a.recycle();
	}

	public TileView(Context context, AttributeSet attrs) {
		super(context, attrs);

		TypedArray a = context.obtainStyledAttributes(attrs,
				R.styleable.TileView);

		mTileSize = a.getInt(R.styleable.TileView_tileSize, 12);
		a.recycle();
	}

	/**
	 * Rests the internal array of Bitmaps used for drawing tiles, and sets the
	 * maximum index of tiles to be inserted
	 * 
	 * @param tilecount
	 */

	public void resetTiles(int tilecount) {
		mTileArray = new Bitmap[tilecount];
	}

	/**
	 * 计算并获取游戏界面的基本数据.
	 * 
	 * @param w
	 *            当前视图的宽度，在这里也就是屏幕的宽度.
	 * @param h
	 *            同上.
	 */
	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		Log.d(TAG, "onSizeChanged()");
		Log.d(TAG, "w --> " + w);
		Log.d(TAG, "h --> " + h);
		Log.d(TAG, "oldw --> " + oldw);
		Log.d(TAG, "oldh --> " + oldh);
		Log.d(TAG, "mTileSize --> " + mTileSize);

		mXTileCount = (int) Math.floor(w / mTileSize);
		mYTileCount = (int) Math.floor(h / mTileSize);

		Log.d(TAG, "mXTileCount --> " + mXTileCount);
		Log.d(TAG, "mYTileCount --> " + mYTileCount);

		mXOffset = ((w - (mTileSize * mXTileCount)) / 2);
		mYOffset = ((h - (mTileSize * mYTileCount)) / 2);

		Log.d(TAG, "mXOffset --> " + mXOffset);
		Log.d(TAG, "mYOffset --> " + mYOffset);

		mTileGrid = new int[mXTileCount][mYTileCount];

		// 将mTileGrid 的所有元素值设置为0.
		clearTiles();
	}

	/**
	 * Function to set the specified Drawable as the tile for a particular
	 * integer key.
	 * 
	 * @param key
	 * @param tile
	 */
	public void loadTile(int key, Drawable tile) {
		Bitmap bitmap = Bitmap.createBitmap(mTileSize, mTileSize,
				Bitmap.Config.ARGB_8888);
		Canvas canvas = new Canvas(bitmap);
		tile.setBounds(0, 0, mTileSize, mTileSize);
		tile.draw(canvas);

		mTileArray[key] = bitmap;

	}

	/**
	 * Resets all tiles to 0 (empty)
	 * 
	 */
	public void clearTiles() {
		for (int x = 0; x < mXTileCount; x++) {
			for (int y = 0; y < mYTileCount; y++) {
				setTile(0, x, y);
			}
		}
	}

	/**
	 * Used to indicate that a particular tile (set with loadTile and referenced
	 * by an integer) should be drawn at the given x/y coordinates during the
	 * next invalidate/draw cycle.
	 * 
	 * @param tileindex
	 * @param x
	 * @param y
	 */
	public void setTile(int tileindex, int x, int y) {
		mTileGrid[x][y] = tileindex;
	}

	@Override
	public void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		Log.d(TAG, "onDraw()");
		// 对整个游戏屏幕的 tiles 进行遍历.
		for (int x = 0; x < mXTileCount; x += 1) {
			for (int y = 0; y < mYTileCount; y += 1) {

				// Draw the game screen, include snake's body, random apples and
				// the wall..
				if (mTileGrid[x][y] > 0) {
					canvas.drawBitmap(mTileArray[mTileGrid[x][y]], mXOffset + x
							* mTileSize, mYOffset + y * mTileSize, mPaint);
				}
			}
		}

	}

}
