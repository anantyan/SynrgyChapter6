package id.anantyan.foodapps.di

import android.content.Context
import androidx.room.Room
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import id.anantyan.foodapps.data.local.dao.FoodsDao
import id.anantyan.foodapps.data.local.dao.UsersDao
import id.anantyan.foodapps.data.local.database.RoomDB
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object LocalModule {

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context): RoomDB {
        return Room.databaseBuilder(
            context.applicationContext,
            RoomDB::class.java,
            "db_app"
        ).addMigrations(MIGRATION_1_2, MIGRATION_2_3).fallbackToDestructiveMigration().build()
    }

    private val MIGRATION_1_2: Migration = object : Migration(1, 2) {
        override fun migrate(db: SupportSQLiteDatabase) {
            db.execSQL("CREATE TABLE users_new (id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, username TEXT, email TEXT, password TEXT)")
            db.execSQL("INSERT INTO users_new (id, username, email, password) SELECT id, username, email, password FROM tbl_users")
            db.execSQL("DROP TABLE tbl_users")
            db.execSQL("ALTER TABLE users_new RENAME TO tbl_users")
        }
    }

    private val MIGRATION_2_3: Migration = object : Migration(2, 3) {
        override fun migrate(db: SupportSQLiteDatabase) {
            db.execSQL("CREATE TABLE users_new (id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, username TEXT, email TEXT, password TEXT, image TEXT)")
            db.execSQL("INSERT INTO users_new (id, username, email, password) SELECT id, username, email, password FROM tbl_users")
            db.execSQL("DROP TABLE tbl_users")
            db.execSQL("ALTER TABLE users_new RENAME TO tbl_users")

            db.execSQL("CREATE TABLE foods_new (readyInMinutes INTEGER, image TEXT, servings INTEGER, id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, title TEXT, userId INTEGER)")
            db.execSQL("INSERT INTO foods_new (readyInMinutes, image, servings, id, title, userId) SELECT readyInMinutes, image, servings, id, title, userId FROM tbl_foods")
            db.execSQL("DROP TABLE tbl_foods")
            db.execSQL("ALTER TABLE foods_new RENAME TO tbl_foods")
        }
    }

    @Provides
    fun provideUsersDao(roomDB: RoomDB): UsersDao {
        return roomDB.usersDao()
    }

    @Provides
    fun provideFoodsDao(roomDB: RoomDB): FoodsDao {
        return roomDB.foodsDao()
    }
}