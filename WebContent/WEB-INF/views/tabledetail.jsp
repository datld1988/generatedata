<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<jsp:include page="header.jsp" />
<script type="text/javascript">
	$(document).ready(function() {
		console.log("ready!");
	});
</script>
<h3><h3>Thông tin cột và cài đặt GEN DATA</h3></h3>
<form class="form-horizontal" action="downloadExcel" method="POST">
	<div style="float: right;">
		<div class="col-sm-offset-2 col-sm-10">
			<button type="submit" class="btn btn-default">Tạo Data</button>
		</div>
	</div>
	<br> <br>
	<div id="content-table">
		<table class="table" id="tblAllTable">
			<thead>
				<tr>
					<th>Cài đặt GenData</th>
					<th>Tên bảng</th>
					<th><div class="checkbox">
							<label><input type="checkbox" name="checkAll" id="ckbAll" onclick="settingOnLoad.checkAllTable(this)"></label>
						</div></th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${allTable}" var="tableName">
					<tr>
						<td><a href="${contextPath}/getonetable?tableName=${tableName}"> ${fn:toUpperCase(tableName)}</a> </td>
						<td>
							<div class="checkbox">
								<label><input type="checkbox" name="listTable" value="${tableName}" onclick="settingOnLoad.unCheckAllTable(this)"></label>
							</div>
						</td>

						<td><button type="button" class="btn btn-default">Thiết lập</button></td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>
</form>

<!-- Modal -->
<div class="modal fade" tabindex="-1" role="dialog" id="myModal">
  <div class="modal-dialog" role="document">
    <div class="modal-content">
      <div class="modal-header" style="text-align: center;">
        
         Modal title
      </div>
      <div class="modal-body">
        <p>One fine body&hellip;</p>
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
      </div>
    </div><!-- /.modal-content -->
  </div><!-- /.modal-dialog -->
</div><!-- /.modal -->


<jsp:include page="footer.jsp" />