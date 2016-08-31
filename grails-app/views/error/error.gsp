<!DOCTYPE html>
<html>
	<head>
		<title><g:if env="development">Grails Runtime Exception</g:if><g:else>Error - 1</g:else></title>
		<meta name="layout" content="main">
		<g:if env="development"><asset:stylesheet src="errors.css"/></g:if>
	</head>
	<body>
		<g:if env="production">
			<ul class="errors">
				<li>An error has occurred</li>
			</ul>
		</g:if>
		<g:else>
			<g:renderException exception="${exception}" />
		</g:else>
	</body>
</html>
