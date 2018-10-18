package com.alfouz.tfm.tfm.Database;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.migration.Migration;
import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import com.alfouz.tfm.tfm.Database.DAOs.CourseDao;
import com.alfouz.tfm.tfm.Database.DAOs.LessonDao;
import com.alfouz.tfm.tfm.Database.DAOs.MathTaskDao;
import com.alfouz.tfm.tfm.Database.DAOs.MathTaskOptionDao;
import com.alfouz.tfm.tfm.Database.DAOs.UserDao;
import com.alfouz.tfm.tfm.Database.Entities.CourseEntity;
import com.alfouz.tfm.tfm.Database.Entities.LessonEntity;
import com.alfouz.tfm.tfm.Database.Entities.MathTaskEntity;
import com.alfouz.tfm.tfm.Database.Entities.MathTaskOptionEntity;
import com.alfouz.tfm.tfm.Database.Entities.UserEntity;

import java.util.concurrent.Executors;

@Database(entities = {UserEntity.class, CourseEntity.class, LessonEntity.class, MathTaskEntity.class, MathTaskOptionEntity.class}, version = 1)
        public abstract class AppDatabase extends RoomDatabase {
    private static AppDatabase INSTANCE;

    public abstract UserDao userDao();
    public abstract CourseDao courseDao();
    public abstract LessonDao lessonDao();
    public abstract MathTaskDao mathTaskDao();
    public abstract MathTaskOptionDao mathTaskOptionDao();

    public synchronized static AppDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = buildDatabase(context);
        }
        return INSTANCE;
    }

    private static AppDatabase buildDatabase(final Context context) {
        return Room.databaseBuilder(context,
                AppDatabase.class,
                "alfouzMathDatabase")
                .addCallback(new Callback() {
                    @Override
                    public void onCreate(@NonNull SupportSQLiteDatabase database) {
                        super.onCreate(database);
                        final SupportSQLiteDatabase db = database;
                        Executors.newSingleThreadScheduledExecutor().execute(new Runnable() {
                            @Override
                            public void run() {
                                //insertInitialData(db);
                            }
                        });
                    }
                })
                //.addMigrations(MIGRATION_1_2)
                .build();
    }

    /*public static final Migration MIGRATION_1_2 =
            new Migration(1, 2) {
                @Override
                public void migrate(SupportSQLiteDatabase database) {
                    database.execSQL("CREATE TABLE IF NOT EXISTS Challenges (" +
                            "id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT," +
                            "Title TEXT NOT NULL," +
                            "Description TEXT NOT NULL," +
                            "Level INTEGER NOT NULL," +
                            "Objective REAL NOT NULL," +
                            "Operator TEXT NOT NULL," +
                            "Variable TEXT NOT NULL);");

                    database.execSQL("CREATE TABLE IF NOT EXISTS Achievements (" +
                            "idUser INTEGER NOT NULL," +
                            "idChallenge INTEGER NOT NULL," +
                            "FOREIGN KEY(idUser) REFERENCES Users(id) ON UPDATE CASCADE ON DELETE CASCADE," +
                            "FOREIGN KEY(idChallenge) REFERENCES Challenges(id) ON UPDATE CASCADE ON DELETE CASCADE," +
                            "PRIMARY KEY(idUser, idChallenge));");

                    insertInitialData(database);
                }
            };*/

    private static void insertInitialData(SupportSQLiteDatabase database){

        /*database.execSQL("INSERT INTO Users (IdGoogle) VALUES ('-1')");

        database.execSQL("INSERT INTO Courses (Title, Creator, Score, Level) VALUES ('Álgebra 3º ESO', '1', '5', '3')");
        database.execSQL("INSERT INTO Courses (Title, Creator, Score, Level) VALUES ('Ecuaciones 1º ESO', '1', '2', '2')");
        database.execSQL("INSERT INTO Courses (Title, Creator, Score, Level) VALUES ('Física 3º ESO', '1', '5', '4')");

        database.execSQL("INSERT INTO Lessons (Course, Title, Description, Duration, isDone) VALUES ('1', 'Introducción', 'Breve introducción al álgebra', '3', 'true')");
        database.execSQL("INSERT INTO Lessons (Course, Title, Description, Duration, isDone) VALUES ('1', 'Ecuaciones', 'Soluciones a ecuaciones básicas', '2', 'false')");
        database.execSQL("INSERT INTO Lessons (Course, Title, Description, Duration, isDone) VALUES ('1', 'Inecuaciones', 'Aprendiendo sobre desigualdades', '5', 'true')");
        database.execSQL("INSERT INTO Lessons (Course, Title, Description, Duration, isDone) VALUES ('1', 'Sistemas de ecuaciones', 'Resolver varias ecuaciones relacionadas', '6', 'false')");*/
        /*
        //Logros de distancia
        database.execSQL("INSERT INTO Challenges (Title, Description, Level, Objective, Operator, Variable) VALUES ('Hacer un viaje de al menos 10 km', 'Haz un viaje de 10 km de distancia o mas', 3, 10, '>=', 'kms')");
        database.execSQL("INSERT INTO Challenges (Title, Description, Level, Objective, Operator, Variable) VALUES ('Hacer un viaje de al menos 20 km', 'Haz un viaje de 20 km de distancia o mas', 2, 20, '>=', 'kms')");
        database.execSQL("INSERT INTO Challenges (Title, Description, Level, Objective, Operator, Variable) VALUES ('Hacer un viaje de al menos 50 km', 'Haz un viaje de 50 km de distancia o mas', 1, 50, '>=', 'kms')");

        //Logros de eficiencia
        database.execSQL("INSERT INTO Challenges (Title, Description, Level, Objective, Operator, Variable) VALUES ('Hacer un viaje a bajas revoluciones: Menos de 3000 RPM', 'Conducir a altas revoluciones implica un mayor consumo de combustible. Intenta mejorarlo!', 3, 3000, '<=', 'rpm')");
        database.execSQL("INSERT INTO Challenges (Title, Description, Level, Objective, Operator, Variable) VALUES ('Hacer un viaje a bajas revoluciones: Menos de 2800 RPM', 'Conducir a bajas revoluciones ayuda a consumir menos combustible y ser mas eficientes', 2, 2800, '<=', 'rpm')");
        database.execSQL("INSERT INTO Challenges (Title, Description, Level, Objective, Operator, Variable) VALUES ('Hacer un viaje a bajas revoluciones: Menos de 2500 RPM', 'Conducir a 2500 RPM permitira obtener un consumo optimo de combustible', 1, 2500, '<=', 'rpm')");

        database.execSQL("INSERT INTO Challenges (Title, Description, Level, Objective, Operator, Variable) VALUES ('Hacer un viaje con una velocidad media de 15 km/h', 'Cuanto mas baja sea la velocidad media alcanzada el consumo de combustible sera mas elevado. Intenta mejorarlo', 3, 15, '>=', 'speedAVG')");
        database.execSQL("INSERT INTO Challenges (Title, Description, Level, Objective, Operator, Variable) VALUES ('Hacer un viaje con una velocidad media de 30 km/h', 'Una velocidad media mas alta permitira conseguir un consumo de combustible mas eficiente', 2, 30, '>=', 'speedAVG')");
        database.execSQL("INSERT INTO Challenges (Title, Description, Level, Objective, Operator, Variable) VALUES ('Hacer un viaje con una velocidad media de 60 km/h', 'A 60 km/h el consumo de combustible sera mas bajo si lo combinas con uno de los logros relacionado con las RPM', 1, 60, '>=', 'speedAVG')");
    */
    }
}
