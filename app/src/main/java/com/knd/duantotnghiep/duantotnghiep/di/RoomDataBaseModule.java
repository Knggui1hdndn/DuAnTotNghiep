package com.knd.duantotnghiep.duantotnghiep.di;

import android.content.Context;

import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.knd.duantotnghiep.duantotnghiep.Dao.SearchDao;
import com.knd.duantotnghiep.duantotnghiep.room.MyRoomDataBase;
import com.knd.duantotnghiep.duantotnghiep.utils.Constants;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.qualifiers.ApplicationContext;
import dagger.hilt.components.SingletonComponent;

@InstallIn(SingletonComponent.class)
@Module
public class RoomDataBaseModule {
    @Provides
    @Singleton
    public MyRoomDataBase providesRoomDataBase(@ApplicationContext Context context) {
        return Room.databaseBuilder(context, MyRoomDataBase.class, Constants.NAME_DATABASE).allowMainThreadQueries().build();
    }

    @Provides
    @Singleton
    public SearchDao providesSearchDao(MyRoomDataBase myRoomDataBase){
        return  myRoomDataBase.searchDao();
    }
}
