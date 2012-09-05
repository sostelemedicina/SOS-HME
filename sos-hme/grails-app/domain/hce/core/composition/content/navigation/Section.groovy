package hce.core.composition.content.navigation

import hce.core.composition.content.ContentItem

class Section extends ContentItem {

    List items // Para que guarden en orden
    // PAB: corregido a items 17-01-2010
    static hasMany = [items:ContentItem]
    static mapping = {
        items column: "section_items"
        items cascade: "save-update"
    }

    static constraints = {
    }
}
