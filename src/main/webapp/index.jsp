<%!
int visitorCount = 0;
%>
<%
String name = request.getParameter("name");
visitorCount++;
int a = Integer.parseInt(request.getParameter("a"));
int b = Integer.parseInt(request.getParameter("b"));
int sum = main.MainApp.add(a, b);
%>
Hello <%=name%>!<br/>
You are the <%=""+visitorCount%>th person!
Sum is <%=""+sum%>

<form method='post'>
   Username <input type='text' name='username'>
   Password <input type='text' name='password'>
   <input type='submit' name='submit'>
   
</form>
   