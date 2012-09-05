package binding

import org.openehr.am.archetype.constraintmodel.CObject

/**
 * @author Pablo Pazos Gutierrez
 *
 * Contenedor para los errores de validacion en el bindeo.
 * 
 */
class Errors {

	def errors = [:] // Map: arqId-> (Map: path-> ErrorList)

    public static String ERROR_BAD_FORMAT = "error.bad_format"
	public static String ERROR_OCCURRENCES = "error.occurrences"
    public static String ERROR_EMPTY = "error.empty"
	// TODO: errores por tipo de dato, para mostrar mejores mensajes. 

	def add(String arqId, String path, String errorType)
	{
		if (!errors[arqId]) errors[arqId] = [:] // Map: path-> ErrorList
		if (!errors[arqId][path]) errors[arqId][path] = [] // ErrorList
		errors[arqId][path] << errorType
	}
	def add(String arqId, CObject cobj, String errorType)
	{
		if (!errors[arqId]) errors[arqId] = [:] // Map: path-> ErrorList
		if (!errors[arqId][cobj.path()]) errors[arqId][cobj.path()] = [] // ErrorList
		errors[arqId][cobj.path()] << cobj.rmTypeName+"."+errorType
	}

	def getErrorsByArchetype( String arqId )
	{
		return errors[arqId]
	}

	def getErrors( String arqId, String path )
	{
		if (!errors[arqId]) return null
		return errors[arqId][path]
	}
	
	def hasErrors()
	{
		return errors.size() > 0
	}
	
	def hasErrorsForArchetype( String arqId )
	{
		if (!errors[arqId]) return false
		return errors[arqId].size() > 0
	}
	
	def hasErrorsForPath( String arqId, String path )
	{
		if (!errors[arqId]) return false
		if (!errors[arqId][path]) return false
		return errors[arqId][path].size() > 0
	}

	def hasErrorType( String arqId, String path, String errorType )
	{
		if (!errors[arqId]) return false
		if (!errors[arqId][path]) return false
		return errors[arqId][path].contains(errorType)
	}

	String toString()
	{
		return this.errors.toString()
	}
}
