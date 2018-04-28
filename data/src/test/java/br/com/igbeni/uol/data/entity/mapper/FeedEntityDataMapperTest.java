package br.com.igbeni.uol.data.entity.mapper;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

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
        assertThat(feed.getFeedItems().toArray()[0], is(instanceOf(FeedItem.class)));
        assertThat(feed.getFeedItems().toArray()[1], is(instanceOf(FeedItem.class)));
        assertThat(feed.getFeedItems().size(), is(2));
    }

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