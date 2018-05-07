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

package br.com.igbeni.uol.data.net;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.Nullable;

import java.net.MalformedURLException;
import java.util.List;
import java.util.Objects;

import br.com.igbeni.uol.data.entity.FeedItemEntity;
import br.com.igbeni.uol.data.entity.mapper.FeedEntityJsonMapper;
import br.com.igbeni.uol.data.exception.NetworkConnectionException;
import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;

/**
 * {@link RestApi} implementation for retrieving data from the network.
 */
public class RestApiImpl implements RestApi {

    private final Context context;
    @Nullable
    private FeedEntityJsonMapper feedEntityJsonMapper;

    /**
     * Constructor of the class
     *
     * @param context              {@link Context}.
     * @param feedEntityJsonMapper
     */
    public RestApiImpl(@Nullable Context context, @Nullable FeedEntityJsonMapper feedEntityJsonMapper) {
        if (context == null || feedEntityJsonMapper == null) {
            throw new IllegalArgumentException("The constructor parameters cannot be null!!!");
        }
        this.context = context.getApplicationContext();
        this.feedEntityJsonMapper = feedEntityJsonMapper;
    }

    /**
     * Checks if the device has any active internet connection.
     *
     * @return true device with internet connection, otherwise false.
     */
    private boolean isThereInternetConnection() {
        boolean isConnected;

        ConnectivityManager connectivityManager =
                (ConnectivityManager) this.context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = Objects.requireNonNull(connectivityManager).getActiveNetworkInfo();
        isConnected = (networkInfo != null && networkInfo.isConnectedOrConnecting());

        return isConnected;
    }

    @Override
    public Flowable<List<FeedItemEntity>> feedItemEntities() {
        return Flowable.create(emitter -> {
            if (isThereInternetConnection()) {
                try {
                    String responseFeedEntity = getFeedEntityFromApi();
                    if (responseFeedEntity != null) {
                        emitter.onNext(Objects.requireNonNull(feedEntityJsonMapper).transformToListFromEntity(responseFeedEntity));
                        emitter.onComplete();
                    } else {
                        emitter.onError(new NetworkConnectionException());
                    }
                } catch (Exception e) {
                    emitter.onError(new NetworkConnectionException(e.getCause()));
                }
            } else {
                emitter.onError(new NetworkConnectionException());
            }
        }, BackpressureStrategy.BUFFER);
    }

    @Nullable
    private String getFeedEntityFromApi() throws MalformedURLException {
        return ApiConnection.createGET(API_BASE_URL).requestSyncCall();
    }
}