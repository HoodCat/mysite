package com.cafe24.mysite.dao.test;

import java.util.List;
import java.util.Map;

import com.cafe24.mysite.dao.BoardDao;
import com.cafe24.mysite.vo.BoardVo;

public class BoardDaoTest {
    public static void main(String[] args) {
		getListTest();
	}
    
    private static void getListTest() {
    	BoardDao dao = new BoardDao();
    	List<Map<String, BoardVo>> list = dao.getList(1);
    	
    	for(Map<String, BoardVo> entry : list) {
    		for(String key: entry.keySet()) {
    			System.out.println(key);
    			System.out.println(entry.get(key));
    		}
    	}
    }
}
