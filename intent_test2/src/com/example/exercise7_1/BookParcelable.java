package com.example.exercise7_1;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

public class BookParcelable implements Parcelable {
	private String bookName;
	private String author;
	private int publishTime;

	String getBookName() {
		return bookName;
	}

	void setBookName(String bookName) {
		this.bookName = bookName;
	}

	String getAuthor() {
		return author;
	}

	void setAuthor(String author) {
		this.author = author;
	}

	int getPublishTime() {
		return publishTime;
	}

	void setPublishTime(int publishTime) {
		this.publishTime = publishTime;
	}

	public static final Parcelable.Creator<BookParcelable> CREATOR = new Creator<BookParcelable>() {

		@Override
		public BookParcelable createFromParcel(Parcel source) {
			// TODO Auto-generated method stub
			Log.d("test", "3. createFromParcel");
			BookParcelable bookParcelable = new BookParcelable();
			bookParcelable.setAuthor(source.readString());
			bookParcelable.setBookName(source.readString());
			bookParcelable.setPublishTime(source.readInt());
			return bookParcelable;
		}

		@Override
		public BookParcelable[] newArray(int size) {
			// TODO Auto-generated method stub
			return null;
		}
		
	};
	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		// TODO Auto-generated method stub
		Log.d("test", "4. writeToParcel...write...sth.");
		dest.writeString(author);
		dest.writeString(bookName);
		dest.writeInt(publishTime);
	}

}
