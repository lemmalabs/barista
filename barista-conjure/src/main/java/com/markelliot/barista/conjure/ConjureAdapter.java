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

package com.markelliot.barista.conjure;

import com.markelliot.barista.HttpMethod;
import com.markelliot.barista.endpoints.EndpointHandler;
import com.markelliot.barista.endpoints.EndpointRuntime;
import com.markelliot.barista.endpoints.Endpoints;
import com.palantir.conjure.java.undertow.lib.Endpoint;
import com.palantir.conjure.java.undertow.lib.UndertowRuntime;
import com.palantir.conjure.java.undertow.lib.UndertowService;
import io.undertow.server.HttpHandler;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Adapts Conjure endpoints for use with Barista.
 *
 * <p>Example usage:
 *
 * <pre>{@code
 * UndertowRuntime conjureRuntime = ConjureUndertowRuntime.builder().build();
 * Endpoints endpoints = ConjureAdapter.adapt(
 *         SampleServiceEndpoints.of(new DefaultSampleService()), conjureRuntime);
 * Server.builder()
 *         .endpoints(endpoints)
 *         ...
 * }</pre>
 */
public final class ConjureAdapter {
    private ConjureAdapter() {}

    public static Endpoints adapt(UndertowService service, UndertowRuntime runtime) {
        Set<EndpointHandler> handlers =
                service.endpoints(runtime).stream()
                        .map(ConjureAdapter::fromConjureEndpoint)
                        .collect(Collectors.toSet());
        return () -> handlers;
    }

    private static EndpointHandler fromConjureEndpoint(Endpoint endpoint) {
        return new EndpointHandler() {
            @Override
            public HttpMethod method() {
                return switch (endpoint.method().toString()) {
                    case "GET" -> HttpMethod.GET;
                    case "PUT" -> HttpMethod.PUT;
                    case "POST" -> HttpMethod.POST;
                    case "DELETE" -> HttpMethod.DELETE;
                    default -> throw new IllegalStateException(
                            "Unsupported HTTP method " + endpoint.method());
                };
            }

            @Override
            public String route() {
                return endpoint.template();
            }

            @Override
            public HttpHandler handler(EndpointRuntime runtime) {
                return exchange -> {
                    exchange.startBlocking();
                    endpoint.handler().handleRequest(exchange);
                };
            }
        };
    }
}
