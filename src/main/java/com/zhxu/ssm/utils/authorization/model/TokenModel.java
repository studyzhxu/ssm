package com.zhxu.ssm.utils.authorization.model;


import com.zhxu.ssm.utils.Util;
import org.apache.commons.codec.digest.DigestUtils;

public class TokenModel {

	/**
	 * 用户ID
	 */
	private int userId;

	/**
	 * token
	 */
	private String token;

	/**
	 * 凭证
	 */
	private String sign;

	/**
	 * 时间戳
	 */
	private long timeStemp;

	/**
	 * 角色IDs
	 */
	private String roleIds;

	public TokenModel(int userId, String token, String roleIds, long timeStemp) {
		this.userId = userId;
		this.token = token;
		this.timeStemp = timeStemp;
		this.roleIds = roleIds;
		String publickey = Util.getValueByKey("app", "public_key");
		this.sign = DigestUtils.md5Hex(token + userId + publickey);
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public long getTimeStemp() {
		return timeStemp;
	}

	public void setTimeStemp(long timeStemp) {
		this.timeStemp = timeStemp;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	public String getRoleIds() {
		return roleIds;
	}

	public void setRoleIds(String roleIds) {
		this.roleIds = roleIds;
	}

}
