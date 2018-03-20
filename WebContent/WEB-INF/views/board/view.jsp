<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%
	request.setAttribute("newLine", "\n");
%>
<!DOCTYPE html>
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
			<div id="board" class="board-form">
				<table class="tbl-ex">
					<tr>
						<th colspan="2">글보기</th>
					</tr>
					<tr>
						<td class="label">제목</td>
						<td>${vo.title}</td>
					</tr>
					<tr>
						<td class="label">내용</td>
						<td>
							<div class="view-content">${fn:replace(vo.content, newLine, "<br/>")}
							</div>
						</td>
					</tr>
				</table>
				<div class="bottom">
					<a href="/mysite/board?page=${page}">글목록</a>
					<c:if test="${not empty authUser and authUser.no == vo.userNo}">
						<a href="/mysite/board?a=modifyform&no=${vo.no}&page=${param.page}">글수정</a>
					</c:if>
					<c:if test="${not empty authUser}">
						<a href="/mysite/board?a=replyform&no=${vo.no}">답글</a>
					</c:if>
				</div>

				<div id="comment">
					<c:if test="${not empty authUser}">
						<form action="/mysite/board" method="post">
							<input type="hidden" name="a" value="commentInsert"/>
							<input type="hidden" name="userNo" value="${authUser.no}" />
							<input type="hidden" name="boardNo" value="${vo.no}" />
							<input type="hidden" name="page" value="${param.page }"/>
							<table>
								<tr>
									<td colspan=4>
									<textarea name="content" id="content" style="resize: none;width:550px"></textarea>
									</td>
								</tr>
								<tr>
									<td colspan=4 align=right><input type="submit"
										VALUE=" 확인 "></td>
								</tr>
							</table>
						</form>
					</c:if>
					<ul>
						<li><c:set var="count" value='${fn:length(cmtList)}' /> <c:forEach
								items='${cmtList}' var="cmt" varStatus="status">
								<c:forEach items="${cmt}" var="entry">
									<table>
										<tr>
											<td>[${count - status.index}]</td>
											<td>${entry.key}</td>
											<td>${entry.value.regDate}</td>
											<c:if test="${authUser.no==entry.value.userNo}">
												<td><a
													href="/mysite/board?a=commentDelete&no=${vo.no}&commentNo=${entry.value.no}">삭제</a></td>
											</c:if>
										</tr>
										<tr>
											<td colspan=4>${fn:replace(entry.value.content, newLine, "<br/>")}
											</td>
										</tr>
									</table>
									<br>
								</c:forEach>
							</c:forEach></li>
					</ul>
				</div>
			</div>
		</div>

		<c:import url="/WEB-INF/views/includes/navigation.jsp">
			<c:param name="menu" value="board"></c:param>
		</c:import>
		<c:import url="/WEB-INF/views/includes/footer.jsp" />
	</div>
</body>
</html>