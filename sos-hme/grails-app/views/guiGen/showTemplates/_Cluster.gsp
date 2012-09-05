<%@ page import="com.thoughtworks.xstream.XStream" %>
<%@ page import="util.UniqueIdIssuer" %>
<%--

in: rmNode (Cluster)
in: archetype

Seudocodigo: (es analogo para todos los objetos RM que tengan un atributo multiple como
              ITEM_TREE, ITEM_LIST, ITEM_TABLE, HISTORY, SECTION y COMPOSITION)

modo = edit?
{
    cc = nodo que restringe al cluster en el AOM
    para cada ci en cc.attributes del AOM,
      verificar que existe un item en cluster.items en el RM
      No hay item en el RM para ese item del AOM?
        Utilizo en AOM para generar los campos de edicion.
      Hay item en el RM para ese AOM?
        Utilizo el item RM para generar los campos de edicion.
}
modo = show? (si el modo no es edit es show)
{
    Utilizo el cluster y sus items en el RM para generar los campos de show,
    // si cae en este caso es que la estructura RM esta completa y no hubo
    // errores de validacion, por eso puedo usar solo el RM para generar la vista.
}

--%>


<div class="CLUSTER" id="${rmNode.archetypeNodeId}" >

  
  <%--
  arhcID: ${rmNode.archetypeDetails.archetypeId},
  nodeID: ${rmNode.archetypeNodeId},
  id: ${rmNode.id}<br/><br/>
  --%>
  
  <g:if test="${mode && mode=='edit'}">
    <span class="label labelCluster">
      ${rmNode.name.value} 
   
    </span>
    <span class="content">
      
	    <g:set var="aomNode" value="${archetype.node(rmNode.path)}" />
	    
	    <%--
	    FIXME: solo con esto podria mostrar el lugar para editar, el problema es que no 
	    muestra valores ingresados ni errores en los nodos del RM creados en el bindeo,
	    pero la logica de GUI ya esta implementada.
	    <g:render template="../guiGen/templates2/cObject"
	              model="[cObject: aomNode,
	                      archetype: archetype,
	                      params: params]" />
	    --%>
	    <%--
	    Tengo que ver si para cara item del cluster definido por el AOM
	    tengo un valor en el RM, si no tengo, para ese nodo uso el AOM
	    para generar. Si no uso el RM con sus valores. Similar a ItemTree.
	    --%>
	    <%--
	    Recorro los CObject de attributes[0].
	    Attributes[0] es la restriccion sobre el atributo cluster.items
	    --%>
           
	    <g:each status="i" in="${aomNode.attributes[0].children}" var="children" >
	      <%
               def aux
               // permite guardar el ultimo valor 'item' de un grupo de
               //rmItems , aux se utiliza para preguntar si ese conjunto es multiple
               
              

	      //println "Children: "+children.getClass() + "<br/>"
	      // El cluster del RM tiene algun item con la path del AOM?
	      def rmItems = rmNode.items.findAll{ it.path == children.path() }
	      if (rmItems.size()==0) // No hay items RM para esa path, genero usando el AOM
	      {
                
	        // USO AOM
	        //print "_Cluster usa AOM"
                
	        print render(template:"../guiGen/templates2/cObject",
	                     model:[cObject: children,
	                            archetype: archetype,
	                            template: template,
	                            pathFromParent: children.path()])
                
	      }
	      else
	      {
                
	        // USO RM
	        //print "_Cluster usa RM<br/>"
	       
                rmItems.each{ item->

                 
	          //print item.path + "<br/>"
	          def templateName = item.getClassName()
	         // print templateName + "<br/>"
                 
	          print render(template: "../guiGen/showTemplates/${templateName}",
	                       model: [rmNode: item,
	                               archetype: archetype,
	                               template: template,
	                               pathFromParent: item.path,
                                       ])
	        aux= item
                }
               
                //print aomNode.attributes[0].children.size()
                
              %>

              <%-- Agregado por Armando--%>
              <g:if test="${mode && mode=='edit' }">
              <%--ES UN CLUSTER, PREGUNTAR SI ES MULTIPLE--%>
              <g:parentElementIsMultiple archetypeId="${archetype.archetypeId.value}" nodePath="${aux.path}">
                <%-- Para que al agregar una entrada no haga scroll al inicio de la pagina --%>
        <g:set var="anchor" value="${UniqueIdIssuer.getId()}" />
        
         


              <div class="multiple">
                
              <a href="#${anchor}" class="clone"><g:message code="view.guiGen.showTemplates.cComplexObject.agregar" /></a>
              </div>
             </g:parentElementIsMultiple>
             <%-- PREGUNTAR SI ES MULTIPLE --%>
             </g:if>
              <%-- FIN Agregado por Armando--%>


              <%
	      }
	      %>

              
	    </g:each>

           
            
            
    </span>

  
  </g:if>

 


  <g:else>
       <span class="label labelCluster">
      ${rmNode.name.value}
    </span>
    <span class="content">
      <g:each in="${rmNode.items}" var="item">
        <%-- element o cluster --%>
        <%-- esto tira Item_$$_javassist_165, no se porque, asi que lo hago con instanceof --%>
        <%-- <g:set var="templateName" value="${item.getClass().getSimpleName()}" /> --%>
        <g:set var="templateName" value="${item.getClassName()}" />
        <%-- Item: ${templateName}<br/> --%>
        <g:render template="../guiGen/showTemplates/${templateName}"
                  model="[rmNode: item, archetype: archetype, template: template, pathFromParent: item.path]" />
      </g:each>
    </span>
  </g:else>
  
</div>

