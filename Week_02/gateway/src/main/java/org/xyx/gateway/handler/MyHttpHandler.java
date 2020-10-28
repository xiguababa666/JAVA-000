package org.xyx.gateway.handler;

import com.alibaba.fastjson.JSON;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.*;
import io.netty.util.CharsetUtil;
import org.xyx.gateway.dto.Data;
import org.xyx.gateway.restful.HttpClientTest;

/**
 * description
 *
 * @author xyx
 * @date 2020/10/28 16:41
 */
public class MyHttpHandler extends SimpleChannelInboundHandler<FullHttpRequest> {


    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("client address:" + ctx.channel().remoteAddress());
        super.channelActive(ctx);
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, FullHttpRequest msg) throws Exception {
        System.out.println("msg:" + msg);

        String uri = msg.uri();
        HttpMethod method = msg.method();

        //Data data = new Data();
        //data.setCode(0);
        //data.setMessage("ok");
        String data = "";

        if (HttpMethod.GET.equals(method)) {
            if ("/test".equals(uri)) {
                //data.setData("test get");
                data = (String) HttpClientTest.test();
                //data.setData(s);

            } else {
                DefaultFullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.BAD_REQUEST);
                ctx.writeAndFlush(response).addListener(ChannelFutureListener.CLOSE);
                return;
            }
        } else if(HttpMethod.POST.equals(method)) {
            if ("/test".equals(uri)) {
                //data.setData("test post");
            } else {
                DefaultFullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.BAD_REQUEST);
                ctx.writeAndFlush(response).addListener(ChannelFutureListener.CLOSE);
                return;
            }
        }

        System.out.println("response data:" + JSON.toJSONString(data));

        DefaultFullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1,
                HttpResponseStatus.OK,
                Unpooled.copiedBuffer(data, CharsetUtil.UTF_8));

        response.headers()
                //.add(HttpHeaderNames.CONTENT_TYPE, "application/json;charset=UTF-8");
                .add(HttpHeaderNames.CONTENT_TYPE, "text/html;charset=UTF-8");


        ctx.writeAndFlush(response).addListener(ChannelFutureListener.CLOSE);

    }


    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        System.out.println("channelReadComplete");
        super.channelReadComplete(ctx);
        ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        System.out.println("exceptionCaught");
        if (null != cause) {
            cause.printStackTrace();
        }
        if (null != ctx) {
            ctx.close();
        }
    }


}
