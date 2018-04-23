package br.com.igbeni.uol.data.net;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import java.net.MalformedURLException;

import br.com.igbeni.uol.data.entity.FeedEntity;
import br.com.igbeni.uol.data.entity.mapper.FeedEntityJsonMapper;
import br.com.igbeni.uol.data.exception.NetworkConnectionException;
import io.reactivex.Observable;

/**
 * {@link RestApi} implementation for retrieving data from the network.
 */
public class RestApiImpl implements RestApi {

    private final Context context;
    private FeedEntityJsonMapper feedEntityJsonMapper;

    /**
     * Constructor of the class
     *
     * @param context                  {@link Context}.
     * @param feedEntityJsonMapper
     */
    public RestApiImpl(Context context, FeedEntityJsonMapper feedEntityJsonMapper) {
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
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        isConnected = (networkInfo != null && networkInfo.isConnectedOrConnecting());

        return isConnected;
    }

    @Override
    public Observable<FeedEntity> feedEntity() {
        return Observable.create(emitter -> {
            if (isThereInternetConnection()) {
                try {
                    String responseFeedEntity = getFeedEntityFromApi();
                    if (responseFeedEntity != null) {
                        emitter.onNext(feedEntityJsonMapper.transformFromEntity(responseFeedEntity));
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
        });
    }

    private String getFeedEntityFromApi() throws MalformedURLException {
        return ApiConnection.createGET(API_BASE_URL).requestSyncCall();
    }
}