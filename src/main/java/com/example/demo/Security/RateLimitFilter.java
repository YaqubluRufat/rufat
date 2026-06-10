package com.example.demo.Security;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class RateLimitFilter implements Filter {
    private final ConcurrentHashMap<String, Integer> requestCount = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<String, Long> timeMap = new ConcurrentHashMap<>();

    private static final int LIMIT = 5; // 10 request
    private static final long WINDOW = 60_000; // 1 minute

    @Override
    public void doFilter(ServletRequest request,
                         ServletResponse response,
                         FilterChain chain) throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

        String ip = req.getRemoteAddr();
        long now = System.currentTimeMillis();

        timeMap.putIfAbsent(ip,now);
        requestCount.putIfAbsent(ip,0);

        if (now - timeMap.get(ip) > WINDOW) {
            timeMap.put(ip, now);
            requestCount.put(ip, 0);
        }

        int count = requestCount.get(ip);

        if (count >= LIMIT) {
            res.setStatus(429);
            res.getWriter().write("Too Many Requests");
            return;
        }
        requestCount.put(ip, count + 1);

        chain.doFilter(request, response);
    }
}
