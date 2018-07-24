<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<jsp:include page="header.jsp" />
<script type="text/javascript">
	$(document).ready(function() {
		console.log("ready!");
		
		var isCheked = false;
		
		$('#tblAllTable').each(function(){
            $(this).find('tr > td').each(function(){
            	if($(this).find('input[name="listTable"]').prop('checked')) {
            		isCheked = true;
            		
            	}
            });
		});
		
		if (isCheked) {
			$(':input[type="submit"]').prop('disabled', false);
		}else {
			$(':input[type="submit"]').prop('disabled', true);
		}
		
		settingOnLoad.loadSetting();
	});
</script>
<h3 style="text-align: center;">DANH SÁCH BẢNG</h3>
<form class="form-horizontal" action="downloadExcel" method="POST">

	<input type="hidden" name="rowConfig">
	<div style="float: left;">
		Số record được Gen <select onchange="settingOnLoad.chooseEnv(this)"
			class="select" name="numberRecord">
			<option value="1">1</option>
			<option value="5">5</option>
			<option value="10">10</option>
			<option value="20">20</option>
		</select>
	</div>
	<div style="float: right;">
		<div class="col-sm-offset-2 col-sm-10">
			<button type="submit" class="btn btn-default" id="genData">Tạo Data</button>
		</div>
	</div>
	<br> <br>
	<div id="content-table">
		<table class="table table-bordered" id="tblAllTable">
			<thead>
				<tr>
					<th>#</th>
					<th>Tên bảng</th>
					<th><div class="checkbox">
							<label><input type="checkbox" name="checkAll" id="ckbAll" onclick="settingOnLoad.checkAllTable(this)"></label>
						</div></th>
					<th>Cài đặt GenData</th>
				</tr>
			</thead>
			<tbody>
				<c:set var="numberTable" value="1" scope="page" />
				
				<c:forEach items="${allTableStruct}" var="allTable">
					<tr>
						<td>${numberTable}</td>
						<td><b>${fn:toUpperCase(allTable.tableName)}</b></td>
						<td>
							<div class="checkbox">
								<label><input type="checkbox" name="listTable" value="${allTable.tableName}" onclick="settingOnLoad.unCheckAllTable(this)"></label>
							</div>
						</td>

						<td><button type="button" class="btn btn-default" onclick="settingOnLoad.openDetails(${allTable.tableName})">Thiết lập</button></td>
					</tr>
					<tr id="${allTable.tableName}" style="display: none;">
						
						<td colspan="4" style="padding-left: 50px; padding-right: 50px;">
							<input type="hidden" name="tableName" value="${allTable.tableName}">
							<input type="hidden" id="trHiden" value="0">
							
							<!-- Nội dung config -->
							Danh sách các trường của bảng
							<table class="table table-bordered" id="tblRowConfig">
								<thead>
									<tr>
										<th>#</th>
										<th>Tên trường</th>
										<th>Kiểu dữ liệu</th>
										<th>Độ dài</th>
										<th>Kiểu dữ liệu GEN</th>
									</tr>
								</thead>
								<tbody>
									<c:set var="numberField" value="1" scope="page" />
									<c:forEach items="${allTable.listColumn}" var="allField">
										<tr>
											<td>${numberField}</td>
											<td id="nameColumn">${fn:toUpperCase(allField.nameColumn)}</td>
											<td id="type">${allField.type}</td>
											<td>${allField.length}</td>
											<td>
												<select onchange="settingOnLoad.setRowConfig(this)" class="select" name="typeGen">
													<option value="0">--------------------------------------</option>
													<option value="1">1: FULL SIZE</option>
													<option value="2">2: HALF SIZE</option>
													<option value="3">3: NUMBER</option>
												</select>
											</td>
										</tr>
										<c:set var="numberField" value="${numberField + 1}" scope="page"/>
									</c:forEach>
								</tbody>
							</table>
							
							<!-- Kết thúc Nội dung config -->
							
						</td>
					</tr>
					<c:set var="numberTable" value="${numberTable + 1}" scope="page"/>
				</c:forEach>
			</tbody>
		</table>
	</div>
</form>

<jsp:include page="footer.jsp" />