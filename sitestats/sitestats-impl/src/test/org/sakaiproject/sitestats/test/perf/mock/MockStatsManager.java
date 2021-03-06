/**
 * Copyright (c) 2006-2017 The Apereo Foundation
 *
 * Licensed under the Educational Community License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *             http://opensource.org/licenses/ecl2
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.sakaiproject.sitestats.test.perf.mock;

import org.sakaiproject.sitestats.api.SitePresenceTotal;
import org.sakaiproject.sitestats.api.StatsManager;

import java.util.HashMap;
import java.util.Map;

public abstract class MockStatsManager implements StatsManager {

	@Override
	public Map<String, SitePresenceTotal> getPresenceTotalsForSite(final String siteId) {

		final Map<String, SitePresenceTotal> totals = new HashMap<String, SitePresenceTotal>();
		return totals;
	}

}
