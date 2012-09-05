<html>
  <head>
    <meta name="layout" content="basicrecord" />
    <style>
   


      .ui-timepicker-div .ui-widget-header { margin-bottom: 8px; }
      .ui-timepicker-div dl { text-align: left; }
      .ui-timepicker-div dl dt { height: 25px; }
      .ui-timepicker-div dl dd { margin: -25px 10px 10px 65px; }
      .ui-timepicker-div td { font-size: 90%; }
      .ui-tpicker-grid-label { background: none; border: none; margin: 0; padding: 0; }
    </style>
  <r:require module="jquery-ui"/>
  <g:javascript library="jquery" />
  <jqui:resources themeCss="../css/jquery/jquery-ui-1.8.16.custom.css"/>

  <script type="text/javascript" src="../js/jquery/jquery-ui-i18n.min.js"></script>
  <script type="text/javascript" src="../js/jquery/jquery-ui-timepicker-addon.js"> </script>

  <script type="text/javascript">
      $(document).ready(function()
      {
$.datepicker.setDefaults($.datepicker.regional['${session.'org.springframework.web.servlet.i18n.SessionLocaleResolver.LOCALE'}']);
      
      
       $('#datepicker').datetimepicker({dateFormat: 'dd-mm-yy',
        ampm: true,
        changeYear: true,
                                     
                                     buttonText: 'Calendario',
                                     buttonImage: '../images/datepicker.gif',
                                     maxDate: new Date(),
                                     minDate: new Date(1900, 9, 15),
                                     
                                     showButtonPanel: true,
                                     showOn: 'button'

});
$("#datepicker").attr("readonly",true);

$("#datepicker").click(function(){
  
  $(this).val('');

});
      });
  </script>

</head>
<body>

 <div id="menu1">
      <ul>
        <li>
            <a href="#" class="selected contextoEhr"><g:message code="records.registroActual"/></a>
        </li>
        <li>
        <g:link controller="records" action="list" ><g:message code="records.action.list" /></g:link>
        </li>
        <li>
        <g:link controller="demographic" action="admisionPaciente"><g:message code="demographic.action.admisionPaciente" /></g:link>
        </li>

        <li>
        <g:link controller="reportes"><g:message code="reportes.Reportes"/></g:link>
        </li>
      </ul>
  </div>

  <div id="nivel1">
    <div id="nivel2">

      <div id="contenido">

       
          <h1><g:message code="trauma.create.title" /></h1>

          <g:if test="${flash.message}">
            <div style="color:red;">
              <g:message code="${flash.message}" />
            </div>
          </g:if>

          <g:form action="create" class="form1">
         
<%-- se crea el episodio para una persona seleccionada por admision --%>
            <g:if test="${params.root && params.extension}">
			<%-- i18n --%>
              Se crea episodio para la persona con identificador: ${params.nombreCorto} - ${params.extension}<br/>
              <%--<g:render template="UIDBasedID" collection="${ids}" var="id"/>--%>
			  <input type="hidden" name="root" value="${params.root}" />
              <input type="hidden" name="extension" value="${params.extension}" />
            </g:if>

            <p>
            <label>
            <g:message code="trauma.create.label.fechaIngreso" />
            </label>
            <input name="startDate" type="text" id="datepicker"> 
            
            </p>
            
            <p>
              <label>
            <g:message code="trauma.create.label.observaciones" />
            </label>
            <textarea name="otherContext" id="Observaciones" cols="45" rows="5">${params.otherContext}</textarea>
            </p>
           

           
             <p> <g:submitButton name="doit" class="boton_submit" value="${message(code:'trauma.create.action')}" />
           </p>

          </g:form>
        </div>
      </div>
   
  </div>
</body>
</html>