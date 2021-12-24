<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + "/";%>
<!DOCTYPE html>
<html>
<head>
    <base href="<%=basePath%>">
    <meta charset="UTF-8">

    <link href="jquery/bootstrap_3.3.0/css/bootstrap.min.css" type="text/css" rel="stylesheet"/>
    <link href="jquery/bootstrap-datetimepicker-master/css/bootstrap-datetimepicker.min.css" type="text/css" rel="stylesheet"/>

    <script type="text/javascript" src="jquery/jquery-1.11.1-min.js"></script>
    <script type="text/javascript" src="jquery/bootstrap_3.3.0/js/bootstrap.min.js"></script>
    <script type="text/javascript" src="jquery/bootstrap-datetimepicker-master/js/bootstrap-datetimepicker.js"></script>
    <script type="text/javascript" src="jquery/bootstrap-datetimepicker-master/locale/bootstrap-datetimepicker.zh-CN.js"></script>
    <link rel="stylesheet" type="text/css" href="jquery/bs_pagination/jquery.bs_pagination.min.css">
    <script type="text/javascript" src="jquery/bs_pagination/jquery.bs_pagination.min.js"></script>
    <script type="text/javascript" src="jquery/bs_pagination/en.js"></script>

    <script type="text/javascript">

        $(function () {

            $("#updateBtn").click(function (){

                $.ajax({
                    url : "workbench/activity/updateActivity.do",
                    data : {

                        "id" : $.trim($("#edit-id").val()),
                        "owner": $.trim($("#edit-marketActivityOwner").val()),
                        "name": $.trim($("#edit-marketActivityName").val()),
                        "startDate": $.trim($("#edit-startTime").val()),
                        "endDate": $.trim($("#edit-endTime").val()),
                        "cost": $.trim($("#edit-cost").val()),
                        "description": $.trim($("#edit-describe").val())

                    },
                    type : "post",
                    dataType: "json",
                    success : function (data){

                        //返回一个success：true/false
                        if (data.success){
                            $("#editActivityModal").modal("hide");
                            pageList($("#activityPage").bs_pagination('getOption', 'currentPage')
                             ,$("#activityPage").bs_pagination('getOption', 'rowsPerPage'));
                        }else {
                            alert("修改失败！")
                        }

                    }
                })

            })

            $("#editBtn").click(function (){
                $(".time").datetimepicker({
                    minView: "month",
                    language: 'zh-CN',
                    format: 'yyyy-mm-dd',
                    autoclose: true,
                    todayBtn: true,
                    pickerPosition: "bottom-left"
                });

               if ( $("input[name=xz]:checked").length==0){
                   alert("请选择需要修改的内容！");

               }else if ($("input[name=xz]:checked").length>1){
                   alert("一直只能修改一条哦！");
               }else {
                   var id = $("input[name=xz]:checked").val();
                   $.ajax({
                       url : "workbench/activity/edit.do",
                       data : {

                           "id":id
                       },
                       type : "get",
                       dataType: "json",
                       success : function (data){
                           html ="";
                           //返回一个activity
                           $.each(data.userList,function (i,n){
                              html += "<option value='"+n.id+"'>"+n.name+"</option>"
                           })

                           $("#edit-marketActivityOwner").html(html);
                           $("#edit-id").val(data.activity.id);
                           $("#edit-marketActivityOwner").val(data.activity.owner);
                           $("#edit-marketActivityName").val(data.activity.name);
                           $("#edit-startTime").val(data.activity.startDate);
                           $("#edit-endTime").val(data.activity.endDate);
                           $("#edit-cost").val(data.activity.cost);
                           $("#edit-describe").val(data.activity.description);



                       }


                   })

                   $("#editActivityModal").modal("show");
               }




            })

            $("#deleteBtn").click(function (){
                if ($("input[name=xz]:checked").length==0){
                    alert("请选择需要删除的市场活动！");
                }else{
                    if (confirm("确定删除所选择的记录吗？")){
                        var param = "";
                        var tmp= $("input[name=xz]:checked");
                        for (i=0;i<tmp.length;i++){
                            param += "id="+$(tmp[i]).val();
                            if (i<tmp.length-1){
                                param += "&";
                            }
                        }
                        $.ajax({
                            url : "workbench/activity/delete.do",
                            data : param,
                            type : "get",
                            dataType: "json",
                            success : function (data){
                                if (data.success){
                                    pageList(1,$("#activityPage").bs_pagination('getOption', 'rowsPerPage'))
                                    alert("删除成功！")

                                }else {
                                    alert("删除失败！");
                                }

                            }
                        })

                    }

                }

            })
            $("#qx").click(function (){
                $("input[name=xz]").prop("checked",this.checked);

            })
            $("#activiBody").on("click",$("input[name=xz]"),function (){
                $("#qx").prop("checked",$("input[name=xz]").length==$("input[name=xz]:checked").length)
            })

            $("#addBtn").click(function () {

                $(".time").datetimepicker({
                    minView: "month",
                    language: 'zh-CN',
                    format: 'yyyy-mm-dd',
                    autoclose: true,
                    todayBtn: true,
                    pickerPosition: "bottom-left"
                });

                $.ajax({
                    url: "workbench/activity/getUserList.do",
                    type: "get",
                    dataType: "json",
                    success: function (data) {
                        var html = "";
                        $.each(data, function (i, n) {
                            html += "<option value='"+n.id+"'>" + n.name + "</option>";
                            $("#create-Owner").html(html);
                        })
                        $("#create-Owner").val("${user.id}");

                    }
                })
                <%--$("#create-marketActivityOwner").val("${user.id}");--%>
                $("#createActivityModal").modal("show");
            })


            $("#saveBtn").click(function () {

                $.ajax({
                    url: "workbench/activity/save.do",
                    data: {

                        "owner": $.trim($("#create-Owner").val()),
                        "name": $.trim($("#create-Name").val()),
                        "startDate": $.trim($("#create-startTime").val()),
                        "endDate": $.trim($("#create-endTime").val()),
                        "cost": $.trim($("#cost").val()),
                        "description": $.trim($("#create-describe").val())

                    },
                    type: "post",
                    dataType: "json",
                    success: function (data) {

                        if (data.success) {
                            //刷新市场活动列表（局部刷新）
                            pageList(1,2);
                            //关闭模态窗口
                            $("#createActivityModal").modal("hide");
                            // $("#create-Owner").val("");
                            $("#create-Name").val("");
                            $("#create-startTime").val("");
                            $("#create-endTime").val("");
                            $("#cost").val("");
                            $("#create-describe").val("");

                        } else {
                            alert("添加失败");

                        }

                    }
                })


            })
            $("#searchBtn").click(function (){

                $("#hidden-name").val($.trim($("#search-name").val()));
                $("#hidden-owner").val($.trim($("#search-owner").val()));
                $("#hidden-startTime").val($.trim($("#search-startTime").val()));
                $("#hidden-endTime").val($.trim($("#search-endTime").val()));
                pageList(1,2);

            })

            pageList(1,2);

        });
        function pageList(pageNo,pageSize){
            $("#qx").prop("checked",false);

            $("#search-name").val($.trim($("#hidden-name").val()));
            $("#search-owner").val($.trim($("#hidden-owner").val()));
            $("#search-startTime").val($.trim($("#hidden-startTime").val()));
            $("#search-endTime").val($.trim($("#hidden-endTime").val()));

            $.ajax({
                url : "workbench/activity/pageList.do",
                data : {
                    "pageNo" : pageNo,
                    "pageSize" : pageSize,
                    "search-name" : $.trim($("#search-name").val()),
                    "search-owner" : $.trim($("#search-owner").val()),
                    "search-startTime" : $.trim($("#search-startTime").val()),
                    "search-endTime" : $.trim($("#search-endTime").val())
                },
                type : "get",
                dataType: "json",
                success : function (data){
                    html = "";
                    //{"tatal":100,[dataList:{市场活动列表1},{市场活动列表2},{市场活动列表2}]}

                    $.each(data.dataList,function (i,n){

                        html += '<tr class="active">';
                        html += '<td><input type="checkbox" name="xz" value="'+n.id+'"/></td>';
                        html += '<td><a style="text-decoration: none; cursor: pointer;" onclick="window.location.href=\'workbench/activity/detail.do?id='+n.id+'\';">'+n.name+'</a></td>';
                        html += '<td>'+n.owner+'</td>';
                        html += '<td>'+n.startDate+'</td>';
                        html += '<td>'+n.endDate+'</td>';
                        html += '</tr>';

                    })
                    $("#activiBody").html(html);
                    //计算总页数
                    var totalPages = data.total%pageSize==0?data.total/pageSize:parseInt(data.total/pageSize)+1;
                    $("#activityPage").bs_pagination({
                        currentPage: pageNo, // 页码
                        rowsPerPage: pageSize, // 每页显示的记录条数
                        maxRowsPerPage: 20, // 每页最多显示的记录条数
                        totalPages: totalPages, // 总页数
                        totalRows: data.total, // 总记录条数

                        visiblePageLinks: 3, // 显示几个卡片

                        showGoToPage: true,
                        showRowsPerPage: true,
                        showRowsInfo: true,
                        showRowsDefaultInfo: true,

                        onChangePage : function(event, data){
                            pageList(data.currentPage , data.rowsPerPage);
                        }
                    });

                }
            })
        }

    </script>
</head>
<body>
<input type="hidden" id="hidden-name" />
<input type="hidden" id="hidden-owner" />
<input type="hidden" id="hidden-startTime" />
<input type="hidden" id="hidden-endTime" />


<!-- 创建市场活动的模态窗口 -->
<div class="modal fade" id="createActivityModal" role="dialog">
    <div class="modal-dialog" role="document" style="width: 85%;">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal">
                    <span aria-hidden="true">×</span>
                </button>
                <h4 class="modal-title" id="myModalLabel1">创建市场活动</h4>
            </div>
            <div class="modal-body">

                <form class="form-horizontal" role="form">

                    <div class="form-group">
                        <label for="create-marketActivityOwner" class="col-sm-2 control-label">所有者<span
                                style="font-size: 15px; color: red;">*</span></label>
                        <div class="col-sm-10" style="width: 300px;">
                            <select class="form-control" id="create-Owner">


                            </select>
                        </div>
                        <label for="create-marketActivityName" class="col-sm-2 control-label">名称<span
                                style="font-size: 15px; color: red;">*</span></label>
                        <div class="col-sm-10" style="width: 300px;">
                            <input type="text" class="form-control" id="create-Name">
                        </div>
                    </div>

                    <div class="form-group">
                        <label for="create-startTime" class="col-sm-2 control-label">开始日期</label>
                        <div class="col-sm-10" style="width: 300px;">
                            <input type="text" class="form-control time" id="create-startTime" readonly>
                        </div>
                        <label for="create-endTime" class="col-sm-2 control-label">结束日期</label>
                        <div class="col-sm-10" style="width: 300px;">
                            <input type="text" class="form-control time" id="create-endTime" readonly>
                        </div>
                    </div>
                    <div class="form-group">

                        <label for="create-cost" class="col-sm-2 control-label">成本</label>
                        <div class="col-sm-10" style="width: 300px;">
                            <input type="text" class="form-control" id="cost">
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="create-describe" class="col-sm-2 control-label">描述</label>
                        <div class="col-sm-10" style="width: 81%;">
                            <textarea class="form-control" rows="3" id="create-describe"></textarea>
                        </div>
                    </div>

                </form>

            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                <button type="button" class="btn btn-primary" id="saveBtn">保存</button>
            </div>
        </div>
    </div>
</div>

<!-- 修改市场活动的模态窗口 -->
<div class="modal fade" id="editActivityModal" role="dialog">
    <div class="modal-dialog" role="document" style="width: 85%;">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal">
                    <span aria-hidden="true">×</span>
                </button>
                <h4 class="modal-title" id="myModalLabel2">修改市场活动</h4>
            </div>
            <div class="modal-body">

                <form class="form-horizontal" role="form">

                    <input type="hidden" id="edit-id" />

                    <div class="form-group">
                        <label for="edit-marketActivityOwner" class="col-sm-2 control-label">所有者<span
                                style="font-size: 15px; color: red;">*</span></label>
                        <div class="col-sm-10" style="width: 300px;">
                            <select class="form-control" id="edit-marketActivityOwner">
                            </select>
                        </div>
                        <label for="edit-marketActivityName" class="col-sm-2 control-label">名称<span
                                style="font-size: 15px; color: red;">*</span></label>
                        <div class="col-sm-10" style="width: 300px;">
                            <input type="text" class="form-control" id="edit-marketActivityName">
                        </div>
                    </div>

                    <div class="form-group">
                        <label for="edit-startTime" class="col-sm-2 control-label">开始日期</label>
                        <div class="col-sm-10" style="width: 300px;">
                            <input type="text" class="form-control time" id="edit-startTime" readonly>
                        </div>
                        <label for="edit-endTime" class="col-sm-2 control-label time">结束日期</label>
                        <div class="col-sm-10" style="width: 300px;">
                            <input type="text" class="form-control time" id="edit-endTime" readonly>
                        </div>
                    </div>

                    <div class="form-group">
                        <label for="edit-cost" class="col-sm-2 control-label">成本</label>
                        <div class="col-sm-10" style="width: 300px;">
                            <input type="text" class="form-control" id="edit-cost">
                        </div>
                    </div>

                    <div class="form-group">
                        <label for="edit-describe" class="col-sm-2 control-label">描述</label>
                        <div class="col-sm-10" style="width: 81%;">
                            <textarea class="form-control" rows="3" id="edit-describe"></textarea>
                        </div>
                    </div>

                </form>

            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                <button type="button" class="btn btn-primary" id="updateBtn">更新</button>
            </div>
        </div>
    </div>
</div>


<div>
    <div style="position: relative; left: 10px; top: -10px;">
        <div class="page-header">
            <h3>市场活动列表</h3>
        </div>
    </div>
</div>
<div style="position: relative; top: -20px; left: 0px; width: 100%; height: 100%;">
    <div style="width: 100%; position: absolute;top: 5px; left: 10px;">

        <div class="btn-toolbar" role="toolbar" style="height: 80px;">
            <form class="form-inline" role="form" style="position: relative;top: 8%; left: 5px;">

                <div class="form-group">
                    <div class="input-group">
                        <div class="input-group-addon">名称</div>
                        <input class="form-control" type="text" id="search-name">
                    </div>
                </div>

                <div class="form-group">
                    <div class="input-group">
                        <div class="input-group-addon">所有者</div>
                        <input class="form-control" type="text" id="search-owner">
                    </div>
                </div>


                <div class="form-group">
                    <div class="input-group">
                        <div class="input-group-addon">开始日期</div>
                        <input class="form-control" type="text" id="search-startTime"/>
                    </div>
                </div>
                <div class="form-group">
                    <div class="input-group">
                        <div class="input-group-addon">结束日期</div>
                        <input class="form-control" type="text" id="search-endTime">
                    </div>
                </div>

                <button type="button" id="searchBtn" class="btn btn-default">查询</button>

            </form>
        </div>
        <div class="btn-toolbar" role="toolbar"
             style="background-color: #F7F7F7; height: 50px; position: relative;top: 5px;">
            <div class="btn-group" style="position: relative; top: 18%;">
                <button type="button" class="btn btn-primary" id="addBtn"><span class="glyphicon glyphicon-plus"></span>
                    创建
                </button>
                <button type="button" class="btn btn-default" id="editBtn"><span
                        class="glyphicon glyphicon-pencil"></span> 修改
                </button>
                <button type="button" class="btn btn-danger" id="deleteBtn"><span class="glyphicon glyphicon-minus"></span> 删除</button>
            </div>

        </div>
        <div style="position: relative;top: 10px;">
            <table class="table table-hover">
                <thead>
                <tr style="color: #B3B3B3;">
                    <td><input type="checkbox" id="qx"/></td>
                    <td>名称</td>
                    <td>所有者</td>
                    <td>开始日期</td>
                    <td>结束日期</td>
                </tr>
                </thead>
                <tbody id="activiBody">
<%--                <tr class="active" >--%>
<%--                    <td><input type="checkbox"/></td>--%>
<%--                    <td><a style="text-decoration: none; cursor: pointer;"--%>
<%--                           onclick="window.location.href='workbench/activity/detail.jsp';">发传单</a></td>--%>
<%--                    <td>zhangsan</td>--%>
<%--                    <td>2020-10-10</td>--%>
<%--                    <td>2020-10-20</td>--%>
<%--                </tr>--%>
<%--                <tr class="active">--%>
<%--                    <td><input type="checkbox"/></td>--%>
<%--                    <td><a style="text-decoration: none; cursor: pointer;"--%>
<%--                           onclick="window.location.href='workbench/activity/detail.jsp';">发传单</a></td>--%>
<%--                    <td>zhangsan</td>--%>
<%--                    <td>2020-10-10</td>--%>
<%--                    <td>2020-10-20</td>--%>
<%--                </tr>--%>
                </tbody>
            </table>
        </div>

        <div style="height: 50px; position: relative;top: 30px;">
            <div id="activityPage"></div>
        </div>
    </div>
    </div>
</body>
</html>