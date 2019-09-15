package com.workingtogether.android.database;

/**
 * This class provides layout schema for all database tables
 * as well as global constants related to database
 *
 * @author Carlos Alberto Arroyo Martínez <carlosarroyoam@gmail.com>
 */
public class DatabaseSchema {

	static final String DATABASE_NAME = "working_together.db";
	static final int DB_VERSION = 1;

    public static class HomeworksTable {

        public static final String TABLE_NAME = "homeworks";

        public static class Cols {
            public static final String UUID = "_id";
            public static final String TITLE = "title";
            public static final String DESCRIPTION = "description";
            public static final String CREATED_AT = "created_at";
            public static final String UPDATED_AT = "updated_at";
            public static final String DELIVERY_DATE = "delivery_date";
        }
    }

    public static class ActivitiesTable {

        public static final String TABLE_NAME = "Activities";

        public static class Cols {
            public static final String UUID = "_id";
            public static final String TITLE = "title";
            public static final String DESCRIPTION = "description";
            public static final String CREATED_AT = "created_at";
            public static final String UPDATED_AT = "updated_at";
            public static final String DELIVERY_DATE = "delivery_date";
        }
    }

}
