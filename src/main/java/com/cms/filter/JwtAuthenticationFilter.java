package com.cms.filter;

import java.io.IOException;
import java.util.logging.Logger;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpMethod;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.cms.service.JwtService;
import com.cms.utils.Constants;

//@WebFilter(urlPatterns = "/api/*")
public class JwtAuthenticationFilter implements Filter {
	private static final String[] EXCLUDE_PATTERNS = { "/api/authenticate", "/api/user/create" };
	private static final java.util.logging.Logger LOG = Logger.getLogger(JwtAuthenticationFilter.class.getName());

	private static final String AUTH_HEADER_KEY = "Authorization";
	// with trailing space to separate token
	private static final String AUTH_HEADER_VALUE_PREFIX = "Bearer ";

	private static final int STATUS_CODE_UNAUTHORIZED = 401;
	private JwtService jwtService;

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		WebApplicationContext context = WebApplicationContextUtils
				.getWebApplicationContext(filterConfig.getServletContext());
		jwtService = context.getBean(JwtService.class);
	}

	@Override
	public void doFilter(final ServletRequest servletRequest, final ServletResponse servletResponse,
			final FilterChain filterChain) throws IOException, ServletException {
		HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;

		boolean loggedIn = true;
		try {
			// HttpMethod.OPTIONS pour eviter le Cross domain de chrome
			if (!excludeUrl(httpRequest.getServletPath())
					&& !HttpMethod.OPTIONS.toString().equals(httpRequest.getMethod())) {

				String jwt = getBearerToken(httpRequest);
				if (jwt != null && !jwt.isEmpty()) {
					Long userId = jwtService.parseJWT(jwt);
					// Set User ID
					httpRequest.setAttribute(Constants.USER_ID_ATTRIBUTE, userId);
				} else {
					loggedIn = false;
					LOG.warning("No JWT provided, go on unauthenticated");
				}
			}
		} catch (final Exception e) {
			LOG.warning("Failed Security token : " + e.getMessage());
			loggedIn = false;
		}

		if (!loggedIn) {
			HttpServletResponse httpResponse = (HttpServletResponse) servletResponse;
			httpResponse.setContentLength(0);
			httpResponse.setStatus(STATUS_CODE_UNAUTHORIZED);
		} else {
			filterChain.doFilter(servletRequest, servletResponse);
		}
	}

	private boolean excludeUrl(String url) {
		for (String excludeUrl : EXCLUDE_PATTERNS) {
			if (url.startsWith(excludeUrl)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public void destroy() {
		LOG.info("JwtAuthenticationFilter destroyed");
	}

	/**
	 * Get the bearer token from the HTTP request. The token is in the HTTP request
	 * "Authorization" header in the form of: "Bearer [token]"
	 */
	private String getBearerToken(HttpServletRequest request) {
		String authHeader = request.getHeader(AUTH_HEADER_KEY);
		if (authHeader != null && authHeader.startsWith(AUTH_HEADER_VALUE_PREFIX)) {
			return authHeader.substring(AUTH_HEADER_VALUE_PREFIX.length());
		}
		return null;
	}
}