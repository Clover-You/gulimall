/**
 * Copyright (c) 2016-2019 人人开源 All rights reserved.
 *
 * https://www.renren.io
 *
 * 版权所有，侵权必究！
 */

package top.ctong.gulimall.common.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.fasterxml.jackson.core.type.TypeReference;
import com.google.gson.Gson;
import org.apache.http.HttpStatus;
import top.ctong.gulimall.common.exception.BizCodeEnum;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 返回数据
 *
 * @author Mark sunlightcs@gmail.com
 */
public class R extends HashMap<String, Object> {
	private static final long serialVersionUID = 1L;

	public <T> T getData(TypeReference<T> typeRef) {
		return getData("data", typeRef);
	}

	public <T> T getData(String key, TypeReference<T> typeRef) {
		Object data = this.get(key);
		String s = JSON.toJSONString(data);
		return JSON.<T>parseObject(s, typeRef.getType());
	}

	public R setData(Object data) {
		this.put("data", data);
		return this;
	}

	public R() {
		put("code", 0);
		put("msg", "success");
	}
	
	public static R error() {
		return error(HttpStatus.SC_INTERNAL_SERVER_ERROR, "未知异常，请联系管理员");
	}
	
	public static R error(String msg) {
		return error(HttpStatus.SC_INTERNAL_SERVER_ERROR, msg);
	}
	
	public static R error(int code, String msg) {
		R r = new R();
		r.put("code", code);
		r.put("msg", msg);
		return r;
	}

	public static R error(BizCodeEnum bizCode) {
		R r = new R();
		r.put("code", bizCode.getCode());
		r.put("msg", bizCode.getMsg());
		return r;
	}

	public static R ok(String msg) {
		R r = new R();
		r.put("msg", msg);
		return r;
	}

	public static R ok(BizCodeEnum bizCode) {
		R r = new R();
		r.put("msg", bizCode.getMsg());
		r.put("code", bizCode.getCode());
		return r;
	}
	
	public static R ok(Map<String, Object> map) {
		R r = new R();
		r.putAll(map);
		return r;
	}
	
	public static R ok() {
		return new R();
	}

	public static R data(Object data) {
		R r = new R();
		r.setData(data);
		return r;
	}

	@Override
	public R put(String key, Object value) {
		super.put(key, value);
		return this;
	}

	public Integer getCode() {
		return (Integer) this.get("code");
	}

	public String getMsg() {
		return (String) this.get("msg");
	}
}
