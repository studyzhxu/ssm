package com.zhxu.ssm.service;

import com.zhxu.ssm.dao.TestTbDao;
import com.zhxu.ssm.entity.TestTb;
import com.zhxu.ssm.utils.orm.mybatis.MyBatisDao;
import com.zhxu.ssm.utils.orm.mybatis.MyBatisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Administrator on 2017/8/16.
 */
@Service
public class TestTbService extends MyBatisService<TestTb,Integer> {

    @Autowired
    private TestTbDao testTbDao ;

    @Override
    public MyBatisDao<TestTb, Integer> getMyBatisDao() {
        return testTbDao;
    }

    public void insertTestTb(TestTb tb){
        testTbDao.insertTestTb(tb);
//        throw new RuntimeException();
    }
}
