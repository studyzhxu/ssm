package com.zhxu.ssm.dao;

import com.zhxu.ssm.entity.TestTb;
import com.zhxu.ssm.utils.orm.mybatis.MyBatisDao;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by Administrator on 2017/8/16.
 */
@Component
public class TestTbDao extends MyBatisDao<TestTb,Integer> {

    /**
     * 添加数据
     * @param testTb
     */
    public void insertTestTb(TestTb testTb){
        getSqlSession().insert("insertTestTb",testTb) ;
    }


}
