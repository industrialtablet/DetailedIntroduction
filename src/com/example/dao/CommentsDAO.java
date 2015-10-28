package com.example.dao;

import java.util.ArrayList;
import java.util.List;

import com.example.sql.CommentsSQLiteOpenHelper;
import com.example.uitl.Comments;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class CommentsDAO {
	private CommentsSQLiteOpenHelper helper;
	
	public CommentsDAO(Context context){
		helper = new CommentsSQLiteOpenHelper(context);
	}
	
	//添加
	public long addComment(String agentid , int veryGood ,int good , int general , int bad){
		SQLiteDatabase db = helper.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put("agentid", agentid);
		values.put("veryGood", veryGood);
		values.put("good", good);
		values.put("general", general);
		values.put("bad", bad);
		long id =db.insert("comments", null, values);
		db.close();
		return id;
	}
	
	//查
	public Comments findComment(String agentid){
		SQLiteDatabase db = helper.getReadableDatabase();
		Cursor cursor = db.query("comments", null, "agentid = ?", new String []{agentid}, null, null, null);
		if(cursor.moveToNext()){
			return new Comments(cursor.getInt(cursor.getColumnIndex("id")),
					cursor.getString(cursor.getColumnIndex("agentid")),
					cursor.getInt(cursor.getColumnIndex("veryGood")),
					cursor.getInt(cursor.getColumnIndex("good")),
					cursor.getInt(cursor.getColumnIndex("general")),
					cursor.getInt(cursor.getColumnIndex("bad")));
		}
		cursor.close();
		db.close();
		return null;
	}
	
	//更新
	public void updateComment(Comments comments , String agentid){
		SQLiteDatabase db = helper.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put("agentid", comments.getAgentid());
		values.put("veryGood", comments.getVeryGood());
		values.put("good", comments.getGood());
		values.put("general", comments.getGeneral());
		values.put("bad", comments.getBad());
		db.update("comments", values, "agentid=?", new String[]{agentid});
		db.close();
	}
	
	public int deleteComment(String agentid){
		SQLiteDatabase db = helper.getWritableDatabase();
		int dd=db.delete("comments", "agentid=?", new String[]{agentid});
		db.close();
		return dd;
	}
	
	public List<Comments> findAllComment(){
		SQLiteDatabase db = helper.getReadableDatabase();
		Cursor cursor = db.query("comments", new String[]{ "agentid" , "id" , "veryGood" , "good" , "general" , "bad"}, null, null, null, null, null);
		List<Comments> comments = new ArrayList<Comments>();
		while (cursor.moveToNext()) {
			int id = cursor.getInt(cursor.getColumnIndex("id"));
			String agentid = cursor.getString(cursor.getColumnIndex("agentid"));
			int veryGood = cursor.getInt(cursor.getColumnIndex("veryGood"));
			int good = cursor.getInt(cursor.getColumnIndex("good"));
			int general = cursor.getInt(cursor.getColumnIndex("general"));
			int bad = cursor.getInt(cursor.getColumnIndex("bad"));
			
			Comments c = new Comments(id ,agentid, veryGood, good, general, bad);
			comments.add(c);
		}
		cursor.close();
		db.close();
		return comments;
	}
}
