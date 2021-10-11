<!DOCTYPE html>

<head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"></meta>
  <meta http-equiv="refresh" content="10">
  <title>Web Checkers | ${title}</title>
  <link rel="stylesheet" type="text/css" href="/css/style.css">
</head>

<body>
<div class="page">

  <h1>Web Checkers | ${title}</h1>

  <!-- Provide a navigation bar -->
  <#include "nav-bar.ftl" />

  <div class="body">

    <!-- Provide a message to the user, if supplied. -->
    <#include "message.ftl" />

    <!-- TODO: future content on the Home:
            to start games,
            spectating active games,
            or replay archived games
    -->
<#--    <#if currentUser??>-->
<#--      <#list activePlayers as player>-->
<#--        <p>${player.name}</p>-->
<#--      </#list>-->
<#--    <#else>-->
<#--      <p>${activePlayerCount}</p>-->
<#--    </#if>-->

  <div class="activePlayers">
      <#if currentUser??>
      <#if playerList??>
      <h2>Online Player</h2>
      <#list activePlayers as player>
          <#if player.name != currentUser.name>
          <form action="/game" method="GET">
              <input type="submit" name="player" value=${player.name}>
              </#if>
              </#list>
              </#if>
              <#else>
                  ${activePlayerCount}
              </#if>
  </div>


  </div>

</div>
</body>

</html>
