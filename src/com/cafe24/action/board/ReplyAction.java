package com.cafe24.action.board;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.cafe24.mvc.action.Action;
import com.cafe24.mvc.util.WebUtil;
import com.cafe24.mysite.dao.BoardDao;
import com.cafe24.mysite.vo.BoardVo;
import com.cafe24.mysite.vo.UserVo;

public class ReplyAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		BoardDao dao = new BoardDao();
		int no = Integer.parseInt(request.getParameter("no"));
		BoardVo vo = dao.getBoard((long)no);
		String title = request.getParameter("title");
		String content = request.getParameter("content");
		UserVo userVo = (UserVo)request.getSession().getAttribute("authUser");
		
		vo.setTitle(title);
		vo.setContent(content);
		vo.setUserNo(userVo.getNo());
		dao.insertReply(vo);
		WebUtil.redirect(request, response, "/mysite/board");
	}

}
