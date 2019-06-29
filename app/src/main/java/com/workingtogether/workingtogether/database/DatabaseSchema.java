package com.workingtogether.workingtogether.database;

public class DatabaseSchema {

    public static final String DATABASE_NAME = "working_together.db";

    public static final class HomeworksTable {

        public static final String TABLE_NAME = "homeworks";

        public static final class Cols {
            public static final String UUID = "id";
            public static final String FIRSTNAME = "fisrt_name";
            public static final String LASTNAME = "last_name";
            public static final String EMAIL = "email";
            public static final String PASSWORD = "password";
            public static final String CREATED_AT = "created_at";
            public static final String UPDATED_AT = "updated_at";
        }
    }
}
