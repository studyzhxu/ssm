package com.zhxu.ssm.utils.authorization.manager.impl;


import com.zhxu.ssm.utils.Util;
import com.zhxu.ssm.utils.authorization.LoginDataUtils;
import com.zhxu.ssm.utils.authorization.manager.TokenManager;
import com.zhxu.ssm.utils.authorization.model.TokenModel;
import org.joda.time.DateTime;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.UUID;

@Component
public class TokenManagerImpl implements TokenManager {

	@Override
	public TokenModel createToken(int userId, int customerId, String roleIds) {
		TokenModel model = (TokenModel) LoginDataUtils.getData(String.valueOf(userId));
		if (null == model) {
			String token = UUID.randomUUID().toString().replace("-", "");
			model = new TokenModel(userId, token, roleIds, new Date().getTime());
			LoginDataUtils.setData(String.valueOf(userId), model);
		}
		String timeOut = Util.getValueByKey("app", "token_expiration_time");
		long timeStemp = new DateTime().minusHours(Integer.valueOf(timeOut)).toDate().getTime();
		if (model.getTimeStemp() < timeStemp) {
			String token = UUID.randomUUID().toString().replace("-", "");
			model = new TokenModel(userId, token, roleIds, new Date().getTime());
			LoginDataUtils.setData(String.valueOf(userId), model);
		}
		return model;
	}

	@Override
	public boolean checkToken(String token, int userId) {
		if (token == null) {
			return false;
		}
		if (userId == 0) {
			return false;
		}
		TokenModel model = (TokenModel) LoginDataUtils.getData(String.valueOf(userId));
		if (null == model) {
			return false;
		}
		if (userId != model.getUserId()) {
			return false;
		}
		if (!token.equals(model.getToken())) {
		}
		if (!token.equals(model.getToken())) {
			return false;
		}
		return true;
	}

	@Override
	public void clearToken(String token) {
		LoginDataUtils.clearData(token);
	}

	@Override
	public TokenModel queryToken(int userId) {
		return (TokenModel) LoginDataUtils.getData(String.valueOf(userId));
	}

	@Override
	public boolean checkTokenTimeOut(String token, int userId) {
		TokenModel model = (TokenModel) LoginDataUtils.getData(String.valueOf(userId));
		if (null == model) {
			return false;
		}
		String timeOut = Util.getValueByKey("app", "token_expiration_time");
		long timeStemp = new DateTime().minusHours(Integer.valueOf(timeOut)).toDate().getTime();
		if (model.getTimeStemp() < timeStemp) {
			return false;
		}
		return true;
	}

}
