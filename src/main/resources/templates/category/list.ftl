<html>
    <#include "../common/header.ftl">
    <body>

    <div id="wrapper" class="toggled">
        <#--边栏-->
            <#include "../common/nav.ftl">

        <#--主要内容-->

        <div id="page-content-wrapper">
            <div class="container-fluid">
                <div class="row clearfix">
                    <div class="col-md-12 column">
                        <table class="table table-bordered table-condensed">
                            <thead>
                            <tr>
                                <th>
                                    类目Id
                                </th>
                                <th>
                                    名称
                                </th>
                                <th>
                                    类型
                                </th>
                                <th>
                                    创建时间
                                </th>
                                <th>
                                    修改时间
                                </th>

                                <th colspan="2">操作</th>
                            </tr>
                            </thead>
                            <tbody>
                    <#list categoryList as categoryInfo>
                    <tr>
                        <td>
                            ${categoryInfo.categoryId}
                        </td>
                        <td>
                            ${categoryInfo.categoryName}
                        </td>
                        <td>
                            ${categoryInfo.categoryType}
                        </td>
                        <td>
                            ${categoryInfo.createTime}
                        </td>
                        <td>
                            ${categoryInfo.updateTime}
                        </td>
                        <td>
                            <a href="/sell/seller/category/index?categoryId=${categoryInfo.categoryId}">修改</a>
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
