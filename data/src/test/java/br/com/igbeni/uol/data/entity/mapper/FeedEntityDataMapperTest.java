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

package br.com.igbeni.uol.data.entity.mapper;

import android.support.annotation.NonNull;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import br.com.igbeni.uol.data.entity.FeedEntity;
import br.com.igbeni.uol.data.entity.FeedItemEntity;
import br.com.igbeni.uol.domain.Feed;
import br.com.igbeni.uol.domain.FeedItem;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.mock;

@RunWith(MockitoJUnitRunner.class)
public class FeedEntityDataMapperTest {

    private FeedEntityDataMapper feedEntityDataMapper;

    @Before
    public void setUp() {
        feedEntityDataMapper = new FeedEntityDataMapper();
    }

    @Test
    public void testTransformFeedEntity() {
        FeedEntity feedEntity = createFakeFeedEntity();
        Feed feed = feedEntityDataMapper.transform(feedEntity);

        assertThat(feed, is(instanceOf(Feed.class)));
        assertThat(Objects.requireNonNull(feed).getFeedItems().toArray()[0], is(instanceOf(FeedItem.class)));
        assertThat(feed.getFeedItems().toArray()[1], is(instanceOf(FeedItem.class)));
        assertThat(feed.getFeedItems().size(), is(2));
    }

    @NonNull
    private FeedEntity createFakeFeedEntity() {
        FeedEntity feedEntity = new FeedEntity();

        FeedItemEntity mockFeedItemEntityOne = mock(FeedItemEntity.class);
        FeedItemEntity mockFeedItemEntityTwo = mock(FeedItemEntity.class);

        List<FeedItemEntity> feedItemEntities = new ArrayList<>(5);
        feedItemEntities.add(mockFeedItemEntityOne);
        feedItemEntities.add(mockFeedItemEntityTwo);

        feedEntity.setFeedItemEntities(feedItemEntities);

        return feedEntity;
    }
}