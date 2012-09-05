<%@ page import="hce.core.datastructure.itemstructure.representation.*" %><%@ page import="com.thoughtworks.xstream.XStream" %>

<%--

in: rmNode (ItemTree)
in: template
--%>

<g:if test="${mode && mode =='edit'}">

<%--
Tengo que ver si para cada item del ItemTree, hay un nodo en el RM.
Si el RM está, muestro ese RM,
Si no está, genero los campos usando AOM. 
--%>
  <%
  // deberia dar un CComplexObject con rmTypeName ITEM_TREE,
  // y deberia tener un solo attribute multiple para su atributo 'items'.
  // Los hijos del CAttribute items son los que recorro para ver si estan en le RM.
  def aomNode = archetype.node(rmNode.path)

  // java.util.Collections$UnmodifiableRandomAccessList 
  // println "CCO Attributes: "+aomNode.attributes.getClass() 
  %>
  <g:each in="${aomNode.attributes[0].children}" var="children">
  <%
  //println "Children: "+children.getClass() + "<br/>"
  def rmItems = rmNode.items.findAll{ it.path == children.path() }
  if (rmItems.size()==0) // No hay items RM para esa path, genero usando el AOM
  {
    //println "No hay items RM para esa path, genero usando el AOM"
    //print "ItemTree AOM"
    print render(template:"../guiGen/templates2/cObject",
               model:[cObject: children,
                      archetype: archetype])
  }
  else // Hay items RM para ese AOM, genero usando el RM
  {
    //println "Hay items RM para ese AOM, genero usando el RM"
    //println "ItemTree RM<br/>"
    rmItems.each { item ->
        def templateName = item.getClassName()
        //print "templateName:"+templateName+"<br/>"
        //print '<textarea style="width: 700px; height: 200px;">' + new XStream().toXML(item) + '</textarea><br/>'
        //print '<textarea style="width: 700px; height: 200px;">' + new XStream().toXML(archetype.node(item.path)) + '</textarea><br/>'
      print render(template:"../guiGen/showTemplates/${templateName}",
             model:[rmNode:item, archetype: archetype, template: template])
    }
  }
  %>
  </g:each>
</g:if>
<g:else>
  <g:each in="${rmNode.getItems()}" var="item"><%-- element o cluster --%>
    
    <%-- esto tira Item_$$_javassist_165, no se porque, asi que lo hago con instanceof --%>
    <%-- <g:set var="templateName" value="${item.getClass().getSimpleName()}" /> --%>
    <g:set var="templateName" value="${item.getClassName()}" />
    
    <%--
    <g:if test="${item instanceof Cluster}">
    CLUSTER
    </g:if>
    <g:else>
    ELEMENT
    </g:else>
    --%>
  
    <g:render template="../guiGen/showTemplates/${templateName}"
              model="[rmNode: item, pathFromParent: rmNode.path+'/items', archetype: archetype, template: template]" />
  </g:each>
</g:else>