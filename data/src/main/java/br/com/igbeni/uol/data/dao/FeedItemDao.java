package br.com.igbeni.uol.data.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

import br.com.igbeni.uol.data.entity.FeedItemEntity;
import io.reactivex.Flowable;

@Dao
public interface FeedItemDao {

    @Query("SELECT * FROM feed_item")
    Flowable<List<FeedItemEntity>> getFeedItems();

    @Query("SELECT * FROM feed_item WHERE id = :itemId")
    Flowable<FeedItemEntity> getFeedItem(String itemId);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertFeedItemEntity(FeedItemEntity feedItemEntity);

    @Query("DELETE FROM feed_item")
    void deleteAllFeedItemEntities();
}
