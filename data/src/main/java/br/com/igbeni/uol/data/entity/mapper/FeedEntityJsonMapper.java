package br.com.igbeni.uol.data.entity.mapper;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

import javax.inject.Inject;

import br.com.igbeni.uol.data.entity.FeedEntity;
import br.com.igbeni.uol.data.entity.FeedItemEntity;

public class FeedEntityJsonMapper implements JsonMapper<FeedEntity, String> {

    private final Gson gson;

    @Inject
    public FeedEntityJsonMapper() {
        this.gson = new Gson();
    }

    @Override
    public FeedEntity transformFromEntity(String feedJsonResponse) {
        final Type feedEntityType = new TypeToken<FeedEntity>() {
        }.getType();
        return this.gson.fromJson(feedJsonResponse, feedEntityType);
    }

    public List<FeedItemEntity> transformToListFromEntity(String feedJsonResponse) {
        final Type feedEntityType = new TypeToken<FeedEntity>() {
        }.getType();
        FeedEntity feedEntity = this.gson.fromJson(feedJsonResponse, feedEntityType);
        return feedEntity.getFeedItemEntities();
    }
}
