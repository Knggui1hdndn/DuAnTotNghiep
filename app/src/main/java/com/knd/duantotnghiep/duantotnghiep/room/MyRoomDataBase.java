package com.knd.duantotnghiep.duantotnghiep.room;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.knd.duantotnghiep.duantotnghiep.Dao.SearchDao;
import com.knd.duantotnghiep.duantotnghiep.models.SearchLocal;

import java.sql.Array;

@Database(entities = {SearchLocal.class},version = 1,exportSchema = false)
public abstract class MyRoomDataBase extends RoomDatabase {
   public abstract SearchDao searchDao();
}
