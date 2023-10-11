package com.knd.duantotnghiep.duantotnghiep.Provider;

import android.content.SearchRecentSuggestionsProvider;

public class MySuggestionProvider extends SearchRecentSuggestionsProvider {
    public final static String AUTHORITY = "com.knd.duantotnghiep.duantotnghiep.Provider";
    public final static int MODE = DATABASE_MODE_QUERIES;

    public MySuggestionProvider() {
        setupSuggestions(AUTHORITY, MODE);
    }
}
