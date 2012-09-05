/**
 * 
 */
package templates.tom.constraints

/**
 * @author Pablo Pazos Gutierrez (pablo.swp@gmail.com)
 *
 */
class Transform extends FieldConstraint {

    // Operaciones disponibles
    static List operations = ["*", "+", "-", "/"]
    
    String operation
    String operand
    
    Object process( Object value )
    {
        // No tiene sentido procesar un valor nulo
        if (!value) throw new Exception("El valor no puede ser nulo")
        
        // No tiene sentido operar si ni es numerico
        if (!value instanceof java.lang.Number) throw new Exception("El valor pasado no se Number es: "+ value +" [" + value.getClass() + "]")
        switch (this.operation)
        {
            case "+": return processSum(value)
            case "-": return processMinus(value)
            case "/": return processDiv(value)
            case "*": return processMult(value)
            default: throw Exception("Operacion " + this.operation + " no soportada")
        }
    }
    
    String toString()
    {
        return "Transform: " + this.path + " " + this.operation + " " + this.operand
    }
    
    private Object processMult( Object value )
    {
        return value * Float.valueOf(this.operand)
    }
    private Object processDiv( Object value )
    {
        return value / Float.valueOf(this.operand)
    }
    private Object processSum( Object value )
    {
        return value + Float.valueOf(this.operand)
    }
    private Object processMinus( Object value )
    {
        return value - Float.valueOf(this.operand)
    }
    
}
