package com.zhxu.ssm.utils.orm.mybatis;

import com.zhxu.ssm.utils.orm.Page;
import org.mybatis.spring.support.SqlSessionDaoSupport;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

public class MyBatisDao<T, PK extends Serializable> extends SqlSessionDaoSupport {

	// 系统Mapper.xml中定义的SQL查询的键值
	private static final String GET = ".get";
	private static final String FIND = ".find";
	private static final String COUNT = ".count";
	private static final String INSERT = ".insert";
	private static final String UPDATE = ".update";
	private static final String DELETE = ".delete";

	protected Class<PK> pkClass;
	protected Class<T> entityClass;
	protected String className;

	public String getClassName() {
		return className;
	}

	public String getMapperMethod(String methodName) {
		return className + "." + methodName;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public MyBatisDao() {
		Type genType = getClass().getGenericSuperclass();
		Type[] params = ((ParameterizedType) genType).getActualTypeArguments();

		this.entityClass = (Class) params[0];
		this.pkClass = (Class) params[1];
		this.className = entityClass.getSimpleName();
	}

	/**
	 * 分页查询
	 * 
	 * @param params
	 * @return
	 */
	public Page<T> findPage(Page<T> page, Map<String, Object> params) {
		List<T> list = getSqlSession().selectList(className + FIND, params, page.getRowBounds());
		if (page.isAutoCount()) {
			page.setTotalCount(count(params));
		}
		page.setResult(list);
		return page;
	}

	/**
	 * 分页数量查询
	 * 
	 * @param params
	 * @return
	 */

	public int count(Map<String, Object> params) {
		if (null == params)
			return count();
		return (Integer) getSqlSession().selectOne(className + COUNT, params);
	}

	public int count() {
		return (Integer) getSqlSession().selectOne(className + COUNT);
	}

	/**
	 * 得到某一条
	 * 
	 * @param id
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public T get(Integer id) {
		return (T) getSqlSession().selectOne(className + GET, id);
	}

	/**
	 * 列表查询
	 * 
	 * @param params
	 * @return
	 */
	public List<T> find(Map<String, Object> params) {
		if (null == params)
			return find();
		return getSqlSession().selectList(className + FIND, params);
	}

	public List<T> find() {
		return getSqlSession().selectList(className + FIND);
	}

	/**
	 * 新增
	 * 
	 * @param t
	 */
	public int insert(T t) {
		return getSqlSession().insert(className + INSERT, t);
	}

	/**
	 * 更新
	 * 
	 * @param t
	 * @return
	 */
	public int update(T t) {
		return getSqlSession().update(className + UPDATE, t);
	}

	/**
	 * 删除
	 * 
	 * @param id
	 * @return
	 */
	public int delete(Integer id) {
		return getSqlSession().delete(className + DELETE, id);
	}


}
