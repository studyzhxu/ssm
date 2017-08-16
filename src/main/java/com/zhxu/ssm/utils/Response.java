package com.zhxu.ssm.utils;

/**
 * 
 * <B>功能简述</B><br>
 * 返回工具 JSON返回格式
 * 
 * @date 2016年12月8日 下午6:21:01
 * @author xusheng
 * @since [CIM/模块版本]
 */
public class Response {

	private static final String OK = "ok";
	private static final String ERROR = "error";

	private Meta meta;
	private Object data;

	public Response success() {
		this.meta = new Meta(true, OK);
		this.data = "成功";
		return this;
	}

	public Response success(Object data) {
		this.meta = new Meta(true, OK);
		this.data = data;
		return this;
	}

	public Response failure() {
		this.meta = new Meta(false, ERROR);
		this.data = "失败";
		return this;
	}

	public Response failure(String message) {
		this.meta = new Meta(false, message);
		this.data = "失败";
		return this;
	}

	public Meta getMeta() {
		return meta;
	}

	public Object getData() {
		return data;
	}

	public class Meta {

		private boolean success;
		private String message;

		public Meta(boolean success) {
			this.success = success;
		}

		public Meta(boolean success, String message) {
			this.success = success;
			this.message = message;
		}

		public boolean isSuccess() {
			return success;
		}

		public String getMessage() {
			return message;
		}
	}
}
