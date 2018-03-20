package com.cafe24.action.board;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.cafe24.mvc.action.Action;
import com.cafe24.mvc.util.WebUtil;
import com.cafe24.mysite.dao.BoardDao;
import com.cafe24.mysite.vo.BoardVo;

public class ListAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		BoardDao dao = new BoardDao();
		int page = 1;
		String pageStr = request.getParameter("page");
		if(pageStr != null && !"".equals(pageStr)) {
			page = Integer.parseInt(pageStr);
		}
		BoardDao.setCurrentPage((long)page);
		List<Map<String, BoardVo>> mapList = dao.getList(page);
		request.setAttribute("totCount", dao.getTotalCount());
		request.setAttribute("mapList", mapList);
		request.setAttribute("endPage", BoardDao.getEndPage());
		request.setAttribute("curPage", BoardDao.getCurrentPage());
		WebUtil.forward(request, response, "/WEB-INF/views/board/list.jsp");
	}

}
