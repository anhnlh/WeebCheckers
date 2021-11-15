 <div class="navigation">
    <a href="/help" target="_blank">help</a> | 
  <#if currentUser??>
    <a href="/">my home</a> |
    <a href='/' onclick="location.reload()">Reload</a> | 
<#--    <a href='/' onclick=" document.body.style.backgroundColor = 'red' ; ">Play WeebCheckers</a> | -->
    <form id="signout" action="/signout" method="post">
      <a href="#" onclick="event.preventDefault(); signout.submit();">sign out [${currentUser.name}]</a>
    </form>
  <#else>
    <a href="/signin">sign in</a>
  </#if>
 </div>
