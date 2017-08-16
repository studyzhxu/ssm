package com.zhxu.ssm.utils.authorization.manager;


import com.zhxu.ssm.utils.authorization.model.TokenModel;

public interface TokenManager {

	/**
	 * 
	 * <B>功能简述</B><br>
	 * 创建一个token关联上指定用户
	 * 
	 * @date 2016年12月18日 下午11:53:46
	 * @author xusheng
	 * @param userId
	 * @param customerId
	 * @return
	 */
	TokenModel createToken(int userId, int customerId, String roleIds);

	/**
	 * 
	 * <B>功能简述</B><br>
	 * 检查token是否有效
	 * 
	 * @date 2016年12月18日 下午11:53:37
	 * @author xusheng
	 * @param token
	 * @param userId
	 * @return
	 */
	boolean checkToken(String token, int userId);

	/**
	 * 
	 * <B>功能简述</B><br>
	 * 清除token
	 * 
	 * @date 2016年12月18日 下午11:54:09
	 * @author xusheng
	 * @param userId
	 */
	void clearToken(String token);

	/**
	 * 
	 * <B>功能简述</B><br>
	 * 查询tokenmodel
	 * 
	 * @date 2016年12月20日 下午12:06:36
	 * @author xusheng
	 * @param userId
	 * @return
	 */
	TokenModel queryToken(int userId);

	/**
	 * 
	 * <B>功能简述</B><br>
	 * 查看token是否超时
	 * 
	 * @date 2017年1月12日 下午12:01:38
	 * @author xusheng
	 * @param token
	 * @param userId
	 * @return
	 */
	boolean checkTokenTimeOut(String token, int userId);

}
