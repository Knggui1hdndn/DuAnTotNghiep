package com.knd.duantotnghiep.duantotnghiep.Dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.knd.duantotnghiep.duantotnghiep.models.SearchLocal;

import java.util.List;
@Dao
public interface SearchDao {
    @Insert
    void insertSearch(SearchLocal searchLocal);
    @Delete
    void deleteSearch(SearchLocal searchLocal);

    @Query("SELECT * FROM SearchLocal")
    List<SearchLocal> getListSearch();
//    @Query("SELECT * FROM SearchLocal WHERE name LIKE ( :searchQuery) ORDER BY _id")
//    LiveData<List<Product>> getSearchDatabase(final String searchQuery);

}
