package com.cafe24.action.board;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.cafe24.mvc.action.Action;
import com.cafe24.mvc.util.WebUtil;
import com.cafe24.mysite.dao.BoardDao;
import com.cafe24.mysite.dao.CommentDao;
import com.cafe24.mysite.vo.BoardVo;

public class ViewAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		BoardDao dao = new BoardDao();
		int no = Integer.parseInt(request.getParameter("no"));
		int page = Integer.parseInt(request.getParameter("page"));
		request.setAttribute("page", page);
		dao.updateViews((long)no);
		BoardVo vo = dao.getBoard((long)no);
		request.setAttribute("vo", vo);
		CommentDao cmtDao = new CommentDao();
		request.setAttribute("cmtList", cmtDao.getList((long)no));
		WebUtil.forward(request, response, "/WEB-INF/views/board/view.jsp");
	}

}
