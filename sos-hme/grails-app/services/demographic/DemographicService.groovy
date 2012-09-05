/**
 * 
 */
package demographic

import demographic.DemographicAccess
import demographic.LocalDemographicAccess
import sos.EnfermedadesNotificables


import hce.core.support.identification.UIDBasedID

import demographic.identity.PersonName
import demographic.identity.*
import demographic.party.Person
import demographic.role.Role
import tablasMaestras.TipoIdentificador

// Configuracion de consulta local o remota
import org.codehaus.groovy.grails.commons.ApplicationHolder

import java.util.*;



//creacion de archivos xml
import groovy.xml.MarkupBuilder
import org.custommonkey.xmlunit.*
import java.lang.Object.*
import groovy.xml.XmlUtil
import groovy.util.XmlSlurper
import javax.xml.parsers.*
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NamedNodeMap;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;


/**
 * @author Pablo Pazos Gutierrez (pablo.swp@gmail.com)
 *
 */
class DemographicService {

    DemographicAccess demographicAccess
    def grailsApplication
    //int queryCount = 0
    
    // FIXME: ojo, capaz necesita instanciarse distinto por GRAILS, los servicesson singletons...
    def DemographicService()
    {
        if (ApplicationHolder.application.config.hce.patient_administration.serviceType.local)
        {
            println "DemographicService - PA LOCAL"
            // TODO: instanciar demographicAccess segun configuracion
            demographicAccess = new LocalDemographicAccess()
        }
        else
        {
            println "DemographicService - PA REMOTO"
            demographicAccess = new PixPdqDemographicAccess()
        }
    }
    
    /**
     * True si la persona tiene 1 id, primer nombre, primer apellido, sexo y fecha nacimiento.
     * @param p
     * @return
     */
    public boolean personHasAllData( Person p )
    {
        //println "Person: "+ p
        //println "Identities: " + p.identities
        
        if (!p.identities || p.identities.size()==0) return false
        
        def pn = p.identities.find{ it.purpose == 'PersonName' }
        if (!pn) return false
        
        return ( p.sexo && p.fechaNacimiento && pn.primerNombre && pn.primerApellido && p.ids.size()>0 )
    }
    
    /**
     * Encuentra otros identificadores de la persona, a partir de un identificador conocido.
     * Se corresponde a PIX ids query con un solo identificador como criterio de busqueda.
     */
    public List<UIDBasedID> findIdsById( UIDBasedID id )
    {
        //this.queryCount++
        //println queryCount
        return demographicAccess.findIdsById(id)
    }
    
    /**
     * Encuentra otros identificadores de la persona, a partir de los identificadores conocidos.
     * Se corresponde a PIX ids query con muchos identificadores como criterio de busqueda.
     * El resultado debe ser el mismo que si se invoca findIdsById para cada id de ids y luego se hace merge de los resultados.
     */
    public List<UIDBasedID> findIdsByIds( List<UIDBasedID> ids )
    {
        return demographicAccess.findIdsByIds(ids)
    }
    
    /**
     * Busca por extension, Pacientes. 
	 * Cambiar nombre a: findPersonPatientById
     */
    public List<Person> findPersonById( UIDBasedID id )
    {
        //this.queryCount++
        //println queryCount
			
			
			//este query es necesario para filtrar la busqueda de personas por  un id asociado
			//y por el tipo de identidad (patient) que esta tenga.
			def existPerson = Person.withCriteria{
				ids{
					eq("value", id.value)
				}
				identities{
					eq("purpose", "PersonNamePatient")
				}
			
			}
        //return  demographicAccess.findPersonById(id)
		
		return existPerson
    }
	
    /**
     * Busca por extension, Usuarios.
     */
    public List<Person> findPersonUserById( UIDBasedID id )
    {

			def existPerson = Person.withCriteria{
				ids{
					eq("value", id.value)
				}
				identities{
					eq("purpose", "PersonNameUser")
				}
			
			}
		
		return existPerson
    }    
    public List<Person> findByPersonData( PersonName n, Date bithdate, String sex )
    {
        //this.queryCount++
        //println queryCount
        return demographicAccess.findByPersonData(n, bithdate, sex)
    }
    
    public List<Person> findByPersonDataAndId( PersonName n, Date bithdate, String sex, UIDBasedID id )
    {
        return demographicAccess.findByPersonDataAndId(n, bithdate, sex, id)
    }
    
    public List<Person> findByPersonDataAndIds( PersonName n, Date bithdate, String sex, List<UIDBasedID> ids )
    {
        return demographicAccess.findByPersonDataAndIds(n, bithdate, sex, ids)
    }
    
    public List<Person> findByPersonDataAndRole( PersonName n, Date bithdate, String sex, Role role )
    {
        return demographicAccess.findByPersonDataAndRole(n, bithdate, sex, role)
    }
	
    /*
     *@author Angel Rodriguez Leon
     *
     *Busca en la BD los valores de nombres de usuario identificados por el id
	 *y los trae para la creacion de un paciente a partir de estos datos.
     * */  
    public List<Person> findPatientById(UIDBasedID id){
		def candidatosPacientes = Person.withCriteria{

				ids{
					eq("value", id.value)
				}
				identities{

					eq("purpose", "PersonNamePatient")
				}
			
			}
		return candidatosPacientes
	
	}
	
    public List<Person> findUserById(UIDBasedID id){
		def candidatosUsuarios = Person.withCriteria{

				ids{
					eq("value", id.value)
				}
				identities{

					eq("purpose", "PersonNameUser")
				}
			
			}
		return candidatosUsuarios
	
	}
    public List<Person> findByPersonDataAndIdAndRole( PersonName n, Date bithdate, String sex, UIDBasedID id, String roleType )
    {
        return demographicAccess.findByPersonDataAndIdAndRole(n, bithdate, sex, id, roleType)
		/*
				if(id==null){
					id.value="1"
				}

				println "primer nombre: "+n.primerNombre
				if(n.primerNombre == null){
					n.primerNombre = "22"}
				println "segundo nombre: "+n.segundoNombre
				if(n.segundoNombre == null){
					n.segundoNombre="22"}
				println "primer Apellido: "+n.primerApellido
				if(n.primerApellido == null){
					n.primerApellido="22"}
				println "segundo apellido: "+n.segundoApellido
				if(n.segundoApellido == null){
					n.segundoApellido="22"}
				
				println "id: "+id+" date: "+bithdate
				println "sexo: "+sex 
				def candidatosPacientes = Person.withCriteria{
					//eq("sexo",sex)
					and{
						or{
						
							ids{
								eq("value", id.value)
							}
							
							//or{
								identities{
									eq("primerNombre", n.primerNombre)
									
								}
								identities{
									eq("segundoNombre", n.segundoNombre)
									
								}
								identities{
									eq("primerApellido", n.primerApellido)
									
								}
								identities{
									eq("segundoApellido", n.segundoApellido)
									
								}
							//}

						}
						identities{
							eq("purpose", "PersonNamePatient")
						}
						

					}
				}
				println "candidatos: "+candidatosPacientes
				
		return candidatosPacientes*/
	}
    
    public List<Person> findByPersonDataAndIdsAndRole( PersonName n, Date bithdate, String sex, List<UIDBasedID> ids, Role role )
    {
        return demographicAccess.findByPersonDataAndIdsAndRole(n, bithdate, sex, ids, role)
    }
    
    /*
     *@author Juan Carlos Escalante
     *regresa el nombre en los lugares recursivos a partir del id del ultimo hijo 
     *dicho id esta identificado con el nombre Lugar y Direccion de la clase PersonNamePatient*/
    
    public String findFullAddress(Integer lugar){
        def parroquia = null
        def municipio = null
        def estado = null
        def pais = null
        def fullAddress = null
        
        if(lugar){
            def sitio=Lugar.get(lugar)
            //println(sitio.tipolugar)
            if(sitio.tipolugar=="Municipio"){
                municipio =  sitio.nombre
                estado = Lugar.get(sitio.padre.id)
                fullAddress = "municipio "+ municipio + ", estado "+estado.nombre
            }
            if(sitio.tipolugar=="Parroquia"){
                parroquia = sitio.nombre
                municipio = Lugar.get(sitio.padre.id)
                estado = Lugar.get(municipio.padre.id)
                fullAddress = "parroquia "+ parroquia + ", municipio "+ municipio.nombre + ", estado "+estado.nombre
            }
        }
        return fullAddress
    }
    
    public String tokenDireccion(Integer lugar){
        def parroquia = null
        def municipio = null
        def estado = null
        def fullAddress = null
        if(lugar){
           def sitio=Lugar.get(lugar) 
           if(sitio.tipolugar=="Municipio"){
                municipio =  sitio.nombre
                estado = Lugar.get(sitio.padre.id)
                fullAddress = municipio + "#"+estado.nombre
            }
            if(sitio.tipolugar=="Parroquia"){
                parroquia = sitio.nombre
                municipio = Lugar.get(sitio.padre.id)
                estado = Lugar.get(municipio.padre.id)
                fullAddress = parroquia + "#"+ municipio.nombre + "#"+estado.nombre
            }
        }
      return fullAddress  
    }
    
    public String getOcupacion(Integer ocupacion){
        def ocupa = Ocupacion.get(ocupacion)
        return ocupa.nombre
    }
    
    
    public boolean createXML (String tipo){
        Date hoy = new Date()
        def xml = new groovy.xml.StreamingMarkupBuilder().bind(){
            mkp.xmlDeclaration()
            pacientes(){
                fechareporte(hoy.format("dd/MM/yyyy"))
                //nombre()
                //apellido()
            }
            
        }
        def output = XmlUtil.serialize(xml)
        
        def stringWriter = new StringWriter()
        def node = new XmlParser().parseText(output.toString());
        new XmlNodePrinter(new PrintWriter(stringWriter)).print(node)
        
        def writer = new File(ApplicationHolder.application.parentContext.servletContext.getRealPath("/data/reports/source/"+tipo+".xml"))
        //writer.write(output)
        writer.write(stringWriter.toString())
        
        return true
        
    }
    public boolean crearXmlEpi10(String docxml, String fecha, String cedula, String nombre, String direccion, String ocupacion, String edad, String sexo, String[] diagnosticos){
        def ruta = ApplicationHolder.application.parentContext.servletContext.getRealPath("/data/reports/source/"+docxml+".xml")
        
        // bloque java
        DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
        Document doc = docBuilder.parse(ruta);
        
        Node pacientes = doc.getFirstChild();
        //NamedNodeMap pacientesAttributes = pacientes.getAttributes();
        Node paciente = doc.createElement("paciente");
        pacientes.appendChild(paciente);
        
        Node fechaxml = doc.createElement("fecha")
        fechaxml.setTextContent(fecha)
        paciente.appendChild(fechaxml)
        
        Node cedulaxml = doc.createElement("cedula")
        cedulaxml.setTextContent(cedula)
        paciente.appendChild(cedulaxml)
        
        
        Node nombrexml = doc.createElement("nombre")
        nombrexml.setTextContent(nombre)
        paciente.appendChild(nombrexml)
        
        Node direccionxml = doc.createElement("direccion")
        direccionxml.setTextContent(direccion)
        paciente.appendChild(direccionxml)
        
        Node ocupacionxml = doc.createElement("ocupacion")
        ocupacionxml.setTextContent(ocupacion)
        paciente.appendChild(ocupacionxml)
        
        Node edadxml = doc.createElement("edad")
        edadxml.setTextContent(edad)
        paciente.appendChild(edadxml)
        
        Node sexoxml = doc.createElement("sexo")
        sexoxml.setTextContent(sexo)
        paciente.appendChild(sexoxml)
        
        Node diagnosticosxml = doc.createElement("diagnosticos")
        paciente.appendChild(diagnosticosxml)
        
        def i=diagnosticos.size()
        def j=0
        while(j<=i-1){
            Node diagnosticoxml = doc.createElement("diagnostico")
            diagnosticoxml.setTextContent(diagnosticos[j])
            diagnosticosxml.appendChild(diagnosticoxml)
            j++
        }
        
        def output = XmlUtil.serialize(pacientes)
        /*
        def stringWriter = new StringWriter()
        def node = new XmlParser().parseText(output.toString());
        new XmlNodePrinter(new PrintWriter(stringWriter)).print(node)
        */
        def writer = new File(ApplicationHolder.application.parentContext.servletContext.getRealPath("/data/reports/source/"+docxml+".xml"))
        //writer.write(stringWriter.toString())
        writer.write(output)
        
        return true
    }
    
    public boolean crearXmlEPI10Gen(String docxml, String cedula, String nombre, String fechanacimiento, String direccion, String sexo,String etnia, String niveleducativo,String edad, String[] diagnosticos){
        def ruta = ApplicationHolder.application.parentContext.servletContext.getRealPath("/data/reports/source/"+docxml+".xml")
        
        
        
        Date hoy = new Date()
        println("hoy:->"+hoy.format("dd/MM/yyyy"))
        
        
        // bloque java
        DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
        Document doc = docBuilder.parse(ruta);
        
        Node pacientes = doc.getFirstChild();
        Node paciente = doc.createElement("paciente");
        pacientes.appendChild(paciente);
        
        Node cedulaxml = doc.createElement("cedula");
        cedulaxml.setTextContent(cedula)
        paciente.appendChild(cedulaxml)
        
        Node nombrexml = doc.createElement("nombre");
        nombrexml.setTextContent(nombre)
        paciente.appendChild(nombrexml)
        
        Node fechanacexml = doc.createElement("fechanacimiento");
        fechanacexml.setTextContent(fechanacimiento)
        paciente.appendChild(fechanacexml)
        
        Node direccionxml = doc.createElement("direccion");
        direccionxml.setTextContent(direccion)
        paciente.appendChild(direccionxml)
        
        Node sexoxml = doc.createElement("sexo");
        sexoxml.setTextContent(sexo)
        paciente.appendChild(sexoxml)
        
        Node etniaxml = doc.createElement("etnia")
        etniaxml.setTextContent(etnia)
        paciente.appendChild(etniaxml)
        
        Node educativoxml = doc.createElement("niveleducativo")
        educativoxml.setTextContent(niveleducativo)
        paciente.appendChild(educativoxml)
        
        Node edadxml = doc.createElement("edad");
        edadxml.setTextContent(edad)
        paciente.appendChild(edadxml)
        
        Node diagnosticosxml = doc.createElement("diagnosticos")
        paciente.appendChild(diagnosticosxml)
        
        def i=diagnosticos.size()
        def j=0
        while(j<=i-1){
            Node diagnosticoxml = doc.createElement("diagnostico")
            diagnosticoxml.setTextContent(diagnosticos[j])
            diagnosticosxml.appendChild(diagnosticoxml)
            j++
        }
        
        def output = XmlUtil.serialize(pacientes)
        def writer = new File(ApplicationHolder.application.parentContext.servletContext.getRealPath("/data/reports/source/"+docxml+".xml"))
        writer.write(output)
        return true
    }
    
    public boolean crearXmlEPI13Morbilidad(String docxml, String cedula, String nombre, String fechaNacimiento, 
                                           String direccion, String parroquia, String municipio, String estado,
                                           String fechaRegistro, String sexo, String[] diagnosticos, String semanaInicio, String semanaFin)
    {
        def ruta = ApplicationHolder.application.parentContext.servletContext.getRealPath("/data/reports/source/"+docxml+".xml")
        DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
        Document doc = docBuilder.parse(ruta);
        
        
        StringTokenizer st = new StringTokenizer(fechaRegistro,"-",false)
        def anioRegistro = st.nextToken().toString()
        def mesRegistro = st.nextToken().toString()
        def diaRegistroBasic = st.nextToken().toString()
        StringTokenizer stk = new StringTokenizer(diaRegistroBasic)
        def diaRegistro = stk.nextToken()
        String fechaRegistroFormato = diaRegistro+"/"+mesRegistro+"/"+anioRegistro
        
        Node pacientes = doc.getFirstChild();
        Node paciente = doc.createElement("paciente");
        pacientes.appendChild(paciente);
        
        Node fechaxml = doc.createElement("fecha");
        fechaxml.setTextContent(fechaRegistroFormato)
        paciente.appendChild(fechaxml)
        
        Node nombrexml = doc.createElement("nombre");
        nombrexml.setTextContent(nombre)
        paciente.appendChild(nombrexml)
        
        Node cedulaxml = doc.createElement("cedula");
        cedulaxml.setTextContent(cedula)
        paciente.appendChild(cedulaxml)
        
        def sexoSigla
        if(sexo=="Masculino"){
            sexoSigla="M"
        }else{
            sexoSigla="F"
        }
        Node sexoxml = doc.createElement("genero");
        sexoxml.setTextContent(sexoSigla)
        paciente.appendChild(sexoxml)
        
        Node fechaNacimientoxml = doc.createElement("fechanacimiento");
        fechaNacimientoxml.setTextContent(fechaNacimiento)
        paciente.appendChild(fechaNacimientoxml)
        
        Node direccionxml = doc.createElement("direccionresidencia");
        direccionxml.setTextContent(direccion)
        paciente.appendChild(direccionxml)
        
        Node estadoxml = doc.createElement("entidad");
        estadoxml.setTextContent(estado)
        paciente.appendChild(estadoxml)
        
        Node municipioxml = doc.createElement("municipio");
        municipioxml.setTextContent(municipio)
        paciente.appendChild(municipioxml)
        
        Node parroquiaxml = doc.createElement("parroquia");
        parroquiaxml.setTextContent(parroquia)
        paciente.appendChild(parroquiaxml)
        
        Node diagnosticosxml = doc.createElement("diagnosticos")
        paciente.appendChild(diagnosticosxml)
        
        def i=diagnosticos.size()
        def j=0
        while(j<=i-1){
            Node diagnosticoxml = doc.createElement("diagnostico")
            diagnosticoxml.setTextContent(diagnosticos[j])
            diagnosticosxml.appendChild(diagnosticoxml)
            j++
        }
        
        StringTokenizer stsi = new StringTokenizer(semanaInicio,"-",false)
        def semanaInicioDia = stsi.nextToken()
        def semanaInicioMes = stsi.nextToken()
        
        StringTokenizer stsf = new StringTokenizer(semanaFin,"-",false)
        def semanaFinDia = stsf.nextToken()
        def semanaFinMes = stsf.nextToken()
        
        String semanaRegistro = "Del "+semanaInicioDia+"/"+semanaInicioMes+" al "+semanaFinDia+"/"+semanaFinMes
        Node semanaxml = doc.createElement("semana");
        semanaxml.setTextContent(semanaRegistro)
        paciente.appendChild(semanaxml)
        
        Node anioxml = doc.createElement("anioregistro");
        anioxml.setTextContent(stsi.nextToken())
        paciente.appendChild(anioxml)
        
        
        def output = XmlUtil.serialize(pacientes)
        def writer = new File(ApplicationHolder.application.parentContext.servletContext.getRealPath("/data/reports/source/"+docxml+".xml"))
        writer.write(output)
        return true
                                           
    }
        
    public boolean crearXmlEPI12Morbilidad(String docxml, String codigo, String subgrupo, String edad, String sexo){
        
        def ruta = ApplicationHolder.application.parentContext.servletContext.getRealPath("/data/reports/source/"+docxml+".xml")
        
       
        DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance()
        DocumentBuilder docBuilder = docFactory.newDocumentBuilder()
        Document doc = docBuilder.parse(ruta)
        Node pacientes = doc.getFirstChild()
        
        def nodoUpdate = nombreNodo(edad, sexo, codigo, subgrupo)
        println("nodoUpdate:->"+nodoUpdate)
        
        if(nodoUpdate!="vacio"){
            Node nodoCambio = doc.getElementsByTagName(nodoUpdate).item(0)
            def valorUpdate = Integer.parseInt(nodoCambio.getFirstChild().getTextContent())
            valorUpdate++
            nodoCambio.getFirstChild().setTextContent(Integer.toString(valorUpdate))
            
            def output = XmlUtil.serialize(pacientes)
            def writer = new File(ApplicationHolder.application.parentContext.servletContext.getRealPath("/data/reports/source/"+docxml+".xml"))
            writer.write(output)
            return true
        }else{
            return false
        }
        
    }
    
    public String nombreNodo(String edadF, String sexoF, String codigoF, String subgrupoF){
        def listaSubgrupo = EnfermedadesNotificables.getCodigos()
        def listaCodigo = EnfermedadesNotificables.getCodigosIden()
        def nombre = "vacio"
        def nombreGrupo = "vacio"
        def edadGrupo = "vacio"
        if(subgrupoF in listaSubgrupo.codigo){
            for (int i=0;i<listaSubgrupo.codigo.size();i++){
                if(listaSubgrupo.codigo[i]==subgrupoF){
                    println("encontrado:->"+listaSubgrupo.codigo[i])
                    switch (listaSubgrupo.codigo[i]){
                        case "A00":
                            nombreGrupo = "A00";
                            break;
                        case "A08": case "A09": 
                            nombreGrupo = "A08-A09";
                            break;
                        case "A06":
                            nombreGrupo = "A06";
                            break;
                        case "B15":
                            nombreGrupo = "B15";
                            break;
                        case "A15": case "A16": case "A17": case "A18": case "A19":
                            nombreGrupo = "A15-A19";
                            break;
                        case "J10": case "J11":
                            nombreGrupo = "J10-J11";
                            break;
                        case "A50":
                            nombreGrupo = "A50";
                            break;
                        case "Z21":
                            nombreGrupo = "Z21";
                            break;    
                        case "B20": case "B21": case "B22": case "B23": case "B24":
                            nombreGrupo = "B20-B24";
                            break;
                        case "A37":
                            nombreGrupo = "A37";
                            break;    
                        case "B26":
                            nombreGrupo = "B26";
                            break;
                        case "A33":
                            nombreGrupo = "A33";
                            break;
                        case "A34":
                            nombreGrupo = "A34";
                            break;
                        case "A35":
                            nombreGrupo = "A35";
                            break;
                        case "A36":
                            nombreGrupo = "A36";
                            break;    
                        case "B05":
                            nombreGrupo = "B05";
                            break;    
                        case "B06":
                            nombreGrupo = "B06";
                            break;
                        case "A90":
                            nombreGrupo = "A90";
                            break;    
                        case "A91":
                            nombreGrupo = "A91";
                            break;
                        case "A95":
                            nombreGrupo = "A95";
                            break;    
                        case "B50": case "B51": case "B52": case "B53": case "B54":
                            nombreGrupo = "B50-B54";
                            break;
                        case "B55":
                            nombreGrupo = "B55";
                            break;    
                        case "B57":
                            nombreGrupo = "B57";
                            break;    
                        case "A82":
                            nombreGrupo = "A82";
                            break;    
                        case "A27":
                            nombreGrupo = "A27";
                            break;    
                        case "A87":
                            nombreGrupo = "A87";
                            break;
                        case "G00":
                            nombreGrupo = "G00";
                            break;
                        case "B01":
                            nombreGrupo = "B01";
                            break;    
                        case "B16":
                            nombreGrupo = "B16";
                            break;
                        case "B17":
                            nombreGrupo = "B17";
                            break;    
                        case "B19":
                            nombreGrupo = "B19";
                            break;
                        case "J12": case "J13": case "J14": case "J15": case "J16": case "J17": case "J18":
                            nombreGrupo = "J12-J18";
                            break;
                        case "T60":
                            nombreGrupo = "T60";
                            break;
                        case "A82":
                            nombreGrupo = "A82";
                            break;    
                        case "R50":
                            nombreGrupo = "R50";
                            break;
                        case "J00": case "J01": case "J02": case "J03": case "J04": case "J05": case "J06": case "J20": case "J21": case "J22":
                            nombreGrupo = "J00-J06yJ20-J22";
                            break;    
                        case "Y40": case "Y41": case "Y42": case "Y43": case "Y44": case "Y45": case "Y46": case "Y47": case "Y48": case "Y49": case "Y50":
                        case "Y51": case "Y52": case "Y53": case "Y54": case "Y55": case "Y56": case "Y57":
                            nombreGrupo = "Y40-Y57";
                            break;    
                        case "Y58": case "Y59":
                            nombreGrupo = "Y58-Y59";
                            break;
                        default:
                            nombreGrupo = "vacio"
                            break;
                    }
                }
            }
        }
        
        if(codigoF in listaCodigo.codigo){
            for (int i=0;i<listaCodigo.codigo.size();i++){
                if(listaCodigo.codigo[i]==codigoF){
                    println("encontrado:->"+listaCodigo.codigo[i])
                    switch (listaCodigo.codigo[i]){
                       case "A01.0": 
                            nombreGrupo: "A01.0"
                            break;
                       case "A92.2": 
                            nombreGrupo: "A92.2"
                            break;
                       case "A39.0": 
                            nombreGrupo: "A39.0"
                            break;     
                       case "A39.9": 
                            nombreGrupo: "A39.9"
                            break;     
                       case "A17.1": case "B18.2":
                            nombreGrupo: "A17.1-B18.2"
                            break;     
                       case "G82.0": 
                            nombreGrupo: "A82.0"
                            break;     
                       case "A96.8": 
                            nombreGrupo: "A96.8"
                            break;     
                       default:
                            nombreGrupo="vacio"
                            break;
                    }
                }
            }
        }
        
        if(Integer.parseInt(edadF) < 1){
            edadGrupo = "menosde1"
        }
        if(Integer.parseInt(edadF) >= 1 && Integer.parseInt(edadF) <=4){
            edadGrupo = "1a4"
        }
        if(Integer.parseInt(edadF) >= 5 && Integer.parseInt(edadF) <=6){
            edadGrupo = "5a6"
        }
        if(Integer.parseInt(edadF) >= 7 && Integer.parseInt(edadF) <=9){
            edadGrupo = "7a9"
        }
        if(Integer.parseInt(edadF) >= 10 && Integer.parseInt(edadF) <=11){
            edadGrupo = "10a11"
        }
        if(Integer.parseInt(edadF) >= 12 && Integer.parseInt(edadF) <=14){
            edadGrupo = "12a14"
        }
        if(Integer.parseInt(edadF) >= 15 && Integer.parseInt(edadF) <=19){
            edadGrupo = "15a19"
        }
        if(Integer.parseInt(edadF) >= 20 && Integer.parseInt(edadF) <=24){
            edadGrupo = "20a24"
        }
        if(Integer.parseInt(edadF) >= 25 && Integer.parseInt(edadF) <=44){
            edadGrupo = "25a44"
        }
        if(Integer.parseInt(edadF) >= 45 && Integer.parseInt(edadF) <=59){
            edadGrupo = "45a59"
        }
        if(Integer.parseInt(edadF) >= 60 && Integer.parseInt(edadF) <=64){
            edadGrupo = "60a64"
        }
        if(Integer.parseInt(edadF) >= 65){
            edadGrupo = "masde65"
        }
        
        if(nombreGrupo!="vacio" && edadGrupo!="vacio"){
            if(sexoF=="Masculino"){
                nombre = nombreGrupo+"_"+edadGrupo+"_H"
            }
            if(sexoF=="Femenino"){
                nombre = nombreGrupo+"_"+edadGrupo+"_M"
            }
            
        }
        
        
        return nombre
    }
    
    def agregaNodoNotificable(grupo,documento) {
        def ruta = ApplicationHolder.application.parentContext.servletContext.getRealPath("/data/reports/source/"+documento+".xml")
        DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance()
        DocumentBuilder docBuilder = docFactory.newDocumentBuilder()
        Document doc = docBuilder.parse(ruta)
        Node pacientes = doc.getFirstChild()
        
        def rangos = ["menosde1","1a4","5a6","7a9","10a11","12a14","15a19","20a24","25a44","45a59","60a64","masde65","edadignorada"]
        def cicloRangos = 0
        while(cicloRangos<=rangos.size()-1){
            
            Node hombreRango1 = doc.createElement(grupo+"_"+rangos[cicloRangos]+"_H")
            hombreRango1.setTextContent("0")
            pacientes.appendChild(hombreRango1)

            Node mujerRango1 = doc.createElement(grupo+"_"+rangos[cicloRangos]+"_M")
            mujerRango1.setTextContent("0")
            pacientes.appendChild(mujerRango1)
            cicloRangos++
        }
        
        Node totalHombre = doc.createElement(grupo+"_totalhombre")
        totalHombre.setTextContent("0")
        pacientes.appendChild(totalHombre)
        
        Node totalMujer = doc.createElement(grupo+"_totalmujer")
        totalMujer.setTextContent("0")
        pacientes.appendChild(totalMujer)
        
        Node totalGeneral = doc.createElement(grupo+"_totalgeneral")
        totalGeneral.setTextContent("0")
        pacientes.appendChild(totalGeneral)
        
        
        def output = XmlUtil.serialize(pacientes)
        def writer = new File(ApplicationHolder.application.parentContext.servletContext.getRealPath("/data/reports/source/"+documento+".xml"))
        writer.write(output)
        return true
    }
    
    public List getSections(String ruta){
        def sections = []
        this.getDomainTemplates(ruta).keySet().each {

            sections << it
        }
        
        return sections
    }
    
    public Map getDomainTemplates(String path){
        // Nuevo: para devolver los templates del dominio seleccionado
        //def domain = session.traumaContext.domainPath
        def domain = path
        def domainTemplates = grailsApplication.config.templates2."$domain"
        
        return domainTemplates
    }
    
    public List getSubsections( String section, String ruta ){
        def subsections = []
        
        this.getDomainTemplates(ruta)."$section".each { subsection ->
           subsections << section + "-" + subsection
        }
        return subsections
    }
    
    public boolean verificaEnfermedadNotificable(String subgrupo, String codigo){
        def contenido = false
        def gruposNotificables = ["A00","A08","A09","B15","A15","A16","A17","A18","A19","J10","J11",
                                  "A50","Z21","B20","B21","B22","B23","B24","A37","B26","A33","A34","A35","A36",
                                  "B05","B06","A90","A91","A95","B50","B51","B52","B53","B54","B55","B57","A82",
                                  "A27","A87","G00","B01","B16","B17","B19","J12","J13","J14","J15","J16","J17",
                                  "J18","T60","A82","R50","Y40","Y41","Y42","Y43","Y44","Y45","Y46","Y47","Y48",
                                  "Y49","Y50","Y51","Y52","Y53","Y54","Y55","Y56","Y57","Y58","Y59","A06","J00",
                                  "J01","J02","J03","J04","J05","J06","J20","J21","J22"]
        
        def codigosNotificables = ["A01.0","A92.2","A39.0","A39.9","B17.1","B18.2","G82.0","A96.8"]
        
        if(codigo=="NULL"){
            if(subgrupo in gruposNotificables){
                contenido = true
            }else{
                contenido = false
            }
        }
        else{
            if(codigo in codigosNotificables || subgrupo in gruposNotificables){
                contenido = true
            }
            else{
                contenido = false
            }
        }
        return contenido
    }
    
    public String tipoIdentificador (String rootIdentificador){
        def tipo = null
        def identificadorTemp = TipoIdentificador.withCriteria{
                eq('codigo',rootIdentificador)
        }
        if(identificadorTemp){
            tipo = identificadorTemp.nombreCorto
        }
      return tipo
    }
}
