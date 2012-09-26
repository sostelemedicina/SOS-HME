package authorization

class PreguntaSecreta {
    
    String pregunta

    static constraints = {
        
        pregunta(blank: false)
    }
}
