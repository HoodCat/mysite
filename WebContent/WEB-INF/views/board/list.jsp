<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html>
<html>
<head>
<title>mysite</title>
<meta http-equiv="content-type" content="text/html; charset=utf-8">
<link href="/mysite/assets/css/board.css" rel="stylesheet" type="text/css">
</head>
<body>
	<div id="container">
		<c:import url="/WEB-INF/views/includes/header.jsp"/>
		<div id="content">
			<div id="board">
				<form id="search_form" action="" method="post">
					<input type="text" id="kwd" name="kwd" value="">
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
								<td>${status.index}</td>
								<td style="text-align:left">
								<c:if test="${entry.value.depth > 0}]">
								  <td style="text-align:left; padding-bottom:${20*entry.value.depth}">
								  <img src="/mysite/assets/images/reply.png"/>
								</c:if>
								  <a href="/mysite/board?a=view&no=${entry.value.no}">${entry.value.title}</a>
							    </td>
								<td>${entry.key}</td>
								<td>${entry.value.views}</td>
								<td>${entry.value.regDate}</td>
								<c:if test="${not empty authUser and entry.value.userNo == authUser.no}">
									<td><a href="/mysite/board?a=delete&no=${entry.value.no}" class="del">삭제</a></td>
								</c:if>
							</tr>					
					    </c:forEach>
					</c:forEach>				
				</table>
				<div class="pager">
					<ul>
						<li><a href="">◀</a></li>
						<li><a href="">1</a></li>
						<li><a href="">2</a></li>
						<li class="selected">3</li>
						<li><a href="">4</a></li>
						<li>5</li>
						<li><a href="">▶</a></li>
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
		<c:import url="/WEB-INF/views/includes/footer.jsp"/>
	</div>
</body>
</html>