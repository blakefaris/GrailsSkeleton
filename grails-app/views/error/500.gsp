<!DOCTYPE html>
<html>
<head>
    <title><g:message code="site.name"/> - Internal Server Error</title>
    <meta name="layout" content="main">
</head>
<body>
<div id="message-panel" class="row">
    <div class="col-lg-10 col-lg-offset-1">
        <div class="panel panel-danger">
            <div class="panel-heading">
                <h3 class="panel-title text-center">Sorry, we had a problem loading this page.</h3>
            </div>

            <div class="panel-body">
                <p class="text-center margin-bottom">We have been alerted to this error and will look into the problem. For now, try refreshing the page.</p>
            </div>
        </div>
    </div>
</div>
<g:if env="production"><!-- Stack trace suppressed in production environment --></g:if>
<g:else>
    <div id="error-body" class="row" style="display: none;">
        <div class="col-xs-12">
            <h1>Stacktrace</h1>
            <pre>
                <g:renderException exception="${exception}"/>
            </pre>
        </div>
    </div>
    <script type="text/javascript">
        $('#message-panel').dblclick(function () {
            $('#error-body').slideDown();
        });
    </script>
</g:else>

</body>
</html>
