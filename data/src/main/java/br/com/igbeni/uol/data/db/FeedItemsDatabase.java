package br.com.igbeni.uol.data.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import br.com.igbeni.uol.data.dao.FeedItemDao;
import br.com.igbeni.uol.data.entity.FeedItemEntity;

@Database(entities = {FeedItemEntity.class}, version = 1, exportSchema = false)
public abstract class FeedItemsDatabase extends RoomDatabase {

    private static volatile FeedItemsDatabase INSTANCE;

    public static FeedItemsDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (FeedItemsDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            FeedItemsDatabase.class, "feedItem.db")
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    public abstract FeedItemDao feedItemDao();
}
