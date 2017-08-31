<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<%   String path;
    path = request.getContextPath();
    String basePath = request.getScheme() + "://"
            + request.getServerName() + ":" + request.getServerPort()
            + path + "/";%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script type="text/javascript"
    src="<%=basePath%>js/jquery.min.js"></script>
<script type="text/javascript"
    src="<%=basePath%>js/jquery.easyui.min.js"></script>
 <script type="text/javascript"
    src="<%=basePath%>js/easyui-lang-zh_CN.js"></script>
<link rel="stylesheet" type="text/css"
    href="<%=basePath%>css/easyui.css">
<link rel="stylesheet" type="text/css"
    href="<%=basePath%>css/icon.css">
<script type="text/javascript">

var url;
function newUser(){
    $('#dlg').dialog('open').dialog('setTitle','新增');
    $('#fm').form('clear');
    url = 'http://localhost:8080/test4/merch/merchAdd';
}
function editUser(){
    var row = $('#dg').datagrid('getSelected');
    if (row){
        $('#dlg').dialog('open').dialog('setTitle','修改');
        $('#fm').form('load',row);
        url = 'http://localhost:8080/test4/merch/update' ;
    }
}
function saveUser(){
    $('#fm').form('submit',{
        url: url,
        onSubmit: function(){
            return $(this).form('validate');
        },
        success: function(result){
        	
         //var result = eval('('+result+')');
           // if (result.success){
               /*  $.messager.show({
                    title: '提示',
                    msg: result.message
                }); */
                console.log(result.message);
                $('#dlg').dialog('close');        // close the dialog
                $('#dg').datagrid('reload');    // reload the user data
          //  } else {
             /*   $.messager.show({
                    title: '提示',
                    msg: result.message
                });  */
         //  }
        }
    });
}
function removeUser(){
    var row = $('#dg').datagrid('getSelections');
    console.log(row);
   
   

    if (row){
        $.messager.confirm('确认','您确定要删除吗？',function(r){
            if (r){
            	
            	 for (var i = 0; i < row.length; i++) {
            	        //获取自定义table 的中的checkbox值
            	        var id = row[i].id;   //OTRECORDID这个是你要在列表中取的单个id
            	       
            	        
            	        $.post('http://localhost:8080/test4/merch/delmany',{id:id},$('#dg').datagrid('reload') ,'json');
                        
            	    }
            	
            }
        });
    }
}
function doSearch(){
    $('#dg').datagrid('load',{
        name: $('#username').val(),
        xueli: $('#userxueli').val()
    });
}
</script>
    
</head>


<body>

<div align="center">
    <table id="dg" class="easyui-datagrid" title="商品列表" style="width:700px;height:450px"
           fitColumns="true" pagination="true"
           data-options="rownumbers:true, singleSelect:false,url:'http://localhost:8080/test4/merch/main',method:'post',toolbar:'#tb'">
        <thead>
        <tr><th data-options="field:'cd',checkbox:true"></th>
            <th data-options="field:'id',width:80 ,align:'right'">ID</th>
            <th data-options="field:'cade',width:100 ,align:'right'">cade</th>
            <th data-options="field:'name',width:80,align:'right'">Name</th>
            <th data-options="field:'factory',width:80,align:'right'">Factory</th>
            <th data-options="field:'packages',width:240 ,align:'right'">Package</th>
            <th data-options="field:'price',width:60,align:'center'">Price</th>  </tr>
        </thead>
    </table>
</div>
 <div id="tb">
        <a href="#" class="easyui-linkbutton"
            iconCls="icon-add" plain="true" onclick="newUser()">添加</a> <a
            href="#" class="easyui-linkbutton"
            iconCls="icon-edit" plain="true" onclick="editUser()">修改</a> <a
            href="#" class="easyui-linkbutton"
            iconCls="icon-remove" plain="true"  onclick="removeUser()">删除</a>
  <div> 		
    <div id="dlg" class="easyui-dialog" style="width:400px;height:280px;padding:10px 20px"
            closed="true" buttons="#dlg-buttons">
        <div class="ftitle">商品信息</div>
        <form id="fm" method="post" novalidate>
        <div class="fitem">
                <label>序号:</label>
                <input name="id"  required="true" readonly="readonly">
            </div>
            
            <div class="fitem">
                <label>名字:</label>
                <input name="name" class="easyui-validatebox" required="true">
            </div>
            <div class="fitem">
                <label>编码:</label>
                <input name="cade" class="easyui-validatebox" required="true">
            </div>
            <div class="fitem">
                <label>厂商:</label>
                <input name="factory" class="easyui-validatebox" required="true">
            </div>
            <div class="fitem">
                <label>包装类型:</label>
                <input name="packages" class="easyui-validatebox" required="true">
            </div>
            <div class="fitem">
                <label>价格:</label>
                <input name="price" class="easyui-validatebox" required="true">
            </div>
        </form>
    </div>
    <div id="dlg-buttons">
        <a href="#" class="easyui-linkbutton" iconCls="icon-ok" onclick="saveUser()">提交</a>
        <a href="#" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#dlg').dialog('close')">取消</a>
    </div>
    
          <form action="http://localhost:8080/test4/merch/upload" method="post" enctype="multipart/form-data">
                 <input type="file" id="myfile" name="myfile"/>&nbsp;&nbsp;   
                 <input type="submit"  value="导入Excel" onclick="importEmp()"/>   
         </form>
	 <form action="http://localhost:8080/test4/merch/excel" method="post">
        <button>上传</button>
        
     </form> 


<script type="text/javascript">

//Excel文件导入到数据库中   
function importEmp(){   
    //检验导入的文件是否为Excel文件   
    var excelPath = document.getElementById("excelPath").value;   
    if(excelPath == null || excelPath == ''){   
        alert("请选择要上传的Excel文件");   
        return;   
    }else{   
        var fileExtend = excelPath.substring(excelPath.lastIndexOf('.')).toLowerCase();    
        if(fileExtend == '.xls'){   
        }else{   
            alert("文件格式需为'.xls'格式");   
            return;   
        }   
    }   
   
    
}  

</script>
    
    
</body>
</html>