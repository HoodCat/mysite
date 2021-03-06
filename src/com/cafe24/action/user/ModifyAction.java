package com.cafe24.action.user;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.cafe24.mvc.action.Action;
import com.cafe24.mvc.util.WebUtil;
import com.cafe24.mysite.dao.UserDao;
import com.cafe24.mysite.vo.UserVo;

public class ModifyAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		UserDao dao = new UserDao();
		UserVo vo = new UserVo();
		int no = Integer.parseInt(request.getParameter("no"));
		vo.setNo((long)no);
		vo.setName(request.getParameter("name"));
		vo.setPassword(request.getParameter("password"));
		vo.setGender(request.getParameter("gender"));
		dao.update(vo);
		
		HttpSession session = request.getSession();
		UserVo authUser = (UserVo)session.getAttribute("authUser");
		
		if( session != null && authUser != null) {
			session.removeAttribute("authUser");
			session.invalidate();
		}
		
		WebUtil.forward(request, response, "/WEB-INF/views/user/modifysuccess.jsp");
	}

}
