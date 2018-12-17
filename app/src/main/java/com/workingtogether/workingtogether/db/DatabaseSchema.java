package com.workingtogether.workingtogether.db;

public class DatabaseSchema {

    public static final String DATABASE_NAME = "working_together.db";

    public static final class HomeworksTable {

        public static final String TABLE_NAME = "HOMEWORK";

        public static final class Cols {

            public static final String UUID = "id_user";
            public static final String NAME = "name";
            public static final String LASTNAME = "lastname";
            public static final String EMAIL = "email";
            public static final String PASSWORD = "password";
            public static final String CREATEDATE = "createdate";
        }
    }

}
