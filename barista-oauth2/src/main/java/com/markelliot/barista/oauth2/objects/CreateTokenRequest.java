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

package com.markelliot.barista.oauth2.objects;

import org.immutables.value.Value;

@Value.Immutable
@ImmutablesStagedStyle
public interface CreateTokenRequest {
    String grantType();

    String authorizationCode();

    String callbackUrl();

    OAuth2Authorization authorization();

    static ImmutableCreateTokenRequest.GrantTypeBuildStage builder() {
        return ImmutableCreateTokenRequest.builder();
    }
}