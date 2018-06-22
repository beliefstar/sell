<!doctype html>
<html lang="zh">
    <#include "../common/header.ftl">
<body>

<div id="wrapper" class="toggled">
    <#include "../common/nav.ftl">

    <div id="page-content-wrapper">
        <div class="container-fluid">
            <div class="row clearfix">
                <div class="col-md-12 column">
                    <table class="table table-bordered table-hover">
                        <thead>
                        <tr>
                            <th>类目ID</th>
                            <th>类目名</th>
                            <th>类目编号</th>
                            <th>支付状态</th>
                            <th>创建时间</th>
                            <th>操作</th>
                        </tr>
                        </thead>
                        <tbody>
                <#list categoryList as item>
                <tr>
                    <td>${item.categoryId}</td>
                    <td>${item.categoryName}</td>
                    <td>${item.categoryType}</td>
                    <td>${item.createTime}</td>
                    <td>${item.updateTime}</td>
                    <td>
                        <a href="/sell/seller/category/index?categoryId=${item.categoryId}">修改</a>
                    </td>
                </tr>
                </#list>
                        </tbody>
                    </table>
                </div>

            </div>
        </div>
    </div>
</div>


</body>
</html>