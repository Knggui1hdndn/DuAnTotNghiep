package com.knd.duantotnghiep.duantotnghiep.ui;

import androidx.appcompat.app.AppCompatActivity;



import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.provider.SearchRecentSuggestions;
import android.widget.SearchView;
import android.widget.Toast;


import com.knd.duantotnghiep.duantotnghiep.Provider.MySuggestionProvider;
import com.knd.duantotnghiep.duantotnghiep.R;

public class SearchActivity extends AppCompatActivity {
SearchView searchView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

         searchView = findViewById(R.id.search);
         SearchManager searchManager = (SearchManager) getSystemService(SEARCH_SERVICE);
         searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
         searchView.setSubmitButtonEnabled(true);

        Intent intent  = getIntent();

        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            SearchRecentSuggestions suggestions = new SearchRecentSuggestions(this, MySuggestionProvider.AUTHORITY, MySuggestionProvider.MODE);
            suggestions.saveRecentQuery(query, null);
            Toast.makeText(this, query, Toast.LENGTH_SHORT).show();
        }
    }


}