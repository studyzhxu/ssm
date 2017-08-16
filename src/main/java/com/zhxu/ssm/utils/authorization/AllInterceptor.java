package com.zhxu.ssm.utils.authorization;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AllInterceptor implements HandlerInterceptor {

//	@Autowired
//	private TokenManager tokenManager;

	private List<String> excludedUrls;

	public void setExcludedUrls(List<String> excludedUrls) {
		this.excludedUrls = excludedUrls;
	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		String requestUri = request.getRequestURI();
		for (String url : excludedUrls) {
			Pattern pattern = Pattern.compile(url);
			Matcher matcher = pattern.matcher(requestUri);
			if (matcher.find()) {
				return true;
			}
		}
//		Map<String, Object> params = Util.reqParamToGenericMap(request);
//		response.setContentType("text/html;charset=UTF-8");
//		response.setHeader("Content-Type", "text/html;charset=UTF-8");
//		if (Util.checkParams(params, "token", "userId")) {
//			Response json = new Response().failure("缺少凭证参数");
//			response.getOutputStream().write(JSON.toJSONBytes(json, SerializerFeature.WriteDateUseDateFormat));
//			return false;
//		}
//		final boolean checkToken = tokenManager.checkToken(String.valueOf(params.get("token")),
//				Integer.valueOf(String.valueOf(params.get("userId"))));
//		final boolean checkTokenTimeOut = tokenManager.checkTokenTimeOut(String.valueOf(params.get("token")),
//				Integer.valueOf(String.valueOf(params.get("userId"))));
//		if (!checkToken) {
//			Response json = new Response().failure("凭证有误");
//			response.getOutputStream().write(JSON.toJSONBytes(json, SerializerFeature.WriteDateUseDateFormat));
//			return false;
//		}
//		if (!checkTokenTimeOut) {
//			Response json = new Response().failure("登录超时,请重新登录");
//			response.getOutputStream().write(JSON.toJSONBytes(json, SerializerFeature.WriteDateUseDateFormat));
//			return false;
//		}
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

	}

}
