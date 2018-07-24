var settingOnLoad = {
		
		loadSetting : function() {
			
			var lstTypeText = ['varchar', '_varchar', 'text', '_text'];
			var lstTypeBoolean = ['bool', '_bool'];
			
			$('#tblAllTable').each(function(){
	            $(this).find('tbody > tr').each(function(){
	            	
	            	var tableName = $(this).find('input[name="tableName"]').val(); 
	            	
	            	if(tableName != undefined) {
	            		
	            		$(this).find('#tblRowConfig').each(function(){
	            			$(this).find('tbody > tr').each(function(){
	            				
	            				$(this).find('select[name=typeGen]').children('option').remove();
	            				var type = $(this).find("#type").html();
	            				
	            				if( lstTypeText.indexOf(type) >= 0 ) {
	            					
	            					
	            					$(this).find('select[name=typeGen]').append($('<option>', { 
	            				        value: '0',
	            				        text : '--------------------------------------------' 
	            				    }));
	            					
	            					$(this).find('select[name=typeGen]').append($('<option>', { 
	            				        value: '1',
	            				        text : '1: FULL SIZE' 
	            				    }));
	            					
	            					$(this).find('select[name=typeGen]').append($('<option>', { 
	            				        value: '2',
	            				        text : '2: HALF SIZE' 
	            				    }));
	            					
	            					$(this).find('select[name=typeGen]').append($('<option>', { 
	            				        value: '3',
	            				        text : '3: NUMBER' 
	            				    }));
	            				} else if( lstTypeBoolean.indexOf(type) >= 0 ) {
	            					
	            					$(this).find('select[name=typeGen]').append($('<option>', { 
	            				        value: '0',
	            				        text : '--------------------------------------------' 
	            				    }));
	            					
	            					$(this).find('select[name=typeGen]').append($('<option>', { 
	            				        value: '1',
	            				        text : 'TRUE' 
	            				    }));
	            					
	            					$(this).find('select[name=typeGen]').append($('<option>', { 
	            				        value: '2',
	            				        text : 'FALSE' 
	            				    }));
	            				} else {
	            					$(this).find('select[name=typeGen]').hide();
	            				}
	            			});
	            		});
	            	}
	            });
			});
		},	

	chooseEnv : function(thiz) {
		var idxEnv = $(thiz).prop('selectedIndex');
		switch (idxEnv) {
		case 0:
			$("#posgresSQL").show();
			$("#sqlServer").hide();
			$("#oracle").hide();
			$("#mySql").hide();
			break;
		case 1:
			$("#posgresSQL").hide();
			$("#sqlServer").show();
			$("#oracle").hide();
			$("#mySql").hide();
			break;
		case 2:
			$("#posgresSQL").hide();
			$("#sqlServer").hide();
			$("#oracle").show();
			$("#mySql").hide();
			break;
		case 3:
			$("#posgresSQL").hide();
			$("#sqlServer").hide();
			$("#oracle").hide();
			$("#mySql").show();
			break;
		}
	},

	checkAllTable : function(thiz) {
		
		if($(thiz).prop("checked")) {
			
			$("input:checkbox[name='listTable']").prop('checked', true);
        	$(':input[type="submit"]').prop('disabled', false);
			
			
//			$('#tblAllTable').each(function(){
//                $(this).find('tr > td').each(function(){
//                	$(this).find('input[name="listTable"]').prop('checked', true);
//                	$(':input[type="submit"]').prop('disabled', false);
//                });
//			});
		} else {
			$("input:checkbox[name='listTable']").prop('checked', false);
        	$(':input[type="submit"]').prop('disabled', true);
//			$('#tblAllTable').each(function(){
//                $(this).find('tr > td').each(function(){
//                	$(this).find('input[name="listTable"]').prop('checked', false);
//                	$(':input[type="submit"]').prop('disabled', true);
//                });
//			});
		}
	},
	
	unCheckAllTable : function(thiz) {
		
		if(!$(thiz).prop("checked")) {
			$('#ckbAll').prop('checked', false);
			
			if($("input:checkbox[name='listTable']:checked").length > 0 ) {
				$(':input[type="submit"]').prop('disabled', false);
			}else {
				$(':input[type="submit"]').prop('disabled', true);
			}
			
//			$('[name="listTable"]:checked').length
//			
//			var isCheked = false;
//			
//			$('#tblAllTable').each(function(){
//	            $(this).find('tr > td').each(function(){
//	            	if($(this).find('input[name="listTable"]').prop('checked')) {
//	            		isCheked = true;
//	            		
//	            	}
//	            });
//			});
//			
//			if (isCheked) {
//				$(':input[type="submit"]').prop('disabled', false);
//			}else {
//				$(':input[type="submit"]').prop('disabled', true);
//			}
			
		} else {
			$(':input[type="submit"]').prop('disabled', false);
		}
	},
	
	setRowConfig : function (tableName) {
		
		var posgresSqlColumn;
		var posgresSqlTableArr = [];
		var numberTable = 0;
		
		$('#tblAllTable').each(function(){
            $(this).find('tbody > tr').each(function(){
            	
            	var tableName = $(this).find('input[name="tableName"]').val(); 
            	
            	if(tableName != undefined) {
            		
            		var posgresSqlTable = {tableName:tableName, listColumn:""};
            		
            		var listColumn = [];
            		var numberColumn = 0;
            		
            		$(this).find('#tblRowConfig').each(function(){
            			$(this).find('tbody > tr').each(function(){
            				
            				var column = {nameColumn:"", rowConfig:"", type:""};
            				
            				column.nameColumn = $(this).find("#nameColumn").html();
            				column.type = $(this).find("#type").html();
            				column.rowConfig = $(this).find('select[name=typeGen]').prop('selectedIndex');
            				
//            				console.log("Column Name: " + column.nameColumn + " has type " + column.type);
            				
            				listColumn[numberColumn] = column;
            				numberColumn++;
            			});
            		});
            		posgresSqlTable.listColumn = listColumn
            		posgresSqlTableArr[numberTable] = posgresSqlTable;
            		numberTable++;
            	}
            });
		});
		
		
		
//		var myJSON = JSON.stringify(posgresSqlTable);
		
//		console.log(numberTable);
//		console.log(posgresSqlTableArr);
		
		$("input[name=rowConfig]").val(JSON.stringify(posgresSqlTableArr));
	},
	openDetails : function (tableName) {
		
//		console.log(tableName);
		
		var isShow = $(tableName).find("#trHiden").val();
		
//		console.log(isShow);
		
		if (isShow == '0') {
			$(tableName).closest('tr').removeAttr("style");
			$(tableName).removeAttr("style");
			$(tableName).find("#trHiden").val("1")
		} else {
			$(tableName).closest('tr').attr("style", "background-color: aqua;");
			$(tableName).attr("style", "display: none");
			$(tableName).find("#trHiden").val("0")
		}
	},
};