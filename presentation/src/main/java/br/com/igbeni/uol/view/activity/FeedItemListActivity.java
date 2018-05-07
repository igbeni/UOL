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

package br.com.igbeni.uol.view.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;

import br.com.igbeni.uol.R;
import br.com.igbeni.uol.internal.di.HasComponent;
import br.com.igbeni.uol.internal.di.components.DaggerFeedComponent;
import br.com.igbeni.uol.internal.di.components.FeedComponent;
import br.com.igbeni.uol.model.FeedItemModel;
import br.com.igbeni.uol.view.fragment.FeedItemListFragment;

/**
 * Activity that shows a list of FeddItem.
 */
public class FeedItemListActivity extends BaseActivity implements HasComponent<FeedComponent>,
        FeedItemListFragment.FeedItemListener {

    private FeedComponent feedComponent;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed_item_list);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(getString(R.string.app_name));

        this.initializeInjector();

        if (savedInstanceState == null) {
            addFragment(R.id.fragmentContainer, new FeedItemListFragment());
        }
    }

    private void initializeInjector() {
        this.feedComponent = DaggerFeedComponent.builder()
                .applicationComponent(getApplicationComponent())
                .activityModule(getActivityModule())
                .build();
    }

    @Override
    public FeedComponent getComponent() {
        return feedComponent;
    }

    @Override
    public void onUserClicked(@NonNull FeedItemModel feedItemModel) {
        this.navigator.navigateToFeedItem(this, feedItemModel.getId());
    }
}
