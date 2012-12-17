package com.example.exercise7_1;

import android.os.Parcel;
import android.os.Parcelable;
import java.io.Serializable;

public class Employee implements Serializable {
	Employee(String name, String post, int no, int workyears) {
		super();
		this.name = name;
		this.post = post;
		this.no = no;
		this.workyears = workyears;
	}

	private String name;
	private String post;
	private int no, workyears;

	String getName() {
		return name;
	}

	void setName(String name) {
		this.name = name;
	}

	String getPost() {
		return post;
	}

	void setPost(String post) {
		this.post = post;
	}

	int getNo() {
		return no;
	}

	void setNo(int no) {
		this.no = no;
	}

	int getWorkyears() {
		return workyears;
	}

	void setWorkyears(int workyears) {
		this.workyears = workyears;
	}
}
