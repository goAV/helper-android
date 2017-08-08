/*
 * Copyright (c) 2017.
 * By chinaume@163.com
 */

package com.goav.parser.okhttp;


import com.goav.parser.ParserConst;
import com.orhanobut.logger.Logger;

import java.io.EOFException;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.UnsupportedCharsetException;

import okhttp3.Headers;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okhttp3.internal.Util;
import okhttp3.internal.http.HttpHeaders;
import okio.Buffer;
import okio.BufferedSink;
import okio.BufferedSource;

/**
 * 网络 <b>request</b> 和 <b>response</b> 信息打印<br/>
 * 全局打印 <b>GET</b> 和 <b>POST</b>
 */
public final class GoLoggingInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();

        if (!ParserConst.DEBUG) {
            return chain.proceed(request);
        }

        try {
            if (request.method().equals("POST")) {
                BufferedSink sink = new Buffer().emit();
                RequestBody body = request.body();
                body.writeTo(sink);
                Buffer buffer = sink.buffer();
                String s;
                StringBuffer stringBuffer = new StringBuffer();
                while ((s = buffer.readUtf8Line()) != null) {
                    stringBuffer.append(s + "\n");
                }
                try {
                    Logger.json(buffer.clone().readString(body.contentType().charset(Util.UTF_8)));
                    Logger.i(stringBuffer.toString());
                    Logger.i("<-- END HTTP (" + request.method() + "/" + buffer.size() + "-byte body)");
                } catch (Exception e) {

                }
            }
        } catch (Exception e) {
        }

        long t1 = System.nanoTime();
        try {
            com.orhanobut.logger.Logger.i(String.format("Sending request %s on %s%n%s",
                    request.url(), chain.connection(), request.headers()));
        } catch (Exception e) {

        }
        Response response = chain.proceed(request);

        long t2 = System.nanoTime();
        try {
            com.orhanobut.logger.Logger.i(String.format("Received response for %s in %.1fms%n%s",
                    response.request().url(), (t2 - t1) / 1e6d, response.headers()));
        } catch (Exception e) {

        }
//            response.newBuilder()
//                    .header("Cache-Control", String.format("max-age=%d, only-if-cached, max-stale=%d", 60, 0))
//                    .build();

//            try {
        ResponseBody body = response.body();
//                BufferedSource source = body.source();
//                source.request(Long.MAX_VALUE);
//                okio.Buffer buffer = source.buffer();
//
//                Charset charset = Util.UTF_8;
//                MediaType contentType = body.contentType();
//                if (contentType != null) {
//                    charset = contentType.charset(charset);
//                }
//
//                try {
//                    Logger.json(buffer.clone().readString(charset));
//                    Logger.i("<-- END HTTP (" + buffer.size() + "-byte body)");
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
        long contentLength = body.contentLength();
//                Headers headers = response.headers();
//                for (int i = 0, count = headers.size(); i < count; i++) {
//                    Logger.i(headers.name(i) + ": " + headers.value(i));
//                }

        if (!HttpHeaders.hasBody(response)) {
            Logger.i("<-- END HTTP");
        } else if (bodyEncoded(response.headers())) {
            Logger.i("<-- END HTTP (encoded body omitted)");
        } else {
            BufferedSource source = body.source();
            source.request(Long.MAX_VALUE); // Buffer the entire body.
            Buffer buffer = source.buffer();

            Charset charset = Charset.forName("UTF-8");
            MediaType contentType = body.contentType();
            if (contentType != null) {
                try {
                    charset = contentType.charset(charset);
                } catch (UnsupportedCharsetException e) {
                    Logger.i("Couldn't decode the response body; charset is likely malformed.");
                    Logger.i("<-- END HTTP");

                    return response;
                }
            }

            if (!isPlaintext(buffer)) {
                Logger.i("<-- END HTTP (binary " + buffer.size() + "-byte body omitted)");
                return response;
            }

            if (contentLength != 0) {
                Logger.json(buffer.clone().readString(charset));
            }

            Logger.i("<-- END HTTP (" + buffer.size() + "-byte body)");
        }

        return response;
    }

    /**
     * Returns true if the body in question probably contains human readable text. Uses a small sample
     * of code points to detect unicode control characters commonly used in binary file signatures.
     */
    static boolean isPlaintext(Buffer buffer) {
        try {
            Buffer prefix = new Buffer();
            long byteCount = buffer.size() < 64 ? buffer.size() : 64;
            buffer.copyTo(prefix, 0, byteCount);
            for (int i = 0; i < 16; i++) {
                if (prefix.exhausted()) {
                    break;
                }
                int codePoint = prefix.readUtf8CodePoint();
                if (Character.isISOControl(codePoint) && !Character.isWhitespace(codePoint)) {
                    return false;
                }
            }
            return true;
        } catch (EOFException e) {
            return false; // Truncated UTF-8 sequence.
        }
    }

    private boolean bodyEncoded(Headers headers) {
        String contentEncoding = headers.get("Content-Encoding");
        return contentEncoding != null && !contentEncoding.equalsIgnoreCase("identity");
    }
}
