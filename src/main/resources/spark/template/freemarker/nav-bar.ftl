 <div class="navigation">
    <a href="/help" target="_blank">help</a> | 
  <#if currentUser??>
    <form id="signout" action="/signout" method="post">
      <a href="#" onclick="event.preventDefault(); signout.submit();">sign out [${currentUser.name}]</a>
    </form>
  <#else>
    <a href="/signin">sign in</a>
  </#if>
 </div>
