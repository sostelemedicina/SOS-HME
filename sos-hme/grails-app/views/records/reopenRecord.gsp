<?xml version="1.0" encoding="UTF-8" ?>
<html>
  <head>
    <meta name="layout" content="ehr" />
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <title><g:message code="trauma.reopenRecord.title" /></title>
    <g:javascript library="prototype/prototype" />
<g:javascript library="jquery" />
<script type="text/javascript" src="${createLinkTo(dir:'js/', file:'jquery.simplemodal.js')}"></script>
<script type="text/javascript" src="${createLinkTo(dir:'js/', file:'basic.js')}"></script>
	
    <g:javascript>
      <!--
        Event.observe(window, 'load', function(event) {

          // Focus en input nombre de usuario
          $('user').focus();

        });
      -->
    </g:javascript>
    <style>
      table {
        border: 0px;
      }
      th {
        background: none;
        text-align: right;
        vertical-align: middle;
        padding-right: 10px;
        width: 80px;
      }
	  #nivel3 h2{
	  
	  }
      .error {
        /* TODO: meter icono de error ! */
        border: 1px solid #f00;
        background-color: #f99;
        padding: 2px;
		height: 30px;
        margin-bottom: 3px;
		margin-top: 0px;
      }
      .error ul {
        list-style:none;
        margin:0;
        padding:0;
      }
      .message {
        /* TODO: meter icono de error ! */
        border: 1px solid #0f0;
        background-color: #9f9;
        padding: 2px;
        margin-bottom: 3px;
      }
      .message ul {
        list-style:none;
        margin:0;
        padding:0;
      }
      table #reopen_table {
        width: 290px;
      }
      #form1 input[type=submit] {
        position: relative;
        float: right;
      }
    </style>
  </head>
  <body>
    <div id="nivel3">
    <h2><g:message code="trauma.reopenRecord.title" /></h2>

    <g:if test="${flash.error}">
      <div id="logine"><div class="error">
        <ul><li><g:message code="${flash.error}" /></li></ul></div>
	  </div>
    </g:if>
    <g:if test="${flash.message}">
        <div class="message"><ul><li><g:message code="${flash.message}" /></li></ul></div>
    </g:if>
    <g:if test="${!patient && !flash.error}">
      <div class="message"><ul><li><g:message code="trauma.sign.noPatientSelected" /></li></ul></div>
    </g:if>

    </div>
  </body>
</html>