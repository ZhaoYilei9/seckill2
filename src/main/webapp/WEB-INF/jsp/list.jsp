<%--
  Created by IntelliJ IDEA.
  User: zyl
  Date: 18-4-27
  Time: 下午6:59
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="common/tag.jsp"%>
<!DOCTYPE html>
<html>
<head>
    <title>秒杀商品列表</title>
    <%@include file="common/head.jsp"%>

</head>
<body>

    <div class="container">
        <div class="panel panel-default">

            <div class="panel-heading text-center">
                <h2>秒杀列表</h2>

            </div>
            <div class="panel-body">
                <table class="table table-hover">
                    <thead class="table table-hover">
                        <tr>
                            <td>名称</td>
                            <td>库存</td>
                            <td>开始时间</td>
                            <td>结束时间</td>
                            <td>创建时间</td>
                            <td>详情页</td>
                        </tr>
                    </thead>
                    <tbody>
                           <c:if test="${seckills != null}">
                               <c:forEach items="${seckills}" var="seckill"> 
                                  <tr>
                                      <td>${seckill.name}</td>
                                      <td>${seckill.number}</td>
                                      <td>
                                          <fmt:formatDate value="${seckill.startTime}" pattern="yyyy-MM-dd hh:mm:ss"/>
                                      </td>
                                      <td>
                                          <fmt:formatDate value="${seckill.endTime}" pattern="yyyy-MM-dd hh:mm:ss"/>
                                      </td>
                                      <td>
                                          <fmt:formatDate value="${seckill.createTime}" pattern="yyyy-MM-dd hh:mm:ss"/>
                                      </td>
                                      <td>
                                          <a class="btn btn-info" href="/seckill/${seckill.seckillId}/details" target="_blank">link</a>
                                      </td>
                                  </tr>
                               </c:forEach>
                           </c:if>
                    </tbody>
                </table>
            </div>
        </div>

    </div>


</body>

</html>
