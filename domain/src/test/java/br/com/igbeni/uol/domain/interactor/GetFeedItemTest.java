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

package br.com.igbeni.uol.domain.interactor;

import android.support.annotation.NonNull;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.UUID;

import br.com.igbeni.uol.domain.executor.PostExecutionThread;
import br.com.igbeni.uol.domain.executor.ThreadExecutor;
import br.com.igbeni.uol.domain.interactor.GetFeedItem.Params;
import br.com.igbeni.uol.domain.repository.FeedRepository;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.verifyZeroInteractions;

@RunWith(MockitoJUnitRunner.class)
public class GetFeedItemTest {

    private static final String FAKE_FEED_ITEM_ID = UUID.randomUUID().toString();
    @NonNull
    @Rule
    public final ExpectedException expectedException = ExpectedException.none();
    private GetFeedItem getFeedItem;
    @Mock
    private ThreadExecutor mockThreadExecutor;
    @Mock
    private PostExecutionThread mockPostExecutionThread;
    @Mock
    private FeedRepository mockFeedRepository;

    @Before
    public void setUp() {
        getFeedItem = new GetFeedItem(mockFeedRepository, mockThreadExecutor, mockPostExecutionThread);
    }

    @Test
    public void testGetFeedItemUseCaseObservableHappyCase() {
        getFeedItem.buildUseCaseObservable(Params.forItem(FAKE_FEED_ITEM_ID));

        verify(mockFeedRepository).feedItem(FAKE_FEED_ITEM_ID);
        verifyNoMoreInteractions(mockFeedRepository);
        verifyZeroInteractions(mockPostExecutionThread);
        verifyZeroInteractions(mockThreadExecutor);
    }

    @Test
    public void testShouldFailWhenNoOrEmptyParameters() {
        expectedException.expect(NullPointerException.class);
        getFeedItem.buildUseCaseObservable(null);
    }
}