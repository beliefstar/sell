<!doctype html>
<html lang="zh">
<#include "../common/header.ftl">

<style>
    #productImg img {
        width: 200px;
    }
</style>
<body>

<div id="wrapper" class="toggled">
<#include "../common/nav.ftl">

    <div id="page-content-wrapper">

        <div class="container-fluid">
            <div class="row clearfix">
                <div class="col-md-12 column">
                    <form action="/sell/seller/product/saveOrUpdate" method="post" role="form">
                        <input type="hidden" name="productId" value="${(productInfo.productId)!}">
                        <div class="form-group">
                            <label>名称</label>
                            <input name="productName" type="text" class="form-control" value="${(productInfo.productName)!}" />
                        </div>
                        <div class="form-group">
                            <label>商品单价</label>
                            <input name="productPrice" type="text" class="form-control"  value="${(productInfo.productPrice)!}"/>
                        </div>
                        <div class="form-group">
                            <label>商品库存</label>
                            <input name="productStock" type="text" class="form-control"  value="${(productInfo.productStock)!}"/>
                        </div>
                        <div class="form-group">
                            <label>商品描述</label>
                            <input name="productDescription" type="text" class="form-control" value="${(productInfo.productDescription)!}" />
                        </div>
                        <div class="form-group">
                            <label>商品小图</label>
                            <div id="productImg"></div>
                            <div style="position: relative;cursor: pointer">
                                <input type="file" style="position: absolute; width: 100%;height: 100%;top: 0;left: 0;opacity: 0;cursor: pointer" id="imgFile">
                                <a href="javascript:void(0)" style="cursor: pointer">点击上传</a>
                            </div>
                            <input name="productIcon" type="text" id="imgPath" class="form-control" value="${(productInfo.productIcon)!}" />
                        </div>
                        <div class="form-group">
                            <label>类目</label>
                            <select name="categoryType" class="form-control">
                                <#list categories as item>
                                    <option value="${item.categoryType}"
                                            <#if productInfo?? && productInfo.categoryType == item.categoryType>
                                                selected
                                            </#if>
                                    >${item.categoryName}</option>
                                </#list>
                            </select>
                        </div>

                        <button type="submit" class="btn btn-default">提交</button>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>
<script
        src="https://code.jquery.com/jquery-2.2.4.min.js"
        integrity="sha256-BbhdlvQf/xTY9gja0Dq3HiwQF8LaCRTXxZKRutelT44="
        crossorigin="anonymous">
</script>
<script>
    $("#imgFile").change(function () {
        var formData = new FormData();
        formData.append('file', $(this)[0].files[0]);
        jQuery.ajax({
            url: '${imgServer}/upImg',
            type: 'POST',
            cache: false,
            data: formData,
            processData: false,
            contentType: false
        }).done(function (result) {
            if (result.status === 200) {
                $("#imgPath").val("${imgServer}" + result.data);
                $("#productImg").html("<img src=\"${imgServer}" + result.data + "\" >");
            }
        });
    });
    $("#imgPath").keyup(function () {
        $("#productImg").html("<img src=\"" + $(this).val() + "\" >");
    });
</script>
</body>
</html>