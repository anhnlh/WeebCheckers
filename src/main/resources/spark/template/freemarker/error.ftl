<#if error??>
  <div id="error" class="${error.type}">${error.text}</div>
<#else>
  <div id="error" class="ERROR" style="display:none">
    <!-- keep here for client-side messages -->
  </div>
</#if>
