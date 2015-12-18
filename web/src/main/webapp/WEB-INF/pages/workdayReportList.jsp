<%@ include file="/common/taglibs.jsp"%>

<head>
    <title><fmt:message key="workdayReportList.title"/></title>
    <meta name="menu" content="WorkdayReportMenu"/>
</head>
<body>
    <h2><fmt:message key="workdayReportList.heading"/></h2>

    <form method="get" action="${ctx}/workdayReports" id="searchForm" class="form-inline">
    <div id="search" class="text-right">
        <span class="col-sm-9">
            <input type="text" size="20" name="q" id="query" value="${param.q}"
                   placeholder="<fmt:message key="search.enterTerms"/>" class="form-control input-sm"/>
        </span>
        <button id="button.search" class="btn btn-default btn-sm" type="submit">
            <i class="icon-search"></i> <fmt:message key="button.search"/>
        </button>
    </div>
    </form>

    <p><fmt:message key="workdayReportList.message"/></p>

    <div id="actions" class="btn-group">
        <a class="btn btn-primary" href="<c:url value='/editWorkdayReport'/>" >
            <i class="icon-plus icon-white"></i> <fmt:message key="button.add"/>
        </a>
        <a class="btn btn-default" href="<c:url value="/home"/>" >
            <i class="icon-ok"></i> <fmt:message key="button.done"/>
        </a>
    </div>

    <display:table name="workdayReports" class="table table-condensed table-striped table-hover" requestURI="" id="workdayReportList" export="true" pagesize="25">
        <display:column property="id" sortable="true" href="editWorkdayReport" media="html"
            paramId="id" paramProperty="id" titleKey="workdayReport.id"/>
        <display:column property="id" media="csv excel xml pdf" titleKey="workdayReport.id"/>
        <display:column property="lastUpdateTime" sortable="true" titleKey="workdayReport.lastUpdateTime"/>
        <display:column property="todayReport" sortable="true" titleKey="workdayReport.todayReport"/>
        <display:column property="tomrrowPlan" sortable="true" titleKey="workdayReport.tomrrowPlan"/>
        <display:column property="userId" sortable="true" titleKey="workdayReport.userId"/>
        <display:column property="writeTime" sortable="true" titleKey="workdayReport.writeTime"/>

        <display:setProperty name="paging.banner.item_name"><fmt:message key="workdayReportList.workdayReport"/></display:setProperty>
        <display:setProperty name="paging.banner.items_name"><fmt:message key="workdayReportList.workdayReports"/></display:setProperty>

        <display:setProperty name="export.excel.filename"><fmt:message key="workdayReportList.title"/>.xls</display:setProperty>
        <display:setProperty name="export.csv.filename"><fmt:message key="workdayReportList.title"/>.csv</display:setProperty>
        <display:setProperty name="export.pdf.filename"><fmt:message key="workdayReportList.title"/>.pdf</display:setProperty>
    </display:table>
</body>