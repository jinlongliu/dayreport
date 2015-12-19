<%@ include file="/common/taglibs.jsp"%>

<head>
    <title><fmt:message key="workdayReportList.title"/></title>
    <meta name="menu" content="WorkdayReportMenu"/>
</head>
<body>
    <h2><fmt:message key="workdayReportList.heading"/></h2>

    <form method="get" action="${ctx}/showReports" id="searchForm" class="form-inline">
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
    <form method="get" action="${ctx}/showReports" id="searchForm" class="form-inline">
    <div>
        <s:select name="someoneUserId" list="allUsers" cssClass="form-control" listKey="id" listValue="firstName+lastName"
                  headerKey="" headerValue="%{queryLabel}"/>
        <s:textfield cssClass="form-control date" name="queryDate" namemaxlength="19" size="11" title="date" datepicker="true">
            <s:param name="value"><s:date name="queryDate" format="yyyy-MM-dd"/></s:param>
        </s:textfield>
        <button id="button.search" class="btn btn-primary btn-sm" type="submit">
            <i class="icon-search"></i> <fmt:message key="button.filter"/>
        </button>
    </div>
    </form>
    <display:table name="reportShows" class="table table-condensed table-striped table-hover" requestURI="" id="reportShow" export="true" pagesize="25">
        <display:column property="workdayReport.id" sortable="true" href="editWorkdayReport" media="html"
            paramId="id" paramProperty="workdayReport.id" titleKey="workdayReport.id"/>
        <display:column property="workdayReport.id" media="csv excel xml pdf" titleKey="workdayReport.id"/>
        <display:column property="reportWriteName" sortable="true" titleKey="workdayReport.writeName"/>
        <display:column property="workdayReport.todayReport" sortable="true" titleKey="workdayReport.todayReport"/>
        <display:column property="workdayReport.tomrrowPlan" sortable="true" titleKey="workdayReport.tomrrowPlan"/>
        <%--<display:column property="workdayReport.userId" sortable="true" titleKey="workdayReport.userId"/>--%>

        <display:column property="workdayReport.writeTime" sortable="true" titleKey="workdayReport.writeTime"/>
        <display:column property="workdayReport.lastUpdateTime" sortable="true" titleKey="workdayReport.lastUpdateTime"/>

        <display:setProperty name="paging.banner.item_name"><fmt:message key="workdayReportList.workdayReport"/></display:setProperty>
        <display:setProperty name="paging.banner.items_name"><fmt:message key="workdayReportList.workdayReports"/></display:setProperty>

        <display:setProperty name="export.excel.filename"><fmt:message key="workdayReportList.title"/>.xls</display:setProperty>
        <display:setProperty name="export.csv.filename"><fmt:message key="workdayReportList.title"/>.csv</display:setProperty>
        <display:setProperty name="export.pdf.filename"><fmt:message key="workdayReportList.title"/>.pdf</display:setProperty>
    </display:table>
    <link rel="stylesheet" type="text/css" media="all" href="<c:url value='/webjars/bootstrap-datepicker/1.3.1/css/datepicker.css'/>" />
    <script type="text/javascript" src="<c:url value='/webjars/jquery/1.11.1/jquery.min.js'/>"></script>
    <script type="text/javascript" src="<c:url value='/webjars/bootstrap-datepicker/1.3.1/js/bootstrap-datepicker.js'/>"></script>
    <c:if test="${pageContext.request.locale.language != 'en'}">
        <script type="text/javascript" src="<c:url value='/webjars/bootstrap-datepicker/1.3.1/js/locales/bootstrap-datepicker.zh-CN.js'/>"></script>
    </c:if>
    <script type="text/javascript">
        $(document).ready(function() {
            $('.date').datepicker({format: "<fmt:message key='calendar.format'/>", weekStart: "<fmt:message key='calendar.weekstart'/>", language: 'zh-CN'});
        });
    </script>
</body>