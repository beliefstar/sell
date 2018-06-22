<!doctype html>
<html lang="zh">
<#include "../common/header.ftl">
<body>

<div class="container" style="padding-top: 30px;">
    <div class="row clearfix">
        <div class="col-md-12 column">
            <div class="alert alert-dismissable alert-info">
                <button type="button" class="close" data-dismiss="alert" aria-hidden="true">×</button>
                <h4>
                    ${message!}
                </h4>  <a href="${url!}" class="alert-link"><span id="se">3</span>秒后自动跳转</a>
            </div>
        </div>
    </div>
</div>
<script>

    var se = 3;
    var seElem = document.getElementById("se");

    var timer = setInterval(function () {
        se--;
        seElem.innerHTML = se;
        if (se <= 0) {
            window.location.href = "${url!}";
        }
    }, 1000);

</script>
</body>
</html>