<%--
  Created by IntelliJ IDEA.
  User: zyl
  Date: 18-4-27
  Time: 下午7:10
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>


<html>
<head>
    <title>秒杀详情页</title>
    <%@include file="common/head.jsp"%>
    <script src="/resources/script/countdown.js"></script>
    <script src="https://cdn.bootcss.com/jquery-cookie/1.4.1/jquery.cookie.js"></script>
    <script src="/resources/script/seckill.js" type="text/javascript" ></script>
    <script type="text/javascript">
        $(function () {
            seckill.details.init({
                seckillId:${seckill.seckillId},
                startTime:${seckill.startTime.time},
                endTime:${seckill.endTime.time}
            });

        })
    </script>
</head>
<body>
    <div class="container">
        <div class="panel panel-default text-center">
            <div class="panel-heading">
               <h2>${seckill.name}</h2>
            </div>
            <div class="panel-body">
                <span id="seckill-box"></span>
            </div>
        </div>
    </div>
    <div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
                        &times
                    </button>
                    <h4 class="modal-title row text-center" id="myModalLabel">
                        手机注册
                    </h4>
                </div>
                <div class="modal-body row" >
                    <div class="col-md-8 col-md-offset-2">
                        <input type="text" name="userPhone" placeholder="填手机号^o^" class="form-control" id="killPhone">

                    </div>
                </div>
                <div class="modal-footer">
                    <span id="killPhoneMessage" class="glyphicon"></span>
                    <button type="button" class="btn btn-primary" id="saveBtn">
                        提交更改
                    </button>
                </div>
            </div><!-- /.modal-content -->
        </div><!-- /.modal -->
    </div>


</body>
</html>
