<!doctype html>
<html lang="zh">
    <#include "../common/header.ftl">
<body>

<div id="wrapper" class="toggled">
    <#include "../common/nav.ftl">
    <div id="page-content-wrapper">

        <div class="container">
    <div class="row clearfix">
        <div class="col-md-6 column">
            <table class="table table-bordered table-hover">
                <thead>
                <tr>
                    <th>订单ID</th>
                    <th>总金额</th>
                </tr>
                </thead>
                <tbody>
                <tr>
                    <td>${orderDTO.orderId}</td>
                    <td>${orderDTO.orderAmount}</td>
                </tr>
                </tbody>
            </table>
        </div>

        <div class="col-md-12 column">
            <table class="table table-bordered table-hover">
                <thead>
                <tr>
                    <th>商品ID</th>
                    <th>商品名</th>
                    <th>价格</th>
                    <th>数量</th>
                    <th>金额</th>
                </tr>
                </thead>
                <tbody>
                <#list orderDTO.orderDetailList as item>
                    <tr>
                        <td>${item.productId}</td>
                        <td>${item.productName}</td>
                        <td>${item.productPrice}</td>
                        <td>${item.productQuantity}</td>
                        <td>${item.productPrice * item.productQuantity}</td>
                    </tr>
                </#list>
                </tbody>
            </table>
        </div>
        <#if orderDTO.getOrderStatusEnum() == "新订单">
            <div class="col-md-6">
                <a href="/sell/seller/order/finish?orderId=${orderDTO.orderId}" type="button" class="btn btn-primary btn-default">完结订单</a>
                <a href="/sell/seller/order/cancel?orderId=${orderDTO.orderId}" type="button" class="btn btn-danger btn-default">取消订单</a>
            </div>
        </#if>
    </div>
</div>
    </div>
</div>
</body>
</html>