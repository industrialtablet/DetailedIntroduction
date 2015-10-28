package com.example.sql;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class CommentsSQLiteOpenHelper extends SQLiteOpenHelper {

	private static final String DBNAME = "comments.db";
	private static final int VERSION = 1;

	public CommentsSQLiteOpenHelper(Context context) {
		super(context, DBNAME, null, VERSION);
	}

	public void onCreate(SQLiteDatabase db) {
		db.execSQL("create table comments(id integer primary key,agentid varchar(20),veryGood integer ,good integer ,general integer ,bad integer)");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

	}

}