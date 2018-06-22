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
                    <form action="/sell/seller/category/saveOrUpdate" method="post" role="form">
                        <input type="hidden" name="categoryId" value="${(productCategory.categoryId)!}">
                        <div class="form-group">
                            <label>类目名</label>
                            <input name="categoryName" type="text" class="form-control" value="${(productCategory.categoryName)!}" />
                        </div>
                        <div class="form-group">
                            <label>类目编号</label>
                            <input name="categoryType" type="text" class="form-control"  value="${(productCategory.categoryType)!}"/>
                        </div>
                        <button type="submit" class="btn btn-default">提交</button>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>

</body>
</html>