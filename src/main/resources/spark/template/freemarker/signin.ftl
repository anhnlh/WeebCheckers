<head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"></meta>
  
  <title>Web Checkers | ${title}</title>
  
  <link rel="stylesheet" type="text/css" href="/css/style.css">

</head>

<body>
  <div class="page">
    <h1>Web Checkers | ${title}</h1>
      <div class="body">
        <form action="/signin" method="POST">
          <#include "message.ftl">
          <input name="userID" />
          <br><br/>
          <button type="submit">Sign In</button>
        </form>
      </div>
  </div>
</body>

