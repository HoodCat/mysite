<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

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
			<div id="board">
				<form id="search_form" action="/mysite/board" method="post">
				    <input type="hidden" name="a" value="list"/>
					<input type="text" id="kwd" name="kwd" value="공|사|중 <^^" readonly>
                    <input type="submit" value="찾기">
				</form>
				<table class="tbl-ex">
					<tr>
						<th>번호</th>
						<th>제목</th>
						<th>글쓴이</th>
						<th>조회수</th>
						<th>작성일</th>
						<th>&nbsp;</th>
					</tr>

					<c:forEach items="${mapList}" var="entrys" varStatus="status">
						<c:forEach items="${entrys}" var="entry">
							<tr>
								<td>${totCount - (curPage-1)*10 - status.index}</td>
								<c:choose>
									<c:when test="${entry.value.depth > 0}">
										<td
											style="text-align:left; padding-left:${20*(entry.value.depth-1)}">
											<img src="/mysite/assets/images/reply.png" /> <a
											href="/mysite/board?a=view&no=${entry.value.no}&page=${curPage}">${entry.value.title}</a>
										</td>
									</c:when>
									<c:otherwise>
										<td style="text-align: left"><a
											href="/mysite/board?a=view&no=${entry.value.no}&page=${curPage}">${entry.value.title}</a>
										</td>
									</c:otherwise>
								</c:choose>
								<td>${entry.key}</td>
								<td>${entry.value.views}</td>
								<td>${entry.value.regDate}</td>
								<td>
								    <c:if test="${not empty authUser and entry.value.userNo == authUser.no}">
										<a href="/mysite/board?a=delete&no=${entry.value.no}" class="del">삭제</a>
									</c:if>
								</td>
							</tr>
						</c:forEach>
					</c:forEach>
				</table>
				<div class="pager">
					<ul>
					<c:if test="${curPage != 1}">
						<li><a href="/mysite/board?page=${(curPage-1)<1?1:(curPage-1)}">◀</a></li>					
					</c:if>
						<fmt:parseNumber var="sPage" value="${(curPage-1)/10}" integerOnly="true"/>
						<c:forEach begin="${sPage*10 + 1}" end="${(sPage*10 + 10)<=endPage?(sPage*10 + 10):endPage}" var="page">
						    <c:choose>
						        <c:when test="${page == curPage}">
									<li class="selected">${page}</li>						        
						        </c:when>
						        <c:otherwise>
									<li><a href="/mysite/board?page=${page}">${page}</a></li>						        
						        </c:otherwise>
						    </c:choose>
						</c:forEach>
						<c:if test="${curPage != endPage}">
							<li><a href="/mysite/board?page=${(curPage+1)>endPage?endPage:(curPage+1)}">▶</a></li>
						</c:if>
					</ul>
				</div>
				<c:if test="${not empty authUser}">
					<div class="bottom">
						<a href="/mysite/board?a=writeform" id="new-book">글쓰기</a>
					</div>
				</c:if>
			</div>
		</div>
		<c:import url="/WEB-INF/views/includes/navigation.jsp">
			<c:param name="menu" value="board"></c:param>
		</c:import>
		<c:import url="/WEB-INF/views/includes/footer.jsp" />
	</div>
</body>
</html>