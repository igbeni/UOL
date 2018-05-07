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

import com.fernandocejas.arrow.checks.Preconditions;

import org.jetbrains.annotations.NotNull;

import javax.inject.Inject;

import br.com.igbeni.uol.domain.FeedItem;
import br.com.igbeni.uol.domain.executor.PostExecutionThread;
import br.com.igbeni.uol.domain.executor.ThreadExecutor;
import br.com.igbeni.uol.domain.repository.FeedRepository;
import io.reactivex.Flowable;

public class GetFeedItem extends FlowableUseCase<FeedItem, GetFeedItem.Params> {

    private final FeedRepository feedRepository;

    @Inject
    GetFeedItem(FeedRepository feedRepository, ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread) {
        super(threadExecutor, postExecutionThread);
        this.feedRepository = feedRepository;
    }

    @Override
    Flowable<FeedItem> buildUseCaseObservable(@NotNull Params params) {
        Preconditions.checkNotNull(params);
        return this.feedRepository.feedItem(params.itemId);
    }

    public static final class Params {

        private final String itemId;

        Params(String itemId) {
            this.itemId = itemId;
        }

        public static Params forItem(String itemId) {
            return new Params(itemId);
        }
    }
}
