<?xml version="1.0" encoding="ISO-8859-1" ?>
<html>
  <head>
    <g:javascript src="jquery-1.3.2.min.js" />
    <g:javascript>
      $(document).ready(function() {
         $("a.clone").click(function() {
            //alert("Hello world!");
            //alert( $(this).parent() );
            
            //alert( $(this).parent().parent().children()[0] );
            
            //$("body").append( $(this).parent().parent().children()[0].clone() );
            $(this).parent().parent().prepend(  $(this).parent().parent().clone() );
            
         });
      });
    </g:javascript>
   
    <style>
    body {
      font-family: verdana, tahoma;
      font-size: 12px;
    }
    .ccomplex_object_title {
      font-weight: bold;
      font-size: 14px;
    }
    .cattributes {
      border: 1px solid #336699;
      padding: 5px;
      background-color: #ffdddd;
    }
    .cobjects {
      border: 1px solid #339966;
      padding: 5px;
      background-color: #ffffdd;
    }
    .cobject {
      background-color: #ddddff;
      width: 100%;
    }
    .active {
      font-weight: bold;
    }
    .multiple {
      text-align: right;
    }
    .slot {
      border: 2px solid red;
      padding: 2px;
    }
    </style>
  </head>
  <body>
    <h1>Generar</h1>
    
    <ul class="userBar">
      <li ${(session.locale.getLanguage()=='en')?'class="active"':''}>
        <%-- <a href="changeLocale?lang=en&backAction=login"><g:message code="common.lang.en" /></a> --%>
        <a href="?sessionLang=en&archetypeId=${params.archetypeId}"><g:message code="common.lang.en" /></a>
      </li>
      <li ${(session.locale.getLanguage()=='es')?'class="active"':''}>
        <a href="?sessionLang=es&archetypeId=${params.archetypeId}"><g:message code="common.lang.es" /></a>
      </li>
    </ul>
    
    <g:if test="${false}">
      <pre>
        ${archetype}
      </pre>
    </g:if>
    
    <%-- ${archetypeService} --%>
 
    <g:form action="save" class="cobjects">
    
      <input type="hidden" name="archetypeId" value="${params.archetypeId}" />
    
      <g:render template="templates/cComplexObject"
                model="[cComplexObject: archetype.definition, archetype:archetype, archetypeService:archetypeService]" />
    
      <g:submitButton name="doit" value="Guardar" />
    
    </g:form>

  </body>
</html>