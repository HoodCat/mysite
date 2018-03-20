package com.cafe24.action.board;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.cafe24.mvc.action.Action;
import com.cafe24.mvc.util.WebUtil;
import com.cafe24.mysite.dao.BoardDao;
import com.cafe24.mysite.dao.CommentDao;
import com.cafe24.mysite.vo.BoardVo;

public class DeleteAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		if(session.getAttribute("authUser") == null) {
			WebUtil.redirect(request, response, "/mysite/board");
			return;
		}
		
		int no = Integer.parseInt(request.getParameter("no"));
		new CommentDao().deleteToBoardNo((long)no);
		BoardDao dao = new BoardDao();
		BoardVo vo = new BoardVo();
		vo.setNo((long)no);
		dao.delete(vo);
		WebUtil.redirect(request, response, "/mysite/board");
	}

}
