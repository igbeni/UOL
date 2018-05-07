/*
 * (C) Copyright 2018.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *
 *  Contributors:
 *      Iggor Alves
 */

package br.com.igbeni.uol.data.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.support.annotation.NonNull;

import br.com.igbeni.uol.data.dao.FeedItemDao;
import br.com.igbeni.uol.data.entity.FeedItemEntity;

@Database(entities = {FeedItemEntity.class}, version = 1, exportSchema = false)
public abstract class FeedItemsDatabase extends RoomDatabase {

    private static volatile FeedItemsDatabase INSTANCE;

    public static FeedItemsDatabase getInstance(@NonNull Context context) {
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
