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
                            <th>商品ID</th>
                            <th>商品名</th>
                            <th>商品单价</th>
                            <th>商品库存</th>
                            <th style="width: 200px;">商品描述</th>
                            <th>商品图片</th>
                            <th>类目编号</th>
                            <th>商品状态</th>
                            <th>创建时间</th>
                            <th>修改时间</th>
                            <th colspan="2">操作</th>
                        </tr>
                        </thead>
                        <tbody>
                <#list productInfoPage.content as item>
                <tr>
                    <td>${item.productId}</td>
                    <td>${item.productName}</td>
                    <td>${item.productPrice}</td>
                    <td>${item.productStock}</td>
                    <td>${item.productDescription}</td>
                    <td>
                        <img src="${item.productIcon}" style="height: 100px">
                    </td>
                    <td>${item.categoryType}</td>
                    <td>${item.getProductStatusEnum()}</td>
                    <td>${item.createTime}</td>
                    <td>${item.updateTime}</td>
                    <td>
                        <a href="/sell/seller/product/index?productId=${item.productId}">修改</a>
                    </td>
                    <td>
                        <#if item.getProductStatusEnum() == "在架">
                            <a href="/sell/seller/product/offSale?productId=${item.productId}">下架</a>
                        <#else >
                            <a href="/sell/seller/product/onSale?productId=${item.productId}">上架</a>
                        </#if>
                    </td>
                </tr>
                </#list>
                        </tbody>
                    </table>
                </div>
                <div class="col-md-12 column">
                    <form id="mForm">
                        <input type="hidden" id="pageInput" name="page" value="0">
                        <ul class="pagination pull-right">
                    <#if currentPage == 0>
                        <li class="disabled">
                            <a href="javascript:void(0)">上一页</a>
                        </li>
                    <#else >
                        <li>
                            <a href="javascript:void(0)" onclick="jumpPage(${currentPage - 1})">上一页</a>
                        </li>
                    </#if>
                    <#list 1..productInfoPage.getTotalPages() as item>
                        <#if (currentPage + 1) lte (item + 5) && (currentPage + 1) gte (item - 5)>
                            <#if currentPage == item_index>
                                <li class="disabled"><a href="#">${item}</a></li>
                            <#else >
                                <li class="">
                                    <a href="javascript:void(0)" onclick="jumpPage(${item_index})">${item}</a>
                                </li>
                            </#if>
                        </#if>
                    </#list>
                    <#if currentPage == (productInfoPage.getTotalPages() - 1)>
                        <li class="disabled">
                            <a href="javascript:void(0)">下一页</a>
                        </li>
                    <#else >
                        <li>
                            <a href="javascript:void(0)" onclick="jumpPage(${currentPage + 1})">下一页</a>
                        </li>
                    </#if>
                        </ul>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>


<script
        src="https://code.jquery.com/jquery-2.2.4.min.js"
        integrity="sha256-BbhdlvQf/xTY9gja0Dq3HiwQF8LaCRTXxZKRutelT44="
        crossorigin="anonymous"></script>
<script>
    
    function jumpPage(pageNum) {
        $("#pageInput").val(pageNum);
        $("#mForm").submit();
    }
    
</script>
</body>
</html>