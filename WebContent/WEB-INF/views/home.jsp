<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<jsp:include page="header.jsp" />

<script type="text/javascript">
	$(document).ready(function() {
		$("#posgresSQL").show();
		$("#oracle").hide();
		$("#sqlServer").hide();
		$("#mySql").hide();
	});
</script>

Chọn môi trường
<select onchange="settingOnLoad.chooseEnv(this)">
	<option value="0">Posgres SQL</option>
	<option value="1">SQL Server</option>
	<option value="2">Oracle</option>
	<option value="3">My SQL</option>
</select>

<div id="posgresSQL">
	<br>
	<form class="form-horizontal" action="getalltable" method="post">
		<div class="form-group">
			<label class="control-label col-sm-2" for="ip">Địa chỉ máy:</label>
			<div class="col-sm-10">
				<input type="ip" class="form-control" id="ip"
					placeholder="Địa chỉ máy" name="ip">
			</div>
		</div>
		<div class="form-group">
			<label class="control-label col-sm-2" for="pwd">Tên DB:</label>
			<div class="col-sm-10">
				<input type="dbName" class="form-control" id="dbName"
					placeholder="Tên DB" name="dbName">
			</div>
		</div>
		<div class="form-group">
			<label class="control-label col-sm-2" for="pwd">Username:</label>
			<div class="col-sm-10">
				<input type="username" class="form-control" id="username"
					placeholder="Username DB" name="username">
			</div>
		</div>
		<div class="form-group">
			<label class="control-label col-sm-2" for="pwd">Password:</label>
			<div class="col-sm-10">
				<input type="password" class="form-control" id="password"
					placeholder="Enter password" name="password">
			</div>
		</div>
		<div class="form-group">
			<div class="col-sm-offset-2 col-sm-10">
				<button type="submit" class="btn btn-default">Lấy thông tin bảng</button>
			</div>
		</div>
	</form>

</div>
<div id="oracle">Chưa hỗ trợ</div>
<div id="sqlServer">Chưa hỗ trợ</div>
<div id="mySql">Chưa hỗ trợ</div>
<jsp:include page="footer.jsp" />