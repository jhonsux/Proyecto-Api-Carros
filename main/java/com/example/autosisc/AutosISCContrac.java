package com.example.autosisc;

import android.provider.BaseColumns;

public class AutosISCContrac {
    public AutosISCContrac() {
    }
    public static class Automobil implements BaseColumns{
        public static final String TABLE_NAME = " tbAutomoviles";
        public static final String COLUMN_NAME_FOLIO = " folio";
        public static final String COLUMN_NAME_MARCA=" marca";
        public static final String COLUMN_NAME_MODELO=" modelo";
        public static final String COLUMN_NAME_AÑO=" año";
        public static final String COLUMN_NAME_COLOR=" color";
        public static final String COLUMN_NAME_SERIE=" serie";
        public static final String COLUMN_NAME_PRECIO=" precio";
    }
}

