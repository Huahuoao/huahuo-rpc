package com.huahuo.rpc.server.impl;

import com.huahuo.rpc.model.RpcRequest;
import com.huahuo.rpc.model.RpcResponse;
import com.huahuo.rpc.registry.LocalRegistry;
import com.huahuo.rpc.serializer.impl.JdkSerializer;
import io.vertx.core.Handler;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.http.HttpServerRequest;
import com.huahuo.rpc.serializer.Serializer;
import io.vertx.core.http.HttpServerResponse;
import lombok.extern.slf4j.Slf4j;


import java.io.IOException;
import java.lang.reflect.Method;

/**
 * 重写vertX Handler，用来处理请求
 */
public class HttpServerHandle implements Handler<HttpServerRequest> {

    @Override
    public void handle(HttpServerRequest request) {
        //选择序列化器
        final Serializer serializer = new JdkSerializer();
        System.out.println("Received request: " + request.method() + " " + request.uri());
        request.bodyHandler(body -> {
            //序列化请求体
            byte[] bytes = body.getBytes();
            RpcRequest rpcRequest = null;
            try {
                rpcRequest = serializer.deserialize(bytes, RpcRequest.class);
            } catch (Exception e) {
                e.printStackTrace();
            }
            RpcResponse rpcResponse = new RpcResponse();
            //请求体为空，特殊处理一下
            if (rpcRequest == null) {
                rpcResponse.setMessage("rpcRequest is null");
                doResponse(request, rpcResponse, serializer);
                return;
            }
            //请求体不为空 就需要构造响应体了
            try {
                //通过反射获取到注册的服务类，再通过这个类获得到真正执行的方法的对象
                Class<?> implClass = LocalRegistry.get(rpcRequest.getServiceName());
                Method method = implClass.getMethod(rpcRequest.getMethodName(), rpcRequest.getParameterTypes());
                //使用Method反射机制来执行这个方法 获得返回值
                Object result = method.invoke(implClass.getDeclaredConstructor().newInstance(), rpcRequest.getArgs());
                rpcResponse.setData(result);
                rpcResponse.setDataType(method.getReturnType());
                rpcResponse.setMessage("ok");
            } catch (Exception e) {
                e.printStackTrace();
                rpcResponse.setMessage(e.getMessage());
                rpcResponse.setException(e);
            }
            doResponse(request, rpcResponse, serializer);
        });
    }

    /**
     * 把结果封装一下返回给客户端
     * @param request
     * @param rpcResponse
     * @param serializer
     */
    protected void doResponse(HttpServerRequest request, RpcResponse rpcResponse, Serializer serializer) {
        HttpServerResponse httpServerResponse = request.response().putHeader("content-type", "application/json");
        try {
            byte[] serialized = serializer.serialize(rpcResponse);
            //end方法就是把结果返回给客户端
            httpServerResponse.end(Buffer.buffer(serialized));
        } catch (IOException e) {
            e.printStackTrace();
            httpServerResponse.end(Buffer.buffer());
        }
    }
}
