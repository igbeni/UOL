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

package br.com.igbeni.uol.navigation;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;

import javax.inject.Inject;

import br.com.igbeni.uol.view.activity.FeedItemDetailsActivity;

/**
 * Class used to navigate through the application.
 */
public class Navigator {

    @Inject
    public Navigator() {
        //empty
    }

    public void navigateToFeedItem(@Nullable Context context, String itemId) {
        if (context != null) {
            Intent intentToLaunch = FeedItemDetailsActivity.getCallingIntent(context, itemId);
            context.startActivity(intentToLaunch);
        }
    }
}
