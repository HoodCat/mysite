package com.cafe24.action.guestbook;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.cafe24.mvc.action.Action;
import com.cafe24.mvc.util.WebUtil;
import com.cafe24.mysite.dao.GuestBookDao;
import com.cafe24.mysite.vo.GuestBookVo;

public class DeleteAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		GuestBookDao dao = new GuestBookDao();
		int no = Integer.parseInt(request.getParameter("no"));
		String password = request.getParameter("password");
		
		dao.delete(new GuestBookVo((long)no, null, password, null, null));
		WebUtil.redirect(request, response, "/mysite/guestbook?a=list");
	}

}
