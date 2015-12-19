<%@ include file="/common/taglibs.jsp"%>

<head>
    <title><fmt:message key="workdayReportDetail.title"/></title>
    <meta name="menu" content="WorkdayReportMenu"/>
</head>
<c:set var="delObject" scope="request"><fmt:message key="workdayReportList.workdayReport"/></c:set>
<script type="text/javascript">var msgDelConfirm =
   "<fmt:message key="delete.confirm"><fmt:param value="${delObject}"/></fmt:message>";
</script>

<div class="col-sm-3">
    <h2><fmt:message key="workdayReportDetail.heading"/></h2>
    <fmt:message key="workdayReportDetail.message"/>
</div>

<div class="col-sm-6">
    <s:form id="workdayReportForm" action="saveWorkdayReport" method="post" validate="true" cssClass="well">
        <s:hidden key="workdayReport.id"/>

        <s:textarea rows="6" cssClass="form-control" key="workdayReport.todayReport" required="false" maxlength="255" />
        <s:textarea rows="6" cssClass="form-control" key="workdayReport.tomrrowPlan" required="false" maxlength="255" />
        <s:hidden cssClass="form-control" key="workdayReport.userId" maxlength="255" />



        <c:choose>
            <c:when test="${not empty workdayReport.id}">
                <s:textfield disabled="true" cssClass="form-control date" key="workdayReport.lastUpdateTime" required="false" maxlength="19" size="11" title="date" datepicker="true">
                    <s:param name="value"><s:date name="workdayReport.lastUpdateTime" format="yyyy-MM-dd HH:mm:ss"/></s:param>
                </s:textfield>
            </c:when>
            <c:otherwise>
                <s:hidden cssClass="form-control date" key="workdayReport.lastUpdateTime" required="false" maxlength="19" size="11" title="date" datepicker="true">
                    <s:param name="value"><s:date name="workdayReport.lastUpdateTime" format="yyyy-MM-dd"/></s:param>
                </s:hidden>
            </c:otherwise>
        </c:choose>

        <c:choose>
            <c:when test="${not empty workdayReport.id}">
                <s:textfield disabled="true" cssClass="form-control date" key="workdayReport.writeTime" required="false" maxlength="19" size="11" title="date" datepicker="true">
                    <s:param name="value"><s:date name="workdayReport.writeTime" format="yyyy-MM-dd HH:mm:ss"/></s:param>
                </s:textfield>
            </c:when>
            <c:otherwise>
                <s:hidden cssClass="form-control date" key="workdayReport.writeTime" required="false" maxlength="19" size="11" title="date" datepicker="true">
                    <s:param name="value"><s:date name="workdayReport.writeTime" format="yyyy-MM-dd"/></s:param>
                </s:hidden>
            </c:otherwise>
        </c:choose>

        <div class="form-group">
            <s:submit type="button" id="save" cssClass="btn btn-primary" method="save" key="button.save" theme="simple">
                <i class="icon-ok icon-white"></i> <fmt:message key="button.save"/>
            </s:submit>
            <c:if test="${not empty workdayReport.id}">
                <c:if test="${userIsAdmin}">
                    <s:submit type="button" id="delete" cssClass="btn btn-danger" method="delete" key="button.delete"
                        onclick="return confirmMessage(msgDelConfirm)" theme="simple">
                        <i class="icon-trash icon-white"></i> <fmt:message key="button.delete"/>
                    </s:submit>
                </c:if>
            </c:if>
            <a href="${ctx}/showReports" class="btn btn-default">
                <i class="icon-remove"></i> <fmt:message key="button.cancel"/></a>
        </div>
    </s:form>
</div>

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
