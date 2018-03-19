<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>mysite</title>
<meta http-equiv="content-type" content="text/html; charset=utf-8">
<link href="/mysite/assets/css/board.css" rel="stylesheet"
	type="text/css">
</head>
<body>
	<div id="container">
		<c:import url="/WEB-INF/views/includes/header.jsp" />
		<div id="content">
			<div id="board">
				<form class="board-form" method="post" action="/mysite/board">
					<c:choose>
						<c:when test="${isReply}">
							<input type="hidden" name="a" value="reply">
							<input type="hidden" name="no" value="${param.no}" />
						</c:when>
						<c:otherwise>
							<input type="hidden" name="a" value="write">
						</c:otherwise>
					</c:choose>
					<table class="tbl-ex">
						<tr>
							<c:choose>
								<c:when test="${isReply}">
									<th colspan="2">답글쓰기</th>
								</c:when>
								<c:otherwise>
									<th colspan="2">글쓰기</th>
								</c:otherwise>
							</c:choose>
						</tr>
						<tr>
							<td class="label">제목</td>
							<td><input type="text" name="title" value=""></td>
						</tr>
						<tr>
							<td class="label">내용</td>
							<td><textarea id="content" name="content"></textarea></td>
						</tr>
					</table>
					<div class="bottom">
						<a href="/mysite/board">취소</a> <input type="submit" value="등록">
					</div>
				</form>
			</div>
		</div>
		<c:import url="/WEB-INF/views/includes/navigation.jsp">
			<c:param name="menu" value="board" />
		</c:import>
		<c:import url="/WEB-INF/views/includes/footer.jsp" />
	</div>
</body>
</html>