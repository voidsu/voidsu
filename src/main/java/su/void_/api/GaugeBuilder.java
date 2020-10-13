/*
 * Copyright 2020 Dmitry Romanyuta
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package su.void_.api;

import java.time.Instant;

public class GaugeBuilder {
    private String metricName = null;
    private String metricValue = null;
    private String labelName = null;
    private String labelValue = null;

    public static GaugeBuilder create() {
        return new GaugeBuilder();
    }

    public GaugeBuilder metric(String name, String value) {
        this.metricName = name;
        this.metricValue = value;
        return this;
    }

    public GaugeBuilder label(String name, String value) {
        this.labelName = name;
        this.labelValue = value;
        return this;
    }

    public long timestamp() {
        return Instant.now().toEpochMilli();
    }

    @Override
    public String toString() {
        return this.metricName + "{" + this.labelName + "=\"" + this.labelValue + "\"} " + this.metricValue + " " + this.timestamp() + "\n";
    }
}
