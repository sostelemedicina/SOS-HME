/**
 * 
 */
package tablasMaestras


/**
 * @author Pablo Pazos Gutierrez (pablo.swp@gmail.com)
 *
 */
class MotivoConsulta {

    String nombre // TODO: podria dejar solo codigos y el nombre lo dejo i18n
    String codigo // codigo interno
    
    //static transients = ['tipos']
    
    static def getTipos()
    {
        def ret = []
        
        ret << new MotivoConsulta(nombre:"Traumatizado por colisión vehicular", codigo:"evento.tipo.transito")
        ret << new MotivoConsulta(nombre:"Peatón embestido", codigo:"evento.tipo.peaton")
        ret << new MotivoConsulta(nombre:"Precipitado", codigo:"evento.tipo.precipitado")
        ret << new MotivoConsulta(nombre:"Caída desde su altura", codigo:"evento.tipo.caida")
        ret << new MotivoConsulta(nombre:"Injuria por quemadura", codigo:"evento.tipo.quemadura")
        ret << new MotivoConsulta(nombre:"Injuria por frío", codigo:"evento.tipo.frio")
        ret << new MotivoConsulta(nombre:"Herida(s) por proyectil(es) de arma de fuego", codigo:"evento.tipo.hpaf")
        ret << new MotivoConsulta(nombre:"Herida(s) punzocortante(s)", codigo:"evento.tipo.hpc")
        
        return ret
    }  
    
}
