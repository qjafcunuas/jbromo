<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<html>
<head>
<title>QuoteStream</title>
<meta http-equiv="REFRESH"
	content="0;url=<%=request.getContextPath()%>/error/<%=request.getParameter("page") %>">
</head>
<body>
	<!-- 
IE overrides several HTTP error status pages but it has a size threshold. Basically if the error page send by the server has a large enough body then IE decides it's meaningful and displays it.
Usually to be safe you should make error pages that are larger then 512 bytes. The threshold varies per HTTP status code. You can look at what your thresholds are currently set to. 
In IE 5 and greater the settings are stored in the registry under[HKEY_LOCAL_MACHINE\SOFTWARE\Microsoft\Internet Explorer\Main\ErrorThresholds]
-->
</body>
</html>

