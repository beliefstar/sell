<!doctype html>
<html lang="zh">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>订单详细</title>
    <link href="https://cdn.bootcss.com/bootstrap/3.3.5/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
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
                <a href="/sell/seller/order/finish?orderId=${orderDTO.orderId}">
                    <button type="button" class="btn btn-primary btn-default">完结订单</button>
                </a>
                <a href="/sell/seller/order/cancel?orderId=${orderDTO.orderId}">
                    <button type="button" class="btn btn-danger btn-default">取消订单</button>
                </a>
            </div>
        </#if>
    </div>
</div>
</body>
</html>