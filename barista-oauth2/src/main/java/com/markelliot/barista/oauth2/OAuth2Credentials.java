/*
 * (c) Copyright 2022 Mark Elliot. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.markelliot.barista.oauth2;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.palantir.tokens.auth.BearerToken;
import java.util.Optional;
import org.immutables.value.Value;

@JsonDeserialize(as = ImmutableOAuth2Credentials.class)
@JsonSerialize(as = ImmutableOAuth2Credentials.class)
@JsonIgnoreProperties(ignoreUnknown = true)
@Value.Immutable
public interface OAuth2Credentials {

    @JsonProperty("access_token")
    BearerToken bearerToken();

    @JsonProperty("refresh_token")
    Optional<String> refreshToken();

    @JsonProperty("expires_in")
    int expiresIn();

    static Builder builder() {
        return new Builder();
    }

    final class Builder extends ImmutableOAuth2Credentials.Builder {}
}
