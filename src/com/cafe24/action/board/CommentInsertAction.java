package com.cafe24.action.board;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.cafe24.mvc.action.Action;
import com.cafe24.mvc.util.WebUtil;
import com.cafe24.mysite.dao.CommentDao;
import com.cafe24.mysite.vo.CommentVo;

public class CommentInsertAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int userNo = Integer.parseInt(request.getParameter("userNo"));
		int boardNo = Integer.parseInt(request.getParameter("boardNo"));
		int page = Integer.parseInt(request.getParameter("page"));
		String content = request.getParameter("content");

		CommentVo vo = new CommentVo(null, content, null, (long) userNo, (long) boardNo);
		new CommentDao().insert(vo);
		WebUtil.redirect(request, response, "/mysite/board?a=view&no=" + boardNo + "&page=" + page);
	}

}
