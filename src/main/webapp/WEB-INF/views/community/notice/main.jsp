<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/WEB-INF/views/common/tags.jsp" %>
<!doctype html>
<html lang="ko">
<head>
	<%@include file="/WEB-INF/views/common/common.jsp" %>
</head>
<style>
    #content-title:hover {
        text-decoration: black underline;
        font-weight: bold;
    }
</style>
<body>
<%@include file="/WEB-INF/views/common/nav.jsp" %>
<div class="container-xxl text-center" id="wrap">
	<div class="row p-3 justify-content-center">
		<div class="col mb-3">
			<h2><a href="main" style="text-decoration-line: none; color: black">공지사항</a></h2>
		</div>
	</div>
	
	<form id="form-search" method="get" action="main">
		<div class="p-1 col d-flex justify-content-end">
			<input type="hidden" name="page" value="${param.page != null ? param.page : 1}">
			<div class="form-check-inline">
				<input class="form-check-input" type="radio" name="sort" value="date" onchange="changeSort()"
				${empty param.sort or param.sort eq 'date' ? 'checked' : ''}>
				<label class="form-check-label">중요도순</label>
			</div>
			<div class="form-check form-check-inline">
				<input class="form-check-input" type="radio" name="sort" value="new" onchange="changeSort()"
				${param.sort eq 'new' ? 'checked' : ''}>
				<label class="form-check-label">최신순</label>
			</div>
			<div class="form-check-inline">
				<input class="form-check-input" type="radio" name="sort" value="viewCnt" onchange="changeSort()"
				${param.sort eq 'viewCnt' ? 'checked' : ''}>
				<label class="form-check-label">조회순</label>
			</div>
			<div>
				<select class="form-control-sm" name="rows" onchange="changeRows()">
					<option value="5" ${param.rows eq 5 ? 'selected' : ''}>5개씩 보기</option>
					<option value="10" ${empty param.rows or param.rows eq 10 ? 'selected' : ''}>10개씩 보기</option>
					<option value="20" ${param.rows eq 20 ? 'selected' : ''}>20개씩 보기</option>
					<option value="30" ${param.rows eq 30 ? 'selected' : ''}>30개씩 보기</option>
				</select>
			</div>
		</div>
		
		<input type="hidden" name="page" value="${param.page != null ? param.page : 1}">
		<div class="row p-3">
			<table class="table">
				<colgroup>
					<col width="5%">
					<col width="*">
					<col width="10%">
					<col width="15%">
				</colgroup>
				<thead>
				<tr style="text-align: center">
					<th>순번</th>
					<th>제목</th>
					<th>조회</th>
					<th>날짜</th>
				</tr>
				</thead>
				<tbody style="text-align: center">
				<c:choose>
					<c:when test="${empty notices}">
						<tr>
							<td colspan="4" style="text-align: center">검색된 공지사항이 없습니다.</td>
						</tr>
					</c:when>
					<c:otherwise>
						<c:forEach var="notice" items="${notices}" varStatus="status">
							<tr>
								<td>${status.count}</td>
								<td id="content-title" style="text-align: start">
									<a href="hit?no=${notice.no}"
										 style="text-decoration-line: none; color: ${notice.first eq 'true' ? 'red' : 'black'}">
											${notice.title}
									</a>
								</td>
								<td>${notice.viewCnt}</td>
								<td><fmt:formatDate value="${notice.createdDate}" pattern="yyyy-MM-dd"/></td>
							</tr>
						</c:forEach>
					</c:otherwise>
				</c:choose>
				</tbody>
			</table>
		</div>
		<div class="row p-3 d-flex justify-content-left">
			<div class="col-2">
				<select class="form-control" name="opt">
					<option value="all" ${param.opt eq 'all' ? 'selected' : ''}> 제목+내용</option>
					<option value="title" ${param.opt eq 'title' ? 'selected' : ''}> 제목</option>
					<option value="content" ${param.opt eq 'content' ? 'selected' : ''}> 내용</option>
				</select>
			</div>
			<div class="col-4">
				<input type="text" class="form-control" name="keyword" value="${param.keyword}">
			</div>
			<div class="col-1">
				<button class="btn btn-outline-primary" onclick="searchKeyword()">검색</button>
			</div>
			<div class="col d-flex justify-content-center"></div>
			
			<div class="col d-flex justify-content-end">
				<security:authorize access="isAuthenticated()">
					<security:authentication property="principal" var="loginUser"/>
					<c:if test="${loginUser.nickname eq '관리자'}">
						<a href="form" type="button" class="btn btn-primary">글쓰기</a>
					</c:if>
				</security:authorize>
			</div>
		</div>
		
		<!-- 페이징처리 -->
		<c:if test="${paging.totalRows ne 1}">
			<div>
				<ul class="pagination justify-content-center">
					<li class="page-item ${paging.first ? 'disabled' : '' }">
						<a class="page-link"
							 onclick="changePage(${paging.prevPage}, event)"
							 href="javascript:void(0)"><<</a>
					</li>
					
					<c:forEach var="num" begin="${paging.beginPage }" end="${paging.endPage }">
						<li class="page-item ${paging.page eq num ? 'active' : '' }">
							<a class="page-link"
								 onclick="changePage(${num }, event)"
								 href="javascript:void(0)">${num }</a>
						</li>
					</c:forEach>
					
					<li class="page-item ${paging.last ? 'disabled' : '' }">
						<a class="page-link"
							 onclick="changePage(${paging.nextPage}, event)"
							 href="javascript:void(0)">>></a>
					</li>
				</ul>
			</div>
		</c:if>
	</form>
</div>
<%@include file="/WEB-INF/views/common/footer.jsp" %>
</body>
<script type="text/javascript">
    function changePage(page, event) {
        event.preventDefault();
        let form = document.querySelector("#form-search");
        let pageInput = form.querySelector("input[name=page]");
        pageInput.value = page;
        form.submit();
    }

    function searchKeyword() {
        let pageInput = form.querySelector("input[name=page]");
        pageInput.value = 1;
        form.submit();
    }

    // 정렬방식이 변경될 때
    function changeSort() {
        let form = document.querySelector("#form-search");
        let sortInput = document.querySelector("input[name=sort]:checked");
        form.submit();
    }

    // 한 화면에 표기할 행의 갯수가 변경될 때
    function changeRows() {
        // 페이지 갯수를 클릭했다면 해당 페이징 요청
        let form = document.querySelector("#form-search");
        // 페이지 번호 input 요소 선택
        let rowsInput = document.querySelector("input[name=rows]");
        // 새로 요청하는 페이지는 1로 초기화
        let pageInput = form.querySelector("input[name=page]");
        pageInput.value = 1;
        // 폼 제출
        form.submit();
    }
</script>
</html>