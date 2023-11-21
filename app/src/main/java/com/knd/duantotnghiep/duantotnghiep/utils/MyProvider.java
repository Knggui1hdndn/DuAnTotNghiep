package com.knd.duantotnghiep.duantotnghiep.utils;

import androidx.core.content.FileProvider;

import com.knd.duantotnghiep.duantotnghiep.R;

public class MyProvider extends FileProvider {
    public MyProvider( ) {
        super(R.xml.file_paths);
    }
}
