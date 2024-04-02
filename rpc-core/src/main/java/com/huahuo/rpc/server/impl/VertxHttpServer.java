package com.huahuo.rpc.server.impl;

import com.huahuo.rpc.server.HttpServer;
import io.vertx.core.Vertx;

public class VertxHttpServer implements HttpServer {
    @Override
    public void doStart(int port) {
        Vertx vertx = Vertx.vertx();
        io.vertx.core.http.HttpServer server = vertx.createHttpServer();
        server.requestHandler(new HttpServerHandle());
        server.listen(port, result -> {
            if (result.succeeded()) {
                System.out.println("Server now listening on port: " + port);

            } else {
                System.err.println("Failed to start server: " + result.cause());
            }
        });
    }
}
